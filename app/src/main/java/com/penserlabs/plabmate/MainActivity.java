package com.penserlabs.plabmate;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    Button plab1BT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        plab1BT = findViewById(R.id.plab1_BT);

        DataBaseHelper myDbHelper = new DataBaseHelper(MainActivity.this);
        try {
            myDbHelper.createDataBase();
            Toast.makeText(MainActivity.this, "Database successfully Imported", Toast.LENGTH_SHORT).show();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }


    }

    public void gotocategory(View view) {

        Intent categoryintent =  new Intent(MainActivity.this,CategoryActivity.class);
        startActivity(categoryintent);

    }
}
