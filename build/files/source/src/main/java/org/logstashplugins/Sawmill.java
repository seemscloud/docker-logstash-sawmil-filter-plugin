package org.logstashplugins;

import co.elastic.logstash.api.Configuration;
import co.elastic.logstash.api.Context;
import co.elastic.logstash.api.Event;
import co.elastic.logstash.api.EventFactory;
import co.elastic.logstash.api.Filter;
import co.elastic.logstash.api.FilterMatchListener;
import co.elastic.logstash.api.LogstashPlugin;
import co.elastic.logstash.api.PluginConfigSpec;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

import io.logz.sawmill.ProcessResult;
import io.logz.sawmill.Processor;
import io.logz.sawmill.Template;
import io.logz.sawmill.TemplateService;
import io.logz.sawmill.Doc;
import io.logz.sawmill.ExecutionResult;
import io.logz.sawmill.Pipeline;
import io.logz.sawmill.PipelineExecutor;
import io.logz.sawmill.processors.GeoIpProcessor;
import io.logz.sawmill.GeoIpConfiguration;


import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@LogstashPlugin(name = "sawmill")
public class Sawmill implements Filter {
    public static final PluginConfigSpec<String> SOURCE_CONFIG = PluginConfigSpec.stringSetting("source", "message");

    private String id;
    private String sourceField;
    SawmillSingleton sawmillSingleton;

    public Sawmill(String id, Configuration config, Context context) {
        this.id = id;
        this.sourceField = config.get(SOURCE_CONFIG);
        this.sawmillSingleton = SawmillSingleton.getInstance();
    }

    @Override
    public Collection<Event> filter(Collection<Event> events, FilterMatchListener matchListener) {
        try {
            String dir = System.getenv("SAWMILL_PIPELINES_PATH");
            String filename = "fragment.json";
            String mmdbFilePath = "/root/config/GeoLite2-City.mmdb";

            GeoIpConfiguration geoIpConfiguration = new GeoIpConfiguration(mmdbFilePath);
            String pipelineString = this.sawmillSingleton.getPipeline(dir, filename);

            Pipeline.Factory factory = new Pipeline.Factory(geoIpConfiguration);
            Pipeline pipeline = factory.create(pipelineString);

            for (Event e : events) {
                Doc doc = new Doc(e.toMap());
                ExecutionResult executionResult = new PipelineExecutor().execute(pipeline, doc);

                Map<String, Object> map = doc.getSource();

                Event tmp = new org.logstash.Event(map);
                matchListener.filterMatched(tmp);

                e.overwrite(tmp);
            }
        }
        catch (Exception ex) {
            System.out.print(ex);
        }

        return events;
    }

    @Override
    public Collection<PluginConfigSpec<?>> configSchema() {
        return Collections.singletonList(SOURCE_CONFIG);
    }

    @Override
    public String getId() {
        return this.id;
    }
}