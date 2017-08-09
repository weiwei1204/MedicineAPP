package com.example.carrie.carrie_test1;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
    //String chineseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("drug","1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        Log.d("drug","2");

       // setContentView(R.layout.activity_fourth);
       // Log.d("chart","2");
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("chineseName");//get 中文名字
        Log.d("drug","4");
        TextView chineseName=(TextView) findViewById(R.id.chineseName3);
        Log.d("drug","5");
        String string = getIntent().getExtras().getString("chineseName", "not found");
        chineseName.setText(string);

        String string1= getIntent().getExtras().getString("indication", "not found");
        indi = bundle.getString("indication");//get 中文名字
        TextView indication=(TextView) findViewById(R.id.indication);
        indication.setText(string1);

        String string2= getIntent().getExtras().getString("englishName", "not found");
        eng = bundle.getString("englishName");//get 中文名字
        TextView englishName=(TextView) findViewById(R.id.englishName3);
        englishName.setText(string2);

        String string3= getIntent().getExtras().getString("licenseNumber", "not found");
        license = bundle.getString("licenseNumber");//get 中文名字
        TextView licenseNumber=(TextView) findViewById(R.id.licenseNumber);
        licenseNumber.setText(string3);

        String string4= getIntent().getExtras().getString("category", "not found");
        cate = bundle.getString("category");//get 中文名字
        TextView category=(TextView) findViewById(R.id.category);
        category.setText(string4);
        Log.d("drug","6");

        String string5= getIntent().getExtras().getString("component", "not found");
        compon = bundle.getString("component");//get 中文名字
        TextView component=(TextView) findViewById(R.id.component);
        component.setText(string5);
        Log.d("drug","8");


        String string6= getIntent().getExtras().getString("maker_Country", "not found");
        maker_Coun = bundle.getString("maker_Country");//get 中文名字
        TextView maker_Country=(TextView) findViewById(R.id.maker_Country);
        maker_Country.setText(string6);

        String string7= getIntent().getExtras().getString("applicant", "not found");
        appli = bundle.getString("applicant");//get 中文名字
        TextView applicant=(TextView) findViewById(R.id.applicant);
        applicant.setText(string7);

        String string8= getIntent().getExtras().getString("maker_Name", "not found");
        maker_Nam = bundle.getString("maker_Name");//get 中文名字
        TextView maker_Name=(TextView) findViewById(R.id.maker_Name);
        maker_Name.setText(string8);

//        String string9= getIntent().getExtras().getString("image", "not found");
//        ima = bundle.getString("image");//get 中文名字
//        ImageView image=(ImageView) findViewById(R.id.image);
//        image.setImageURI(Uri.parse(string9));


//    chineseName.setText(String.valueOf(id));
//        Log.d("drug","6");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Log.d("drug","7");
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    }
    public void goback(View v){
        finish();
    }



    }


