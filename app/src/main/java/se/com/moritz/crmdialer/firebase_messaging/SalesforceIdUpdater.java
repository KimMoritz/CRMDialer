package se.com.moritz.crmdialer.firebase_messaging;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

import se.com.moritz.crmdialer.phonecall.MyBroadCastReceiver;

public class SalesforceIdUpdater extends AsyncTask {
    private static final String userName = resourceBundle.getString("sf_username");
    private static final String password = resourceBundle.getString("sf_password");
    private static final String loginInstanceDomain = resourceBundle.getString("sf_logininstance");
    private static final String apiVersion = resourceBundle.getString("sf_apiversion");
    private static final String consumerKey = resourceBundle.getString("sf_consumerkey");
    private static final String consumerSecret = resourceBundle.getString("sf_consumersecret");
    private static final String grantType = "password";
    private Header prettyPrintHeader = new BasicHeader("X-PrettyPrint", "1");
    private String TAG = new String("SalesforceIdUpdater");
    private JSONObject json;
    private String baseUri;
    private String contactId = resourceBundle.getString("sf_contactid1");
    private String newToken;

    public SalesforceIdUpdater(String newToken) {
        this.newToken = newToken;
        String phoneNumber = MyBroadCastReceiver.phoneNumber;
        phoneNumber = phoneNumber.substring(phoneNumber.length()-3, phoneNumber.length()-1);
        Log.i(TAG, "Phone number: " + phoneNumber);
        if(phoneNumber.equals("38")){
            this.contactId = "sf_contactid2";
        }else if(phoneNumber.equals("39")){
            this.contactId = "sf_contactid3";
        }
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        String loginHostUri = "https://" + loginInstanceDomain + "/services/oauth2/token";
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(loginHostUri);
            String requestBodyText = "grant_type=" + grantType + "&username=" + userName +
                    "&password=" + password + "&client_id=" + consumerKey + "&client_secret=" + consumerSecret;
            StringEntity requestBody = new StringEntity(requestBodyText);
            requestBody.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(requestBody);
            httpPost.addHeader(prettyPrintHeader);
            HttpResponse response = httpClient.execute(httpPost);

            if (  response.getStatusLine().getStatusCode() == 200 ) {
                String response_string = EntityUtils.toString(response.getEntity());
                json = new JSONObject(response_string);
                Log.i("salesforce", response_string);

                String REST_ENDPOINT = "/services/data";
                baseUri = json.get("instance_url") + REST_ENDPOINT + "/v" + apiVersion;
                Log.i(TAG,"\nSuccessfully logged in to instance: " + baseUri);
            } else {
                Log.i(TAG, "Fail");
            }

            URL url = new URL(baseUri+"/sobjects/Contact/" + contactId);
            HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("PATCH");
            httpURLConnection.setRequestProperty("Authorization", "OAuth " + json.get("access_token"));
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            OutputStreamWriter out = new OutputStreamWriter(httpURLConnection.getOutputStream());
            out.write(
                    "{ \n" +
                    "\"Description\" : \"" + newToken +"\"\n"+
                    "}"
            );
            out.flush();
            out.close();
            System.out.println(url);
            Log.i(TAG, "Salesforce response code: " + httpURLConnection.getResponseCode() + ": " + httpURLConnection.getResponseMessage());

        } catch (JSONException |IOException | NullPointerException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

}
