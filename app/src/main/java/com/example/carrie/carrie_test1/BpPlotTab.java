package com.example.carrie.carrie_test1;

/**
 * Created by jonathan on 2017/8/6.
 */
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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

    private static int numberOfLines = 1;
    private static int maxNumberOfLines = 4;
    private static int numberOfPoints ;

    static float[][] randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];

    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasPoints = true;
    private boolean hasLines = true;
    private boolean isCubic = false;
    private boolean hasLabels = false;
    public String url = "http://54.65.194.253/Health_Calendar/getBpRecordDate.php";
    private BloodPressure record ;
    private List<BloodPressure> data_list;
    RequestQueue requestQueue;
    private boolean getdb = false;
    public static ArrayList<BloodPressure>bloodPressureList;
    public static String sentmember_id; //從上頁傳進來的
    public static String member_id;//getRecord抓的
    public static int userid;
    private String memberid; //從資料庫抓的
    public static String highmmhg ="";
    public static String lowmmhg ="";
    public static String bpm ="";
    public static String savetime ="";
//上面是傳過來的
    public static String usrhighmmhg ="";
    public static String usrlowmmhg ="";
    public static String usrbpm ="";
    public static String usrsavetime ="";
//上面是自己從資料庫抓的
    public static int [] higharray ;
    public static int [] lowarray ;
    public static int [] bpmarray ;
//傳過來塞進去
    public static String [] time ;
    public static ArrayList<Date> dateList = new ArrayList<Date>();

    public static int [] highvaluearray ;
    public static int [] lowvaluearray ;
    public static int [] bpmvaluearray ;
    public static String [] datearray;
//抓下來塞進去
    boolean rd = false;
    public int count;

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_combo_line_column_chart, container, false);
        chart = (ComboLineColumnChartView) rootView.findViewById(R.id.chart);
        chart.setOnValueTouchListener(new ValueTouchListener());
        getRecord();
        Bundle bundle = this.getArguments();
        sentmember_id = bundle.getString("memberid");
        Log.d("9999","memberid:"+sentmember_id);
//        highmmhg = bundle.getString("highmmhg");
//        Log.d("9999","highmmhg:"+highmmhg);
//        lowmmhg = bundle.getString("lowmmhg");
//        Log.d("9999","lowmmhg:"+lowmmhg);
//        bpm = bundle.getString("bpm");
//        Log.d("9999","bpm:"+bpm);
//        sugarvalue = bundle.getString("sugarvalue");
//        Log.d("9999","sugarvalue:"+sugarvalue);
//        savetime = bundle.getString("savetime");
//        Log.d("9999","savetime:"+savetime);
        if(bundle!=null) {

            bloodPressureList = bundle.getParcelableArrayList("data_list");
            higharray = new int[bloodPressureList.size()];
            lowarray = new int[bloodPressureList.size()];
            bpmarray = new int[bloodPressureList.size()];
            time = new String[bloodPressureList.size()];

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

            for(int i =0;i<bloodPressureList.size();i++) {
                memberid = bloodPressureList.get(i).getMember_id();
                if(memberid.equals(sentmember_id)) {
                        try {
                            savetime = bloodPressureList.get(i).getSavetime();
                            Log.d("8787", "id:" + memberid);
                            highmmhg = bloodPressureList.get(i).getHighmmhg();
                            Log.d("8787", "highmmhg:" + highmmhg);
                            lowmmhg = bloodPressureList.get(i).getLowmmhg();
                            Log.d("8787", "lowmmhg:" + lowmmhg);
                            bpm = bloodPressureList.get(i).getBpm();
                            Log.d("8787", "bpm:" + bpm);
                            Log.d("8787", "savetime:" + savetime);
                            higharray[i] = Integer.parseInt(highmmhg);
                            lowarray[i] = Integer.parseInt(lowmmhg);
                            bpmarray[i] = Integer.parseInt(bpm);
                            time[i] = savetime;
                            Log.d("3333","test:" +Arrays.toString(higharray));
                            Log.d("3333","test:" +Arrays.toString(lowarray));
                            Log.d("3333","test:" +Arrays.toString(bpmarray));
                            Log.d("3333","test:" +Arrays.toString(time));
                            Date date = simpleDateFormat.parse(savetime);
                            System.out.println("date : "+simpleDateFormat.format(date));
                            Calendar threeday = Calendar.getInstance();
                            threeday.add(Calendar.DATE, -3);
                            if(date.after(threeday.getTime())){
                                dateList.add(date);
                                System.out.println("list : "+simpleDateFormat.format(dateList));
                            }
                        }
                        catch (ParseException ex) {
                            System.out.println("Exception "+ex);
                        }

                    }
                }
            Log.d("4444","test:" +Arrays.toString(higharray));
            }
                else{
            bloodPressureList = new ArrayList<BloodPressure>();
        }
//        generateValues();
//        generateData();
//        addLineToData();
        return rootView;

        }
        //onCreateView區域在上面


    public static boolean isCurrentThreedays(String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date usrRecord = new Date();
        sdf.setLenient(false);
        try {
            for(int i =0 ;i< dateList.size();i++){
                usrRecord = dateList.get(i);
            }
            Calendar threeday = Calendar.getInstance();
            threeday.add(Calendar.DATE, -3);

//            return now.get(Calendar.YEAR) == cdate.get(Calendar.YEAR)
//                    && now.get(Calendar.MONTH) == cdate.get(Calendar.MONTH)
//                    && now.get(Calendar.DATE) == cdate.get(Calendar.DATE);
            if(usrRecord.before(threeday.getTime())){
                 return true;
            }else{
                 return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;

        }
    }
    public  void getRecord(){
        Log.d("777","in method");
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Log.d("777","1");
        final JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.POST, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("777","in response");
                count = -1;
                try {
                    rd = true;
//                    JSONArray array = new JSONArray(response);
//                    Log.d("777",array.toString());



                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        record = new BloodPressure(object.getInt("id"), object.getString("member_id"), object.getString("highmmhg"), object.getString("lowmmhg"), object.getString("bpm"), object.getString("savetime"));
//                        data_list.add(record);
//                        highvaluearray = new int[response.length()];
//                        lowvaluearray = new int[response.length()];
//                        bpmvaluearray = new int[response.length()];
//                        datearray =new String[response.length()];
                        userid = object.getInt("id");
                        member_id = object.getString("member_id");
                        Log.d("1234","saw id:" +member_id);
                        if (member_id.equals(sentmember_id)) {
                            usrhighmmhg = object.getString("highmmhg");
                            usrlowmmhg = object.getString("lowmmhg");
                            usrbpm = object.getString("bpm");
                            usrsavetime = object.getString("savetime");

                            highvaluearray = new int[response.length()];
                            lowvaluearray = new int[response.length()];
                            bpmvaluearray = new int[response.length()];
                            datearray = new String[response.length()];


                            count++;
                            Log.d("8777","response"+response.length());
                            Log.d("8777","count"+count);
                            Log.d("6969", "member_id:" + member_id);
                            Log.d("6969", "highmmhg:" + usrhighmmhg);
                            Log.d("6969", "lowmmhg:" + usrlowmmhg);
                            Log.d("6969", "bpm:" + usrbpm);
                            Log.d("9999", "savetime:" + usrsavetime);

                            highvaluearray[count] = Integer.parseInt(usrhighmmhg);
                            lowvaluearray[count] = Integer.parseInt(usrlowmmhg);
                            bpmvaluearray[count] = Integer.parseInt(usrbpm);
                            datearray[count] = usrsavetime;


                            Log.d("2345","higharray:"+highvaluearray[count]);
                            Log.d("3456","lowarray:" +lowvaluearray[count]);
                            Log.d("4567","bpmarray:" +bpmvaluearray[count]);
                            Log.d("1112","datearray:"+datearray[count]);

                            numberOfPoints = count+1;
                            randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];

                            for (int k = 0; k < maxNumberOfLines; ++k) {
                                Log.d("9996","number of lines:"+maxNumberOfLines);
                                for (int j = 0; j < numberOfPoints; ++j) {
                                    Log.d("9996","number of points:"+numberOfPoints);
                                    randomNumbersTab[k][j] = highvaluearray[count];
                                }
                            }


                        }
                    }

                    Log.d("9995","num:"+numberOfPoints);
                    Log.d("8721","count:"+count);
                    Log.d("9995","higharray"+highvaluearray[count]);

//                  generateValues();
                    generateData();
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("777",error.toString());
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
        requestQueue.add(jsonObjectRequest);



    }



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
//    private static void generateValues() {
//        Log.d("9995","number of points:"+numberOfPoints);
//        for (int i = 0; i < maxNumberOfLines; ++i) {
//            Log.d("9996","number of lines:"+maxNumberOfLines);
//            for (int j = 0; j < numberOfPoints; ++j) {
//                Log.d("9996","number of points:"+numberOfPoints);
////                randomNumbersTab[i][j] = (float) Math.random() * 100f + 5;
//                randomNumbersTab[i][j] = highvaluearray[j];
//            }
//        }
//    }
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
                axisX.setName("3日內變化");
                axisY.setName("血壓平均  mmhg");
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
        int numColumns ;
        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        numColumns = numberOfPoints;
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue(highvaluearray[count], ChartUtils.COLOR_GREEN));
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
                value.setTarget(value.getX(), (float) Math.random() * 100 + 5);
            }
        }

        // Columns animations
        for (Column column : data.getColumnChartData().getColumns()) {
            for (SubcolumnValue value : column.getValues()) {
                value.setTarget((float) Math.random() * 100 + 5);
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

