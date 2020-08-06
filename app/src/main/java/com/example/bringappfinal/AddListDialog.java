package com.example.bringappfinal;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddListDialog extends AppCompatDialogFragment {
    EditText listnameedit;
    EditText budgetedit;
    TextView datelist;
    private AddlistDialogListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        String    date= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        View view=layoutInflater.inflate(R.layout.createlistdialog,null);
        datelist=(TextView) view.findViewById(R.id.dateoflist);
        datelist.setText(date);
        listnameedit=(EditText) view.findViewById(R.id.listnameedit);
        budgetedit=(EditText) view.findViewById(R.id.listbudget);

        builder.setView(view)
                .setTitle("Add List Name")//title of dialogbox
                //cancel button for dialog
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Create List", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String listname=listnameedit.getText().toString();
                        String budget=budgetedit.getText().toString();
                        String date=datelist.getText().toString();
                        //validating that list name must be given
                        if((listname.isEmpty())||budget.isEmpty())
                        {
                            Toast.makeText(getActivity(), "Sorry! You have to give me List name & Budget", Toast.LENGTH_LONG).show();
                        }
                        else{
                            listener.applyTexts(listname,budget,date);
                        }
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener=(AddlistDialogListener) context;
    }

    public interface AddlistDialogListener{
        void applyTexts(String listname,String budget,String date);
    }
}

