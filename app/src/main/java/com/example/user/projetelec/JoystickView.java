package com.example.user.projetelec;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class JoystickView extends SurfaceView implements SurfaceHolder.Callback {

    public float centerX;
    public float centerY;
    public float baseRadius;
    public float hatRadius;
    private JoystickListener joystickCallBack;

    private void setupDimensions(){
        centerX = getWidth()/2;
        centerY = getHeight()/2;
        baseRadius = Math.min(getWidth(), getHeight())/3;
        hatRadius = Math.min(getWidth(), getHeight())/5;
    }


    public JoystickView (Context context){
        super(context);
        getHolder().addCallback(this);
        if (context instanceof JoystickListener)
            joystickCallBack = (JoystickListener) context;
    }

    public JoystickView (Context context, AttributeSet attributes, int style){
        super (context, attributes, style);
        getHolder().addCallback(this);
        if (context instanceof JoystickListener)
            joystickCallBack = (JoystickListener) context;
    }

    public JoystickView(Context context, AttributeSet attributes){
        super (context, attributes);
        getHolder().addCallback(this);
        if (context instanceof JoystickListener)
            joystickCallBack = (JoystickListener) context;
    }



    private void drawJoystick(float newX, float newY){
        if (getHolder().getSurface().isValid()){
            Canvas myCanvas = this.getHolder().lockCanvas();
            Paint colors = new Paint();
            myCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            colors.setARGB(255,50,50,50);
            myCanvas.drawCircle(centerX, centerY, baseRadius, colors);
            colors.setARGB(255,0,0,255);
            myCanvas.drawCircle(newX, newY, hatRadius, colors);
            getHolder().unlockCanvasAndPost(myCanvas);
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder){
        setupDimensions();
        drawJoystick(centerX, centerY);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
         if (e.getAction() != e.ACTION_UP) {
             float displacement = (float) Math.sqrt((Math.pow(e.getX() - centerX, 2)) + Math.pow(e.getY() - centerY, 2));
             if (displacement < baseRadius) {
                 drawJoystick(e.getX(), e.getY());
                 joystickCallBack.onJoystickMoved((e.getX() - centerX) / baseRadius, (e.getY() - centerY) / baseRadius, getId());
             } else {
                 float ratio = baseRadius / displacement;
                 float constrainedX = centerX + (e.getX() - centerX) * ratio;
                 float constrainedY = centerY + (e.getY() - centerY) * ratio;
                 drawJoystick(constrainedX, constrainedY);
                 joystickCallBack.onJoystickMoved((constrainedX - centerX) / baseRadius, (constrainedY - centerY) / baseRadius, getId());
             }
         } else {
             drawJoystick(centerX, centerY);
             joystickCallBack.onJoystickMoved(0, 0, getId());
         }
         return true;
        }

    public interface JoystickListener {
        void onJoystickMoved(float xPercent, float yPercent, int id);
    }
}
