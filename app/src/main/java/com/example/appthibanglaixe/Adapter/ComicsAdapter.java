package com.example.appthibanglaixe.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appthibanglaixe.R;

import com.example.appthibanglaixe.model.Comic;

import java.util.ArrayList;

public class ComicsAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<Comic> comicsList;

    public ComicsAdapter(Activity activity, ArrayList<Comic> comicsList) {
        this.activity = activity;
        this.comicsList = comicsList;
    }

    @Override
    public int getCount() {
        return comicsList.size();
    }

    @Override
    public Object getItem(int position) {
        return comicsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        ImageView comicImage;
        TextView comicTitle;
        TextView comicDescription;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout_cus_bode, null);

            viewHolder = new ViewHolder();
            viewHolder.comicImage = convertView.findViewById(R.id.imageView_comic);
            viewHolder.comicTitle = convertView.findViewById(R.id.textView_title);
            viewHolder.comicDescription = convertView.findViewById(R.id.textView_description);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Comic comic = (Comic) getItem(position);

        viewHolder.comicTitle.setText(comic.getTitle());
        viewHolder.comicDescription.setText(comic.getDescription());

        Glide.with(activity).load(comic.getImageUrl()).into(viewHolder.comicImage);

        return convertView;
    }
}
