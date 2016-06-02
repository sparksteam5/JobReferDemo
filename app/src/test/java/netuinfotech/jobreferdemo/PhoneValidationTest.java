package netuinfotech.jobreferdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import netuinfotech.jobreferdemo.model.Controller;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class PhoneValidationTest {

    private String phone;
    private boolean expected;

    public PhoneValidationTest(String phone, boolean expected) {
        this.phone = phone;
        this.expected = expected;
    }

    @Parameterized.Parameters(name = "{index}: isValid({0}={1})")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                        {"+919033026998", true},
                        {"333334", false},
                        {"84604343333", false},
                }
        );
    }

    @Test
    public void test_validPhone() {
        assertEquals(expected, Controller.isValidMobile(phone));
    }

}