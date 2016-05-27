package netuinfotech.jobreferdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import netuinfotech.jobreferdemo.model.Controller;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class PasswordValidationTest {

    private String pass;
    private boolean expected;

    public PasswordValidationTest(String pass, boolean expected) {
        this.pass = pass;
        this.expected = expected;
    }

    @Parameterized.Parameters(name = "{index}: isValid({0}={1})")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                        {"acb@123", false},
                        {"mY1A@", false},
                        {"Def@123", true},
                        {"mkyoNg12*", false},
                }
        );
    }

    @Test
    public void test_validDomains() {
        assertEquals(expected, Controller.isValidPassword(pass));
    }

}