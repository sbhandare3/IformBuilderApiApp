package com.example.shreyas.i_form_builderapp.APIServices;

import com.example.shreyas.i_form_builderapp.Classes.ItemDetail;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by shreyas on 8/15/2017.
 */

@SuppressWarnings("deprecation")
public class APIService {

    private String url;
    private ArrayList<String> idList;
    private StringBuilder builder;

    // initialize objects, vairables
    public APIService(){
        url = "https://app.iformbuilder.com/exzact/api/profiles/470103/pages/3639672/records";
        idList = new ArrayList<>();
        builder = new StringBuilder();
    }

    // generate URL to get ID list
    public void generateIdURL(String token){
        url+="?ACCESS_TOKEN="+token+"&VERSION=5.1";
    }

    // generate URL to get Detail List
    public void generateDetailURL(String token, int pos){
        int id = (pos+1)*3;
        url = url+"/"+id+"/feed?ACCESS_TOKEN="+token+"&VERSION=5.1&FORMAT=json";
    }

    // Get Http response
    public void getResponse(){
        try {

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet getRequest = new HttpGet(url);

            HttpResponse response = httpClient.execute(getRequest);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

            for (String line = null; (line = br.readLine()) != null;) {
                builder.append(line).append("\n");
            }

            httpClient.getConnectionManager().shutdown();

        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    // get List of Ids from response
    public ArrayList<String> getIdFromResponse(){
        JSONObject jsonObj;
        try {
            jsonObj = new JSONObject(builder.toString());
            JSONArray recordArray = jsonObj.getJSONArray("RECORDS");
            for(int i = 0; i < recordArray.length(); i++)
            {
                JSONObject objects = recordArray.getJSONObject(i);
                idList.add("Id: "+Integer.toString(objects.getInt("ID")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Collections.reverse(idList);
        return idList;
    }

    // get details of specific id from response
    public ItemDetail getItemDetailFromResponse(){
        ItemDetail newItem = null;
        JSONArray jsonArray;

        try {
            jsonArray = new JSONArray(builder.toString());
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            JSONObject jsonObject1 = jsonObject.getJSONObject("record");

            newItem = new ItemDetail();
            newItem.setItemId(jsonObject1.getString("ID"));
            newItem.setName(jsonObject1.getString("my_name"));
            newItem.setPhone(jsonObject1.getString("my_phone"));
            newItem.setDate(jsonObject1.getString("my_date"));
            newItem.setAge(Integer.toString(jsonObject1.getInt("age")));

            String imgStr = jsonObject1.getString("my_image");
            newItem.setImage(imgStr);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return newItem;
    }

}
