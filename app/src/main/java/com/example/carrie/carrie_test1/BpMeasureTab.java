package com.example.carrie.carrie_test1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class BpMeasureTab  extends Fragment {
    ListView bpmeasure;
    private ArrayList<BsBpMeasureObject> my_measure =new ArrayList<BsBpMeasureObject>();
    MesureBpAdapter mesureBpAdapter ;
    Button saveBpMeasureButton;
    String bp1;
    String bp2;
    String bp3;
    RequestQueue requestQueue;
    String memberid;
    String updateFirstUrl = "http://54.65.194.253/Member/updateMeature.php";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bpmeature_tab2, container, false);
        bpmeasure = (ListView)rootView.findViewById(R.id.bpfirst);
        //BsBpMeasureObject bs = new BsBpMeasureObject(1,"1","7:00","12:30","18:00","9:00","12:00","18:00");
        Intent intent = getActivity().getIntent();
        Bundle bundle = getActivity().getIntent().getExtras();
        memberid=bundle.getString("memberid");
        BsBpMeasureObject bsBpMeasureObject = (BsBpMeasureObject)intent.getSerializableExtra("bsBpMeasureObject");
        my_measure.add(bsBpMeasureObject);
        mesureBpAdapter=new MesureBpAdapter(getActivity(),my_measure);
        bpmeasure.setAdapter(mesureBpAdapter);
        saveBpMeasureButton = (Button) rootView.findViewById(R.id.saveBpMeasureButton);
        saveBpMeasureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("saveBpMeasureButton","clickabble");
                View view1 = bpmeasure.getChildAt(0);
                View view2 = bpmeasure.getChildAt(1);
                View view3 = bpmeasure.getChildAt(2);
                EditText bp_1 =(EditText) view1.findViewById(R.id.TimeMeasureDisplay);
                EditText bp_2 =(EditText) view2.findViewById(R.id.TimeMeasureDisplay);
                EditText bp_3 =(EditText) view3.findViewById(R.id.TimeMeasureDisplay);
//                Log.d("saveBpMeasureButton",bp_1.getText().toString());
//                Log.d("saveBpMeasureButton",bp_2.getText().toString());
                if (bp_1.getText().length()==0){
                    bp1 = "2017-01-01 00:00:00";
                }else{
                    bp1 = "2017-09-18 "+bp_1.getText().toString();
                }
                if (bp_2.getText().length()==0){
                    bp2 = "2017-01-01 00:00:00";
                }else{
                    bp2 = "2017-09-18 "+bp_2.getText().toString();
                }
                if (bp_3.getText().length()==0){
                    bp3 = "2017-01-01 00:00:00";
                }else{
                    bp3 = "2017-09-18 "+bp_3.getText().toString();
                }
//                Log.d("saveBpMeasureButton",bp_3.getText().toString());
                insertMeasure();
            }
        });
        return rootView;
    }
    public void insertMeasure() {

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        final StringRequest request = new StringRequest(Request.Method.POST, updateFirstUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("rrr", error.toString());
                Toast.makeText(getActivity().getApplicationContext(), "Error read updateMeature.php!!!", Toast.LENGTH_LONG).show();
            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("bp_first", bp1);
                parameters.put("bp_second",bp2);
                parameters.put("bp_third",bp3);
                parameters.put("member_id",memberid);
                Log.d("test111111111", parameters.toString());
                return parameters;

            }
        }
                ;
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);

    }
}