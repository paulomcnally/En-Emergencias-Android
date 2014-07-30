package com.mc.emergency;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by paulomcnally on 4/12/14.
 */
public class CustomGridAdapter extends BaseAdapter {

    private Context context;
    private String[] texts;
    private String[] images;
    LayoutInflater inflater;

    public CustomGridAdapter(Context context, String[] texts, String[] images) {
        this.context = context;
        this.texts = texts;
        this.images = images;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_item, null);
        }

        TextView text = (TextView) convertView.findViewById( R.id.grid_item_text );
        text.setText(texts[position]);

        int identifier = context.getResources().getIdentifier(images[position], "drawable", context.getPackageName());

        ImageView image = (ImageView) convertView.findViewById( R.id.grid_item_image );
        image.setBackgroundResource(identifier);


        //convertView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, rowHigh));

        //Button button = (Button) convertView.findViewById(R.id.grid_item);
        //button.setText(items[position]);

        return convertView;
    }

    @Override
    public int getCount() {
        return texts.length;
    }

    @Override
    public Object getItem(int position) {
        return texts[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}