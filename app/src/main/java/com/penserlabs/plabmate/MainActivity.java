package com.penserlabs.plabmate;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    TextView plab1TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        plab1TV = (TextView) findViewById(R.id.plab_TV);

        DataBaseHelper myDbHelper = new DataBaseHelper(MainActivity.this);
        try {
            myDbHelper.createDataBase();
//            Toast.makeText(MainActivity.this, "Database successfully Imported", Toast.LENGTH_SHORT).show();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }


    }

    public void gotocategory(View view) {

        Intent categoryintent =  new Intent(MainActivity.this,CategoryActivity.class);
        startActivity(categoryintent);

    }

    public void gotofb(View view) {
        final String url = getString(R.string.Fb_URL);
        try{
            // open in Facebook app
            getApplicationContext().getPackageManager().getPackageInfo("com.facebook.katana", 0);
            Intent fbintent= new  Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href="+url ));
            startActivity(fbintent);

        } catch (Exception e) {
            // open in browser
            Intent fbintent=  new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(fbintent);
        }
    }

    public void gotoyt(View view) {
        String url =getString(R.string.Youtube_URL);
        try{
            Intent intent =new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse(url));
            startActivity(intent);

        } catch (Exception e) {
            // open in browser
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(webIntent);
        }
    }

    public void gotopenserlabs(View view) {
        String url = getString(R.string.penserlabs_url);
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
        startActivity(intent);
    }

    public void gotoMocktest(View view) {
        Toast.makeText(MainActivity.this,"Will be available soon",Toast.LENGTH_SHORT).show();
    }

    public void gotoTerms(View view) {
        Intent intent = new Intent(MainActivity.this,TermsActivity.class);
        startActivity(intent);
    }
}
