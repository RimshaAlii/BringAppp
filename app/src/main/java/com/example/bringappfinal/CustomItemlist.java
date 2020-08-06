package com.example.bringappfinal;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
public class CustomItemlist extends BaseAdapter {
    private Activity context;
    ArrayList<Item> listitem;
    public CustomItemlist(Activity context, ArrayList listitem) {
        // super(context, R.layout.row_item, countries);
        this.context = context;
        this.listitem=listitem;
    }
    public static class ViewHolder
    {
        CheckBox checkBox;
        TextView textViewname;
        TextView textViewprice;
        TextView textViewquantity;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        ViewHolder vh;
        if(convertView==null) {
            vh=new ViewHolder();
            row = inflater.inflate(R.layout.baserow_item, null, true);
            vh.checkBox=(CheckBox) row.findViewById(R.id.checkbox) ;
            vh.textViewname = (TextView) row.findViewById(R.id.textViewname);
            vh.textViewprice = (TextView) row.findViewById(R.id.textViewprice);
            vh.textViewquantity= (TextView) row.findViewById(R.id.textViewquantity);
            // store the holder with the view.
            row.setTag(vh);
        }
        else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.textViewname.setText(listitem.get(position).getItemname());
        vh.textViewprice.setText(""+listitem.get(position).getItemprice());
        vh.textViewquantity.setText(""+listitem.get(position).getItemquantity());
        return  row;
    }
    public long getItemId(int position) {
        return position;
    }
    public Object getItem(int position) {
        return position;
    }
    public int getCount() {

        if (listitem.size() <= 0)
            return 1;
        return listitem.size();
    }
}




