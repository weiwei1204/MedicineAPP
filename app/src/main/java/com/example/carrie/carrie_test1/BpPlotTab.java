package com.example.carrie.carrie_test1;

/**
 * Created by jonathan on 2017/8/6.
 */
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.listener.ComboLineColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.ComboLineColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ComboLineColumnChartView;

public class BpPlotTab extends Fragment{
    private ComboLineColumnChartView chart;
    private ComboLineColumnChartData data;

    private int numberOfLines = 1;
    private int maxNumberOfLines = 4;
    private int numberOfPoints = 12;

    float[][] randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];

    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasPoints = true;
    private boolean hasLines = true;
    private boolean isCubic = false;
    private boolean hasLabels = false;

    RequestQueue requestQueue;
    public String url = "http://54.65.194.253/Health_Calendar/ShowBp.php";
    private List<BloodPressure>bloodPressureList;
    public static String member_id; //從上頁傳進來的
    public static int userid;
    private String memberid; //從資料庫抓的
    private String highmmhg ;
    private String lowmmhg;
    private String bpm;
    private String sugarvalue;
    private String savetime;






    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_combo_line_column_chart, container, false);
        chart = (ComboLineColumnChartView) rootView.findViewById(R.id.chart);
        chart.setOnValueTouchListener(new ValueTouchListener());
        Bundle bundle = this.getArguments();
        member_id = bundle.getString("memberid");
        Log.d("9999","memberid:"+member_id);
       highmmhg = bundle.getString("highmmhg");
        Log.d("9999","highmmhg:"+highmmhg);
       lowmmhg = bundle.getString("lowmmhg");
        Log.d("9999","lowmmhg:"+lowmmhg);
        bpm = bundle.getString("bpm");
        Log.d("9999","bpm:"+bpm);
       sugarvalue = bundle.getString("sugarvalue");
        savetime = bundle.getString("savetime");
//        getData();
        bloodPressureList = new ArrayList<>();
        Log.d("0909","high:"+highmmhg);
        Log.d("0909","low:"+lowmmhg);
//        for(int i =0;i<bloodPressureList.size();i++) {
//          String high = bloodPressureList.get(i).getHighmmhg();
//            Log.d("3456","high:"+high);
//          String low  = bloodPressureList.get(i).getLowmmhg();
//            Log.d("3456","low:"+low);
//        }
        generateValues();
        generateData();
        return rootView;
        }

//    public void getData(){
//        Log.d("777","in method");
//        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
//        Log.d("777","1");
//        final JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                Log.d("777","in response");
//                try {
////                    JSONArray array = new JSONArray(response);
////                    Log.d("777",array.toString());
//                    for (int i = 0; i < response.length(); i++) {
//                        JSONObject object = response.getJSONObject(i);
//                        BloodPressure bp = new BloodPressure(object.getInt("id"),object.getString("member_id"),object.getString("highmmhg"),object.getString("lowmmhg"),object.getString("bpm"),object.getString("sugarvalue"),object.getString("savetime"));
//                        bloodPressureList.add(bp);
//                        userid = object.getInt("id");
//                        memberid = object.getString("member_id");
//                        if (member_id.equals(memberid)) {
//                            highmmhg = object.getString("highmmhg");
//                            lowmmhg = object.getString("lowmmhg");
//                            bpm = object.getString("bpm");
//                            sugarvalue = object.getString("sugarvalue");
//                            savetime = object.getString("savetime");
//                            Log.d("55556", "member_id:" + member_id);
//                            Log.d("55556", "highmmhg:" + highmmhg);
//                            Log.d("55556", "lowmmhg:" + lowmmhg);
//                            Log.d("55556", "bpm:" + bpm);
//                            Log.d("55556", "sugarvalue:" + sugarvalue);
//                            Log.d("55556", "savetime:" + savetime);
//                        }
//                    }
//
//
//
//                }catch (JSONException e){
//                    e.printStackTrace();
//                }
//
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("777",error.toString());
//                    }
//                }){
//            protected Map<String, String> getParams(){
//                Map<String, String> parameters = new HashMap<>();
//                parameters.put("highmmhg", highmmhg);
//                parameters.put("lowmmhg", lowmmhg);
//                parameters.put("bpm", bpm);
//                parameters.put("sugarvalue", sugarvalue);
//                parameters.put("savetime", savetime);
//                Log.d("highmmhg", parameters.toString());
//                return parameters;
//            }
//        };
//
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
//        requestQueue.add(jsonObjectRequest);
//
//
//    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.combo_line_column_chart, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_reset) {
            reset();
            generateData();
            return true;
        }
        if (id == R.id.action_add_line) {
            addLineToData();
            return true;
        }
        if (id == R.id.action_toggle_lines) {
            toggleLines();
            return true;
        }
        if (id == R.id.action_toggle_points) {
            togglePoints();
            return true;
        }
        if (id == R.id.action_toggle_cubic) {
            toggleCubic();
            return true;
        }
        if (id == R.id.action_toggle_labels) {
            toggleLabels();
            return true;
        }
        if (id == R.id.action_toggle_axes) {
            toggleAxes();
            return true;
        }
        if (id == R.id.action_toggle_axes_names) {
            toggleAxesNames();
            return true;
        }
        if (id == R.id.action_animate) {
            prepareDataAnimation();
            chart.startDataAnimation();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void generateValues() {
        for (int i = 0; i < maxNumberOfLines; ++i) {
            for (int j = 0; j < numberOfPoints; ++j) {
                randomNumbersTab[i][j] = (float) Math.random() * 50f + 5;
            }
        }
    }
    private void reset() {
        numberOfLines = 1;

        hasAxes = true;
        hasAxesNames = true;
        hasLines = true;
        hasPoints = true;
        hasLabels = false;
        isCubic = false;

    }
    private void generateData() {
        // Chart looks the best when line data and column data have similar maximum viewports.
        data = new ComboLineColumnChartData(generateColumnData(), generateLineData());

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("8/13");
                axisY.setName("血壓平均mmhg");
            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        chart.setComboLineColumnChartData(data);
    }
    private LineChartData generateLineData() {

        List<Line> lines = new ArrayList<Line>();
        for (int i = 0; i < numberOfLines; ++i) {

            List<PointValue> values = new ArrayList<PointValue>();
            for (int j = 0; j < numberOfPoints; ++j) {
                values.add(new PointValue(j, randomNumbersTab[i][j]));
            }

            Line line = new Line(values);
            line.setColor(ChartUtils.COLORS[i]);
            line.setCubic(isCubic);
            line.setHasLabels(hasLabels);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);
            lines.add(line);
        }

        LineChartData lineChartData = new LineChartData(lines);

        return lineChartData;

    }
    private ColumnChartData generateColumnData() {
        int numSubcolumns = 1;
        int numColumns = 12;
        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue((float) Math.random() * 50 + 5, ChartUtils.COLOR_GREEN));
            }

            columns.add(new Column(values));
        }

        ColumnChartData columnChartData = new ColumnChartData(columns);
        return columnChartData;
    }
    private void addLineToData() {
        if (data.getLineChartData().getLines().size() >= maxNumberOfLines) {
            Toast.makeText(getActivity(), "Samples app uses max 4 lines!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            ++numberOfLines;
        }

        generateData();
    }
    private void toggleLines() {
        hasLines = !hasLines;

        generateData();
    }

    private void togglePoints() {
        hasPoints = !hasPoints;

        generateData();
    }

    private void toggleCubic() {
        isCubic = !isCubic;

        generateData();
    }

    private void toggleLabels() {
        hasLabels = !hasLabels;

        generateData();
    }

    private void toggleAxes() {
        hasAxes = !hasAxes;

        generateData();
    }

    private void toggleAxesNames() {
        hasAxesNames = !hasAxesNames;

        generateData();
    }
    private void prepareDataAnimation() {

        // Line animations
        for (Line line : data.getLineChartData().getLines()) {
            for (PointValue value : line.getValues()) {
                // Here I modify target only for Y values but it is OK to modify X targets as well.
                value.setTarget(value.getX(), (float) Math.random() * 50 + 5);
            }
        }

        // Columns animations
        for (Column column : data.getColumnChartData().getColumns()) {
            for (SubcolumnValue value : column.getValues()) {
                value.setTarget((float) Math.random() * 50 + 5);
            }
        }
    }
    private class ValueTouchListener implements ComboLineColumnChartOnValueSelectListener {

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

        @Override
        public void onColumnValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
            Toast.makeText(getActivity(), "Selected column: " + value, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPointValueSelected(int lineIndex, int pointIndex, PointValue value) {
            Toast.makeText(getActivity(), "Selected line point: " + value, Toast.LENGTH_SHORT).show();
        }
        public void goback(View v)
        {
            getActivity().onBackPressed();
        }

    }





    // MENU

    }

