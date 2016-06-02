package netuinfotech.jobreferdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import netuinfotech.jobreferdemo.model.Controller;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class SkillsValidationTest {

    private String skill;
    private boolean expected;

    public SkillsValidationTest(String skill, boolean expected) {
        this.skill = skill;
        this.expected = expected;
    }

    @Parameterized.Parameters(name = "{index}: isValid({0}={1})")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                        {"", false},
                        {"android,java", true},
                }
        );
    }

    @Test
    public void test_validSkills() {
        assertEquals(expected, Controller.isNullSkill(skill));
    }

}