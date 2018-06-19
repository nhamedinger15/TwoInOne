package hamedinger.nico.whowantstobeamillionaire;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Nico on 17.05.2018.
 */

public class Question {
    private final String text;
    private final String falseOne;
    private final String falseTwo;
    private final String falseThree;
    private final String correctAnswer;
    private int level;
    private String saveCorrect;

    private ArrayList<String> randAnswers = new ArrayList<>();




    public Question(String text, String falseOne, String falseTwo, String falseThree, String correctAnswer, int level) {
        this.text = text;
        this.falseOne = falseOne;
        this.falseTwo = falseTwo;
        this.falseThree = falseThree;
        this.correctAnswer = correctAnswer;
        this.level = level;

        saveCorrect = correctAnswer;

        randAnswers.add(falseOne);
        randAnswers.add(falseTwo);
        randAnswers.add(falseThree);
        randAnswers.add(correctAnswer);

        Collections.shuffle(randAnswers);



    }

    public String getText() {
        return text;
    }

    public String getFalseOne() {
        return falseOne;
    }

    public String getFalseTwo() {
        return falseTwo;
    }

    public String getFalseThree() {
        return falseThree;
    }

    public String getsaveCorrectAnswer() {
        return saveCorrect;
    }

    public int getLevel() {
        return level;
    }

    public ArrayList<String> getRandAnswers()
    {
        return randAnswers;
    }
}
