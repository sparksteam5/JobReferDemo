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

    public static boolean isValidMobile(String number) {
        String PASS_PATTERN = "^\\+[0-9]{10,13}$";

        Pattern pattern = Pattern.compile(PASS_PATTERN);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    public static boolean isValidPercentage(String per){
        String PASS_PATTERN = "^[1-9]\\d*(\\.\\d+)?$";

        Pattern pattern = Pattern.compile(PASS_PATTERN);
        Matcher matcher = pattern.matcher(per);
        return matcher.matches();


//        if(per.length() <= 5)
//            return true;
//        else
//            return false;
    }

    public static boolean isValidAddress(String add){

        if(add.length() <= 15)
            return false;
        else
            return true;
    }

    public static boolean isNullName(String name){
        if(name.equals(""))
            return false;
        else
            return true;
    }

    public static boolean isNullSkill(String skill){
        if(skill.equals(""))
            return false;
        else
            return true;
    }

    public static boolean isNullCompany(String comapany){
        if(comapany.equals(""))
            return false;
        else
            return true;
    }

    public static boolean isNullExp(String exep){
        if(exep.equals(""))
            return false;
        else
            return true;
    }

}
