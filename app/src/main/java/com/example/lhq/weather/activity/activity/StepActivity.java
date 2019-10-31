package com.example.lhq.weather.activity.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lhq.weather.R;
import com.example.lhq.weather.activity.activity.music.MusicMvActivity;
import com.example.lhq.weather.activity.adapter.music.MusicAdapter;
import com.example.lhq.weather.activity.adapter.music.MusicSreachAdapter;
import com.example.lhq.weather.activity.adapter.music.MyMusicAdapter;
import com.example.lhq.weather.activity.common.ActivityCollector;
import com.example.lhq.weather.activity.data.Music;
import com.example.lhq.weather.activity.data.MusicLabs;
import com.example.lhq.weather.activity.data.MyLoveMusic;
import com.example.lhq.weather.activity.data.MyMusic;
import com.example.lhq.weather.activity.data.SreachMusic;
import com.example.lhq.weather.activity.db.StepBean;
import com.example.lhq.weather.activity.json.image360api.musicApi.GsonMusicApi;
import com.example.lhq.weather.activity.json.image360api.musicApi.MusicApi;
import com.example.lhq.weather.activity.json.image360api.musicApi.Songs;
import com.example.lhq.weather.activity.json.image360api.musicApi.SreachGsonMusic;
import com.example.lhq.weather.activity.json.image360api.musicApi.SreachMusicApi;
import com.example.lhq.weather.activity.json.image360api.musicApi.SreachSongs;
import com.example.lhq.weather.activity.utils.BingPicUtility;
import com.example.lhq.weather.activity.utils.DateString;
import com.example.lhq.weather.activity.utils.Gson360Api;
import com.example.lhq.weather.activity.utils.GsonUtility;
import com.example.lhq.weather.activity.utils.HttpUtility;
import com.example.lhq.weather.activity.utils.StepUtillty;
import com.example.lhq.weather.activity.utils.Utility;
import com.example.lhq.weather.activity.view.DYLoadingView;
import com.example.lhq.weather.activity.view.StepsView;
import com.example.zhouwei.library.CustomPopWindow;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.loopj.android.http.TextHttpResponseHandler;
import com.muddzdev.styleabletoast.StyleableToast;

import org.litepal.LitePal;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

public class StepActivity extends AppCompatActivity implements OnDateSetListener, MusicAdapter.MusicOnListClickListener {

    public static final String TAG = "StepActivity";

    public int menu = 0;


    @BindView(R.id.dyLoadingview)
    DYLoadingView dyLoadingView;

    /**
     * 主界面
     */
    public static final int STEP_MEMU = 0;
    /**
     * 音乐界面
     */
    public static final int STEP_MUSCI = 1;
    /**
     * 音乐搜索界面
     */
    public static final int STEP_SREACH = 2;

    /**
     * 音乐我的歌单界面
     */
    public static final int STEP_MY_MUSIC = 3;

    /**
     * 音乐我的歌单界面
     */
    public static final int STEP_MY_SEARCH_MUSIC = 4;


    private ObjectAnimator discObjectAnimator;

    @BindView(R.id.clock_layout)
    LinearLayout mClockLayout;
    @BindView(R.id.musics_layout)
    LinearLayout mMusicLayout;

    @BindView(R.id.musics_title_layout)
    RelativeLayout musicsTitleRelativeLayout;
    @BindView(R.id.musics_Stitle_layout)
    RelativeLayout musicsStitlerelativeLayout;
    @BindView(R.id.myMusic_title_layout)
    RelativeLayout musicsMyTitleRelativeLayout;
    /**
     * 打卡
     */
    @BindView(R.id.step_view)
    StepsView mstepsView;

    @BindView(R.id.tv_sign_click)
    RadioButton mTvSign;

    @BindView(R.id.clock_scrollview)
    NestedScrollView scrollView;


    @BindView(R.id.night_tv_sign_click)
    RadioButton mNightTvSign;

    @BindView(R.id.night_tv_rb)
    RadioButton mNightButton;

    @BindView(R.id.step_title_layout)
    RelativeLayout SteprelativeLayout;

    @BindView(R.id.step_back)
    ImageView stepBackImageview;

    //日期
    @BindView(R.id.front_day)
    TextView dayTextview;
    @BindView(R.id.front_ym)
    TextView ymTextview;

    @BindView(R.id.back_week)
    TextView weekTextview;
    @BindView(R.id.back_ym)
    TextView ymBackTextview;
    @BindView(R.id.back_day)
    TextView dayBackTextview;

    /**
     * 旋转图片
     *
     * @param savedInstanceState
     */
    AnimatorSet mRightOutSet, mLeftOutSet;
    boolean mIsShowBack;
    @BindView(R.id.main_fl_card_back)
    RelativeLayout mainFlCardBack;
    @BindView(R.id.main_fl_card_front)
    RelativeLayout mainFlCardFront;
    @BindView(R.id.main_fl_container)
    RelativeLayout mainFlContainer;

    @BindView(R.id.back_image)
    ImageView backImageView;
    @BindView(R.id.front_iamge)
    ImageView frontImageView;

    @BindView(R.id.anim_title)
    TextView animTitle;

    //晚上打卡
    boolean mIsShowBackNight, mIsShowBackNight2;
    @BindView(R.id.main_fl_night_back)
    RelativeLayout mainFlCardBackNight;
    @BindView(R.id.main_fl_night_front)
    RelativeLayout mainFlCardFrontNight;
    @BindView(R.id.main_fl_container_night)
    RelativeLayout mainFlContainerNight;

    @BindView(R.id.night_back_image)
    ImageView backImageViewNight;
    @BindView(R.id.front_night_image)
    ImageView frontImageViewNight;

    @BindView(R.id.night_back_week)
    TextView nightweekTextview;
    @BindView(R.id.night_back_ym)
    TextView nightymBackTextview;
    @BindView(R.id.night_back_day)
    TextView nightdayBackTextview;

    @BindView(R.id.night_title)
    TextView nightTitle;

    /**
     * 播放音乐
     */
    private MediaPlayer mp = new MediaPlayer();
    String url;
    //下一首下标
    private int musicIndex = 0;

    @BindView(R.id.music_playing)
    ImageView musicImageview;
    //换一首
    @BindView(R.id.music_cut)
    ImageView musicCutImageview;

    @BindView(R.id.music_song)
    TextView songTextView;

    //音乐列表界面
    private Music music;
    private List<Music> musicList;
    private MusicAdapter adapter;
    @BindView(R.id.music_listview)
    ListView listView;
    private int listveiwId;


    private int musicIndexId;

    //判断音乐列表里播放的歌曲是否和我的歌单中的相同
//    private String musicSong , musicSonger;

    //搜索列表界面
    private SreachMusic sreachMusic;
    private List<SreachMusic> sreachMusicList;
    @BindView(R.id.music_Slistview)
    ListView SreachlistView;
    private MusicSreachAdapter musicSreachAdapter;
    @BindView(R.id.musics_Search_songs)
    AutoCompleteTextView songNameTextview;
    @BindView(R.id.musics_Sback)
    ImageView musicSreachBack;
    private int SlistveiwId;
    //获取每次输入的歌名
    private String editSongName = "";
    //判断是否前一次一致
    private boolean isEdit;
    //要重复播放地址
    private String SreachUrl;
    //判断播放顺序
    private boolean isMusic;
    private int isMusicFirst = 0;

    /**
     * 我的歌单页面
     */
    //用于判断是那个界面
    private int isMusicMenu = 0;

    private MyMusic myMusic;
    private List<MyMusic> myMusicList;
    private MyMusicAdapter myMusicAdapter;

    @BindView(R.id.music_Mylistview)
    ListView myMusicListView;
    @BindView(R.id.mymusic_back)
    ImageView MymusicBack;

    //listView的Item下标
    private int myMusicPosition;
    //我的歌单是否播放的是同一首歌
    private int myMusicIndex = -1;

    //标记当前播放到那首歌的下标了
    private int hronMusicIndex = -2;

    //判断是否是点击了我的歌单的item
    private boolean isItemHorn, isItemHornPause;

    //判断音乐列表里播放的歌曲是否和我的歌单中的相同
    private String myMusicSong, myMusicSonger;

    //判断我的歌单中是否和音乐列表里播放的歌曲的相同
    private String musicSongName, musicSongerName;

    //音乐列表找到我的歌单对应的歌曲排名值
    private int comperteMusicPosition;

    /**
     * 喜欢列表暂时存储区
     */
    private List<MyLoveMusic> myLoveMusicList = new ArrayList<>();
    //播放列表的下标
    private int loveMusicPosition = -1;
    //判断是否进行了暂时存储区的操作
    private boolean isloveMusic;


    //音乐播放顺序Popu对话框
    private View contentView;
    private CustomPopWindow popWindow;
    @BindView(R.id.musics_dialog)
    ImageView musicDialogImageview;

    //音乐菜单Popu对话框
    private View menuView;
    private CustomPopWindow menuPopWindow;
    @BindView(R.id.main_menu)
    ImageView musicMenuImageview;


    /**
     * 定时器和计时器---控制音乐播放的时间
     */
    //设置的定时小时和分钟
    private long timerHourMinute;
    private Thread timerThread; //倒计时的线程
    private Timer timer;
    private TimerTask timerTask;
    private boolean isStart = false; //判断是否开启定时
    //显示倒计时
    @BindView(R.id.music_timer)
    TextView musicTimerTextView;


    //底部控件
    @BindView(R.id.main_image)
    CircleImageView circleImageView;
    @BindView(R.id.main_song)
    TextView musicSongTextview;
    @BindView(R.id.main_songer)
    TextView musicSongerTextview;

    @BindView(R.id.main_play)
    ImageView musicPlayImageview;
    @BindView(R.id.main_next)
    ImageView musicNextImageview;

    @BindView(R.id.music_bottom_layout)
    RelativeLayout musicBottomLayout;


    //艺术字
    @BindView(R.id.paint_text)
    TextView mPaintText;
    @BindView(R.id.paint_text2)
    TextView mPaintText2;
    /**
     * 自定义签到
     */
    private List<StepBean> mStepBeans = new ArrayList<>();

    private int mPosition = -1;

    private int step = StepBean.STEP_UNDO;

    private StepBean stepBean;

    private int number;

    private int count;

    private String weatherInfo;


    /**
     * 网络请求子线程
     *
     * @param context
     * @param type
     */
    //图片网络请求的子线程
    private Thread image360Thread;

    //音乐Api网络请求的子线程
    private Thread musicThread;

    private ImageView imageView;

    public static void actionStart(Context context, String type) {
        Intent intent = new Intent(context, StepActivity.class);
        intent.putExtra("cityType", type);
        context.startActivity(intent);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, StepActivity.class);
        context.startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        Log.d(TAG, "onCreate");
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);

 /*       Toolbar toolbar = findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collaping_toolbar);
        imageView = findViewById(R.id.image_toolbarBg);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle("返回");
        */

        dyLoadingView.setVisibility(View.VISIBLE);
        dyLoadingView.start();

        setNetworkRequestThread(); //创建网络请求耗时操作的子线程

        setinitViewId();

        setAnimators();//设置动画

        setCameraDistance();//设置距离

        showBackground();

        showinitLrcView();

        setMyMusicData(); //我的歌单逻辑

//        setinitMusicWYyData();  //音乐列表请求操作

//        setAutoCompleteTextView(); //音乐搜索界面的操作


//        setMusicWYyData();

        //显示是否已打卡
        showClock();

        initData(step);

        initListener();

    }

    /**
     * 创建网络请求耗时操作的子线程
     */
    private void setNetworkRequestThread() {
        //网易云音乐歌曲请求子线程
        musicThread = new Thread(new Runnable() {
            @Override
            public void run() {
                //耗时操作
                Message message = new Message();
                message.what = 3;
                handler.sendMessage(message);
            }
        });
        musicThread.start();

        //360接口图片请求子线程
        image360Thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //耗时操作
                Message message = new Message();
                message.what = 2;
                handler.sendMessage(message);
            }
        });
        image360Thread.start();


    }

    /**
     * 加载我的歌单逻辑
     */
    private void setMyMusicData() {

        myMusicList = new ArrayList<>();
        setMyMusicAdapter(hronMusicIndex); //初始化我的歌单数据
        myMusicAdapter = new MyMusicAdapter(myMusicList, this, this);
        myMusicListView.setAdapter(myMusicAdapter);

        myMusicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                musicIndex = position;

//                //查询配置适配器，传入新的数据更新
                isItemHorn = true;   //判断是否点击这个item，如果点击再次进入歌单就会显示
                int hornPosition = MusicLabs.getMyMusicSongId(myMusicSong, myMusicSonger) - 1;

                hronMusicIndex = musicIndex;
                myMusicList.clear();
                setMyMusicAdapter(hronMusicIndex);
                myMusicAdapter.notifyDataSetChanged();

                MyMusic myMusic = myMusicList.get(musicIndex);
                String url = myMusic.getUri();
                String pic = myMusic.getPic();
                String name = myMusic.getSong();
                String songer = myMusic.getSonger();

                musicSongName = name;
                musicSongerName = songer;

                if (mp.isPlaying()) {
                    //播放状态
                    if (myMusicPosition == position) {
                        mp.pause();
                        discObjectAnimator.cancel();
                        musicImageview.setImageResource(R.mipmap.play);
                        musicPlayImageview.setImageResource(R.mipmap.play);
                        Log.d(TAG, "播放状态myMusicPosition == position");
                    } else {
                        if (hornPosition != position) {  //判断当前的点击的歌曲是否是正在播放的，在播放点击时暂停
                            //记录歌曲名
                            myMusicSong = name;
                            myMusicSonger = songer;

                            Mediaplay(url);
                            Glide.with(StepActivity.this).load(pic).into(circleImageView);
                            musicSongTextview.setText(name);
                            musicSongerTextview.setText(songer);
                            discObjectAnimator.start();
                            musicImageview.setImageResource(R.mipmap.pause);
                            musicPlayImageview.setImageResource(R.mipmap.pause);
                            Log.d(TAG, "播放状态myMusicPosition != position");
                        } else {
                            mp.pause();
                            discObjectAnimator.cancel();
                            musicImageview.setImageResource(R.mipmap.play);
                            musicPlayImageview.setImageResource(R.mipmap.play);
                        }
                        Log.d(TAG, "播放状态hornPosition != position");
                    }
                } else {
                    //暂停状态
                    musicImageview.setImageResource(R.mipmap.pause);
                    musicPlayImageview.setImageResource(R.mipmap.pause);
                    if (myMusicPosition == position) {
                        mp.start();
                        discObjectAnimator.start();
                        Log.d(TAG, "暂停状态myMusicPosition == position");
                    } else if (myMusicPosition == -1) {
                        if (hornPosition != position) {
                            //记录歌曲名
                            myMusicSong = name;
                            myMusicSonger = songer;

                            Mediaplay(url);
                            Glide.with(StepActivity.this).load(pic).into(circleImageView);
                            musicSongTextview.setText(name);
                            musicSongerTextview.setText(songer);
                            discObjectAnimator.start();
                            Log.d(TAG, "暂停状态myMusicPosition != position");
                        } else {
                            mp.start();
                            discObjectAnimator.start();
                        }
                        Log.d(TAG, "暂停状态myMusicPosition == -1");
                    } else {
                        if (hornPosition != position) {
                            //记录歌曲名
                            myMusicSong = name;
                            myMusicSonger = songer;

                            Mediaplay(url);
                            Glide.with(StepActivity.this).load(pic).into(circleImageView);
                            musicSongTextview.setText(name);
                            musicSongerTextview.setText(songer);
                            discObjectAnimator.start();
                        } else {
                            mp.start();
                            discObjectAnimator.start();
                        }
                        Log.d(TAG, "暂停状态hornPosition != position");
                    }
                }
                myMusicPosition = position;
                myMusicIndex = position;

            }
        });

        myMusicListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {

                setAlertDialogRemoveItem(position);

                return true;
            }
        });
    }

    /**
     * 配置我的歌单适配器
     */
    private void setMyMusicAdapter(int position) {

        //获取的存储后的歌曲，并显示在的我的歌单里
        List<MyMusic> myMusics = MusicLabs.getMyMusicList();

        int id = -1;
        if (myMusics.size() > 0 && myMusics != null) {
            for (MyMusic music : myMusics) {
                id++;
                String song = music.getSong();
                String songer = music.getSonger();
                String songUrl = music.getUri();
                int point = R.mipmap.music_remove;
                String pic = music.getPic();
                myMusic = new MyMusic(point, song, songer, songUrl, pic, id, position);
                //设置下标
                myMusicList.add(myMusic);
            }
        }

    }


    //搜索界面
    private void setAutoCompleteTextView() {

        songNameTextview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable edit) {
                String song = edit.toString();
                if (!editSongName.equals(song) || TextUtils.isEmpty(editSongName)) {
                    editSongName = song;
                    SlistveiwId = -1;
                    isEdit = true;
                } else {
                    isEdit = false;
                }
                setinitMusicSreachWYyData(song);
            }
        });


    }


    /**
     * 选择播放方式
     */
    private void setinitViewId() {
        //初始化
        timer = new Timer();
        contentView = LayoutInflater.from(this).inflate(R.layout.popwin_share, null);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popWindow != null) {
                    popWindow.dissmiss();
                }
                switch (view.getId()) {
                    case R.id.layout_single:
                        //单曲循环
                        isMusic = true;
                        StepUtillty.setisPlayWay(StepActivity.this, true);
                        isMusicFirst = 1;
                        StyleableToast.makeText(StepActivity.this, "单曲循环", Toast.LENGTH_LONG, R.style.mytoast).show();
                        break;
                    case R.id.layout_order:
                        //顺序播放
                        isMusic = false;
                        StepUtillty.setisPlayWay(StepActivity.this, false);
                        isMusicFirst = 1;
                        StyleableToast.makeText(StepActivity.this, "顺序循环", Toast.LENGTH_LONG, R.style.mytoast).show();
                        break;
                    case R.id.layout_mv:
                        MusicMvActivity.actionStart(StepActivity.this);
                        break;
                    default:
                        break;
                }
            }
        };
        contentView.findViewById(R.id.layout_single).setOnClickListener(listener);
        contentView.findViewById(R.id.layout_order).setOnClickListener(listener);
        contentView.findViewById(R.id.layout_mv).setOnClickListener(listener);

        menuView = LayoutInflater.from(this).inflate(R.layout.popwin_menu, null);
        View.OnClickListener listener1 = new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if (menuPopWindow != null) {
                    menuPopWindow.dissmiss();
                }
                switch (view.getId()) {
                    case R.id.pop_layout_timer:
                        //弹出时间选择对话框
                        showTimePickerDialog();
                        break;
                    case R.id.pop_layout_close:
                        if (mp.isPlaying()) {
                            onStartHandlerThread(0);
                            onCloseHanderTimer();
                        }
                        break;
                    case R.id.music_layout_car:
                        musicsMyTitleRelativeLayout.setVisibility(View.VISIBLE);
                        myMusicListView.setVisibility(View.VISIBLE);
                        if (isMusicMenu == 1 || isMusicMenu == 0) {
                            listView.setVisibility(View.GONE);
                            musicsTitleRelativeLayout.setVisibility(View.GONE);
                            musicsMyTitleRelativeLayout.setBackgroundResource(R.color.music_bg);
                            menu = STEP_MY_MUSIC;
                        } else if (isMusicMenu == 2) {
                            SreachlistView.setVisibility(View.GONE);
                            musicsStitlerelativeLayout.setVisibility(View.GONE);
                            musicsMyTitleRelativeLayout.setBackgroundResource(R.color.music_search);
                            menu = STEP_MY_SEARCH_MUSIC;
                        }

                        //如果在我的歌单界面，音乐播放的两个下标不相等的话
                        if (myMusicPosition != myMusicIndex) {
                            //让它重置再播放
                            myMusicPosition = -1;
                        }

                        isMusicFirst = 4;


                        //重新配置数据，判断喇叭
                        if (isItemHorn) {
                            myMusicList.clear();
                            hronMusicIndex = MusicLabs.getMyMusicSongId(myMusicSong, myMusicSonger) - 1;
                            if (!MusicLabs.CompreteMusicName(myMusicSong, myMusicSonger)) {
                                setMyMusicAdapter(MusicLabs.getMyMusicSongId(myMusicSong, myMusicSonger) - 1);
                                Log.d(TAG, myMusicSong + "----" + myMusicSonger);
                                Log.d(TAG, "刷新适配器--更新数据" + (MusicLabs.getMyMusicSongId(myMusicSong, myMusicSonger) - 1));
                            } else {
                                setMyMusicAdapter(hronMusicIndex);
                                Log.d(TAG, myMusicSong + "----" + myMusicSonger);
                                Log.d(TAG, "刷新适配器--不更新数据" + hronMusicIndex);
                            }
                            //刷新适配器--更新数据
                            myMusicAdapter.notifyDataSetChanged();
                        } else {
                            myMusicList.clear();
                            setMyMusicAdapter(-1);
                            //刷新适配器--更新数据
                            myMusicAdapter.notifyDataSetChanged();
                        }
                        break;
                    default:
                        break;
                }
            }
        };
        menuView.findViewById(R.id.pop_layout_timer).setOnClickListener(listener1);
        menuView.findViewById(R.id.pop_layout_close).setOnClickListener(listener1);
        menuView.findViewById(R.id.music_layout_car).setOnClickListener(listener1);
    }

    //弹出时间对话框
    private void showTimePickerDialog() {
        long oneDay = 1000 * 60 * 60 * 24L; //二十四小时
        TimePickerDialog mDialogAll = new TimePickerDialog.Builder()
                .setCallBack(this)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("选择时间")
                .setHourText("时")
                .setMinuteText("分钟")
                .setCyclic(true) //设置是否可循环
                .setMinMillseconds(0) //最小日期和时间
                .setMaxMillseconds(System.currentTimeMillis() + oneDay) //最大日期和时间
                .setCurrentMillseconds(0)
                .setType(Type.HOURS_MINS)
                .setThemeColor(getResources().getColor(R.color.music_pop_bg))
                .setWheelItemTextNormalColor(getResources().getColor(R.color.c_3D3C3C))//未选中的文本颜色
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.music_pop_text_color))//当前文本颜色
                .setWheelItemTextSize(12)
                .setToolBarTextColor(getResources().getColor(R.color.c_3D3C3C))
                .build();

        mDialogAll.show(getSupportFragmentManager(), "ALL");
    }


    //初始化艺术字
    private void showinitLrcView() {

        String fonts = "fonts/paint.ttf";
        Typeface typeface = Typeface.createFromAsset(getAssets(), fonts);
        mPaintText.setTypeface(typeface);
        mPaintText2.setTypeface(typeface);
        songTextView.setTypeface(typeface);
    }

    private void setinitMusicSreachWYyData(String songName) {

        String url = SreachGsonMusic.getSreachMusic(songName, 50);
        HttpUtility.AsynchttpRequest(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                SreachMusicApi sreachMusicApi = SreachGsonMusic.handleMusicResponse(responseString);
                if (sreachMusicApi != null) {
                    initSreachMusic(sreachMusicApi);
                }
            }
        });
    }

    private void initSreachMusic(final SreachMusicApi sreachMusicApi) {
        sreachMusicList = new ArrayList<>();
        List<MyMusic> myMusics = MusicLabs.getMyMusicList();
        for (SreachSongs songs : sreachMusicApi.sreachSongsList) {
            String song = songs.name;
            String songer = songs.singer;
            String songUrl = songs.url;
            int imageadd = R.mipmap.music_icon;
            int point = 0;
            String pic = songs.pic;

            boolean isLove = false;

            //用来判断我的歌单中是否添加了喜欢的歌曲，有就显示红爱心，没有就灰爱心
            for (MyMusic myMusic : myMusics) {
                if (song.equals(myMusic.getSong()) && songer.equals(myMusic.getSonger())) {
                    isLove = true;
                    break;
                } else {
                    isLove = false;
                }
            }

            sreachMusic = new SreachMusic(imageadd, point, song, songer, songUrl, pic, isLove);
            sreachMusicList.add(sreachMusic);
        }
        musicSreachAdapter = new MusicSreachAdapter(sreachMusicList, StepActivity.this, StepActivity.this);
        SreachlistView.setAdapter(musicSreachAdapter);

        SreachlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (isEdit) {
                    musicIndex = 0;
                }
                musicIndex = position;
                SreachSongs songs = sreachMusicApi.sreachSongsList.get(musicIndex);
                String url = songs.url;
                String pic = songs.pic;
                String name = songs.name;
                String songer = songs.singer;
                //重复循环
                SreachUrl = url;
                isMusicFirst = 2;
                /*
                用于恢复我的歌单界面的下标为-1，只要点击了item就恢复到原来的下标值，
                重新让我的歌单重置歌曲播放
                 */
                myMusicIndex = -1;

                if (mp.isPlaying()) {
                    //播放状态
                    if (SlistveiwId == position) {
                        mp.pause();
                        discObjectAnimator.cancel();
                        musicImageview.setImageResource(R.mipmap.play);
                        musicPlayImageview.setImageResource(R.mipmap.play);
                    } else {
                        Mediaplay(url);
                        Glide.with(StepActivity.this).load(pic).into(circleImageView);
                        musicSongTextview.setText(name);
                        musicSongerTextview.setText(songer);
                        discObjectAnimator.start();
                        musicImageview.setImageResource(R.mipmap.pause);
                        musicPlayImageview.setImageResource(R.mipmap.pause);
                    }
                } else {
                    //暂停状态
                    musicImageview.setImageResource(R.mipmap.pause);
                    musicPlayImageview.setImageResource(R.mipmap.pause);
                    if (SlistveiwId == position) {
                        mp.start();
                        discObjectAnimator.start();
                    } else if (SlistveiwId == -1) {
                        Mediaplay(url);
                        Glide.with(StepActivity.this).load(pic).into(circleImageView);
                        musicSongTextview.setText(name);
                        musicSongerTextview.setText(songer);
                        discObjectAnimator.start();
                    } else {
                        Mediaplay(url);
                        Glide.with(StepActivity.this).load(pic).into(circleImageView);
                        musicSongTextview.setText(name);
                        musicSongerTextview.setText(songer);
                        discObjectAnimator.start();
                    }
                }
                SlistveiwId = position;
            }
        });
    }


    private void setinitMusicWYyData() {
        HttpUtility.AsynchttpRequest(GsonMusicApi.WY_MUSIC_URL, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                MusicApi musicApi = GsonMusicApi.handleMusicResponse(responseString);
                if (musicApi != null) {
                    Songs songs = musicApi.songsList.get(musicIndex);
                    String songName = songs.name;
                    String songer = songs.singer;
                    String songPic = songs.pic;
                    String lyr = songs.lrc + "&type=.lrc";
                    url = songs.url;
                    //设置歌曲名字
                    songTextView.setText(songName + '\t' + "-" + '\t' + songer);
                    musicSongTextview.setText(songName);
                    musicSongerTextview.setText(songer);
                    Glide.with(StepActivity.this).load(songPic).into(circleImageView);
                    initMediaplay(url);
                    initMusicdata(musicApi);
                    scrollView.setVisibility(View.VISIBLE);
                    //加载控件
                    if (dyLoadingView != null) {
//                        dyLoadingView.stop();
                        dyLoadingView.setVisibility(View.GONE);
                    }

                }

            }
        });


    }

    private void setMusicWYyData() {
        HttpUtility.AsynchttpRequest(GsonMusicApi.WY_MUSIC_URL, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                MusicApi musicApi = GsonMusicApi.handleMusicResponse(responseString);
                if (musicApi != null) {
                    getMusicListResponse(musicApi, musicIndex);
                }
            }
        });

    }

    /**
     * 初始化音乐数据
     *
     * @param musicApi
     */
    private void initMusicdata(final MusicApi musicApi) {

        musicList = new ArrayList<>();
        setInitMusicDataAdapter(musicApi);
        adapter = new MusicAdapter(musicList, this, StepActivity.this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                musicIndex = position;
                isMusicFirst = 1;
                /*
                用于恢复我的歌单界面的下标为-1，只要点击了item就恢复到原来的下标值，
                重新让我的歌单重置歌曲播放
                 */
                myMusicIndex = -1;

                if (mp.isPlaying()) {
                    //播放状态
                    if (listveiwId == position) {
                        mp.pause();
                        discObjectAnimator.cancel();
                        musicImageview.setImageResource(R.mipmap.play);
                        musicPlayImageview.setImageResource(R.mipmap.play);
                    } else {
                        getMusicListResponse(musicApi, musicIndex);
                        discObjectAnimator.start();
                        musicImageview.setImageResource(R.mipmap.pause);
                        musicPlayImageview.setImageResource(R.mipmap.pause);
                    }
                } else {
                    //暂停状态
                    musicImageview.setImageResource(R.mipmap.pause);
                    musicPlayImageview.setImageResource(R.mipmap.pause);
                    if (listveiwId == position) {
                        mp.start();
                        discObjectAnimator.start();
                    } else if (listveiwId == -1) {
                        getMusicListResponse(musicApi, musicIndex);
                        discObjectAnimator.start();
                    } else {
                        getMusicListResponse(musicApi, musicIndex);
                        discObjectAnimator.start();
                    }
                }
                listveiwId = position;
                musicIndexId = position;
            }
        });
    }

    /**
     * 初始化音乐的适配器数据
     *
     * @param musicApi
     */
    private void setInitMusicDataAdapter(MusicApi musicApi) {
        List<MyMusic> myMusics = MusicLabs.getMyMusicList();
        for (int i = 0; i < musicApi.songsList.size(); i++) {
            Songs songs = musicApi.songsList.get(i);
            String song = songs.name;
            String songer = songs.singer;
            String songUrl = songs.url;
            String pic = songs.pic;
            int imageadd = R.mipmap.music_icon;
            int point = 0;
            boolean isLove = false;
            int myMusicId = 0;


            //用来判断我的歌单中是否添加了喜欢的歌曲，有就显示红爱心，没有就灰爱心
            for (int j = 0; j < myMusics.size(); j++) {
                MyMusic myMusic = myMusics.get(j);
//                Log.d(TAG,song +"---1---"+songer);
//                Log.d(TAG,myMusic.getSong() +"---2---"+myMusic.getSonger());
                if (song.equals(myMusic.getSong()) && songer.equals(myMusic.getSonger())) {
                    isLove = true;
                    //如果成功了就跳出来不再执行下面循环
                    myMusicId = j + 1;
                    break;
                } else {
                    isLove = false;
                }

            }

            //用来判断是否当前播放的存在已音乐列表
            for (int z = 0; z < 2; z++) {
                if (song.equals(musicSongName) && songer.equals(musicSongerName)) {
                    listveiwId = i;
                    isItemHornPause = true;
                    Log.d(TAG, listveiwId + "+break");
                    Log.d(TAG, musicSongName + "----" + musicSongerName);
                    Log.d(TAG, listveiwId + "----(i)");
                    break;
                }

            }

            music = new Music(imageadd, point, song, songer, songUrl, pic, isLove, myMusicId);
            musicList.add(music);

        }

    }


    private void getMusicListResponse(MusicApi musicApi, int index) {
        Songs songs = musicApi.songsList.get(index);
        Music music = musicList.get(index);
        url = music.getUri();
        listveiwId = index;
        Mediaplay(url);
        String songName = music.getSong();
        String songer = music.getSonger();
        String songPic = songs.pic;
        Glide.with(StepActivity.this).load(songPic).into(circleImageView);
        //设置歌曲名字
        songTextView.setText(songName + '\t' + "-" + '\t' + songer);
        musicSongTextview.setText(songName);
        musicSongerTextview.setText(songer);

        //记录歌曲名
        myMusicSong = songName;
        myMusicSonger = songer;

        if (!MusicLabs.CompreteMusicName(songName, songer)) {
            isItemHorn = true;

            musicSongName = songName;
            musicSongerName = songer;
        } else {
            isItemHorn = false;
        }
        comperteMusicPosition = MusicLabs.getMyMusicSongId(myMusicSong, myMusicSonger);

        Log.d(TAG, comperteMusicPosition + "---" + myMusicSong + "-----" + myMusicSonger);
    }

    /**
     * 初始化音乐
     *
     * @param uri
     */
    private void initMediaplay(String uri) {
        mp.reset();
        try {
            mp.setDataSource(uri);
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放音乐
     *
     * @param uri
     */
    private void Mediaplay(String uri) {
        mp.reset();
        try {
            mp.setDataSource(uri);
            mp.prepare();
            mp.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showBackground() {
        weatherInfo = getIntent().getStringExtra("cityType");
        GsonUtility.showSetTitleLayout(StepActivity.this, weatherInfo, SteprelativeLayout);

        //front页面日期
        number = StepUtillty.getNumber(this);
        dayTextview.setText(DateString.getDateYyd().get(0));
        ymTextview.setText(DateString.getDateYyd().get(1));
        //back页面日期
        weekTextview.setText(DateString.getDateYyd().get(2));
        ymBackTextview.setText(DateString.getDateYyd().get(1));
        dayBackTextview.setText(DateString.getDateYyd().get(0));
        nightweekTextview.setText(DateString.getDateYyd().get(2));
        nightymBackTextview.setText(DateString.getDateYyd().get(1));
        nightdayBackTextview.setText(DateString.getDateYyd().get(0));

    }

    //缓存图片
    private void glideImage(SharedPreferences images, int index, ImageView imageView) {
        String pic = images.getString(index + "", null);
        Glide.with(this).load(pic).into(imageView);
    }

    //判断是否当天打卡方法
    @SuppressLint("ResourceAsColor")
    private void showClock() {
        String date = StepUtillty.getDate(StepActivity.this);
        String dateNight = StepUtillty.getDateNight(StepActivity.this);
        if (BingPicUtility.getNowDate().equals(date)) {
            mTvSign.setEnabled(false);
            mTvSign.setChecked(true);
            mTvSign.setText("今日已打卡");
            animTitle.setText("恭喜获取日签卡");
            mainFlCardFront.bringToFront();
            fipCard(mainFlCardFront, mainFlCardBack); //android 怎么解析歌词
        } else {
            fipCard(mainFlCardBack, mainFlCardFront); //android 怎么解析歌词
            mTvSign.setEnabled(true);
            mTvSign.setChecked(false);
        }

        //晚安打卡
        if (BingPicUtility.getNowDate().equals(dateNight)) {
            mNightTvSign.setEnabled(false);
            mNightTvSign.setChecked(true);
            mNightTvSign.setText("晚安已打卡");
            nightTitle.setText("恭喜获取晚安卡");
            mainFlCardFrontNight.bringToFront();
            fipCardNight(mainFlCardFrontNight, mainFlCardBackNight);
        } else {
            mNightTvSign.setEnabled(true);
            mNightTvSign.setChecked(false);
            fipCardNight3(mainFlCardBackNight, mainFlCardFrontNight);
        }
    }


    //初始化数据
    private void initData(int step) {

        int start = StepUtillty.setStartNumber(number);

        for (int i = start; i < start + 7; i++) {
            /**
             *  8 9 10 11 12 13 14
             */
            //显示打卡天数
            count++;
            if ((count + start) <= number) {
                stepBean = new StepBean(StepBean.STEP_COMPLETED, i + 1);
                mStepBeans.add(stepBean);
            } else {
                stepBean = new StepBean(StepBean.STEP_UNDO, i + 1);
                mStepBeans.add(stepBean);
            }

        }
        mstepsView.setStepNum(mStepBeans);
        mstepsView.startSignAnimation(number);
    }

    //打卡事件
    private void initListener() {
        mTvSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPosition = number % 7;
                mstepsView.startSignAnimation(mPosition);
                mTvSign.setChecked(true);
                int start = StepUtillty.setStartNumber(number);
                for (int i = start; i < start + 7; i++) {
                    StepBean stepBean = new StepBean();
                    if (i < mPosition + start) {
                        stepBean.setState(StepBean.STEP_COMPLETED);
                    } else if (i == mPosition + start) {
                        stepBean.setState(StepBean.STEP_COMPLETED);
                    } else {
                        stepBean.setState(StepBean.STEP_UNDO);
                    }
                    //显示打卡天数
                    stepBean.setNumber(i + 1);
                    mStepBeans.add(stepBean);
                    mstepsView.setStepNum(mStepBeans);
                }

                if (mPosition == 6) {
                    mPosition = -1;
                }
                mTvSign.setEnabled(false);
                mTvSign.setText("今日已打卡");
                animTitle.setText("恭喜获取日签卡");
                //储存打卡天数和当天打卡的日期
                int number = StepUtillty.getNumber(StepActivity.this) + 1;
                StepUtillty.setNumber(StepActivity.this, number);
                StepUtillty.setDate(StepActivity.this, BingPicUtility.getNowDate());

                onfipCard();
            }
        });

        mNightTvSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour = DateString.getHourMm().get(0);
                //只能在晚上八点到十二点打卡
                if (hour >= 8 && hour <= 24) {
                    mNightTvSign.setChecked(true);
                    mNightTvSign.setEnabled(false);
//                    mNightButton.setEnabled(false);
                    mNightTvSign.setText("晚安已打卡");
                    nightTitle.setText("恭喜获取晚安卡");
                    StepUtillty.setDateNight(StepActivity.this, BingPicUtility.getNowDate());
                    fipCardNight2(mainFlCardBackNight, mainFlCardFrontNight);
                }
            }
        });
    }

    //设置动画
    private void setAnimators() {

        //设置卡片的高度
        mainFlContainer.getLayoutParams().height = Utility.getWindowHeight(this) / 2;
        mainFlContainerNight.getLayoutParams().height = Utility.getWindowHeight(this) / 2;

        mRightOutSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.anim_out);
        mLeftOutSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.anim_in);
        //设置点击事件
        mRightOutSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mainFlContainer.setClickable(false);
            }
        });

        mLeftOutSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mainFlContainer.setClickable(true);
            }
        });

        //加载图片旋转资源
        animator();
    }

    //改变视角距离，贴近屏幕
    private void setCameraDistance() {
        int distance = 16000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mainFlCardFront.setCameraDistance(scale);
        mainFlCardBack.setCameraDistance(scale);
        mainFlCardBackNight.setCameraDistance(scale);
        mainFlCardFrontNight.setCameraDistance(scale);
    }

    //点击卡片翻转卡片
    public void fipCard(final RelativeLayout mainFlCardBack, final RelativeLayout mainFlCardFront) {
        mainFlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mTvSign.isEnabled()) {
                    //正面朝上
                    if (!mIsShowBack) {
                        if (!mRightOutSet.isRunning() && !mLeftOutSet.isRunning()) {
                            mRightOutSet.setTarget(mainFlCardBack);
                            mLeftOutSet.setTarget(mainFlCardFront);
                            mRightOutSet.start();
                            mLeftOutSet.start();
                            mIsShowBack = true;
                        }
                    } else {
                        if (!mLeftOutSet.isRunning() && !mRightOutSet.isRunning()) {
                            mRightOutSet.setTarget(mainFlCardFront);
                            mLeftOutSet.setTarget(mainFlCardBack);
                            mRightOutSet.start();
                            mLeftOutSet.start();
                            mIsShowBack = false;
                        }
                    }
                }
            }
        });

    }

    public void onfipCard() {
        if (!mTvSign.isEnabled()) {
            //正面朝上
            if (!mIsShowBack) {
                if (!mRightOutSet.isRunning() && !mLeftOutSet.isRunning()) {
                    mRightOutSet.setTarget(mainFlCardBack);
                    mLeftOutSet.setTarget(mainFlCardFront);
                    mRightOutSet.start();
                    mLeftOutSet.start();
                    mIsShowBack = true;
                }
            } else {
                if (!mLeftOutSet.isRunning() && !mRightOutSet.isRunning()) {
                    mRightOutSet.setTarget(mainFlCardFront);
                    mLeftOutSet.setTarget(mainFlCardBack);
                    mRightOutSet.start();
                    mLeftOutSet.start();
                    mIsShowBack = false;
                }
            }
        }
    }

    //晚安点击卡片翻转卡片
    public void fipCardNight(final RelativeLayout mainFlCardBackNight, final RelativeLayout mainFlCardFrontNight) {
        mainFlContainerNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour = DateString.getHourMm().get(0);
                if (hour >= 8 && hour <= 24) {
                    //正面朝上
                    if (!mIsShowBackNight) {
                        if (!mRightOutSet.isRunning() && !mLeftOutSet.isRunning()) {
                            mRightOutSet.setTarget(mainFlCardBackNight);
                            mLeftOutSet.setTarget(mainFlCardFrontNight);
                            mRightOutSet.start();
                            mLeftOutSet.start();
                            mIsShowBackNight = true;
                            musicImageview.setEnabled(false);
//                            mNightButton.setEnabled(false);
                        }
                        mainFlCardFrontNight.bringToFront();
                    } else {
                        if (!mLeftOutSet.isRunning() && !mRightOutSet.isRunning()) {
                            mRightOutSet.setTarget(mainFlCardFrontNight);
                            mLeftOutSet.setTarget(mainFlCardBackNight);
                            mRightOutSet.start();
                            mLeftOutSet.start();
                            mIsShowBackNight = false;
                            musicImageview.setEnabled(true);
//                            mNightButton.setEnabled(true);
                        }
                        mainFlCardBackNight.bringToFront();
                    }
                }
            }
        });


    }

    //晚安点击卡片翻转卡片
    public void fipCardNight2(final RelativeLayout mainFlCardBackNight, final RelativeLayout mainFlCardFrontNight) {
        //正面朝上
        if (!mIsShowBackNight2) {
            if (!mRightOutSet.isRunning() && !mLeftOutSet.isRunning()) {
                mRightOutSet.setTarget(mainFlCardBackNight);
                mLeftOutSet.setTarget(mainFlCardFrontNight);
                mRightOutSet.start();
                mLeftOutSet.start();
                mIsShowBackNight2 = true;
                musicImageview.setEnabled(true);
            }
            mainFlCardFrontNight.bringToFront();
        } else {
            if (!mLeftOutSet.isRunning() && !mRightOutSet.isRunning()) {
                mRightOutSet.setTarget(mainFlCardFrontNight);
                mLeftOutSet.setTarget(mainFlCardBackNight);
                mRightOutSet.start();
                mLeftOutSet.start();
                mIsShowBackNight2 = false;
                musicImageview.setEnabled(false);
            }
            mainFlCardBackNight.bringToFront();
        }
    }

    //晚安点击卡片翻转卡片
    public void fipCardNight3(final RelativeLayout mainFlCardBackNight, final RelativeLayout mainFlCardFrontNight) {
        //正面朝上
        mainFlContainerNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mIsShowBackNight2) {
                    if (!mRightOutSet.isRunning() && !mLeftOutSet.isRunning()) {
                        mRightOutSet.setTarget(mainFlCardBackNight);
                        mLeftOutSet.setTarget(mainFlCardFrontNight);
                        mRightOutSet.start();
                        mLeftOutSet.start();
                        mIsShowBackNight2 = true;
                        musicImageview.setEnabled(true);
//                            mNightButton.setEnabled(true);
                    }
                    mainFlCardFrontNight.bringToFront();
                } else {
                    if (!mLeftOutSet.isRunning() && !mRightOutSet.isRunning()) {
                        mRightOutSet.setTarget(mainFlCardFrontNight);
                        mLeftOutSet.setTarget(mainFlCardBackNight);
                        mRightOutSet.start();
                        mLeftOutSet.start();
                        mIsShowBackNight2 = false;

                        musicImageview.setEnabled(false);
//                            mNightButton.setEnabled(false);
                    }
                    mainFlCardBackNight.bringToFront();
                }
            }
        });

    }


    /**
     * 获取圆角位图的方法
     *
     * @param bitmap 需要转化成圆角的位图
     * @param pixels 圆角的度数，数值越大，圆角越大
     * @return 处理后的圆角位图
     */
    public static Bitmap toRoundCornerImage(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;
        // 抗锯齿
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        mstepsView.startSignAnimation(mPosition);
        //判断是否第一次进入---然后选择音乐播放方式

        setOnClickLiner();
        Log.d(TAG, "onResume");

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //针对singleTask启动模式，重新设置Intent
        setIntent(intent);
        Log.d(TAG, "onNewIntent");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");

    }


    @OnClick(R.id.step_back)
    public void onClickBack(View view) {
        Intent intent = new Intent(StepActivity.this, WeatherActivity.class);
        intent.putExtra("page", -2);
        startActivity(intent);
    }

    //点击播放顺序事件
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick(R.id.musics_dialog)
    public void onClickDialog(View view) {
        popWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .setTouchable(true)
                .setOutsideTouchable(true)
                .enableBackgroundDark(false)//弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .setAnimationStyle(R.style.AnimTools)
                .create()
                .showAsDropDown(musicDialogImageview, -250, 20, 0);

    }

    /**
     * 音乐的播放方式
     *
     * @param isMusic
     */
    private void musicPlayingWay(boolean isMusic) {
        if (isMusic) {
            setMusicWYyData();
        } else {
            if (++musicIndex == 199) {
                musicIndex = 0;
            } else {
                listveiwId = musicIndex;
            }
            //播放歌曲
            musicImageview.setImageResource(R.mipmap.pause);
            musicPlayImageview.setImageResource(R.mipmap.pause);
            setMusicWYyData();
        }
    }

    /**
     * 我的歌单的播放方式
     *
     * @param isMusic
     */
    private void myMusicPlayingWay(boolean isMusic) {
        if (isMusic) {
            MyMusic myMusic = myMusicList.get(musicIndex);
            Mediaplay(myMusic.getUri());
        } else {
            if (++musicIndex == myMusicList.size()) {
                musicIndex = 0;
            } else {
                myMusicPosition = musicIndex;
            }
            //播放歌曲
            musicImageview.setImageResource(R.mipmap.pause);
            musicPlayImageview.setImageResource(R.mipmap.pause);
            MyMusic myMusic = myMusicList.get(musicIndex);
            Glide.with(StepActivity.this).load(myMusic.getPic()).into(circleImageView);
            //设置歌曲名字
            songTextView.setText(myMusic.getSong() + '\t' + "-" + '\t' + myMusic.getSonger());
            musicSongTextview.setText(myMusic.getSong());
            musicSongerTextview.setText(myMusic.getSonger());
            Mediaplay(myMusic.getUri());

            //重新配置数据，判断喇叭
            myMusicList.clear();
            hronMusicIndex = musicIndex;
            setMyMusicAdapter(hronMusicIndex);
            myMusicAdapter.notifyDataSetChanged();
        }
    }

    private void setOnClickLiner() {
        musicImageview.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if (mp.isPlaying()) {
                    mp.pause();
                    discObjectAnimator.cancel();
                    musicImageview.setImageResource(R.mipmap.play);
                    musicPlayImageview.setImageResource(R.mipmap.play);
                } else {
                    if (url != null) {
                        mp.start();
                        discObjectAnimator.start();
                        musicImageview.setImageResource(R.mipmap.pause);
                        musicPlayImageview.setImageResource(R.mipmap.pause);
                    }
                }
            }
        });

        musicPlayImageview.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if (mp.isPlaying()) {
                    mp.pause();
                    discObjectAnimator.cancel();
                    musicImageview.setImageResource(R.mipmap.play);
                    musicPlayImageview.setImageResource(R.mipmap.play);
                } else {
                    if (url != null) {
                        mp.start();
                        discObjectAnimator.start();
                        musicImageview.setImageResource(R.mipmap.pause);
                        musicPlayImageview.setImageResource(R.mipmap.pause);
                    }
                }
            }
        });

        musicCutImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (++musicIndex == 199) {
                    musicIndex = 0;
                }
                //播放歌曲
                musicImageview.setImageResource(R.mipmap.pause);
                musicPlayImageview.setImageResource(R.mipmap.pause);
                setMusicWYyData();
            }
        });

        musicNextImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //校正播放歌曲的下标值
                if (!MusicLabs.CompreteMusicName(myMusicSong, myMusicSonger)) {
                    musicIndex = MusicLabs.getMyMusicSongId(myMusicSong, myMusicSonger) - 1;
                    myMusicSong = "111";
                    myMusicSonger = "111";
                    Log.d(TAG, "校正播放歌曲的下标值");
                }
                //播放列表里的1歌曲
                if (isMusicFirst == 3) {
                    //播放暂存区喜欢的歌曲
                    playLoveMusic();
                } else if (isMusicFirst == 4) {
                    if (++musicIndex == myMusicList.size()) {
                        musicIndex = 0;
                    } else {
                        myMusicPosition = musicIndex;
                    }
                    //播放歌曲
                    musicImageview.setImageResource(R.mipmap.pause);
                    musicPlayImageview.setImageResource(R.mipmap.pause);
                    MyMusic myMusic = myMusicList.get(musicIndex);
                    Glide.with(StepActivity.this).load(myMusic.getPic()).into(circleImageView);
                    //设置歌曲名字
                    songTextView.setText(myMusic.getSong() + '\t' + "-" + '\t' + myMusic.getSonger());
                    musicSongTextview.setText(myMusic.getSong());
                    musicSongerTextview.setText(myMusic.getSonger());
                    Mediaplay(myMusic.getUri());

                    //重新配置数据，判断喇叭
                    myMusicList.clear();
                    hronMusicIndex = musicIndex;
                    setMyMusicAdapter(hronMusicIndex);
                    myMusicAdapter.notifyDataSetChanged();
                } else {
                    if (++musicIndex == 199) {
                        musicIndex = 0;
                    }
                    //播放歌曲
                    musicImageview.setImageResource(R.mipmap.pause);
                    musicPlayImageview.setImageResource(R.mipmap.pause);
                    setMusicWYyData();
                }

            }
        });

        //设置播放顺序方式
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Log.d(TAG, "isMusicFirst=" + isMusicFirst);
                switch (isMusicFirst) {
                    case 0:
                        boolean isMusicFirst = StepUtillty.getisPlayWay(StepActivity.this);
                        musicPlayingWay(isMusicFirst);
                        Log.d(TAG, "单曲--0");
                        break;
                    case 1:
                        //循环播放
                        musicPlayingWay(isMusic);
                        Log.d(TAG, "顺序播放--1");
                        break;
                    case 2:
                        //搜索音乐重复播放
                        Mediaplay(SreachUrl);
                        break;
                    case 3:
                        //播放暂存区喜欢的歌曲
                        playLoveMusic();
                        Log.d(TAG, "播放暂存区喜欢的歌曲--1");
                        break;
                    case 4:
                        //我的歌单
                        myMusicPlayingWay(isMusic);
                        break;
                    default:
                        break;
                }
            }
        });


        mNightButton.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                LayoutTitle(R.color.music_bg);
                mClockLayout.setVisibility(View.GONE);
                mMusicLayout.setVisibility(View.VISIBLE);
                menu = STEP_MUSCI;
            }
        });

        musicSreachBack.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                listveiwId = -1;
                if (isloveMusic) {
                    isMusicFirst = 3;
                } else {
                    isMusicFirst = 1;
                }
                isMusicMenu = 1;
                LayoutTitle(R.color.music_bg);
                mClockLayout.setVisibility(View.GONE);
                mMusicLayout.setVisibility(View.VISIBLE);
                musicsTitleRelativeLayout.setVisibility(View.VISIBLE);
                musicsStitlerelativeLayout.setVisibility(View.GONE);
                SreachlistView.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                menu = STEP_MUSCI;
            }
        });

        musicMenuImageview.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                int width = Utility.getWindowWidth(StepActivity.this);
                int height = Utility.getWindowHeight(StepActivity.this);
                menuPopWindow = new CustomPopWindow.PopupWindowBuilder(StepActivity.this)
                        .setView(menuView)
                        .setTouchable(true)
                        .setOutsideTouchable(true)
                        .enableBackgroundDark(false)
                        .setBgDarkAlpha(0.7f)
                        .setAnimationStyle(R.style.AnimTools)
                        .create()
//                        .showAtLocation(musicMenuImageview,Gravity.RIGHT,-50,-450);
                        .showAsDropDown(musicMenuImageview, -250, -500, Gravity.BOTTOM);

            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.musics_back)
    public void onClickmusicBack(View view) {

        GsonUtility.showSetTitleLayout(StepActivity.this, weatherInfo, SteprelativeLayout);

        mClockLayout.setVisibility(View.VISIBLE);
        mMusicLayout.setVisibility(View.GONE);
        menu = STEP_MEMU;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.musics_search)
    public void OnclickSreach(View view) {
        //第一次进入会重新播放
        SlistveiwId = -1;
        isMusicFirst = 2;
        isMusicMenu = 2;
        LayoutTitle(R.color.music_search);
        musicsTitleRelativeLayout.setVisibility(View.GONE);
        musicsStitlerelativeLayout.setVisibility(View.VISIBLE);
        SreachlistView.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        menu = STEP_SREACH;
    }

    //我的歌单返回键
    @OnClick(R.id.mymusic_back)
    public void onClickMymusic(View view) {
        musicsMyTitleRelativeLayout.setVisibility(View.GONE);
        myMusicListView.setVisibility(View.GONE);
        if (isMusicMenu == 1 || isMusicMenu == 0) {
            listView.setVisibility(View.VISIBLE);
            musicsTitleRelativeLayout.setVisibility(View.VISIBLE);
//            //判断是否相同
//            if(musicSong.equals(myMusicSong) && musicSonger.equals(myMusicSonger)){
//                listveiwId = musicIndexId;
//            }else {
//                listveiwId = -1;
//            }
            if (isloveMusic) {
                isMusicFirst = 3;
            } else {
                isMusicFirst = 1;
            }
        } else if (isMusicMenu == 2) {
            SreachlistView.setVisibility(View.VISIBLE);
            musicsStitlerelativeLayout.setVisibility(View.VISIBLE);
            SlistveiwId = -1;
            isMusicFirst = 2;
        }

        /**
         *  重新请求数据来设置更新我的歌单的红色爱心---删除数据后更新
         */
        HttpUtility.AsynchttpRequest(GsonMusicApi.WY_MUSIC_URL, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                MusicApi musicApi = GsonMusicApi.handleMusicResponse(responseString);
                if (musicApi != null) {
                    musicList.clear();
                    setInitMusicDataAdapter(musicApi);
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        if (menu == STEP_MEMU) {
            finish(); //直接结束该活动
        } else if (menu == STEP_MUSCI) {
            String type = getIntent().getStringExtra("cityType");
            GsonUtility.showSetTitleLayout(StepActivity.this, type, SteprelativeLayout);
            mClockLayout.setVisibility(View.VISIBLE);
            mMusicLayout.setVisibility(View.GONE);
            menu = STEP_MEMU;
            return;
        } else if (menu == STEP_SREACH) {
            listveiwId = -1;
            if (isloveMusic) {
                isMusicFirst = 3;
            } else {
                isMusicFirst = 1;
            }
            isMusicMenu = 1;
            LayoutTitle(R.color.music_bg);
            mClockLayout.setVisibility(View.GONE);
            mMusicLayout.setVisibility(View.VISIBLE);
            musicsTitleRelativeLayout.setVisibility(View.VISIBLE);
            musicsStitlerelativeLayout.setVisibility(View.GONE);
            SreachlistView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            menu = STEP_MUSCI;
            return;
        } else if (menu == STEP_MY_MUSIC) {
            musicsMyTitleRelativeLayout.setVisibility(View.GONE);
            myMusicListView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            musicsTitleRelativeLayout.setVisibility(View.VISIBLE);
            //判断是否相同
//            if(musicSong.equals(myMusicSong) && musicSonger.equals(myMusicSonger)){
//                listveiwId = musicIndexId;
//            }else {
//                listveiwId = -1;
//            }

            if (isloveMusic) {
                isMusicFirst = 3;
            } else {
                isMusicFirst = 1;
            }
            menu = STEP_MUSCI;
            return;
        } else if (menu == STEP_MY_SEARCH_MUSIC) {
            musicsMyTitleRelativeLayout.setVisibility(View.GONE);
            myMusicListView.setVisibility(View.GONE);
            SreachlistView.setVisibility(View.VISIBLE);
            musicsStitlerelativeLayout.setVisibility(View.VISIBLE);
            SlistveiwId = -1;
            isMusicFirst = 2;
            menu = STEP_SREACH;
            return;
        }
        super.onBackPressed();
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


    @Override
    protected void onDestroy() {

        super.onDestroy();
        Log.d(TAG, "onDestroy");
        if (mp != null) {
            mp.stop();
            mp.release();
            discObjectAnimator.cancel();
        }
    }

    @SuppressLint("WrongConstant")
    private void animator() {
        discObjectAnimator = ObjectAnimator.ofFloat(circleImageView, "rotation", 0, 360);
        discObjectAnimator.setDuration(15000);
        //使ObjectAnimator动画匀速平滑旋转
        discObjectAnimator.setInterpolator(new LinearInterpolator());
        //无限循环旋转
        discObjectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        discObjectAnimator.setRepeatMode(ValueAnimator.INFINITE);

    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
//        if (mp.isPlaying()) {
            Date date = new Date(millseconds);
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");//小时：分钟
            String time = format.format(date);
            int hour = Integer.valueOf(time.substring(0, 2));
            int minute = Integer.valueOf(time.substring(3, time.length()));
            //定时的总分钟
            timerHourMinute = (hour * 60 + minute) * 60 * 1000;

            //防止重新设定时间
            if (timerThread != null) {
                onStartHandlerThread(0);
                onCloseHanderTimer();
            }
            isStart = true;
            //开启定时器任务倒计时，主要用来显示定时时间
            onStartTimer(timerHourMinute, hour);
            //开启消息线程来控制音乐的暂停
            onStartHandlerThread(timerHourMinute);
            //提醒定时的时间
            String dates = "";
            if (hour == 0 && minute >= 0) {
                dates = minute + "分钟后关闭定时";
            } else if (hour > 0 && minute >= 0) {
                dates = hour + "小时" + minute + "分钟后关闭定时";
            }
            StyleableToast.makeText(StepActivity.this, dates, Toast.LENGTH_LONG, R.style.mytoast).show();

//        }
    }


    /**
     * 开启定时器任务倒计时
     */
    private void onStartTimer(long timerHourMinute, int hour) {
        if (timerTask == null) {
            onCloseTimer(timerHourMinute, hour); //定时关闭倒计时
        } else {
            if (timerTask != null) { //任务存在
                timerTask.cancel();
                timerTask = null;
            }
            onCloseTimer(timerHourMinute, hour); //定时关闭倒计时
        }
    }

    //定时关闭倒计时
    private void onCloseTimer(final long timerHourMinute, final int hour) {
        timerTask = new TimerTask() {
            long allTimes = timerHourMinute / 1000; //总时重新赋值

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (allTimes > 0) {
                            musicTimerTextView.setVisibility(View.VISIBLE);
                            musicTimerTextView.setText(getStringAllTime(allTimes--, hour));
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    private String getStringAllTime(long allTimes, int hour1) {
        int hour = (int) (allTimes / 3600);
        int minute = (int) (allTimes % 3600 / 60);
        int second = (int) (allTimes % 60);
        if (hour1 == 0) {
            return String.format(Locale.CHINA, "%02d:%02d", minute, second);
        }
        return String.format(Locale.CHINA, "%02d:%02d:%02d", hour, minute, second);
    }

    /**
     * 开启消息线程来控制音乐的暂停
     *
     * @param timerHourMinute
     */
    private void onStartHandlerThread(long timerHourMinute) {
        if (timerThread == null) { //判断线程是否为空
            onStartHandler(timerHourMinute);  //启动消息发送定时
            timerThread.start();
        } else {
            if (timerThread != null) {
                synchronized (timerThread) {
                    timerThread.notifyAll();  //移除
                    timerThread.interrupt(); //中断
                    timerThread = null;
                }
            }
            onStartHandler(timerHourMinute);  //启动消息发送定时
            timerThread.start();
        }
    }

    //启动消息发送定时
    private void onStartHandler(final long timerHourMinute) {
        timerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (timerHourMinute != 0) {
                    if (mp.isPlaying()) {
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessageDelayed(message, timerHourMinute);
                    }
                } else {
                    handler.removeMessages(1);  //删除消息
                }
            }
        });
    }

    /**
     * 清除消息的发送和定时器的计时
     */
    private void onCloseHanderTimer() {
        //关闭线程
        synchronized (timerThread) {
            timerThread.notifyAll();
            timerThread = null;
        }
        //关闭计时器
        if (isStart) {
            if (!timerTask.cancel()) {
                timerTask.cancel();
                timer.cancel();
                timerTask = null;
                timer = null;
            }
        }
        musicTimerTextView.setVisibility(View.INVISIBLE);
    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //暂停播放音乐，当定时完成时
                    onCloseHanderTimer();
                    mp.pause();
                    discObjectAnimator.cancel();
                    musicPlayImageview.setImageResource(R.mipmap.play);
                    break;
                case 2:
                    //进行耗时网络图片的请求
                    networkRequestData360Image();
                    break;
                case 3:
                    //进行耗时网络数据解析音乐的请求
                    setinitMusicWYyData(); //网易云音乐热歌列表请求解析

                    setAutoCompleteTextView(); //音乐搜索界面的操作
                    break;
                default:
            }
            super.handleMessage(msg);
        }
    };

    //请求图片数据
    private void networkRequestData360Image() {
        SharedPreferences images = getSharedPreferences("imageUrl", Context.MODE_PRIVATE);
        String date = images.getString("date", null);

//        int conunt = 1;
        //每天更新一次图片
        if (TextUtils.isEmpty(date)) {
            Gson360Api.httpGson360api(StepActivity.this, frontImageView, 1);
            Gson360Api.httpGson360api(StepActivity.this, backImageView, 8+1);
            Gson360Api.httpGson360api(StepActivity.this, backImageViewNight, 18+1);
            Gson360Api.httpGson360api(StepActivity.this, frontImageViewNight, 12+1);
            Log.d(TAG, "为空加载");
        } else {
            if (date.equals(BingPicUtility.getNowDate())) {
                glideImage(images, 1, frontImageView);
                glideImage(images, 8+1, backImageView);
                glideImage(images, 18+1, backImageViewNight);
                glideImage(images, 12+1, frontImageViewNight);
                Log.d(TAG, "缓存加载");
            } else {
                //解析的360图片数据
                Gson360Api.httpGson360api(StepActivity.this, frontImageView, 1);
                Gson360Api.httpGson360api(StepActivity.this, backImageView, 8+1);
                Gson360Api.httpGson360api(StepActivity.this, backImageViewNight, 18+1);
                Gson360Api.httpGson360api(StepActivity.this, frontImageViewNight, 12+1);
                Log.d(TAG, "流量加载");
            }
        }
    }


    /**
     * 播放暂存区喜欢的歌曲
     */
    private void playLoveMusic() {
        if (++loveMusicPosition == myLoveMusicList.size()) {
            //判断如果播放到了最后一首的话--恢复到原来播放顺序并清空集合列表
            myLoveMusicList.clear();
            loveMusicPosition = -1;
            isMusicFirst = 1;
            isloveMusic = false;
            musicPlayingWay(isMusic);
            Log.d(TAG, "isMusicFirst=" + isMusicFirst);
        }

        if (myLoveMusicList.size() > 0 && myLoveMusicList != null) {
            MyLoveMusic loveMusic = myLoveMusicList.get(loveMusicPosition);
            String url = loveMusic.getUri();
            String pic = loveMusic.getPic();
            String songName = loveMusic.getSong();
            String songer = loveMusic.getSonger();
            musicSongTextview.setText(songName);
            musicSongerTextview.setText(songer);
            Glide.with(StepActivity.this).load(pic).into(circleImageView);
            Mediaplay(url);
        }
    }

    /**
     * 适配器传值过来的接口音乐数据方法---把喜欢的歌曲放入暂存区
     *
     * @param view
     * @param url
     * @param song
     * @param songer
     * @param pic
     */
    @Override
    public void onItemLoveMusic(View view, String url, String song, String songer, String pic) {
        //把喜欢的音乐添加到喜欢列表里
        MyLoveMusic loveMusic = new MyLoveMusic(song, songer, url, pic);
        myLoveMusicList.add(loveMusic);
        for (MyLoveMusic loveMusic1 : myLoveMusicList) {   //测试存储的歌曲数
            String song1 = loveMusic1.getSong();
            String songer1 = loveMusic1.getSonger();
            Log.d(TAG, song1 + "----" + songer1);
        }
        Log.d(TAG, myLoveMusicList.size() + "");
        isMusicFirst = 3;
        isloveMusic = true;
        Log.d(TAG, "isMusicFirst=" + isMusicFirst);
        StyleableToast.makeText(StepActivity.this, "已添加" + myLoveMusicList.size() + "首歌曲到下一首播放", Toast.LENGTH_LONG, R.style.mytoast).show();


    }

    /**
     * 适配器传值过来的接口方法，实现本地我的歌单的歌曲存储
     *
     * @param view
     * @param url
     * @param song
     * @param songer
     * @param pic
     */
    @Override
    public void onItemMyMusic(View view, String url, String song, String songer, String pic) {
//        List<MyMusic> myMusicList = MusicLabs.getMyMusicList();

        if (myMusicList.isEmpty()) {
            //集合为空添加到第一个位置
            MyMusic myMusic1 = new MyMusic(R.mipmap.point, song, songer, url, pic, 0, hronMusicIndex);
            myMusic1.save();
            MusicLabs.getMyMusicList().add(0, myMusic1);
            StyleableToast.makeText(StepActivity.this, "已添加到我的歌单", Toast.LENGTH_LONG, R.style.mytoast).show();
        }

        if (myMusicList != null && myMusicList.size() > 0) {
            if (MusicLabs.CompreteMusicName(song, songer)) {
                //存储数据到音乐数据库中
                MyMusic myMusic = new MyMusic(R.mipmap.point, song, songer, url, pic, myMusicList.size() - 1, hronMusicIndex);
                myMusic.save();
                //添加到我的歌单数据库中
                //不为空时，添加到最后一个位置
                MusicLabs.getMyMusicList().add(myMusicList.size() - 1, myMusic);
                StyleableToast.makeText(StepActivity.this, "已添加到我的歌单", Toast.LENGTH_LONG, R.style.mytoast).show();
            } else {
                StyleableToast.makeText(StepActivity.this, "歌曲已存在", Toast.LENGTH_LONG, R.style.mytoast).show();
            }
        }


        //重新请求数据来设置更新我的歌单的红色爱心
        HttpUtility.AsynchttpRequest(GsonMusicApi.WY_MUSIC_URL, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                MusicApi musicApi = GsonMusicApi.handleMusicResponse(responseString);
                if (musicApi != null) {
                    musicList.clear();
//                    handler.removeMessages(3);
                    setInitMusicDataAdapter(musicApi);
                    adapter.notifyDataSetChanged();
                }
            }
        });


    }

    /**
     * 我的歌单适配器传值过来的接口方法，实现本地我的歌单的歌曲存储
     *
     * @param view
     * @param position
     */
    @Override
    public void onRemoveItemMyMusic(View view, final int position) {

        setAlertDialogRemoveItem(position);

    }

    /**
     * 弹出一个小清新的对话框并删除数据库中的数据
     *
     * @param position
     */
    private void setAlertDialogRemoveItem(final int position) {
        //获取我的歌单中的id
        final int id = MusicLabs.getMyMusicSongId(myMusicList.get(position).getSong(), myMusicList.get(position).getSonger());

        //弹出一个小清新的对话框
        SweetAlertDialog dialog = new SweetAlertDialog(StepActivity.this, SweetAlertDialog.WARNING_TYPE);
        dialog.setTitleText("你确定要删除它吗?")
                .setContentText("删除后将无法恢复这首歌")
                .setConfirmText("确定")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        //(1)删除列表中的数据
                        myMusicList.remove(position);
                        //(2)更新适配器
                        myMusicAdapter.notifyDataSetChanged();
                        //(3)删除数据库中的数据
                        LitePal.delete(MyMusic.class, id);
                        sweetAlertDialog.dismissWithAnimation();
                        sweetAlertDialog.cancel();
                    }
                }).show();
    }

}



