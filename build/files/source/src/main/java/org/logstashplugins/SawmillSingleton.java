package org.logstashplugins;

import io.logz.sawmill.Pipeline;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public final class SawmillSingleton {
    private static SawmillSingleton INSTANCE;

    List<SawmillPipeline> pipelines = new ArrayList<SawmillPipeline>();

    private SawmillSingleton() {  }

    public static SawmillSingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SawmillSingleton();
        }

        return INSTANCE;
    }

    public Pipeline getPipeline(String geoIp, String pipeline) {
        String geoIpPath = System.getenv("SAWMILL_GEOIP_DB_DIR") + "/" + geoIp + ".mmdb";
        String pipelinePath = System.getenv("SAWMILL_PIPELINES_DIR") + "/" + pipeline + ".json";

        SawmillPipeline sawmillPipeline = null;

        for (int i = 0; i < this.pipelines.size(); i++) {
            if (pipelinePath.equals(this.pipelines.get(i).getPipelinePath())) {
                sawmillPipeline = this.pipelines.get(i);
                break;
            }
        }

        if (sawmillPipeline == null) {
            sawmillPipeline = new SawmillPipeline(geoIpPath, pipelinePath);
            this.pipelines.add(sawmillPipeline);
        }

        System.out.print(this.pipeline.size();

        return sawmillPipeline.getPipeline();
    }
}