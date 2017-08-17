package com.example.shreyas.i_form_builderapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shreyas.i_form_builderapp.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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
            setTitle("ID: "+ ListActivity.itemDetails.get(pos).getItemId());
            nameView.setText(ListActivity.itemDetails.get(pos).getName());
            ageView.setText(ListActivity.itemDetails.get(pos).getAge());
            dateView.setText(ListActivity.itemDetails.get(pos).getDate());
            phoneView.setText(ListActivity.itemDetails.get(pos).getPhone());
            new getItemImage().execute();

        }
    }

    // load image in the background
    private class getItemImage extends AsyncTask<Void,Void,Void> {
        Bitmap image;
        URL url = null;

        @Override
        protected Void doInBackground(Void... params) {

            try {
                url = new URL(ListActivity.itemDetails.get(pos).getImage());
                image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
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
