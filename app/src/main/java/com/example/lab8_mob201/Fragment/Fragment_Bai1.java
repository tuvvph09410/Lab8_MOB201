package com.example.lab8_mob201.Fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lab8_mob201.R;


public class Fragment_Bai1 extends Fragment {
    private Button btnCamera;
    private ImageView ivCamera;
    private static final int CAMERA_ACTION_CODE = 1;


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

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, CAMERA_ACTION_CODE);
        } else {
            Toast.makeText(getContext(), "Không có thiết bị hộ trợ", Toast.LENGTH_SHORT).show();
        }


    }

    private void initByViewId(View view) {
        this.btnCamera = view.findViewById(R.id.btn_camera);
        this.ivCamera = view.findViewById(R.id.iv_camera);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_ACTION_CODE && resultCode == RESULT_OK && data != null) {
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            this.ivCamera.setImageBitmap(bitmap);
        }
    }
}