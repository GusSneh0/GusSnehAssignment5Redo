package com.example.assignment5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ProfileBaseAdapter extends BaseAdapter {

    Context context;
    List<String> textList;
    LayoutInflater inflater;

    public ProfileBaseAdapter(Context context, List<String> textList) {
        this.context = context;
        this.textList = textList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return textList.size();
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
        convertView = inflater.inflate(R.layout.activity_profile_list_view, null);
        TextView profileTV = (TextView) convertView.findViewById(R.id.profileTmageView);
        profileTV.setText(textList.get(position));
        return convertView;
    }

    public void updateAdapter(List<String> textList) {
        this.textList = textList;
        notifyDataSetChanged();
    }
}
