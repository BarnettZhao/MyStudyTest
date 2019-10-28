package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.okHttp.HttpCallback;
import com.example.myapplication.okHttp.OkHttpHelper;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loadPicture = findViewById(R.id.main_load_picture);
        loadPicture.setOnClickListener(this);
        Button netRequest = findViewById(R.id.main_net_request);
        netRequest.setOnClickListener(this);
        Button inputPassword = findViewById(R.id.main_password_input);
        inputPassword.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_password_input:
                startActivity(new Intent(MainActivity.this, InputPasswordActivity.class));
                break;
            case R.id.main_load_picture:
                Intent loadPictureIntent = new Intent(MainActivity.this, LoadPictureActivity.class);
                startActivity(loadPictureIntent);
                break;
            case R.id.main_net_request:
                OkHttpHelper.getInstance().httpGet("https://www.runoob.com/?s=toast", new HttpCallback() {
                    @Override
                    public void onRequestBefore(Request request) {
                        //请求之前，可设置加载dialog
                    }

                    @Override
                    public void onFailure(Request request, IOException e) {
                        //请求失败
                    }

                    @Override
                    public void onSuccess(Response response) {
                        //请求成功，数据解析。。。
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Response response, int errorCode, Exception e) {
                        //请求成功，数据返回失败
                    }
                });
                break;
            default:
                break;
        }
    }


}
