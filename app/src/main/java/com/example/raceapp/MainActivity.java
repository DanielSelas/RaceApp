package com.example.raceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.raceapp.Logic.GameManager;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private ShapeableImageView gameMat[][];
    private ImageButton[] movementButtomGlob; //buttons for right and left
    private ShapeableImageView main_IMG_background;
    private ShapeableImageView[] main_IMG_hearts; //life in the game
    private ShapeableImageView[][] main_IMG_obstacles; //falling tests
    private ShapeableImageView[] main_IMG_student; //player images

    private static final int DELAY=1000;
    private static final long vibareTime=1000;
    final Handler handler = new Handler();

    private Random rm;
    GameManager gameManager;
    private int[] testsIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        gameManager = new GameManager();
        playerMove();
        timer();
        testsIndex=new int[gameManager.getROWS()];

    }
    private void findView() {
         main_IMG_background = findViewById(R.id.board_IMG_background);

        //hearts
        main_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)};

        //the falling tests
        main_IMG_obstacles = new ShapeableImageView[][]{
                        {findViewById(R.id.main_IMG_test13),
                        findViewById(R.id.main_IMG_test14),
                        findViewById(R.id.main_IMG_test15)},

                        { findViewById(R.id.main_IMG_test10),
                        findViewById(R.id.main_IMG_test11),
                        findViewById(R.id.main_IMG_test12),},

                        {findViewById(R.id.main_IMG_test7),
                        findViewById(R.id.main_IMG_test8),
                        findViewById(R.id.main_IMG_test9),},

                        {findViewById(R.id.main_IMG_test4),
                        findViewById(R.id.main_IMG_test5),
                        findViewById(R.id.main_IMG_test6),},

                        {findViewById(R.id.main_IMG_test1),
                        findViewById(R.id.main_IMG_test2),
                        findViewById(R.id.main_IMG_test3),}

                        };

        //buttons
        movementButtomGlob = new ImageButton[]{
                findViewById(R.id.main_FBTN_left),
                findViewById(R.id.main_FBTN_right)};

        //student image
        main_IMG_student = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_student1),
                findViewById(R.id.main_IMG_student2),
                findViewById(R.id.main_IMG_student3)};
    }

    Runnable rna = new Runnable() {
        public void run() {
            handler.postDelayed(this, DELAY);
            refreshUI();
        }
    };

// refreshes
    private void refreshUI(){
    gameManager.refresh();
    if(gameManager.hit){
        heartsStatus();
        vibare();
        Toast.makeText(this,"You got hit!", Toast.LENGTH_SHORT).show();
        gameManager.setHit(false);
    }
        refreshObsticale();
}
    private void refreshObsticale() {
        for (int i = 0; i < gameManager.getROWS(); i++) {
            for (int j = 0; j < gameManager.getCOLS(); j++) {
                if (gameManager.activeGame(i, j))
                    main_IMG_obstacles[i][j].setVisibility(View.VISIBLE);
                else main_IMG_obstacles[i][j].setVisibility(View.INVISIBLE);
            }
        }

    }
    private void heartsStatus() {
        boolean[] hearts=gameManager.getStudentLife();
        for (int i = 0; i < hearts.length; i++) {
            if (hearts[i])
                main_IMG_hearts[i].setVisibility(View.VISIBLE);
            else
                main_IMG_hearts[i].setVisibility(View.INVISIBLE);
        }
    }
    public void playerMove() {
        //left move
        movementButtomGlob[0].setOnClickListener(view0-> {
            if (main_IMG_student[1].isShown()) {
                main_IMG_student[0].setVisibility(View.VISIBLE);
                main_IMG_student[1].setVisibility(View.INVISIBLE);
                main_IMG_student[2].setVisibility(View.INVISIBLE);
                gameManager.setPlayerPlace(0);
            } else if (main_IMG_student[2].isShown()) {
                main_IMG_student[0].setVisibility(View.INVISIBLE);
                main_IMG_student[1].setVisibility(View.VISIBLE);
                main_IMG_student[2].setVisibility(View.INVISIBLE);
                gameManager.setPlayerPlace(1);
            }
        });
        // right move
        movementButtomGlob[1].setOnClickListener(view1 -> {
            if (main_IMG_student[0].isShown()) {
                main_IMG_student[0].setVisibility(View.INVISIBLE);
                main_IMG_student[1].setVisibility(View.VISIBLE);
                main_IMG_student[2].setVisibility(View.INVISIBLE);
                gameManager.setPlayerPlace(1);
            } else if (main_IMG_student[1].isShown()) {
                main_IMG_student[0].setVisibility(View.INVISIBLE);
                main_IMG_student[1].setVisibility(View.INVISIBLE);
                main_IMG_student[2].setVisibility(View.VISIBLE);
                gameManager.setPlayerPlace(2);
            }
        });
    }


    private void timer() {
        handler.postDelayed(rna,DELAY);
    }
    private void timerStop() {
        handler.removeCallbacks(rna);
    }
    private void vibare() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(VibrationEffect.createOneShot(vibareTime, VibrationEffect.DEFAULT_AMPLITUDE));
    }




}





