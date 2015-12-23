package com.yeleid.solutions;

import org.apache.hadoop.hbase.util.Bytes;

import java.util.HashMap;
import java.util.Map;

public class Model {
    public Model () {}

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

    public static Model fromMap(Map<String, byte[]> map) {
        Model model = new Model();
        byte[] bytes;

        bytes = map.get("filename");
        if (bytes != null) {
            model.setFilename(Bytes.toString(bytes));
        }
        bytes = map.get("category");
        if (bytes != null) {
            model.setCategory(Bytes.toString(bytes));
        }
        bytes = map.get("author");
        if (bytes != null) {
            model.setAuthor(Bytes.toString(bytes));
        }

        return model;
    }

    public Map<String, byte[]> toMap() {
        Map<String, byte[]> map = new HashMap<String, byte[]>();

        if (filename != null) {
            map.put("filename", Bytes.toBytes(filename));
        }
        if (category != null) {
            map.put("category", Bytes.toBytes(category));
        }
        if (author != null) {
            map.put("author", Bytes.toBytes(author));
        }

        return map;
    }
}
