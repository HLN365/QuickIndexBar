package com.hl.quickindexbar;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.OvershootInterpolator;
import android.widget.ListView;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private boolean isScal = false;
    private Handler mHandler = new Handler();

    private ListView mListView;
    private MyAdapter mAdapter;
    private TextView mCurrentWord;
    private QuickIndexBar mIndexBar;
    private ArrayList<Friend> mFriends = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.lv_list);
        mIndexBar = (QuickIndexBar) findViewById(R.id.quickbar);
        mCurrentWord = (TextView) findViewById(R.id.current_word);

        mAdapter = new MyAdapter(this, mFriends);
        mListView.setAdapter(mAdapter);

        //通过缩小将mCurrentWord隐藏
        ViewHelper.setScaleX(mCurrentWord, 0);
        ViewHelper.setScaleY(mCurrentWord, 0);

        mIndexBar.setOnTouchLetterListener(new QuickIndexBar.OnTouchLetterListener() {
            @Override
            public void onTouchLetter(String letter) {
                //开始匹配触摸的字母
                for (int i = 0; i < mFriends.size(); i++) {
                    String firstWord = mFriends.get(i).getPinyin().charAt(0) + "";
                    if (letter.equals(firstWord)) {
                        //找到即可，停止循环
                        mListView.setSelection(i);
                        break;
                    }
                }
                //显示当前触摸的字母
                showCurrentWord(letter);
            }
        });
    }

    private void showCurrentWord(String letter) {
        mCurrentWord.setText(letter);
        if (!isScal) {
            isScal = true;
            ViewPropertyAnimator.animate(mCurrentWord)
                    .scaleX(1).setInterpolator(new OvershootInterpolator())
                    .setDuration(450).start();

            ViewPropertyAnimator.animate(mCurrentWord)
                    .scaleY(1).setInterpolator(new OvershootInterpolator())
                    .setDuration(450).start();
        }
        //移除以前发送的消息
        mHandler.removeCallbacksAndMessages(null);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewPropertyAnimator.animate(mCurrentWord).scaleX(0).setDuration(450).start();
                ViewPropertyAnimator.animate(mCurrentWord).scaleY(0).setDuration(450).start();
                isScal = false;
            }
        }, 1000);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mFriends.add(new Friend("李伟"));
        mFriends.add(new Friend("张三"));
        mFriends.add(new Friend("阿三"));
        mFriends.add(new Friend("阿四"));
        mFriends.add(new Friend("段誉"));
        mFriends.add(new Friend("段正淳"));
        mFriends.add(new Friend("张三丰"));
        mFriends.add(new Friend("陈坤"));
        mFriends.add(new Friend("林俊杰1"));
        mFriends.add(new Friend("陈坤2"));
        mFriends.add(new Friend("王二a"));
        mFriends.add(new Friend("林俊杰a"));
        mFriends.add(new Friend("张四"));
        mFriends.add(new Friend("林俊杰"));
        mFriends.add(new Friend("王二"));
        mFriends.add(new Friend("王二b"));
        mFriends.add(new Friend("赵四"));
        mFriends.add(new Friend("杨坤"));
        mFriends.add(new Friend("赵子龙"));
        mFriends.add(new Friend("杨坤1"));
        mFriends.add(new Friend("李伟1"));
        mFriends.add(new Friend("宋江"));
        mFriends.add(new Friend("宋江1"));
        mFriends.add(new Friend("李伟3"));
        //对集合进行排序
        Collections.sort(mFriends);
    }
}
