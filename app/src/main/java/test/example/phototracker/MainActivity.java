 package test.example.phototracker;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;


import java.io.IOException;

import tenet.lib.base.utils.Utils;
import test.example.phototracker.common.CameraSource;
import test.example.phototracker.common.CameraSourcePreview;
import test.example.phototracker.common.GraphicOverlay;
import test.example.phototracker.textrecognition.TextRecognitionProcessor;

 public class MainActivity extends AppCompatActivity {
    int requestPermissionID = 12;
    TextView mText;
    CameraSource mCameraSource;
    GraphicOverlay mGraphicOverlay;
    CameraSourcePreview mCameraPreview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        mCameraPreview = findViewById(R.id.cameraPreview);
        mText = findViewById(R.id.text);
        mGraphicOverlay = findViewById(R.id.graphicOverlay);
        mGraphicOverlay.setTextSize(Utils.dpToPx(14)).setTextColor(Color.RED);
        if(!isCamGranted()){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},requestPermissionID);
        } else {
            createCameraSource();
        }
    }


     @SuppressLint("MissingPermission")
     void startCamera() {
         if (mCameraSource != null) {
             try {
                 if (mCameraPreview == null) {
                     mText.setText("resume: Preview is null");
                 }
                 if (mGraphicOverlay == null) {
                     mText.setText("resume: graphOverlay is null");
                 }
                 mCameraPreview.start(mCameraSource, mGraphicOverlay);
             } catch (IOException e) {
                 mText.setText("Unable to start camera source\n"+e.toString());
                 mCameraSource.release();
                 mCameraSource = null;
             }
         }

    }

     @Override
     protected void onResume() {
         super.onResume();
         if(isCamGranted() && mCameraSource != null){
             startCamera();
         }
     }

     @Override
     protected void onPause() {
         super.onPause();
         mCameraPreview.stop();
     }

     private boolean isCamGranted(){
        return ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

     @Override
     public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
         super.onRequestPermissionsResult(requestCode, permissions, grantResults);
         if(isCamGranted()) {
             createCameraSource();
             startCamera();
         }
    }

     @SuppressLint("SetTextI18n")
     private void createCameraSource() {
         mCameraSource = new CameraSource(this, mGraphicOverlay)
                        .setRequestedPreviewSize(480, 360).setRequestedFps(10f);
         mCameraSource.setMachineLearningFrameProcessor(new TextRecognitionProcessor());
     }
 }
