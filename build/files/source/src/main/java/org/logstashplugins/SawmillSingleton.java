package org.logstashplugins;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class SawmillSingleton {
    private static SawmillSingleton INSTANCE;

    private String pipeline = "";
    private String pipelineHash = "";

    private SawmillSingleton() { }

    public static SawmillSingleton getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new SawmillSingleton();
        }

        return INSTANCE;
    }

    public String getPipeline(String dir, String filename) {
        try {
            String path = dir + "/" + filename;
            String hex = checksum(path, MessageDigest.getInstance("MD5"));

            if (this.pipelineHash != hex) {
                this.pipelineHash = hex;
                this.pipeline = FileUtils.readFileToString(new File(path), "UTF-8");
                System.out.print("Detected changes, pipeline reload..");
            } else {
                System.out.print("Pipeline read from cache..");
            }
        }
        catch (Exception ex) {
            System.out.print(ex);
        }

        return this.pipeline;
    }

    private static String checksum(String filepath, MessageDigest md) throws IOException {

        try (InputStream fis = new FileInputStream(filepath)) {
            byte[] buffer = new byte[1024];
            int nread;
            while ((nread = fis.read(buffer)) != -1) {
                md.update(buffer, 0, nread);
            }
        }

        StringBuilder result = new StringBuilder();
        for (byte b : md.digest()) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}