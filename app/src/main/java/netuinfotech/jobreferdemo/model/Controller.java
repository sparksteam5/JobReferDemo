package netuinfotech.jobreferdemo.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {

    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidPassword(String pass) {
        String PASS_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";

        Pattern pattern = Pattern.compile(PASS_PATTERN);
        Matcher matcher = pattern.matcher(pass);
        return matcher.matches();
    }

    public static boolean isValidPasswordConfirmpassword(String pass, String conpass) {

        if (pass.equals(conpass))
            return true;
        else
            return false;
    }

}
