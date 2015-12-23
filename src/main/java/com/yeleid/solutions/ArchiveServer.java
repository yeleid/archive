package com.yeleid.solutions;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.apache.log4j.Logger;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

public class ArchiveServer {
    private static final Logger logger = Logger.getLogger(ArchiveServer.class.getName());
    private Server server;

    public ArchiveServer(String ip, int port) throws Exception {
        JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();

        sf.setProvider(new JacksonJsonProvider());
        sf.setResourceClasses(ArchiveService.class);
        sf.setResourceProvider(ArchiveService.class, new SingletonResourceProvider(new ArchiveService()));
        sf.setAddress(String.format("http://%s:%d/", ip, port));

        server = sf.create();
        logger.info("Start Archive Server.");
    }

    public Server getServer() {
        return server;
    }

    public void close() {
        server.destroy();
        logger.info("Stop Archive Server.");
    }
}
