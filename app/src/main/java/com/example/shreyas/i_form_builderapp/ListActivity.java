package com.example.shreyas.i_form_builderapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.shreyas.i_form_builderapp.APIServices.APIService;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ListView idListView;
    ArrayAdapter<String> idAdapter;
    ArrayList<String> idList;
    public static ArrayList<ItemDetail> itemDetails;
    APIService serviceIds = new APIService();
    APIService serviceDetails;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        idListView = (ListView) findViewById(R.id.list_id);
        itemDetails = new ArrayList<>();

        Intent intent = getIntent();
        if(intent.hasExtra("token")){
            token = intent.getStringExtra("token");
            serviceIds.generateIdURL(token);
            new syncData().execute();
        }

        else
            token="";


        idListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //
                Intent intent = new Intent(ListActivity.this,DetailActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });

    }

    private class syncData extends AsyncTask<Void,Void,Void> {

        private ProgressDialog progressDialog;

        @Override
        protected Void doInBackground(Void... params) {
            serviceIds.getResponse();
            idList = serviceIds.getIdFromResponse();
            for(int i=0;i<idList.size();i++) {
                serviceDetails = new APIService();
                serviceDetails.generateDetailURL(token,i);
                serviceDetails.getResponse();
                ItemDetail item = serviceDetails.getItemDetailFromResponse();
                itemDetails.add(item);
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(ListActivity.this,null,"Synchronizing");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            idAdapter = new ArrayAdapter<>(ListActivity.this,android.R.layout.simple_list_item_1,idList);
            idListView.setAdapter(idAdapter);
            progressDialog.dismiss();
        }
    }


}
