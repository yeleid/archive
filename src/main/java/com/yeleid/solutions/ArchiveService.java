package com.yeleid.solutions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import javax.rmi.CORBA.Util;
import javax.ws.rs.*;

import com.google.common.io.ByteStreams;
import org.apache.commons.io.IOUtils;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import org.apache.log4j.Logger;

@Path("/archive")
@Produces("application/json")
public class ArchiveService {
    private static final Logger logger = Logger.getLogger(ArchiveService.class.getName());

    @GET
    @Path("/info")
    public String info() {
        return "Archive System 0.4 @yeleid";
    }

    @POST
    @Path("/nosql/data/put")
    public String noSqlPut(MultipartBody body) throws IOException {

        byte[] data = null;
        String filename = null;
        String id = UUID.randomUUID().toString();

        for (Attachment attachment : body.getAllAttachments()) {
            if (Constants.FIELD_DATA.equalsIgnoreCase(attachment.getContentDisposition().getParameter(Constants.MULTIPART_FIELD_NAME))) {
                filename = attachment.getContentDisposition().getParameter(Constants.MULTIPART_FIELD_FILENAME);
                data = Utils.getStream(attachment.getDataHandler().getInputStream());
            }
            else if (Constants.FIELD_ID.equalsIgnoreCase(attachment.getContentDisposition().getParameter(Constants.MULTIPART_FIELD_NAME))) {
                id = new String(Utils.getStream(attachment.getDataHandler().getInputStream()));
            }
        }

        logger.info("Archive file id  : " + id);
        logger.info("Archive file name: " + filename);

        return id;
    }
}
