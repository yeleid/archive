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
    private static Configuration conf = HBaseConfiguration.create();

    private static TableName dataset;
    private static byte[] dataFamily;
    private static byte[] dataColumn;
    private static byte[] metaFamily;

    static {
        try {
            props.load(ClassLoader.getSystemResource(Constants.PROPERTIES).openStream());
        } catch (IOException e) {
            logger.error("Fail to locate properties file.");
            System.exit(1);
        }

        dataset = TableName.valueOf(Utils.get(Constants.HBASE_TABLE, "archive"));
        dataFamily = Bytes.toBytes(Utils.get(Constants.HBASE_DATA_FAMILY, "d"));
        dataColumn = Bytes.toBytes(Utils.get(Constants.HBASE_DATA_COLUMN, "d"));
        metaFamily = Bytes.toBytes(Utils.get(Constants.HBASE_META_FAMILY, "m"));

        logger.info("HBase info: ");
        logger.info("    Table      : " + dataset.getNameAsString());
        logger.info("    Meta Family: " + Bytes.toString(metaFamily));
        logger.info("    Data Family: " + Bytes.toString(dataFamily));
        logger.info("    Data Column: " + Bytes.toString(dataColumn));
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

    public static byte[] noSqlGetData(final String id) throws IOException {
        return noSqlExecutor(new NoSqlCommand() {
            @Override
            public byte[] execute(Connection connection, Table table) throws IOException {
                Get get = new Get(Bytes.toBytes(id));
                get.addColumn(dataFamily, dataColumn);
                Result res = table.get(get);
                return CellUtil.cloneValue(res.getColumnLatestCell(dataFamily, dataColumn));
            }
        });
    }

    public static boolean noSqlPutData(final String id, final byte[] data) throws IOException {
        return noSqlExecutor(new NoSqlCommand() {
            @Override
            public Boolean execute(Connection connection, Table table) throws IOException {
                Put put = new Put(Bytes.toBytes(id));
                put.addColumn(dataFamily, dataColumn, data);
                table.put(put);
                return true;
            }
        });
    }

    public static Map<String, byte[]> noSqlGetMeta(final String id) throws IOException {
        return noSqlExecutor(new NoSqlCommand() {
            @Override
            public Map<String, byte[]> execute(Connection connection, Table table) throws IOException {
                Get get = new Get(Bytes.toBytes(id));
                get.addFamily(metaFamily);
                Result res = table.get(get);

                Map<String, byte[]> map = new HashMap<String, byte[]>();
                for (Map.Entry<byte[], byte[]> entry : res.getFamilyMap(metaFamily).entrySet()) {
                    map.put(Bytes.toString(entry.getKey()), entry.getValue());
                }
                return map;
            }
        });
    }

    public static boolean noSqlPutMeta(final String id, final Map<String, byte[]> map) throws IOException {
        return noSqlExecutor(new NoSqlCommand() {
            @Override
            public Boolean execute(Connection connection, Table table) throws IOException {
                Put put = new Put(Bytes.toBytes(id));
                for (Map.Entry<String, byte[]> entry : map.entrySet()) {
                    put.addColumn(metaFamily, Bytes.toBytes(entry.getKey()), entry.getValue());
                }
                table.put(put);
                return true;
            }
        });
    }

    static interface NoSqlCommand {
        <T> T execute(Connection connection, Table table) throws IOException;
    }

    private static <T> T noSqlExecutor(NoSqlCommand command) throws IOException {
        Connection connection = null;
        Table table = null;
        try {
            connection = ConnectionFactory.createConnection(conf);
            table = connection.getTable(dataset);

            return command.execute(connection, table);

        } finally {
            if (connection != null) {
                connection.close();
            }
            if (table != null) {
                table.close();
            }
        }
    }
}
