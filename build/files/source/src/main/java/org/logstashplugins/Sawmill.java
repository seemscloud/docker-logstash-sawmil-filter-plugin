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
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.io.File;

import io.logz.sawmill.GeoIpConfiguration;
import io.logz.sawmill.Pipeline;
import io.logz.sawmill.Doc;
import io.logz.sawmill.ExecutionResult;
import io.logz.sawmill.PipelineExecutor;

import java.util.Collection;
import java.util.Map;
import java.util.Arrays;

@LogstashPlugin(name = "sawmill")
public class Sawmill implements Filter {
    public static final PluginConfigSpec<String> PIPELINE_CONFIG = PluginConfigSpec.stringSetting("pipeline", "sawmill");
    public static final PluginConfigSpec<String> GEOIP_CONFIG = PluginConfigSpec.stringSetting("geoip", "GeoLite2");

    private String id;

    private String pipeline;
    private String geoIp;
    Pipeline sawmill;

    public Sawmill(String id, Configuration config, Context context) {
        this.id = id;
        this.pipeline = config.get(PIPELINE_CONFIG);
        this.geoIp = config.get(GEOIP_CONFIG);
    }

    @Override
    public Collection<Event> filter(Collection<Event> events, FilterMatchListener matchListener) {
        try {
            this.sawmill = SawmillSingleton.getInstance().getPipeline(this.pipeline, this.geoIp);

            for (Event e : events) {
                Doc doc = new Doc(e.toMap());
                ExecutionResult executionResult = new PipelineExecutor().execute(sawmill, doc);

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
        return Arrays.asList(PIPELINE_CONFIG, GEOIP_CONFIG);
    }

    @Override
    public String getId() {
        return this.id;
    }
}