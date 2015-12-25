package com.yeleid.solutions;

import com.google.common.io.ByteStreams;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Utils {
    private static final Logger logger = Logger.getLogger(Utils.class.getName());

    private static Properties props = new Properties();

    static {
        try {
            props.load(ClassLoader.getSystemResource(Constants.PROPERTIES).openStream());
        } catch (IOException e) {
            logger.error("Fail to locate properties file.");
            System.exit(1);
        }
    }

    public static String get(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    public static byte[] getBytes(InputStream in) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ByteStreams.copy(in, out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return out.toByteArray();
    }

    public static InputStream getStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }
}
