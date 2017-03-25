package com.example.hp.sarnesdice;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvPlayerTurnScore, tvComputerScore, tvPlayerOverallScore, tvSTATUS;
    private ImageView ivDiceFace;
    private Button btHold, btRoLL, btReset;
    private int[] diceFaces = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6,};
    private int computerTurnScore, computerOverallscore, playerTurnScore, playerOverallScore;

    private Handler handler;

    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tvPlayerTurnScore = (TextView) findViewById(R.id.tvPlayerTurnScore);
        tvPlayerOverallScore = (TextView) findViewById(R.id.tvPlayerOverallScore);
        tvComputerScore = (TextView) findViewById(R.id.tvComputerScore);
        tvSTATUS = (TextView) findViewById(R.id.tvSTATUS);
        ivDiceFace = (ImageView) findViewById(R.id.DiceFace);
        btHold = (Button) findViewById(R.id.HOLD);
        btRoLL = (Button) findViewById(R.id.ROLL);
        btReset = (Button) findViewById(R.id.RESET);

        btHold.setOnClickListener(this);
        btRoLL.setOnClickListener(this);
        btReset.setOnClickListener(this);

        btHold.setEnabled(false);
        handler = new Handler();


    }

    @Override
    public void onClick(View V) {
        switch (V.getId()) {
            case R.id.HOLD:
                hold();
                break;
            case R.id.ROLL:
                roll();
                break;
            case R.id.RESET:
                reset();
                break;


        }
    }


    private void roll() {
        btHold.setEnabled(true);
        tvPlayerTurnScore.setVisibility(View.VISIBLE);
        int rollNumber = getDiceFaceNumber();
        tvPlayerOverallScore.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Rolled:" + rollNumber, Toast.LENGTH_SHORT).show();
        playerTurnScore = rollNumber;
        playerOverallScore += rollNumber;
        if (rollNumber != 1) {
            tvPlayerTurnScore.setText("Your score is : " + playerTurnScore);

        } else {
            playerTurnScore = 0;
            tvPlayerOverallScore.setText("Your OVERALL score is : " + playerOverallScore);
            computerTurn();
        }

    }

    private int getDiceFaceNumber() {
        Random random = new Random();
        int i = random.nextInt(6);
        ivDiceFace.setImageResource(diceFaces[i]);
        return i + 1;
    }

    private void playerTurn() {
        resetPlayerTurnScore();
        Toast.makeText(this, "YOUR TURN", Toast.LENGTH_SHORT).show();
        btHold.setEnabled(false);
        btRoLL.setEnabled(true);
    }


    private void reset() {
        playerOverallScore = 0;
        computerOverallscore = 0;
        playerTurnScore = 0;
        computerTurnScore = 0;
        tvComputerScore.setText("Computer's overall score :" + computerOverallscore);
        tvPlayerOverallScore.setVisibility(View.INVISIBLE);
        ivDiceFace.setImageResource(diceFaces[0]);


    }

    private void hold() {
        playerOverallScore += playerTurnScore;
        playerTurnScore = 0;
        tvPlayerOverallScore.setText("Your overall score :" + playerOverallScore);
        computerTurn();

    }


    private void resetPlayerTurnScore() {
        playerTurnScore = 0;
    }

    private void computerTurn() {
        Toast.makeText(this, "Computer's turn", Toast.LENGTH_SHORT).show();
        disableButtons();
        resetPlayerTurnScore();
        final Random random = new Random();
        while (true) {
            int rollNumber = getDiceFaceNumber();
            Toast.makeText(this, "Rolled:" + rollNumber, Toast.LENGTH_SHORT).show();
            if (rollNumber != 1) {
                computerTurnScore += rollNumber;
                boolean hold = random.nextBoolean();
                if (hold) {
                    computerOverallscore += computerTurnScore;
                    tvComputerScore.setText("Computer overall score is :" + computerOverallscore);

                    break;
                }

            } else {
                computerTurnScore = 0;
                tvComputerScore.setText("Computer overall score is :" + computerOverallscore);
                break;

            }

        }
        btRoLL.setEnabled(true);
        btHold.setEnabled(true);
    }

    private void disableButtons() {
        btRoLL.setEnabled(false);
        btHold.setEnabled(false);

    }

}
