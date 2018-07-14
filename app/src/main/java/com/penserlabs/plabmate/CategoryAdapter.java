package com.penserlabs.plabmate;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.Fade;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class CategoryAdapter extends BaseAdapter {

    private Context mContext;
    String[] item = null;
    int[] imageids=null;
    DataBaseHelper myDbHelper;
    Cursor cursor;

    public CategoryAdapter(@NonNull Context context, String[] values, int[] imageid) {
        mContext = context;
        this.item = values;
        this.imageids=imageid;

        myDbHelper = new DataBaseHelper(mContext);

        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }

    }

    @Override
    public int getCount() {
        return item.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class MyHolder {
        TextView textView,progressTV;
        ImageView imageView;
        ProgressBar progressBar;
        public MyHolder(View v) {
            textView = (TextView) v.findViewById(R.id.textview2);
            imageView = v.findViewById(R.id.category_IV);
            progressTV = v.findViewById(R.id.progress_TV);
            progressBar=v.findViewById(R.id.progress_PB);
        }

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
//        LayoutInflater inflater = (LayoutInflater) mContext
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final MyHolder holder;

        cursor = myDbHelper.query("SELECT * FROM "+item[position], null);
        cursor.getCount();
        cursor.moveToFirst();
        Integer Count=0;
        Integer progress;

        for (int i =0;i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            if(cursor.getInt(cursor.getColumnIndex("Flag")) !=0){
                Count=Count+1;
            }
        }

        progress = Count*100/cursor.getCount();

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.grid_layout, parent, false);
            holder = new MyHolder(view);
            view.setTag(holder);

        }
        else {
            holder = (MyHolder) view.getTag();

        }
        holder.textView.setText(String.valueOf(item[position]));
        holder.imageView.setImageResource(imageids[position]);
        holder.progressTV.setText(String.valueOf(progress)+"%");
        holder.progressBar.setProgress(progress);

        return view;
    }
}
