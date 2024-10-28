package com.nidhi.quizapp.model;

import lombok.Data;

@Data
public class QuestionWrapper {

    private Integer id;
    private  String questionTitle;
    private String option_1;
    private String option_2;
    private String option_3;
    private String option_4;

    public QuestionWrapper(Integer id, String questionTitle, String option_1, String option_2, String option_3, String option_4) {
        this.id = id;
        this.questionTitle = questionTitle;
        this.option_1 = option_1;
        this.option_2 = option_2;
        this.option_3 = option_3;
        this.option_4 = option_4;
    }
}
