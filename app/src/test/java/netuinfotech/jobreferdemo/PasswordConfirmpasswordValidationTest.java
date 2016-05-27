package netuinfotech.jobreferdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import netuinfotech.jobreferdemo.model.Controller;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class PasswordConfirmpasswordValidationTest {

    private String pass,conpass;
    private boolean expected;

    public PasswordConfirmpasswordValidationTest(String pass,String conpass, boolean expected) {
        this.pass = pass;
        this.conpass=conpass;
        this.expected = expected;
    }

    @Parameterized.Parameters(name = "{index}: isValid({0}={1})")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                        {"1234", "1234", true},
                        {"abc@123", "ab123", false},
                }
        );
    }

    @Test
    public void test_validPasswordConfirmpassword() {
        assertEquals(expected, Controller.isValidPasswordConfirmpassword(pass, conpass));
    }

}