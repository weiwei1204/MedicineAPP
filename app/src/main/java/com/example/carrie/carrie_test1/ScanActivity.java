package com.example.carrie.carrie_test1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class ScanActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 100;
    SurfaceView cameraView;
    BarcodeDetector barcode;
    CameraSource cameraSource;
    SurfaceHolder holder;

    //public String google_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        cameraView =(SurfaceView) findViewById(R.id.cameraView);
        cameraView.setZOrderMediaOverlay(true);
        holder = cameraView.getHolder();
        barcode = new BarcodeDetector.Builder(this)
                    .setBarcodeFormats(Barcode.QR_CODE)
                    .build();
        if(!barcode.isOperational()){
            Toast.makeText(getApplicationContext(),"Sorry ,Couldn't set up the detector",Toast.LENGTH_LONG).show();
            this.finish();
        }

        cameraSource = new CameraSource.Builder(this,barcode)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(24)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1920,1024)
                .build();
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback(){
            @Override
               public void surfaceCreated(SurfaceHolder holder){
                    try {
                        if(ContextCompat.checkSelfPermission(ScanActivity.this,Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED) {
                            cameraSource.start(cameraView.getHolder());
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            @Override
            public void surfaceChanged(SurfaceHolder holder,int format,int width,int height){

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder){

            }
        });
        barcode.setProcessor(new Detector.Processor<Barcode>(){
            @Override
            public void release(){

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections){
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if(barcodes.size() > 0){
                    Intent intent = new Intent();
                    intent.putExtra("barcode",barcodes.valueAt(0));
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }
//    public void checkMonitorExist(){
//        requestQueue = Volley.newRequestQueue(getApplicationContext());
//        final StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
////                Log.d("rrr", "1");
////                Log.d("rrr", response);
//
//                if(response.contains("success")){//檢查是否為新會員
//                    //gotoMain();
//
//                }
//                else if(response.contains("nodata")){
//                    //gotoInformation();
//                    Log.d("monitor_check", "success");
//                    normalDialogEvent();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
////                Log.d("rrr", error.toString());
//                Toast.makeText(getApplicationContext(), "Error read insert.php!!!", Toast.LENGTH_LONG).show();
//            }
//        }){
//            protected Map<String, String> getParams() throws AuthFailureError {//把值丟到php
//                Map<String, String> parameters = new HashMap<String, String>();
////                parameters.put("username", gname);
////                parameters.put("password", gemail);
//                parameters.put("google_id_monitor", google_id);
//                Log.d("monitor", parameters.toString());
//                return parameters;
//            }
//        }
//                ;
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(request);
//    }
//    public void addMonitor(){
//
//    }
//    public void  normalDialogEvent(){
//        new AlertDialog.Builder(ScanActivity.this)
//                .setTitle(R.string.addMonitor)
//                .setMessage(R.string.notFindMonitor)
//                .setPositiveButton(R.string.ok,new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .show();
//    }


}
