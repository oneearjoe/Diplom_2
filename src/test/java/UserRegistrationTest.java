import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.*;


public class UserRegistrationTest {

    private UserAPI userMethods = new UserAPI();
    private ValidatableResponse response;
    private String accessToken;
    private User user;

    @Test
    @DisplayName("Создание нового пользователя")
    public void checkItIsAbleToRegisterNewUser(){
        user = User.getRandomUser();
        response = userMethods.registerUser(user);
        accessToken = response.extract().path("accessToken");
        response.assertThat().statusCode(200);
        response.assertThat().body("success", is(true));
        response.assertThat().body("accessToken", notNullValue());
        response.assertThat().body("user.email", equalTo(user.getEmail()));
        response.assertThat().body("user.name", equalTo(user.getName()));
        response.assertThat().body("refreshToken", notNullValue());
    }

    @Test
    @DisplayName("Создание существующего пользователя")
    public void checkErrorMessageWhenUserAlreadyExists(){
        response = userMethods.registerUser(user.getRandomUser());
        accessToken = response.extract().path("accessToken");
        response = userMethods.registerUser(user.getRandomUser());
        response.assertThat().statusCode(403).log().all();
        response.assertThat().body("success", is(false));
        response.assertThat().body("message", equalTo("User already exists"));
    }
    @Test
    @DisplayName("Создание пользователя без имени невозможно")
    public void checkErrorMessageWhenNameIsNotSent(){
        user = User.getRandomUser();
        user.setName(null);
        response = userMethods.registerUser(user);
        response.assertThat().statusCode(403).log().all();
        response.assertThat().body("success", is(false));
        response.assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание пользователя без пароля невозможно")
    public void checkErrorMessageWhenEmailIsNotSent(){
        user = User.getRandomUser();
        user.setPassword(null);
        response = userMethods.registerUser(user);
        response.assertThat().statusCode(403).log().all();
        response.assertThat().body("success", is(false));
        response.assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание пользователя без пароля невозможно")
    public void checkErrorMessageWhenPasswordIsNotSent(){
        user = User.getRandomUser();
        user.setEmail(null);
        response = userMethods.registerUser(user);
        response.assertThat().statusCode(403).log().all();
        response.assertThat().body("success", is(false));
        response.assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @After
    public void tearDown(){
        userMethods.deleteUser(accessToken);
    }
}
