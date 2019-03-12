package com.demo.winwang.tigermachine;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.UnicodeSetSpanner;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.winwang.tigermachine.widget.OnWheelScrollListener;
import com.demo.winwang.tigermachine.widget.UserCaidan;
import com.demo.winwang.tigermachine.widget.WheelView;
import com.demo.winwang.tigermachine.widget.adapters.AbstractWheelAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //private ImageView image;
    DisplayImageOptions options;
    private MediaPlayer mediaPlayer;
    //定义一个变量，来标识是否退出
    private static boolean isExit = false;


//    private List<Integer> picturesList = new ArrayList<>();
//    private List<Integer> numbersList = new ArrayList<>();
//    private List<Integer> caidanList = new ArrayList<>();

    private HashMap<Integer, String> pics = new HashMap<>();
    private Toast mToast;
    private HashMap<Integer, String> shinenums = new HashMap<>();
    private HashMap<Integer, String> ordinums = new HashMap<>();
    private HashMap<Integer, String> caidans = new HashMap<>();
    private List<String> picstempList = new ArrayList<>();
    private List<String> caidantempList = new ArrayList<>();
    private List<String> orditempList = new ArrayList<>();
    private List<String> shinetempList = new ArrayList<>();
    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 3000;
    private static long lastClickTime;
    private long exitTime=0;

    private Button mButton,mRestore;
    private ImageView imageView,picture;
    DBManager dbManager = DBManager.getInstance(this);
    DBCaidanManager dbCaidanManager = DBCaidanManager.getInstance(this);
    DBOrdiManager dbOrdiManager = DBOrdiManager.getInstance(this);
    DBShineManager dbShineManager = DBShineManager.getInstance(this);
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 10) {
                startQuickScrool();
            }else if(msg.what == 8){
                try {
                    imageView.setImageBitmap(getBitmap(picture));
                }catch(Exception e){

                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        initPictures();
        initWheel(R.id.dialog_slot_1);
        initWheel(R.id.dialog_slot_2);
        initWheel(R.id.dialog_slot_3);
        initWheel(R.id.dialog_slot_4);
        mButton = findViewById(R.id.reward_button);
        mRestore=findViewById(R.id.restore_button);
        imageView =findViewById(R.id.prizeresult);
        picture=findViewById(R.id.picture);
        mRestore.setOnClickListener(mListener);
        mButton.setOnClickListener(mListener);
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(this);
        ImageLoader.getInstance().init(configuration);
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.mipmap.caidan01).build();
        //options = new DisplayImageOptions.Builder().build();

    }

    private void initPictures() {
        picstempList.add("caidan01");
        caidantempList.add("caidan01");
        orditempList.add("caidan01");
        shinetempList.add("caidan01");

        pics.put(0, "caidan01");
        pics.put(1, "bailu");
        pics.put(2, "chenguogui");
        pics.put(3, "chenhao");
        pics.put(4, "chenjingjing");
        pics.put(5, "chenquanding");
        pics.put(6, "chijiang");
        pics.put(7, "fenglei");
        pics.put(8, "huangjin");
        pics.put(9, "lihui");
        pics.put(10, "lijianliang");
        pics.put(11, "lishu");
        pics.put(12, "liyang");
        pics.put(13, "zuopingping");
        pics.put(14, "qinfenglei");
        pics.put(15, "shilong");
        pics.put(16, "sumingyu");
        pics.put(17, "sunfei");
        pics.put(18, "sunyifeng");
        pics.put(19, "suochenguang");
        pics.put(20, "tangjuan");
//        pics.put(21, "wangxi");
//        pics.put(22, "wanjinguo");
//        pics.put(23, "wuyaqiang");
//        pics.put(24, "xingrenzhai");
//        pics.put(25, "yangxinyi");
//        pics.put(26, "yangyi");
//        pics.put(27, "zhangli");
//        pics.put(28, "zhangweiwei");
//        pics.put(29, "zhangxiaoping");
//        pics.put(30, "zhaochenxiang");
//        pics.put(31, "zhaoyang");
//        pics.put(32, "zhouqingyu");
//        pics.put(33, "zhujiayuan");

        caidans.put(0, "caidan01");
        caidans.put(1, "jiabang");
        caidans.put(2, "zhuniandaji");
        caidans.put(3, "hongbao");
        caidans.put(4, "jiabang04");
        caidans.put(5, "zhuniandaji06");
        caidans.put(6, "hongbao01");
        caidans.put(7, "zhuniandaji01");
        caidans.put(8, "zhuniandaji02");
        caidans.put(9, "zhuniandaji03");
        caidans.put(10, "zhuniandaji04");
        caidans.put(11, "jiabang01");
        caidans.put(12, "zhuniandaji05");
        caidans.put(13, "hongbao02");
        caidans.put(14, "jiabang03");
        caidans.put(15, "zhuniandaji07");
        caidans.put(16, "hongbao03");
        caidans.put(17, "zhuniandaji08");
        caidans.put(18, "zhuniandaji09");
        caidans.put(19, "zhuniandaji10");
        caidans.put(20, "zhuniandaji11");


        shinenums.put(0, "caidan01");
        shinenums.put(1, "shine1");
        shinenums.put(2, "shine2");
        shinenums.put(3, "shine3");
        shinenums.put(4, "shine4");
        shinenums.put(5, "shine5");
        shinenums.put(6, "shine6");
        shinenums.put(7, "shine7");
        shinenums.put(8, "shine8");
        shinenums.put(9, "shine9");
        shinenums.put(10, "shine10");
        shinenums.put(11, "shine11");
        shinenums.put(12, "shine12");
        shinenums.put(13, "shine13");
        shinenums.put(14, "shine14");
        shinenums.put(15, "shine15");
        shinenums.put(16, "shine16");
        shinenums.put(17, "shine17");
        shinenums.put(18, "shine18");
        shinenums.put(19, "shine19");
        shinenums.put(20, "shine20");

        ordinums.put(0, "caidan01");
        ordinums.put(1, "zhangshangmingzhu10");
        ordinums.put(2,"zhangshangmingzhu01");
        ordinums.put(3,"zhangshangmingzhu02");
        ordinums.put(4,"zhangshangmingzhu03");
        ordinums.put(5, "zhangshangmingzhu04");
        ordinums.put(6, "zhangshangmingzhu09");
        ordinums.put(7, "zhangshangmingzhu05");
        ordinums.put(8,"zhangshangmingzhu06");
        ordinums.put(9,"zhangshangmingzhu07");
        ordinums.put(10, "zhangshangmingzhu08");
        ordinums.put(11, "twothjingdong01");
        ordinums.put(12,"twothjingdong02");
        ordinums.put(13,"twothjingdong03");
        ordinums.put(14,"twothjingdong04");
        ordinums.put(15, "twothjingdong05");
        ordinums.put(16, "twothjingdong06");
        ordinums.put(17, "twothjingdong07");
        ordinums.put(18,"twothjingdong08");
        ordinums.put(19,"twothjingdong09");
        ordinums.put(20, "twothjingdong10");


        for(int i = 1;i<pics.size();i++){
            User userPic = new User();
            userPic.setName(pics.get(i));
            userPic.setFlag(0);
            dbManager.insertUser(userPic);
        }

        for(int i = 1;i<caidans.size();i++){
            UserCaidan userCaidan = new UserCaidan();
            userCaidan.setName(caidans.get(i));
            userCaidan.setFlag(0);
            dbCaidanManager.insertUser(userCaidan);
        }

        for(int i = 1;i<shinenums.size();i++){
            UserShine userShine = new UserShine();
            userShine.setName(shinenums.get(i));
            userShine.setFlag(0);
            dbShineManager.insertUser(userShine);
        }

        for(int i = 1;i<ordinums.size();i++){
            UserOrdi userOrdi = new UserOrdi();
            userOrdi.setName(ordinums.get(i));
            userOrdi.setFlag(0);
            dbOrdiManager.insertUser(userOrdi);
        }


    }

    View.OnClickListener mListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.reward_button:     //确认按钮的监听事件
                    if (isFastClick()) {
                        refreshWheel();
                    }else{
                        if(mToast == null){
                            mToast = Toast.makeText(MainActivity.this,"8秒后才可以再次点击哦～", Toast.LENGTH_SHORT);
                        }else{
                            mToast.setText("8秒后才可以再次点击哦～");
                        }
                        mToast.show();
                        //Toast.makeText(MainActivity.this,"8秒后才可以再次点击哦～", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.restore_button:
                    restoreData();
                    break;
            }
        }
    };


    private void refreshWheel() {
        handler.sendEmptyMessage(10);
    }

    /**
     * 初始化轮子
     *
     * @param id the wheel widget Id
     */
    private void initWheel(int id) {
        WheelView wheel = getWheel(id);
        if (id == R.id.dialog_slot_1) {
            wheel.setViewAdapter(new Slot1MachineAdapter());
            wheel.setVisibleItems(3);
            wheel.addScrollingListener(scrolledListener1);
        } else if (id == R.id.dialog_slot_2) {
            wheel.setViewAdapter(new Slot2MachineAdapter());
            wheel.setVisibleItems(3);
            wheel.addScrollingListener(scrolledListener2);
        } else if (id == R.id.dialog_slot_3) {
            wheel.setViewAdapter(new Slot3MachineAdapter());
            wheel.setVisibleItems(3);
            wheel.addScrollingListener(scrolledListener3);
        } else if (id == R.id.dialog_slot_4) {
            wheel.setViewAdapter(new Slot4MachineAdapter());
            wheel.setVisibleItems(3);
            wheel.addScrollingListener(scrolledListener4);
        }

        wheel.setCyclic(true);
        wheel.setEnabled(false);
        wheel.setDrawShadows(false);
    }

    //车轮滚动的监听器
    OnWheelScrollListener scrolledListener4 = new OnWheelScrollListener() {
        public void onScrollingStarted(WheelView wheel) {
            List<UserCaidan> lists = dbCaidanManager.queryUserList();
            for (UserCaidan userCaidan:lists){
                if(userCaidan.getFlag() == 1){
                    if(!caidantempList.contains(userCaidan.getName())) {
                        caidantempList.add(userCaidan.getName());
                    }
                }
            }
            Log.d("lei","caidantempList = "+caidantempList.size()+"***"+caidantempList.toString());
        }

        public void onScrollingFinished(WheelView wheel) {
            int itemCount = wheel.getViewAdapter().getItemsCount();
            int tempIndex = wheel.getCurrentItem();
            int myInt = 0;
            boolean isC = true;


            if (caidantempList.size() > itemCount) {
                wheel.setCurrentItem(0, true);
                return;
            }

            while (caidantempList.contains(caidans.get(tempIndex)) && isC) {
                tempIndex++;
                myInt++;
                if (tempIndex == itemCount) {
                    tempIndex = 0;
                }
                if (myInt > itemCount) {
                    isC = false;
                    wheel.setCurrentItem(0, true);
                    return;
                }

            }
            wheel.setCurrentItem(tempIndex, true);
            //Log.d("lei",dbCaidanManager.queryUserList(caidans.get(tempIndex)).get(0).getFlag()+"***dbCaidanManager****"+dbCaidanManager.queryUserList().size());
            //dbManager.deleteUser(dbManager.queryUserList(pics.get(tempIndex)).get(0).getId());
            UserCaidan userCaidan = new UserCaidan();
            userCaidan.setId(dbCaidanManager.queryUserList(caidans.get(tempIndex)).get(0).getId());
            userCaidan.setName(dbCaidanManager.queryUserList(caidans.get(tempIndex)).get(0).getName());
            userCaidan.setFlag(1);
            dbCaidanManager.updateUser(userCaidan);
            caidantempList.add(caidans.get(tempIndex));

//            final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).create();
//            ImageView imgView = getView(caidans.get(tempIndex));
//            imgView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    dialog.dismiss();
//                }
//            });
//            dialog.setView(imgView);
//            dialog.show();

        }
    };

    //车轮滚动的监听器
    OnWheelScrollListener scrolledListener3 = new OnWheelScrollListener() {
        public void onScrollingStarted(WheelView wheel) {
            List<UserOrdi> lists = dbOrdiManager.queryUserList();
            for (UserOrdi userOrdi:lists){
                if(userOrdi.getFlag() == 1){
                    if(!orditempList.contains(userOrdi.getName())) {
                        orditempList.add(userOrdi.getName());
                    }
                }
            }
            Log.d("lei","orditempList = "+orditempList.size()+"***"+orditempList.toString());
        }

        public void onScrollingFinished(WheelView wheel) {
            int itemCount = wheel.getViewAdapter().getItemsCount();
            int tempIndex = wheel.getCurrentItem();
            int myInt = 0;
            boolean isC = true;


            if (orditempList.size() > itemCount) {
                wheel.setCurrentItem(0, true);
                return;
            }


            while (orditempList.contains(ordinums.get(tempIndex)) && isC) {
                tempIndex++;
                myInt++;
                if (tempIndex == itemCount) tempIndex = 0;


                if (myInt > itemCount) {
                    isC = false;
                    wheel.setCurrentItem(0, true);
                    return;
                }
            }
            wheel.setCurrentItem(tempIndex, true);
            //Log.d("lei",dbOrdiManager.queryUserList(ordinums.get(tempIndex)).get(0).getFlag()+"***dbOrdiManager****"+dbOrdiManager.queryUserList().size());
            //dbManager.deleteUser(dbManager.queryUserList(pics.get(tempIndex)).get(0).getId());
            UserOrdi userOrdi = new UserOrdi();
            userOrdi.setId(dbOrdiManager.queryUserList(ordinums.get(tempIndex)).get(0).getId());
            userOrdi.setName(dbOrdiManager.queryUserList(ordinums.get(tempIndex)).get(0).getName());
            userOrdi.setFlag(1);
            dbOrdiManager.updateUser(userOrdi);
            orditempList.add(ordinums.get(tempIndex));
        }
    };
    //车轮滚动的监听器
    OnWheelScrollListener scrolledListener2 = new OnWheelScrollListener() {
        public void onScrollingStarted(WheelView wheel) {
            List<UserShine> lists = dbShineManager.queryUserList();
            for (UserShine userShine:lists){
                if(userShine.getFlag() == 1){
                    if(!shinetempList.contains(userShine.getName())) {
                        shinetempList.add(userShine.getName());
                    }
                }
            }
            Log.d("lei","shinetempList = "+shinetempList.size()+"***"+shinetempList.toString());

        }

        public void onScrollingFinished(WheelView wheel) {
            int itemCount = wheel.getViewAdapter().getItemsCount();
            int tempIndex = wheel.getCurrentItem();
            int myInt = 0;
            boolean isC = true;


            if (shinetempList.size() > itemCount) {
                wheel.setCurrentItem(0, true);
                return;
            }

            while (shinetempList.contains(shinenums.get(tempIndex)) && isC) {
                tempIndex++;
                myInt++;
                if (tempIndex == itemCount) tempIndex = 0;
                if (myInt > itemCount) {
                    isC = false;
                    wheel.setCurrentItem(0, true);
                    return;
                }
            }
            wheel.setCurrentItem(tempIndex, true);
            //Log.d("lei",dbShineManager.queryUserList(shinenums.get(tempIndex)).get(0).getFlag()+"***dbShineManager****"+dbShineManager.queryUserList().size());
            UserShine userShine = new UserShine();
            userShine.setId(dbShineManager.queryUserList(shinenums.get(tempIndex)).get(0).getId());
            userShine.setName(dbShineManager.queryUserList(shinenums.get(tempIndex)).get(0).getName());
            userShine.setFlag(1);
            dbShineManager.updateUser(userShine);
            shinetempList.add(shinenums.get(tempIndex));
        }
    };
    //车轮滚动的监听器
    OnWheelScrollListener scrolledListener1 = new OnWheelScrollListener() {
        public void onScrollingStarted(WheelView wheel) {
            List<User> lists = dbManager.queryUserList();
            for (User user:lists){
                if(user.getFlag() == 1){
                    if(!picstempList.contains(user.getName())) {
                        picstempList.add(user.getName());
                    }
                }
            }
            Log.d("lei","picstempList = "+picstempList.size()+"***"+picstempList.toString());
        }

        public void onScrollingFinished(WheelView wheel) {
            int itemCount = wheel.getViewAdapter().getItemsCount();
            int tempIndex = wheel.getCurrentItem();
            int myInt = 0;
            boolean isC = true;


            if (picstempList.size() > itemCount) {
                wheel.setCurrentItem(0, true);
                return;
            }

            while (picstempList.contains(pics.get(tempIndex)) && isC) {
                tempIndex++;
                myInt++;
                if (tempIndex == itemCount) {
                    tempIndex = 0;
                }
                if (myInt > itemCount) {
                    isC = false;
                    wheel.setCurrentItem(0, true);
                    return;
                }

            }
            wheel.setCurrentItem(tempIndex, true);
            //Log.d("lei",dbManager.queryUserList(pics.get(tempIndex)).get(0).getFlag()+"***dbManager****"+dbManager.queryUserList().size());
            User user = new User();
            user.setId(dbManager.queryUserList(pics.get(tempIndex)).get(0).getId());
            user.setName(dbManager.queryUserList(pics.get(tempIndex)).get(0).getName());
            user.setFlag(1);
            dbManager.updateUser(user);
            picstempList.add(pics.get(tempIndex));
            handler.sendEmptyMessage(8);
        }
    };

    public void startScrool(Integer num) {
        String numString = num.toString();
        int length = numString.length();
        if (length == 1) {
            mixWheel(R.id.dialog_slot_1, 50, 2000);
            mixWheel(R.id.dialog_slot_2, 70, 3000);
            mixWheel(R.id.dialog_slot_3, 90 + num, 5000);
        } else if (length == 2) {
            mixWheel(R.id.dialog_slot_1, 50, 2000);
            char c = numString.charAt(0);
            int firstNum = Integer.parseInt(String.valueOf(c));
            mixWheel(R.id.dialog_slot_2, 70 + firstNum, 3000);
            char c1 = numString.charAt(1);
            int secondNum = Integer.parseInt(String.valueOf(c1));
            mixWheel(R.id.dialog_slot_3, 90 + secondNum, 5000);
        } else if (length == 3) {
            char c = numString.charAt(0);
            int firstNum = Integer.parseInt(String.valueOf(c));
            mixWheel(R.id.dialog_slot_1, 50 + firstNum, 2000);
            char c1 = numString.charAt(1);
            int secondNum = Integer.parseInt(String.valueOf(c1));
            mixWheel(R.id.dialog_slot_2, 70 + secondNum, 3000);
            char c2 = numString.charAt(2);
            int thirdNum = Integer.parseInt(String.valueOf(c2));
            mixWheel(R.id.dialog_slot_3, 90 + thirdNum, 5000);
        }
    }

    //
    public void startQuickScrool() {
        int random1 = new Random().nextInt(10);
        mixWheel(R.id.dialog_slot_1, 50 + random1, 2000);
        int random2 = new Random().nextInt(20);
        mixWheel(R.id.dialog_slot_2, 70 + random2, 3000);
        int random3 = new Random().nextInt(30);
        mixWheel(R.id.dialog_slot_3, 90 + random3, 4000);
        int random4 = new Random().nextInt(30);
        mixWheel(R.id.dialog_slot_4, 120 + random4, 2000);
    }

    /**
     * 转动轮子
     *
     * @param id the wheel id
     */
    private void mixWheel(int id, int round, int time) {
        WheelView wheel = getWheel(id);
        wheel.scroll(round, time);
    }


    /**
     * 根据id获取轮子
     *
     * @param id the wheel Id
     * @return the wheel with passed Id
     */
    private WheelView getWheel(int id) {
        WheelView wheelView = (WheelView) findViewById(id);
        return wheelView;
    }


    /**
     * 老虎机适配器
     */
    private class Slot1MachineAdapter extends AbstractWheelAdapter {
        @Override
        public int getItemsCount() {
            return pics.size();
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view;
            if (cachedView != null) {
                view = cachedView;
            } else {
                view = View.inflate(MainActivity.this, R.layout.item_dialog_tiger_img, null);
            }
            ImageView img = (ImageView) view.findViewById(R.id.iv_dialog_home_tiger);

            if (pics.size() > 0) {
                ImageLoader.getInstance().displayImage( "assets://"+pics.get(index)+".png", img,options);
            }


            return view;
        }
    }


    private class Slot2MachineAdapter extends AbstractWheelAdapter {
        @Override
        public int getItemsCount() {
            return shinenums.size();
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view;
            if (cachedView != null) {
                view = cachedView;
            } else {
                view = View.inflate(MainActivity.this, R.layout.item_dialog_tiger_img, null);
            }
            ImageView img = (ImageView) view.findViewById(R.id.iv_dialog_home_tiger);

            if (shinenums.size() > 0) {
                ImageLoader.getInstance().displayImage("assets://" + shinenums.get(index)+".png", img, options);
            }

            return view;
        }
    }


    private class Slot3MachineAdapter extends AbstractWheelAdapter {
        @Override
        public int getItemsCount() {
            return ordinums.size();
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view;
            if (cachedView != null) {
                view = cachedView;
            } else {
                view = View.inflate(MainActivity.this, R.layout.item_dialog_tiger_img, null);
            }
            ImageView img = (ImageView) view.findViewById(R.id.iv_dialog_home_tiger);

            if (ordinums.size() > 0) {
                ImageLoader.getInstance().displayImage("assets://" + ordinums.get(index)+".png", img, options);
            }
            return view;
        }
    }


    private class Slot4MachineAdapter extends AbstractWheelAdapter {
        @Override
        public int getItemsCount() {
            return caidans.size();
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view;
            if (cachedView != null) {
                view = cachedView;
            } else {
                view = View.inflate(MainActivity.this, R.layout.item_dialog_tiger_img, null);
            }
            ImageView img = (ImageView) view.findViewById(R.id.iv_dialog_home_tiger);

            if (caidans.size() > 0) {
                ImageLoader.getInstance().displayImage("assets://"+caidans.get(index)+".png", img,options);
            }
            return view;
        }
    }

    private ImageView getView(Integer pic) {
        ImageView imgView = new ImageView(this);
        imgView.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
        imgView.setImageResource(pic);
        return imgView;
    }


    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer = MediaPlayer.create(this, R.raw.music); //用prepare方法，会报错误java.lang.IllegalStateExceptio //mediaPlayer.prepare();
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
//
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        }else{

            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            }
        }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void restoreData(){
        dbManager.deleteAllUser();
        dbCaidanManager.deleteAllUser();
        dbOrdiManager.deleteAllUser();
        dbShineManager.deleteAllUser();
        picstempList.clear();
        caidantempList.clear();
        orditempList.clear();
        shinetempList.clear();

        picstempList.add("caidan01");
        caidantempList.add("caidan01");
        orditempList.add("caidan01");
        shinetempList.add("caidan01");


        for(int i = 1;i<pics.size();i++){
            User userPic = new User();
            userPic.setName(pics.get(i));
            userPic.setFlag(0);
            dbManager.insertUser(userPic);
        }

        for(int i = 1;i<caidans.size();i++){
            UserCaidan userCaidan = new UserCaidan();
            userCaidan.setName(caidans.get(i));
            userCaidan.setFlag(0);
            dbCaidanManager.insertUser(userCaidan);
        }

        for(int i = 1;i<shinenums.size();i++){
            UserShine userShine = new UserShine();
            userShine.setName(shinenums.get(i));
            userShine.setFlag(0);
            dbShineManager.insertUser(userShine);
        }

        for(int i = 1;i<ordinums.size();i++){
            UserOrdi userOrdi = new UserOrdi();
            userOrdi.setName(ordinums.get(i));
            userOrdi.setFlag(0);
            dbOrdiManager.insertUser(userOrdi);
        }
    }

    public static Bitmap capture(Activity activity) {
        activity.getWindow().getDecorView().setDrawingCacheEnabled(true);
        Bitmap bmp = activity.getWindow().getDecorView().getDrawingCache();
        return bmp;
    }

    /**
     *
     * @param view 需要截取图片的view
     * @return 截图
     */ private Bitmap getBitmap(View view) throws Exception {
            View screenView = getWindow().getDecorView();
            screenView.setDrawingCacheEnabled(true);
            screenView.buildDrawingCache(); //获取屏幕整张图片
            Bitmap bitmap = screenView.getDrawingCache();
            if (bitmap != null) {
                int outWidth = view.getWidth();//需要截取的长和宽
                int outHeight = view.getHeight();//获取需要截图部分的在屏幕上的坐标(view的左上角坐标）
                int[] viewLocationArray = new int[2];
                view.getLocationOnScreen(viewLocationArray); //从屏幕整张图片中截取指定区域
                bitmap = Bitmap.createBitmap(bitmap, viewLocationArray[0], viewLocationArray[1], outWidth, outHeight);
            }
            return bitmap;
     }

}