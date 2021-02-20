package com.coopera.mayakaab.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.coopera.mayakaab.R;
import com.coopera.mayakaab.models.EnvioTamboresModel;

public class ImagenViewerActivity extends AppCompatActivity {


    ImageView imageView;
    String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagen_viewer);
        imageView = findViewById(R.id.imgViewer);

        imgPath = getIntent().getStringExtra("imagen");
        Glide.with(ImagenViewerActivity.this)
                .load(imgPath).placeholder(R.drawable.img_holder)
                .into(imageView);



    }
}