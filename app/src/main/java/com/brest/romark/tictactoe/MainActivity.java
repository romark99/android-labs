package com.brest.romark.tictactoe;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean isFirstPlayerTurn = true;

    private Button[] buttons = new Button[9];
    private Button btnStart;
    private TextView textResult;
    private int[] values = new int[9];
    private final int[][] indexesOfRows = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttons[0] = findViewById(R.id.buttonTL);
        buttons[1] = findViewById(R.id.buttonTC);
        buttons[2] = findViewById(R.id.buttonTR);
        buttons[3] = findViewById(R.id.buttonL);
        buttons[4] = findViewById(R.id.buttonC);
        buttons[5] = findViewById(R.id.buttonR);
        buttons[6] = findViewById(R.id.buttonBL);
        buttons[7] = findViewById(R.id.buttonBC);
        buttons[8] = findViewById(R.id.buttonBR);
        for (Button btn: buttons) {
            btn.setOnClickListener(this);
            btn.setBackgroundColor(Color.TRANSPARENT);
            btn.setEnabled(false);
        }
        btnStart = findViewById(R.id.btnStart);
        textResult = findViewById(R.id.textResult);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Button btn: buttons) {
                    btn.setBackgroundColor(Color.TRANSPARENT);
                    btn.setEnabled(true);
                    btnStart.setEnabled(false);
                    textResult.setText(R.string.FIRST_PLAYER_TURN);
                    values = new int[9];
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        Button btn = (Button) view;
        btn.setBackgroundResource(isFirstPlayerTurn ? R.drawable.cross :R.drawable.circle);
        btn.setEnabled(false);
        for (int i = 0; i < buttons.length; i++) {
            if (btn == buttons[i]) {
                values[i] = isFirstPlayerTurn ? 1 : 2;
            }
        }
        int who = whoWon();
        switch (who) {
            case 1:
                textResult.setText(R.string.PLAYER_1_WON);
                finishGame();
                break;
            case 2:
                textResult.setText(R.string.PLAYER_2_WON);
                finishGame();
                break;
            case 3:
                textResult.setText(R.string.DRAW);
                finishGame();
                break;
            default:
                isFirstPlayerTurn = !isFirstPlayerTurn;
                textResult.setText(isFirstPlayerTurn ?
                        R.string.FIRST_PLAYER_TURN :
                        R.string.SECOND_PLAYER_TURN);
                break;
        }
    }

    /**
     * 0 - if the game is not finished
     * 1 - if (X) the first player wins
     * 2 - if (O) the second player wins
     * 3 - if a draw
     * @return
     */
    private int whoWon() {
        boolean isDraw = true;
        for (int[] row: indexesOfRows) {
            int n = checkRow(row);
            if (n == 1) {
                return 1;
            }
            else if (n == 2) {
                return 2;
            }
            else if (isDraw && n == 0) {
                isDraw = false;
            }
        }
        if (isDraw) {
            return 3;
        }
        return 0;
    }

    /**
     * 0 - if the row is not finished
     * 1 - if (X) the first player wins
     * 2 - if (O) the second player wins
     * -1 - if the row is fulfilled
     * @return
     */
    private int checkRow(int[] indexes) {
       switch (values[indexes[0]] * values[indexes[1]] * values[indexes[2]]) {
           case 1:
               return 1;
           case 8:
               return 2;
           case 0:
               return 0;
       }
       return -1;
    }

    private void finishGame() {
        btnStart = findViewById(R.id.btnStart);
        for (Button btn: buttons) {
            btn.setEnabled(false);

        }
        btnStart.setEnabled(true);
        btnStart.setText(R.string.RESTART_GAME);
    }
}
