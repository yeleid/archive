package com.yeleid.solutions;

import java.security.PrivilegedExceptionAction;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.hadoop.security.UserGroupInformation;

import org.apache.hadoop.fs.FileSystem;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import org.apache.log4j.Logger;

@Path("/archive")
@Produces("application/json")
public class ArchiveService {
    private static final Logger logger = Logger.getLogger(ArchiveService.class.getName());

    private Configuration conf = HBaseConfiguration.create();
    private Connection connection = null;

    @GET
    @Path("/info")
    public String info() {
        return "Archive System 0.4 @yeleid";
    }

}
