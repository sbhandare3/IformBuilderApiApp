package com.example.shreyas.i_form_builderapp.APIServices;

import com.example.shreyas.i_form_builderapp.ItemDetail;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by shreyas on 8/15/2017.
 */

public class APIService {

    String url = "https://app.iformbuilder.com/exzact/api/profiles/470103/pages/3639672/records";
    ArrayList<String> idList;
    StringBuilder builder;

    public APIService(){
        idList = new ArrayList<>();
        builder = new StringBuilder();
    }

    public void generateIdURL(){
        url+="?ACCESS_TOKEN=c4ddc0f068620718bc85d8fb4413122580716ef3&VERSION=5.1";
    }

    public void generateDetailURL(int pos){
        int id = (pos+1)*3;
        url = url+"/"+id+"/feed?ACCESS_TOKEN=c4ddc0f068620718bc85d8fb4413122580716ef3&VERSION=5.1&FORMAT=json";
    }

    public void getResponse(){
        try {

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet getRequest = new HttpGet(url);
            //getRequest.addHeader("accept", "application/json");

            HttpResponse response = httpClient.execute(getRequest);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

            for (String line = null; (line = br.readLine()) != null;) {
                builder.append(line).append("\n");
            }

            /*
            output = EntityUtils.toString((HttpEntity) response);
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            */

            httpClient.getConnectionManager().shutdown();

        } catch (ClientProtocolException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    public ArrayList<String> getIdFromResponse(){
        JSONObject jsonObj;
        try {
            jsonObj = new JSONObject(builder.toString());
            JSONArray recordArray = jsonObj.getJSONArray("RECORDS");
            for(int i = 0; i < recordArray.length(); i++)
            {
                JSONObject objects = recordArray.getJSONObject(i);
                idList.add(Integer.toString(objects.getInt("ID")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return idList;
    }

    public ArrayList<ItemDetail> getItemDetailFromResponse(){
        ArrayList<ItemDetail> itemDetails = new ArrayList<>();
        JSONArray jsonArray;

        try {
            jsonArray = new JSONArray(builder.toString());
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            JSONObject jsonObject1 = jsonObject.getJSONObject("record");

            ItemDetail newItem = new ItemDetail();
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

        return itemDetails;
    }

}
