package netuinfotech.jobreferdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import netuinfotech.jobreferdemo.model.Controller;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class EmailValidationTest {

    private String domain;
    private boolean expected;

    public EmailValidationTest(String domain, boolean expected) {
        this.domain = domain;
        this.expected = expected;
    }

    @Parameterized.Parameters(name = "{index}: isValid({0}={1})")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                        {"google@gmail.com", true},
                        {"dhruval.com", false},
                        {"-mkyong.com", false},
                        {"mkyong-.com", false},
                        {"3423kjk", false}
                }
        );
    }

    @Test
    public void test_validDomains() {
        assertEquals(expected, Controller.isValidEmail(domain));
    }

}