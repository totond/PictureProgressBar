package com.scut.pictureprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

/**
 * Created by yany on 2017/5/2.
 */

public class PictureProgress extends View {
    //画笔
    private Paint paintPicture, paintBackGround, paintBar;
    //颜色
    private int backGroundColor = Color.GRAY, barColor = Color.RED;
    //图片
    private Drawable drawable = null;
    //进度改变监听器
    private OnProgressChangeListener onProgressChangeListener;
    //Drawable的宽高半径
    private int halfDrawableWidth = 0, halfDrawableHeight = 0;
    //Drawable高度偏移量
    private int drawableHeightOffset = 0;
    //是否为圆角
    private boolean isRound = true;
    //圆角半轴
    private int roundX = 20, roundY = 20;
    //进度值和最大值
    private int progress = 0, max = 100;
    //进度条当前进度中心点
    private int x, y;
    //是否令设进度条宽高
    private boolean isSetBar = false;
    //进度条宽高
    private int progressWidth = 10, progressHeight = 30;
    //进度条高度偏移量
    private int progressHeightOffset = 0;
    //进度条刷新时间
    private int refreshTime = 100;

    private RectF rectFBG = new RectF(), rectFPB = new RectF();

    private LinearGradient linearGradient;

    private boolean isAnimRun = true;
    //动画模式
    private int animMode = 0;
    private final int ANIM_NULL = 0;
    private final int ANIM_ROTATE = 1;
    private final int ANIM_SCALE = 2;
    private final int ANIM_ROTATE_SCALE = 3;
    private final int ANIM_FRAME = 4;
    private int rotateRate = 10;
    private int rotateDegree = 0;
    private float scaleMax = 1.5f, scaleMin = 0.5f;
    private float scaleLevel = 1;
    private float scaleRate = 0.1f;
    boolean isScaleIncrease = true;
    private int drawableIds[];
    private int frameIndex = 0;
    private int gradientStartColor = Color.RED,gradientEndColor = Color.YELLOW;


    public PictureProgress(Context context) {
        super(context);
        init();
    }


    public PictureProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取xml属性
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PictureProgress, 0, 0);
        backGroundColor = typedArray.getColor(R.styleable.PictureProgress_backGroundColor, Color.GRAY);
        barColor = typedArray.getColor(R.styleable.PictureProgress_barColor, Color.RED);
        drawable = typedArray.getDrawable(R.styleable.PictureProgress_drawable);
        halfDrawableWidth = typedArray.getDimensionPixelSize(R.styleable.PictureProgress_halfDrawableWidth, 35);
        halfDrawableHeight = typedArray.getDimensionPixelSize(R.styleable.PictureProgress_halfDrawableHeight, 35);
        drawableHeightOffset = typedArray.getDimensionPixelSize(R.styleable.PictureProgress_drawableHeightOffset, 0);
        isRound = typedArray.getBoolean(R.styleable.PictureProgress_isRound, true);
        roundX = typedArray.getDimensionPixelSize(R.styleable.PictureProgress_roundX, 20);
        roundY = typedArray.getDimensionPixelSize(R.styleable.PictureProgress_roundY, 20);
        progress = typedArray.getInt(R.styleable.PictureProgress_progress, 0);
        max = typedArray.getInt(R.styleable.PictureProgress_max, 100);
        isSetBar = typedArray.getBoolean(R.styleable.PictureProgress_isSetBar, false);
        progressHeight = typedArray.getDimensionPixelSize(R.styleable.PictureProgress_progressHeight, 30);
        progressHeightOffset = typedArray.getDimensionPixelSize(R.styleable.PictureProgress_progressHeightOffset, 0);
        refreshTime = typedArray.getInt(R.styleable.PictureProgress_refreshTime,100);
        animMode = typedArray.getInt(R.styleable.PictureProgress_animMode, ANIM_NULL);
        rotateRate = typedArray.getInt(R.styleable.PictureProgress_rotateRate, 10);
        rotateDegree = typedArray.getInt(R.styleable.PictureProgress_rotateDegree, 0);
        scaleMax = typedArray.getFloat(R.styleable.PictureProgress_scaleMax, 2);
        scaleMin = typedArray.getFloat(R.styleable.PictureProgress_scaleMin, 1);
        scaleRate = typedArray.getFloat(R.styleable.PictureProgress_scaleRate, 0.1f);
        gradientStartColor = typedArray.getColor(R.styleable.PictureProgress_gradientStartColor,Color.RED);
        gradientEndColor = typedArray.getColor(R.styleable.PictureProgress_gradientEndColor,Color.YELLOW);
        init();
    }

    public PictureProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //初始化
    private void init() {
        //初始化画笔
        paintPicture = new Paint();

        paintBackGround = new Paint();
        paintBackGround.setColor(backGroundColor);

        paintBar = new Paint();
        paintBar.setColor(barColor);


        //在PreDraw时获取View属性,因为在初始化的时候View还没进行Measure
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                getViewTreeObserver().removeOnPreDrawListener(this);
                linearGradient = new LinearGradient(0, progressHeight / 2, progressWidth, progressHeight / 2, gradientStartColor, gradientEndColor, Shader.TileMode.CLAMP);
                paintBar.setShader(linearGradient);
                return false;
            }
        });


    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        //获取进度条当前进度的中心点坐标
        x = (progressWidth - halfDrawableWidth) * progress / max + halfDrawableWidth;
        y = getHeight() / 2;

        drawBar(canvas);
        drawAnimPicture(canvas);
        //回调Draw过程
        postInvalidateDelayed(refreshTime);
    }

    //画动画
    private void drawAnimPicture(Canvas canvas) {
        if (isAnimRun) {
            switch (animMode) {
                case ANIM_NULL:
                    drawPicture(canvas);
                    break;
                case ANIM_ROTATE:
                    rotateCanvas(canvas);
                    drawPicture(canvas);
                    break;
                case ANIM_SCALE:
                    scaleCanvas(canvas);
                    drawPicture(canvas);
                    break;
                case ANIM_ROTATE_SCALE:
                    rotateCanvas(canvas);
                    scaleCanvas(canvas);
                    drawPicture(canvas);
                    break;
                case ANIM_FRAME:
                    drawable = getResources().getDrawable(drawableIds[frameIndex]);
                    drawPicture(canvas);
                    if (frameIndex >= drawableIds.length -1){
                        frameIndex = 0;
                    }else {
                        frameIndex++;
                    }
                    break;
            }
        } else {
            drawPicture(canvas);
        }
    }

    //画进度条
    private void drawBar(Canvas canvas){
        if (isRound) {
            rectFBG.set(0, y - progressHeight / 2 + progressHeightOffset,
                    progressWidth, y + progressHeight / 2 + progressHeightOffset);
            canvas.drawRoundRect(rectFBG, roundX, roundY, paintBackGround);
            rectFPB.set(0, y - progressHeight / 2 + progressHeightOffset,
                    x, y + progressHeight / 2 + progressHeightOffset);
            canvas.drawRoundRect(rectFPB, roundX, roundY, paintBar);
        } else {
            rectFBG.set(0, 0, getWidth(), getHeight());
            canvas.drawRect(rectFBG, paintBackGround);
            canvas.drawRect(0, 0, x, getHeight(), paintBar);
        }

    }

    //旋转画布
    private void rotateCanvas(Canvas canvas) {
        canvas.rotate(rotateDegree, x, y + drawableHeightOffset);
        rotateDegree += rotateRate;
    }

    //伸缩画布
    private void scaleCanvas(Canvas canvas) {
        if (scaleLevel >= scaleMax) {
            isScaleIncrease = false;
        } else if (scaleLevel <= scaleMin) {
            isScaleIncrease = true;
        }
        if (isScaleIncrease) {
            scaleLevel += scaleRate;
        } else {
            scaleLevel -= scaleRate;
        }
        canvas.scale(scaleLevel, scaleLevel, x, y + drawableHeightOffset);
    }

    //画图
    private void drawPicture(Canvas canvas) {
        drawable.setBounds(x - halfDrawableWidth,
                getHeight() / 2 - halfDrawableHeight + drawableHeightOffset,
                x + halfDrawableWidth,
                getHeight() / 2 + halfDrawableHeight + drawableHeightOffset);
        drawable.draw(canvas);
    }

    //重写onMeasure，以自定义获取进度条的宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);


        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            //在这里实现计算需要wrap_content时需要的宽
            width = halfDrawableWidth * 2;
        }
        if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            //在这里实现计算需要wrap_content时需要的高
            height = halfDrawableHeight * 2;
        }

        progressWidth = width;
        //如果不是自定义设置进度条高度，就直接把高度当作进度条高度
        if (!isSetBar) {
            progressHeight = height;
        }

        //如果有图片，就为图片预留空间
        if (drawable != null) {
            progressWidth = width - halfDrawableWidth;
        }

        //传入处理后的宽高
        setMeasuredDimension(width, height);
    }

    //进行进度改变之后的操作
    private synchronized void doProgressRefresh() {
        if (onProgressChangeListener != null) {
            onProgressChangeListener.onOnProgressChange(progress);
            if (progress >= max) {
                onProgressChangeListener.onOnProgressFinish();
            }
        }
    }

    //设置进度
    public void setProgress(int progress) {
        if (progress <= max) {
            this.progress = progress;
        } else {
            this.progress = max;
        }
        doProgressRefresh();
    }

    //设置动画开关
    public void setAnimRun(boolean isAnimRun) {
        this.isAnimRun = isAnimRun;
    }

    //设置要传入的图片ID数组
    public void setDrawableIds(int[] drawableIds) {
        this.drawableIds = drawableIds;
    }

    //设置图片
    public void setPicture(int id) {
        drawable = getResources().getDrawable(id);
    }

    public int getProgress() {
        return progress;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    public void setBackGroundColor(int backGroundColor) {
        this.backGroundColor = backGroundColor;
    }

    public int getBackGroundColor() {
        return backGroundColor;
    }

    public void setBarColor(int barColor) {
        this.barColor = barColor;
    }

    public int getBarColor() {
        return barColor;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setIsRound(boolean isRound) {
        this.isRound = isRound;
    }

    public boolean getIsRound() {
        return isRound;
    }

    public void setOnProgressChangeListener(OnProgressChangeListener onProgressChangeListener) {
        this.onProgressChangeListener = onProgressChangeListener;
    }

    public interface OnProgressChangeListener {
        public void onOnProgressChange(int progress);
        public void onOnProgressFinish();
    }
}
