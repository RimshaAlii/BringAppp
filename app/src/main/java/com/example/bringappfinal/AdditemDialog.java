package com.example.bringappfinal;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;


//Java class for dialog box add item extends if from AppCompatDialogFragment
public class AdditemDialog extends AppCompatDialogFragment {
    public static final String[] ITEMS={"wheat","rye","oats","corn","barley","buckwheat","rice","bread","rolls","buns","cake","cookie","pies","cereal","corn flakes","oat flakes","wheat flakes","rice flakes","muesli","popcorn","pasta","macaroni","noodles","spaghetti","vermicelli","ravioli","dumplings","flour","dough","batter","cake mix","white bread","whole-wheat bread","corn bread","pita bread","bread roll","sesame roll","poppy seed rool","cinnamon roll","cracker","biscuit","cookie","toast","breadstick","pretzel","hardtack","wafer","waffle","crouton","birthday cake","wedding cake","christmas cake","shortcake","chocolate cake","napoleon","cheesecake","gingerbread","chocolatechip ncake","chocolate layer cake","chocolate frosting cake","honey cake","pound cake","almond cake","raisin cupcake","fudge brownie","oatmeal cookie","chocolate cookie","pie","apple pie","blueberry pie","cherry pie","homemade pie","tart", "apple tart","mince pie","mincemeat pie","pumpkin pie","rhubarb pie","meat pie","knish","pizza","muffin","blueberry muffin","raisin muffin","sour cream biscuit","pancake","griddle cake","English muffin","doughnut (donut)", "fritter","meat","beef","pork","veal","lamb","mutton","beefsteak","roast beef","ground beef","hamburger","spare rib","pork chop","lamb chop","veal cutlet","ham","bacon","pastrami","corned beef","sausage","salami","smoked sausage","Bologna","chicken","turkey","goose","duck","fowl","eggs","whole chicken","chicken quarters","chicken leg","drumstick","chicken wing","chicken breast","turkey breast","fish", "salmon", "trout","sturgeon", "cod","sole", "flatfish", "plaice","halibut", "tuna", "perch", "bass", "sea bass","seafood", "shrimp", "prawns", "crab", "crayfish (crawfish)", "lobster", "oysters", "clams", "shellfish", "squid",
            "milk", "whole milk", "skim milk (skimmed milk)", "low-fat milk", "nonfat milk", "pasteurized milk","dry milk", "condensed milk", "yogurt", "kefir", "sour milk", "buttermilk","cream", "sour cream", "butter","cottage cheese", "farmer cheese", "homemade cheese", "cream cheese", "Swiss cheese", "Parmesan", "Cheddar", "Mozzarella", "Roquefort", "blue cheese","hard cheese", "soft cheese", "sharp cheese", "mild cheese", "smoked cheese", "grated cheese",
            "ice cream", "vanilla ice cream", "chocolate ice cream", "fruit ice", "strawberry ice cream","ice-cream cone", "popsicle", "sundae",
            "fresh fruit", "apple", "pear", "apricot", "peach", "nectarine", "plum", "grapes", "cherry", "sweet cherry","lemon", "lime", "orange", "tangerine", "grapefruit", "banana", "kiwi", "pineapple", "olive", "fig","papaya", "mango", "avocado", "coconut", "persimmon", "pomegranate", "melon", "watermelon",
            "dried apricots", "raisins", "figs", "prunes", "dates", "candied fruit", "hazelnuts", "walnuts", "almonds", "chestnuts", "peanuts", "pistachio nuts", "cedar nuts",
            "raspberry jam", "cranberry jam", "grape jelly", "marmalade", "honey", "maple syrup", "peanut butter",
            "fresh vegetables", "salad vegetable", "canned vegetables", "leaf vegetables", "leafy greens","tomato", "cucumber", "carrot", "beet (beetroot)", "potato", "onion", "green onions", "spring onions","leek",
            "apple juice", "orange juice", "grapefruit juice", "lemon juice", "tomato juice",
            "tea", "green tea", "black tea", "tea with milk", "iced tea", "herbal tea","mint tea", "Indian tea","coffee", "instant coffee", "espresso", "cappuccino", "decaffeinated coffee","black coffee",
            "mineral water", "spring water", "soft drink", "soda water", "lemonade", "cider",
            "tomato sauce", "ketchup", "mushroom sauce", "meat sauce", "steak sauce", "gravy", "spaghetti sauce","hot sauce", "chili sauce", "barbecue sauce", "sweet-and-sour sauce", "spicy sauce", "garlic sauce","white sauce", "dip sauce", "soy sauce", "apple sauce","cranberry sauce","mayonnaise",
            "vegetable oil", "olive oil", "corn oil", "sunflower seed oil", "sesame oil",
            "margarine", "grease", "lard", "fat", "animal fat", "vegetable fat"};



    String item_id="";
        String item_name;
        String item_price;
        String item_quantity;
    public AdditemDialog(@NonNull String item_id,String item_name, String item_price, String item_quantity) {
        this.item_id=item_id;
        this.item_name = item_name;
        this.item_price = item_price;
        this.item_quantity = item_quantity;

    }

    public AdditemDialog() {
    }

    @NonNull
    //edittexts of dialogbox
            AutoCompleteTextView itemName;
    EditText itemPrice;
    EditText itemQuantity;
    private AdditemDialogListener listener;
    Item item=new Item();


    //overriding method of AppcompatDialogFragment
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.additemdialog,null);//adding layout of our dialog that we created in additemdialog.xml
        itemName=view.findViewById(R.id.itemnamee);
        itemPrice=view.findViewById(R.id.itemprice);
        itemQuantity=view.findViewById(R.id.itemquantity);
        if(!item_id.isEmpty())
        {
            itemName.setText(item_name);
            itemPrice.setText(item_price);
            itemQuantity.setText(item_quantity);
        }//setting things in builder
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1,ITEMS);
        itemName.setAdapter(arrayAdapter);

        builder.setView(view)
                .setTitle("Add Item")//title of dialogbox
                //cancel button for dialog
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Add item", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String itemname=itemName.getText().toString();
                        String itemprice=itemPrice.getText().toString();
                        String itemquantity=itemQuantity.getText().toString();
                        if(itemname.isEmpty()||itemprice.isEmpty())
                        {
                            Toast.makeText(getActivity(), "There must be an item name & price!", Toast.LENGTH_LONG).show();
                        }
                        else  if(!item_id.isEmpty())
                        {
                            listener.applyTexts(item_id,itemname,itemprice,itemquantity);
                        }
                        else{
                            listener.applyTexts(itemname, itemprice, itemquantity);
                        }


                    }
                });

        return builder.create();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener=(AdditemDialogListener) context;
    }
    public interface AdditemDialogListener{
        void applyTexts(String itemname,String itemprice,String itemquantity);
        void applyTexts(String id,String itemname,String itemprice,String itemquantity);

    }
}

