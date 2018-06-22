package com.penserlabs.plabmate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class QuestionActivity extends AppCompatActivity {

    String tablename;
    RadioGroup optionsgroup;
    RadioButton opt_a,opt_b,opt_c,opt_d,opt_e;
    TextView question,quesno;
    Cursor cursor;
    DataBaseHelper myDbHelper;
    CardView questionview;
    LinearLayout questionlayout;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        question= findViewById(R.id.question_TV);
        quesno = findViewById(R.id.qno_TV);
        questionview = findViewById(R.id.questioncard);
        questionlayout = findViewById(R.id.questionlayout);
        optionsgroup = findViewById(R.id.options_RG);
        opt_a = findViewById(R.id.optionA_RB);
        opt_b = findViewById(R.id.optionB_RB);
        opt_c = findViewById(R.id.optionC_RB);
        opt_d = findViewById(R.id.optionD_RB);
        opt_e = findViewById(R.id.optionE_RB);


        Bundle extras = getIntent().getExtras();
        tablename = extras.getString("Tablename");
//        Toast.makeText(QuestionActivity.this,tablename,Toast.LENGTH_SHORT).show();
        myDbHelper = new DataBaseHelper(QuestionActivity.this);


        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }

        cursor = myDbHelper.query("SELECT * FROM "+tablename, null);
        if (cursor!= null) {
          cursor.moveToFirst();
          Integer position = cursor.getInt(cursor.getColumnIndex("Position"));
          cursor.moveToPosition(position);

          setquestion();
        }

        optionsgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton checkedbt = findViewById(checkedId);
                Toast.makeText(QuestionActivity.this,checkedbt.getText(),Toast.LENGTH_SHORT).show();

            }
        });


        questionview.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            @Override
            public void onSwipeLeft() {

                cursor.moveToNext();
                if (cursor!=null && cursor.getPosition()<cursor.getCount()){

                    setquestion();

                }else {
                    Toast.makeText(QuestionActivity.this,"You have reached the last question",Toast.LENGTH_SHORT).show();
                    cursor.moveToPrevious();

                }

            }
            @Override
            public void onSwipeRight() {
                cursor.moveToPrevious();
                if (cursor!=null && cursor.getPosition()>=0){
                    setquestion();

                }
            }
        });

        questionlayout.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            @Override
            public void onSwipeLeft() {

                cursor.moveToNext();
                if (cursor!=null && cursor.getPosition()<cursor.getCount()){

                    setquestion();

                }else {
                    Toast.makeText(QuestionActivity.this,"You have reached the last question",Toast.LENGTH_SHORT).show();
                    cursor.moveToPrevious();

                }

            }
            @Override
            public void onSwipeRight() {
                cursor.moveToPrevious();
                if (cursor!=null && cursor.getPosition()>=0){
                    setquestion();

                }
            }
        });
    }

    private void setquestion() {

            quesno.setText(String.valueOf(cursor.getPosition()+1)+"/"+String.valueOf(cursor.getCount()));
            String questioncoloumn = cursor.getString(cursor.getColumnIndex("Question"));
            String optA = cursor.getString(cursor.getColumnIndex("Opt_A"));
            String optB = cursor.getString(cursor.getColumnIndex("Opt_B"));
            String optC = cursor.getString(cursor.getColumnIndex("Opt_C"));
            String optD = cursor.getString(cursor.getColumnIndex("Opt_D"));
            String optE = cursor.getString(cursor.getColumnIndex("Opt_E"));
            String ans = cursor.getString(cursor.getColumnIndex("Answer"));

            question.setText(questioncoloumn);
            opt_a.setText(optA);
            opt_b.setText(optB);
            opt_c.setText(optC);
            opt_d.setText(optD);
            opt_e.setText(optE);

            Integer position = cursor.getPosition();
            myDbHelper.saveposition("UPDATE "+tablename+" SET (Position) = '"+ position +"' WHERE _id = 1;");

    }


}
