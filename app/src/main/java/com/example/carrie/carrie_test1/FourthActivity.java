package com.example.carrie.carrie_test1;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class FourthActivity extends AppCompatActivity {
    String id;
    String indi;
    String eng;
    String license;
    String cate;
    String compon;
    String maker_Coun;
    String appli;
    String maker_Nam;
    String ima;
    android.support.design.widget.CoordinatorLayout coordinatorLayout;

    private int drugid;
    private String drugname;
    private FloatingActionButton addnmcal;
    private ArrayList<ArrayList<String>> mydrugs = new ArrayList<ArrayList<String>>();
    private String m_calid;
    private static float MAX_TEXT_SIZE = 20;
    TextView indication,englishName;
    TextView gltxt;


    //String chineseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("drug","1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.addlayout);
        Log.d("drug","2");
        Bundle bundle5=getIntent().getExtras();
        m_calid = bundle5.getString("m_calid",m_calid);
//        Log.d("qqqqq123",m_calid);

        if (m_calid.equals("-1")){//如果直接從搜尋add符號隱藏
            coordinatorLayout.setVisibility(View.GONE);
        }


        addnmcal = (FloatingActionButton)findViewById(R.id.addmcal);
        inserttomcal();
       // setContentView(R.layout.activity_fourth);
       // Log.d("chart","2");
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        drugid = bundle.getInt("id");
        Log.d("drugooo", String.valueOf(drugid));


        id = bundle.getString("chineseName");//get 中文名字
        drugname=id;
        Log.d("drug","4");
        TextView chineseName=(TextView) findViewById(R.id.chineseName3);
        Log.d("drug","5");
        String string = getIntent().getExtras().getString("chineseName", "not found");
        chineseName.setText(string);
        passtxt(chineseName);

        String string1= getIntent().getExtras().getString("indication", "not found");
        indi = bundle.getString("indication");//get 中文名字
        indication=(TextView) findViewById(R.id.indication);
        indication.setText(string1);
        passtxt(indication);

//        indication.setText(autoSplitText(indication));
//        indication.getViewTreeObserver().addOnGlobalLayoutListener(new OnTvGlobalLayoutListener());

        //    indi = autoSplitText(indication);
   //     indication.setText(indi);

        String string2= getIntent().getExtras().getString("englishName", "not found");
        eng = bundle.getString("englishName");//get 中文名字
        englishName=(TextView) findViewById(R.id.englishName3);
        englishName.setText(string2);
        Log.d("dadada",englishName.getText().toString());
//        englishName.setText(autoSplitText(englishName));
        passtxt(englishName);
//        englishName.getViewTreeObserver().addOnGlobalLayoutListener(new OnTvGlobalLayoutListener());
        Log.d("dadada222",englishName.getText().toString());


        String string3= getIntent().getExtras().getString("licenseNumber", "not found");
        license = bundle.getString("licenseNumber");//get 中文名字
        TextView licenseNumber=(TextView) findViewById(R.id.licenseNumber);
        licenseNumber.setText(string3);
        passtxt(licenseNumber);

        String string4= getIntent().getExtras().getString("category", "not found");
        cate = bundle.getString("category");//get 中文名字
        TextView category=(TextView) findViewById(R.id.category);
        category.setText(string4);
        passtxt(category);
        Log.d("drug","6");

        String string5= getIntent().getExtras().getString("component", "not found");
        compon = bundle.getString("component");//get 中文名字
        TextView component=(TextView) findViewById(R.id.component);
        component.setText(string5);
        passtxt(component);
        Log.d("drug","8");


        String string6= getIntent().getExtras().getString("maker_Country", "not found");
        maker_Coun = bundle.getString("maker_Country");//get 中文名字
        TextView maker_Country=(TextView) findViewById(R.id.maker_Country);
        maker_Country.setText(string6);
        passtxt(maker_Country);

        String string7= getIntent().getExtras().getString("applicant", "not found");
        appli = bundle.getString("applicant");//get 中文名字
        TextView applicant=(TextView) findViewById(R.id.applicant);
        applicant.setText(string7);
        passtxt(applicant);

        String string8= getIntent().getExtras().getString("maker_Name", "not found");
        maker_Nam = bundle.getString("maker_Name");//get 中文名字
        TextView maker_Name=(TextView) findViewById(R.id.maker_Name);
        maker_Name.setText(string8);
        passtxt(maker_Name);


        String string9= getIntent().getExtras().getString("image", "not found");
        ima = bundle.getString("image");//get 中文名字
        ImageView image=(ImageView) findViewById(R.id.image3);
        Glide.with(getBaseContext()).load(string9).into(image);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Log.d("drug","7");
//

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String member_id = memberdata.getMember_id();
        String google_id= memberdata.getGoogle_id();
        String m_id=memberdata.getMy_mon_id();
        Intent i = new Intent(getApplicationContext(), druginfo.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("my_id", member_id);
        i.putExtra("my_google_id", google_id);
        i.putExtra("my_supervise_id", m_id);
        i.putExtra("m_calid",m_calid);
        startActivity(i);
        finish();
    }

    public void passtxt(TextView txt){
        gltxt=txt;
        gltxt.getViewTreeObserver().addOnGlobalLayoutListener(new OnTvGlobalLayoutListener());
    }

    private class OnTvGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {

         @Override
         public void onGlobalLayout() {
                         gltxt.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                         final String newText = autoSplitText(gltxt);
                         if (!TextUtils.isEmpty(newText)) {
                                 gltxt.setText(newText);
                             }
                     }
     }

    private String autoSplitText(final TextView tv) {
//        gltxt=tv;
//        gltxt.getViewTreeObserver().addOnGlobalLayoutListener(new OnTvGlobalLayoutListener());
        final String rawText = tv.getText().toString(); //原始文本
        final Paint tvPaint = tv.getPaint(); //paint，包含字體等信息
        final float tvWidth = tv.getWidth() - tv.getPaddingLeft() - tv.getPaddingRight(); //控制項可用寬度

        //將原始文本按行拆分
        String [] rawTextLines = rawText.replaceAll("\r", "").split("\n");
        StringBuilder sbNewText = new StringBuilder();
        for (String rawTextLine : rawTextLines) {
            if (tvPaint.measureText(rawTextLine) <= tvWidth) {
                //如果整行寬度在控制項可用寬度之內，就不處理了
                sbNewText.append(rawTextLine);
            } else {
                //如果整行寬度超過控制項可用寬度，則按字元測量，在超過可用寬度的前一個字元處手動換行
                float lineWidth = 0;
                for (int cnt = 0; cnt != rawTextLine.length(); ++cnt) {
                    char ch = rawTextLine.charAt(cnt);
                    lineWidth += tvPaint.measureText(String.valueOf(ch));
                    if (lineWidth <= tvWidth) {
                        sbNewText.append(ch);
                    } else {
                        sbNewText.append("\n");
                        lineWidth = 0;
                        --cnt;
                    }
                }
            }
            sbNewText.append("\n");
        }
        //把結尾多餘的\n去掉
        if (!rawText.endsWith("\n")) {
            sbNewText.deleteCharAt(sbNewText.length() - 1);
        }

        return sbNewText.toString();
    }




    public void inserttomcal(){
        addnmcal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!m_calid.equals(null) && !m_calid.equals("0")){
                    Log.d("drug","100");
                    Intent it =new Intent(FourthActivity.this,medicine_cal.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("drugid", String.valueOf(drugid));
                    bundle1.putString("chineseName",drugname);
                    bundle1.putInt("entertype",1);
                    bundle1.putString("m_calid",m_calid);
                    it.putExtras(bundle1);
                    startActivity(it);
                }else {
                    //                int i= mcaldata.getMcaldrugs().size();
//                Log.d("drugarray",String.valueOf(i));
//                mdrugs = mcaldata.getMcaldrugs();
//                Log.d("drug","100");
//                mdrugs.add(new ArrayList<String>());
//                Log.d("drug","200");
//                mdrugs.get(i).add(drugname);
//                Log.d("drug","300");
//                mdrugs.get(i).add(String.valueOf(drugid));
//                Log.d("drug","400");
//                mcaldata.setMcaldrugs(mdrugs);
                    mydrugs.add(new ArrayList<String>());
                    mydrugs.get(0).add(drugname);
                    mydrugs.get(0).add(String.valueOf(drugid));
                    Log.d("drug","500");
                    Intent it =new Intent(FourthActivity.this,ThirdActivity.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("drugid", String.valueOf(drugid));
                    bundle1.putString("chineseName",drugname);
                    bundle1.putInt("entertype",1);
                    it.putExtras(bundle1);
                    startActivity(it);
                }


            }
        });
    }


    public void goback(View v){
        finish();
    }


}


