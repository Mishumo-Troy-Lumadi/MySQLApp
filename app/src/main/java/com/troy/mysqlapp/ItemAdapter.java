package com.troy.mysqlapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.troy.mysqlapp.Fruit;

import java.text.NumberFormat;
import java.util.ArrayList;

public class ItemAdapter extends BaseAdapter {

    LayoutInflater mInflater;

   ArrayList<Fruit> fruits;

    public ItemAdapter(Context c, ArrayList<Fruit> f ){
        fruits = f;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return fruits.size();
    }

    @Override
    public Object getItem(int position) {
        return fruits.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = mInflater.inflate(R.layout.list_detail,null);

        TextView nameTextView = v.findViewById(R.id.nameTextView);
        TextView priceTextView = v.findViewById(R.id.priceTextView);

        Fruit obj = fruits.get(position);

        String name = obj.getName();
        double cost = obj.getPrice();

        nameTextView.setText(name);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(cost));

        return v;

    }
}
