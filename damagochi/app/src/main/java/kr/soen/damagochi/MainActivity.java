package kr.soen.damagochi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.*;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.*;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity  {
    private static final String TAG = "LogTest";
    WebView mWeb;
    final static int ACT_EDIT = 0;
    int deviceWidth;
    int deviceHeight;
    ImageView mAnimTarget;

    int moveDistance=0;
    int petLeftX=0;
    int petRightX=0;
    int petTopY=0;
    int petBottomY=0;

    int sign= +1;



    MyView vw;

    @Override //액티비티가 처음 만들어졌을 때 호출
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        LinearLayout linear = (LinearLayout) View.inflate(this, R.layout.activity_main, null);
        vw = new MyView(this);
        linear.addView(vw);
        setContentView(linear);

        //get Device_Size
        DisplayMetrics disp = getApplicationContext().getResources().getDisplayMetrics();
        deviceWidth = disp.widthPixels;
        deviceHeight = disp.heightPixels;
        Log.i(TAG, "deviceWidth = " + deviceWidth + ", deviceHeight = " + deviceHeight);


        //connect click_event with button
        findViewById(R.id.rice).setOnClickListener(btnClickListener);
        findViewById(R.id.play).setOnClickListener(btnClickListener);
        findViewById(R.id.stroll).setOnClickListener(btnClickListener);
        findViewById(R.id.gift).setOnClickListener(btnClickListener);


       // mAnimTarget = (ImageView)findViewById(R.id.chicken);
    }

    Button.OnClickListener btnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            Animation anim = null;


            switch (v.getId()) {
                case R.id.rice: // feed pet
                   /* anim = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0.8f,
                            Animation.RELATIVE_TO_PARENT,0,Animation.RELATIVE_TO_PARENT,0);
                    anim.setDuration(10000);
                    anim.setRepeatCount(-1);
                    anim.setRepeatMode(Animation.REVERSE);
                    mAnimTarget.startAnimation(anim);
                    break;*/
                    vw.petMove();
                    break;
                case R.id.play: // play with pet inside
                    LinearLayout linear_rice = (LinearLayout) View.inflate(MainActivity.this, R.layout.riceview, null);
                    Toast t1 = new Toast(MainActivity.this);
                    t1.setView(linear_rice);
                    t1.show();
                    break;
                case R.id.stroll: // play with pet outside    임시로 다른 액티비티 넘어가는 것
                    Intent intent = new Intent(MainActivity.this, SubActivity.class);
                    startActivityForResult(intent, ACT_EDIT);
                    break;

                case R.id.gift: //connect CrowdFunding Site

                    new android.app.AlertDialog.Builder(MainActivity.this)
                            .setTitle("선물하기")
                            .setMessage("크라우드 펀딩 사이트로 연결하시겠습니까?")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    setContentView(R.layout.funding_site);

                                    mWeb = (WebView) findViewById(R.id.web);
                                    mWeb.setWebViewClient(new MyWebClient());
                                    WebSettings set = mWeb.getSettings();
                                    set.setJavaScriptEnabled(true);
                                    set.setBuiltInZoomControls(true);
                                    mWeb.loadUrl("http://danaeri.dothome.co.kr");
                                }
                            })
                            .setNegativeButton("취소", null)
                            .show();

                    break;

            }

        }
    };

    //특정 작업을 주기적으로 실행하는 클래스

/*        final Timer timer;
        TimerTask timerTask;

        timerTask = new TimerTask() {

        @Override
        public void run () {
            Log.i(TAG, "타이머 작동중");
        }

    };

    }
*/


    class MyWebClient extends WebViewClient {
        /*
	 * 웹뷰 내 링크 터치 시 새로운 창이 뜨지 않고
	 * 해당 웹뷰 안에서 새로운 페이지가 로딩되도록 함
	 */
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            //mWebsetDefaultZoom(WebSettings.ZoomDensity.FAR);
            return true;
        }
    }

    //웹뷰에서 뒤로가기를 했을 때, 앱이 종료되지 않고 기존 액티비티로 이동하는 구문
    @Override
    public void onBackPressed() {
        if (mWeb.canGoBack()) {
            mWeb.goBack();
        } else {
            recreate();
            //setContentView(R.layout.activity_main);
            //System.exit(0);
        }
        //super.onBackPressed(); // 원래 뒤로가기 기능을 실행, 즉 종료
    }
    /*public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if ((keyCode == android.view.KeyEvent.KEYCODE_BACK) && mWeb.canGoBack()) { //canGoBack()은 지금 페이지에서 더이상 뒤로가기가 가능한지를 판단
            mWeb.goBack();
            return true;
        }
        else {
            setContentView(R.layout.activity_main);
        }
        return false;
    }*/


    // 캐릭터 이미지
   class MyView extends View {
        public MyView(Context context) {
            super(context);
        }


        public void onDraw(Canvas canvas) {
            android.content.res.Resources res = getResources();
            BitmapDrawable bd = (BitmapDrawable) res.getDrawable(R.drawable.chicken);
            Bitmap bit = bd.getBitmap();

            //캐릭터 왼쪽 x좌표
            petLeftX = deviceWidth / 2 - deviceWidth / 8 + moveDistance;
            //캐릭터 오른쪽 x좌표
            petRightX = deviceWidth / 2 + deviceWidth / 8 + moveDistance;
            //캐릭터 위쪽 y좌표
            petTopY = deviceHeight / 2 - deviceHeight / 10;
            //캐릭터 아래쪽 y좌표
            petBottomY = deviceHeight / 2 + deviceHeight / 10;

            canvas.drawBitmap(bit, null, new Rect(petLeftX, petTopY, petRightX, petBottomY), null);
            invalidate();

        }

        public void petMove() {
            if(petRightX > deviceWidth){
                sign= -1;
            }
            else if(petLeftX < 0) {
                sign=+1;
            }
            moveDistance = moveDistance + sign*50;
            Log.i(TAG,"x좌표"+petRightX);
        }
    }
}






