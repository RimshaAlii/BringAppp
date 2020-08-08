package com.example.bringappfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ViewListClass extends AppCompatActivity implements AddListDialog.AddlistDialogListener {
    ArrayList<List> getlistarray = new ArrayList<>();
    //  ArrayList<String> listnames=new ArrayList<>();
    SQLiteDatabase sqLiteDatabase;

    Intent showitems;
    Activity activity;
    AlertDialog.Builder builder;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    SwipeMenuListView  listview;
    ArrayList<List> listentries=new ArrayList<List>();
    List list=new List();
    String id;
    EditText listedit,listbudget;
    TextView datelist;
    String    date= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list_cla);
        listedit=(EditText)  findViewById(R.id.listnameedit);
        listbudget=(EditText)  findViewById(R.id.listbudget);
        datelist=(TextView) findViewById(R.id.dateoflist);

        showitems = new Intent(this, CreateListActivity.class);
        getListdetail();
        activity=this;


      /*  for(int i=0;i<getlistarray.size();i++)
        {
            listnames.add(getlistarray.get(i).getListname());
            listnames.add(getlistarray.get(i).getListdate());
        }*/
        if(!listentries.isEmpty())
        {
            new showlists().execute();
        }

        listview = (SwipeMenuListView) findViewById(R.id.alllistview);
    }

    private Void getListdetail() {
        listentries=databaseHelper.getlistnames();
        return null;
    }




    class showlists extends AsyncTask {
        public showlists() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            CustomAllListClass customAllListClass=new CustomAllListClass(activity,listentries);
            listview.setAdapter(customAllListClass);
            listview.setMenuCreator(creator);
            //on item swipe
            listview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                    id=String.valueOf(listentries.get(position).getListid());
                 //   email=weightEntries.get(position).getEmail().trim();
                    switch (index) {
                        case 0:
                            AddListDialog addlistDialog=new AddListDialog();
                            addlistDialog.show(getSupportFragmentManager(),"List Name Dialog");


                            break;
                        case 1:
                            builder=new AlertDialog.Builder(ViewListClass.this);
                            builder.setMessage("Are you sure you want to delete this List?");
                            builder.setCancelable(false);
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                   boolean flag=databaseHelper.deleteListRecord(id);
                                    if(flag)
                                    {
                                        Toast.makeText(getApplicationContext(), "List Deleted ", Toast.LENGTH_SHORT).show();
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
            registerForContextMenu(listview);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    int listid = listentries.get(i).getListid();
                    String listname=listentries.get(i).getListname();
                    showitems.putExtra("listid", listid);
                    startActivity(showitems);
                }
            });
            listview.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    return false;
                }
            });
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            return null;
        }
    }
    @Override
    public void applyTexts(String listname, String budget, String date) {
        list.setListname(listname);
        list.setListdate(date);
        list.setBudget(budget);
        boolean flag1=databaseHelper.updatelist(id,list);
        if(flag1)
        {
            Toast.makeText(this,"List Updated",Toast.LENGTH_SHORT).show();
            finish();
            startActivity(getIntent());
        }
        else{
            Toast.makeText(this,"List Not updated",Toast.LENGTH_SHORT).show();
        }
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





