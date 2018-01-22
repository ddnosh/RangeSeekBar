package la.xiong.seekbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

public class RangeSeekBar extends SeekBar {
    //滑块文字颜色
    private int mBlockTextColor;
    //滑块文字大小
    private float mBlockTextSize;
    //滑块颜色
    private int mBlockColor;
    //起始单位
    private int mStart;
    //滑块文字单位
    private String mUnit;

    //滑块文字内容
    private String mTitleText;
    //矩形背景的宽度、高度
    private float rect_width, rect_height;
    Paint paint, paints;
    RectF rect;
    //文字的宽度
    private float numTextWidth;
    //测量seekbar的规格
    private Rect rect_seek;
    private Paint.FontMetrics fm;

    public static final int TEXT_ALIGN_LEFT = 0x00000001;
    public static final int TEXT_ALIGN_RIGHT = 0x00000010;
    public static final int TEXT_ALIGN_CENTER_VERTICAL = 0x00000100;
    public static final int TEXT_ALIGN_CENTER_HORIZONTAL = 0x00001000;
    public static final int TEXT_ALIGN_TOP = 0x00010000;
    public static final int TEXT_ALIGN_BOTTOM = 0x00100000;
    //文本中轴线X坐标
    private float textCenterX;
    //文本baseline线Y坐标
    private float textBaselineY;
    //文字的方位
    private int textAlign;

    public RangeSeekBar(Context context) {
        this(context, null);
    }

    public RangeSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RangeSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RangeSeekBar, 0, R.style.RangeSeekBarDefaultStyle);

        try {
            mBlockTextSize = array.getDimension(R.styleable.RangeSeekBar_blockTextSize, 16f);
            mBlockTextColor = array.getColor(R.styleable.RangeSeekBar_blockTextColor, Color.WHITE);
            mBlockColor = array.getColor(R.styleable.RangeSeekBar_blockColor, Color.BLUE);
            mStart = array.getInt(R.styleable.RangeSeekBar_start, 0);
            mUnit = array.getString(R.styleable.RangeSeekBar_unit);
        } finally {
            array.recycle();
        }

        paint = new Paint();
        //设置抗锯齿
        paint.setAntiAlias(true);
        //设置文字大小
        paint.setTextSize(mBlockTextSize);
        //设置文字颜色
        paint.setColor(mBlockTextColor);
        //设置控件的padding 给提示文字留出位置
        int left = dip2px(context, 30);
        int top = dip2px(context, 12);
        int right = dip2px(context, 30);
        int bottom = dip2px(context, 12);
        setPadding(left, top, right, bottom);
        //设置文字方向
        textAlign = TEXT_ALIGN_CENTER_HORIZONTAL | TEXT_ALIGN_CENTER_VERTICAL;

        paints = new Paint();
        paints.setColor(mBlockColor);
        paints.setAntiAlias(true);

        rect = new RectF(0, 0, 0, 0);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        rect_seek = this.getProgressDrawable().getBounds();
        super.onDraw(canvas);
        //定位seekbar控件的x坐标偏移位
        float bm_x = rect_seek.width() * getProgress() / getMax();
        //矩形左坐标
        float rectLeft = getPaddingLeft() - dip2px(getContext(), 23) + bm_x;
        //矩形上坐标
        float rectTop = 0;
        //矩形右坐标
        float rectRight = getPaddingLeft() + bm_x + dip2px(getContext(), 23);
        //矩形下坐标
        float rectBottom = rect_seek.bottom + getPaddingTop() + rect_seek.top + getPaddingBottom();
        rect.left = rectLeft;
        rect.top = rectTop;
        rect.right = rectRight;
        rect.bottom = rectBottom;
        //第二、三参数绘制矩形拐角的弧度
        canvas.drawRoundRect(rect, dip2px(getContext(), 9), dip2px(getContext(), 9), paints);
        //文本的宽度
        mTitleText = mStart + getProgress() + mUnit;
        numTextWidth = paint.measureText(mTitleText);
        //计算所绘制作为背景的矩形的宽高
        rect_width = rectRight - rectLeft;
        rect_height = rectBottom;
        float text_x = bm_x + getPaddingLeft() - numTextWidth / 2;
        //定位文本绘制的位置
        setTextLocation();
        //画文字
        canvas.drawText(mTitleText, text_x, textBaselineY, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //监听手势滑动，不断重绘文字和背景图的显示位置
        invalidate();
        System.out.println("RangeSeekBar's event is " + event.getAction());
        return super.onTouchEvent(event);
    }

    /**
     * 定位文本绘制的位置
     */
    private void setTextLocation() {

        fm = paint.getFontMetrics();

        float textCenterVerticalBaselineY = rect_height / 2 - fm.descent + (fm.descent - fm.ascent) / 2;
        switch (textAlign) {
            case TEXT_ALIGN_CENTER_HORIZONTAL | TEXT_ALIGN_CENTER_VERTICAL:
                textCenterX = rect_width / 2;
                textBaselineY = textCenterVerticalBaselineY;
                break;
            case TEXT_ALIGN_LEFT | TEXT_ALIGN_CENTER_VERTICAL:
                textCenterX = numTextWidth / 2;
                textBaselineY = textCenterVerticalBaselineY;
                break;
            case TEXT_ALIGN_RIGHT | TEXT_ALIGN_CENTER_VERTICAL:
                textCenterX = rect_width - numTextWidth / 2;
                textBaselineY = textCenterVerticalBaselineY;
                break;
            case TEXT_ALIGN_BOTTOM | TEXT_ALIGN_CENTER_HORIZONTAL:
                textCenterX = rect_width / 2;
                textBaselineY = rect_height - fm.bottom;
                break;
            case TEXT_ALIGN_TOP | TEXT_ALIGN_CENTER_HORIZONTAL:
                textCenterX = rect_width / 2;
                textBaselineY = -fm.ascent;
                break;
            case TEXT_ALIGN_TOP | TEXT_ALIGN_LEFT:
                textCenterX = numTextWidth / 2;
                textBaselineY = -fm.ascent;
                break;
            case TEXT_ALIGN_BOTTOM | TEXT_ALIGN_LEFT:
                textCenterX = numTextWidth / 2;
                textBaselineY = rect_height - fm.bottom;
                break;
            case TEXT_ALIGN_TOP | TEXT_ALIGN_RIGHT:
                textCenterX = rect_width - numTextWidth / 2;
                textBaselineY = -fm.ascent;
                break;
            case TEXT_ALIGN_BOTTOM | TEXT_ALIGN_RIGHT:
                textCenterX = rect_width - numTextWidth / 2;
                textBaselineY = rect_height - fm.bottom;
                break;
        }
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
