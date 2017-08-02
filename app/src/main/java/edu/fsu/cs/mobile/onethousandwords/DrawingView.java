package edu.fsu.cs.mobile.onethousandwords;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;


public class DrawingView extends View{

    private Path drawPath;  //draw path
    private Paint drawPaint;    //drawing paint
    private Paint canvasPaint;  //canvas paint
    private int paintColor = 0xFF660000;    //initial color
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;
    private float brushSize, oldBrushSize;
    private boolean erase = false;


    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w,h,oldw,oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // draw view
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //finds the path of each stroke
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
                //When view is touched, go to that location
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                break;
                //When moving, create path
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
                //Finalize path once they stop and reset for another path
            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }

        invalidate();   //onDraw executes
        return true;
    }

        //set up the drawing view
    public void setupDrawing() {
        brushSize = getResources().getInteger(R.integer.medium_size);
        oldBrushSize = brushSize;

        drawPath = new Path();
        drawPaint = new Paint();

            //setting path properties
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(brushSize);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    public void setColor(String newColor){
        invalidate();

        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
    }

    public void setBrushSize(float newBrushSize){
        float pixel = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, newBrushSize,
                getResources().getDisplayMetrics());
        brushSize = pixel;
        drawPaint.setStrokeWidth(brushSize);
    }

    public void setOldBrushSize(float lastSize){
        oldBrushSize = lastSize;
    }

    public float getOldBrushSize(){
        return oldBrushSize;
    }

    public void setErase(boolean boolErase){
        erase = boolErase;

        if (erase){
            drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        }
        else {
            drawPaint.setXfermode(null);
        }
    }

    public void newDraw(){
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }

}
