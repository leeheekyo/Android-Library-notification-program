package com.example.samsung.pnulibrary5;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by SAMSUNG on 2017-05-29.
 */

class StudyRoomReservationResult extends AppCompatActivity {

    private Button btn;
    private Button submit;
    private TextView text;
    private TextView title;

    private EditText[] sIDs = new EditText[5];
    private Button[] bIDs = new Button[5];

    private String inputString;
    private StringBuilder sb = new StringBuilder();

    private final int SIZE =5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_room_reservation_result);

        text = (TextView)findViewById(R.id.text);
        btn = (Button)findViewById(R.id.btn);
        submit = (Button)findViewById(R.id.submit);
        title = (TextView)findViewById(R.id.title);

        sIDs[0] = (EditText)findViewById(R.id.sID1);
        sIDs[1] = (EditText)findViewById(R.id.sID2);
        sIDs[2] = (EditText)findViewById(R.id.sID3);
        sIDs[3] = (EditText)findViewById(R.id.sID4);
        sIDs[4] = (EditText)findViewById(R.id.sID5);

        bIDs[0] = (Button)findViewById(R.id.bID1);
        bIDs[1] = (Button)findViewById(R.id.bID2);
        bIDs[2] = (Button)findViewById(R.id.bID3);
        bIDs[3] = (Button)findViewById(R.id.bID4);
        bIDs[4] = (Button)findViewById(R.id.bID5);

        int i;
        int size = title.getWidth()-50;
        for(i=0; i<SIZE; i++){
            sIDs[i].setWidth(size);
        }
        for(i=0;i<SIZE; i++){
            bIDs[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String urladdString="";
                    int identity =-1;
                    for(int i=0; i<SIZE; i++){
                        if(v.equals(bIDs[i])){
                            identity=i;
                            urladdString = sIDs[i].getText().toString();
                            loadHtml("localhost/idcheck.php?id="+urladdString);
                            break;
                        }
                    }
                    while(sb.length()==0) ;

                    String sbString = sb.toString();
                    if(!sbString.equals("0\n") && identity != -1){ //exsist id
                        boolean check=true;
                        for(int i=0;i<identity;i++){ // no duplication
                            if( (sIDs[i].getText().toString()).equals(sIDs[identity].getText().toString()) ) check=false;
                        }
                        if(check){
                            sIDs[identity].setFocusable(false);
                            bIDs[identity].setText("OK");
                            bIDs[identity].setBackgroundColor(Color.GREEN);
                            bIDs[identity].setOnClickListener(null);
                            if(identity==SIZE-1){
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(sIDs[identity].getWindowToken(), 0);

                            }
                        }
                    }

                    sb = new StringBuilder();

                }
            });
        }

        Intent intent = getIntent();
        inputString = intent.getStringExtra("Text"); //241.60415
        text.setText(inputString);

        btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent outIntent = getIntent();
                outIntent.putExtra("Text", 0);
                setResult(0, outIntent);
                finish();
            }

        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //http://164.125.35.32/temp/kyo/studyroomreservation.php?studyRoomNumber=242&studyRoomReservationTime=170614&user1=201224501&user2=201224502&user3=201224503&user4=201224504&user5=201224505

                loadHtml("localhost/studyroomreservation.php?studyRoomNumber="+inputString.substring(0,3)+"&studyRoomReservationTime="+inputString.substring(4)+"&user1="+sIDs[0].getText()+"&user2="+sIDs[1].getText()+"&user3="+sIDs[2].getText()+"&user4="+sIDs[3].getText()+"&user5="+sIDs[4].getText());
                while(sb.length()==0) ;

                String sbString = sb.toString();
                if(sbString.equals("0\n")){
                    Toast.makeText(StudyRoomReservationResult.this,"Success",Toast.LENGTH_SHORT);
                }
                else if(sbString.equals("1\n")){
                    Toast.makeText(StudyRoomReservationResult.this,"Cannot access to DB", Toast.LENGTH_SHORT);
                }
                else if(sbString.equals("2\n")){
                    Toast.makeText(StudyRoomReservationResult.this,"Create Error",Toast.LENGTH_SHORT);
                }

                sb = new StringBuilder();
            }
        });

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
}
