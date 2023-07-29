import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class GetUserListOrderTest {
    private List<String> generalIngredients;
    private List<String> orderIngredients = new ArrayList<>();
    private Order order;
    private User user;
    private UserAPI userMethods = new UserAPI();
    private OrderAPI orderMethods = new OrderAPI();
    private ValidatableResponse response;
    private String accessToken;

    @Before
    public void setUp(){
        user = User.getRandomUser();
        response = userMethods.registerUser(user);
        accessToken = response.extract().path("accessToken");
        generalIngredients = orderMethods.getIngredients().extract().path("data._id");
        for (int i = 0; i <3 ; i++) {
            orderIngredients.add(generalIngredients.get(i));
        }
        order = new Order(orderIngredients);
        response = orderMethods.create(order, accessToken);
    }

    @Test
    @DisplayName("Получение заказа пользователя")
    public void getOrderAuthUserTest() {
        response = orderMethods.getOrders(accessToken);
        response.assertThat().statusCode(200);
        response.assertThat().body("success", is(true));
    }

    @Test
    @DisplayName("Получение заказа пользователя без авторизации")
    public void getUserOrderWithoutAccessToken() {
        response = orderMethods.getOrders("");
        response.assertThat().statusCode(401);
        response.assertThat().body("success", is(false));
        response.assertThat().body("message", equalTo("You should be authorised"));
    }

    @After
    public void tearDown(){
        userMethods.deleteUser(accessToken);
    }
}
