package com.example.bringappfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bringappfinal.DatabaseHelper;
import com.example.bringappfinal.List;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//Homepgae class that contains buttons..//
public class Homepage extends AppCompatActivity implements AddListDialog.AddlistDialogListener {
    Button createbutton;
    EditText listedit,listbudget;
    TextView datelist;
    List list;//Object of user list class
    DatabaseHelper databaseHelper;
    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase db;
    String    date= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        createbutton=(Button) findViewById(R.id.createlistbutton);
        databaseHelper = new DatabaseHelper(this);
        listedit=(EditText)  findViewById(R.id.listnameedit);
        listbudget=(EditText)  findViewById(R.id.listbudget);
        datelist=(TextView) findViewById(R.id.dateoflist);

        list=new List();
    }
    //function to open dialog box on click
    public void Gotocreate(View view)
    {
        AddListDialog addlistDialog=new AddListDialog();
        addlistDialog.show(getSupportFragmentManager(),"List Name Dialog");

    }
    public void Viewlist(View view)
    {
        Intent viewlistintent=new Intent(this,ViewListClass.class);
        startActivity(viewlistintent);
    }

    @Override
    public void applyTexts(String listname,String budget,String Date) {


        list.setListname(listname);
        list.setListdate(date);
        list.setBudget(budget);

        boolean flag=databaseHelper.addlist(list);
        if(flag)
        {
            Toast.makeText(this,"List is created successfully",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this,CreateListActivity.class);
            try {
                int id = databaseHelper.getlistid();
                intent.putExtra("listid", id);
                startActivity(intent);
            }catch(SQLException e)
            {
                Log.d("TAG",e.getMessage());
            }

        }
        else{
            Toast.makeText(this,"Failed to create list",Toast.LENGTH_SHORT).show();
        }
    }

}