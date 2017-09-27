package com.example.carrie.carrie_test1;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;

public class scandrug extends AppCompatActivity {

    String insertUrl = "http://54.65.194.253/Drug/qrcode.php";
    public static final int REQUEST_CODE = 100;
    SurfaceView cameraView;
    BarcodeDetector barcode;
    CameraSource cameraSource;
    SurfaceHolder holder;
    String urlcode,m_calid;
    private Context context;
    MyData mydata;

//    public String google_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scandrug);
        Bundle bundle = getIntent().getExtras();
        m_calid = bundle.getString("m_calid");
        cameraView = (SurfaceView) findViewById(R.id.cameraView);
        cameraView.setZOrderMediaOverlay(true);
        holder = cameraView.getHolder();
        barcode = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        if (!barcode.isOperational()) {
            Toast.makeText(getApplicationContext(), "Sorry ,Couldn't set up the detector", Toast.LENGTH_LONG).show();
            this.finish();
        }

        cameraSource = new CameraSource.Builder(this, barcode)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(24)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1920, 1024)
                .build();
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ContextCompat.checkSelfPermission(scandrug.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(cameraView.getHolder());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
        barcode.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() > 0) {
                    int count;
                    Intent intent = new Intent();
                    intent.putExtra("barcode", barcodes.valueAt(0));
                    Log.d("code", barcodes.valueAt(0).displayValue);
                    urlcode = barcodes.valueAt(0).displayValue;
                    Log.d("code2", barcodes.valueAt(0).displayValue);
                    geturl();
                    setResult(RESULT_OK, intent);
//                    cameraSource.stop();
//                    addNormalDialogEvent();



                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                Log.d("scannnnn", "1111");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void geturl() {
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                String insertUrl = "http://54.65.194.253/Drug/qrcode.php?urlcode=" + urlcode;
                OkHttpClient client = new OkHttpClient();
                Log.d("uuuuuurl", insertUrl);
                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(insertUrl)
                        .build();
                Log.d("okhttpurl", "2222");
                try {
                    okhttp3.Response response = client.newCall(request).execute();
                    Log.d("okhttpurl", "1111");
                    JSONArray array = new JSONArray(response.body().string());
                    Log.d("okhttpurl", "33333");
                    JSONObject object = array.getJSONObject(0);
                    Log.d("okhttpurl", "16666");
                    Log.d("okhttpurl", object.getString("id"));
                    if ((object.getString("id")).equals("nodata")) {
                        Log.d("okht2tp", "4442222");
                        //normalDialogEvent();
                    } else {

                        Log.d("nodata", "noooooooo");
                    }

                    Log.d("searchtest", array.toString());
                    for (int i = 0; i < array.length(); i++) {

                        object = array.getJSONObject(i);

                        mydata = new MyData(object.getInt("id"), object.getString("chineseName"),
                                object.getString("image"), object.getString("indication"), object.getString("englishName"), object.getString("licenseNumber")
                                , object.getString("category"), object.getString("component"), object.getString("maker_Country"), object.getString("applicant")
                                , object.getString("maker_Name"), object.getString("QRCode"));


                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Intent it = new Intent(scandrug.this, FourthActivity.class);
                Log.d("customadapter2", "2");
                Bundle bundle = new Bundle();
                Log.d("customadapter2", "3");
                bundle.putInt("id", mydata.getId());
                Log.d("customadapter2", "4");
                bundle.putString("image", mydata.getImage_link());
                bundle.putString("chineseName", mydata.getChineseName());
                bundle.putString("indication", mydata.getIndication());
                bundle.putString("englishName", mydata.getEnglishName());
                bundle.putString("licenseNumber", mydata.getLicenseNumber());
                bundle.putString("category", mydata.getCategory());
                bundle.putString("component", mydata.getComponent());
                bundle.putString("maker_Country", mydata.getMaker_Country());
                bundle.putString("applicant", mydata.getApplicant());
                bundle.putString("maker_Name", mydata.getMaker_Name());

                Log.d("customadapter2", "5");
                it.putExtras(bundle);
                Bundle bundle3 = new Bundle();
                bundle3.putString("m_calid","-1");
                it.putExtras(bundle3);
                startActivity(it);
                Log.d("customadapter2", "6");

            }
        };
        task.execute();

    }

    public void goback(View v) {
        finish();


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
}

