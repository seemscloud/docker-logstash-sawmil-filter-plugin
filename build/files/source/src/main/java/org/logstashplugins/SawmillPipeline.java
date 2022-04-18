package org.logstashplugins;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import io.logz.sawmill.Pipeline;
import io.logz.sawmill.GeoIpConfiguration;

public final class SawmillPipeline {
    private String geoIpPath;
    private String pipelinePath;
    private String pipelineString;
    private GeoIpConfiguration geoIpConfiguration;
    private Pipeline.Factory factory;
    private Pipeline pipeline;

    public SawmillPipeline (String geoIpPath, String pipelinePath) {
        try {
            this.geoIpPath = geoIpPath;
            this.pipelinePath = pipelinePath;
            this.pipelineString = FileUtils.readFileToString(new File(this.pipelinePath), "UTF-8");
            this.geoIpConfiguration = new GeoIpConfiguration(this.geoIpPath);
            this.factory = new Pipeline.Factory(); // Factory(geoIpConfiguration)
            this.pipeline = this.factory.create(this.pipelineString);
        }
        catch (Exception exception) {
            System.out.print(exception);
        }
    }

    public Pipeline getPipeline(){
        return this.pipeline;
    }

    public String getPipelinePath(){
        return this.pipelinePath;
    }
}