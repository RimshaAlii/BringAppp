package com.example.bringappfinal;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

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
    SwipeMenuListView listview;
    AlertDialog.Builder builder;
    ArrayList<Item> itementries=new ArrayList<Item>();
    ArrayList<Item> itemprices=new ArrayList<Item>();
    int countprice;
    int budget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);
        onclickintent=getIntent();
        listview=(SwipeMenuListView) findViewById(R.id.listview);



        //for getting checkitems from mainlist
        /*for (int i = 0; i < MyCategoriesExpandableListAdapter.parentItems.size(); i++ ){

            String isChecked = MyCategoriesExpandableListAdapter.parentItems.get(i).get(ConstantManager.Parameter.IS_CHECKED);

            if (isChecked.equalsIgnoreCase(ConstantManager.CHECK_BOX_CHECKED_TRUE))
            {
                String parent= MyCategoriesExpandableListAdapter.parentItems.get(i).get(ConstantManager.Parameter.CATEGORY_NAME);
            }

            for (int j = 0; j < MyCategoriesExpandableListAdapter.childItems.get(i).size(); j++ ){

                String isChildChecked = MyCategoriesExpandableListAdapter.childItems.get(i).get(j).get(ConstantManager.Parameter.IS_CHECKED);

                if (isChildChecked.equalsIgnoreCase(ConstantManager.CHECK_BOX_CHECKED_TRUE))
                {
                    String childs=null;
                    childs=childs +" , " + MyCategoriesExpandableListAdapter.parentItems.get(i).get(ConstantManager.Parameter.CATEGORY_NAME) + " "+(j+1);
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
            Toast.makeText(this,"Price Exceeds your budget",Toast.LENGTH_LONG).show();
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

    @Override
    public void applyTexts(String id, String itemname, String itemprice, String itemquantity) {
        Item item=new Item();
        item.setItemid(Integer.parseInt(id));
        item.setItemname(itemname);
        item.setItemprice(itemprice);
        item.setItemquantity(itemquantity);
        boolean flag2=databaseHelper.updateitem(item);
        if (flag2) {
            Toast.makeText(this, "Item is updated successfully", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(getIntent());
        } else {
            Toast.makeText(this, "Failed to updated item", Toast.LENGTH_SHORT).show();
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
            listview.setMenuCreator(creator);
            registerForContextMenu(listview);
            listview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                    final String id=String.valueOf(itementries.get(position).getItemid());
                    final String itemname=String.valueOf(itementries.get(position).getItemname());
                    final String itemprice=String.valueOf(itementries.get(position).getItemprice());
                    final String itemquantity=String.valueOf(itementries.get(position).getItemquantity());
                    switch(index)
                    {
                        case 0:
                            AdditemDialog additemDialog=new AdditemDialog(id,itemname,itemprice,itemquantity);
                            additemDialog.show(getSupportFragmentManager(),"Add item dialog");

                            break;
                        case 1:
                            builder=new AlertDialog.Builder(CreateListActivity.this);
                            builder.setMessage("Are you sure you want to delete this item?");
                            builder.setCancelable(false);
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    boolean flag=databaseHelper.deleteitem(id);
                                    if(flag)
                                    {
                                        Toast.makeText(getApplicationContext(), "Item Deleted ", Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(getIntent());
                                    }
                                }
                            });
                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            AlertDialog alertDialog=builder.create();
                            alertDialog.show();
                            break;
                    }
                    return false;
                }
            });
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
    //Swipemenu options
    SwipeMenuCreator creator = new SwipeMenuCreator() {

        @Override
        public void create(SwipeMenu menu) {
            // create "open" item
           SwipeMenuItem openItem = new SwipeMenuItem(
                    getApplicationContext());
            openItem.setBackground(new ColorDrawable(Color.rgb(0x3a, 0xf4,
                   0x2a)));
            openItem.setWidth(170);
           openItem.setTitle("Edit");
            openItem.setTitleSize(18);
            openItem.setTitleColor(Color.WHITE);
            // add to menu
            menu.addMenuItem(openItem);
            SwipeMenuItem deleteItem = new SwipeMenuItem(
                    getApplicationContext());
            // set item background
            deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFe,
                    0x00, 0x00)));

            deleteItem.setWidth(170);
            deleteItem.setTitle("Delete");
            deleteItem.setTitleSize(18);
            deleteItem.setTitleColor(Color.WHITE);
            // add to menu
            menu.addMenuItem(deleteItem);
        }
    };
}