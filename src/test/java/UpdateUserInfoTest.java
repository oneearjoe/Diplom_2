import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class UpdateUserInfoTest {

    private UserAPI userMethods;
    private ValidatableResponse response;
    private User user;
    private String accessToken;

    @Before
    public void setUp(){
        userMethods = new UserAPI();
        user = User.getRandomUser();
        accessToken = userMethods.registerUser(user).extract().path("accessToken");
    }

    @Test
    @DisplayName("Изменение информации пользователя")
    public void checkItIsAbleToEditUserInfo(){

        user.setName("AAAA");
        user.setEmail("edit_" + user.getEmail());
        response = userMethods.updateUserInfo(accessToken, user);
        response.assertThat().statusCode(200);
        response.assertThat().body("success", is(true));
        response.assertThat().body("user.email", equalTo(user.getEmail()));
        response.assertThat().body("user.name", equalTo(user.getName()));
    }

    @Test
    @DisplayName("Изменение информации пользователя без авторизации")
    public void checkItIsNotAbleToEditUserInfoWithoutAccessToken(){
        user.setName("AAAA");
        user.setEmail("edit_" + user.getEmail());
        response = userMethods.updateUserInfo("", user);
        response.assertThat().statusCode(401);
        response.assertThat().body("success", is(false));
        response.assertThat().body("message", equalTo("You should be authorised"));
    }

    @After
    public void tearDown(){
        userMethods.deleteUser(accessToken);
    }

}
