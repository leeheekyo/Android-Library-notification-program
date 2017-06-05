package com.example.samsung.pnulibrary5;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StudyRoomReservation extends ActionBarActivity {

    private Button move;
    private TextView[] days = new TextView[4];
    private TextView title_l;

    private final int buttonCountMax = 4;
    private final int tCountMax = 3;
    private final int trCountMax=3;
    private final int timeCountMax = 15;


    private int daySelect;
    private String intentText =  "";
    private String myStudentID="123456789";

    TableRow[][] trs = new TableRow[tCountMax][trCountMax];
    TextView[][] tvs = new TextView[tCountMax][timeCountMax];

    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_room_reservation);

        move = (Button)findViewById(R.id.move);
        days[0] = (TextView)findViewById(R.id.today);
        days[1] = (TextView)findViewById(R.id.oneday);
        days[2] = (TextView)findViewById(R.id.twoday);
        days[3] = (TextView)findViewById(R.id.threeday);
        title_l = (TextView)findViewById(R.id.title_l);
        int size ;



        //date check
        long now = System.currentTimeMillis();
        Date date = new Date(now);

        SimpleDateFormat CurDataFormat = new SimpleDateFormat("MM-dd");

        int i;
        days[0].setText(CurDataFormat.format(date));
        String tstring  = days[0].getText().toString();
        daySelect = Integer.valueOf(tstring.substring(0,2)+tstring.substring(3));
        for(i=1;i<buttonCountMax;i++){
            date.setTime(date.getTime()+1000*24*60*60);
            days[i].setText(CurDataFormat.format(date));
        }
        for(i=0;i<buttonCountMax;i++){
            days[i].setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    for(int i=0; i<buttonCountMax; i++){
                        if(v.equals(days[i])){
                            String tstring  = days[i].getText().toString();
                            daySelect = Integer.valueOf(tstring.substring(0,2)+tstring.substring(3));
                        }
                        days[i].setBackgroundColor(Color.LTGRAY);
                    }

                    v.setBackgroundColor(Color.BLUE);
                    repaint();
                }
            });
        }



        move.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Intent Example
                Intent intent = new Intent(StudyRoomReservation.this, StudyRoomReservationResult.class); // 두번째 액티비티를 실행하기 위한 인텐트
                intent.putExtra("Text", intentText);
                startActivityForResult(intent, 0);

            }
        });

        //start table ui ***************************


        int trCount = 0;
        int tCount =0;
        int timeCount = 0;


        size = title_l.getWidth()/5;

        TableLayout[] tables = new TableLayout[3];
        tables[0] = (TableLayout)findViewById(R.id.l241);
        tables[1] = (TableLayout)findViewById(R.id.l242);
        tables[2] = (TableLayout)findViewById(R.id.l243);


        for(tCount=0;tCount<tCountMax;tCount++) {
            for (timeCount = 0, trCount=0; timeCount < timeCountMax; timeCount++) {
                if (timeCount % 5 == 0) {
                    trs[tCount][trCount] = new TableRow(this);
                    //trs[tCount][trCount].setPaddingRelative(1, 1, 1, 1);
                    trs[tCount][trCount].setBackgroundColor(Color.BLACK);
                }

                tvs[tCount][timeCount] = new TextView(this);
                if(timeCount ==0 || timeCount > 12){
                    tvs[tCount][timeCount].setBackgroundColor(Color.BLACK);
                }
                else {
                    if (timeCount < 2) tvs[tCount][timeCount].setText("0" + String.valueOf(timeCount + 8) + ":00");
                    else tvs[tCount][timeCount].setText(String.valueOf(timeCount + 8) + ":00");
                    tvs[tCount][timeCount].setBackgroundColor(Color.LTGRAY);
                    tvs[tCount][timeCount].setTextSize(14);
                    tvs[tCount][timeCount].setWidth(size);
                    tvs[tCount][timeCount].setGravity(Gravity.CENTER);
                    tvs[tCount][timeCount].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TextView b = (TextView) findViewById(v.getId());
                            //if(b.getDrawingCacheBackgroundColor()==Color.LTGRAY) {
                            v.setBackgroundColor(Color.GREEN);

                            //find object
                            boolean finish = false;
                            int tCount = -1;
                            int timeCount = -1;
                            for(int i=0; i<tCountMax; i++){
                                for(int j=0; j<timeCountMax; j++){
                                    if(v.equals(tvs[i][j])){
                                        finish=true;
                                        tCount =i;
                                        timeCount = j;
                                        break;
                                    }
                                }
                                if(finish) break;
                            }

                            if(tCount!=-1 && timeCount != -1) {
                                Intent intent = new Intent(StudyRoomReservation.this, StudyRoomReservationResult.class); // 두번째 액티비티를 실행하기 위한 인텐트

                                String result ="";
                                if(tCount==0) result+="241.";
                                else if(tCount==1) result+="242.";
                                else if(tCount==2) result+="243.";

                                result += String.valueOf(daySelect);
                                result += String.valueOf(timeCount+8);

                                intent.putExtra("Text", result); // 242.60514
                                startActivityForResult(intent, 0);
                            }
                            //}
                            //tvs[tCount][timeCount].setBackgroundColor(Color.BLUE);
                        }
                    });
                }

                tvs[tCount][timeCount].setId(tCount * 100 + timeCount);
                tvs[tCount][timeCount].setPadding(0, 25, 0, 25);



                trs[tCount][trCount].addView(tvs[tCount][timeCount]);

                if (timeCount % 5 == 4) {
                    tables[tCount].addView(trs[tCount][trCount]);
                    trCount++;
                }
            }
        }



        // start parsing ************************

        //Toast.makeText(StudyRoomReservation.this, sb.toString(), Toast.LENGTH_SHORT).show();
        //title_l.setText(sb.toString());

        repaint();





    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode == 0){
            Toast.makeText(this, "ok",Toast.LENGTH_SHORT).show();
        }
    }


    void loadHtml(final String urladdress) { // 웹에서 html 읽어오기
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL(urladdress);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();// 접속
                    if (conn != null) {
                        conn.setConnectTimeout(2000);
                        conn.setUseCaches(false);
                        if (conn.getResponseCode()==HttpURLConnection.HTTP_OK){
                            //    데이터 읽기
                            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
                            while(true) {
                                String line = br.readLine();
                                if (line == null) break;
                                sb.append(line+"\n");
                            }
                            br.close(); // 스트림 해제
                        }
                        conn.disconnect(); // 연결 끊기
                    }
                    // 값을 출력하기
                    Log.d("testCrawl", sb.toString());
                } catch (Exception e) {
                    sb.append("error");
                    e.printStackTrace();
                }
            }
        });
        t.start(); // 쓰레드 시작
    }

    public void repaint(){
        int tCount;
        int timeCount ;
        for(tCount=0;tCount<tCountMax;tCount++) {
            for (timeCount = 0; timeCount < timeCountMax; timeCount++) {
                if(timeCount ==0 || timeCount > 12){
                    tvs[tCount][timeCount].setBackgroundColor(Color.BLACK);
                }
                else {
                    tvs[tCount][timeCount].setBackgroundColor(Color.LTGRAY);
            }
        }

        //!!
        loadHtml("localhost/studyroomcheck.php");
        while(sb.length()==0) ;

        String sbstring = sb.toString();
        String[] sbstrings = sbstring.split("/");
        int c = sbstrings.length-1;
        int i;
        for(i=0; i<c; i++) {
            //Log.i("testString1",daySelect+" "+sbstrings[i].substring(4, sbstrings[i].length() - 2));

            if (daySelect==Integer.valueOf(sbstrings[i].substring(4, sbstrings[i].length() - 2))) {
                // 242.60516
                //Log.i("teststring2", sbstrings[i].substring(0, 3) + sbstrings[i].substring(4));

                int tCountone = -1;
                if (sbstrings[i].substring(0, 3).equals("241")) {
                    tCountone = 0;
                } else if (sbstrings[i].substring(0, 3).equals("242")) {
                    tCountone = 1;
                } else if (sbstrings[i].substring(0, 3).equals("243")) {
                    tCountone = 2;
                }
                //Log.i("testnumber", i + " " + tCountone);


                // 603 604 605 // 60516
                int buttonDate = Integer.valueOf(sbstrings[i].substring(4));

                if (buttonDate / 100 == daySelect) {
                    if (tCountone != -1) {
                        tvs[tCountone][(buttonDate % 100) - 8].setBackgroundColor(Color.DKGRAY);
                        tvs[tCountone][(buttonDate % 100) - 8].setOnClickListener(null);
                    }
                }

                //Log.i("teststring3", buttonDate / 100 + " " + daySelect);
            }
        }
        sb = new StringBuilder();

        loadHtml("localhost/studyroomcheckid.php?id="+myStudentID);
        while(sb.length()==0) ;

        sbstring = sb.toString();
        sbstrings = sbstring.split("/");
        c = sbstrings.length-1;
        for(i=0; i<c; i++) {
            if (daySelect==Integer.valueOf(sbstrings[i].substring(4, sbstrings[i].length() - 2))) {
                int tCountone = -1;
                if (sbstrings[i].substring(0, 3).equals("241")) {
                    tCountone = 0;
                } else if (sbstrings[i].substring(0, 3).equals("242")) {
                    tCountone = 1;
                } else if (sbstrings[i].substring(0, 3).equals("243")) {
                    tCountone = 2;
                }


                // 603 604 605 // 60516
                int buttonDate = Integer.valueOf(sbstrings[i].substring(4));

                if (buttonDate / 100 == daySelect) {
                    if (tCountone != -1) {
                        tvs[tCountone][(buttonDate % 100) - 8].setBackgroundColor(Color.RED);
                        tvs[tCountone][(buttonDate % 100) - 8].setOnClickListener(null);
                    }
                }

                //Log.i("teststring3", buttonDate / 100 + " " + daySelect);
            }
        }
        sb = new StringBuilder();

    }


}