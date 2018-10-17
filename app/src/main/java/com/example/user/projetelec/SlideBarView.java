package com.example.user.projetelec;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.transition.Slide;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SlideBarView extends SurfaceView implements SurfaceHolder.Callback {

    public float cornerX;
    public float cornerY;
    public float largeur;
    public float hauteur;
    private SlideBarListener slideCallBack;

    private void setupDimensions(){
        cornerX = 2*(getWidth()/5);
        cornerY = (getHeight()/10);
        largeur = 3*getWidth()/5;
        hauteur = 9*(getHeight()/10);
    }

    public SlideBarView (Context context){
        super(context);
        getHolder().addCallback(this);
        if (context instanceof SlideBarListener)
            slideCallBack = (SlideBarListener) context;
    }

    public SlideBarView (Context context, AttributeSet attributes, int style){
        super (context, attributes, style);
        getHolder().addCallback(this);
        if (context instanceof SlideBarListener)
            slideCallBack = (SlideBarListener) context;
    }

    public SlideBarView(Context context, AttributeSet attributes){
        super (context, attributes);
        getHolder().addCallback(this);
        if (context instanceof SlideBarListener)
            slideCallBack = (SlideBarListener) context;
    }


    private void drawSlideBar(float position){
        if (getHolder().getSurface().isValid()){
            Canvas myCanvas = this.getHolder().lockCanvas();
            Paint colors = new Paint();
            int id = getId();
            switch (id){
                case R.id.slide2 :
                    myCanvas.rotate(-90, getWidth()/2, getHeight()/2);
            }
            myCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);


            colors.setARGB(255,255,0,255);
            myCanvas.drawRect(cornerX, cornerY, largeur, hauteur, colors);  //xmin  ,  ymin  , xmax, ymax  , couleur


            colors.setARGB(255,0,150,150);
            float etat = cornerY + (cornerY*2)/6;
            while (etat < hauteur){
                myCanvas.drawRect(cornerX, etat, largeur, etat + 5, colors);
                etat = etat + (cornerY*2)/3;
            }

            colors.setARGB(255,255,255,255);
            if (position <= hauteur && position>=cornerY){
                myCanvas.drawRect(cornerX-(getWidth()/10), position-((cornerY*2)/12), largeur+(getWidth()/10), position+((cornerY*2)/12), colors);
            } else {
                if (position > hauteur){
                    myCanvas.drawRect(cornerX-(getWidth()/10), hauteur-((cornerY*2)/12), largeur+(getWidth()/10), hauteur+((cornerY*2)/12), colors);
                } else {
                    myCanvas.drawRect(cornerX-(getWidth()/10), cornerY-((cornerY*2)/12), largeur+(getWidth()/10), cornerY+((cornerY*2)/12), colors);
                }
            }



            getHolder().unlockCanvasAndPost(myCanvas);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        setupDimensions();
        drawSlideBar(getHeight()/2);
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
            int id = getId();
            float displacement = 0;
            Log.d("err","ALLLLOOOOOOOOOOOOOOOOOOOOO");
            switch (id){
                case R.id.slide1 :
                    displacement = e.getY();
                    break;
                case R.id.slide2 :
                    displacement = e.getX();
            }
            drawSlideBar(displacement);

            float percentage = 0;
            switch (id){
                case R.id.slide1 :
                    percentage = 1 - (e.getY()/(getHeight()/2));
                    break;
                case R.id.slide2 :
                    percentage = 1 - (e.getX()/(getWidth()/2));
                    break;
            }
            slideCallBack.onSlideMoved(percentage, getId());
        }
        return true;
    }



    public interface SlideBarListener {
        void onSlideMoved(float yPercent, int id);
    }

}
