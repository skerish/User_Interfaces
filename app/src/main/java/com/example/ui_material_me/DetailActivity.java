package com.example.ui_material_me;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView textView = findViewById(R.id.titleDetail);
        TextView paras = findViewById(R.id.subTitleDetail);
        ImageView imageView = findViewById(R.id.sportsImageDetail);

        Intent intent = getIntent();

        textView.setText(intent.getStringExtra("title_key"));
        paras.setText(intent.getStringExtra("details_key"));

        Glide.with(this).load(intent.getIntExtra("image_key", 0)).into(imageView);

        getWindow().setEnterTransition(new Slide());
    }
}
