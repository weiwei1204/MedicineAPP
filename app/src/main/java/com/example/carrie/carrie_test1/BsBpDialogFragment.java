package com.example.carrie.carrie_test1;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.DialogFragment;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by jonathan on 2017/9/5.
 */

public  class BsBpDialogFragment extends DialogFragment {
    public BsBpDialogFragment(){
    }

    Button btn_confirm;
    Button btn_cancel;
    TextView text_content;
    TextView text_title;
    TextView text_close;
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment
        rootView = inflater.inflate(R.layout.yuanma_dialog,null);
        btn_confirm = (Button)rootView.findViewById(R.id.btn_confirm);
        btn_cancel = (Button)rootView.findViewById(R.id.btn_cancel);
        text_close = (TextView)rootView.findViewById(R.id.text_close);
        text_title  = (TextView)rootView.findViewById(R.id.text_title);
        text_content = (TextView)rootView.findViewById(R.id.text_content);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(),EnterBsBpActivity.class);
                it.putExtra("memberid",MonitorActivity.my_id);
                it.putExtra("my_google_id",MonitorActivity.my_google_id);
                startActivity(it);
                getActivity().finish();

            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        text_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();

            }
        });
        return rootView;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        Dialog dialog = super.onCreateDialog(savedInstanceState);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        return dialog;

        // Use the Builder class for convenient dialog construction
        rootView  = getActivity().getLayoutInflater().inflate(R.layout.yuanma_dialog,new LinearLayout(getActivity()), false);
        btn_confirm = (Button)rootView.findViewById(R.id.btn_confirm);
        btn_cancel = (Button)rootView.findViewById(R.id.btn_cancel);
        text_close = (TextView)rootView.findViewById(R.id.text_close);
        text_title  = (TextView)rootView.findViewById(R.id.text_title);
        text_content = (TextView)rootView.findViewById(R.id.text_content);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(),EnterBsBpActivity.class);
                it.putExtra("memberid",MonitorActivity.my_id);
                startActivity(it);

            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        text_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        Dialog builder = new Dialog(getActivity());
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                getActivity().finish();
            }
        });
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    getActivity().finish();
                }
                return true;
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                getActivity().finish();
            }
        });
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setContentView(rootView);
        builder.setCancelable(false);
        return builder;

    }



}
