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

import io.logz.sawmill.Condition;
import io.logz.sawmill.ConditionFactoryRegistry;
import io.logz.sawmill.ConditionalExecutionStep;
import io.logz.sawmill.ConditionalFactoriesLoader;
import io.logz.sawmill.DateTemplateHandler;
import io.logz.sawmill.Doc;
import io.logz.sawmill.ExecutionResult;
import io.logz.sawmill.ExecutionStep;
import io.logz.sawmill.FieldType;
import io.logz.sawmill.GeoIpConfiguration;
import io.logz.sawmill.Pipeline;
import io.logz.sawmill.PipelineExecutionMetricsMBean;
import io.logz.sawmill.PipelineExecutionMetricsTracker;
import io.logz.sawmill.PipelineExecutionTimeWatchdog;
import io.logz.sawmill.PipelineExecutor;
import io.logz.sawmill.ProcessResult;
import io.logz.sawmill.Processor;
import io.logz.sawmill.ProcessorExecutionStep;
import io.logz.sawmill.ProcessorFactoriesLoader;
import io.logz.sawmill.ProcessorFactoryRegistry;
import io.logz.sawmill.Result;
import io.logz.sawmill.SawmillConfiguration;
import io.logz.sawmill.Template;
import io.logz.sawmill.TemplateService;
import io.logz.sawmill.UnescapedMustacheFactory;
import io.logz.sawmill.UnescapedWithJsonStringMustacheFactory;
import io.logz.sawmill.WatchedPipeline;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Arrays;

@LogstashPlugin(name = "sawmill")
public class Sawmill implements Filter {
    public static final PluginConfigSpec<String> PIPELINE_CONFIG = PluginConfigSpec.stringSetting("pipeline", "sawmill");
    public static final PluginConfigSpec<String> GEOIP_CONFIG = PluginConfigSpec.stringSetting("geoip", "GeoLite2");

    private String id;

    private String pipeline;
    private String geoIp;

    public Sawmill(String id, Configuration config, Context context) {
        this.id = id;
        this.pipeline = config.get(PIPELINE_CONFIG);
        this.geoIp = config.get(GEOIP_CONFIG);
    }

    @Override
    public Collection<Event> filter(Collection<Event> events, FilterMatchListener matchListener) {
        try {
            String pipelinesDir = System.getenv("SAWMILL_PIPELINES_DIR");
            String pipelinesPath = pipelinesDir + "/" + this.pipeline + ".json";
            String pipelineString = FileUtils.readFileToString(new File(pipelinesPath), "UTF-8");

            String geoIpDir = System.getenv("SAWMILL_GEOIP_DB_DIR");
            String geoIpPath = geoIpDir + "/" + this.geoIp + ".mmdb";
            GeoIpConfiguration geoIpConfiguration = new GeoIpConfiguration(geoIpPath);

            Pipeline.Factory factory = new Pipeline.Factory(); // Factory(geoIpConfiguration);
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
        return Arrays.asList(PIPELINE_CONFIG, GEOIP_CONFIG);
    }

    @Override
    public String getId() {
        return this.id;
    }
}