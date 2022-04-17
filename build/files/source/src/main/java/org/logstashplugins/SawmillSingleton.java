package org.logstashplugins;

import org.apache.commons.io.FileUtils;

import java.io.File;

import java.io.IOException;

public final class SawmillSingleton {
    private static SawmillSingleton INSTANCE;

    private String pipeline = "";

    private SawmillSingleton() {
    }

    public static SawmillSingleton getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new SawmillSingleton();
        }

        return INSTANCE;
    }

    public String getPipeline(String dir, String filename) {
        try {
            if (this.pipeline == "") {
                File file = new File(dir + "/" + filename);
                this.pipeline = FileUtils.readFileToString(file, "UTF-8");
                System.out.print("Pipeline created..");
            } else {
                System.out.print("Pipeline read from cache..");
            }
        }
        catch (Exception ex) {
            System.out.print(ex);
        }

        return this.pipeline;
    }
}