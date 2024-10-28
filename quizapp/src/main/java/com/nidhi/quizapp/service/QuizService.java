package com.nidhi.quizapp.service;

import com.nidhi.quizapp.dao.QuestionDao;
import com.nidhi.quizapp.dao.QuizDao;
import com.nidhi.quizapp.model.Question;
import com.nidhi.quizapp.model.QuestionWrapper;
import com.nidhi.quizapp.model.Quiz;
import com.nidhi.quizapp.model.Response;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class QuizService {

    @Autowired
    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;


    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questionFromDB =quiz.get().getQuestions();
        List<QuestionWrapper> questionForUser =new ArrayList<>();

        for(Question q: questionFromDB){
            QuestionWrapper qw =new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption_1(), q.getOption_2(), q.getOption_3(), q.getOption_4());
            questionForUser.add(qw);
        }

        return  new ResponseEntity<>(questionForUser, HttpStatus.OK);

    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz = quizDao.findById(id).get();
        List<Question> questions = quiz.getQuestions();

        int right=0;
        int i=0;
        for(Response response: responses){
            if(response.getResponse().equals(questions.get(i).getRight_answer())){
                right++;
            }
            i++;
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
