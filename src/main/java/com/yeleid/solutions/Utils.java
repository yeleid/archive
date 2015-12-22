package com.yeleid.solutions;

import com.google.common.io.ByteStreams;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utils {
    private static final Logger logger = Logger.getLogger(Utils.class.getName());

    private static Properties props = new Properties();
    private static Configuration conf = HBaseConfiguration.create();
    private static Connection connection = null;

    static {
        try {
            props.load(ClassLoader.getSystemResource(Constants.PROPERTIES).openStream());
        } catch (IOException e) {
            logger.error("Fail to locate properties file.");
            System.exit(1);
        }

        String table = Utils.get(Constants.HBASE_TABLE, "archive");
        String dataFamily = Utils.get(Constants.HBASE_DATA_FAMILY, "d");
        String dataColumn = Utils.get(Constants.HBASE_DATA_COLUMN, "d");
        String metaFamily = Utils.get(Constants.HBASE_META_FAMILY, "m");
        String metaColumn = Utils.get(Constants.HBASE_META_COLUMN, "m");

        logger.info("HBase info: ");
        logger.info("    Meta Family: " + metaFamily);
        logger.info("    Meta Column: " + metaColumn);
        logger.info("    Data Family: " + dataFamily);
        logger.info("    Data Column: " + dataColumn);
    }

    public static String get(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    public static byte[] getStream(InputStream in) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ByteStreams.copy(in, out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return out.toByteArray();
    }
}
