package com.hl.quickindexbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 快速检索自定义View
 * 整体思路：
 * 1.将控件高度等分26份，在onSizeChanged方法中，获取控件整体高度
 * 2.在每一个小格子中绘制相应的字母：ondraw()方法中：每个字母绘制的起点：格子高度/2+文本高度/2+当前索引*格子高度
 * 3.在onTouchEvent()方法中，对触摸到的字母进行判断，当前字母索引值=当前触摸位置/格子高度
 * 4.将触摸到的字母通过接口回调传递出去
 */

public class QuickIndexBar extends View {

    private String[] indexArr = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    /**
     * 控件的宽度
     **/
    private int mWidth;
    /**
     * 等分26个格子的高度
     */
    private float mCellHeight;
    private Paint mPaint;
    /**
     * 上一次索引的值
     */
    private int lastIndex = -1;

    public QuickIndexBar(Context context) {
        super(context);
        initPaint();
    }

    public QuickIndexBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public QuickIndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        //设置抗锯齿
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(16);
        //设置从文本的起点是文字边框底边中心开始绘制
        //正常是从文字边框的左下角开始绘制
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mCellHeight = getMeasuredHeight() * 1.0f / indexArr.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < indexArr.length; i++) {
            //每个文本绘制的起点：格子高度/2+文本高度/2+当前索引*格子高度
            float x = mWidth / 2;
            float y = mCellHeight / 2 + getTextHeight(indexArr[i]) / 2 + mCellHeight * i;
            mPaint.setColor(lastIndex == i ? Color.BLACK : Color.WHITE);
            canvas.drawText(indexArr[i], x, y, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                //得到字母对应的索引
                int index = (int) (y / mCellHeight);
                //当前索引与上一次不同，才做操作
                if (index != lastIndex) {
                    //安全性判断
                    if (index >= 0 && index < indexArr.length) {
                        if (mListener != null) {
                            //将触摸到的字母传递出去
                            mListener.onTouchLetter(indexArr[index]);
                        }
                    }
                }
                lastIndex = index;
                break;
            case MotionEvent.ACTION_UP:
                //重置lastIndex,防止下一次点击出错
                lastIndex = -1;
                break;
        }
        //重绘
        invalidate();
        return true;//自己处理事件不在往下传递
    }

    /**
     * 获取文本的高度
     *
     * @param text
     * @return
     */
    private int getTextHeight(String text) {
        //每一个文字外面都有一个矩形边框
        Rect bounds = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.height();
    }

    //通过回调把触摸的字母传递出去

    private OnTouchLetterListener mListener;

    public void setOnTouchLetterListener(OnTouchLetterListener listener) {
        this.mListener = listener;
    }

    public interface OnTouchLetterListener {
        void onTouchLetter(String letter);
    }
}
