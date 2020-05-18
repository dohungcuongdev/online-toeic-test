package online_toeic_test_springboot.validation;

import online_toeic_test_springboot.domain.model.Part;
import online_toeic_test_springboot.domain.model.Question;
import online_toeic_test_springboot.domain.model.QuestionGroup;
import online_toeic_test_springboot.domain.model.Test;
import online_toeic_test_springboot.exception.BadRequestBodyException;
import org.springframework.stereotype.Component;

@Component
public class RequestBodyValidation {

    public void validateTest(Test test, HttpMethod httpMethod) {
        System.out.println(test);
        StringBuilder badRequestBodyErrorMessage = new StringBuilder();
        if(httpMethod == HttpMethod.PUT) {
            if(test.getId() == null || test.getId() <= 0) {
                badRequestBodyErrorMessage.append("id is required. ");
            }
        }
        if(test.getTestName() == null || test.getTestName().equals("")) {
            badRequestBodyErrorMessage.append("testName is required. ");
        }
        if(test.getAudioLink() == null || test.getAudioLink().equals("")) {
            badRequestBodyErrorMessage.append("audioLink is required. ");
        }
        if(!badRequestBodyErrorMessage.toString().equals("")) {
            throw new BadRequestBodyException(badRequestBodyErrorMessage.toString());
        }
    }

    public void validatePart(Part part, HttpMethod httpMethod) {
        System.out.println(part);
        StringBuilder badRequestBodyErrorMessage = new StringBuilder();
        if(httpMethod == HttpMethod.PUT) {
            if(part.getId() == null || part.getId() <= 0) {
                badRequestBodyErrorMessage.append("id is required. ");
            }
        }
        if(part.getTestId() == null || part.getTestId() <= 0) {
            badRequestBodyErrorMessage.append("testId is required. ");
        }
        if(part.getType() == null || part.getType() <= 0) {
            badRequestBodyErrorMessage.append("type is required. ");
        }
        if(part.getTittle() == null || part.getTittle().equals("")) {
            badRequestBodyErrorMessage.append("tittle is required. ");
        }
        if(part.getPartNum() == null || part.getPartNum() <= 0) {
            badRequestBodyErrorMessage.append("partNum is required. ");
        }
        if(part.getDirection() == null || part.getDirection().equals("")) {
            badRequestBodyErrorMessage.append("direction is required. ");
        }
        if(!badRequestBodyErrorMessage.toString().equals("")) {
            throw new BadRequestBodyException(badRequestBodyErrorMessage.toString());
        }
    }

    public void validateQuestionGroup(QuestionGroup questionGroup, HttpMethod httpMethod) {
        System.out.println(questionGroup);
        StringBuilder badRequestBodyErrorMessage = new StringBuilder();
        if(httpMethod == HttpMethod.PUT) {
            if(questionGroup.getId() == null || questionGroup.getId() <= 0) {
                badRequestBodyErrorMessage.append("id is required. ");
            }
        }
        if(questionGroup.getPartId() == null || questionGroup.getPartId() <= 0) {
            badRequestBodyErrorMessage.append("partId is required. ");
        }
        if(questionGroup.getTittle() == null || questionGroup.getTittle().equals("")) {
            badRequestBodyErrorMessage.append("tittle is required. ");
        }
        if(!badRequestBodyErrorMessage.toString().equals("")) {
            throw new BadRequestBodyException(badRequestBodyErrorMessage.toString());
        }
    }

    public void validateQuestion(Question question, HttpMethod httpMethod) {
        System.out.println(question);
        StringBuilder badRequestBodyErrorMessage = new StringBuilder();
        if(httpMethod == HttpMethod.PUT) {
            if(question.getId() == null || question.getId() <= 0) {
                badRequestBodyErrorMessage.append("id is required. ");
            }
        }
        if(!badRequestBodyErrorMessage.toString().equals("")) {
            throw new BadRequestBodyException(badRequestBodyErrorMessage.toString());
        }
    }
}
