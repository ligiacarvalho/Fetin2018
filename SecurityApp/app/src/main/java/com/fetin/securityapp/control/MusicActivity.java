package com.fetin.securityapp.control;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fetin.securityapp.R;

public class MusicActivity extends AppCompatActivity {

    private Button botaoPlay;
    private Button botaoStop;
    private MediaPlayer somAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // criando os objetos no Layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        // Referenciando o "somAlarm" com a música que está na pasta RAW
        somAlarm = MediaPlayer.create(this, R.raw.music);

        // Referenciando os botoões da tela, para poder manipula-los
        botaoPlay = findViewById(R.id.BotaoPlayMusic);
        botaoStop = findViewById(R.id.BotaoStopMusic);

        // ao clicar no botaoPlay, toca a musica
        botaoPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                somAlarm.start();
            }
        });

        // ao clicar no botaoStop, para a musica
        botaoStop.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                somAlarm.pause();
            }
        });
    }

}
