package me.zjc.onkeyalarm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;


/**
 * Created by chuanzhangjiang on 18-3-8.
 * 提交数据用的Activity
 */

public class CommitActivity extends AppCompatActivity {

    public static final String KEY_FILE_PATH = "filePath";
    public static final String KEY_FILE_TYPE = "fileType"; //获取从上一个界面获取到的数据类型（图片、视频）的key

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commit);
        final ImageView ivImg = findViewById(R.id.iv_img);
        Glide.with(this)
                .load(new File(getIntent().getStringExtra(KEY_FILE_PATH)))
                .into(ivImg);
    }
}
