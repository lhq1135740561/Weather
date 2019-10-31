package com.example.lhq.weather.activity.activity.music;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lhq.weather.R;
import com.example.lhq.weather.activity.activity.StepActivity;
import com.example.lhq.weather.activity.adapter.music.MusicMvAdapter;
import com.example.lhq.weather.activity.data.MusicMv;
import com.example.lhq.weather.activity.json.image360api.musicApi.GsonMusicMv;
import com.example.lhq.weather.activity.json.image360api.musicApi.MusicApi;
import com.example.lhq.weather.activity.json.image360api.musicApi.Songs;
import com.example.lhq.weather.activity.json.image360api.mv.MusicMv1;
import com.example.lhq.weather.activity.json.image360api.mv.Mv;
import com.example.lhq.weather.activity.utils.HttpUtility;
import com.example.lhq.weather.activity.utils.MvUtility;
import com.example.lhq.weather.activity.view.DYLoadingView;
import com.loopj.android.http.TextHttpResponseHandler;
import com.scwang.smartrefresh.header.DropboxHeader;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.header.TaurusHeader;
import com.scwang.smartrefresh.header.WaveSwipeHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class MusicMvActivity extends AppCompatActivity {

    public static final String TAG = "MusicMvActivity";

    private List<MusicMv> musicMvList;

    private MusicMvAdapter mvAdapter;

    @BindView(R.id.music_mv_titleText)
    TextView titleTextview;

    @BindView(R.id.mv_recyclerview)
    RecyclerView mvRecyclerView;

    @BindView(R.id.music_mv_refresh)
    SmartRefreshLayout mvRefreshLayout;

    @BindView(R.id.dyLoadingview2)
    DYLoadingView dyLoadingView;

    private boolean isRefresh;

    private boolean isColor;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MusicMvActivity.class);
        context.startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_mv);
        ButterKnife.bind(this);

        LayoutTitle(R.color.music_bg);

        boolean isRefresh1 = MvUtility.getIsRefresh(MusicMvActivity.this);
        if(isRefresh1){
            initMusicMv(GsonMusicMv.MV_QQ_MUSIC_URL);
            titleTextview.setText("QQ音乐MV榜");
        }else {
            initMusicMv(GsonMusicMv.MV_WYy_MUSIC_URL);
            titleTextview.setText("云音乐MV榜");
        }

        refreshMusicMv();

    }

    private void refreshMusicMv() {

        dyLoadingView.setVisibility(View.VISIBLE);
        dyLoadingView.start();

        //设置 Header 为 贝塞尔雷达 样式
        TaurusHeader header = new TaurusHeader(this);
        header.setBackgroundResource(R.color.music_bg);
        mvRefreshLayout.setRefreshHeader(header);
        //设置 Footer 为 球脉冲 样式
        mvRefreshLayout.setRefreshFooter(new BallPulseFooter(MusicMvActivity.this).setSpinnerStyle(SpinnerStyle.Scale));

        mvRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                isRefresh = MvUtility.getIsRefresh(MusicMvActivity.this);
                if (isRefresh) {
                    titleTextview.setText("云音乐MV榜");
                    initMusicMv(GsonMusicMv.MV_WYy_MUSIC_URL);
                    isRefresh = false;
                    MvUtility.setIsRefresh(MusicMvActivity.this,false);
                } else {
                    titleTextview.setText("QQ音乐MV榜");
                    initMusicMv(GsonMusicMv.MV_QQ_MUSIC_URL);
                    isRefresh = true;
                    MvUtility.setIsRefresh(MusicMvActivity.this,true);
                }
            }
        });

    }

    private void initMusicMv(String url) {
        HttpUtility.AsynchttpRequest(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(MusicMvActivity.this, "解析失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                MusicMv1 musicMv1 = GsonMusicMv.handleMusicResponse(responseString);
                if (musicMv1 != null && musicMv1.result.equals("SUCCESS")) {
                    addMusicMvData(musicMv1);
                    mvRefreshLayout.finishRefresh(1000,false);
                    dyLoadingView.setVisibility(View.GONE);
                }
                mvAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 添加MV数据
     *
     * @param musicMv1
     */
    private void addMusicMvData(MusicMv1 musicMv1) {
        musicMvList = new ArrayList<>();
        //循环添加数据
        for (int i = 0; i < musicMv1.mvList.size(); i++) {
            Mv mv = musicMv1.mvList.get(i);
            if (i >= 0 && i <= 2) {
                isColor = true;
            } else {
                isColor = false;
            }
            String mvPic = mv.pic;
            String mvUrl = mv.url;
            String song = mv.name + '\t' + "-" + '\t' + mv.singer;
            String songName = mv.name;
            MusicMv musicMv = new MusicMv(mvPic, i + 1, song, mvUrl, isColor, songName);
            musicMvList.add(musicMv);
        }

        //设置mvRecyclerView方式适配器
        LinearLayoutManager manager = new LinearLayoutManager(MusicMvActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mvRecyclerView.setLayoutManager(manager);
        mvAdapter = new MusicMvAdapter(musicMvList, MusicMvActivity.this);
        mvRecyclerView.setAdapter(mvAdapter);
        mvRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置动画

    }


    //设置标题栏颜色
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void LayoutTitle(int color) {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
        window.setStatusBarColor(getResources().getColor(color));
    }

    @OnClick(R.id.mv_back)
    public void onClickBack(View view) {
        StepActivity.actionStart(this);
    }

}
