package com.project.tictactoegame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    ImageButton twoPlayersBtn, playWithCpu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        twoPlayersBtn = findViewById(R.id.twoPlayer);
        playWithCpu = findViewById(R.id.vsCpu);

        recalculateScore();

        twoPlayersBtn.setOnClickListener(v -> startActivity(new Intent(this, GameCanvas.class)));
        playWithCpu.setOnClickListener(v -> startActivity(new Intent(this, CpuGameCanvas.class)));
    }

    public void recalculateScore() {
        sharedPreferences = getSharedPreferences("gameScore", MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        recalculateScore();
    }
}