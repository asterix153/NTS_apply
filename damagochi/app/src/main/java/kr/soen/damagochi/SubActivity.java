package kr.soen.damagochi;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SubActivity extends AppCompatActivity {
    EditText mEdit;
    EditText mEdit2;

    @Override //액티비티가 처음 만들어졌을 때 호출
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mEdit = (EditText)findViewById(R.id.stredit);
        mEdit2 = (EditText)findViewById(R.id.stredit2);

    }

    public void mOnClick(View v) {
        Intent intent = new Intent();
        intent.putExtra("TextOut",mEdit.getText().toString());
        intent.putExtra("TextOut2", mEdit2.getText().toString());
        setResult(RESULT_OK,intent);
        finish();

    }
}


