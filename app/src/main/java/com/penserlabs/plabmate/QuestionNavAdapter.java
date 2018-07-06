package com.penserlabs.plabmate;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import static com.penserlabs.plabmate.R.drawable.background;

public class QuestionNavAdapter extends BaseAdapter {

    private Context mContext;
    Integer[] qno;
    Integer[] flag;

    public QuestionNavAdapter(@NonNull Context context, Integer[] quesno, String tablename,Integer[] flags) {
        mContext = context;
        this.qno=quesno;
        this.flag= flags;



    }

    @Override
    public int getCount() {
        return qno.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        Integer i1=1, i2=2;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            view = inflater.inflate(R.layout.question_no_layout, null);

        }
        else {
            view = (View) convertView;

        }
        CardView cardView = view.findViewById(R.id.quesnav_CV);
        TextView textView = (TextView) view.findViewById(R.id.qnonav_TV);
        textView.setText(String.valueOf(qno[position] + 1));


        if (flag[position] == 1) {
            cardView.setBackgroundColor(mContext.getResources().getColor(R.color.correct_answer));

        }
        else if (flag[position] == 2) {
            cardView.setBackgroundColor(mContext.getResources().getColor(R.color.wrong_answer));
        }
        else if(flag[position] == 0){
            cardView.setBackgroundColor(mContext.getResources().getColor(R.color.white));


        }



        return view;
    }
}
