package com.example.shreyas.i_form_builderapp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shreyas.i_form_builderapp.APIServices.AccessToken;
import com.example.shreyas.i_form_builderapp.R;

public class LoginActivity extends AppCompatActivity {

    EditText mUname, mPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUname = (EditText) findViewById(R.id.edit_username);
        mPass = (EditText) findViewById(R.id.edit_password);
    }

    // validate/authenticate user with username and password
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
            //Toast.makeText(LoginActivity.this,"Success", Toast.LENGTH_SHORT).show();
            // generate token for 60 min
            new gettingToken().execute();
        }
        else{
            Toast.makeText(LoginActivity.this,"Please Enter Valid Username and Password", Toast.LENGTH_SHORT).show();
        }
    }

    // if user is authenticated with username and password, get token and start listactvity
    private class gettingToken extends AsyncTask<Void,Void,Void> {

        private ProgressDialog progressDialog;
        String accessToken = null;

        @Override
        protected Void doInBackground(Void... params) {
            String clientKey = "481c7d7bf5652c17c3e2404773c32b117604d788";
            String clientSecret = "2e6fa3b9cf28f945f844a1ab2fa235b08d66be07";
            String URL = "https://app.iformbuilder.com/exzact/api/oauth/token";
            accessToken = AccessToken.getToken(clientKey, clientSecret, URL);
            //System.out.println("accessToken = " + accessToken);
            return null;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(LoginActivity.this,null,"Authenticating..");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog = ProgressDialog.show(LoginActivity.this,null,"Success!");
            progressDialog.dismiss();
            Intent intent = new Intent(LoginActivity.this,ListActivity.class);
            intent.putExtra("token",accessToken);
            startActivity(intent);
            finish();
        }
    }

}
