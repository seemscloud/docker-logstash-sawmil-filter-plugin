package org.logstashplugins;

import co.elastic.logstash.api.Configuration;
import co.elastic.logstash.api.Context;
import co.elastic.logstash.api.Event;
import co.elastic.logstash.api.Filter;
import co.elastic.logstash.api.FilterMatchListener;
import co.elastic.logstash.api.LogstashPlugin;
import co.elastic.logstash.api.PluginConfigSpec;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;

import io.logz.sawmill.Doc;
import io.logz.sawmill.ExecutionResult;
import io.logz.sawmill.Pipeline;
import io.logz.sawmill.PipelineExecutor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collection;
import java.util.Collections;

@LogstashPlugin(name = "java_filter_example")
public class JavaFilterExample implements Filter {

    public static final PluginConfigSpec<String> SOURCE_CONFIG =
            PluginConfigSpec.stringSetting("source", "message");

    private String id;
    private String sourceField;

    public JavaFilterExample(String id, Configuration config, Context context) {
        this.id = id;
        this.sourceField = config.get(SOURCE_CONFIG);
    }

    public static Doc createDoc(Object... objects) {
        if (objects.length % 2 != 0) {
            throw new RuntimeException("Can't construct map out of uneven number of elements");
        }

        LinkedHashMap map = new LinkedHashMap<>();
        if (objects != null) {
            for (int i = 0; i < objects.length; i++) {
                map.put(objects[i], objects[++i]);
            }
        }
        return new Doc(map);
    }

    @Override
    public Collection<Event> filter(Collection<Event> events, FilterMatchListener matchListener) {
        Doc doc = createDoc("message", "testing geoip resolving", "ip", "172.217.11.174");

        Pipeline pipeline = new Pipeline.Factory().create(
                    "{ steps :[{\n" +
                    "    removeField: {\n" +
                    "      config: {\n" +
                    "        path: \"message\"\n" +
                    "      }\n" +
                    "    }\n" +
                    "  }]\n" +
                    "}");

        ExecutionResult executionResult = new PipelineExecutor().execute(pipeline, doc);

        if (executionResult.isSucceeded()) {
            System.out.println("Success! result is:"+doc.toString());
        }

        for (Event e : events) {
            Object f = e.getField(sourceField);
            if (f instanceof String) {
                e.setField(sourceField, StringUtils.reverse((String)f));
                matchListener.filterMatched(e);
            }
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
