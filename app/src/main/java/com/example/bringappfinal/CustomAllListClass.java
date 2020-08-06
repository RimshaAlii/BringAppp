package com.example.bringappfinal;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAllListClass extends BaseAdapter {
    private Activity context;
    ArrayList<List> alllists;
    public CustomAllListClass(Activity context, ArrayList alllists) {
        // super(context, R.layout.all, countries);
        this.context = context;
        this.alllists=alllists;
    }
    public static class ViewHolder
    {

        TextView textViewlistname;
        TextView textViewlistdate;

    }
    @Override
    public int getCount() {
        if (alllists.size() <= 0)
            return 1;
        return alllists.size();

    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;

        LayoutInflater inflater = context.getLayoutInflater();
        ViewHolder vh;
        if(convertView==null) {
            vh=new ViewHolder();
            row = inflater.inflate(R.layout.all_list_row_base, null, true);
            vh.textViewlistname = (TextView) row.findViewById(R.id.textViewlistname);
            vh.textViewlistdate = (TextView) row.findViewById(R.id.textViewlistdate);
            // store the holder with the view.
            row.setTag(vh);
        }
        else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.textViewlistname.setText(alllists.get(position).getListname());
        vh.textViewlistdate.setText(""+alllists.get(position).getListdate());

        return  row;
    }
}
