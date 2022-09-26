package com.practice.wpsactivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button bt_1;
    private Button bt_2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //어플 처음실행할때 실행되는 부분
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bt_1 = findViewById(R.id.bt_1);
        bt_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SubActivity1.class);
                startActivity(intent);
            }
        });

        bt_2 = findViewById(R.id.bt_2);
        bt_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                todo
                    공인 ip는 버리고
                    사설 ip로 접근하기로 했다.
                    굳이 공인 ip를 사용할 이유가 없어짐.
                    사설 ip포트번호를 각 매장별로 동일하게 설정해두면
                    모든 매장에서 일반적으로 적용된다.
                * */


                String domain_link = "http://192.168.0.3:3000";

                //todo 무조건 웹페이지 연결주소는 domain_link로.
                Toast.makeText(MainActivity.this.getApplicationContext(),
                        domain_link,
                        Toast.LENGTH_SHORT).show();

                // todo domain_link로 연결하는 코드
                Intent webintent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(domain_link)));
                startActivity(webintent);

            }
        });

    }


}
