package com.example.bringappfinal;

import android.provider.BaseColumns;

public class DatabaseContract {
    public DatabaseContract(){}
    //Table for userlist
    public static abstract class userlist implements BaseColumns {
        public static final String TABLE_NAME="UserList";
        public static final String USER_LISTID="List_id";
        public static final String USER_LISTNAME="listname";
        public static final String USER_LISTDATE="listdate";
        public static final String USER_BUDGET="budget";

    }
    //table for user list items
    public static abstract class useritem implements BaseColumns {
        public static final String TABLE_NAME="User_Item";
        public static final String COL_ITEMID="ItemId";
        public static final String COL_ITEMNAME="Itemname";
        public static final String USER_LIST_ITEM_ID="itemlistid";
        public static final String COL_ITEMPRICE="Itemprice";
        public static final String COL_ITEMQUANTITY="Itemquantity";
    }
    //table for mainlist categories
    public static abstract class mainlist implements BaseColumns {
        public static final String TABLE_NAME="Main_List";
        public static final String CATEGORY_NAME="Category_name";
    }
    //table for mainlist items
    public static abstract class mainlistitems implements BaseColumns {
        public static final String TABLE_NAME="Main_List_ITEMS";
        public static final String MAIN_CATEGORY_NAME="Main_Category_name";
        public static final String ITEM_NAME="main_item_name";
        public static final String ITEM_PRICE="main_item_price";
    }

}
