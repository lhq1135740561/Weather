package com.example.lhq.weather.activity.activity;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.lhq.weather.R;
import com.example.lhq.weather.activity.activity.test.BaseActivity;
import com.example.lhq.weather.activity.activity.test.QueryWeatherActivity;
import com.example.lhq.weather.activity.adapter.SetTmpAdapter;
import com.example.lhq.weather.activity.adapter.SetWindAdapter;
import com.example.lhq.weather.activity.db.Tmp;
import com.example.lhq.weather.activity.db.Wind;
import com.example.lhq.weather.activity.utils.GsonUtility;
import com.example.lhq.weather.activity.utils.SetUtility;
import com.example.lhq.weather.activity.utils.Utility;
import com.suke.widget.SwitchButton;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;

import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.lhq.weather.R.layout.set_dialog_header;

public class SetActivity extends BaseActivity {

    public static final String TAG = "SetActivity";

    @BindView(R.id.set_title_layout)
    RelativeLayout setTitleLaytou;

    @BindView(R.id.set_Bg_switchButton)
    SwitchButton switchButton;

    @BindView(R.id.set_cityName)
    TextView cityNameTextView;

    @BindView(R.id.set_cityName_switchButton)
    SwitchButton switchCityNameButton;

    @BindView(R.id.set_MainBg_switchButton)
    SwitchButton switchMainBgButton;

    @BindView(R.id.set_float_switchButton)
    SwitchButton switchFloatButton;

    //单位
    @BindView(R.id.set_tmp_layout)
    RelativeLayout tmpRelativeLayout;
    @BindView(R.id.set_wind_layout)
    RelativeLayout windRelativeLayout;

    @BindView(R.id.set_tmp_text)
    TextView tmpText;

    @BindView(R.id.set_wind_text)
    TextView windText;


    private String[] data = {"km/h", "千米/每小时"};

    private String[] data2 = {"摄氏度℃", "华氏度℉"};

    private List<Tmp> tmpList; //存储风力单位集合

    private List<Wind> windList; //存储温度单位集合


    private SetTmpAdapter setWindAdapter;

    private SetWindAdapter setTmpdapter;


    //跳转传值
    public static void actionStart(Context context, String type) {
        Intent intent = new Intent(context, SetActivity.class);
        intent.putExtra("SetType", type);
        context.startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        ButterKnife.bind(this);

        showTitleBackground();

        showCityName();

        showBackground();   //更新图片

        showChangeBackground();  //切换背景

        showFloatBackground();  //浮点背景

        initData();

        initData2();

        tmpWindText();

        setTmpAdapterData();

        setWindAdapterData();


    }


    @Override
    protected ViewModel initViewModel() {
        return null;
    }

//    public void queryWeather(View view) {
//        startActivity(QueryWeatherActivity.class);
//    }


    private void setWindAdapterData() {
        final View view = LayoutInflater.from(this).inflate(R.layout.set_dialog_listview, null);
        final View headerview = LayoutInflater.from(this).inflate(R.layout.set_dialog_header, null);
        TextView textView = headerview.findViewById(R.id.header_text);
        textView.setText("温度单位");

        ListView recyclerView = view.findViewById(R.id.set_wind_recyclerView);
        setTmpdapter = new SetWindAdapter(windList, SetActivity.this);
        recyclerView.setAdapter(setTmpdapter);
        tmpRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTmpDialogPlus(headerview);
            }
        });

    }


    private void tmpWindText() {
        int wind = SetUtility.getWind(SetActivity.this);
        if (wind == 0) {
            windText.setText(data[0]);
        } else if (wind == 1) {
            windText.setText(data[1]);
        } else if (wind == 2) {
            windText.setText(data[2]);
        }

        int tmp = SetUtility.getTmp(SetActivity.this);
        if (tmp == 0) {
            tmpText.setText(data2[0]);
        } else if (tmp == 1) {
            tmpText.setText(data2[1]);
        }
    }


    /**
     * 温度单位对话框
     */
    private void setTmpAdapterData() {
        setWindAdapter = new SetTmpAdapter(tmpList, this);
        final View view = LayoutInflater.from(this).inflate(R.layout.set_dialog_listview, null);
        final View headerview = LayoutInflater.from(this).inflate(R.layout.set_dialog_header, null);
        ListView listView = view.findViewById(R.id.set_tmp_listview);
        listView.setAdapter(setWindAdapter);
        windRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
//                showDialog(items,view,headerview);
                showWindDialogPlus(headerview);
            }
        });
    }

    /**
     * 添加初始化数据
     */
    private void initData() {
        tmpList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            int imageId = R.mipmap.get;
            Tmp tmp = new Tmp(data[i], imageId);
            tmpList.add(tmp);
        }
    }


    /**
     * 添加初始化数据
     */
    private void initData2() {
        windList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            int imageId = R.mipmap.get;
            Wind tmp = new Wind(data2[i], imageId);
            windList.add(tmp);
        }
    }


    private void showBackground() {

        if (Utility.getChangBg(this)) {
            switchButton.setChecked(true);
        } else {
            switchButton.setChecked(false);
        }

        //事件
        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    Utility.isChangBg(SetActivity.this, true);
                    Toast.makeText(SetActivity.this, "更新每图", Toast.LENGTH_SHORT).show();
                } else {
                    Utility.isChangBg(SetActivity.this, false);
                    Toast.makeText(SetActivity.this, "不更新每图", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     * 切换背景
     */
    private void showChangeBackground() {
        if(SetUtility.getChangeBg(this)){
            switchMainBgButton.setChecked(true);
        }else {
            switchMainBgButton.setChecked(false);
        }

        switchMainBgButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if(isChecked){
                    SetUtility.setChangeBg(SetActivity.this,true);
                    Toast.makeText(SetActivity.this, "切换背景", Toast.LENGTH_SHORT).show();
                }else {
                    SetUtility.setChangeBg(SetActivity.this,false);
                    Toast.makeText(SetActivity.this, "不切换背景", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //浮点背景
    private void showFloatBackground() {
        if(SetUtility.getFloatBg(this)){
            switchFloatButton.setChecked(true);
        }else {
            switchFloatButton.setChecked(false);
        }

        switchFloatButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if(isChecked){
                    SetUtility.setFloatBg(SetActivity.this,true);
                    Toast.makeText(SetActivity.this, "浮点背景", Toast.LENGTH_SHORT).show();
                }else {
                    SetUtility.setFloatBg(SetActivity.this,false);
                    Toast.makeText(SetActivity.this, "取消浮点背景", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showCityName() {
        cityNameTextView.setText(Utility.getLocationDistrictName(this) + '\t'
                + Utility.getLocationStreetName(this));
        if (SetUtility.getCityName(this)) {
            switchCityNameButton.setChecked(true);
        } else {
            switchCityNameButton.setChecked(false);
        }


        //事件
        switchCityNameButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    SetUtility.setCityName(SetActivity.this, true);
                    Toast.makeText(SetActivity.this, "提醒天气", Toast.LENGTH_SHORT).show();
                } else {
                    SetUtility.setCityName(SetActivity.this, false);
                    Toast.makeText(SetActivity.this, "不提醒天气", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 显示背景色
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showTitleBackground() {
        String type = getIntent().getStringExtra("SetType");
        Log.d(TAG, type);
        GsonUtility.showSetTitleLayout(SetActivity.this, type, setTitleLaytou);
        if(!Utility.getTmpFirst(this)){
            Utility.setTmp(this,false);
            Utility.setTmpFirst(this,true);
        }
    }


    @OnClick(R.id.set_back)
    public void onClickBack(View view) {
        WeatherActivity.actionStart(this);
    }


    private void showWindDialogPlus(View headerview) {
        DialogPlus dialogPlus = DialogPlus.newDialog(SetActivity.this)
                .setHeader(headerview)
                .setGravity(Gravity.CENTER)
                .setMargin(30, 0, 30, 0)
                .setAdapter(setWindAdapter)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        switch (position) {
                            case 0:
                                SetUtility.setWind(SetActivity.this, 0);
                                windText.setText(data[0]);
                                dialog.dismiss();
                                break;
                            case 1:
                                SetUtility.setWind(SetActivity.this, 1);
                                windText.setText(data[1]);
                                dialog.dismiss();
                                break;
                            case 2:
                                SetUtility.setWind(SetActivity.this, 2);
                                windText.setText(data[2]);
                                dialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                })
                .create();
        dialogPlus.show();
    }

    private void showTmpDialogPlus(View headerview) {
        DialogPlus dialogPlus = DialogPlus.newDialog(SetActivity.this)
                .setHeader(headerview)
                .setGravity(Gravity.CENTER)
                .setMargin(30, 0, 30, 0)
                .setAdapter(setTmpdapter)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        switch (position) {
                            case 0:
                                SetUtility.setTmp(SetActivity.this, 0);
                                Utility.setTmp(SetActivity.this,false);
                                tmpText.setText(data2[0]);
                                dialog.dismiss();
                                break;
                            case 1:
                                SetUtility.setTmp(SetActivity.this, 1);
                                Utility.setTmp(SetActivity.this,true);
                                tmpText.setText(data2[1]);
                                dialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                })
                .create();
        dialogPlus.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        WeatherActivity.actionStart(this);

        return true;
    }
}
