package com.example.carrie.carrie_test1;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Generate_Qrcode extends AppCompatActivity {

    ImageView imageqr;
    String googleid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate__qrcode);


        imageqr=(ImageView)findViewById(R.id.imageqr);
        Bundle bundle = getIntent().getExtras();
        googleid=bundle.getString("googleid");

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(googleid.toString(), BarcodeFormat.QR_CODE,1200,1200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageqr.setImageBitmap(bitmap);

        }
        catch (WriterException e){
            Log.d("zaq","2");
            e.printStackTrace();
        }
    }

}
