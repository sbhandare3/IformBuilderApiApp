package com.example.shreyas.i_form_builderapp;

import android.content.Intent;
import android.os.AsyncTask;
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
    APIService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        service = new APIService();
        idList = new ArrayList<>();
        idListView = (ListView) findViewById(R.id.list_id);
        idAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,idList);
        idListView.setAdapter(idAdapter);

        idListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListActivity.this,DetailActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });


        service.generateIdURL();
        new gettingData().execute();
    }

    private class gettingData extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            service.getResponse();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            idList = service.getIdFromResponse();
            idAdapter.notifyDataSetChanged();
        }
    }


}
