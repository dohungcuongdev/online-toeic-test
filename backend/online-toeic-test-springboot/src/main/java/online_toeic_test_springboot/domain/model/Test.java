package online_toeic_test_springboot.domain.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@RequiredArgsConstructor
public class Test {
    private Integer id;
    private String testName;
    private String audioLink;
    private Map<Integer, Part> parts;
    private List<Character> examineeSelectedOptions;
}
