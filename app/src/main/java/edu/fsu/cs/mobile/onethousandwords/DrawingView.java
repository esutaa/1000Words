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

    private Path drawPath;
    private Paint drawPaint;
    private Paint canvasPaint;
    private int paintColor = 0xFF660000;
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

    /*
        Initiates the DrawView in the DrawingFragment
     */

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }


    /* This function handles the motion events caused by the user and activates the methods
        needed for the drawing to work. The handling is done with a switch that changes
        when the user is on the screen, moves, or removes their finger.
    */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }

    /*
        This method sets up the drawing styles for which we wanted to use in our DrawingView
     */

    public void setupDrawing() {
        brushSize = getResources().getInteger(R.integer.medium_size);
        oldBrushSize = brushSize;

        drawPath = new Path();
        drawPaint = new Paint();

        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(brushSize);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    /*
        This method is called in the DrawingFragment when the user selects an imageButton that
        represents a color. The color is represented by a hexcode that is parsed here, and sets
        our Paint object to it.
     */

    public void setColor(String newColor){
        invalidate();

        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
    }

    /*
        This method is called in the DrawingFragment for when the user selects a brush size
        the float value that is sent in is defined by us in an XML file called dimens.xml.
        The value is sent and applied by the TypeValue.applyDimension method and the return value
        is the converted amount of pixels that represent the brush size selected. The Paint object
        is the set to the brush size selected.
     */

    public void setBrushSize(float newBrushSize){
        float pixel = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, newBrushSize,
                getResources().getDisplayMetrics());
        brushSize = pixel;
        drawPaint.setStrokeWidth(brushSize);
    }

    /*
        This method allowed for easy changes between new and old brushes by the user.
     */

    public void setOldBrushSize(float lastSize){
        oldBrushSize = lastSize;
    }

    public float getOldBrushSize(){
        return oldBrushSize;
    }

    /*
        The setErase function handles changing the transfer-method that is set to
        PorterDuff.Mode.Clear which effectively erases the paint that is selected.
     */

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
