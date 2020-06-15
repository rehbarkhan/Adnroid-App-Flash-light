package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Parameter;
import java.security.Policy;

import static android.util.Log.*;

public class MainActivity extends AppCompatActivity {

    Button light;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        light = findViewById(R.id.button);
        light.setText("Turn On Flash");
        if(flashAvailable()){
            Toast.makeText(getApplicationContext(),"Flash is available",Toast.LENGTH_SHORT).show();
        }
        else{
            light.setEnabled(false);
        }
    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void flashLigth(View view) {
        Button light = findViewById(R.id.button);
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);


        if(counter == 0){
            //flah light is off / app just started-turn on the flash flash light
            try{
                String cameraId = cameraManager.getCameraIdList()[0];
                cameraManager.setTorchMode(cameraId,true);
                counter += 1;
                light.setText("Turn off Flash");
            }
            catch (CameraAccessException e){
                Log.i("flash","Unabel to get camera ID");
            }

        }
        else{
            //flashlist is on / turnoff the flash light
            try{
                String cameraId = cameraManager.getCameraIdList()[0];
                cameraManager.setTorchMode(cameraId,false);
                counter -= 1;
                light.setText("Turn On Flash");
            }catch(CameraAccessException e){
                Log.i("flash","Unabel to get camera ID");
            }
        }
    }

    boolean flashAvailable(){
        boolean value = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        return value;
    }
}