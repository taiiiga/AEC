package ru.kstu.aec.controller;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.kstu.aec.models.Question;
import ru.kstu.aec.models.Statistic;
import ru.kstu.aec.models.Test;
import ru.kstu.aec.models.User;
import ru.kstu.aec.services.QuestionService;
import ru.kstu.aec.services.StatisticService;
import ru.kstu.aec.services.TestService;
import ru.kstu.aec.services.UserService;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:8080"})
@RestController
public class StatisticsAPIController {

    private final StatisticService statisticService;
    private final UserService userService;
    private final TestService testService;
    private final QuestionService questionService;

    @Autowired
    public StatisticsAPIController(StatisticService statisticService, UserService userService, TestService testService, QuestionService questionService) {
        this.statisticService = statisticService;
        this.userService = userService;
        this.testService = testService;
        this.questionService = questionService;
    }

    private List<Question> helper(Statistic statistic) throws Exception {
        Test test = testService.getTest(statistic.getTest().getId());
        List<Question> questionList = test.getQuestions();
        List<Question> questions = new ArrayList<>();
        for(int i = 0; i < questionList.size(); i++) {
            questions.add(questionService.getQuestion(questionList.get(i).getId()));
        }
        return questions;
    }

    @GetMapping("/statistics/pol")
    public int statisticsPol() throws Exception {
        int poly;
        int max = 0;
        Statistic statistic = statisticService.getStatistic();
        poly = statistic.getPol();
        List<Question> questions = helper(statistic);
        for(Question q : questions) {
            if(q.getCategory().getName().equals("POL")) {
                max = max + q.getCategory().getRating();
            }
        }
        double pol;
        pol = (double) poly / max;
        System.out.println("pol=" + (int) Math.ceil(pol * 100));
        return (int) Math.ceil(pol * 100);
    }

    @GetMapping("/statistics/chl")
    public int statisticsChl() throws Exception {
        int poly;
        int max = 0;
        Statistic statistic = statisticService.getStatistic();
        poly = statistic.getChl();
        List<Question> questions = helper(statistic);
        for(Question q : questions) {
            if(q.getCategory().getName().equals("CHL")) {
                max = max + q.getCategory().getRating();
            }
        }
        double chl;
        chl = (double) poly / max;
        System.out.println("chl=" + (int) Math.ceil(chl * 100));
        return (int) Math.ceil(chl * 100);
    }

    @GetMapping("/statistics/upr")
    public int statisticsUpr() throws Exception {
        int poly;
        int max = 0;
        Statistic statistic = statisticService.getStatistic();
        poly = statistic.getUpr();
        List<Question> questions = helper(statistic);
        for(Question q : questions) {
            if(q.getCategory().getName().equals("UPR")) {
                max = max + q.getCategory().getRating();
            }
        }
        double upr;
        upr = (double) poly / max;
        System.out.println("upr=" + (int) Math.ceil(upr * 100));
        return (int) Math.ceil(upr * 100);
    }

    @SneakyThrows
    @GetMapping("/statistics/help")
    public int help() {
        Thread.sleep(1000);
        return 1;
    }

    @GetMapping("/statistics/user")
    public String statisticsUser() throws Exception {
        System.out.println("или нет");
        return userService.loadUserByUsername(statisticService.getStatistic().getUser().getEmail()).getFirstname();
    }
}
