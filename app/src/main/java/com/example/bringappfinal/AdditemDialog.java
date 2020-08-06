package com.example.bringappfinal;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;


//Java class for dialog box add item extends if from AppCompatDialogFragment
public class AdditemDialog extends AppCompatDialogFragment {
    @NonNull
    //edittexts of dialogbox
            EditText itemName;
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
        itemQuantity=view.findViewById(R.id.itemquantity); //setting things in builder
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
                        //    if(itemname.isEmpty())
                        //    {
                        //        itemName.setError("Item Name is mandatory");
                        //    }
                        String itemprice=itemPrice.getText().toString();
                        String itemquantity=itemQuantity.getText().toString();
                        listener.applyTexts(itemname,itemprice,itemquantity);

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

    }
}

