package com.example.assignment5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class WatchlistBaseAdapter extends BaseAdapter {

    Context context;
    List<Pokemon> watchlist;
    LayoutInflater inflater;

    public WatchlistBaseAdapter(Context context, List<Pokemon> watchlist) {
        this.context = context;
        this.watchlist = watchlist;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return watchlist.size();
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
        convertView = inflater.inflate(R.layout.activity_watchlist_list_view, null);
        TextView watchlistNameTV = (TextView) convertView.findViewById(R.id.watchlistNameTV);
        watchlistNameTV.setText(watchlist.get(position).getName());
        TextView watchlistIDTV = (TextView) convertView.findViewById(R.id.watchlistIDTV);
        watchlistIDTV.setText("#"+String.valueOf(watchlist.get(position).getId()));
        return convertView;
    }

    public void updateAdapter(List<Pokemon> watchlist) {
        this.watchlist = watchlist;
        notifyDataSetChanged();
    }
}

