package com.project.tictactoegame;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class GameCanvas extends AppCompatActivity {

    Button restart;
    ImageButton pauseGameBtn;
    // X - 0 , O - 1
    int turn, count;
    int[] bufferArray;
    ImageView imageView;
    int[][] winningPositions;
    int winFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_canvas);

        turn = 0;
        count = 0;
        bufferArray = new int[9];
        Arrays.fill(bufferArray, 2);
        winningPositions = new int[][]{
                {0, 1, 2}, {0, 3, 6}, {0, 4, 8},
                {2, 4, 6}, {2, 5, 8},
                {1, 4, 7}, {3, 4, 5}, {6, 7, 8}};
        restart = findViewById(R.id.restart);
        pauseGameBtn = findViewById(R.id.pauseGameBtn);

        restart.setOnClickListener(v -> gameRestart());
        pauseGameBtn.setOnClickListener(v -> showDialog());
    }

    public void playerTap(View view) {
        changePlayerTurn(view);
        if (count > 4) checkWin();
        if (winFlag == 1) return;
        if (count == 9)
            drawGame();
    }

    public void changePlayerTurn(View view) {
        if (winFlag == 1) return;
        imageView = (ImageView) view;
        String position = (String) view.getTag();
        if (bufferArray[Integer.parseInt(position)] != 2) return;
        if (turn == 0) {
            imageView.setImageResource(R.drawable.x);
            bufferArray[Integer.parseInt(position)] = 0;
            turn = 1;
        } else {
            imageView.setImageResource(R.drawable.o);
            bufferArray[Integer.parseInt(position)] = 1;
            turn = 0;
        }
        count++;
    }

    public void gameRestart() {
        startActivity(getIntent());
        finish();
        overridePendingTransition(0, 0);
    }

    public void checkWin() {
        if (winFlag == 1) return;
        for (int[] winnerCheck : winningPositions) {
            if (bufferArray[winnerCheck[0]] == bufferArray[winnerCheck[1]] &&
                    bufferArray[winnerCheck[1]] == bufferArray[winnerCheck[2]] &&
                    bufferArray[winnerCheck[0]] != 2) {
                showWonMessage(bufferArray[winnerCheck[0]]);
                winFlag = 1;
            }
        }
    }

    public void drawGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game draw!")
                .setMessage("Game Draw! play again?")
                .setPositiveButton("Restart", (dialog, which) -> {
                    dialog.dismiss();
                    gameRestart();
                })
                .setNegativeButton("View board", (dialog, which) -> {
                    dialog.dismiss();
                })
                .setCancelable(false);
        builder.show();
    }

    public void showWonMessage(int winner) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String winnerName = winner == 0 ? "X" : "O";

        builder.setTitle(winnerName + " won the game!")
                .setMessage("Congratulations " + winnerName + " for winning the game, what you want to choose?")
                .setPositiveButton("Restart", (dialog, which) -> {
                    dialog.dismiss();
                    gameRestart();
                })
                .setNegativeButton("View Board", (dialog, which) -> dialog.dismiss())
                .setCancelable(false);
        builder.show();
    }

    public void showDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_alert);

        ImageButton homeBtn = dialog.findViewById(R.id.homeBtn);
        ImageButton restart = dialog.findViewById(R.id.restartBtn);

        dialog.show();

        homeBtn.setOnClickListener(v -> {
            dialog.dismiss();
            finish();
        });

        restart.setOnClickListener(v -> {
            dialog.dismiss();
            gameRestart();
        });
    }

}