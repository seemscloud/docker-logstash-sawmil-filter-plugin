package org.logstashplugins;

import co.elastic.logstash.api.Configuration;
import co.elastic.logstash.api.Context;
import co.elastic.logstash.api.Event;
import co.elastic.logstash.api.Filter;
import co.elastic.logstash.api.FilterMatchListener;
import co.elastic.logstash.api.LogstashPlugin;
import co.elastic.logstash.api.PluginConfigSpec;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.logz.sawmill.Doc;
import io.logz.sawmill.ExecutionResult;
import io.logz.sawmill.Pipeline;
import io.logz.sawmill.PipelineExecutor;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Map;

@LogstashPlugin(name = "sawmill")
public class Sawmill implements Filter {
    public static final PluginConfigSpec<String> SOURCE_CONFIG = PluginConfigSpec.stringSetting("source", "message");

    private String id;
    private String sourceField;

    public Sawmill(String id, Configuration config, Context context) {
        this.id = id;
        this.sourceField = config.get(SOURCE_CONFIG);
    }

    @Override
    public Collection<Event> filter(Collection<Event> events, FilterMatchListener matchListener) {
        Pipeline pipeline = new Pipeline.Factory().create("{steps:[{removeField:{config:{path:\"message\"}}}]}");

        ObjectMapper mapper = new ObjectMapper();
        MapType mapType = mapper.getTypeFactory().constructMapType(LinkedHashMap.class, String.class, Object.class);

        for (Event e : events) {
            Doc document = new Doc(e);
        }

//         for (Event e : events) {
//             try {
//                 System.out.println("Debug");
//                 LinkedHashMap<String, Object> map = mapper.readValue(e.toString(), mapType);
//                 Doc document = new Doc(map);
//                 ExecutionResult executionResult = new PipelineExecutor().execute(pipeline, document);
//
//                 if (executionResult.isSucceeded()) {
//                     System.out.println("OK" + document.toString());
//                 }
//             } catch (JsonProcessingException exp) {
//                 System.out.println(exp);
//             }
//             matchListener.filterMatched(e);
//         }

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
