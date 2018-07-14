package com.penserlabs.plabmate;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddQuestionActivity extends AppCompatActivity {

    EditText question,options,key,explanation;
    String addques,addoptions,addkey,addexplanation,mailbody,tablename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        Bundle extras = getIntent().getExtras();
        tablename = extras.getString("Tablename");

        question = findViewById(R.id.addquestion_ET);
        options = findViewById(R.id.addoptions_ET);
        key = findViewById(R.id.addkey_ET);
        explanation = findViewById(R.id.addexplanation_ET);

    }

    public void SubmitQuestion(View view) {

        addques = String.valueOf(question.getText());
        addoptions = String.valueOf(options.getText());
        addkey = String.valueOf(key.getText());
        addexplanation = String.valueOf(explanation.getText());

        mailbody = "Category : "+tablename+"\n"+"Question : "+addques+"\n"+"Options : "+addoptions+"\n"+"Answerkey : "+addkey+"\n"+"Explanation : "+addexplanation;

        if (!addques.isEmpty()&& !addoptions.isEmpty() && !addkey.isEmpty()) {

            try

            {
                SendEmailAsyncTask l = new SendEmailAsyncTask();
                l.execute();  //sends the email in background
                Toast.makeText(AddQuestionActivity.this, R.string.Toast_addques_emailsent,Toast.LENGTH_LONG).show();

            } catch (Exception e) {

                Log.e("SendMail", e.getMessage(), e);

            }
        }else{
            Toast.makeText(AddQuestionActivity.this, R.string.Toast_completefields,Toast.LENGTH_SHORT).show();
        }
    }

    class SendEmailAsyncTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                GMailSender sender = new GMailSender("mockplabmate@gmail.com", "plabuk123");

                sender.sendMail("Add Question",
                        mailbody,"mockplabmate@gmail.com",
                        "developer.penserlabs@gmail.com");

            } catch (Exception e) {

                Log.e("error", e.getMessage(), e);

                return "Email Not Sent";

            }
            return "Email Sent";

        }
    }
}
