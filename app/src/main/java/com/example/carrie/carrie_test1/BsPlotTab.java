package com.example.carrie.carrie_test1;

/**
 * Created by jonathan on 2017/8/6.
 */
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lecho.lib.hellocharts.animation.ChartAnimationListener;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.Chart;
import lecho.lib.hellocharts.view.LineChartView;

public class BsPlotTab extends Fragment{
    public BsPlotTab(){

    }
    public static BsPlotTab newInstance() {
        return new BsPlotTab();
    }
    private LineChartView chart;
    private LineChartData data;
    private static int numberOfLines = 1;
    private static int maxNumberOfLines = 4;
    private static int numberOfPoints ;

    static float[][] randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];

    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLines = true;
    private boolean hasPoints = true;
    private ValueShape shape = ValueShape.CIRCLE;
    private boolean isFilled = false;
    private boolean hasLabels = false;
    private boolean isCubic = false;
    private boolean hasLabelForSelected = false;
    private boolean pointsHaveDifferentColor;
    private boolean hasGradientToTransparent = false;
    public static String memberid;
    private BloodSugar record ;
    RequestQueue requestQueue;
    Context mContext;
    public String url = "http://54.65.194.253/Health_Calendar/getBsRecordDate.php";
    public String url2 = "http://54.65.194.253/Health_Calendar/getBsRecordWeek.php";
    public String url3 = "http://54.65.194.253/Health_Calendar/getBsRecordMonth.php";
    public static String member_id;//getRecord抓的
    public static int userid;
    public static String bloodsugar="";
    public static String savetime="";
    public static int [] bsarray ;
    public static String [] datearray;
    ArrayList<BloodSugar>sugarArrayList;
    private static final String TAG = "FragmentOne";
    FragmentManager fragmentManager;
    TextView showwarn;
    RadioGroup rg;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.tab1_bloodsugar, container, false);
        chart = (LineChartView) rootView.findViewById(R.id.chart);
        chart.setOnValueTouchListener(new ValueTouchListener());
        chart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showwarn.setVisibility(View.INVISIBLE);
                return false;
            }
        });
        final ImageButton warn = (ImageButton) rootView.findViewById(R.id.warn);
        showwarn = (TextView) rootView.findViewById(R.id.showbswarn);
        warn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showwarn.setVisibility(View.VISIBLE);
                showwarn.setText(Html.fromHtml("<b><small><font color=\"#000000\">" + "正常人的血糖值：飯前空腹：70-100 mg/dL，飯後2小時：小於140 mg/dL"+ "</font></small></b>" +"<b><small><font color=\"#FF0000\">" + "糖尿病病人應控制值：飯前空腹：80-130 mg/dL，飯後2小時：小於180 mg/dL" + "</font></small></b>" + "</font>"));
            }
        });
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showwarn.setVisibility(View.INVISIBLE);
                return false;
            }
        });
//        rg = (RadioGroup) getActivity().findViewById(R.id.toggle);
//        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//                switch(checkedId){
//                    case R.id.three:
//                        showwarn.setVisibility(View.INVISIBLE);
//                        getRecord();
//                        toggleFilled();
//                        toggleLabelForSelected();
//                        break;
//                    case R.id.week:
//                        showwarn.setVisibility(View.INVISIBLE);
//                        sugarArrayList = new ArrayList<>();
//                        getRecordWeek();
//                        toggleFilled2();
//                        toggleLabelForSelected2();
//                        break;
//                    case R.id.month:
//                        showwarn.setVisibility(View.INVISIBLE);
//                        sugarArrayList = new ArrayList<>();
//                        getRecordMonth();
//                        toggleFilled3();
//                        toggleLabelForSelected3();
//                        break;
//                    default:
//                        break;
//                }
//
//            }
//        });
        Bundle bundle = this.getArguments();
//        Bundle bundle2 = getActivity().getIntent().getExtras();
        memberid = bundle.getString("memberid");
        Log.d("689","sent id"+memberid);
        sugarArrayList = new ArrayList<>();
        getRecord();
//        generateValues();
//        generateData();
        chart.setViewportCalculationEnabled(false);
        fragmentManager = getFragmentManager();
        setHasOptionsMenu(true);
        return rootView;
    }
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
//        super.onViewCreated(view, savedInstanceState);
//    }
@Override
public void onAttach(Context context) {
    super.onAttach(context);
    mContext = context;
}
    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onDestroyView()
    {
        // TODO Auto-generated method stub
        super.onDestroyView();

//        numberOfPoints = 0;
        numberOfLines = 1;
        Log.e(TAG, "onDestroyView");
    }
    @Override
    public void onDetach(){
        super.onDetach();
        Log.d("4343","do this");
    }



    public void getRecord(){
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Log.d("689","1");
        sugarArrayList = new ArrayList<>();
        final JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.POST, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    Log.d("689", "in response");
                    int counter = 0;
                    try {
//                    JSONArray array = new JSONArray(response);
//                    Log.d("777",array.toString());
                        sugarArrayList = new ArrayList<>();


                        for (int i = 0; i < response.length(); i++) {
                            JSONObject object = response.getJSONObject(i);
                            record = new BloodSugar(object.getInt("id"), object.getString("member_id"), object.getString("bloodsugar"), object.getString("savetime"));


                            userid = object.getInt("id");
                            member_id = object.getString("member_id");
                            Log.d("689", "saw id:" + member_id);
                            if (member_id.equals(memberid)) {
                                sugarArrayList.add(record);
                                bloodsugar = object.getString("bloodsugar");
                                savetime = object.getString("savetime");
                                counter++;
                                bsarray = new int[counter];
                                datearray = new String[counter];

                                Log.d("6899", "member_id:" + member_id);
                                Log.d("6899", "bloodsugar:" + bloodsugar);
                                Log.d("6899", "savetime:" + savetime);


                                numberOfPoints = counter;
                                randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];
                                for (int k = 0; k < maxNumberOfLines; k++) {
                                    for (int j = 0; j < sugarArrayList.size(); j++) {


                                                Log.d("5555", "length:  " + bsarray.length);
                                                bsarray[j] = Integer.parseInt(sugarArrayList.get(j).getBloodsugar());
                                                sugarArrayList.get(j).getBloodsugar();
                                                randomNumbersTab[k][j] = bsarray[j];
                                                Log.d("1345", "bsarray:  " + bsarray[j]);



                                    }
                                }
                            }
                        }

                        System.out.println(Arrays.deepToString(randomNumbersTab).replace("], ", "]\n"));
                        generateData();
                        resetViewport();
                        toggleFilled();
                        toggleLabelForSelected();
                        Log.d("1996", "num:" + numberOfPoints);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    for (int i = 0; i < maxNumberOfLines; ++i) {
                        for (int j = 0; j < numberOfPoints; ++j) {
                            randomNumbersTab[i][j] = (float) Math.random() * 100f;
                        }
                    }
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
    public void getRecordWeek(){
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Log.d("689","1");
        final JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.POST, url2, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    Log.d("689", "in response");
                    int counter = 0;
                    try {
                        sugarArrayList = new ArrayList<>();
//                    JSONArray array = new JSONArray(response);
//                    Log.d("777",array.toString());


                        for (int i = 0; i < response.length(); i++) {
                            JSONObject object = response.getJSONObject(i);
                            record = new BloodSugar(object.getInt("id"), object.getString("member_id"), object.getString("bloodsugar"), object.getString("savetime"));


                            userid = object.getInt("id");
                            member_id = object.getString("member_id");
                            Log.d("689", "saw id:" + member_id);
                            if (member_id.equals(memberid)) {
                                sugarArrayList.add(record);
                                bloodsugar = object.getString("bloodsugar");
                                savetime = object.getString("savetime");
                                counter++;
                                bsarray = new int[counter];
                                datearray = new String[counter];

                                Log.d("6899", "member_id:" + member_id);
                                Log.d("6899", "bloodsugar:" + bloodsugar);
                                Log.d("6899", "savetime:" + savetime);


                                numberOfPoints = counter;
                                randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];
                                for (int k = 0; k < maxNumberOfLines; k++) {
                                    for (int j = 0; j < sugarArrayList.size(); j++) {


                                        Log.d("5555", "length:  " + bsarray.length);
                                        bsarray[j] = Integer.parseInt(sugarArrayList.get(j).getBloodsugar());
                                        sugarArrayList.get(j).getBloodsugar();
                                        randomNumbersTab[k][j] = bsarray[j];
                                        Log.d("1345", "bsarray:  " + bsarray[j]);



                                    }
                                }
                            }
                        }

                        System.out.println(Arrays.deepToString(randomNumbersTab).replace("], ", "]\n"));
                        generateData2();
                        resetViewport();
                        toggleFilled2();
                        toggleLabelForSelected2();
                        Log.d("1996", "num:" + numberOfPoints);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    for (int i = 0; i < maxNumberOfLines; ++i) {
                        for (int j = 0; j < numberOfPoints; ++j) {
                            randomNumbersTab[i][j] = (float) Math.random() * 100f;
                        }
                    }

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
    public void getRecordMonth(){
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Log.d("689","1");
        final JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.POST, url3, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    Log.d("689", "in response");
                    int counter = 0;
                    try {
                        sugarArrayList = new ArrayList<>();
//                    JSONArray array = new JSONArray(response);
//                    Log.d("777",array.toString());


                        for (int i = 0; i < response.length(); i++) {
                            JSONObject object = response.getJSONObject(i);
                            record = new BloodSugar(object.getInt("id"), object.getString("member_id"), object.getString("bloodsugar"), object.getString("savetime"));


                            userid = object.getInt("id");
                            member_id = object.getString("member_id");
                            Log.d("689", "saw id:" + member_id);
                            if (member_id.equals(memberid)) {
                                sugarArrayList.add(record);
                                bloodsugar = object.getString("bloodsugar");
                                savetime = object.getString("savetime");
                                counter++;
                                bsarray = new int[counter];
                                datearray = new String[counter];

                                Log.d("6899", "member_id:" + member_id);
                                Log.d("6899", "bloodsugar:" + bloodsugar);
                                Log.d("6899", "savetime:" + savetime);


                                numberOfPoints = counter;
                                randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];
                                for (int k = 0; k < maxNumberOfLines; k++) {
                                    for (int j = 0; j < sugarArrayList.size(); j++) {


                                        Log.d("5555", "length:  " + bsarray.length);
                                        bsarray[j] = Integer.parseInt(sugarArrayList.get(j).getBloodsugar());
                                        sugarArrayList.get(j).getBloodsugar();
                                        randomNumbersTab[k][j] = bsarray[j];
                                        Log.d("1345", "bsarray:  " + bsarray[j]);



                                    }
                                }
                            }
                        }

                        System.out.println(Arrays.deepToString(randomNumbersTab).replace("], ", "]\n"));
                        generateData3();
                        resetViewport();
                        toggleFilled3();
                        toggleLabelForSelected3();
                        Log.d("1996", "num:" + numberOfPoints);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    for (int i = 0; i < maxNumberOfLines; ++i) {
                        for (int j = 0; j < numberOfPoints; ++j) {
                            randomNumbersTab[i][j] = (float) Math.random() * 100f;
                        }
                    }
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.line_chart, menu);
        rg = (RadioGroup) getActivity().findViewById(R.id.toggle);
        sugarArrayList = new ArrayList<>();
        getRecord();
        toggleFilled();
        toggleLabelForSelected();
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch(checkedId){
                    case R.id.three:
                        showwarn.setVisibility(View.INVISIBLE);
                        getRecord();
                        toggleFilled();
                        toggleLabelForSelected();
                        break;
                    case R.id.week:
                        showwarn.setVisibility(View.INVISIBLE);
                        sugarArrayList = new ArrayList<>();
                        getRecordWeek();
                        toggleFilled2();
                        toggleLabelForSelected2();
                        break;
                    case R.id.month:
                        showwarn.setVisibility(View.INVISIBLE);
                        sugarArrayList = new ArrayList<>();
                        getRecordMonth();
                        toggleFilled3();
                        toggleLabelForSelected3();
                        break;
                    default:
                        break;
                }

            }
        });
        rg.check(R.id.three);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.toolbar :
            {

            }
        }
//        if (id == R.id.action_reset) {
//            reset();
//            generateData();
//            return true;
//        }
//        if (id == R.id.action_add_line) {
//            addLineToData();
//            return true;
//        }
//        if (id == R.id.action_toggle_lines) {
//            toggleLines();
//            return true;
//        }
//        if (id == R.id.action_toggle_points) {
//            togglePoints();
//            return true;
//        }
//        if (id == R.id.action_toggle_gradient) {
//            toggleGradient();
//            return true;
//        }
//        if (id == R.id.action_toggle_cubic) {
//            toggleCubic();
//            return true;
//        }
//        if (id == R.id.action_toggle_area) {
//            toggleFilled();
//            return true;
//        }
//        if (id == R.id.action_point_color) {
//            togglePointColor();
//            return true;
//        }
//        if (id == R.id.action_shape_circles) {
//            setCircles();
//            return true;
//        }
//        if (id == R.id.action_shape_square) {
//            setSquares();
//            return true;
//        }
//        if (id == R.id.action_shape_diamond) {
//            setDiamonds();
//            return true;
//        }
//        if (id == R.id.action_toggle_labels) {
//            toggleLabels();
//            return true;
//        }
//        if (id == R.id.action_toggle_axes) {
//            toggleAxes();
//            return true;
//        }
//        if (id == R.id.action_toggle_axes_names) {
//            toggleAxesNames();
//            return true;
//        }
//        if (id == R.id.action_animate) {
//            prepareDataAnimation();
//            chart.startDataAnimation();
//            return true;
//        }
//        if (id == R.id.action_toggle_selection_mode) {
//            toggleLabelForSelected();
//
//            Toast.makeText(getActivity(),
//                    "Selection mode set to " + chart.isValueSelectionEnabled() + " select any point.",
//                    Toast.LENGTH_SHORT).show();
//            return true;
//        }
//        if (id == R.id.action_toggle_touch_zoom) {
//            chart.setZoomEnabled(!chart.isZoomEnabled());
//            Toast.makeText(getActivity(), "IsZoomEnabled " + chart.isZoomEnabled(), Toast.LENGTH_SHORT).show();
//            return true;
//        }
//        if (id == R.id.action_zoom_both) {
//            chart.setZoomType(ZoomType.HORIZONTAL_AND_VERTICAL);
//            return true;
//        }
//        if (id == R.id.action_zoom_horizontal) {
//            chart.setZoomType(ZoomType.HORIZONTAL);
//            return true;
//        }
//        if (id == R.id.action_zoom_vertical) {
//            chart.setZoomType(ZoomType.VERTICAL);
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    private void generateValues() {
        for (int i = 0; i < maxNumberOfLines; ++i) {
            for (int j = 0; j < numberOfPoints; ++j) {
                randomNumbersTab[i][j] = (float) Math.random() * 100f;
                System.out.println(Arrays.deepToString(randomNumbersTab).replace("], ", "]\n"));
            }
        }
    }
    private void reset() {
        numberOfLines = 1;

        hasAxes = true;
        hasAxesNames = true;
        hasLines = true;
        hasPoints = true;
        shape = ValueShape.CIRCLE;
        isFilled = false;
        hasLabels = false;
        isCubic = false;
        hasLabelForSelected = false;
        pointsHaveDifferentColor = false;

        chart.setValueSelectionEnabled(hasLabelForSelected);
        resetViewport();
    }
    private void resetViewport() {
        // Reset viewport height range to (0,100)
        final Viewport v = new Viewport(chart.getMaximumViewport());
        v.bottom = 0;
        v.top = 180;
        v.left = 0;
        v.right = numberOfPoints - 1;
        chart.setMaximumViewport(v);
        chart.setCurrentViewport(v);
    }
    private void generateData() {
        if(numberOfPoints==0){
            Toast.makeText(getActivity(),"您3日內尚未新增血糖相關紀錄哦！趕快去新增吧！",Toast.LENGTH_LONG).show();
        }

            List<Line> lines = new ArrayList<Line>();
            for (int i = 0; i < numberOfLines; ++i) {

                List<PointValue> values = new ArrayList<PointValue>();
                for (int j = 0; j < numberOfPoints; ++j) {
                    Log.d("2223", "points" + numberOfPoints);
                    values.add(new PointValue(j, randomNumbersTab[i][j]));
                }
                Log.d("5566", "values: " + values);

                Line line = new Line(values);
                line.setColor(ChartUtils.COLORS[i]);
                line.setShape(shape);
                line.setCubic(isCubic);
                line.setFilled(isFilled);
                line.setHasLabels(hasLabels);
                line.setHasLabelsOnlyForSelected(hasLabelForSelected);
                line.setHasLines(hasLines);
                line.setHasPoints(hasPoints);
//            line.setHasGradientToTransparent(hasGradientToTransparent);
                if (pointsHaveDifferentColor) {
                    line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
                }
                lines.add(line);
            }

            data = new LineChartData(lines);

            if (hasAxes) {
                Axis axisX = new Axis();
                Axis axisY = new Axis().setHasLines(true);
                if (hasAxesNames) {
                    axisX.setName("3日內變化");
                    axisX.setTextColor(Color.BLACK);
                    axisY.setName("血糖  BPM");
                    axisY.setTextColor(Color.BLACK);
                }
                data.setAxisXBottom(axisX);
                data.setAxisYLeft(axisY);
            } else {
                data.setAxisXBottom(null);
                data.setAxisYLeft(null);
            }
            data.setBaseValue(Float.NEGATIVE_INFINITY);
            chart.setLineChartData(data);

    }
    private void generateData2() {
//        if(numberOfPoints==0){
//            Toast.makeText(getActivity(),"您7日內尚未新增血糖相關紀錄哦！趕快去新增吧！",Toast.LENGTH_LONG).show();
//        }

            List<Line> lines = new ArrayList<Line>();
            for (int i = 0; i < numberOfLines; ++i) {

                List<PointValue> values = new ArrayList<PointValue>();
                for (int j = 0; j < numberOfPoints; ++j) {
                    Log.d("2223", "points" + numberOfPoints);
                    values.add(new PointValue(j, randomNumbersTab[i][j]));
                }
                Log.d("5566", "values: " + values);

                Line line = new Line(values);
                line.setColor(ChartUtils.COLORS[i]);
                line.setShape(shape);
                line.setCubic(isCubic);
                line.setFilled(isFilled);
                line.setHasLabels(hasLabels);
                line.setHasLabelsOnlyForSelected(hasLabelForSelected);
                line.setHasLines(hasLines);
                line.setHasPoints(hasPoints);
//            line.setHasGradientToTransparent(hasGradientToTransparent);
                if (pointsHaveDifferentColor) {
                    line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
                }
                lines.add(line);
            }

            data = new LineChartData(lines);

            if (hasAxes) {
                Axis axisX = new Axis();
                Axis axisY = new Axis().setHasLines(true);
                if (hasAxesNames) {
                    axisX.setName("7天內變化");
                    axisX.setTextColor(Color.BLACK);
                    axisY.setName("血糖  BPM");
                    axisY.setTextColor(Color.BLACK);
                }
                data.setAxisXBottom(axisX);
                data.setAxisYLeft(axisY);
            } else {
                data.setAxisXBottom(null);
                data.setAxisYLeft(null);
            }
            data.setBaseValue(Float.NEGATIVE_INFINITY);
            chart.setLineChartData(data);

    }
    private void generateData3() {
//        if(numberOfPoints==0){
//            Toast.makeText(getActivity(),"您30日內尚未新增血糖相關紀錄哦！趕快去新增吧！",Toast.LENGTH_LONG).show();
//        }

            List<Line> lines = new ArrayList<Line>();
            for (int i = 0; i < numberOfLines; ++i) {

                List<PointValue> values = new ArrayList<PointValue>();
                for (int j = 0; j < numberOfPoints; ++j) {
                    Log.d("2223", "points" + numberOfPoints);
                    values.add(new PointValue(j, randomNumbersTab[i][j]));
                }
                Log.d("5566", "values: " + values);

                Line line = new Line(values);
                line.setColor(ChartUtils.COLORS[i]);
                line.setShape(shape);
                line.setCubic(isCubic);
                line.setFilled(isFilled);
                line.setHasLabels(hasLabels);
                line.setHasLabelsOnlyForSelected(hasLabelForSelected);
                line.setHasLines(hasLines);
                line.setHasPoints(hasPoints);
//            line.setHasGradientToTransparent(hasGradientToTransparent);
                if (pointsHaveDifferentColor) {
                    line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
                }
                lines.add(line);
            }

            data = new LineChartData(lines);

            if (hasAxes) {
                Axis axisX = new Axis();
                Axis axisY = new Axis().setHasLines(true);
                if (hasAxesNames) {
                    axisX.setName("30天內變化");
                    axisX.setTextColor(Color.BLACK);
                    axisY.setName("血糖  BPM");
                    axisY.setTextColor(Color.BLACK);
                }
                data.setAxisXBottom(axisX);
                data.setAxisYLeft(axisY);
            } else {
                data.setAxisXBottom(null);
                data.setAxisYLeft(null);
            }
            data.setBaseValue(Float.NEGATIVE_INFINITY);
            chart.setLineChartData(data);

    }
    private void addLineToData() {
        if (data.getLines().size() >= maxNumberOfLines) {
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

    private void toggleGradient() {
        hasGradientToTransparent = !hasGradientToTransparent;

        generateData();
    }
    private void toggleCubic() {
        isCubic = !isCubic;

        generateData();

        if (isCubic) {
            // It is good idea to manually set a little higher max viewport for cubic lines because sometimes line
            // go above or below max/min. To do that use Viewport.inest() method and pass negative value as dy
            // parameter or just set top and bottom values manually.
            // In this example I know that Y values are within (0,100) range so I set viewport height range manually
            // to (-5, 105).
            // To make this works during animations you should use Chart.setViewportCalculationEnabled(false) before
            // modifying viewport.
            // Remember to set viewport after you call setLineChartData().
            final Viewport v = new Viewport(chart.getMaximumViewport());
            v.bottom = -5;
            v.top = 105;
            // You have to set max and current viewports separately.
            chart.setMaximumViewport(v);
            // I changing current viewport with animation in this case.
            chart.setCurrentViewportWithAnimation(v);
        } else {
            // If not cubic restore viewport to (0,100) range.
            final Viewport v = new Viewport(chart.getMaximumViewport());
            v.bottom = 0;
            v.top = 100;

            // You have to set max and current viewports separately.
            // In this case, if I want animation I have to set current viewport first and use animation listener.
            // Max viewport will be set in onAnimationFinished method.
            chart.setViewportAnimationListener(new ChartAnimationListener() {

                @Override
                public void onAnimationStarted() {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAnimationFinished() {
                    // Set max viewpirt and remove listener.
                    chart.setMaximumViewport(v);
                    chart.setViewportAnimationListener(null);

                }
            });
            // Set current viewpirt with animation;
            chart.setCurrentViewportWithAnimation(v);
        }

    }
    private void toggleFilled() {
        isFilled = !isFilled;

        generateData();
    }
    private void toggleFilled2() {
        isFilled = !isFilled;

        generateData2();
    }
    private void toggleFilled3() {
        isFilled = !isFilled;

        generateData3();
    }

    private void togglePointColor() {
        pointsHaveDifferentColor = !pointsHaveDifferentColor;

        generateData();
    }

    private void setCircles() {
        shape = ValueShape.CIRCLE;

        generateData();
    }

    private void setSquares() {
        shape = ValueShape.SQUARE;

        generateData();
    }

    private void setDiamonds() {
        shape = ValueShape.DIAMOND;

        generateData();
    }

    private void toggleLabels() {
        hasLabels = !hasLabels;

        if (hasLabels) {
            hasLabelForSelected = false;
            chart.setValueSelectionEnabled(hasLabelForSelected);
        }

        generateData();
    }

    private void toggleLabelForSelected() {
        hasLabelForSelected = !hasLabelForSelected;

        chart.setValueSelectionEnabled(hasLabelForSelected);

        if (hasLabelForSelected) {
            hasLabels = false;
        }

        generateData();
    }
    private void toggleLabelForSelected2() {
        hasLabelForSelected = !hasLabelForSelected;

        chart.setValueSelectionEnabled(hasLabelForSelected);

        if (hasLabelForSelected) {
            hasLabels = false;
        }

        generateData2();
    }
    private void toggleLabelForSelected3() {
        hasLabelForSelected = !hasLabelForSelected;

        chart.setValueSelectionEnabled(hasLabelForSelected);

        if (hasLabelForSelected) {
            hasLabels = false;
        }

        generateData3();
    }

    private void toggleAxes() {
        hasAxes = !hasAxes;

        generateData();
    }

    private void toggleAxesNames() {
        hasAxesNames = !hasAxesNames;

        generateData();
    }

    /**
     * To animate values you have to change targets values and then call {@link Chart#startDataAnimation()}
     * method(don't confuse with View.animate()). If you operate on data that was set before you don't have to call
     * {@link LineChartView#setLineChartData(LineChartData)} again.
     */
    private void prepareDataAnimation() {
        for (Line line : data.getLines()) {
            for (PointValue value : line.getValues()) {
                // Here I modify target only for Y values but it is OK to modify X targets as well.
                value.setTarget(value.getX(), (float) Math.random() * 100);
            }
        }
    }
    private class ValueTouchListener implements LineChartOnValueSelectListener {

        @Override
        public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
            Toast.makeText(getActivity(), "Selected: " + value.getY(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }

    public void goback(View v)
    {
        getActivity().onBackPressed();
        Log.d("1234","do this");
    }

}
