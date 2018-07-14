package com.penserlabs.plabmate;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionNav extends AppCompatActivity {

    String tablename;
    Cursor cursor;
    DataBaseHelper myDbHelper;
    TextView qnonav;
    LinearLayout questionlayout;
    GridView qnonavview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_nav);

        qnonavview = findViewById(R.id.quesnav_GV);
        qnonav = findViewById(R.id.qnonav_TV);

        Bundle extras = getIntent().getExtras();
        tablename = extras.getString("Tablename");
        myDbHelper = new DataBaseHelper(QuestionNav.this);
        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }



    }

    @Override
    protected void onResume() {
        super.onResume();


        cursor = myDbHelper.query("SELECT * FROM "+tablename, null);
        cursor.getCount();
        cursor.moveToFirst();
        Integer flag[]= new Integer[cursor.getCount()];

        for (int i =0;i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            flag[i]=cursor.getInt(cursor.getColumnIndex("Flag"));
        }


        final Integer[] qno = new Integer[cursor.getCount()];

        for (int i =0;i<cursor.getCount();i++){
            qno[i] = i;
        }
        QuestionNavAdapter questionNavAdapter = new QuestionNavAdapter(this,qno,tablename,flag);
        qnonavview.setAdapter(questionNavAdapter);

        qnonavview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent questionintent = new Intent(QuestionNav.this,QuestionActivity.class);
                questionintent.putExtra("Qno",qno[position]+1);
                questionintent.putExtra("Tablename",tablename);
                startActivity(questionintent);

            }
        });

    }

    public void Addquestion(View view) {
        Intent addques = new Intent(QuestionNav.this,AddQuestionActivity.class);
        addques.putExtra("Tablename",tablename);
        startActivity(addques);
    }
}
