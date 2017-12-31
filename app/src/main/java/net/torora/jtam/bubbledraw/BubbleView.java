package net.torora.jtam.bubbledraw;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

@SuppressLint("AppCompatCustomView")
public class BubbleView extends ImageView implements View.OnTouchListener{
    private ArrayList<Bubble> bubbleList;
    private final int DELAY = 16;
    private Paint myPaint = new Paint();
    private Handler h;

    public BubbleView (Context context, AttributeSet attrs){
        super(context, attrs);

        bubbleList = new ArrayList<Bubble>();
        myPaint.setColor(Color.WHITE);
        h = new Handler();

        this.setOnTouchListener(this);

        /**
        for (int x =0; x < 100; x++)
            bubbleList.add(new Bubble((int)(Math.random()* 300),
                    (int)(Math.random() * 400), 50));
         */
    }

    private Runnable r = new Runnable() {
        @Override
        public void run() {
            // update all the bubbles
            for (Bubble bubble: bubbleList){
                bubble.update();
            }
            // redraw the screen
            invalidate();
        }
    };

    protected void onDraw(Canvas c){
        // test the ability to draw bubbles
        /**
        for (int x =0; x < 3; x++)
            bubbleList.add(new Bubble((int)(Math.random()* 300),
                (int)(Math.random() * 400), 50)); */

        for(Bubble bubble:bubbleList){
            myPaint.setColor(bubble.color);
            c.drawOval(bubble.x - (bubble.size / 2), bubble.y - bubble.size/2,
                        bubble.x + bubble.size/2, bubble.y + bubble.size/2,
                        myPaint);
        }
        h.postDelayed(r, DELAY);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        bubbleList.add(new Bubble(
                (int)event.getX(),
                (int)event.getY(),
                (int)(Math.random() * 50 + 50)));
        return true;
    }

    public class Bubble{
        /** A bubble needs x,y location and a size */
        public int x;
        public int y;
        public int size;
        public int color;
        public int xspeed;
        public int yspeed;
        private final int MAX_SPEED = 5;

        public Bubble(int newX, int newY, int newSize){
            x = newX;
            y = newY;
            size = newSize;
            color = Color.argb((int)(Math.random()*256),
                    (int)(Math.random()*256),
                    (int)(Math.random()*256),
                    (int)(Math.random()*256));
            xspeed = (int)(Math.random() * MAX_SPEED * 2 - MAX_SPEED);
            yspeed = (int)(Math.random() * MAX_SPEED * 2 - MAX_SPEED);

            if (xspeed ==0 && yspeed == 0){
                xspeed = 1;
                yspeed = 1;
            }
        }
        public void update(){
            x += xspeed;
            y += yspeed;

            // collison detection with edges of the View
            if (x <= size / 2 || x + size / 2 >= getWidth())
                xspeed = -1 * xspeed;
            if (y <= size / 2 || y + size / 2 >= getHeight())
                yspeed = -1;
        }
    }
}
