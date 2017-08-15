package com.example.shreyas.i_form_builderapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shreyas.i_form_builderapp.APIServices.APIService;

import java.util.ArrayList;

public class DetailActivity extends Activity {

    int pos;
    APIService service;
    ArrayList<ItemDetail> itemDetails;

    ImageView imageView;
    TextView nameView, dateView, ageView, phoneView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        itemDetails = new ArrayList<>();
        service = new APIService();
        imageView = (ImageView) findViewById(R.id.view_image);
        nameView = (TextView) findViewById(R.id.view_name);
        ageView = (TextView) findViewById(R.id.view_age);
        phoneView = (TextView) findViewById(R.id.view_phone);
        dateView = (TextView) findViewById(R.id.view_date);

        Intent intent = getIntent();
        if(intent.hasExtra("position")) {
            pos = intent.getIntExtra("position",0);
            //service.generateDetailURL(pos);
            nameView.setText(itemDetails.get(pos).getName());
            ageView.setText(itemDetails.get(pos).getAge());
            dateView.setText(itemDetails.get(pos).getDate());
            phoneView.setText(itemDetails.get(pos).getPhone());

            Bitmap bitmap = BitmapFactory.decodeFile(itemDetails.get(pos).getImage());
            imageView.setImageBitmap(bitmap);
        }
    }
}
