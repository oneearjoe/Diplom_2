import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserAPI {

    private static final String REGISTER = "/api/auth/register";

    private static final String AUTH_USER = "api/auth/user";
    private static final String LOGIN = "api/auth/login";


    @Step("Регистрация нового пользователя")
    public ValidatableResponse registerUser(User user){
        return given()
                .spec(Config.getSpecification())
                .and()
                .body(user)
                .when()
                .post(REGISTER)
                .then()
                .log().all();
    }

    @Step("Удаление пользователя")
    public void deleteUser(String accessToken){
        if (accessToken == null) {
            return;
        }
        given()
                .header("Authorization",accessToken)
                .spec(Config.getSpecification())
                .when()
                .delete(AUTH_USER)
                .then()
                .statusCode(202)
                .log().all();

    }

    @Step("Авторизация пользователя")
    public ValidatableResponse login (User user){
        return given()
                .spec(Config.getSpecification())
                .and()
                .body(user)
                .when()
                .post(LOGIN)
                .then()
                .log().all();
    }

    @Step("Изменение пользователя")
    public ValidatableResponse updateUserInfo(String token, User user) {
        return given()
                .headers("Authorization", token)
                .spec(Config.getSpecification())
                .body(user)
                .when()
                .patch(AUTH_USER)
                .then()
                .log().all();
    }
}
