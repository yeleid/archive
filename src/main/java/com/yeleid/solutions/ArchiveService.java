package com.yeleid.solutions;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.yeleid.solutions.model.Meta;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;

import org.apache.log4j.Logger;

@Path("/archive")
@Produces(MediaType.APPLICATION_JSON)
public class ArchiveService {
    private static final Logger logger = Logger.getLogger(ArchiveService.class.getName());

    @GET
    @Path("/info")
    public String info() {
        return "Archive System 0.4 @yeleid";
    }

    @POST
    @Path("/nosql/data/put/{id}")
    public String noSqlDataPut(@PathParam("id") String id, MultipartBody body) throws IOException {

        byte[] data = null;
        String filename = null;
        if (Constants.PLACEHOLDER_NEW_FILE.equals(id))
            id = UUID.randomUUID().toString();

        for (Attachment attachment : body.getAllAttachments()) {
            if (Constants.FIELD_DATA.equalsIgnoreCase(attachment.getContentDisposition().getParameter(Constants.MULTIPART_FIELD_NAME))) {
                filename = attachment.getContentDisposition().getParameter(Constants.MULTIPART_FIELD_FILENAME);
                data = Utils.getBytes(attachment.getDataHandler().getInputStream());
            }
        }

        logger.info("Archive file id  : " + id);
        logger.info("Archive file name: " + filename);

        if (!NoSqlEngine.noSqlPutData(id, data)) {
            logger.error("Failed to store data [" + id + "]");
        }

        return id;
    }

    @GET
    @Path("/nosql/data/get/{id}")
    public Response noSqlDataGet(@PathParam("id") String id) throws IOException {
        byte[] data = NoSqlEngine.noSqlGetData(id);
        return Response.ok(Utils.getStream(data), MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + id + "\"" )
                .build();
    }

    @POST
    @Path("/nosql/meta/put/{id}")
    public boolean noSqlMetaPut(@PathParam("id") String id, Meta meta) throws IOException {
        Map<String, byte[]> map = meta.toMap();
        return NoSqlEngine.noSqlPutMeta(id, map);
    }

    @GET
    @Path("/nosql/meta/get/{id}")
    public Meta noSqlMetaGet(@PathParam("id") String id) throws IOException {
        Map<String, byte[]> map = NoSqlEngine.noSqlGetMeta(id);
        return Meta.fromMap(map);
    }

    @POST
    @Path("/search")
    public SearchEngine.Response search(String query) throws IOException {
        SearchEngine.Request request = new SearchEngine.RequestBuilder()
                .setQuery(query)
                .build();
        return SearchEngine.search(request);
    }
}
