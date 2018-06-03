package com.penserlabs.plabmate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Category extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        String[] categories= {"Anatomy", "Cardiology", "Dermatology", "Emergency", "Endocrinology", "ENT", "Epidemiology", "Ethics", "Gastroentology", "Genetics", "Hematology", "Infectious Diseases", "Nephrology", "Neurology", "OBG", "Ophthalmology", "Orthopedics", "Paediatrics", "Pharmacology", "Psychiatry", "Respiratory", "Rheumatology", "Surgery", "Urology", "Vascular"};

        final ListAdapter categoryAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_activated_1,categories);

        ListView categotyListview = findViewById(R.id.Category_LV);

        categotyListview.setAdapter(categoryAdapter);

        categotyListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(Category.this,"you clicked "+ String.valueOf(parent.getItemAtPosition(position)), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
