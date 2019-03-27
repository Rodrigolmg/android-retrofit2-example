package com.fatesg.senaigo.retrofit2.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.fatesg.senaigo.retrofit2.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openActAdd(View view) {
        Intent actAdd = new Intent(MainActivity.this, AddUserActivity.class);
        startActivity(actAdd);
    }

    public void openACtShow(View view) {
        Intent actShow = new Intent(MainActivity.this, ShowUserActivity.class);
        startActivity(actShow);
    }
}
