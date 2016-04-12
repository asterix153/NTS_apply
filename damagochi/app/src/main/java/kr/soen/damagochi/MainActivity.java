package kr.soen.damagochi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.rice).setOnClickListener(btnClickListener);
        findViewById(R.id.play).setOnClickListener(btnClickListener);
        findViewById(R.id.stroll).setOnClickListener(btnClickListener);
        findViewById(R.id.gift).setOnClickListener(btnClickListener);
    }

    Button.OnClickListener btnClickListener = new View.OnClickListener() {
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.rice:
                    Toast.makeText(MainActivity.this, "hello", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.play:
                    LinearLayout linear_rice = (LinearLayout)View.inflate(MainActivity.this,R.layout.riceview,null);
                    Toast t1 = new Toast(MainActivity.this);
                    t1.setView(linear_rice);
                    t1.show();
                    break;
            }

        }
    };
}

