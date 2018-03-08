package me.zjc.onkeyalarm;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import io.reactivex.functions.Consumer;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_TAKE_PHOTO = 0x00000066;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //拍照
        findViewById(R.id.btn_photo).setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                new RxPermissions(MainActivity.this)
                        .request(
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean granted) throws Exception {
                                if (granted) { //权限校验通过
                                    goTakePhoto();
                                } else {
                                    Toast.makeText(MainActivity.this, "没有权限", Toast.LENGTH_SHORT)
                                            .show();
                                }
                            }
                        });
            }
        });

        //录像
        findViewById(R.id.btn_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * 执行跳转拍照界面操作
     */
    private void goTakePhoto() {
        MultiImageSelector.create()
                .single()
                .start(this, REQ_TAKE_PHOTO);
    }

    public static final int PATH_TYPE_PHOTO = 0;
    public static final int PATH_TYPE_VIDEO = 1;

    /**
     * 跳转到提交数据的界面
     * @param path 照片或视频的存放路径
     * @param pathType 标记是照片还是录像
     */
    private void goCommitView(String path, int pathType) {
        final Bundle bundle = new Bundle();
        bundle.putInt(CommitActivity.KEY_FILE_TYPE, pathType);
        bundle.putString(CommitActivity.KEY_FILE_PATH, path);
        final Intent intent = new Intent(this, CommitActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_TAKE_PHOTO && resultCode == RESULT_OK) { //拍照成功回调
            final List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            goCommitView(path.get(0), PATH_TYPE_PHOTO);
        }
    }
}
