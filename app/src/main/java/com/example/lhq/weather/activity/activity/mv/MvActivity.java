package com.example.lhq.weather.activity.activity.mv;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lhq.weather.R;
import com.example.lhq.weather.activity.utils.Utility;
import com.shuyu.gsyvideoplayer.GSYVideoADManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MvActivity extends AppCompatActivity {

    public static final String TAG ="MvActivity";

    @BindView(R.id.mv_videoview)
    StandardGSYVideoPlayer videoPlayer;

    @BindView(R.id.mv_titleName)
    TextView mvNameTextView;

    OrientationUtils orientationUtils;


    //传值
    public static void actionStart(Context context,String mvUrl,String pic,String mvName){
        Intent intent = new Intent(context,MvActivity.class);
        intent.putExtra("url",mvUrl);
        intent.putExtra("pic",pic);
        intent.putExtra("mvName",mvName);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitle();
        setContentView(R.layout.activity_mv);
        Log.d(TAG,"onCreate方法");
        ButterKnife.bind(this);

        initVideoViewData();
    }

    //加载视频资源
    private void initVideoViewData() {
        //设置高度
        videoPlayer.getLayoutParams().height = Utility.getWindowHeight(this)/3;

        //获取mv的地址信息
        String mvUrl = getIntent().getStringExtra("url");
        String mvPic = getIntent().getStringExtra("pic");
        String mvName = getIntent().getStringExtra("mvName");

        //设置播放地址和标题
        mvNameTextView.setText(mvName);
        videoPlayer.setUp(mvUrl, true, mvName);
        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(this).load(mvPic).into(imageView);
        videoPlayer.setThumbImageView(imageView);
        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        //设置返回键
        videoPlayer.getBackButton().setVisibility(View.VISIBLE);
        //设置旋转
        orientationUtils = new OrientationUtils(this, videoPlayer);
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orientationUtils.resolveByClick();
            }
        });

        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(true);
        //设置返回按键功能
        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoPlayer.getLayoutParams().height = Utility.getWindowHeight(MvActivity.this);
                onBackPressed();
            }
        });
        videoPlayer.startPlayLogic();
    }


    //标题栏
    private void initTitle() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart方法");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume方法");
        videoPlayer.onVideoResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause方法");
        videoPlayer.onVideoPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop方法");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onRestart方法");
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy方法");
        //释放资源
        GSYVideoADManager.releaseAllVideos();
        if(orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onBackPressed() {
        //先返回正常状态
        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            videoPlayer.getFullscreenButton().performClick();
            return;
        }
        //释放所有
        videoPlayer.setVideoAllCallBack(null);
        super.onBackPressed();
    }

    /**
     * 播放mv接口
     */
    public interface mvStart{
        void onStopMusic();
    }
}
