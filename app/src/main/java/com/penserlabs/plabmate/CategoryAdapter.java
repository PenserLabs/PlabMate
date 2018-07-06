package com.penserlabs.plabmate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryAdapter extends BaseAdapter {

    private Context mContext;
    String[] item = null;
    int[] imageids=null;

    public CategoryAdapter(@NonNull Context context, String[] values, int[] imageid) {
        mContext = context;
        this.item = values;
        this.imageids=imageid;


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

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_layout, null);


        }
        else {
            grid = (View) convertView;
        }

        TextView textView = (TextView) grid.findViewById(R.id.textview2);
        textView.setText(item[position]);
        ImageView imageView = (ImageView)grid.findViewById(R.id.category_IV);
        imageView.setImageResource(imageids[position]);

        return grid;
    }
}
