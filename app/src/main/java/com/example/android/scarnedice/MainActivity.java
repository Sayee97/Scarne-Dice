package com.example.android.scarnedice;

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
    private TextView tvTurnScore, tvPlayerOverallScore, tvComputerScore, tvStatus;
    private ImageView ivDiceFace;
    private Button btHold, btRoll, btReset;
    private int TurnScore, PlayerOverallScore, ComputerScore, Status, ComputerOverallScore;
    private int[] diceFace = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTurnScore = (TextView) (findViewById(R.id.tvTurnScore));
        tvComputerScore = (TextView) (findViewById(R.id.tvComputerScore));
        tvPlayerOverallScore = (TextView) (findViewById(R.id.tvPlayerOverallScore));
        btHold = (Button) (findViewById(R.id.btHold));
        btReset = (Button) (findViewById(R.id.btReset));
        btRoll = (Button) (findViewById(R.id.btRoll));
        ivDiceFace = (ImageView) (findViewById(R.id.ivDiceFace));
        tvStatus = (TextView) (findViewById(R.id.tvStatus));
        btRoll.setOnClickListener(this);
        btReset.setOnClickListener(this);
        btHold.setOnClickListener(this);
        btHold.setEnabled(false);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btHold:
                Hold();
                break;
            case R.id.btReset:
                Reset();
                break;
            case R.id.btRoll:
                Roll();
                break;

        }

    }

    private void Hold() {
        PlayerOverallScore += TurnScore;
        TurnScore = 0;
        tvPlayerOverallScore.setText("Your overall score is:" + PlayerOverallScore);
        computerTurn();
    }

    private void Reset() {
        TurnScore = 0;
        PlayerOverallScore = 0;
        ComputerScore = 0;
        ComputerOverallScore = 0;
        tvComputerScore.setText("Computer's overall score is:" + ComputerOverallScore);
        tvTurnScore.setText("Your turn score is:" + TurnScore);
        tvPlayerOverallScore.setText("You overall score is:" + PlayerOverallScore);
        ivDiceFace.setImageResource(diceFace[0]);
        tvTurnScore.setVisibility(View.INVISIBLE);
    }

    private void Roll() {
        btHold.setEnabled(true);

        tvTurnScore.setVisibility(View.VISIBLE);
        int rollNumber = getDiceFaceNumber();
        Toast.makeText(this, "Rolled:" + rollNumber, Toast.LENGTH_SHORT).show();

        if (rollNumber != 1) {
            TurnScore += rollNumber;
            tvTurnScore.setText("Your turn score:" + TurnScore);
            PlayerOverallScore += TurnScore;
        } else {
            tvPlayerOverallScore.setText("Your overall score:" + PlayerOverallScore);
            TurnScore = 0;
            tvTurnScore.setText("Your turn score:" + TurnScore);
            computerTurn();
        }
    }

    private void computerTurn() {
        Toast.makeText(this, "Computer's Turn", Toast.LENGTH_SHORT).show();
        disableButtons();
        resetTurnScore();
        Random random = new Random();
        while (true) {
            int rollNumber = getDiceFaceNumber();
            Toast.makeText(this, "Rolled" + rollNumber, Toast.LENGTH_SHORT).show();
            if (rollNumber != 1) {
                ComputerScore += rollNumber;
                boolean hold = random.nextBoolean();
                if (hold) {
                    ComputerOverallScore += ComputerScore;
                    break;

                }
            } else {
                ComputerScore = 0;
                break;
            }
        }
        tvComputerScore.setText(getString(R.string.comp_overall_score, ComputerOverallScore));
        playerturn();
    }


    private void playerturn() {
        resetTurnScore();
        Toast.makeText(this, "YOUR TURN", Toast.LENGTH_SHORT).show();
        btHold.setEnabled(false);
        btRoll.setEnabled(true);
    }

    private void resetTurnScore() {
        TurnScore = 0;
        btRoll.setEnabled(false);
    }

    private void disableButtons() {
        btHold.setEnabled(false);
        btRoll.setEnabled(false);
    }

    private int getDiceFaceNumber() {
        Random random = new Random();
        int i = random.nextInt(6);
        ivDiceFace.setImageResource(diceFace[i]);
        return i + 1;
    }
}

