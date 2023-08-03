package com.duhanaktan.catchferb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
    }
    //oyunu main activity e geçrek başlatır
    public void Start(View view){
        Intent intent=new Intent(MainScreenActivity.this,MainActivity.class);
        startActivity(intent);
    }
    //tüm uygulamaları kapatır
    public void Close(View view){
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }
}