package com.tbeta.ephraim.phpmysqllogin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    EditText InputUserName, InputPassWord;
    Button login;
    ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InputUserName = (EditText) findViewById(R.id.inputUsername);
        InputPassWord = (EditText) findViewById(R.id.inputPassword);
        login = (Button) findViewById(R.id.loginBtn);
        loader = (ProgressBar) findViewById(R.id.loadingSpinner);

        //Set onClick listener on the button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GEt the user's input
                String username = InputUserName.getText().toString().trim();
                String password = InputPassWord.getText().toString().trim();
                //Url of your php file
                String url = "";
                RequestParams params = new RequestParams();
                params.put("username", username);
                params.put("password", password);

                AsyncHttpClient client = new AsyncHttpClient();
                loader.setVisibility(View.VISIBLE);

                client.post(url, params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Toast.makeText(MainActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                        loader.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        loader.setVisibility(View.INVISIBLE);
                        if(responseString.toLowerCase().contains("success")){
                            Toast.makeText(MainActivity.this, "Logged in Succesfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Wrong login credentials, kindly try again", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }
}
