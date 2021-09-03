package com.nyw.pets.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.nyw.pets.util.Model;

import java.util.ArrayList;

/**
 * Created by monking-macbook
 * on 2017/7/8
 * in TestForInteger
 * description: use a dialog to introduce this class
 */

public class KLineOilView extends View {

    private Context mContext;
    private float mDensity;
    private int mWidth, mHeight;

    //这个数值是每一个间隔的高度 0-20 ，20-40 ，40-60 ，60-80 ，80-100
    private final static int splitHeight = 40;

    //这个数值是数字的size 单位是sp
    private final static int numberSize = 10;

    //这个数值是X轴上的小竖线的长度 单位是DP
    private final static int schedaulLine = 4;


    //这里是画笔
    private Paint mNumberPaint;
    private Paint mProxyPaint;
    private Paint mGridPaint;
    private Paint mCirclePaint;
    private Paint mNoPaint;


    private ArrayList<Model> date = new ArrayList<Model>();
    private ArrayList<Model> no = new ArrayList<Model>();

    public KLineOilView(Context context) {
        this(context, null);
    }

    public KLineOilView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KLineOilView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mDensity = mContext.getResources().getDisplayMetrics().density;

        initRulerView(attrs);
    }


    private void initRulerView(AttributeSet attrs) {
        mNumberPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mNumberPaint.setTextSize(sp2px(mContext, numberSize));
        mNumberPaint.setStrokeWidth(dp2px(mContext, 1));
        mNumberPaint.setColor(Color.WHITE);

        mProxyPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProxyPaint.setColor(Color.parseColor("#77FFFFFF"));
        mProxyPaint.setStrokeWidth(dp2px(mContext, 1));

        mGridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGridPaint.setColor(Color.parseColor("#77FFFFFF"));
        mGridPaint.setStrokeWidth(1);

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(Color.parseColor("#79d6f5"));
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setStrokeWidth(dp2px(mContext, 3));
        mCirclePaint.setTextSize(sp2px(mContext, numberSize));

        mNoPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mNoPaint.setColor(Color.parseColor("#7d72f0"));
        mNoPaint.setStyle(Paint.Style.FILL);
        mNoPaint.setStrokeWidth(dp2px(mContext, 3));
        mNoPaint.setTextSize(sp2px(mContext, numberSize));
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = measureWitdh(widthMeasureSpec);
        int measureHeight = measureHeight(heightMeasureSpec);
        setMeasuredDimension(measureWidth, measureHeight);
    }


    private int measureWitdh(int widthMeasureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = measureWrapWitdh();
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(measureWrapWitdh(), specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;

    }

    private int measureWrapWitdh() {
        return getScreenWidth(mContext);
    }

    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);

        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = measureWrapHeight();
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(measureWrapHeight(), specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    private int measureWrapHeight() {
        return dp2px(mContext, splitHeight) * 5 + getTextHeight(mNumberPaint) + dp2px(mContext, schedaulLine) + getTextHeight(mNumberPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBaseLine(canvas);
        drawNumber(canvas);
        drawDate(canvas);

    }

    //画X轴和Y轴 以及20 40 60 80 100 的分割线
    private void drawBaseLine(Canvas canvas) {
        int offset = (int) mNumberPaint.measureText("000");
        canvas.drawLine(offset, getTextHeight(mNumberPaint), offset, mHeight - dp2px(mContext, schedaulLine) - getTextHeight(mNumberPaint), mProxyPaint);
        canvas.drawLine(offset, mHeight - dp2px(mContext, schedaulLine) - getTextHeight(mNumberPaint), mWidth, mHeight - dp2px(mContext, schedaulLine) - getTextHeight(mNumberPaint), mProxyPaint);
        for (int i = 0; i < 5; i++) {
            canvas.drawLine(offset, getTextHeight(mNumberPaint) + i * dp2px(mContext, splitHeight), mWidth, getTextHeight(mNumberPaint) + i * dp2px(mContext, splitHeight), mGridPaint);
        }
    }

    //画Y轴上的数字
    private void drawNumber(Canvas canvas) {
        int offset = (int) mNumberPaint.measureText("000");
        canvas.drawText("15", offset - mNumberPaint.measureText("15"), mHeight - dp2px(mContext, schedaulLine) - getTextHeight(mNumberPaint), mNumberPaint);
        canvas.drawText("25", offset - mNumberPaint.measureText("25"), mHeight - dp2px(mContext, schedaulLine) - getTextHeight(mNumberPaint) - dp2px(mContext, splitHeight), mNumberPaint);
        canvas.drawText("35", offset - mNumberPaint.measureText("35"), mHeight - dp2px(mContext, schedaulLine) - getTextHeight(mNumberPaint) - dp2px(mContext, splitHeight) * 2, mNumberPaint);
        canvas.drawText("45", offset - mNumberPaint.measureText("45"), mHeight - dp2px(mContext, schedaulLine) - getTextHeight(mNumberPaint) - dp2px(mContext, splitHeight) * 3, mNumberPaint);
        canvas.drawText("55", offset - mNumberPaint.measureText("55"), mHeight - dp2px(mContext, schedaulLine) - getTextHeight(mNumberPaint) - dp2px(mContext, splitHeight) * 4, mNumberPaint);
        canvas.drawText("65", offset - mNumberPaint.measureText("65"), mHeight - dp2px(mContext, schedaulLine) - getTextHeight(mNumberPaint) - dp2px(mContext, splitHeight) * 5, mNumberPaint);
    }

    //画主题内容
    private void drawDate(Canvas canvas) {


        if (no == null || no.size() == 0 || date == null || date.size() == 0) {
            return;
        }

        canvas.save();


        int offset = (int) mNumberPaint.measureText("000") + mMoveX;
        int split = (int) mNumberPaint.measureText("  00-00-00  ");


        for (int i = 0; i < no.size(); i++) {
            canvas.drawLine(offset + split / 2 + i * split, mHeight - dp2px(mContext, schedaulLine) - getTextHeight(mNumberPaint), offset + split / 2 + i * split, mHeight - dp2px(mContext, schedaulLine) - getTextHeight(mNumberPaint) + dp2px(mContext, schedaulLine), mNumberPaint);
            canvas.drawText(date.get(i).date, offset + split / 2 + i * split - mNumberPaint.measureText(date.get(i).date) / 2, drawTextFromTop(mHeight - getTextHeight(mNumberPaint), mNumberPaint), mNumberPaint);

            canvas.drawCircle(offset + split / 2 + i * split, mHeight - dp2px(mContext, schedaulLine) - getTextHeight(mNumberPaint) - (no.get(i).number - 15) / 50.0f * dp2px(mContext, splitHeight) * 5, dp2px(mContext, 4), mNoPaint);
            canvas.drawText(String.valueOf(no.get(i).number), offset + split / 2 + i * split - mNoPaint.measureText(String.valueOf(no.get(i).number)) / 2, mHeight - dp2px(mContext, schedaulLine) - getTextHeight(mNumberPaint) - (no.get(i).number - 15) / 50.0f * dp2px(mContext, splitHeight) * 5 - dp2px(mContext, 4), mNoPaint);
            if (i > 0) {
                canvas.drawLine(offset + split / 2 + (i - 1) * split,
                        mHeight - dp2px(mContext, schedaulLine) - getTextHeight(mNoPaint) - (no.get(i - 1).number - 15) / 50.0f * dp2px(mContext, splitHeight) * 5,
                        offset + split / 2 + i * split,
                        mHeight - dp2px(mContext, schedaulLine) - getTextHeight(mNoPaint) - (no.get(i).number - 15) / 50.0f * dp2px(mContext, splitHeight) * 5,
                        mNoPaint);
            }
        }

        for (int i = 0; i < date.size(); i++) {
            canvas.drawCircle(offset + split / 2 + i * split, mHeight - dp2px(mContext, schedaulLine) - getTextHeight(mNumberPaint) - (date.get(i).number - 15) / 50.0f * dp2px(mContext, splitHeight) * 5, dp2px(mContext, 4), mCirclePaint);
            canvas.drawText(String.valueOf(date.get(i).number), offset + split / 2 + i * split - mCirclePaint.measureText(String.valueOf(date.get(i).number)) / 2, mHeight - dp2px(mContext, schedaulLine) - getTextHeight(mNumberPaint) - (date.get(i).number - 15) / 50.0f * dp2px(mContext, splitHeight) * 5 - dp2px(mContext, 4), mCirclePaint);
            if (i > 0) {
                canvas.drawLine(offset + split / 2 + (i - 1) * split,
                        mHeight - dp2px(mContext, schedaulLine) - getTextHeight(mNumberPaint) - (date.get(i - 1).number - 15) / 50.0f * dp2px(mContext, splitHeight) * 5,
                        offset + split / 2 + i * split,
                        mHeight - dp2px(mContext, schedaulLine) - getTextHeight(mNumberPaint) - (date.get(i).number - 15) / 50.0f * dp2px(mContext, splitHeight) * 5,
                        mCirclePaint);
            }
        }

        canvas.restore();

    }


    private int mMoveX;
    private int mLastX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveX += (int) (event.getX() - mLastX);
                mLastX = (int) event.getX();
                postInvalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mLastX = 0;
                break;
        }

        return true;
    }


    public void setData(ArrayList<Model> used, ArrayList<Model> noUsed) {
        this.date = used;
        this.no = noUsed;
        postInvalidate();
    }

    /**
     * 工具方法
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获取屏幕宽
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int getTextHeight(Paint paint) {
        return (int) (paint.getFontMetrics().descent - paint.getFontMetrics().ascent);
    }

    public static int drawTextFromTop(float top, Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) (top - fm.top);
    }


}
