package com.yeleid.solutions;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.cxf.helpers.IOUtils;
import org.apache.hadoop.util.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

public class SearchEngine {
    public static class Request {
        private String query;
        public String getQuery() { return  query; }
        public void setQuery(String value) { query = value; }

        public String toEncodedQueryString() {
            StringBuilder sb = new StringBuilder();
            try {
                sb.append("q=" + URLEncoder.encode(query, "UTF-8"));
                sb.append("&wt=json&indent=true");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return sb.toString();
        }
    }

    public static class Response {
        Response(int status, String content) {
            this.status = status;
            this.content = content;
        }
        private int status;
        private String content;
        public String getContent() { return content; }
        public void setContent(String value) { content = value; }
    }

    private static interface LoadBalance {
        String getServer();
        List<String> getServers();
    }

    private static final Logger logger = Logger.getLogger(SearchEngine.class.getName());
    private static CloseableHttpClient client = null;
    private static LoadBalance loadBalance = new LoadBalance() {
        private List<String> servers = null;
        private int index = 0;
        {
            String param = Utils.get(Constants.SOLR_SERVERS, "127.0.0.1:8983");
            servers = Arrays.asList(param.split(","));
            logger.info("Search servers: " + StringUtils.join(", ", servers));
            if (servers.size() <= 0) {
                logger.error("Failed to locate search server.");
                System.exit(1);
            }
        }
        @Override
        public String getServer() {
            String server = servers.get(index);
            index = (index + 1) % servers.size();
            return server;
        }
        @Override
        public List<String> getServers() {
            return servers;
        }
    };
    private static String dataset = Utils.get(Constants.SOLR_COLLECTION, "solr_collection");

    public static Response search(Request request) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://" + loadBalance.getServer() + "/solr/" + dataset + "/select?" + request.toEncodedQueryString());
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = client.execute(httpGet);
            String content = null;
            if (HttpStatus.SC_OK == httpResponse.getStatusLine().getStatusCode()) {
                HttpEntity entity = httpResponse.getEntity();
                content = IOUtils.readStringFromStream(entity.getContent());
            }
            return new Response(httpResponse.getStatusLine().getStatusCode(), content);

        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
            if (client != null) {
                client.close();
            }
        }
    }
}
