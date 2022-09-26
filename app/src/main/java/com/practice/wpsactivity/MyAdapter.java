package com.practice.wpsactivity;

import android.content.Context;
import android.content.Intent;
import android.net.DhcpInfo;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/*todo
    리사이클러뷰를 이용하여
    주변에 위치하고 있는 매장들을 확인함과 동시에,
    매장 와이파이를 클릭하면 네이버창이 떠서
    검색할 수 있는 기능을 넣어주었다.
* */
//리사이클러 뷰의 어댑터 사용을 위해서는
//3개의 메소드를 override해줘야 한다.
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    //와이파이를 ScanResult를 List로 띄워주고, 각 항목을 list로 접근할수 있게.
    private List<ScanResult> items;
    //add
    private Context mcontext;

    //생성자가 아이템들을 나열을 한다.
    public MyAdapter(List<ScanResult> items){
        this.items=items;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mcontext = parent.getContext();

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item , parent, false);
        return new MyViewHolder(itemView);
    }

    //버튼을 만들어서 바인딩 해주었다
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        ScanResult item=items.get(position);
        holder.setItem(items.get(position));

        /**추가한 부분**/
        holder.btncon.findViewById(R.id.btn_connect);
    }

    //아이템리스트 개수 가져옴
    @Override
    public int getItemCount() {
        return items.size();
    }

    //inner class
    //todo // Myadapter에서 해당 항목에 대한 이벤트를 처리해주는 부분.(Viewholder)
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvWifiName;
        public Button btncon;
        private String domain_link;

        private WifiManager wifiManager = (WifiManager)mcontext.getSystemService(Context.WIFI_SERVICE);

        DhcpInfo dhcpInfo =wifiManager.getDhcpInfo() ;
        int serverIp= dhcpInfo.ipAddress;
         String ipAddress
         = String.format(	"%d.%d.%d.%d",
         (serverIp & 0xff),
         (serverIp >> 8 & 0xff),
         (serverIp >> 16 & 0xff),
         (serverIp >> 24 & 0xff));
        String ipa = String.valueOf(serverIp);


        public MyViewHolder(View itemView) {

            super(itemView);
            tvWifiName = itemView.findViewById(R.id.tv_wifiName);

            btncon = itemView.findViewById(R.id.btn_connect);


            //itemview는 전체 아이템들을 의미 (리사이클러뷰의 아이템들)
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int currentpos = getAdapterPosition();
                    //list배열의 인덱스넘버
                    ScanResult scanResult = items.get(currentpos);
                    //토스트메시지로 itemView의 아이템을 클릭해주면 SSID가 반환되도록 해주었다.
                    Toast.makeText(mcontext, scanResult.SSID, Toast.LENGTH_SHORT).show();
                }
            });

            //btncon 에 연결하면 웹페이지에서 해당 매장 내용을 검색할수 있게 해준다.
            btncon.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.Q)
                @Override
                public void onClick(View view) {
                    int currentpos = getAdapterPosition();
                    ScanResult scanResult = items.get(currentpos);
                    int show_currentpos = currentpos+1;

                    Toast.makeText(mcontext, scanResult.SSID, Toast.LENGTH_SHORT).show();
                    Toast.makeText(mcontext.getApplicationContext(), "connect complete\n" + show_currentpos, Toast.LENGTH_SHORT).show();

                    if (ipAddress == "0.0.0.0"){
                        Toast.makeText(mcontext, "you should check wifistate.", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        //네이버를 통해 해당 매장에 대한 추가적인 정보를 검색할 수있게 추가 구성하였다.
                        domain_link = "https://naver.com";

                        Intent intentURL = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(domain_link));
                        mcontext.startActivity(intentURL);
                    }
                }
            });
        }

        //리사이클러뷰에 뜨는 와이파이 이름 SSID를 받아와서 띄운다.
        public void setItem(ScanResult item) {

            tvWifiName.setText(item.SSID);

        }
    }
}