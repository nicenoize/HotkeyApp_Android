package HotKeyApp.model;

import HotKeyApp.controller.MainController;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * HotKeyApp_GUI v0.5
 * #############################
 * @author Alexander Stahl
 * @version 0.5
 * #############################
 * Provides the needed CRUD-Operations for this program
 */
public class RestService {

    /** The HTTPClient to CREATE, READ, UPDATE or DELETE a resource */
    private final HttpClient client = new DefaultHttpClient();

    /** Address of the REST-Service */
    private final String serverAddress = "http://localhost:6666/";

    /**
     * Command: Connects to the REST-Service and tries to POST the given  JSON resource to the given URL.
     * @param url URL of the resource
     * @param content the content to POST
     * @return message if operation was successfull
     */
    public String httpPost(final String url, final JSONObject content){
        final HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 1000); //Timeout Limit
        final HttpResponse response;

        try {
            final HttpPost post = new HttpPost(serverAddress + url);
            final StringEntity se = new StringEntity(content.toString());
            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(se);
            response = client.execute(post);

            System.out.println(response.getStatusLine().getStatusCode());
            if(response.getStatusLine().getStatusCode() == 200 && url.contains("/vote/wish"))   return "Vote (Wish) erfolgreich abgegeben";
            if(response.getStatusLine().getStatusCode() == 400 && url.contains("/vote/wish"))   return "Du hast für diese Map bereits gevotet";
            if(response.getStatusLine().getStatusCode() == 200 && url.contains("/vote/work"))   return "Vote (Work) erfolgreich abgegeben";
            if(response.getStatusLine().getStatusCode() == 400 && url.contains("/vote/work"))   return "Du hast für diese Map bereits gevotet";
            if(response.getStatusLine().getStatusCode() == 201 && url.contains("register"))     return "Konto erfolgreich erstellt";
            if(response.getStatusLine().getStatusCode() == 400 && url.contains("register"))     return "Benutzername wird bereits verwendet";
            if(response.getStatusLine().getStatusCode() == 200 && url.contains("/map/new"))     return "Map erfolgreich gepostet. Vielen Dank!";
            if(response.getStatusLine().getStatusCode() == 400 && url.contains("/map/new"))     return "Map existiert bereits";
            if(response.getStatusLine().getStatusCode() == 201 && url.contains("/map/addKeys")) return "Neue Befehle wurden hizugefügt";
            if(response.getStatusLine().getStatusCode() == 400 && url.contains("/map/addKeys")) return "Keine neuen Befehle hinzugefügt";
        }

        catch (Exception e) {}

        return "REST-Service ist nicht erreichbar";
    }

    /**
     * Command: Connects to the REST-Service and tries to GET the resource at the given URL.
     * @param url URL of the resource
     * @return the ressource from the given URL as JSONArray
     */
    public JSONArray httpGet(final String url){
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 1000); //Timeout Limit
        final HttpResponse response;
        final JSONArray json;

        try {
            final HttpGet get = new HttpGet(serverAddress + url);
            response = client.execute(get);

            final String responseBody = response == null ? "" : EntityUtils.toString(response.getEntity());
            final JSONParser parser = new JSONParser();

            try {
                final Object obj = parser.parse(responseBody);
                json = (JSONArray) obj;
                return json;
            }
            catch (ParseException e) {}
        }

        catch (Exception e) {}

        return null;
    }

}
