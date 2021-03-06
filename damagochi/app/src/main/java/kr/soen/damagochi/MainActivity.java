package kr.soen.damagochi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.*;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.CountDownTimer;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;//java.util.logging.Handler is the wrong class to import
import java.lang.String;
import android.view.View.OnClickListener;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.logging.LogRecord;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "LogTest"; //로그 사용을 위해서 정의해둔 값
    WebView mWeb;// 웹페이지 객체
    final static int ACT_EDIT = 0;
    int deviceWidth; //기기 가로길이
    int deviceHeight; //기기 세로길이

    int moveDistance = 0; //기본 위치 기준, 좌우로 이동한 값
    int moveDistance2 =0; //빗자루 이동 거리

    //캐릭터가 위치한 xy좌표
    int petLeftX = 0;
    int petRightX = 0;
    int petTopY = 0;
    int petBottomY = 0;

    int sign = +1; // 캐릭터 방향(양/음)
    int legOrder = 0; // 캐릭터의 움직임 효과, 보폭 표현
    int riceOrder = 0;

    LinearLayout linear;
    FrameLayout frameLayout;

    MyView vw; //캐릭터가 들어있는 뷰
    MyView2 vw2;
    MyView4 vw4;
    public static MyView3[] dungView = new MyView3[5];


    Handler mHandler;

    private GoogleApiClient client;

    @Override //액티비티가 처음 만들어졌을 때 호출
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        linear = (LinearLayout) View.inflate(this, R.layout.activity_main, null);
        setContentView(linear);

        //디바이스 가로,세로 길이 구하기
        DisplayMetrics disp = getApplicationContext().getResources().getDisplayMetrics();
        deviceWidth = disp.widthPixels;
        deviceHeight = disp.heightPixels;
        Log.i(TAG, "deviceWidth = " + deviceWidth + ", deviceHeight = " + deviceHeight);


        //각 버튼에 리스너 등록
        findViewById(R.id.rice).setOnClickListener(btnClickListener);
        findViewById(R.id.play).setOnClickListener(btnClickListener);
        findViewById(R.id.clean).setOnClickListener(btnClickListener);
        findViewById(R.id.gift).setOnClickListener(btnClickListener);

////////////db에 있는 사용자 정보 가져오기

        SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
        /*String username = prefs.getString("id", "");
        String password = prefs.getString("pw", "");*/
        String username = "r";
        String password = "r";
        String type = "State";
        StateConfirm stateConfirm = new StateConfirm(this);


        String choice = null;
        try {
            choice = stateConfirm.execute(type, username, password).get();
            Log.i(TAG,"지금 OK?"+choice);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(choice.equals("hello")) { //그냥 ==로 하면 오류나네
            Log.i(TAG,"myThread confirm");
        }

        //////똥 갯수에 맞게 랜덤으로 투척////////////
        String dungTmp = choice.split("/")[2];
        int numOfDung = Integer.valueOf(dungTmp);

        frameLayout = (FrameLayout)findViewById(R.id.dynamic_layout);
        vw = new MyView(this);
        frameLayout.addView(vw);


        for(int i=0 ; i< numOfDung; i++) {

            dungView[i] = new MyView3(this);
            frameLayout.addView(dungView[i]);//ㅠㅠ... 리니어 레이아웃에선 addview를 하면 덮어쓰기가 되어버린다. 그래서 removeview를 하지않으면 추가된 애가 보이지 않음.... 따라서 프레임 레이아웃을 사용해야한다,

        }
        //frameLayout.removeView(v); //recreate()로 하면 똥들이 다시 랜덤값으로 옮기면서 똥하나가 사라지니, 뭔가 어색함. 클릭한 똥만 화면에서 사라지게 하면 된다.
        setContentView(linear);


        //////   //////////사용자 레벨에 따라 그림 바꾸기
        ImageView iv= (ImageView)findViewById(R.id.level);
        iv.setImageResource(R.drawable.lv2);


        ///캐릭터 움직임 표현
        mHandler = new android.os.Handler() {
            public void handleMessage(android.os.Message msg){
                mHandler.sendEmptyMessageDelayed(0,600);
                vw.invalidate();
            }
        };
        mHandler.sendEmptyMessage(0);

    }

    Button.OnClickListener btnClickListener = new View.OnClickListener() {
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.rice: // feed pet
                    /* 토스트를 이용한 방법(레이아웃을 토스트로 띄우기)
                    LinearLayout linear_rice = (LinearLayout) View.inflate(MainActivity.this, R.layout.riceview, null);
                    Toast t1 = new Toast(MainActivity.this);
                    t1.setView(linear_rice);
                    t1.show();*/

                    //LinearLayout linear2 = (LinearLayout) View.inflate(MainActivity.this, R.layout.activity_main, null);



                    frameLayout.removeAllViews();
                    //frameLayout.removeView(vw);
                    //frameLayout.removeView(vw3);
                    vw2 = new MyView2(MainActivity.this);
                    frameLayout.addView(vw2);
                    setContentView(linear);


                    new CountDownTimer(5*1000,1000) {//1초간격으로 총 5초 진행
                        @Override
                        public void onTick(long millisUntilFinished) {
                            vw2.invalidate();
                        }

                        @Override
                        public void onFinish() {
                            recreate();
                            /*이렇게 하면 에러
                            linear.addView(vw);
                            setContentView(linear);
                            */
                        }
                    }.start();

                    break;

                case R.id.clean:

                    frameLayout.removeView(vw);

                    vw4 = new MyView4(MainActivity.this);
                    frameLayout.addView(vw4);
                    setContentView(linear);


                    new CountDownTimer(5*1000,1000) {//1초간격으로 총 5초 진행
                        @Override
                        public void onTick(long millisUntilFinished) {
                            vw4.invalidate();
                        }

                        @Override
                        public void onFinish() {

                            DungRemove dungRemove = new DungRemove(MainActivity.this);
                            String type="Dung";
                            String username="r";
                            String password="r";
                            dungRemove.execute(type, username, password);

                            recreate();

                        }
                    }.start();


                    break;

                case R.id.play: // play with pet inside 임시로 예금기능 구현

                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(intent, ACT_EDIT);
                    break;

                case R.id.gift: //connect CrowdFunding Site

                    new AlertDialog.Builder(MainActivity.this)
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
            recreate(); //본 액티비티 재실행
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


    // 캐릭터 이미지 출력
    class MyView extends View {
        public MyView(Context context) {
            super(context);
        }


        public void onDraw(Canvas canvas) {
            Resources res = getResources();
            BitmapDrawable bd = (BitmapDrawable) res.getDrawable(R.drawable.chicken);
            BitmapDrawable bd2 = (BitmapDrawable) res.getDrawable(R.drawable.chicken2);
            BitmapDrawable bd3 = (BitmapDrawable) res.getDrawable(R.drawable.chicken_reverse);
            BitmapDrawable bd4 = (BitmapDrawable) res.getDrawable(R.drawable.chicken_reverse2);

            Bitmap bit = bd.getBitmap();
            Bitmap bit2 = bd2.getBitmap();
            Bitmap bit3 = bd3.getBitmap();
            Bitmap bit4 = bd4.getBitmap();

            //캐릭터 왼쪽 x좌표
            petLeftX = deviceWidth / 2 - deviceWidth / 8 + moveDistance;
            //캐릭터 오른쪽 x좌표
            petRightX = deviceWidth / 2 + deviceWidth / 8 + moveDistance;
            //캐릭터 위쪽 y좌표
            petTopY = deviceHeight / 4 - deviceHeight / 10;
            //캐릭터 아래쪽 y좌표
            petBottomY = deviceHeight / 4 + deviceHeight / 10;
            Log.i(TAG, "petLeftX = " + petLeftX + ", petTopY = " + petTopY);

            if (petRightX > deviceWidth) {
                sign = -1;
            } else if (petLeftX < 0) {
                sign = 1;
            }
            moveDistance = moveDistance + sign * 50;
           /* Log.i(TAG, "x좌표" + petRightX);
            Log.i(TAG, "repaint!");*/

            //android.os.SystemClock.sleep(600);


            if (sign == 1 && legOrder == 0) {
                canvas.drawBitmap(bit, null, new Rect(petLeftX, petTopY, petRightX, petBottomY), null);
                legOrder += 1;
            } else if (sign == 1 && legOrder == 1) {
                canvas.drawBitmap(bit2, null, new Rect(petLeftX, petTopY, petRightX, petBottomY), null);
                legOrder -= 1;
            } else if (sign == -1 && legOrder == 0) {
                canvas.drawBitmap(bit3, null, new Rect(petLeftX, petTopY, petRightX, petBottomY), null);
                legOrder += 1;
            } else if (sign == -1 && legOrder == 1) {
                canvas.drawBitmap(bit4, null, new Rect(petLeftX, petTopY, petRightX, petBottomY), null);
                legOrder -= 1;
            }

            //invalidate();//onDraw 재호출
        }


    }

    //밥 먹는 이미지 출력
    class MyView2 extends View {
        public MyView2(Context context) {
            super(context);
        }


        public void onDraw(Canvas canvas) {
            Resources res = getResources();
            BitmapDrawable bd5 = (BitmapDrawable) res.getDrawable(R.drawable.rice_empty);
            BitmapDrawable bd6 = (BitmapDrawable) res.getDrawable(R.drawable.rice_full);



            Bitmap bit5 = bd5.getBitmap();
            Bitmap bit6 = bd6.getBitmap();


            //캐릭터 왼쪽 x좌표
            petLeftX = deviceWidth / 2 - deviceWidth / 8;
            //캐릭터 오른쪽 x좌표
            petRightX = deviceWidth / 2 + deviceWidth / 8;
            //캐릭터 위쪽 y좌표
            petTopY = deviceHeight / 4 - deviceHeight / 10;
            //캐릭터 아래쪽 y좌표
            petBottomY = deviceHeight / 4 + deviceHeight / 10;


            if(riceOrder==0) {
                canvas.drawBitmap(bit5, null, new Rect(petLeftX, petTopY, petRightX, petBottomY), null);
                riceOrder++;
            }else if(riceOrder==1) {
                canvas.drawBitmap(bit6, null, new Rect(petLeftX, petTopY, petRightX, petBottomY), null);
                riceOrder--;

            }

        }
    }

    //똥 이미지 출력
    class MyView3 extends View {

        Random random = new Random();
        //익명클래스는 생성자x
        public MyView3(Context context) {
            super(context);
        }
        public void onDraw(Canvas canvas) {

            Resources res = getResources();
            BitmapDrawable bd7 = (BitmapDrawable) res.getDrawable(R.drawable.dung);

            Bitmap bit7 = bd7.getBitmap();

            int randValueWidth = random.nextInt(deviceWidth);
            int randValueHeight = random.nextInt(deviceHeight);
            Log.i(TAG, "randValueWidth :" + randValueWidth + ", randValueHeight :" + randValueHeight);

            //캐릭터 왼쪽 x좌표
            int dung_petLeftX = randValueWidth - deviceWidth / 16;
            //캐릭터 오른쪽 x좌표
            int dung_petRightX = randValueWidth + deviceWidth / 16;
            //캐릭터 위쪽 y좌표
            int dung_petTopY = deviceHeight / 4 - deviceHeight / 20;
            //캐릭터 아래쪽 y좌표
            int dung_petBottomY = deviceHeight / 4 + deviceHeight / 20;


            canvas.drawBitmap(bit7, null, new Rect(dung_petLeftX, dung_petTopY, dung_petRightX, dung_petBottomY), null);

        }
    }


    //청소하는 이미지지

    class MyView4 extends View {

        //익명클래스는 생성자x
        public MyView4(Context context) {
            super(context);
        }
        public void onDraw(Canvas canvas) {

            Resources res = getResources();
            BitmapDrawable bd8 = (BitmapDrawable) res.getDrawable(R.drawable.broom);
            BitmapDrawable bd9 = (BitmapDrawable) res.getDrawable(R.drawable.broom2);

            Bitmap bit8 = bd8.getBitmap();
            Bitmap bit9 = bd9.getBitmap();

            //캐릭터 왼쪽 x좌표
            petLeftX = deviceWidth / 2 - deviceWidth / 8 + moveDistance2;
            //캐릭터 오른쪽 x좌표
            petRightX = deviceWidth / 2 + deviceWidth / 8 + moveDistance2;
            //캐릭터 위쪽 y좌표
            petTopY = deviceHeight / 4 - deviceHeight / 10;
            //캐릭터 아래쪽 y좌표
            petBottomY = deviceHeight / 4 + deviceHeight / 10;


            if (petRightX > deviceWidth*3/4) {//가로길이 3/4지점까지만 오른쪽으로
                sign = -1;
            } else if (petLeftX < deviceWidth*1/4) {//가로길이 1/4지점까지만 왼쪽으로
                sign = 1;
            }
            moveDistance2 = moveDistance2 + sign * 50;


            if (sign == 1 && legOrder == 0) {
                canvas.drawBitmap(bit8, null, new Rect(petLeftX, petTopY, petRightX, petBottomY), null);
                legOrder += 1;
            } else if (sign == 1 && legOrder == 1) {
                canvas.drawBitmap(bit9, null, new Rect(petLeftX, petTopY, petRightX, petBottomY), null);
                legOrder -= 1;
            } else if (sign == -1 && legOrder == 0) {
                canvas.drawBitmap(bit8, null, new Rect(petLeftX, petTopY, petRightX, petBottomY), null);
                legOrder += 1;
            } else if (sign == -1 && legOrder == 1) {
                canvas.drawBitmap(bit9, null, new Rect(petLeftX, petTopY, petRightX, petBottomY), null);
                legOrder -= 1;
            }

            //invalidate();//onDraw 재호출
        }


    }
}







