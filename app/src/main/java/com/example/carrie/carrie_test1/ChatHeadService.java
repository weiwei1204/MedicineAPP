package com.example.carrie.carrie_test1;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by jonathan on 2017/9/25.
 */

public class ChatHeadService extends Service {
    private WindowManager windowManager;
    private ImageView chatHead;
    private static final int REQUEST_CODE = 1;
    private View mChatHeadView;
    private Context mContext;
    private FrameLayout mFrameLayout;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams params;
    Context context;
    WindowManager.LayoutParams layoutParams;
    private WindowManager windowManager2;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mChatHeadView = LayoutInflater.from(this).inflate(R.layout.activity_lex_connect ,null);
//        RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) mChatHeadView.getLayoutParams();
//        params1.height = 130;
//        mChatHeadView.setLayoutParams(params1);

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        chatHead = new ImageView(this);
        chatHead.setImageResource(R.drawable.rab);

//
//        params = new WindowManager.LayoutParams(
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                WindowManager.LayoutParams.TYPE_TOAST,
//                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//                PixelFormat.TRANSLUCENT);
//
//        params.gravity = Gravity.TOP | Gravity.LEFT;
//        params.x = 0;
//        params.y = 100;


        params = new WindowManager.LayoutParams();

        params.format = PixelFormat.RGBA_8888;

        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        params.gravity = Gravity.LEFT | Gravity.TOP;


            if(Build.VERSION.SDK_INT > 24){
                params.type = WindowManager.LayoutParams.TYPE_PHONE;
            }else{
                params.type = WindowManager.LayoutParams.TYPE_TOAST;
            }


        params.x = 0;
        params.y = 0;


        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        windowManager.addView(chatHead, params);



        final GestureDetector gestureDetector = new GestureDetector(this, new MyGestureDetector());
        View.OnTouchListener gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                chatHead.performClick();
                return gestureDetector.onTouchEvent(motionEvent);
            }
        };
        ImageView closebutton = new ImageView(this);
        closebutton.setImageResource(R.drawable.ic_close);
        closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopSelf();
            }
        });
//        chatHead.setOnTouchListener(new View.OnTouchListener() {
//            private int lastAction;
//            private int initialX;
//            private int initialY;
//            private float initialTouchX;
//            private float initialTouchY;
//
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                switch (motionEvent.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//
//                        //remember the initial position.
//                        initialX = params.x;
//                        initialY = params.y;
//
//                        //get the touch location
//                        initialTouchX = motionEvent.getRawX();
//                        initialTouchY = motionEvent.getRawY();
//
//                        lastAction = motionEvent.getAction();
//                        return true;
//                    case MotionEvent.ACTION_UP:
//                        //As we implemented on touch listener with ACTION_MOVE,
//                        //we have to check if the previous action was ACTION_DOWN
//                        //to identify if the user clicked the view or not.
//                        if (lastAction == MotionEvent.ACTION_DOWN) {
//                            //Open the chat conversation click.
//                            Intent intent = new Intent(ChatHeadService.this, TextActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                            startActivity(intent);
//
//                            //close the service and remove the chat heads
//                            stopSelf();
//                        }
//                        lastAction = motionEvent.getAction();
//                        return true;
//                    case MotionEvent.ACTION_MOVE:
//                        //Calculate the X and Y coordinates of the view.
//                        params.x = initialX + (int) (motionEvent.getRawX() - initialTouchX);
//                        params.y = initialY + (int) (motionEvent.getRawY() - initialTouchY);
//
//                        //Update the layout with new X & Y coordinate
//                        windowManager.updateViewLayout(chatHead, params);
//                        lastAction = motionEvent.getAction();
//
//
//                        return true;
//
//                }
//
//                return false;
//            }
//
//        }
//
//        );

        chatHead.setOnTouchListener(gestureListener);

    }





    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != chatHead) {
            windowManager.removeView(chatHead);
        }
    }

    private class MyGestureDetector extends android.view.GestureDetector.SimpleOnGestureListener {
        private int initialX;
        private int initialY;
        private float initialTouchX;
        private float initialTouchY;


        @Override
        public boolean onDown(MotionEvent e) {
            initialX = params.x;
            initialY = params.y;
            initialTouchX = e.getRawX();
            initialTouchY = e.getRawY();
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            params.x = initialX + (int) (e2.getRawX() - initialTouchX);
            params.y = initialY + (int) (e2.getRawY() - initialTouchY);
            windowManager.updateViewLayout(chatHead, params);

            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.i("head", "clicked");
            Toast.makeText(ChatHeadService.this, "Hello There!", Toast.LENGTH_SHORT).show();
            Intent it = new Intent(ChatHeadService.this,LexConnect.class);
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(it);
            return true;
        }
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            return true;
        }
        @Override
        public void onLongPress(MotionEvent ev) {
            Intent i=new Intent(getApplicationContext(),MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            stopSelf();
            Log.d("DEBUG","onLongPress");
        }



    }

}
