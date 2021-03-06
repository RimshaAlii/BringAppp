package com.example.bringappfinal;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHelper  extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Grocery.db";
    //Table for storing userlists name
    private static final String CREATE_TBL__USER_LIST = "CREATE TABLE "
            + DatabaseContract.userlist.TABLE_NAME + " ("
            + DatabaseContract.userlist.USER_LISTID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DatabaseContract.userlist.USER_LISTNAME + " TEXT NOT NULL, "
            + DatabaseContract.userlist.USER_LISTDATE + " TEXT, "
            + DatabaseContract.userlist.USER_BUDGET + " TEXT NOT NULL) ";
    //Table for storing user list items details
    private static final String CREATE_TBL_USER_ITEM = "CREATE TABLE "
            + DatabaseContract.useritem.TABLE_NAME + " ("
            + DatabaseContract.useritem.COL_ITEMID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DatabaseContract.useritem.COL_ITEMNAME + " TEXT NOT NULL,"
            + DatabaseContract.useritem.USER_LIST_ITEM_ID + " INTEGER,"
            + DatabaseContract.useritem.COL_ITEMPRICE + " TEXT ,"
            + DatabaseContract.useritem.COL_ITEMQUANTITY + " TEXT )";

    //Table for storing mainlist categories
    private static final String CREATE_TBL_MAIN_LIST = "CREATE TABLE "
            + DatabaseContract.mainlist.TABLE_NAME + " ("
            + DatabaseContract.mainlist.CATEGORY_NAME+ " TEXT NOT NULL) ";
    //Table for storing mainlist items
    private static final String CREATE_TBL_MAIN_LIST_ITEMS = "CREATE TABLE "
            + DatabaseContract.mainlistitems.TABLE_NAME + " ("
            + DatabaseContract.mainlistitems.MAIN_CATEGORY_NAME+ " TEXT NOT NULL, "
            + DatabaseContract.mainlistitems.ITEM_NAME + " TEXT NOT NULL,"
            + DatabaseContract.mainlistitems.ITEM_PRICE + " TEXT NOT NULL)";
    //constructor
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TBL__USER_LIST);
        sqLiteDatabase.execSQL(CREATE_TBL_USER_ITEM);
        sqLiteDatabase.execSQL(CREATE_TBL_MAIN_LIST);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    //Function to add a new list in the database
    public boolean addlist(List list)
    {
        long row=0;
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(DatabaseContract.userlist.USER_LISTNAME,list.getListname());
        values.put(DatabaseContract.userlist.USER_LISTDATE,list.getListdate());
        values.put(DatabaseContract.userlist.USER_BUDGET,list.getBudget());

        try{
            row=sqLiteDatabase.insert(DatabaseContract.userlist.TABLE_NAME,null,values);
        }catch (SQLException e)
        {
            Log.d("TAG",e.getMessage());
        }
        if(row==-1)
        {
            return false;
        }else{
            return true;
        }

    }
    //function to add an item in the list of a database
    public boolean additem(Item item)
    {
        long row=0;
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(DatabaseContract.useritem.COL_ITEMNAME,item.getItemname());
        values.put(DatabaseContract.useritem.USER_LIST_ITEM_ID,item.getItemlistid());
        values.put(DatabaseContract.useritem.COL_ITEMPRICE,item.getItemprice());
        values.put(DatabaseContract.useritem.COL_ITEMQUANTITY,item.getItemquantity());
        try{
            row=sqLiteDatabase.insert(DatabaseContract.useritem.TABLE_NAME,null,values);
        }catch (SQLException e)
        {
            Log.d("TAG",e.getMessage());
        }
        if(row==-1)
        {
            return false;
        }else{
            return true;
        }
    }
    //fuunction to get the id og the latest list created
    public int  getlistid()
    {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("Select Max("+DatabaseContract.userlist.USER_LISTID+") FROM " + DatabaseContract.userlist.TABLE_NAME,null);
        int maxid=(cursor.moveToFirst()? cursor.getInt(0):0);
        return maxid;

    }
    //function tto get all the items of a list
    public ArrayList<Item> getItementries(int listid)
    {
        String id=String.valueOf(listid);
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ArrayList<Item> itemArrayList=new ArrayList<Item>();
        try{

            String selection=DatabaseContract.useritem.USER_LIST_ITEM_ID+" = ?";
            String[] selectionArgs={id};
            Cursor cursor=sqLiteDatabase.query(DatabaseContract.useritem.TABLE_NAME,null,selection,selectionArgs,null,null,null);
            while(cursor.moveToNext())
            {
                Item item=new Item();
                item.setItemid(cursor.getInt(0));
                item.setItemname(cursor.getString(1));
                item.setItemlistid(cursor.getInt(2));
                item.setItemprice(cursor.getString(3));
                item.setItemquantity(cursor.getString(4));
                itemArrayList.add(item);
            }
            Log.d("TAG",String.valueOf(cursor.getCount()));
        }catch(SQLException e){
            Log.d("TAG",e.getMessage());
        }
        return itemArrayList;
    }
    //function to get all the listnames
    public ArrayList<List> getlistnames()
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ArrayList<List> list=new ArrayList<>();

        try{
            String[] columns={DatabaseContract.userlist.USER_LISTID,DatabaseContract.userlist.USER_LISTNAME,DatabaseContract.userlist.USER_LISTDATE};
            String sort=DatabaseContract.userlist.USER_LISTID + " ASC";
            Cursor cursor=sqLiteDatabase.query(DatabaseContract.userlist.TABLE_NAME,columns,null,null,null,null,sort);
            while(cursor.moveToNext())
            {
                List newlist=new List();
                newlist.setListid(cursor.getInt(0));
                newlist.setListname(cursor.getString(1));
                newlist.setListdate(cursor.getString(2));
                list.add(newlist);
            }
            cursor.close();
        } catch(SQLiteException e)
        {
            Log.d("TAG",e.getMessage());
        }
        return list;
    }//end of getlistnames function
    //function tto get all the items of a list
    public ArrayList<Item> getItemprices(int listid)
    {
        String id=String.valueOf(listid);
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ArrayList<Item> itemArrayprice=new ArrayList<Item>();
        try{
            String[] columns={DatabaseContract.useritem.COL_ITEMPRICE};
            String selection=DatabaseContract.useritem.USER_LIST_ITEM_ID+" = ?";
            String[] selectionArgs={id};
            Cursor cursor=sqLiteDatabase.query(DatabaseContract.useritem.TABLE_NAME,columns,selection,selectionArgs,null,null,null);
            while(cursor.moveToNext())
            {
                Item item=new Item();
                item.setItemprice(cursor.getString(0));
                itemArrayprice.add(item);
            }
            Log.d("TAG",String.valueOf(cursor.getCount()));
        }catch(SQLException e){
            Log.d("TAG",e.getMessage());
        }
        return itemArrayprice;
    }

    public int getbudget(int listid)
    {
        String id=String.valueOf(listid);
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        String bb;
        int budget=0;
        ArrayList<List> listbudget=new ArrayList<List>();
        try{
            String[] columns={DatabaseContract.userlist.USER_BUDGET};
            String selection=DatabaseContract.userlist.USER_LISTID+" = ?";
            String[] selectionArgs={id};
            Cursor cursor=sqLiteDatabase.query(DatabaseContract.userlist.TABLE_NAME,columns,selection,selectionArgs,null,null,null);
            while(cursor.moveToNext())
            {
                List list=new List();
                list.setBudget(cursor.getString(0));
                listbudget.add(list);
                bb=listbudget.get(0).getBudget();
                budget=Integer.parseInt(bb);

            }
            Log.d("TAG",String.valueOf(cursor.getCount()));
        }catch(SQLException e){
            Log.d("TAG",e.getMessage());
        }
        return budget;
    }
    public boolean deleteListRecord(String listid)
    {

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        String selection=DatabaseContract.userlist.USER_LISTID+" = ?";
        String[] selectionArgs={listid};
        int i=0;
        try{
            i=sqLiteDatabase.delete(DatabaseContract.userlist.TABLE_NAME,selection,selectionArgs);
        }catch(SQLiteException e)
        {
            Log.d("TAG",e.getMessage());
        }
        if(i>0)
            return true;
        return false;
    }
    //function to update list
    public boolean updatelist(String listid,List list)
    {
        long row=0;
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(DatabaseContract.userlist.USER_LISTNAME,list.getListname());
        values.put(DatabaseContract.userlist.USER_LISTDATE,list.getListdate());
        values.put(DatabaseContract.userlist.USER_BUDGET,list.getBudget());
        String selection=DatabaseContract.userlist.USER_LISTID+" = ?";
        String[] selectionArgs={listid};

        try{
            row=sqLiteDatabase.update(DatabaseContract.userlist.TABLE_NAME,values,selection,selectionArgs);
        }catch (SQLException e)
        {
            Log.d("TAG",e.getMessage());
        }
        if(row==-1)
        {
            return false;
        }else{
            return true;
        }
    }
    public boolean deleteitem(String id)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        String selection=DatabaseContract.useritem.COL_ITEMID+" = ?";
        String[] selectionArgs={id};
        int i=0;
        try{
            i=sqLiteDatabase.delete(DatabaseContract.useritem.TABLE_NAME,selection,selectionArgs);
        }catch(SQLiteException e)
        {
            Log.d("TAG",e.getMessage());
        }
        if(i>0)
            return true;
        return false;
    }
    //function to update list
    public boolean updateitem(Item item)
    {
        long row=0;
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(DatabaseContract.useritem.COL_ITEMNAME,item.getItemname());
        values.put(DatabaseContract.useritem.COL_ITEMPRICE,item.getItemprice());
        values.put(DatabaseContract.useritem.COL_ITEMQUANTITY,item.getItemquantity());
        String selection=DatabaseContract.useritem.COL_ITEMID+" = ?";
        String[] selectionArgs={String.valueOf(item.getItemid())};

        try{
            row=sqLiteDatabase.update(DatabaseContract.useritem.TABLE_NAME,values,selection,selectionArgs);
        }catch (SQLException e)
        {
            Log.d("TAG",e.getMessage());
        }
        if(row==-1)
        {
            return false;
        }else{
            return true;
        }
    }
}
