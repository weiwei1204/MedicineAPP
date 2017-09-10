package com.example.carrie.carrie_test1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mindy on 2017/9/4.
 */
public class BsMeasureTab extends Fragment{
    ListView bsmeasure;
    private ArrayList<BsBpMeasureObject> my_measure =new ArrayList<BsBpMeasureObject>();
    MesureAdapter mesureAdapter ;
    Button saveBsMeasureButton;
    String bs1;
    String bs2;
    String bs3;
    RequestQueue requestQueue;
    String memberid;
    String updateFirstUrl = "http://54.65.194.253/Member/updateBsMeature.php";
    String[] measure_time={"9:00","12:00","18:00"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.bsmeature_tab1, container, false);
        bsmeasure = (ListView)rootView.findViewById(R.id.bsfirst);
        //BsBpMeasureObject bs = new BsBpMeasureObject(1,"1","7:00","12:30","18:00","9:00","12:00","18:00");
        Intent intent = getActivity().getIntent();
        Bundle bundle = getActivity().getIntent().getExtras();
        memberid=bundle.getString("memberid");
        BsBpMeasureObject bsBpMeasureObject = (BsBpMeasureObject)intent.getSerializableExtra("bsBpMeasureObject");
        my_measure.add(bsBpMeasureObject);
        mesureAdapter=new MesureAdapter(getActivity(),my_measure);
        bsmeasure.setAdapter(mesureAdapter);

        bsmeasure.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "你按下"+position, Toast.LENGTH_SHORT).show();
            }

        });
        saveBsMeasureButton = (Button) rootView.findViewById(R.id.saveBsMeasureButton);
        saveBsMeasureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("saveBsMeasureButton","clickabble");
                View view1 = bsmeasure.getChildAt(0);
                View view2 = bsmeasure.getChildAt(1);
                View view3 = bsmeasure.getChildAt(2);
                EditText bs_1 =(EditText) view1.findViewById(R.id.TimeMeasureDisplay);
                EditText bs_2 =(EditText) view2.findViewById(R.id.TimeMeasureDisplay);
                EditText bs_3 =(EditText) view3.findViewById(R.id.TimeMeasureDisplay);
                Log.d("saveBsMeasureButton",bs_1.getText().toString());
                Log.d("saveBsMeasureButton",bs_2.getText().toString());
                Log.d("saveBsMeasureButton",bs_3.getText().toString());
                if (bs_1.getText().length()==0){
                    bs1 = "2017-01-01 00:00:00";
                }else{
                    bs1 = "2017-09-18 "+bs_1.getText().toString();
                }
                if (bs_2.getText().length()==0){
                    bs2 = "2017-01-01 00:00:00";
                }else{
                    bs2 = "2017-09-18 "+bs_2.getText().toString();
                }
                if (bs_3.getText().length()==0){
                    bs3 = "2017-01-01 00:00:00";
                }else{
                    bs3 = "2017-09-18 "+bs_3.getText().toString();
                }
//                Log.d("saveBpMeasureButton",bp_3.getText().toString());
                insertBsMeasure();
                saveCheck();
            }
        });
        return rootView;

    }
    public void insertBsMeasure() {

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        final StringRequest request = new StringRequest(Request.Method.POST, updateFirstUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("rrr", error.toString());
                Toast.makeText(getActivity().getApplicationContext(), "Error read updateBsMeature.php!!!", Toast.LENGTH_LONG).show();
            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("bs_first", bs1);
                parameters.put("bs_second",bs2);
                parameters.put("bs_third",bs3);
                parameters.put("member_id",memberid);
                Log.d("test111111111", parameters.toString());
                return parameters;

            }
        }
                ;
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);

    }
    public void saveCheck() {
        new AlertDialog.Builder(getActivity())
                .setMessage("儲存成功")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .show();
    }

}
