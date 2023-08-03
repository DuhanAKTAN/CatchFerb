package com.duhanaktan.catchferb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView topS;
    TextView score;
    TextView timeText;
    Handler handler;
    Runnable runnable;
    ImageView imageView0,imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8;
    ImageView[] imageArray;
    int scoreText=0;
    int top=0;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //tanımladığımız obje ve değişkenlerin atamalırını yapıyoruz
        topS=findViewById(R.id.topS);
        score=findViewById(R.id.Score);
        timeText=findViewById(R.id.timeText);

        imageView0=findViewById(R.id.imageView0);
        imageView1=findViewById(R.id.imageView1);
        imageView2=findViewById(R.id.imageView2);
        imageView3=findViewById(R.id.imageView3);
        imageView4=findViewById(R.id.imageView4);
        imageView5=findViewById(R.id.imageView5);
        imageView6=findViewById(R.id.imageView6);
        imageView7=findViewById(R.id.imageView7);
        imageView8=findViewById(R.id.imageView8);

        imageArray=new ImageView[]{imageView0,imageView1,imageView2,imageView3,imageView4,imageView5,
        imageView6,imageView7,imageView8};

        sharedPreferences=getSharedPreferences("com.duhanaktan.catchferb", Context.MODE_PRIVATE);
        int value=sharedPreferences.getInt("topValue",0);
        topS.setText("Top Score: "+value);
        timer();
        hideImage();
    }
    // ekranda çıkan Ferb ü yakalayıp tıklayabilirsen skoru arttırır.
    public void ImageClick(View view){
        for(ImageView image:imageArray){
            image.setEnabled(false);
        }
        scoreText++;
        score.setText("Score: "+scoreText);
    }
    // oyun aşlar başlamaz 15 saniyeden geri saymaya başlar (onCreate de çağırdık)
    public void timer(){
        new CountDownTimer(15000,1000){

            @Override
            public void onTick(long l) {
                timeText.setText("Time: "+l/1000);
            }

            @Override
            public void onFinish() {
                if(scoreText>top){   //en yüksek skoru kontrol eder ver mini veritabanına kaydeder
                    top=scoreText;
                }
                sharedPreferences.edit().putInt("topValue",top).apply();//en yüksek skoru kaydeder

                timeText.setText("Time Off");
                handler.removeCallbacks(runnable);//arka planda çalışan runnable ı kapatır
                for(ImageView i:imageArray){
                    i.setVisibility(View.INVISIBLE);
                }

                //oyunun bitiminde alert dialog açar
                AlertDialog.Builder alert= new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Restart?");
                alert.setMessage("Want to Restart the game?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent=new Intent(MainActivity.this,MainScreenActivity.class);
                        startActivity(intent);
                    }
                });
                alert.show();
            }
        }.start();
    }
    //resimleri random bir şekilde ekranda gösterir ve tıklanılabilirliği ayarlar
    public void hideImage(){
        handler=new Handler();
        runnable=new Runnable() {
            @Override
            public void run() {
                for(ImageView i:imageArray){
                    i.setEnabled(true);
                    i.setVisibility(View.INVISIBLE);
                }
                Random random=new Random();
                int i=random.nextInt(9);
                imageArray[i].setVisibility(View.VISIBLE);
                handler.postDelayed(runnable,500);
            }
        };
        handler.post(runnable);
    }
}