package com.space.space_1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class BallView extends View {
    private int x;
    private int y;
    private int radius;
    private Paint paint;

    public BallView(Context context) {
        super(context);
        init();
    }

    public BallView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED);
        radius = 50;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(x, y, radius, paint);
    }

    public void setX(int x) {
        this.x = x;
        invalidate();
    }

    public void setY(int y) {
        this.y = y;
        invalidate();
    }
}
