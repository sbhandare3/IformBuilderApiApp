package com.example.shreyas.i_form_builderapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shreyas.i_form_builderapp.APIServices.APIService;

public class LoginActivity extends AppCompatActivity {

    EditText mUname, mPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUname = (EditText) findViewById(R.id.edit_username);
        mPass = (EditText) findViewById(R.id.edit_password);
    }

    public void validateAndContinue(View view){
        if(mUname.getText().toString().equals("") && mPass.getText().toString().equals("")){
            Toast.makeText(LoginActivity.this,"Enter Username and Password", Toast.LENGTH_SHORT).show();
        }
        else if(mUname.getText().toString().equals("")){
            Toast.makeText(LoginActivity.this,"Enter Username", Toast.LENGTH_SHORT).show();
        }
        else if(mPass.getText().toString().equals("")){
            Toast.makeText(LoginActivity.this,"Enter Password", Toast.LENGTH_SHORT).show();
        }
        else if(mUname.getText().toString().equals("admin") && mPass.getText().toString().equals("admin")){
            Toast.makeText(LoginActivity.this,"Success", Toast.LENGTH_SHORT).show();
            // generate token for 60 min
            new synching().execute();
        }
        else{
            Toast.makeText(LoginActivity.this,"Please Enter Valid Username and Password", Toast.LENGTH_SHORT).show();
        }
    }

    private class synching extends AsyncTask<Void,Void,Void> {

        private ProgressDialog progressDialog;

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(LoginActivity.this,null,"Synchronizing");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            startActivity(new Intent(LoginActivity.this,ListActivity.class));
            finish();
        }
    }


}
