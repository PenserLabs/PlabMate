package com.penserlabs.plabmate;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import static com.penserlabs.plabmate.R.color.question_background;
import static com.penserlabs.plabmate.R.color.radiobutton_bg;
import static com.penserlabs.plabmate.R.color.radiochecked_bg;
import static com.penserlabs.plabmate.R.color.white;



public class QuestionActivity extends AppCompatActivity {

    String tablename;
    int qno;
    RadioGroup optionsgroup;
    RadioButton opt_a,opt_b,opt_c,opt_d,opt_e;
    TextView question,quesno,remark,explanation;
    Cursor cursor;
    DataBaseHelper myDbHelper;
    CardView questionview,optionsview;
    LinearLayout questionlayout;
    FloatingActionButton submit;
    String answer = "";
    ImageView remarkiv;
    Handler handler = new Handler();
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);


        question= (TextView) findViewById(R.id.question_TV);
        quesno =(TextView) findViewById(R.id.qno_TV);
        questionview = (CardView) findViewById(R.id.questioncard);
        optionsview = (CardView) findViewById(R.id.optionscard);
        questionlayout = (LinearLayout) findViewById(R.id.questionlayout);
        optionsgroup = (RadioGroup) findViewById(R.id.options_RG);
        remark = (TextView) findViewById(R.id.remark_TV);
        explanation = (TextView) findViewById(R.id.explanation_TV);
        opt_a = (RadioButton) findViewById(R.id.optionA_RB);
        opt_b = (RadioButton)findViewById(R.id.optionB_RB);
        opt_c = (RadioButton)findViewById(R.id.optionC_RB);
        opt_d = (RadioButton)findViewById(R.id.optionD_RB);
        opt_e = (RadioButton)findViewById(R.id.optionE_RB);
        submit = (FloatingActionButton) findViewById(R.id.submit_FAB);
        remarkiv = findViewById(R.id.remark_IV);


        Bundle extras = getIntent().getExtras();
        qno = extras.getInt("Qno");
        tablename = extras.getString("Tablename");
        myDbHelper = new DataBaseHelper(QuestionActivity.this);


        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }


    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onResume() {
        super.onResume();
        cursor = myDbHelper.query("SELECT * FROM "+tablename, null);
        if (cursor!= null) {
            cursor.moveToFirst();
            Integer position = qno-1;
            cursor.moveToPosition(position);

            setquestion();
        }

        optionsgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedbt =(RadioButton) findViewById(checkedId);
                ResetView();
                checkedbt.setBackgroundColor(getResources().getColor(radiochecked_bg));
                answer = String.valueOf( checkedbt.getText());
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

            ResetView();
            quesno.setText(String.valueOf(cursor.getPosition()+1)+"/"+String.valueOf(cursor.getCount()));
            String questioncoloumn = cursor.getString(cursor.getColumnIndex("Question"));
            String optA = cursor.getString(cursor.getColumnIndex("Opt_A"));
            String optB = cursor.getString(cursor.getColumnIndex("Opt_B"));
            String optC = cursor.getString(cursor.getColumnIndex("Opt_C"));
            String optD = cursor.getString(cursor.getColumnIndex("Opt_D"));
            String optE = cursor.getString(cursor.getColumnIndex("Opt_E"));

            question.setText(questioncoloumn);
            opt_a.setText(optA);
            opt_b.setText(optB);
            opt_c.setText(optC);
            opt_d.setText(optD);
            opt_e.setText(optE);

        if (cursor.getInt(cursor.getColumnIndex("Flag"))!=0){
           CheckedView();

        }
    }

    private void checkanswer(){
        String ans = cursor.getString(cursor.getColumnIndex("Answer"));
        int answercheck =ans.compareToIgnoreCase(answer);
        if (answercheck == 0){

            String explain = cursor.getString(cursor.getColumnIndex("Explanation"));
            explanation.setVisibility(View.VISIBLE);
            explanation.setText("\nExplanation: \n \n"+explain);
            remark.setVisibility(View.VISIBLE);
            remark.setText(ans);
            submit.setVisibility(View.GONE);
            remarkiv.setImageResource(R.drawable.tick);
            remarkiv.setVisibility(View.VISIBLE);
            optionsgroup.setVisibility(View.GONE);
            questionview.setBackgroundColor(getResources().getColor(R.color.correct_answer));

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                ObjectAnimator colorfade = ObjectAnimator.ofObject(questionview, "backgroundColor" /*view attribute name*/, new ArgbEvaluator(),
                        getApplicationContext().getResources().getColor(R.color.correct_answer) /*from color*/, Color.WHITE);
                colorfade.setDuration(2000);
                colorfade.setStartDelay(200);
                colorfade.start();
            }
            Integer position = cursor.getPosition();
            myDbHelper.saveflag("UPDATE "+tablename+" SET Flag = ? WHERE _id ="+String.valueOf(position+1),new String[]{"1"});
        }
        else {
            String explain = cursor.getString(cursor.getColumnIndex("Explanation"));
            explanation.setVisibility(View.VISIBLE);
            explanation.setText("\nExplanation: \n \n"+explain);

            remark.setVisibility(View.VISIBLE);
            remark.setText(ans);
            submit.setVisibility(View.GONE);
            remarkiv.setImageResource(R.drawable.wrong);
            remarkiv.setVisibility(View.VISIBLE);
            optionsgroup.setVisibility(View.GONE);
            questionview.setBackgroundColor(getResources().getColor(R.color.wrong_answer));

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                ObjectAnimator colorfade = ObjectAnimator.ofObject(questionview, "backgroundColor" /*view attribute name*/, new ArgbEvaluator(),
                        getApplicationContext().getResources().getColor(R.color.wrong_answer) /*from color*/, Color.WHITE);

                colorfade.setDuration(2000);
                colorfade.setStartDelay(200);
                colorfade.start();
            }
            Integer position = cursor.getPosition();
            myDbHelper.saveflag("UPDATE "+tablename+" SET Flag = ? WHERE _id ="+String.valueOf(position+1),new String[]{"2"});
        }
    }

    public void ResetView(){
        remark.setVisibility(View.GONE);
        explanation.setVisibility(View.GONE);
        remarkiv.setVisibility(View.GONE);
        submit.setVisibility(View.VISIBLE);
        optionsgroup.setVisibility(View.VISIBLE);
        questionview.setBackgroundColor(getResources().getColor(white));
        questionlayout.setBackground(getResources().getDrawable(R.drawable.background));
        opt_a.setBackgroundColor(getResources().getColor(radiobutton_bg));
        opt_b.setBackgroundColor(getResources().getColor(radiobutton_bg));
        opt_c.setBackgroundColor(getResources().getColor(radiobutton_bg));
        opt_d.setBackgroundColor(getResources().getColor(radiobutton_bg));
        opt_e.setBackgroundColor(getResources().getColor(radiobutton_bg));

    }

    public void CheckedView(){
        String ans = cursor.getString(cursor.getColumnIndex("Answer"));
        String explain = cursor.getString(cursor.getColumnIndex("Explanation"));
        explanation.setVisibility(View.VISIBLE);
        explanation.setText("\nExplanation: \n \n"+explain);
        remark.setVisibility(View.VISIBLE);
        remark.setText(ans);
        submit.setVisibility(View.GONE);
        opt_a.setClickable(false);
        opt_b.setClickable(false);
        opt_c.setClickable(false);
        opt_d.setClickable(false);
        opt_e.setClickable(false);

    }

    public void SubmitAnswer(View view) {
        checkanswer();
    }
}
