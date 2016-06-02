package netuinfotech.jobreferdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import netuinfotech.jobreferdemo.model.Controller;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class AddressValidationTest {

    private String add;
    private boolean expected;

    public AddressValidationTest(String add, boolean expected) {
        this.add = add;
        this.expected = expected;
    }

    @Parameterized.Parameters(name = "{index}: isValid({0}={1})")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                        {"adjasdasdkjas", false},
                        {"dsdasdasdasdasdsa", true},
                }
        );
    }

    @Test
    public void test_validAddress() {
        assertEquals(expected, Controller.isValidAddress(add));
    }

}