package edu.tufts.gis.projectexplorer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultBHttpClientConnection;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.protocol.*;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by cbarne02 on 4/21/15.
 */
public class AbstractJsonResponseService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    protected List<?> responseMapper(String jsonString, Class clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonString);

        // ArrayNode users = (ArrayNode) root.path("users");
        List response = new ArrayList<>();

        if (root.isArray()) {
            Iterator<JsonNode> iterator = root.iterator();
            while (iterator.hasNext()) {
                Object object = mapper.readValue(iterator.next().toString(), clazz);
                response.add(clazz.cast(object));
            }
        } else {

            Object object = mapper.readValue(root.asText(), clazz);
            response.add(clazz.cast(object));
        }


        return response;
    }


    protected List<?> sendPost(String url$, String text, Class responseClass) throws IOException, HttpException {

        URL url = new URL(url$);
        HttpProcessor httpproc = HttpProcessorBuilder.create()
                .add(new RequestContent())
                .add(new RequestTargetHost())
                .add(new RequestConnControl())
                .add(new RequestUserAgent("Test/1.1"))
                .add(new RequestExpectContinue(true)).build();

        HttpRequestExecutor httpexecutor = new HttpRequestExecutor();

        HttpCoreContext coreContext = HttpCoreContext.create();
        HttpHost host = new HttpHost(url.getHost(), url.getPort()); //does port default to 80 if none specified?
        coreContext.setTargetHost(host);

        DefaultBHttpClientConnection conn = new DefaultBHttpClientConnection(8 * 1024);
        ConnectionReuseStrategy connStrategy = DefaultConnectionReuseStrategy.INSTANCE;

        try {

            HttpEntity requestBody = new StringEntity(
                    text,
                    ContentType.create("text/plain", Consts.UTF_8));
            //use the following if we need to chunk

 /*                   new InputStreamEntity(
                            new ByteArrayInputStream(
                                    "This is the third test request (will be chunked)"
                                            .getBytes("UTF-8")),
                            ContentType.APPLICATION_OCTET_STREAM)
                            */


            if (!conn.isOpen()) {
                log.info("port: {}", Integer.toString(host.getPort()));
                Socket socket;
                if (host.getPort() == -1){
                    socket = new Socket(host.getHostName(), 80);
                } else {
                    socket = new Socket(host.getHostName(), host.getPort());
                }
                conn.bind(socket);
            }

            BasicHttpEntityEnclosingRequest request = new BasicHttpEntityEnclosingRequest("POST",
                    url.getPath());

            request.setEntity(requestBody);


            System.out.println(">> Request URI: " + request.getRequestLine().getUri());

            //what's actually going on here? why all the extra stuff?
            httpexecutor.preProcess(request, httpproc, coreContext);
            HttpResponse response = httpexecutor.execute(request, conn, coreContext);
            httpexecutor.postProcess(response, httpproc, coreContext);

            HttpEntity entity = response.getEntity();
            String response$ = EntityUtils.toString(entity);

            System.out.println("<< Response: " + response.getStatusLine());
            System.out.println(response$);
            System.out.println("==============");
            //convert the string response to an object using Jackson


            if (!connStrategy.keepAlive(response, coreContext)) {
                conn.close();
            } else {
                System.out.println("Connection kept alive...");
            }
            EntityUtils.consumeQuietly(entity);

            return responseMapper(response$, responseClass);

        } finally {
            conn.close();
        }

    }
}
