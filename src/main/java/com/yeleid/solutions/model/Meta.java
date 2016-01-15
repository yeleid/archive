package com.yeleid.solutions.model;

import org.apache.hadoop.hbase.util.Bytes;

import java.util.HashMap;
import java.util.Map;

public class Meta {
    public Meta() {}

    private String filename;
    private String category;
    private String author;

    public String getFilename() {
        return filename;
    }
    public String getCategory() {
        return category;
    }
    public String getAuthor() {
        return author;
    }

    public void setFilename(String value) {
        filename = value;
    }
    public void setCategory(String value) {
        category = value;
    }
    public void setAuthor(String value) {
        author = value;
    }

    public static Meta fromMap(Map<String, byte[]> map) {
        Meta meta = new Meta();
        byte[] bytes;

        bytes = map.get("f");
        if (bytes != null) {
            meta.setFilename(Bytes.toString(bytes));
        }
        bytes = map.get("c");
        if (bytes != null) {
            meta.setCategory(Bytes.toString(bytes));
        }
        bytes = map.get("a");
        if (bytes != null) {
            meta.setAuthor(Bytes.toString(bytes));
        }

        return meta;
    }

    public Map<String, byte[]> toMap() {
        Map<String, byte[]> map = new HashMap<String, byte[]>();

        if (filename != null) {
            map.put("f", Bytes.toBytes(filename));
        }
        if (category != null) {
            map.put("c", Bytes.toBytes(category));
        }
        if (author != null) {
            map.put("a", Bytes.toBytes(author));
        }

        return map;
    }
}
