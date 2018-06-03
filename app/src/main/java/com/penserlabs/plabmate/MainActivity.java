package com.penserlabs.plabmate;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase categorydb=null;
    Button plab1BT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        plab1BT = findViewById(R.id.plab1_BT);
    }

    public void gotocategory(View view) {

        try {
            categorydb=this.openOrCreateDatabase("CategoriesDB",MODE_PRIVATE,null);

            File database= getApplicationContext().getDatabasePath("CAtegoriesDB");

            if (!database.exists()){
                Toast.makeText(MainActivity.this,"DB FOUND",Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            Log.e("DB no found","ERROR");
        }

    }
}
