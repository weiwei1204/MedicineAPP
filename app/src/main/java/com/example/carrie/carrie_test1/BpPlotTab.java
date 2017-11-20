package com.example.carrie.carrie_test1;

/**
 * Created by jonathan on 2017/8/6.
 */
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
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
import java.util.List;

import lecho.lib.hellocharts.listener.ComboLineColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.ComboLineColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ComboLineColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;

public class BpPlotTab extends Fragment{
    public BpPlotTab(){

    }
    public static BpPlotTab newInstance() {
        return new BpPlotTab();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private ComboLineColumnChartView chart;
    private ComboLineColumnChartData data;
    private LineChartView lineChartView;
    private LineChartData linedata;
    LinearLayout linearLayout;
    TextView plotdetail;
    View rootView;
    TextView highwarn;
    TextView lowwarn;
    TextView heartwarn;
    TextView compare;
    ImageButton warning;
    RadioGroup radiogroup;

    private static int numberOfLines = 1;
    private static int maxNumberOfLines = 4;
    private static int numberOfPoints ;

    static float[][] randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];
    static float[][] randomNumbersTab2 = new float[maxNumberOfLines][numberOfPoints];
    static float[][] randomNumbersTab3 = new float[maxNumberOfLines][numberOfPoints];

    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasPoints = true;
    private boolean hasLines = true;
    private boolean isCubic = false;
    private boolean hasLabels = false;
    private ValueShape shape = ValueShape.CIRCLE;
    private boolean isFilled = false;
    private boolean hasLabelForSelected = false;
    private boolean pointsHaveDifferentColor;
    public int menu;
    public String url = "http://54.65.194.253/Health_Calendar/getBpRecordDate.php";
    public String url2 = "http://54.65.194.253/Health_Calendar/getBpRecordWeek.php";
    public String url3 = "http://54.65.194.253/Health_Calendar/getBpRecordMonth.php";
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
    private static int count;
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
    ArrayList harr = new ArrayList<Integer>();
    FragmentManager manager;
    private static final String TAG = "FragmentOne";
    Context mContext;

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        rootView = inflater.inflate(R.layout.tab2_bloodpressure, container, false);
        lineChartView = (LineChartView) rootView.findViewById(R.id.chart2);
        plotdetail = (TextView) rootView.findViewById(R.id.section_label) ;
        plotdetail.setVisibility(View.INVISIBLE);
//        linearLayout =  (LinearLayout) rootView.findViewById(R.id.ly);
        warning = (ImageButton) rootView.findViewById(R.id.warn);
        highwarn = (TextView) rootView.findViewById(R.id.showbphighwarn);
        lowwarn = (TextView) rootView.findViewById(R.id.showbplowwarn);
        heartwarn = (TextView) rootView.findViewById(R.id.showbpheartwarn);
        compare = (TextView) rootView.findViewById(R.id.compare);
        warning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highwarn.setVisibility(View.VISIBLE);
                highwarn.setText(Html.fromHtml("<b><font color=\"#FF0000\">" + "收縮壓(上壓) mmHg" + "</font></b>" +"<b><small><font color=\"#000000\">" + "正常血壓：90 - 119 mmhg，正常高值：120 – 139 mmhg，"+ "</font></small></b>" +"<b><small><font color=\"#FF0000\">" + "1期高血壓：140 - 159 mmhg，低血壓：< 90 mmhg" + "</font></small></b>" + "</font>"));
            }
        });
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                highwarn.setVisibility(View.INVISIBLE);
                lowwarn.setVisibility(View.INVISIBLE);
                heartwarn.setVisibility(View.INVISIBLE);
                compare.setVisibility(View.INVISIBLE);
                return false;
            }
        });
        lineChartView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                highwarn.setVisibility(View.INVISIBLE);
                lowwarn.setVisibility(View.INVISIBLE);
                heartwarn.setVisibility(View.INVISIBLE);
                compare.setVisibility(View.INVISIBLE);
                return false;
            }
        });
        lineChartView.setOnValueTouchListener(new LineChartOnValueSelectListener() {
            @Override
            public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
                Toast.makeText(getActivity(), "Selected: " + value.getY(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onValueDeselected() {

            }
        });
        rootView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

            }
        });

//        radiogroup = (RadioGroup) getActivity().findViewById(R.id.toggle);
//        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//                switch(checkedId){
//                    case R.id.three:
//                        highwarn.setVisibility(View.INVISIBLE);
//                        lowwarn.setVisibility(View.INVISIBLE);
//                        heartwarn.setVisibility(View.INVISIBLE);
//                        compare.setVisibility(View.INVISIBLE);
//                        data_list = new ArrayList<>();
//                        getRecord();
//
//                        toggleFilled();
//                        toggleLabelForSelected();
//                        break;
//                    case R.id.week:
//                        highwarn.setVisibility(View.INVISIBLE);
//                        lowwarn.setVisibility(View.INVISIBLE);
//                        heartwarn.setVisibility(View.INVISIBLE);
//                        compare.setVisibility(View.INVISIBLE);
//                        data_list = new ArrayList<>();
//                        getRecordWeek();
//
//                        toggleFilled();
//                        toggleLabelForSelected();
//                        break;
//                    case R.id.month:
//                        highwarn.setVisibility(View.INVISIBLE);
//                        lowwarn.setVisibility(View.INVISIBLE);
//                        heartwarn.setVisibility(View.INVISIBLE);
//                        compare.setVisibility(View.INVISIBLE);
//                        data_list= new ArrayList<>();
//                        getRecordMonth();
//
//                        toggleFilled();
//                        toggleLabelForSelected();
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
        data_list = new ArrayList<>();
        getRecord();
        Bundle bundle = this.getArguments();
        sentmember_id = bundle.getString("memberid");
        int[]arr = bundle.getIntArray("high");
        harr = bundle.getIntegerArrayList("higharr");
        Log.d("7676","arr:  "+harr);
        manager = getFragmentManager();
        Log.d("9999","memberid:"+sentmember_id);
        Log.d("9999","arr:  "+Arrays.toString(arr));
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
        setHasOptionsMenu(true);
        return rootView;

        }
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
        numberOfPoints= 0;
        numberOfLines = 1;
        Log.e(TAG, "onDestroyView");

    }

    //onCreateView區域在上面
        @Override
        public void onDetach(){
            super.onDetach();
            Log.d("4343","do this");
        }



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
    ///////////////////////////////////////////////////////////////////////4日內圖
    public void getRecord(){
        Log.d("777","in method");
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Log.d("777","1");
        final JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.POST, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                    Log.d("777", "in response");
                    int count = 0;
                    try {
                        data_list = new ArrayList<>();
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
                            Log.d("1234", "saw id:" + member_id);
                            if (member_id.equals(sentmember_id)) {
                                data_list.add(record);
                                usrhighmmhg = object.getString("highmmhg");
                                usrlowmmhg = object.getString("lowmmhg");
                                usrbpm = object.getString("bpm");
                                usrsavetime = object.getString("savetime");

                                Log.d("6969", "member_id:" + member_id);
                                Log.d("6969", "highmmhg:" + usrhighmmhg);
                                Log.d("6969", "lowmmhg:" + usrlowmmhg);
                                Log.d("6969", "bpm:" + usrbpm);
                                Log.d("9999", "savetime:" + usrsavetime);

                                count++;

                                highvaluearray = new int[count];
                                lowvaluearray = new int[count];
                                bpmvaluearray = new int[count];
                                datearray = new String[count];

                                numberOfPoints = count;
                                randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];


                                for (int k = 0; k < maxNumberOfLines; k++) {
                                    Log.d("9996", "number of lines:" + maxNumberOfLines);

                                    for (int j = 0; j < data_list.size(); j++) {
                                        Log.d("9996", "number of points:" + numberOfPoints);


                                            highvaluearray[j] = Integer.parseInt(data_list.get(j).getHighmmhg());
                                            Log.d("7654", "array:  " + highvaluearray[j]);
                                            Log.d("7654", "length:  " + highvaluearray.length);

                                            randomNumbersTab[k][j] = highvaluearray[j];



//                                    System.out.print(+randomNumbersTab[k][j]+" ");
//                                    System.out.println();
//                                    System.out.println(Arrays.deepToString(randomNumbersTab).replace("], ", "]\n"));
                                    }
                                }
                            }
                        }

//                        generateLineData();
//                        generateData();
                        toggleLabelForSelected();
                        toggleFilled();
                        generatelineData();
                        resetViewport();

                        System.out.println(Arrays.deepToString(randomNumbersTab).replace("], ", "]\n"));
                        Log.d("9995", "num:" + numberOfPoints);
                        Log.d("8721", "count:" + count);

//                    Log.d("9995","higharray"+highvaluearray[count]);

//                  generateValues();
//                    generateData();
                    } catch (JSONException e) {
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
    public void getRecord2(){
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Log.d("777","1");
        final JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.POST, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("777", "in response");
                int counter = 0;
                try {
                    data_list = new ArrayList<>();
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
                        Log.d("1234", "saw id:" + member_id);
                        if (member_id.equals(sentmember_id)) {
                            data_list.add(record);
                            usrhighmmhg = object.getString("highmmhg");
                            usrlowmmhg = object.getString("lowmmhg");
                            usrbpm = object.getString("bpm");
                            usrsavetime = object.getString("savetime");

                            Log.d("6969", "member_id:" + member_id);
                            Log.d("6969", "highmmhg:" + usrhighmmhg);
                            Log.d("6969", "lowmmhg:" + usrlowmmhg);
                            Log.d("6969", "bpm:" + usrbpm);
                            Log.d("9999", "savetime:" + usrsavetime);

                            counter++;

                            highvaluearray = new int[counter];
                            lowvaluearray = new int[counter];
                            bpmvaluearray = new int[counter];
                            datearray = new String[counter];

                            numberOfPoints = counter;
                            randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];


                            for (int k = 0; k < maxNumberOfLines; k++) {
                                Log.d("9996", "number of lines:" + maxNumberOfLines);

                                for (int j = 0; j < data_list.size(); j++) {
                                    Log.d("9996", "number of points:" + numberOfPoints);


                                    lowvaluearray[j] = Integer.parseInt(data_list.get(j).getLowmmhg());
                                    Log.d("7654", "array:  " + lowvaluearray[j]);
                                    Log.d("7654", "length:  " + lowvaluearray.length);

                                    randomNumbersTab[k][j] = lowvaluearray[j];



//                                    System.out.print(+randomNumbersTab[k][j]+" ");
//                                    System.out.println();
//                                    System.out.println(Arrays.deepToString(randomNumbersTab).replace("], ", "]\n"));
                                }
                            }
                        }
                    }
                    toggleLabelForSelected();
                    toggleFilled();
                    generateline1Data();
                    resetViewport();


//                    generateLineData();
//                    data = new ComboLineColumnChartData(generateColumnData2(), generateLineData());
//                    if (hasAxes) {
//                        Axis axisX = new Axis();
//                        Axis axisY = new Axis().setHasLines(true);
//                        if (hasAxesNames) {
//                            axisX.setName("3日內變化");
//                            axisX.setTextColor(Color.BLACK);
//                            axisY.setName("舒張壓  mmhg");
//                            axisY.setTextColor(Color.BLACK);
//                        }
//                        data.setAxisXBottom(axisX);
//                        data.setAxisYLeft(axisY);
//                    } else {
//                        data.setAxisXBottom(null);
//                        data.setAxisYLeft(null);
//                    }
//
//                    chart.setComboLineColumnChartData(data);
                    System.out.println(Arrays.deepToString(randomNumbersTab).replace("], ", "]\n"));
                    Log.d("9995", "num:" + numberOfPoints);
                    Log.d("8721", "count:" + counter);

//                    Log.d("9995","higharray"+highvaluearray[count]);

//                  generateValues();
//                    generateData();
                } catch (JSONException e) {
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
    public void getRecord3(){
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Log.d("777","1");
        final JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.POST, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("777", "in response");
                int counting = 0;
                try {
                    data_list = new ArrayList<>();
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
                        Log.d("1234", "saw id:" + member_id);
                        if (member_id.equals(sentmember_id)) {
                            data_list.add(record);
                            usrhighmmhg = object.getString("highmmhg");
                            usrlowmmhg = object.getString("lowmmhg");
                            usrbpm = object.getString("bpm");
                            usrsavetime = object.getString("savetime");

                            Log.d("6969", "member_id:" + member_id);
                            Log.d("6969", "highmmhg:" + usrhighmmhg);
                            Log.d("6969", "lowmmhg:" + usrlowmmhg);
                            Log.d("6969", "bpm:" + usrbpm);
                            Log.d("9999", "savetime:" + usrsavetime);

                            counting++;

                            highvaluearray = new int[counting];
                            lowvaluearray = new int[counting];
                            bpmvaluearray = new int[counting];
                            datearray = new String[counting];

                            numberOfPoints = counting;
                            randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];


                            for (int k = 0; k < maxNumberOfLines; k++) {
                                Log.d("9996", "number of lines:" + maxNumberOfLines);

                                for (int j = 0; j < data_list.size(); j++) {
                                    Log.d("9996", "number of points:" + numberOfPoints);


                                    bpmvaluearray[j] = Integer.parseInt(data_list.get(j).getBpm());
                                    Log.d("5666", "array:  " + bpmvaluearray[j]);
                                    Log.d("5666", "length:  " + bpmvaluearray.length);

                                    randomNumbersTab[k][j] = bpmvaluearray[j];



//                                    System.out.print(+randomNumbersTab[k][j]+" ");
//                                    System.out.println();
//                                    System.out.println(Arrays.deepToString(randomNumbersTab).replace("], ", "]\n"));
                                }
                            }
                        }
                    }
                    toggleLabelForSelected();
                    toggleFilled();
                    generateline2Data();
                    resetViewport();



//                    generateLineData();
//                    data = new ComboLineColumnChartData(generateColumnData3(), generateLineData());
//                    if (hasAxes) {
//                        Axis axisX = new Axis();
//                        Axis axisY = new Axis().setHasLines(true);
//                        if (hasAxesNames) {
//                            axisX.setName("3日內變化");
//                            axisX.setTextColor(Color.BLACK);
//                            axisY.setName("心跳  bpm");
//                            axisY.setTextColor(Color.BLACK);
//                        }
//                        data.setAxisXBottom(axisX);
//                        data.setAxisYLeft(axisY);
//                    } else {
//                        data.setAxisXBottom(null);
//                        data.setAxisYLeft(null);
//                    }
//
//                    chart.setComboLineColumnChartData(data);
                    System.out.println(Arrays.deepToString(randomNumbersTab).replace("], ", "]\n"));
                    Log.d("9995", "num:" + numberOfPoints);
                    Log.d("8721", "count:" + counting);

//                    Log.d("9995","higharray"+highvaluearray[count]);

//                  generateValues();
//                    generateData();
                } catch (JSONException e) {
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
    public void compare(){
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.POST, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int counting = 0;
                try {
                    rd = true;
                    data_list = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        record = new BloodPressure(object.getInt("id"), object.getString("member_id"), object.getString("highmmhg"), object.getString("lowmmhg"), object.getString("bpm"), object.getString("savetime"));
                        userid = object.getInt("id");
                        member_id = object.getString("member_id");
                        if (member_id.equals(sentmember_id)) {
                            data_list.add(record);
                            usrhighmmhg = object.getString("highmmhg");
                            usrlowmmhg = object.getString("lowmmhg");
                            usrbpm = object.getString("bpm");
                            usrsavetime = object.getString("savetime");
                            counting++;
                            highvaluearray = new int[counting];
                            lowvaluearray = new int[counting];
                            bpmvaluearray = new int[counting];
                            datearray = new String[counting];

                            numberOfPoints = counting;
                            randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];
                            randomNumbersTab2 = new float[maxNumberOfLines][numberOfPoints];
                            randomNumbersTab3 = new float[maxNumberOfLines][numberOfPoints];


                            for (int k = 0; k < maxNumberOfLines; k++) {
                                for (int j = 0; j < data_list.size(); j++) {
                                    Log.d("9996", "number of points:" + numberOfPoints);
                                    highvaluearray[j] = Integer.parseInt(data_list.get(j).getHighmmhg());
                                    lowvaluearray[j] = Integer.parseInt(data_list.get(j).getLowmmhg());
                                    bpmvaluearray[j] = Integer.parseInt(data_list.get(j).getBpm());
                                    randomNumbersTab[k][j] = highvaluearray[j];
                                    randomNumbersTab2[k][j] = lowvaluearray[j];
                                    randomNumbersTab3[k][j] = bpmvaluearray[j];
                                }
                            }
                            maxNumberOfLines++;
                            generateline3Data();

                        }
                    }
                    toggleLabelForSelected();
                    resetViewport();
                    System.out.println(Arrays.deepToString(randomNumbersTab).replace("], ", "]\n"));
                    Log.d("9995", "num:" + numberOfPoints);
                    Log.d("8721", "count:" + counting);
                } catch (JSONException e) {
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
    //////////////////////////////////////////////////////////////////////7天內圖
    public void getRecordWeek(){
        Log.d("777","in method");
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Log.d("777","1");
        final JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.POST, url2, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("777", "in response");
                int count = 0;
                try {
                    data_list = new ArrayList<>();
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
                        Log.d("1234", "saw id:" + member_id);
                        if (member_id.equals(sentmember_id)) {
                            data_list.add(record);
                            usrhighmmhg = object.getString("highmmhg");
                            usrlowmmhg = object.getString("lowmmhg");
                            usrbpm = object.getString("bpm");
                            usrsavetime = object.getString("savetime");

                            Log.d("6969", "member_id:" + member_id);
                            Log.d("6969", "highmmhg:" + usrhighmmhg);
                            Log.d("6969", "lowmmhg:" + usrlowmmhg);
                            Log.d("6969", "bpm:" + usrbpm);
                            Log.d("9999", "savetime:" + usrsavetime);

                            count++;

                            highvaluearray = new int[count];
                            lowvaluearray = new int[count];
                            bpmvaluearray = new int[count];
                            datearray = new String[count];

                            numberOfPoints = count;
                            randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];


                            for (int k = 0; k < maxNumberOfLines; k++) {
                                Log.d("9996", "number of lines:" + maxNumberOfLines);

                                for (int j = 0; j < data_list.size(); j++) {
                                    Log.d("9996", "number of points:" + numberOfPoints);


                                    highvaluearray[j] = Integer.parseInt(data_list.get(j).getHighmmhg());
                                    Log.d("7654", "array:  " + highvaluearray[j]);
                                    Log.d("7654", "length:  " + highvaluearray.length);

                                    randomNumbersTab[k][j] = highvaluearray[j];



//                                    System.out.print(+randomNumbersTab[k][j]+" ");
//                                    System.out.println();
//                                    System.out.println(Arrays.deepToString(randomNumbersTab).replace("], ", "]\n"));
                                }
                            }
                        }
                    }

//                        generateLineData();
//                        generateData();
                    toggleLabelForSelected();
                    toggleFilled();
                    generatelineDataWeek();
                    resetViewport();

                    System.out.println(Arrays.deepToString(randomNumbersTab).replace("], ", "]\n"));
                    Log.d("9995", "num:" + numberOfPoints);
                    Log.d("8721", "count:" + count);

//                    Log.d("9995","higharray"+highvaluearray[count]);

//                  generateValues();
//                    generateData();
                } catch (JSONException e) {
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
    public void getRecordWeek2(){
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Log.d("777","1");
        final JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.POST, url2, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("777", "in response");
                int counter = 0;
                try {
                    data_list = new ArrayList<>();
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
                        Log.d("1234", "saw id:" + member_id);
                        if (member_id.equals(sentmember_id)) {
                            data_list.add(record);
                            usrhighmmhg = object.getString("highmmhg");
                            usrlowmmhg = object.getString("lowmmhg");
                            usrbpm = object.getString("bpm");
                            usrsavetime = object.getString("savetime");

                            Log.d("6969", "member_id:" + member_id);
                            Log.d("6969", "highmmhg:" + usrhighmmhg);
                            Log.d("6969", "lowmmhg:" + usrlowmmhg);
                            Log.d("6969", "bpm:" + usrbpm);
                            Log.d("9999", "savetime:" + usrsavetime);

                            counter++;

                            highvaluearray = new int[counter];
                            lowvaluearray = new int[counter];
                            bpmvaluearray = new int[counter];
                            datearray = new String[counter];

                            numberOfPoints = counter;
                            randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];


                            for (int k = 0; k < maxNumberOfLines; k++) {
                                Log.d("9996", "number of lines:" + maxNumberOfLines);

                                for (int j = 0; j < data_list.size(); j++) {
                                    Log.d("9996", "number of points:" + numberOfPoints);


                                    lowvaluearray[j] = Integer.parseInt(data_list.get(j).getLowmmhg());
                                    Log.d("7654", "array:  " + lowvaluearray[j]);
                                    Log.d("7654", "length:  " + lowvaluearray.length);

                                    randomNumbersTab[k][j] = lowvaluearray[j];



//                                    System.out.print(+randomNumbersTab[k][j]+" ");
//                                    System.out.println();
//                                    System.out.println(Arrays.deepToString(randomNumbersTab).replace("], ", "]\n"));
                                }
                            }
                        }
                    }
                    toggleLabelForSelected();
                    generateline1DataWeek();
                    resetViewport();
                    System.out.println(Arrays.deepToString(randomNumbersTab).replace("], ", "]\n"));
                    Log.d("9995", "num:" + numberOfPoints);
                    Log.d("8721", "count:" + counter);

//                    Log.d("9995","higharray"+highvaluearray[count]);

//                  generateValues();
//                    generateData();
                } catch (JSONException e) {
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
    public void getRecordWeek3(){
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Log.d("777","1");
        final JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.POST, url2, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("777", "in response");
                int counting = 0;
                try {
                    data_list = new ArrayList<>();
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
                        Log.d("1234", "saw id:" + member_id);
                        if (member_id.equals(sentmember_id)) {
                            data_list.add(record);
                            usrhighmmhg = object.getString("highmmhg");
                            usrlowmmhg = object.getString("lowmmhg");
                            usrbpm = object.getString("bpm");
                            usrsavetime = object.getString("savetime");

                            Log.d("6969", "member_id:" + member_id);
                            Log.d("6969", "highmmhg:" + usrhighmmhg);
                            Log.d("6969", "lowmmhg:" + usrlowmmhg);
                            Log.d("6969", "bpm:" + usrbpm);
                            Log.d("9999", "savetime:" + usrsavetime);

                            counting++;

                            highvaluearray = new int[counting];
                            lowvaluearray = new int[counting];
                            bpmvaluearray = new int[counting];
                            datearray = new String[counting];

                            numberOfPoints = counting;
                            randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];


                            for (int k = 0; k < maxNumberOfLines; k++) {
                                Log.d("9996", "number of lines:" + maxNumberOfLines);

                                for (int j = 0; j < data_list.size(); j++) {
                                    Log.d("9996", "number of points:" + numberOfPoints);


                                    bpmvaluearray[j] = Integer.parseInt(data_list.get(j).getBpm());
                                    Log.d("5666", "array:  " + bpmvaluearray[j]);
                                    Log.d("5666", "length:  " + bpmvaluearray.length);

                                    randomNumbersTab[k][j] = bpmvaluearray[j];



//                                    System.out.print(+randomNumbersTab[k][j]+" ");
//                                    System.out.println();
//                                    System.out.println(Arrays.deepToString(randomNumbersTab).replace("], ", "]\n"));
                                }
                            }
                        }
                    }
                    toggleLabelForSelected();
                    generateline2DataWeek();
                    resetViewport();
                    System.out.println(Arrays.deepToString(randomNumbersTab).replace("], ", "]\n"));
                    Log.d("9995", "num:" + numberOfPoints);
                    Log.d("8721", "count:" + counting);

//                    Log.d("9995","higharray"+highvaluearray[count]);

//                  generateValues();
//                    generateData();
                } catch (JSONException e) {
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
    public void compareWeek(){
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.POST, url2, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int counting = 0;
                try {
                    data_list = new ArrayList<>();
                    rd = true;
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        record = new BloodPressure(object.getInt("id"), object.getString("member_id"), object.getString("highmmhg"), object.getString("lowmmhg"), object.getString("bpm"), object.getString("savetime"));
                        userid = object.getInt("id");
                        member_id = object.getString("member_id");
                        if (member_id.equals(sentmember_id)) {
                            data_list.add(record);
                            usrhighmmhg = object.getString("highmmhg");
                            usrlowmmhg = object.getString("lowmmhg");
                            usrbpm = object.getString("bpm");
                            usrsavetime = object.getString("savetime");
                            counting++;
                            highvaluearray = new int[counting];
                            lowvaluearray = new int[counting];
                            bpmvaluearray = new int[counting];
                            datearray = new String[counting];

                            numberOfPoints = counting;
                            randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];
                            randomNumbersTab2 = new float[maxNumberOfLines][numberOfPoints];
                            randomNumbersTab3 = new float[maxNumberOfLines][numberOfPoints];


                            for (int k = 0; k < maxNumberOfLines; k++) {
                                for (int j = 0; j < data_list.size(); j++) {
                                    Log.d("9996", "number of points:" + numberOfPoints);
                                    highvaluearray[j] = Integer.parseInt(data_list.get(j).getHighmmhg());
                                    lowvaluearray[j] = Integer.parseInt(data_list.get(j).getLowmmhg());
                                    bpmvaluearray[j] = Integer.parseInt(data_list.get(j).getBpm());
                                    randomNumbersTab[k][j] = highvaluearray[j];
                                    randomNumbersTab2[k][j] = lowvaluearray[j];
                                    randomNumbersTab3[k][j] = bpmvaluearray[j];
                                }
                            }
                            maxNumberOfLines++;
                            generateline3DataWeek();

                        }
                    }
                    toggleLabelForSelected();
                    resetViewport();
                    System.out.println(Arrays.deepToString(randomNumbersTab).replace("], ", "]\n"));
                    Log.d("9995", "num:" + numberOfPoints);
                    Log.d("8721", "count:" + counting);
                } catch (JSONException e) {
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
    //////////////////////////////////////////////////////////////30天內圖
    public void getRecordMonth(){
        Log.d("777","in method");
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Log.d("777","1");
        final JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.POST, url3, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("777", "in response");
                int count = 0;
                try {
                    data_list = new ArrayList<>();
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
                        Log.d("1234", "saw id:" + member_id);
                        if (member_id.equals(sentmember_id)) {
                            data_list.add(record);
                            usrhighmmhg = object.getString("highmmhg");
                            usrlowmmhg = object.getString("lowmmhg");
                            usrbpm = object.getString("bpm");
                            usrsavetime = object.getString("savetime");

                            Log.d("6969", "member_id:" + member_id);
                            Log.d("6969", "highmmhg:" + usrhighmmhg);
                            Log.d("6969", "lowmmhg:" + usrlowmmhg);
                            Log.d("6969", "bpm:" + usrbpm);
                            Log.d("9999", "savetime:" + usrsavetime);

                            count++;

                            highvaluearray = new int[count];
                            lowvaluearray = new int[count];
                            bpmvaluearray = new int[count];
                            datearray = new String[count];

                            numberOfPoints = count;
                            randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];


                            for (int k = 0; k < maxNumberOfLines; k++) {
                                Log.d("9996", "number of lines:" + maxNumberOfLines);

                                for (int j = 0; j < data_list.size(); j++) {
                                    Log.d("9996", "number of points:" + numberOfPoints);


                                    highvaluearray[j] = Integer.parseInt(data_list.get(j).getHighmmhg());
                                    Log.d("7654", "array:  " + highvaluearray[j]);
                                    Log.d("7654", "length:  " + highvaluearray.length);

                                    randomNumbersTab[k][j] = highvaluearray[j];



//                                    System.out.print(+randomNumbersTab[k][j]+" ");
//                                    System.out.println();
//                                    System.out.println(Arrays.deepToString(randomNumbersTab).replace("], ", "]\n"));
                                }
                            }
                        }
                    }

//                        generateLineData();
//                        generateData();
                    toggleLabelForSelected();
                    toggleFilled();
                    generatelineDataMonth();
                    resetViewport();

                    System.out.println(Arrays.deepToString(randomNumbersTab).replace("], ", "]\n"));
                    Log.d("9995", "num:" + numberOfPoints);
                    Log.d("8721", "count:" + count);

//                    Log.d("9995","higharray"+highvaluearray[count]);

//                  generateValues();
//                    generateData();
                } catch (JSONException e) {
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
    public void getRecordMonth2(){
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Log.d("777","1");
        final JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.POST, url3, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("777", "in response");
                int counter = 0;
                try {
                    data_list = new ArrayList<>();
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
                        Log.d("1234", "saw id:" + member_id);
                        if (member_id.equals(sentmember_id)) {
                            data_list.add(record);
                            usrhighmmhg = object.getString("highmmhg");
                            usrlowmmhg = object.getString("lowmmhg");
                            usrbpm = object.getString("bpm");
                            usrsavetime = object.getString("savetime");

                            Log.d("6969", "member_id:" + member_id);
                            Log.d("6969", "highmmhg:" + usrhighmmhg);
                            Log.d("6969", "lowmmhg:" + usrlowmmhg);
                            Log.d("6969", "bpm:" + usrbpm);
                            Log.d("9999", "savetime:" + usrsavetime);

                            counter++;

                            highvaluearray = new int[counter];
                            lowvaluearray = new int[counter];
                            bpmvaluearray = new int[counter];
                            datearray = new String[counter];

                            numberOfPoints = counter;
                            randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];


                            for (int k = 0; k < maxNumberOfLines; k++) {
                                Log.d("9996", "number of lines:" + maxNumberOfLines);

                                for (int j = 0; j < data_list.size(); j++) {
                                    Log.d("9996", "number of points:" + numberOfPoints);


                                    lowvaluearray[j] = Integer.parseInt(data_list.get(j).getLowmmhg());
                                    Log.d("7654", "array:  " + lowvaluearray[j]);
                                    Log.d("7654", "length:  " + lowvaluearray.length);

                                    randomNumbersTab[k][j] = lowvaluearray[j];



//                                    System.out.print(+randomNumbersTab[k][j]+" ");
//                                    System.out.println();
//                                    System.out.println(Arrays.deepToString(randomNumbersTab).replace("], ", "]\n"));
                                }
                            }
                        }
                    }
                    toggleLabelForSelected();
                    toggleFilled();
                    generateline1DataMonth();
                    resetViewport();
                    System.out.println(Arrays.deepToString(randomNumbersTab).replace("], ", "]\n"));
                    Log.d("9995", "num:" + numberOfPoints);
                    Log.d("8721", "count:" + counter);

//                    Log.d("9995","higharray"+highvaluearray[count]);

//                  generateValues();
//                    generateData();
                } catch (JSONException e) {
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
    public void getRecordMonth3(){
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Log.d("777","1");
        final JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.POST, url3, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("777", "in response");
                int counting = 0;
                try {
                    data_list = new ArrayList<>();
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
                        Log.d("1234", "saw id:" + member_id);
                        if (member_id.equals(sentmember_id)) {
                            data_list.add(record);
                            usrhighmmhg = object.getString("highmmhg");
                            usrlowmmhg = object.getString("lowmmhg");
                            usrbpm = object.getString("bpm");
                            usrsavetime = object.getString("savetime");

                            Log.d("6969", "member_id:" + member_id);
                            Log.d("6969", "highmmhg:" + usrhighmmhg);
                            Log.d("6969", "lowmmhg:" + usrlowmmhg);
                            Log.d("6969", "bpm:" + usrbpm);
                            Log.d("9999", "savetime:" + usrsavetime);

                            counting++;

                            highvaluearray = new int[counting];
                            lowvaluearray = new int[counting];
                            bpmvaluearray = new int[counting];
                            datearray = new String[counting];

                            numberOfPoints = counting;
                            randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];


                            for (int k = 0; k < maxNumberOfLines; k++) {
                                Log.d("9996", "number of lines:" + maxNumberOfLines);

                                for (int j = 0; j < data_list.size(); j++) {
                                    Log.d("9996", "number of points:" + numberOfPoints);


                                    bpmvaluearray[j] = Integer.parseInt(data_list.get(j).getBpm());
                                    Log.d("5666", "array:  " + bpmvaluearray[j]);
                                    Log.d("5666", "length:  " + bpmvaluearray.length);

                                    randomNumbersTab[k][j] = bpmvaluearray[j];



//                                    System.out.print(+randomNumbersTab[k][j]+" ");
//                                    System.out.println();
//                                    System.out.println(Arrays.deepToString(randomNumbersTab).replace("], ", "]\n"));
                                }
                            }
                        }
                    }
                    toggleLabelForSelected();
                    toggleFilled();
                    generateline2DataMonth();
                    resetViewport();
                    System.out.println(Arrays.deepToString(randomNumbersTab).replace("], ", "]\n"));
                    Log.d("9995", "num:" + numberOfPoints);
                    Log.d("8721", "count:" + counting);

//                    Log.d("9995","higharray"+highvaluearray[count]);

//                  generateValues();
//                    generateData();
                } catch (JSONException e) {
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
    public void compareMonth(){
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.POST, url3, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int counting = 0;
                try {
                    data_list = new ArrayList<>();
                    rd = true;
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        record = new BloodPressure(object.getInt("id"), object.getString("member_id"), object.getString("highmmhg"), object.getString("lowmmhg"), object.getString("bpm"), object.getString("savetime"));
                        userid = object.getInt("id");
                        member_id = object.getString("member_id");
                        if (member_id.equals(sentmember_id)) {
                            data_list.add(record);
                            usrhighmmhg = object.getString("highmmhg");
                            usrlowmmhg = object.getString("lowmmhg");
                            usrbpm = object.getString("bpm");
                            usrsavetime = object.getString("savetime");
                            counting++;
                            highvaluearray = new int[counting];
                            lowvaluearray = new int[counting];
                            bpmvaluearray = new int[counting];
                            datearray = new String[counting];

                            numberOfPoints = counting;
                            randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];
                            randomNumbersTab2 = new float[maxNumberOfLines][numberOfPoints];
                            randomNumbersTab3 = new float[maxNumberOfLines][numberOfPoints];


                            for (int k = 0; k < maxNumberOfLines; k++) {
                                for (int j = 0; j < data_list.size(); j++) {
                                    Log.d("9996", "number of points:" + numberOfPoints);
                                    highvaluearray[j] = Integer.parseInt(data_list.get(j).getHighmmhg());
                                    lowvaluearray[j] = Integer.parseInt(data_list.get(j).getLowmmhg());
                                    bpmvaluearray[j] = Integer.parseInt(data_list.get(j).getBpm());
                                    randomNumbersTab[k][j] = highvaluearray[j];
                                    randomNumbersTab2[k][j] = lowvaluearray[j];
                                    randomNumbersTab3[k][j] = bpmvaluearray[j];
                                }
                            }
                            maxNumberOfLines++;
                            generateline3DataMonth();

                        }
                    }
                    toggleLabelForSelected();
                    resetViewport();
                    System.out.println(Arrays.deepToString(randomNumbersTab).replace("], ", "]\n"));
                    Log.d("9995", "num:" + numberOfPoints);
                    Log.d("8721", "count:" + counting);
                } catch (JSONException e) {
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


/////////////////////////////////////////////////////////////////////menu


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.combo_line_column_chart, menu);
        radiogroup = (RadioGroup) getActivity().findViewById(R.id.toggle);
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch(checkedId){
                    case R.id.three:
                        highwarn.setVisibility(View.INVISIBLE);
                        lowwarn.setVisibility(View.INVISIBLE);
                        heartwarn.setVisibility(View.INVISIBLE);
                        compare.setVisibility(View.INVISIBLE);
                        data_list = new ArrayList<>();
                        getRecord();

                        toggleFilled();
                        toggleLabelForSelected();
                        break;
                    case R.id.week:
                        highwarn.setVisibility(View.INVISIBLE);
                        lowwarn.setVisibility(View.INVISIBLE);
                        heartwarn.setVisibility(View.INVISIBLE);
                        compare.setVisibility(View.INVISIBLE);
                        data_list = new ArrayList<>();
                        getRecordWeek();

                        toggleFilled();
                        toggleLabelForSelected();
                        break;
                    case R.id.month:
                        highwarn.setVisibility(View.INVISIBLE);
                        lowwarn.setVisibility(View.INVISIBLE);
                        heartwarn.setVisibility(View.INVISIBLE);
                        compare.setVisibility(View.INVISIBLE);
                        data_list= new ArrayList<>();
                        getRecordMonth();

                        toggleFilled();
                        toggleLabelForSelected();
                        break;
                    default:
                        highwarn.setVisibility(View.INVISIBLE);
                        lowwarn.setVisibility(View.INVISIBLE);
                        heartwarn.setVisibility(View.INVISIBLE);
                        compare.setVisibility(View.INVISIBLE);
                        data_list = new ArrayList<>();
                        getRecord();

                        toggleFilled();
                        toggleLabelForSelected();
                        break;
                }
            }
        });
        radiogroup.check(R.id.three);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        menu = item.getItemId();
       radiogroup = (RadioGroup) getActivity().findViewById(R.id.toggle);

        if (id == R.id.action_reset) {
            if(plotdetail.getVisibility() == View.VISIBLE) {
                plotdetail.setVisibility(View.INVISIBLE);
            }
            highwarn.setVisibility(View.INVISIBLE);
            lowwarn.setVisibility(View.INVISIBLE);
            heartwarn.setVisibility(View.INVISIBLE);
            compare.setVisibility(View.INVISIBLE);
            reset();
            data_list = new ArrayList<>();
            getRecord();
            warning.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    highwarn.setVisibility(View.VISIBLE);
                    highwarn.setText(Html.fromHtml("<b><font color=\"#FF0000\">" + "收縮壓(上壓) mmHg" + "</font></b>" +"<b><small><font color=\"#000000\">" + "正常血壓：90 - 119 mmhg，正常高值：120 – 139 mmhg，"+ "</font></small></b>" +"<b><small><font color=\"#FF0000\">" + "1期高血壓：140 - 159 mmhg，低血壓：< 90 mmhg" + "</font></small></b>" + "</font>"));
                }
            });
            radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    switch(checkedId){
                        case R.id.three:
                            highwarn.setVisibility(View.INVISIBLE);
                            lowwarn.setVisibility(View.INVISIBLE);
                            heartwarn.setVisibility(View.INVISIBLE);
                            compare.setVisibility(View.INVISIBLE);
                            data_list = new ArrayList<>();
                            getRecord();
                            toggleFilled();
                            toggleLabelForSelected();
                            break;
                        case R.id.week:
                            highwarn.setVisibility(View.INVISIBLE);
                            lowwarn.setVisibility(View.INVISIBLE);
                            heartwarn.setVisibility(View.INVISIBLE);
                            compare.setVisibility(View.INVISIBLE);
                            data_list = new ArrayList<>();
                            getRecordWeek();
                            toggleFilled();
                            toggleLabelForSelected();
                            break;
                        case R.id.month:
                            highwarn.setVisibility(View.INVISIBLE);
                            lowwarn.setVisibility(View.INVISIBLE);
                            heartwarn.setVisibility(View.INVISIBLE);
                            compare.setVisibility(View.INVISIBLE);
                            data_list= new ArrayList<>();
                            getRecordMonth();
                            toggleFilled();
                            toggleLabelForSelected();
                            break;
                        default:
                            highwarn.setVisibility(View.INVISIBLE);
                            lowwarn.setVisibility(View.INVISIBLE);
                            heartwarn.setVisibility(View.INVISIBLE);
                            compare.setVisibility(View.INVISIBLE);
                            data_list = new ArrayList<>();
                            getRecord();
                            toggleFilled();
                            toggleLabelForSelected();
                            break;
                    }
                }
            });
            radiogroup.check(R.id.three);
            toggleLabelForSelected();
            toggleFilled();
//            generateData();
            return true;
        }
        if (id == R.id.action_add_line) {
            if(plotdetail.getVisibility() == View.VISIBLE) {
                plotdetail.setVisibility(View.INVISIBLE);
            }
            highwarn.setVisibility(View.INVISIBLE);
            lowwarn.setVisibility(View.INVISIBLE);
            heartwarn.setVisibility(View.INVISIBLE);
            compare.setVisibility(View.INVISIBLE);
            warning.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lowwarn.setVisibility(View.VISIBLE);
                    lowwarn.setText(Html.fromHtml("<b><font color=\"#FF0000\">" + "舒張壓(下壓) mmHg" + "</font></b>" +"<b><small><font color=\"#000000\">" + "正常血壓：60 – 79 mmhg，正常高值：80 – 89 mmhg，"+ "</font></small></b>" +"<b><small><font color=\"#FF0000\">" + "1期高血壓：90 - 99 mmhg，低血壓：< 60 mmhg" + "</font></small></b>" + "</font>"));
                }
            });
            addLineToData();
            radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    switch(checkedId){
                        case R.id.three:
                            highwarn.setVisibility(View.INVISIBLE);
                            lowwarn.setVisibility(View.INVISIBLE);
                            heartwarn.setVisibility(View.INVISIBLE);
                            compare.setVisibility(View.INVISIBLE);
                            data_list = new ArrayList<>();
                            getRecord2();
                            toggleFilled();
                            toggleLabelForSelected();
                            break;
                        case R.id.week:
                            highwarn.setVisibility(View.INVISIBLE);
                            lowwarn.setVisibility(View.INVISIBLE);
                            heartwarn.setVisibility(View.INVISIBLE);
                            compare.setVisibility(View.INVISIBLE);
                            data_list = new ArrayList<>();
                            getRecordWeek2();
//                            toggleFilled();
                            toggleLabelForSelected();
                            break;
                        case R.id.month:
                            highwarn.setVisibility(View.INVISIBLE);
                            lowwarn.setVisibility(View.INVISIBLE);
                            heartwarn.setVisibility(View.INVISIBLE);
                            compare.setVisibility(View.INVISIBLE);
                            data_list= new ArrayList<>();
                            getRecordMonth2();
                            toggleFilled();
                            toggleLabelForSelected();
                            break;
                        default:
                            highwarn.setVisibility(View.INVISIBLE);
                            lowwarn.setVisibility(View.INVISIBLE);
                            heartwarn.setVisibility(View.INVISIBLE);
                            compare.setVisibility(View.INVISIBLE);
                            data_list = new ArrayList<>();
                            getRecord2();
                            toggleFilled();
                            toggleLabelForSelected();
                            break;
                    }
                }
            });
            radiogroup.check(R.id.three);
//            toggleFilled();
            return true;
        }
        if (id == R.id.action_toggle_lines) {
            if(plotdetail.getVisibility() == View.VISIBLE) {
                plotdetail.setVisibility(View.INVISIBLE);
            }
            highwarn.setVisibility(View.INVISIBLE);
            lowwarn.setVisibility(View.INVISIBLE);
            heartwarn.setVisibility(View.INVISIBLE);
            compare.setVisibility(View.INVISIBLE);
            warning.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    heartwarn.setVisibility(View.VISIBLE);
                    heartwarn.setText(Html.fromHtml("<b><font color=\"#FF0000\">" + "心跳(心率) bpm" + "</font></b>" +"<b><small><font color=\"#000000\">" + "理想狀態：55 - 70 bpm，成年人正常範圍：60 - 100 bpm，"+ "</font></small></b>" +"<b><small><font color=\"#FF0000\">" + "危險警惕範圍：80 - 85 bpm" + "</font></small></b>" + "</font>"));
                }
            });
            data_list = new ArrayList<>();
            toggleLines();
            radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    switch(checkedId){
                        case R.id.three:
                            highwarn.setVisibility(View.INVISIBLE);
                            lowwarn.setVisibility(View.INVISIBLE);
                            heartwarn.setVisibility(View.INVISIBLE);
                            compare.setVisibility(View.INVISIBLE);
                            data_list = new ArrayList<>();
                            getRecord3();
                            toggleFilled();
                            toggleLabelForSelected();
                            break;
                        case R.id.week:
                            highwarn.setVisibility(View.INVISIBLE);
                            lowwarn.setVisibility(View.INVISIBLE);
                            heartwarn.setVisibility(View.INVISIBLE);
                            compare.setVisibility(View.INVISIBLE);
                            data_list = new ArrayList<>();
                            getRecordWeek3();
//                            toggleFilled();
                            toggleLabelForSelected();
                            break;
                        case R.id.month:
                            highwarn.setVisibility(View.INVISIBLE);
                            lowwarn.setVisibility(View.INVISIBLE);
                            heartwarn.setVisibility(View.INVISIBLE);
                            compare.setVisibility(View.INVISIBLE);
                            data_list= new ArrayList<>();
                            getRecordMonth3();
                            toggleFilled();
                            toggleLabelForSelected();
                            break;
                        default:
                            highwarn.setVisibility(View.INVISIBLE);
                            lowwarn.setVisibility(View.INVISIBLE);
                            heartwarn.setVisibility(View.INVISIBLE);
                            compare.setVisibility(View.INVISIBLE);
                            data_list = new ArrayList<>();
                            getRecord3();
                            toggleFilled();
                            toggleLabelForSelected();
                            break;
                    }
                }
            });
            radiogroup.check(R.id.three);
//            toggleFilled();
            return true;
        }
        if (id == R.id.compare){
            plotdetail.setVisibility(View.VISIBLE);
            highwarn.setVisibility(View.INVISIBLE);
            lowwarn.setVisibility(View.INVISIBLE);
            heartwarn.setVisibility(View.INVISIBLE);
            compare.setVisibility(View.INVISIBLE);
            warning.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    compare.setVisibility(View.VISIBLE);
                }
            });
            data_list = new ArrayList<>();
            compare();
            radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    switch(checkedId){
                        case R.id.three:
                            highwarn.setVisibility(View.INVISIBLE);
                            lowwarn.setVisibility(View.INVISIBLE);
                            heartwarn.setVisibility(View.INVISIBLE);
                            compare.setVisibility(View.INVISIBLE);
                            data_list = new ArrayList<>();
                            compare();
                            toggleLabelForSelected();
                            break;
                        case R.id.week:
                            highwarn.setVisibility(View.INVISIBLE);
                            lowwarn.setVisibility(View.INVISIBLE);
                            heartwarn.setVisibility(View.INVISIBLE);
                            compare.setVisibility(View.INVISIBLE);
                            data_list = new ArrayList<>();
                            compareWeek();
                            toggleLabelForSelected();
                            break;
                        case R.id.month:
                            highwarn.setVisibility(View.INVISIBLE);
                            lowwarn.setVisibility(View.INVISIBLE);
                            heartwarn.setVisibility(View.INVISIBLE);
                            compare.setVisibility(View.INVISIBLE);
                            data_list= new ArrayList<>();
                            compareMonth();
                            toggleLabelForSelected();
                            break;
                        default:
                            highwarn.setVisibility(View.INVISIBLE);
                            lowwarn.setVisibility(View.INVISIBLE);
                            heartwarn.setVisibility(View.INVISIBLE);
                            compare.setVisibility(View.INVISIBLE);
                            data_list = new ArrayList<>();
                            compare();
                            toggleLabelForSelected();
                            break;
                    }
                }
            });
            radiogroup.check(R.id.three);
//
            toggleLabelForSelected();
            return true;
        }
//        if (id == R.id.action_toggle_points) {
//            togglePoints();
//            return true;
//        }
//        if (id == R.id.action_toggle_cubic) {
//            toggleCubic();
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
                axisX.setTextColor(Color.BLACK);
                axisY.setName("收縮壓  mmhg");
                axisY.setTextColor(Color.BLACK);
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
                Log.d("0909","numberofpoint: "+numberOfPoints);
                values.add(new PointValue(j, randomNumbersTab[i][j]));
            }
            Log.d("5567","values: "+values);
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
        if(numColumns!=0) {
            for (int i = 0; i < highvaluearray.length; ++i) {

                values = new ArrayList<SubcolumnValue>();
                for (int j = 0; j < numSubcolumns; ++j) {
                    values.add(new SubcolumnValue(highvaluearray[i], ChartUtils.COLOR_GREEN));
                }

                columns.add(new Column(values));
            }

            ColumnChartData columnChartData = new ColumnChartData(columns);
            return columnChartData;
        }

        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue(i, ChartUtils.COLOR_GREEN));
            }

            columns.add(new Column(values));
        }

        ColumnChartData NoAnyData = new ColumnChartData(columns);
        Toast.makeText(this.getActivity(), "您尚未新增血壓相關的紀錄哦！趕快去新增吧！", Toast.LENGTH_LONG).show();
        return NoAnyData;

    }
    private ColumnChartData generateColumnData2() {
        int numSubcolumns = 1;
        int numColumns ;
        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        numColumns = numberOfPoints;
        if(numColumns!=0) {
            for (int i = 0; i < lowvaluearray.length; ++i) {

                values = new ArrayList<SubcolumnValue>();
                for (int j = 0; j < numSubcolumns; ++j) {
                    values.add(new SubcolumnValue(lowvaluearray[i], ChartUtils.COLOR_GREEN));
                }

                columns.add(new Column(values));
            }

            ColumnChartData columnChartData = new ColumnChartData(columns);
            return columnChartData;
        }

        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue(i, ChartUtils.COLOR_GREEN));
            }

            columns.add(new Column(values));
        }

        ColumnChartData NoAnyData = new ColumnChartData(columns);
        Toast.makeText(this.getActivity(), "您尚未新增血壓相關的紀錄哦！趕快去新增吧！", Toast.LENGTH_LONG).show();
        return NoAnyData;

    }
    private ColumnChartData generateColumnData3() {
        int numSubcolumns = 1;
        int numColumns ;
        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        numColumns = numberOfPoints;
        if(numColumns!=0) {
            for (int i = 0; i < bpmvaluearray.length; ++i) {

                values = new ArrayList<SubcolumnValue>();
                for (int j = 0; j < numSubcolumns; ++j) {
                    values.add(new SubcolumnValue(bpmvaluearray[i], ChartUtils.COLOR_GREEN));
                }

                columns.add(new Column(values));
            }

            ColumnChartData columnChartData = new ColumnChartData(columns);
            return columnChartData;
        }

        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue(i, ChartUtils.COLOR_GREEN));
            }

            columns.add(new Column(values));
        }

        ColumnChartData NoAnyData = new ColumnChartData(columns);
        Toast.makeText(this.getActivity(), "您尚未新增血壓相關的紀錄哦！趕快去新增吧！", Toast.LENGTH_LONG).show();
        return NoAnyData;

    }
    private void addLineToData() {
        data_list = new ArrayList<>();
        getRecord2();
        toggleLabelForSelected();
        toggleFilled();
//        if (linedata.getLines().size() >= maxNumberOfLines) {
//            Toast.makeText(getActivity(), "再看看其他的血壓資訊吧!", Toast.LENGTH_SHORT).show();
//            data_list = new ArrayList<>();
//            return;
//        } else {
////        numberOfLines++;
//        }
    }
    private void toggleLines() {
//        hasLines = !hasLines;
        data_list = new ArrayList<>();
        getRecord3();
        toggleLabelForSelected();
        toggleFilled();
//        if (linedata.getLines().size() >= maxNumberOfLines) {
//            Toast.makeText(getActivity(), "再看看其他的血壓資訊吧!", Toast.LENGTH_SHORT).show();
//            data_list = new ArrayList<>();
//            return;
//        } else {
////            numberOfLines++;
//        }
    }
    private void resetViewport() {
        // Reset viewport height range to (0,100)
        final Viewport v = new Viewport(lineChartView.getMaximumViewport());
        v.bottom = 0;
        v.top = 200;
        v.left = 0;
        v.right = numberOfPoints - 1;
        lineChartView.setMaximumViewport(v);
        lineChartView.setCurrentViewport(v);
    }
    ///////////////////////////////////////////////////////////3日圖示
    private void generatelineData() {
        if(numberOfPoints==0){
            Toast.makeText(getActivity(),"您尚未新增血壓相關紀錄哦！趕快去新增吧！",Toast.LENGTH_LONG).show();
        }


        List<Line> lines = new ArrayList<Line>();
        for (int i = 0; i < numberOfLines; ++i) {

            List<PointValue> values = new ArrayList<PointValue>();
            for (int j = 0; j < numberOfPoints; ++j) {
                Log.d("2223","points"+numberOfPoints);
                values.add(new PointValue(j, randomNumbersTab[i][j]));
            }
            Log.d("5566","values: "+values);

            Line line = new Line(values);
            line.setColor(ChartUtils.COLOR_VIOLET);
            line.setShape(shape);
            line.setCubic(isCubic);
            line.setFilled(isFilled);
            line.setHasLabels(hasLabels);
            line.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);
//            line.setHasGradientToTransparent(hasGradientToTransparent);
            if (pointsHaveDifferentColor){
                line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
            }
            lines.add(line);
        }

        linedata = new LineChartData(lines);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("3日內變化");
                axisX.setTextColor(Color.BLACK);
                axisY.setName("收縮壓  mmhg");
                axisY.setTextColor(Color.BLACK);
            }
            linedata.setAxisXBottom(axisX);
            linedata.setAxisYLeft(axisY);
        } else {
            linedata.setAxisXBottom(null);
            linedata.setAxisYLeft(null);
        }
        linedata.setBaseValue(Float.NEGATIVE_INFINITY);
        lineChartView.setLineChartData(linedata);

    }
    private void generateline1Data(){
        if(numberOfPoints==0){
            Toast.makeText(getActivity(),"您尚未新增血壓相關紀錄哦！趕快去新增吧！",Toast.LENGTH_LONG).show();
        }

        List<Line> lines = new ArrayList<Line>();
        for (int i = 0; i < numberOfLines; ++i) {

            List<PointValue> values = new ArrayList<PointValue>();
            for (int j = 0; j < numberOfPoints; ++j) {
                Log.d("2223","points"+numberOfPoints);
                values.add(new PointValue(j, randomNumbersTab[i][j]));
            }
            Log.d("5566","values: "+values);

            Line line = new Line(values);
            line.setColor(ChartUtils.COLOR_ORANGE);
            line.setShape(shape);
            line.setCubic(isCubic);
            line.setFilled(isFilled);
            line.setHasLabels(hasLabels);
            line.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);
//            line.setHasGradientToTransparent(hasGradientToTransparent);
            if (pointsHaveDifferentColor){
                line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
            }
            lines.add(line);
        }

        linedata = new LineChartData(lines);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("3日內變化");
                axisX.setTextColor(Color.BLACK);
                axisY.setName("舒張壓  mmhg");
                axisY.setTextColor(Color.BLACK);
            }
            linedata.setAxisXBottom(axisX);
            linedata.setAxisYLeft(axisY);
        } else {
            linedata.setAxisXBottom(null);
            linedata.setAxisYLeft(null);
        }
        linedata.setBaseValue(Float.NEGATIVE_INFINITY);
        lineChartView.setLineChartData(linedata);

    }
    private void generateline2Data() {
        if(numberOfPoints==0){
            Toast.makeText(getActivity(),"您尚未新增血壓相關紀錄哦！趕快去新增吧！",Toast.LENGTH_LONG).show();
        }

        List<Line> lines = new ArrayList<Line>();
        for (int i = 0; i < numberOfLines; ++i) {

            List<PointValue> values = new ArrayList<PointValue>();
            for (int j = 0; j < numberOfPoints; ++j) {
                Log.d("2223","points"+numberOfPoints);
                values.add(new PointValue(j, randomNumbersTab[i][j]));
            }
            Log.d("5566","values: "+values);

            Line line = new Line(values);
            line.setColor(ChartUtils.COLOR_RED);
            line.setShape(shape);
            line.setCubic(isCubic);
            line.setFilled(isFilled);
            line.setHasLabels(hasLabels);
            line.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);
//            line.setHasGradientToTransparent(hasGradientToTransparent);
            if (pointsHaveDifferentColor){
                line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
            }
            lines.add(line);
        }

        linedata = new LineChartData(lines);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("3日內變化");
                axisX.setTextColor(Color.BLACK);
                axisY.setName("心跳  bpm");
                axisY.setTextColor(Color.BLACK);
            }
            linedata.setAxisXBottom(axisX);
            linedata.setAxisYLeft(axisY);
        } else {
            linedata.setAxisXBottom(null);
            linedata.setAxisYLeft(null);
        }
        linedata.setBaseValue(Float.NEGATIVE_INFINITY);
        lineChartView.setLineChartData(linedata);

    }
    private void generateline3Data(){
        if(numberOfPoints==0){
            Toast.makeText(getActivity(),"您尚未新增血壓相關紀錄哦！趕快去新增吧！",Toast.LENGTH_LONG).show();
        }

        List<Line> lines = new ArrayList<Line>();
        for (int i = 0; i < numberOfLines; ++i) {

            List<PointValue> values = new ArrayList<PointValue>();
            List<PointValue> values2 = new ArrayList<PointValue>();
            List<PointValue> values3 = new ArrayList<PointValue>();
            for (int j = 0; j < numberOfPoints; ++j) {
                Log.d("2223","points"+numberOfPoints);
                values.add(new PointValue(j, randomNumbersTab[i][j]));
                values2.add(new PointValue(j, randomNumbersTab2[i][j]));
                values3.add(new PointValue(j, randomNumbersTab3[i][j]));
            }
            Log.d("5566","values: "+values);

            Line line = new Line(values);
            line.setColor(ChartUtils.COLOR_VIOLET);
            line.setShape(shape);
            line.setCubic(isCubic);
//            line.setFilled(isFilled);
            line.setHasLabels(hasLabels);
            line.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);
            Line line2 = new Line(values2);
            line2.setColor(ChartUtils.COLOR_ORANGE);
            line2.setShape(shape);
            line2.setCubic(isCubic);
//            line2.setFilled(isFilled);
            line2.setHasLabels(hasLabels);
            line2.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line2.setHasLines(hasLines);
            line2.setHasPoints(hasPoints);
            Line line3 = new Line(values3);
            line3.setColor(ChartUtils.COLOR_RED);
            line3.setShape(shape);
            line3.setCubic(isCubic);
//            line3.setFilled(isFilled);
            line3.setHasLabels(hasLabels);
            line3.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line3.setHasLines(hasLines);
            line3.setHasPoints(hasPoints);
//            line.setHasGradientToTransparent(hasGradientToTransparent);
            if (pointsHaveDifferentColor){
                line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
            }
            if (pointsHaveDifferentColor){
                line2.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
            }
            if (pointsHaveDifferentColor){
                line3.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
            }
            lines.add(line);
            lines.add(line2);
            lines.add(line3);
        }

        linedata = new LineChartData(lines);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);

            if (hasAxesNames) {
                SpannableStringBuilder builder = new SpannableStringBuilder();
                String high = "收縮壓數值(高) ";
                SpannableString Spannable1= new SpannableString(high);
                Spannable1.setSpan(new ForegroundColorSpan(ChartUtils.COLOR_VIOLET), 0, high.length(), 0);
                builder.append(Spannable1);
                String low = "舒張壓數值(低) ";
                SpannableString Spannable2= new SpannableString(low);
                Spannable2.setSpan(new ForegroundColorSpan(ChartUtils.COLOR_ORANGE), 0, low.length(), 0);
                builder.append(Spannable2);
                String heart = "心跳數值";
                SpannableString Spannable3= new SpannableString(heart);
                Spannable3.setSpan(new ForegroundColorSpan(ChartUtils.COLOR_RED), 0, heart.length(), 0);
                builder.append(Spannable3);
                plotdetail =(TextView) rootView.findViewById(R.id.section_label);
                plotdetail.setVisibility(View.VISIBLE);
                plotdetail.setText(builder, TextView.BufferType.SPANNABLE);
                String setin = plotdetail.getText().toString();
                TextView tv = new TextView(this.getActivity());
                tv.setText("收縮壓數值(高)");
                tv.setTextColor(ChartUtils.COLOR_VIOLET);
                String str1 = tv.getText().toString();
                TextView tv2 = new TextView(this.getActivity());
                tv2.setText("舒張壓數值(低)");
                tv2.setTextColor(ChartUtils.COLOR_ORANGE);
                String str2 = tv2.getText().toString();
                TextView tv3 = new TextView(this.getActivity());
                tv3.setText("心跳數值");
                tv3.setTextColor(ChartUtils.COLOR_RED);
                String str3 = tv3.getText().toString();
                axisX.setName("3日內變化");
                axisX.setTextColor(Color.BLACK);
                axisX.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
                axisY.setName("綜合比較");
                axisY.setTextColor(Color.BLACK);
            }
            linedata.setAxisXBottom(axisX);
            linedata.setAxisYLeft(axisY);
        } else {
            linedata.setAxisXBottom(null);
            linedata.setAxisYLeft(null);
        }
        linedata.setBaseValue(Float.NEGATIVE_INFINITY);
        lineChartView.setLineChartData(linedata);

    }
    ///////////////////////////////////////////////////////////7日圖示
    private void generatelineDataWeek() {
        if(numberOfPoints==0){
            Toast.makeText(getActivity(),"您尚未新增血壓相關紀錄哦！趕快去新增吧！",Toast.LENGTH_LONG).show();
        }


        List<Line> lines = new ArrayList<Line>();
        for (int i = 0; i < numberOfLines; ++i) {

            List<PointValue> values = new ArrayList<PointValue>();
            for (int j = 0; j < numberOfPoints; ++j) {
                Log.d("2223","points"+numberOfPoints);
                values.add(new PointValue(j, randomNumbersTab[i][j]));
            }
            Log.d("5566","values: "+values);

            Line line = new Line(values);
            line.setColor(ChartUtils.COLOR_VIOLET);
            line.setShape(shape);
            line.setCubic(isCubic);
            line.setFilled(isFilled);
            line.setHasLabels(hasLabels);
            line.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);
//            line.setHasGradientToTransparent(hasGradientToTransparent);
            if (pointsHaveDifferentColor){
                line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
            }
            lines.add(line);
        }

        linedata = new LineChartData(lines);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("7日內變化");
                axisX.setTextColor(Color.BLACK);
                axisY.setName("收縮壓  mmhg");
                axisY.setTextColor(Color.BLACK);
            }
            linedata.setAxisXBottom(axisX);
            linedata.setAxisYLeft(axisY);
        } else {
            linedata.setAxisXBottom(null);
            linedata.setAxisYLeft(null);
        }
        linedata.setBaseValue(Float.NEGATIVE_INFINITY);
        lineChartView.setLineChartData(linedata);

    }
    private void generateline1DataWeek(){
        if(numberOfPoints==0){
            Toast.makeText(getActivity(),"您尚未新增血壓相關紀錄哦！趕快去新增吧！",Toast.LENGTH_LONG).show();
        }

        List<Line> lines = new ArrayList<Line>();
        for (int i = 0; i < numberOfLines; ++i) {

            List<PointValue> values = new ArrayList<PointValue>();
            for (int j = 0; j < numberOfPoints; ++j) {
                Log.d("2223","points"+numberOfPoints);
                values.add(new PointValue(j, randomNumbersTab[i][j]));
            }
            Log.d("5566","values: "+values);

            Line line = new Line(values);
            line.setColor(ChartUtils.COLOR_ORANGE);
            line.setShape(shape);
            line.setCubic(isCubic);
            line.setFilled(isFilled);
            line.setHasLabels(hasLabels);
            line.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);
//            line.setHasGradientToTransparent(hasGradientToTransparent);
            if (pointsHaveDifferentColor){
                line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
            }
            lines.add(line);
        }

        linedata = new LineChartData(lines);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("7日內變化");
                axisX.setTextColor(Color.BLACK);
                axisY.setName("舒張壓  mmhg");
                axisY.setTextColor(Color.BLACK);
            }
            linedata.setAxisXBottom(axisX);
            linedata.setAxisYLeft(axisY);
        } else {
            linedata.setAxisXBottom(null);
            linedata.setAxisYLeft(null);
        }
        linedata.setBaseValue(Float.NEGATIVE_INFINITY);
        lineChartView.setLineChartData(linedata);

    }
    private void generateline2DataWeek() {
        if(numberOfPoints==0){
            Toast.makeText(getActivity(),"您尚未新增血壓相關紀錄哦！趕快去新增吧！",Toast.LENGTH_LONG).show();
        }

        List<Line> lines = new ArrayList<Line>();
        for (int i = 0; i < numberOfLines; ++i) {

            List<PointValue> values = new ArrayList<PointValue>();
            for (int j = 0; j < numberOfPoints; ++j) {
                Log.d("2223","points"+numberOfPoints);
                values.add(new PointValue(j, randomNumbersTab[i][j]));
            }
            Log.d("5566","values: "+values);

            Line line = new Line(values);
            line.setColor(ChartUtils.COLOR_RED);
            line.setShape(shape);
            line.setCubic(isCubic);
            line.setFilled(isFilled);
            line.setHasLabels(hasLabels);
            line.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);
//            line.setHasGradientToTransparent(hasGradientToTransparent);
            if (pointsHaveDifferentColor){
                line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
            }
            lines.add(line);
        }

        linedata = new LineChartData(lines);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("7日內變化");
                axisX.setTextColor(Color.BLACK);
                axisY.setName("心跳  bpm");
                axisY.setTextColor(Color.BLACK);
            }
            linedata.setAxisXBottom(axisX);
            linedata.setAxisYLeft(axisY);
        } else {
            linedata.setAxisXBottom(null);
            linedata.setAxisYLeft(null);
        }
        linedata.setBaseValue(Float.NEGATIVE_INFINITY);
        lineChartView.setLineChartData(linedata);

    }
    private void generateline3DataWeek(){
        if(numberOfPoints==0){
            Toast.makeText(getActivity(),"您尚未新增血壓相關紀錄哦！趕快去新增吧！",Toast.LENGTH_LONG).show();
        }

        List<Line> lines = new ArrayList<Line>();
        for (int i = 0; i < numberOfLines; ++i) {

            List<PointValue> values = new ArrayList<PointValue>();
            List<PointValue> values2 = new ArrayList<PointValue>();
            List<PointValue> values3 = new ArrayList<PointValue>();
            for (int j = 0; j < numberOfPoints; ++j) {
                Log.d("2223","points"+numberOfPoints);
                values.add(new PointValue(j, randomNumbersTab[i][j]));
                values2.add(new PointValue(j, randomNumbersTab2[i][j]));
                values3.add(new PointValue(j, randomNumbersTab3[i][j]));
            }
            Log.d("5566","values: "+values);

            Line line = new Line(values);
            line.setColor(ChartUtils.COLOR_VIOLET);
            line.setShape(shape);
            line.setCubic(isCubic);
//            line.setFilled(isFilled);
            line.setHasLabels(hasLabels);
            line.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);
            Line line2 = new Line(values2);
            line2.setColor(ChartUtils.COLOR_ORANGE);
            line2.setShape(shape);
            line2.setCubic(isCubic);
//            line2.setFilled(isFilled);
            line2.setHasLabels(hasLabels);
            line2.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line2.setHasLines(hasLines);
            line2.setHasPoints(hasPoints);
            Line line3 = new Line(values3);
            line3.setColor(ChartUtils.COLOR_RED);
            line3.setShape(shape);
            line3.setCubic(isCubic);
//            line3.setFilled(isFilled);
            line3.setHasLabels(hasLabels);
            line3.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line3.setHasLines(hasLines);
            line3.setHasPoints(hasPoints);
//            line.setHasGradientToTransparent(hasGradientToTransparent);
            if (pointsHaveDifferentColor){
                line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
            }
            if (pointsHaveDifferentColor){
                line2.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
            }
            if (pointsHaveDifferentColor){
                line3.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
            }
            lines.add(line);
            lines.add(line2);
            lines.add(line3);
        }

        linedata = new LineChartData(lines);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);

            if (hasAxesNames) {
                SpannableStringBuilder builder = new SpannableStringBuilder();
                String high = "收縮壓數值(高) ";
                SpannableString Spannable1= new SpannableString(high);
                Spannable1.setSpan(new ForegroundColorSpan(ChartUtils.COLOR_VIOLET), 0, high.length(), 0);
                builder.append(Spannable1);
                String low = "舒張壓數值(低) ";
                SpannableString Spannable2= new SpannableString(low);
                Spannable2.setSpan(new ForegroundColorSpan(ChartUtils.COLOR_ORANGE), 0, low.length(), 0);
                builder.append(Spannable2);
                String heart = "心跳數值";
                SpannableString Spannable3= new SpannableString(heart);
                Spannable3.setSpan(new ForegroundColorSpan(ChartUtils.COLOR_RED), 0, heart.length(), 0);
                builder.append(Spannable3);
                plotdetail =(TextView) rootView.findViewById(R.id.section_label);
                plotdetail.setVisibility(View.VISIBLE);
                plotdetail.setText(builder, TextView.BufferType.SPANNABLE);
                String setin = plotdetail.getText().toString();
                TextView tv = new TextView(this.getActivity());
                tv.setText("收縮壓數值(高)");
                tv.setTextColor(ChartUtils.COLOR_VIOLET);
                String str1 = tv.getText().toString();
                TextView tv2 = new TextView(this.getActivity());
                tv2.setText("舒張壓數值(低)");
                tv2.setTextColor(ChartUtils.COLOR_ORANGE);
                String str2 = tv2.getText().toString();
                TextView tv3 = new TextView(this.getActivity());
                tv3.setText("心跳數值");
                tv3.setTextColor(ChartUtils.COLOR_RED);
                String str3 = tv3.getText().toString();
                axisX.setName("7日內變化");
                axisX.setTextColor(Color.BLACK);
                axisX.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
                axisY.setName("綜合比較");
                axisY.setTextColor(Color.BLACK);
            }
            linedata.setAxisXBottom(axisX);
            linedata.setAxisYLeft(axisY);
        } else {
            linedata.setAxisXBottom(null);
            linedata.setAxisYLeft(null);
        }
        linedata.setBaseValue(Float.NEGATIVE_INFINITY);
        lineChartView.setLineChartData(linedata);

    }

    ///////////////////////////////////////////////////////////30日圖示
    private void generatelineDataMonth() {
        if(numberOfPoints==0){
            Toast.makeText(getActivity(),"您尚未新增血壓相關紀錄哦！趕快去新增吧！",Toast.LENGTH_LONG).show();
        }


        List<Line> lines = new ArrayList<Line>();
        for (int i = 0; i < numberOfLines; ++i) {

            List<PointValue> values = new ArrayList<PointValue>();
            for (int j = 0; j < numberOfPoints; ++j) {
                Log.d("2223","points"+numberOfPoints);
                values.add(new PointValue(j, randomNumbersTab[i][j]));
            }
            Log.d("5566","values: "+values);

            Line line = new Line(values);
            line.setColor(ChartUtils.COLOR_VIOLET);
            line.setShape(shape);
            line.setCubic(isCubic);
            line.setFilled(isFilled);
            line.setHasLabels(hasLabels);
            line.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);
//            line.setHasGradientToTransparent(hasGradientToTransparent);
            if (pointsHaveDifferentColor){
                line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
            }
            lines.add(line);
        }

        linedata = new LineChartData(lines);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("30日內變化");
                axisX.setTextColor(Color.BLACK);
                axisY.setName("收縮壓  mmhg");
                axisY.setTextColor(Color.BLACK);
            }
            linedata.setAxisXBottom(axisX);
            linedata.setAxisYLeft(axisY);
        } else {
            linedata.setAxisXBottom(null);
            linedata.setAxisYLeft(null);
        }
        linedata.setBaseValue(Float.NEGATIVE_INFINITY);
        lineChartView.setLineChartData(linedata);

    }
    private void generateline1DataMonth(){
        if(numberOfPoints==0){
            Toast.makeText(getActivity(),"您尚未新增血壓相關紀錄哦！趕快去新增吧！",Toast.LENGTH_LONG).show();
        }

        List<Line> lines = new ArrayList<Line>();
        for (int i = 0; i < numberOfLines; ++i) {

            List<PointValue> values = new ArrayList<PointValue>();
            for (int j = 0; j < numberOfPoints; ++j) {
                Log.d("2223","points"+numberOfPoints);
                values.add(new PointValue(j, randomNumbersTab[i][j]));
            }
            Log.d("5566","values: "+values);

            Line line = new Line(values);
            line.setColor(ChartUtils.COLOR_ORANGE);
            line.setShape(shape);
            line.setCubic(isCubic);
            line.setFilled(isFilled);
            line.setHasLabels(hasLabels);
            line.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);
//            line.setHasGradientToTransparent(hasGradientToTransparent);
            if (pointsHaveDifferentColor){
                line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
            }
            lines.add(line);
        }

        linedata = new LineChartData(lines);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("30日內變化");
                axisX.setTextColor(Color.BLACK);
                axisY.setName("舒張壓  mmhg");
                axisY.setTextColor(Color.BLACK);
            }
            linedata.setAxisXBottom(axisX);
            linedata.setAxisYLeft(axisY);
        } else {
            linedata.setAxisXBottom(null);
            linedata.setAxisYLeft(null);
        }
        linedata.setBaseValue(Float.NEGATIVE_INFINITY);
        lineChartView.setLineChartData(linedata);

    }
    private void generateline2DataMonth() {
        if(numberOfPoints==0){
            Toast.makeText(getActivity(),"您尚未新增血壓相關紀錄哦！趕快去新增吧！",Toast.LENGTH_LONG).show();
        }

        List<Line> lines = new ArrayList<Line>();
        for (int i = 0; i < numberOfLines; ++i) {

            List<PointValue> values = new ArrayList<PointValue>();
            for (int j = 0; j < numberOfPoints; ++j) {
                Log.d("2223","points"+numberOfPoints);
                values.add(new PointValue(j, randomNumbersTab[i][j]));
            }
            Log.d("5566","values: "+values);

            Line line = new Line(values);
            line.setColor(ChartUtils.COLOR_RED);
            line.setShape(shape);
            line.setCubic(isCubic);
            line.setFilled(isFilled);
            line.setHasLabels(hasLabels);
            line.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);
//            line.setHasGradientToTransparent(hasGradientToTransparent);
            if (pointsHaveDifferentColor){
                line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
            }
            lines.add(line);
        }

        linedata = new LineChartData(lines);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("30日內變化");
                axisX.setTextColor(Color.BLACK);
                axisY.setName("心跳  bpm");
                axisY.setTextColor(Color.BLACK);
            }
            linedata.setAxisXBottom(axisX);
            linedata.setAxisYLeft(axisY);
        } else {
            linedata.setAxisXBottom(null);
            linedata.setAxisYLeft(null);
        }
        linedata.setBaseValue(Float.NEGATIVE_INFINITY);
        lineChartView.setLineChartData(linedata);

    }
    private void generateline3DataMonth(){
        if(numberOfPoints==0){
            Toast.makeText(getActivity(),"您尚未新增血壓相關紀錄哦！趕快去新增吧！",Toast.LENGTH_LONG).show();
        }

        List<Line> lines = new ArrayList<Line>();
        for (int i = 0; i < numberOfLines; ++i) {

            List<PointValue> values = new ArrayList<PointValue>();
            List<PointValue> values2 = new ArrayList<PointValue>();
            List<PointValue> values3 = new ArrayList<PointValue>();
            for (int j = 0; j < numberOfPoints; ++j) {
                Log.d("2223","points"+numberOfPoints);
                values.add(new PointValue(j, randomNumbersTab[i][j]));
                values2.add(new PointValue(j, randomNumbersTab2[i][j]));
                values3.add(new PointValue(j, randomNumbersTab3[i][j]));
            }
            Log.d("5566","values: "+values);

            Line line = new Line(values);
            line.setColor(ChartUtils.COLOR_VIOLET);
            line.setShape(shape);
            line.setCubic(isCubic);
//            line.setFilled(isFilled);
            line.setHasLabels(hasLabels);
            line.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);
            Line line2 = new Line(values2);
            line2.setColor(ChartUtils.COLOR_ORANGE);
            line2.setShape(shape);
            line2.setCubic(isCubic);
//            line2.setFilled(isFilled);
            line2.setHasLabels(hasLabels);
            line2.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line2.setHasLines(hasLines);
            line2.setHasPoints(hasPoints);
            Line line3 = new Line(values3);
            line3.setColor(ChartUtils.COLOR_RED);
            line3.setShape(shape);
            line3.setCubic(isCubic);
//            line3.setFilled(isFilled);
            line3.setHasLabels(hasLabels);
            line3.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line3.setHasLines(hasLines);
            line3.setHasPoints(hasPoints);
//            line.setHasGradientToTransparent(hasGradientToTransparent);
            if (pointsHaveDifferentColor){
                line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
            }
            if (pointsHaveDifferentColor){
                line2.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
            }
            if (pointsHaveDifferentColor){
                line3.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
            }
            lines.add(line);
            lines.add(line2);
            lines.add(line3);
        }

        linedata = new LineChartData(lines);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);

            if (hasAxesNames) {
                SpannableStringBuilder builder = new SpannableStringBuilder();
                String high = "收縮壓數值(高) ";
                SpannableString Spannable1= new SpannableString(high);
                Spannable1.setSpan(new ForegroundColorSpan(ChartUtils.COLOR_VIOLET), 0, high.length(), 0);
                builder.append(Spannable1);
                String low = "舒張壓數值(低) ";
                SpannableString Spannable2= new SpannableString(low);
                Spannable2.setSpan(new ForegroundColorSpan(ChartUtils.COLOR_ORANGE), 0, low.length(), 0);
                builder.append(Spannable2);
                String heart = "心跳數值";
                SpannableString Spannable3= new SpannableString(heart);
                Spannable3.setSpan(new ForegroundColorSpan(ChartUtils.COLOR_RED), 0, heart.length(), 0);
                builder.append(Spannable3);
                plotdetail =(TextView) rootView.findViewById(R.id.section_label);
                plotdetail.setVisibility(View.VISIBLE);
                plotdetail.setText(builder, TextView.BufferType.SPANNABLE);
                String setin = plotdetail.getText().toString();
                TextView tv = new TextView(this.getActivity());
                tv.setText("收縮壓數值(高)");
                tv.setTextColor(ChartUtils.COLOR_VIOLET);
                String str1 = tv.getText().toString();
                TextView tv2 = new TextView(this.getActivity());
                tv2.setText("舒張壓數值(低)");
                tv2.setTextColor(ChartUtils.COLOR_ORANGE);
                String str2 = tv2.getText().toString();
                TextView tv3 = new TextView(this.getActivity());
                tv3.setText("心跳數值");
                tv3.setTextColor(ChartUtils.COLOR_RED);
                String str3 = tv3.getText().toString();
                axisX.setName("30日內變化");
                axisX.setTextColor(Color.BLACK);
                axisX.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
                axisY.setName("綜合比較");
                axisY.setTextColor(Color.BLACK);
            }
            linedata.setAxisXBottom(axisX);
            linedata.setAxisYLeft(axisY);
        } else {
            linedata.setAxisXBottom(null);
            linedata.setAxisYLeft(null);
        }
        linedata.setBaseValue(Float.NEGATIVE_INFINITY);
        lineChartView.setLineChartData(linedata);

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
    private void toggleFilled() {
        isFilled = !isFilled;
    }
    private void toggleLabelForSelected() {
        hasLabelForSelected = !hasLabelForSelected;

        lineChartView.setValueSelectionEnabled(hasLabelForSelected);

        if (hasLabelForSelected) {
            hasLabels = false;
        }

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
    private class ValueTouchListener implements ComboLineColumnChartOnValueSelectListener, LineChartOnValueSelectListener {

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
            Toast.makeText(getActivity(), "Selected line point: " + value.getY(), Toast.LENGTH_SHORT).show();
        }
        public void goback(View v)
        {
            getActivity().onBackPressed();

        }

        @Override
        public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {

        }
    }







    // MENU

    }

