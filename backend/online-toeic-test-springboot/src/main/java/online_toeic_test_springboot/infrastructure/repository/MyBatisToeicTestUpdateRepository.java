package online_toeic_test_springboot.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import online_toeic_test_springboot.domain.model.*;
import online_toeic_test_springboot.domain.repository.ToeicTestCreateRepository;
import online_toeic_test_springboot.domain.repository.ToeicTestUpdateRepository;
import online_toeic_test_springboot.exception.EntityNotFoundException;
import online_toeic_test_springboot.infrastructure.mapper.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
@Transactional
@RequiredArgsConstructor
public class MyBatisToeicTestUpdateRepository implements ToeicTestUpdateRepository {

  private final TestCRUDMapper testCRUDMapper;

  private final PartCRUDMapper partCRUDMapper;

  private final QuestionGroupCRUDMapper questionGroupCRUDMapper;

  private final QuestionCRUDMapper questionCRUDMapper;

  private final AnswerCRUDMapper answerCRUDMapper;

  private final AchievementCRUDMapper achievementCRUDMapper;

  private final ExamineeAnswerCRUDMapper examineeAnswerCRUDMapper;

  @Override
  public void updateTest(Test test) {
    Optional<Test> optionalOldTest = testCRUDMapper.queryTestById(test.getId());
    if(!optionalOldTest.isPresent()) {
      throw new EntityNotFoundException("test not found");
    }
    Test oldTest = optionalOldTest.get();
    if(test.getTestName() == null) {
      test.setTestName(oldTest.getTestName());
    }
    if(test.getAudioLink() == null) {
      test.setAudioLink(oldTest.getAudioLink());
    }
    testCRUDMapper.updateTest(test);
  }

  @Override
  public void updatePart(Part part) {
    Optional<Part> optionalOldPart = partCRUDMapper.queryPartById(part.getId());
    if(!optionalOldPart.isPresent()) {
      throw new EntityNotFoundException("part not found");
    }
    Part oldPart = optionalOldPart.get();
    if(part.getTestId() == null) {
      part.setTestId(oldPart.getTestId());
    }
    if(part.getType() == null) {
      part.setType(oldPart.getType());
    }
    if(part.getPartNum() == null) {
      part.setPartNum(oldPart.getPartNum());
    }
    if(part.getTittle() == null) {
      part.setTittle(oldPart.getTittle());
    }
    if(part.getDirection() == null) {
      part.setDirection(oldPart.getDirection());
    }
  }
  
  @Override
  public void updateQuestionGroup(QuestionGroup questionGroup) {
    Optional<QuestionGroup> optionalOldQuestionGroup = questionGroupCRUDMapper.queryQuestionGroupById(questionGroup.getId());
    if(!optionalOldQuestionGroup.isPresent()) {
      throw new EntityNotFoundException("question group not found");
    }
    QuestionGroup oldQuestionGroup = optionalOldQuestionGroup.get();
    if(questionGroup.getPartId() == null) {
      questionGroup.setPartId(oldQuestionGroup.getPartId());
    }
    if(questionGroup.getIndex() == null) {
      questionGroup.setIndex(oldQuestionGroup.getIndex());
    }
    if(questionGroup.getTittle() == null) {
      questionGroup.setTittle(oldQuestionGroup.getTittle());
    }
    if(questionGroup.getImageLink() == null) {
      questionGroup.setImageLink(oldQuestionGroup.getImageLink());
    }
    if(questionGroup.getParagraph() == null) {
      questionGroup.setParagraph(oldQuestionGroup.getParagraph());
    }
  }

  @Override
  public void updateQuestion(Question question) {
    Optional<Question> optionalOldQuestion = questionCRUDMapper.queryQuestionById(question.getId());
    if(!optionalOldQuestion.isPresent()) {
      throw new EntityNotFoundException("question not found");
    }
    Question oldQuestion = optionalOldQuestion.get();
    if(oldQuestion.getPartId() == null) {
      question.setPartId(oldQuestion.getPartId());
    }
    if(oldQuestion.getGroupId() == null) {
      question.setGroupId(oldQuestion.getGroupId());
    }
    if(question.getIndex() == null) {
      question.setIndex(oldQuestion.getIndex());
    }
    if(question.getQuestionTittle() == null) {
      question.setQuestionTittle(oldQuestion.getQuestionTittle());
    }
    if(question.getImageLink() == null) {
      question.setImageLink(oldQuestion.getImageLink());
    }
    if(question.getCorrectAnswer() == null) {
      question.setCorrectAnswer(oldQuestion.getCorrectAnswer());
    }
    if(question.getCorrectAnswerId() == null) {
      question.setCorrectAnswerId(oldQuestion.getCorrectAnswerId());
    }
    List<Answer> oldAnswers = answerCRUDMapper.queryAnswersByQuestionId(question.getId());
    Map<Character, Answer> answers = question.getAnswers();
    for (Map.Entry<Character, Answer> answerEntry : answers.entrySet()) {
      for(Answer oldAnswer: oldAnswers) {
        Answer answer = answerEntry.getValue();
        if (answer.getId().equals(oldAnswer.getId())) {
          if(answer.getQuestionId() == null) {
            answer.setQuestionId(oldAnswer.getQuestionId());
          }
          if(answer.getContent() == null) {
            answer.setContent(oldAnswer.getContent());
          }
        }
      }
    }
  }
}
