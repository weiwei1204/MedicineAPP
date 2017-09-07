package com.example.carrie.carrie_test1;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.DialogFragment;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by jonathan on 2017/9/5.
 */

public  class BsBpDialogFragment extends DialogFragment {

    Button btn_confirm;
    Button btn_cancel;
    TextView text_content;
    TextView text_title;
    TextView text_close;
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment
        rootView = inflater.inflate(R.layout.yuanma_dialog,container,false);


        btn_confirm = (Button)rootView.findViewById(R.id.btn_confirm);
        btn_cancel = (Button)rootView.findViewById(R.id.btn_cancel);
        text_close = (TextView)rootView.findViewById(R.id.text_close);
        text_title  = (TextView)rootView.findViewById(R.id.text_title);
        text_content = (TextView)rootView.findViewById(R.id.text_content);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickyes();

            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickno();

            }
        });
        text_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return rootView;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        Dialog dialog = super.onCreateDialog(savedInstanceState);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        return dialog;

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(rootView);
        final AlertDialog dialog = builder.create();
        dialog.show();
        return builder.create();

    }
    public void clickyes(){
        Intent it = new Intent(getActivity(),EnterBsBpActivity.class);
        startActivity(it);


    }
    public void clickno(){

    }



}
