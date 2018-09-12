package com.fengan.permissiondemo;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.fengan.permission.AfterPermissionGranted;
import com.fengan.permission.AppSettingsDialog;
import com.fengan.permission.EasyPermissions;

import java.util.List;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks,
        EasyPermissions.RationaleCallbacks {

    public final static int REQUEST_CODE_CAMERA_STORAGE = 1001;

    public final static String[] CAMERA_STORAGE = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.takephoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraTask();
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        switch (requestCode) {
            case REQUEST_CODE_CAMERA_STORAGE:
                doCamera();
                break;
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onRationaleAccepted(int requestCode) {

    }

    @Override
    public void onRationaleDenied(int requestCode) {

    }

    @AfterPermissionGranted(REQUEST_CODE_CAMERA_STORAGE)
    public void cameraTask() {
        if (EasyPermissions.hasPermissions(this, CAMERA_STORAGE)) {
            doCamera();
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.permission_camera),
                    REQUEST_CODE_CAMERA_STORAGE,
                    CAMERA_STORAGE);
        }
    }

    private void doCamera() {
        Toast.makeText(this,"拍照",Toast.LENGTH_LONG).show();
    }
}
