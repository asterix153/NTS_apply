package kr.soen.damagochi;



import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.content.*;

import java.util.concurrent.ExecutionException;


public class LoginActivity extends  AppCompatActivity{

    private static final String TAG = "LogTest";
    final static int ACT_EDIT = 0;

    EditText usernameEt, PassworkdEt;
    String username="";
    String password="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameEt = (EditText) findViewById(R.id.edt_username);
        PassworkdEt = (EditText) findViewById(R.id.edt_password);


        SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
        username = prefs.getString("id", "");
        password = prefs.getString("pw", "");

        if(username!="" && password!="") {

            Log.i(TAG,"first page");
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivityForResult(intent, ACT_EDIT);
        }

    }

    public void btn_login(View view) throws ExecutionException, InterruptedException {
        username = usernameEt.getText().toString();
        password = PassworkdEt.getText().toString();
        String type = "login";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);

        String choice = backgroundWorker.execute(type, username, password).get();

        if(choice.equals("login success")){ //그냥 ==로 하면 오류나네
            Log.i(TAG,"here!");

/*
            //////////////////////////////////////////////////////////////////
            SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
            //preference이름을 PrefName으로
            SharedPreferences.Editor editor = prefs.edit();

            editor.putString("id", username);			//사용자에게 입력받은 strid를 저장
            editor.putString("pw", password);		//사용자에게 입력받은 strpw를 저장
            editor.commit();							//commit
*/
/////////////////////////////////////////////////////////////////////////////////
            /*Intent intent = new Intent();
            intent.putExtra("TextOut", username);
            intent.putExtra("TextOut2", password);
            setResult(RESULT_OK, intent);
            finish();*/

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivityForResult(intent, ACT_EDIT);
        }
        else {
            Log.i(TAG,"here2!");
        }



    }


    public void btn_register(View view) {


        Intent intent2 = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivityForResult(intent2, ACT_EDIT);


        /*Intent intent = new Intent();
        intent.putExtra("TextOut",username);
        intent.putExtra("TextOut2", password);
        setResult(RESULT_OK, intent);
        finish();*/
    }

}

