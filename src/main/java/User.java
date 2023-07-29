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

    static Date currentDate = new Date();
    static long timeMilli = currentDate.getTime();

    public static User getRandomUser() {
        return new User("auto_test_user" + timeMilli + "@nitest.test", RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5));
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

   public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
