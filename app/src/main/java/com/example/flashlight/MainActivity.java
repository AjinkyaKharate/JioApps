package com.example.flashlight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageButton toggleButton;

    boolean hasCameraFlash = false;
    boolean flashon = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggleButton = findViewById(R.id.toggleButton);

        hasCameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                if (hasCameraFlash) {
                    if (flashon) {
                        flashon = false;
                        toggleButton.setImageResource(R.drawable.off);
                        try {
                            flashLightOff();
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }


                    } else {
                        flashon = true;
                        toggleButton.setImageResource(R.drawable.on);
                        try {
                            flashLightOn();
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, "No flash available on  your device.", Toast.LENGTH_LONG);

                }
            }
        });
    }
        private void flashLightOn () throws CameraAccessException {
            CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, true);
            Toast.makeText(MainActivity.this, "FlashLight is On", Toast.LENGTH_LONG).show();
        }
        private void flashLightOff () throws CameraAccessException {
            CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, false);
            Toast.makeText(MainActivity.this, "FlashLight is Off", Toast.LENGTH_LONG).show();

        }
    }
