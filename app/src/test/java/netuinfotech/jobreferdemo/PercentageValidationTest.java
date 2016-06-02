package netuinfotech.jobreferdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import netuinfotech.jobreferdemo.model.Controller;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class PercentageValidationTest {

    private String per;
    private boolean expected;

    public PercentageValidationTest(String per, boolean expected) {
        this.per = per;
        this.expected = expected;
    }

    @Parameterized.Parameters(name = "{index}: isValid({0}={1})")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                        {"70.77", true},
                        {"30", true},
                        {"88..8", false},
                }
        );
    }

    @Test
    public void test_validPercentage() {
        assertEquals(expected, Controller.isValidPercentage(per));
    }

}