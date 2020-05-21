package online_toeic_test_springboot.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestInfor {
    private Integer timeInSecond;
    private Integer totalQuestions;
}
