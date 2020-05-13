package online_toeic_test_springboot.domain.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
@RequiredArgsConstructor
public class Question {
    private int id;
    private int partId;
    private int groupId;
    private int index;
    private String imageLink;
    private String questionTittle;
    private char correctAnswer;
    private int correctAnswerId;
    private int questionNo;
    private Map<Character, Answer> answers;
}