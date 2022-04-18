package org.logstashplugins;

import org.apache.commons.io.FileUtils;

import io.logz.sawmill.Pipeline;
import io.logz.sawmill.GeoIpConfiguration;
import io.logz.sawmill.processors.GeoIpProcessor;

import java.io.File;
import java.io.IOException;

public final class SawmillSingleton {
    private static SawmillSingleton INSTANCE;

    private Pipeline pipeline = null;

    private SawmillSingleton() {  }

    public static SawmillSingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SawmillSingleton();
        }

        return INSTANCE;
    }

    public Pipeline getPipeline(String pipeline, String geoIp) {
        try {
            if (this.pipeline == null) {
                String pipelinesDir = System.getenv("SAWMILL_PIPELINES_DIR");
                System.out.print("Pipeline directory: " + pipelinesDir + "\n");
                String pipelinesPath = pipelinesDir + "/" + pipeline + ".json";
                System.out.print("Pipeline path: " + pipelinesPath + "\n");
                String pipelineString = FileUtils.readFileToString(new File(pipelinesPath), "UTF-8");

                String geoIpDir = System.getenv("SAWMILL_GEOIP_DB_DIR");
                System.out.print("GeoIP directory: " + geoIpDir + "\n");
                String geoIpPath = geoIpDir + "/" + geoIp + ".mmdb";
                System.out.print("GeoIP path: " + geoIpPath + "\n");
                GeoIpConfiguration geoIpConfiguration = new GeoIpConfiguration(geoIpPath);

                Pipeline.Factory factory = new Pipeline.Factory(); // geoIpConfiguration
                this.pipeline = factory.create(pipelineString);
            }
        }
        catch (Exception ex) {
            System.out.print(ex);
        }

        return this.pipeline;
    }
}