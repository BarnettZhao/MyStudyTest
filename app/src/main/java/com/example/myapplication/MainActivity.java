package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loadPicture = findViewById(R.id.main_load_picture);
        loadPicture.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_load_picture:
                Intent loadPictureIntent = new Intent(MainActivity.this, LoadPictureActivity.class);
                startActivity(loadPictureIntent);
                break;
            default:
                break;
        }
    }


}
