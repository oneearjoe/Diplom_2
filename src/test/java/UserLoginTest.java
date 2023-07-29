import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.*;

public class UserLoginTest {
    private UserAPI userMethods = new UserAPI();
    private ValidatableResponse response;
    private String accessToken;
    private User user;

    @Before
    public void setUp(){
        user = User.getRandomUser();
        response = userMethods.registerUser(user);
        accessToken = response.extract().path("accessToken");
    }

    @Test
    @DisplayName("Проверка авторизации пользователя")
    public void loginUserSuccessTest() {
        response.assertThat().statusCode(200);
        response.assertThat().body("success", is(true));
        response.assertThat().body("refreshToken", notNullValue());
        response.assertThat().body("accessToken", notNullValue());
        response.assertThat().body("user", notNullValue());
    }


    @Test
    @DisplayName("Авторизация без email невозможна")
    public void checkErrorMessageIfEmailIsEmptyDuringLogin(){
        user.setEmail(null);
        response = userMethods.login(user);
        response.assertThat().statusCode(401);
        response.assertThat().body("success", is(false));
        response.assertThat().body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Авторизация без password невозможна")
    public void checkErrorMessageIfPasswordIsEmptyDuringLogin(){
        user.setPassword(null);
        response = userMethods.login(user);
        response.assertThat().statusCode(401);
        response.assertThat().body("success", is(false));
        response.assertThat().body("message", equalTo("email or password are incorrect"));
    }

    @After
    public void tearDown(){
        userMethods.deleteUser(accessToken);
    }
}
