package netuinfotech.jobreferdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import netuinfotech.jobreferdemo.model.Controller;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CompanyValidationTest {

    private String comapany;
    private boolean expected;

    public CompanyValidationTest(String company, boolean expected) {
        this.comapany = company;
        this.expected = expected;
    }

    @Parameterized.Parameters(name = "{index}: isValid({0}={1})")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                        {"", false},
                        {"gateway info", true},
                }
        );
    }

    @Test
    public void test_validCompany() {
        assertEquals(expected, Controller.isNullCompany(comapany));
    }

}