import org.apache.commons.lang3.RandomStringUtils;

import java.util.Date;

public class User {

    private String email;
    private String password;
    private String name;


    public User(String email, String password, String name) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User() {
    }

//    public static User getRandomUser() {
//        return new User().setRandomName().setRandomEmail().setRandomPassword();
//    }

    static Date currentDate = new Date();
    static long timeMilli = currentDate.getTime();

    public static User getRandomUser() {
        return new User("auto_test_user" + timeMilli + "@nitest.test", RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5));
    }

//    public static User getName() {
//        return new User().setRandomName();
//    }

    public String getName() {
        return name;
    }

//    public static User getEmail() {
//        return new User().setRandomEmail();
//    }

    public String getEmail() {
        return email;
    }

//    public static User getPassword() {
//        return new User().setRandomPassword();
//    }

    public String getPassword() {
        return password;
    }

//    public User setRandomName() {
//        this.name = "AT" + RandomStringUtils.randomAlphabetic(5);
//        return this;
//    }

    public void setName(String name) {
        this.name = name;
    }

//    public  User setRandomEmail() {
//        this.email = "auto_test_user" + timeMilli + "@nitest.test";
//        return this;
//    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public User setRandomPassword() {
//        this.password = RandomStringUtils.randomAlphabetic(5);
//        return this;
//    }

    public void setPassword(String password) {
        this.password = password;
    }
}
