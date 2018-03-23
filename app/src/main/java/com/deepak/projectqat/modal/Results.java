package com.deepak.projectqat.modal;

/**
 * Created by Deepak Kumar on 15-03-2018.
 * Results from Database
 */

public class Results {

    String q_no;
    String question;
    String correct_ans;
    String your_ans;
    String counter_correct;

    public Results() {
    }

    public String getQ_no() {
        return q_no;
    }

    public String getCounter_correct() {
        return counter_correct;
    }

    public void setCounter_correct(String counter_correct) {
        this.counter_correct = counter_correct;
    }

    public void setQ_no(String q_no) {
        this.q_no = q_no;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrect_ans() {
        return correct_ans;
    }

    public void setCorrect_ans(String correct_ans) {
        this.correct_ans = correct_ans;
    }

    public String getYour_ans() {
        return your_ans;
    }

    public void setYour_ans(String your_ans) {
        this.your_ans = your_ans;
    }
}
