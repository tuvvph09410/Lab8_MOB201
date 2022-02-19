package com.example.lab8_mob201.Fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lab8_mob201.R;
import com.example.lab8_mob201.ReSources.AlbumStorageDirFactory;
import com.example.lab8_mob201.ReSources.BaseAlbumDirFactory;
import com.example.lab8_mob201.ReSources.FroyoAlbumDirFactory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Fragment_Bai1 extends Fragment {
    private Button btnCamera;
    private ImageView ivCamera;
    private final int REQUEST_IMAGE_CAPTURE = 1;
    private final int REQUEST_TAKE_PHOTO = 1;
    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";
    private AlbumStorageDirFactory albumStorageDirFactory = null;
    private String mCurrentPhotoPath;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__bai1, container, false);
        this.initByViewId(view);
        this.initButton();
        return view;
    }

    private void initButton() {
        this.btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessCamera();
            }
        });
    }

    private void accessCamera() {
        int numberCamera = Camera.getNumberOfCameras();
        if (numberCamera > 0) {
            Toast.makeText(getContext(), "Thiet bi co" + numberCamera, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 1);
        } else {
            Toast.makeText(getContext(), "Thiet bi khong co camera", Toast.LENGTH_LONG).show();
        }
    }

    private void initByViewId(View view) {
        this.btnCamera = view.findViewById(R.id.btn_camera);
        this.ivCamera = view.findViewById(R.id.iv_camera);
    }

}