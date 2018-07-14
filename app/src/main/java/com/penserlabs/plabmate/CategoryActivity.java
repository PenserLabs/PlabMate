package com.penserlabs.plabmate;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.nostra13.universalimageloader.utils.L;

import java.io.File;

import static android.widget.Toast.LENGTH_SHORT;

public class CategoryActivity extends AppCompatActivity {

    GridView categoryview;
    DataBaseHelper myDbHelper;
    final int[] imageid = {R.drawable.anatomy,R.drawable.cardiology,R.drawable.dermatology,R.drawable.emergency,R.drawable.endocrinology,R.drawable.ent,R.drawable.epidemiology,R.drawable.ethics,R.drawable.gastroentology,R.drawable.genetics,R.drawable.hematology,R.drawable.infectious,R.drawable.nephrology,R.drawable.neurology,R.drawable.obg,R.drawable.ophthalmology,R.drawable.orthopedics,R.drawable.paediatrics,R.drawable.pharmacology,R.drawable.psychiatry,R.drawable.respiratory,R.drawable.rheumatology,R.drawable.surgery,R.drawable.urology,R.drawable.vascular};
    final String[] categories= {"Anatomy", "Cardiology", "Dermatology", "Emergency", "Endocrinology", "ENT", "Epidemiology", "Ethics", "Gastroentology", "Genetics", "Hematology", "Infectious_Diseases", "Nephrology", "Neurology", "OBG", "Ophthalmology", "Orthopedics", "Paediatrics", "Pharmacology", "Psychiatry", "Respiratory", "Rheumatology", "Surgery", "Urology", "Vascular"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        categoryview = findViewById(R.id.category_GV);
        myDbHelper = new DataBaseHelper(CategoryActivity.this);
        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        CategoryAdapter categoryAdapter = new CategoryAdapter(this,categories,imageid);
        categoryview.setAdapter(categoryAdapter);

        categoryview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent questionintent = new Intent(CategoryActivity.this,QuestionNav.class);
                questionintent.putExtra("Tablename",categories[+position]);
                startActivity(questionintent);
            }
        });

    }
}
