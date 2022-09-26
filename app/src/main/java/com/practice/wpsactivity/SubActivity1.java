package com.practice.wpsactivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SubActivity1 extends AppCompatActivity/**implements AutoPermissions**/{

    Button button1;

    IntentFilter intentFilter = new IntentFilter();
    WifiManager wifiManager;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;

    BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {
            // wifiManager.startScan(); 시  발동되는 메소드 ( 예제에서는 버튼을 누르면 startScan()을 했음.)
            boolean success
                    = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false);
                    //스캔 성공 여부 값 반환
            if (success) {
                scanSuccess();
            } else {
                scanFailure();
            }
        }// onReceive()..
    };

    private void scanSuccess() {    // Wifi검색 성공
        List<ScanResult> results = wifiManager.getScanResults();
        mAdapter=new MyAdapter(results);
        recyclerView.setAdapter(mAdapter);
    }

    private void scanFailure() {    // Wifi검색 실패
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub1);

        recyclerView=findViewById(R.id.rv_recyclerview);

        //Wifi Scan 관련
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        getApplicationContext().registerReceiver(wifiScanReceiver, intentFilter);

        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubActivity1.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //버튼을 눌렀을 때
    public void clickWifiScan(View view) {
        boolean success = wifiManager.startScan();
        if (!success)
            Toast.makeText(SubActivity1.this, "Wifi Scan에 실패하였습니다." ,Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(SubActivity1.this, "wifiScan 성공..!", Toast.LENGTH_SHORT).show();
    }// clickWifiScan()..

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        /**
         * AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
         * **/
    }//Permission에 관한 메소드
}