package com.theappnerds.shubham.myapplication;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.v7.widget.GridLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    //check game is active or not
    boolean isGameActive = true;
    //player 1 = yellow==> activePlayer = 0
    //player 2 = red ==> activePlayer = 1
    int activePlayer = 0;
    //gamestate = 2 means unplayed for that spot
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    //All possible winning positions
    int[][] winningPosition = {{2, 4, 6}, {0, 4, 8}, {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void myTimeToDropIn(View view) {
        ImageView counterImage = (ImageView) view;
        int tappedCounter = Integer.parseInt(counterImage.getTag().toString());

//        Check is Game still active and gameState still tappable
        if (gameState[tappedCounter] == 2 && isGameActive) {
            gameState[tappedCounter] = activePlayer;
            counterImage.setTranslationY(-1000f);

//        set Images at tapped position
            if (activePlayer == 0) {
                counterImage.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counterImage.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            counterImage.animate().translationYBy(1000f).rotation(360).setDuration(500);

            for (int[] winningPos : winningPosition) {

                if (gameState[winningPos[0]] == gameState[winningPos[1]]
                        && gameState[winningPos[1]] == gameState[winningPos[2]]
                        && gameState[winningPos[0]] != 2
                        && gameState[winningPos[1]] != 2
                        && gameState[winningPos[2]] != 2) {

                    isGameActive = false;

                    if (gameState[winningPos[0]] == 0) {
                        showInfoDialog("Winner is YELLOW");
                        break;
                    } else if (gameState[winningPos[0]] == 1) {
                        showInfoDialog("Winner is RED");
                    }

                } else {
                    //for game DRAW state
                    boolean isGameOver = true;
                    for (int counterState : gameState) {
                        if (counterState == 2) {
                            isGameOver = false;
                        }
                    }

                    if (isGameOver) {

                        showInfoDialog("It's a DRAW");
                        break;
                    }

                }
            }

        }
    }


    public void showInfoDialog(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.create();
        builder.setTitle(title);
        builder.setMessage("Do play one more round..");
        builder.setPositiveButton("Yeah..! Sure", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                isGameActive = true;
                dialogInterface.dismiss();

                //Reset the gamestate Array
                activePlayer = 0;
                for (int i = 0; i < gameState.length; i++) {
                    gameState[i] = 2;
                }

                //Reset images on gameBoard
                GridLayout gridLayout = findViewById(R.id.gridLayout);
                for (int i = 0; i < gridLayout.getChildCount(); i++) {
                    ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
                }
            }
        });
        builder.show();


    }
}
