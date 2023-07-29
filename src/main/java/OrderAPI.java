import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class OrderAPI {

    public final static String API_ORDER = "/api/orders";
    private static final String API_INGREDIENTS = "/api/ingredients";

    @Step("Создать заказ")
    public ValidatableResponse create(Order order, String token) {
        return given()
                .headers("Authorization", token)
                .spec(Config.getSpecification())
                .body(order)
                .when()
                .post(API_ORDER)
                .then()
                .log().all();
    }

    @Step("Получение списка ингредиентов")
    public static ValidatableResponse getIngredients() {
        return given()
                .spec(Config.getSpecification())
                .log().uri()
                .when()
                .get(API_INGREDIENTS)
                .then().log().all();
    }

    @Step("Получение списка заказов пользователя")
    public ValidatableResponse getOrders(String accessUserToken) {
        return given()
                .header("Authorization", accessUserToken)
                .spec(Config.getSpecification())
                .when()
                .get(API_ORDER)
                .then()
                .log().body();
    }
}
