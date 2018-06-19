package hamedinger.nico.whowantstobeamillionaire;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

public class MainActivity extends Activity {

    // Level 1 = 100-500€, Level 2 = 1.000-16.000€, Level 3 = 32.000-500.000€, Level 4 = 1.000.000€
    // HAMED -> Standcomputer, Nico -> Laptop

    List<Question> questions;
    TreeMap<Integer,String> money;
    TreeMap<Integer,Integer> level;
    TreeMap<Double,String> audienceScores;
    int levelNow;

    Question lastSaved;


    Button questionButton;
    Button answerOne;
    Button answerTwo;
    Button answerThree;
    Button answerFour;



    boolean secondchance;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);

        levelNow = 1;

        answerOne = findViewById(R.id.answerBtnOne);
        answerTwo = findViewById(R.id.answerBtnTwo);
        answerThree = findViewById(R.id.answerBtnThree);
        answerFour = findViewById(R.id.answerBtnFour);
        questionButton = findViewById(R.id.QuestionBtn);
        secondchance = false;
        FillMoneyAndLevel();
        FillQuestions();
        FillaudienceScores();

    }

    private void FillaudienceScores()
    {
        audienceScores.put(1.1,"90%");
        audienceScores.put(2.1,"70%");
        audienceScores.put(3.1,"60%");
        audienceScores.put(4.1,"20%");

        audienceScores.put(1.2,"3%");
        audienceScores.put(2.2,"10%");
        audienceScores.put(3.2,"20%");
        audienceScores.put(4.2,"50%");

        audienceScores.put(1.3,"2%");
        audienceScores.put(2.3,"15%");
        audienceScores.put(3.3,"7%");
        audienceScores.put(4.3,"13%");

        audienceScores.put(1.4,"5%");
        audienceScores.put(2.4,"5%");
        audienceScores.put(3.4,"13%");
        audienceScores.put(4.4,"17%");
    }

    public void FillMoneyAndLevel()
    {
        money.put(0,"0€");
        money.put(1,"100€");
        money.put(2,"200€");
        money.put(3,"300€");
        money.put(4,"500€");
        money.put(5,"1.000€");
        money.put(6,"2.000€");
        money.put(7,"4.000€");
        money.put(8,"8.000€");
        money.put(9,"16.000€");
        money.put(10,"32.000€");
        money.put(11,"64.000€");
        money.put(12,"125.000€");
        money.put(13,"250.000€");
        money.put(14,"500.000€");
        money.put(15,"1.000.000€");

        level.put(1,1);
        level.put(2,1);
        level.put(3,1);
        level.put(4,1);
        level.put(5,1);
        level.put(6,2);
        level.put(7,2);
        level.put(8,2);
        level.put(9,2);
        level.put(10,2);
        level.put(11,3);
        level.put(12,3);
        level.put(13,3);
        level.put(14,3);
        level.put(15,4);


    }

    private void FillQuestions()
    {

        try {
            BufferedReader File = new BufferedReader(new FileReader("fragen.csv"));
            String zeile;

            try{
                zeile = File.readLine();
                while (zeile != null)
                {
                    String[] parts = zeile.split(";");

                    Question q = new Question(parts[0],parts[1],parts[2],parts[3],parts[4],Integer.parseInt(parts[5]));
                    questions.add(q);

                    zeile = File.readLine();
                }
                File.close();
            }
            catch (IOException e) {
                System.out.println("Fehler beim einlesen der Datei.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.shuffle(questions);

        findRightQuestion();
    }


    public void checkAnswer (View view)
    {
        Button b = findViewById(view.getId());

                    if (lastSaved.getsaveCorrectAnswer().equals(b.getText()))
                    {
                        if(levelNow == 15)
                        {
                            AlertDialog.Builder alert = new AlertDialog.Builder(this);
                            alert.setMessage("Gratulation! Du hast soeben die Millionen gewonnen!");
                            alert.setNeutralButton("YAHOOO!",null);
                            alert.show();

                            Intent intent = new Intent(this, HighscoresActivity.class);
                            intent.putExtra("Score",money.get(levelNow));
                            startActivity(intent);
                        }
                        else
                        {
                            nextLevel();
                        }
                    }
                    else if (!secondchance)
                    {
                        AlertDialog.Builder alert = new AlertDialog.Builder(this);
                        alert.setMessage("Antwort Falsch!");
                        alert.setNeutralButton("Schade!",null);
                        alert.show();

                        Intent intent = new Intent(this, HighscoresActivity.class);
                        intent.putExtra("Score",money.get(levelNow));
                        startActivity(intent);
                    }
                    else
                    {
                        b.setVisibility(View.INVISIBLE);
                        Toast.makeText(this,"Falsche Antwort! Versuchen Sie es noch einmal.",Toast.LENGTH_SHORT).show();
                        secondchance = false;
                    }
    }

    private void nextLevel()
    {
        levelNow++;
        checkVisibility();
        findRightQuestion();
    }

    private void checkVisibility()
    {
        if(answerOne.getVisibility()== View.INVISIBLE)
        {
            answerOne.setVisibility(View.VISIBLE);
        }
        else if (answerTwo.getVisibility()== View.INVISIBLE)
        {
            answerTwo.setVisibility(View.VISIBLE);
        }
        else if (answerThree.getVisibility()== View.INVISIBLE)
        {
            answerThree.setVisibility(View.VISIBLE);
        }
        else if (answerFour.getVisibility()== View.INVISIBLE)
        {
            answerFour.setVisibility(View.VISIBLE);
        }
    }

    public void findRightQuestion()
    {
        boolean foundRight = false;
        int i = 0;

        while (!foundRight)
        {
            if (questions.get(i).getLevel() == level.get(levelNow))
            {
                questionButton.setText(questions.get(i).getText());
                answerOne.setText(questions.get(i).getRandAnswers().get(0));
                answerTwo.setText(questions.get(i).getRandAnswers().get(1));
                answerThree.setText(questions.get(i).getRandAnswers().get(2));
                answerFour.setText(questions.get(i).getRandAnswers().get(3));
                lastSaved = questions.get(i);
                foundRight = true;
            }

            i++;
        }
    }

    public void fiftyActivated(View view)
    {
        //---------------falseOne------------

        if (lastSaved.getFalseOne().equals(answerOne.getText()))
        {
            answerOne.setVisibility(View.INVISIBLE);
        }
        else if (lastSaved.getFalseOne().equals(answerTwo.getText()))
        {
            answerTwo.setVisibility(View.INVISIBLE);
        }
        else if (lastSaved.getFalseOne().equals(answerThree.getText()))
        {
            answerThree.setVisibility(View.INVISIBLE);
        }
        else if (lastSaved.getFalseOne().equals(answerFour.getText()))
        {
            answerFour.setVisibility(View.INVISIBLE);
        }

        //---------------falseTwo------------

        if (lastSaved.getFalseTwo().equals(answerOne.getText()))
        {
            answerOne.setVisibility(View.INVISIBLE);
        }
        else if (lastSaved.getFalseTwo().equals(answerTwo.getText()))
        {
            answerTwo.setVisibility(View.INVISIBLE);
        }
        else if (lastSaved.getFalseTwo().equals(answerThree.getText()))
        {
            answerThree.setVisibility(View.INVISIBLE);
        }
        else if (lastSaved.getFalseTwo().equals(answerFour.getText()))
        {
            answerFour.setVisibility(View.INVISIBLE);
        }
    }

    public void SecondChanceActivated(View view)
    {
        secondchance = true;
        Toast.makeText(this,"Zweite Chance aktiviert! Sie können nun eine falsche Antwort wählen ohne dabei zu verlieren.",Toast.LENGTH_LONG).show();
    }

    public void audienceActivated(View view)
    {
        String answerA = "";
        String answerB = "";
        String answerC = "";
        String answerD = "";


        if (lastSaved.getsaveCorrectAnswer().equals(answerOne.getText()))
        {
            answerA = audienceScores.get(Double.parseDouble(level.get(levelNow).toString())+0.1);
            answerB = audienceScores.get(Double.parseDouble(level.get(levelNow).toString())+0.2);
            answerC = audienceScores.get(Double.parseDouble(level.get(levelNow).toString())+0.3);
            answerD = audienceScores.get(Double.parseDouble(level.get(levelNow).toString())+0.4);
        }
        else if (lastSaved.getsaveCorrectAnswer().equals(answerTwo.getText()))
        {
            answerB = audienceScores.get(Double.parseDouble(level.get(levelNow).toString())+0.1);
            answerA = audienceScores.get(Double.parseDouble(level.get(levelNow).toString())+0.2);
            answerC = audienceScores.get(Double.parseDouble(level.get(levelNow).toString())+0.3);
            answerD = audienceScores.get(Double.parseDouble(level.get(levelNow).toString())+0.4);
        }
        else if (lastSaved.getsaveCorrectAnswer().equals(answerThree.getText()))
        {
            answerC = audienceScores.get(Double.parseDouble(level.get(levelNow).toString())+0.1);
            answerB = audienceScores.get(Double.parseDouble(level.get(levelNow).toString())+0.2);
            answerA = audienceScores.get(Double.parseDouble(level.get(levelNow).toString())+0.3);
            answerD = audienceScores.get(Double.parseDouble(level.get(levelNow).toString())+0.4);
        }
        else if (lastSaved.getsaveCorrectAnswer().equals(answerFour.getText()))
        {
            answerD = audienceScores.get(Double.parseDouble(level.get(levelNow).toString())+0.1);
            answerB = audienceScores.get(Double.parseDouble(level.get(levelNow).toString())+0.2);
            answerC = audienceScores.get(Double.parseDouble(level.get(levelNow).toString())+0.3);
            answerA = audienceScores.get(Double.parseDouble(level.get(levelNow).toString())+0.4);
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("A: " + answerA + " B: " + answerB + " C: " + answerC + " D: " + answerD);
        alert.setNeutralButton("OK",null);
        alert.show();
    }
}