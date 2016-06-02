package netuinfotech.jobreferdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import netuinfotech.jobreferdemo.model.Controller;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ExperienceValidationTest {

    private String exp;
    private boolean expected;

    public ExperienceValidationTest(String exp, boolean expected) {
        this.exp = exp;
        this.expected = expected;
    }

    @Parameterized.Parameters(name = "{index}: isValid({0}={1})")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                        {"", false},
                        {"10", true},
                }
        );
    }

    @Test
    public void test_validExperience() {
        assertEquals(expected, Controller.isNullExp(exp));
    }

}