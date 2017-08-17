package com.example.shreyas.i_form_builderapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shreyas.i_form_builderapp.APIServices.APIService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.shreyas.i_form_builderapp.ListActivity.itemDetails;

public class DetailActivity extends Activity {

    int pos;

    ImageView imageView;
    TextView nameView, dateView, ageView, phoneView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageView = (ImageView) findViewById(R.id.view_image);
        nameView = (TextView) findViewById(R.id.view_name);
        ageView = (TextView) findViewById(R.id.view_age);
        phoneView = (TextView) findViewById(R.id.view_phone);
        dateView = (TextView) findViewById(R.id.view_date);

        Intent intent = getIntent();
        if(intent.hasExtra("position")) {
            pos = intent.getIntExtra("position",0);
            //service.generateDetailURL(pos);
            setTitle("ID: "+itemDetails.get(pos).getItemId());
            nameView.setText(itemDetails.get(pos).getName());
            ageView.setText(itemDetails.get(pos).getAge());
            dateView.setText(itemDetails.get(pos).getDate());
            phoneView.setText(itemDetails.get(pos).getPhone());
            new getItemImage().execute();

        }
    }

    private class getItemImage extends AsyncTask<Void,Void,Void> {
        Bitmap image;
        URL url = null;

        @Override
        protected Void doInBackground(Void... params) {

            try {
                url = new URL(itemDetails.get(pos).getImage());
                image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            imageView.setImageBitmap(image);
        }
    }
}
