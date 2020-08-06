package com.example.bringappfinal;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateListActivity extends AppCompatActivity implements AdditemDialog.AdditemDialogListener {
    //getting intent from MainActivity.java
    Intent onclickintent;
    Item item=new Item();
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase db;
    int list_id;
    int listid;
    Activity activity;
    ListView listview;
    ArrayList<Item> itementries=new ArrayList<Item>();
    ArrayList<Item> itemprices=new ArrayList<Item>();
    int countprice;
    int budget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);
        onclickintent=getIntent();
        listview=(ListView) findViewById(R.id.listview);



        //for getting checkitems from mainlist
        /*for (int i = 0; i < MyCategoriesExpandableListAdapter.parentItems.size(); i++ ){

            String isChecked = MyCategoriesExpandableListAdapter.parentItems.get(i).get(ConstantManager.Parameter.IS_CHECKED);

            if (isChecked.equalsIgnoreCase(ConstantManager.CHECK_BOX_CHECKED_TRUE))
            {
                tvParent.setText(tvParent.getText() + MyCategoriesExpandableListAdapter.parentItems.get(i).get(ConstantManager.Parameter.CATEGORY_NAME));
            }

            for (int j = 0; j < MyCategoriesExpandableListAdapter.childItems.get(i).size(); j++ ){

                String isChildChecked = MyCategoriesExpandableListAdapter.childItems.get(i).get(j).get(ConstantManager.Parameter.IS_CHECKED);

                if (isChildChecked.equalsIgnoreCase(ConstantManager.CHECK_BOX_CHECKED_TRUE))
                {
                    tvChild.setText(tvChild.getText() +" , " + MyCategoriesExpandableListAdapter.parentItems.get(i).get(ConstantManager.Parameter.CATEGORY_NAME) + " "+(j+1));
                }

            }

        }
    }*/
        /////////////////////////
        activity=this;
        getItemEntries();
        if(!itementries.isEmpty())
        {
            new showdata().execute();
        }
        for(int i=0;i<itemprices.size();i++)
        {
            String price=itemprices.get(i).getItemprice();
            countprice+=Integer.parseInt(price);
        }
        TextView textView=(TextView) findViewById(R.id.text_view_total_price);
        textView.setText(""+countprice);
        if(countprice>budget)
        {
            Toast.makeText(this,"Price Exceeds your budget",Toast.LENGTH_SHORT).show();
        }


    }
    public void opendialog(View view){
        AdditemDialog additemDialog=new AdditemDialog();
        additemDialog.show(getSupportFragmentManager(),"Add item dialog");
    }
    public void gotomainlist(View view)
    {
        Intent intentmain=new Intent(this,MainListActivity.class);
        startActivity(intentmain);
    }

    @Override
    public void applyTexts(String itemname, String itemprice, String itemquantity) {
        try {
            item.setItemname(itemname);
            item.setItemlistid(listid);
            item.setItemprice(itemprice);
            item.setItemquantity(itemquantity);
            boolean flag = databaseHelper.additem(item);

            if (flag) {
                Toast.makeText(this, "Item is added successfully", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());
            } else {
                Toast.makeText(this, "Failed to add item", Toast.LENGTH_SHORT).show();
            }
        }catch (SQLException e)
        {
            Log.d("TAG",e.getMessage());
        }

    }
    class showdata extends AsyncTask {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            CustomItemlist customItemlist=new CustomItemlist(activity,itementries);
            listview.setAdapter(customItemlist);
            registerForContextMenu(listview);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                }
            });

        }

        @Override
        protected Object doInBackground(Object[] objects) {
            return null;
        }
    }
    protected Void getItemEntries()
    {
        listid=onclickintent.getIntExtra("listid",0);
        //String listid=DatabaseContract.useritem.USER_LIST_ITEM_ID;
        itementries=databaseHelper.getItementries(listid);
        itemprices=databaseHelper.getItemprices(listid);
        budget=databaseHelper.getbudget(listid);
        return null;
    }
}