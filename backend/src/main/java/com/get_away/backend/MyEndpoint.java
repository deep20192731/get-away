/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.get_away.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

/** An endpoint class we are exposing */
@Api(
  name = "getawayApi",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "backend.get_away.com",
    ownerName = "backend.get_away.com",
    packagePath=""
  )
)
public class MyEndpoint {

    private Map<String,String> userTokens = new HashMap<>();

    @ApiMethod(name = "register" ,path = "register")
    public MyBean register(@Named("fsqid") String fsqid, @Named("token") String token) {
        MyBean response = new MyBean();
        response.setData("Received  a registration request for " + fsqid + " " + token);
        System.out.println("recived fsqid " + fsqid + " token " + token);
        userTokens.put(fsqid, token);

        JSONObject jGcmData = new JSONObject();
        JSONObject jData = new JSONObject();
        jData.put("message", "wassup"); //we can add our payload here as a compressed JSON string
        jGcmData.put("to", token);

        // What to send in GCM message.
        jGcmData.put("data", jData);

        //Some wierd reason unable to send messages through Taskqueue
        //TaskPushQueue queue = TaskPushQueue.getInstance();
        //queue.push(jGcmData.toString());

        /*Synchronously sending*/
        NotifPosterTask n =  new NotifPosterTask(jGcmData.toString());
        n.execute();
        return response;
    }

}
