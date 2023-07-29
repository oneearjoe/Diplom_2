import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


public class CreateOrderTest {

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
    }

    @Test
    @DisplayName("Создание заказа с авторизацией")
    public void createOrderWithAuthorization(){
        generalIngredients = orderMethods.getIngredients().extract().path("data._id");
        for (int i = 0; i <3 ; i++) {
            orderIngredients.add(generalIngredients.get(i));
        }
        order = new Order(orderIngredients);
        response = orderMethods.create(order, accessToken);
        response.assertThat().statusCode(200);
        response.assertThat().body("success", is(true));
    }


    @Test
    @DisplayName("Создание заказа без токена авторизации")
    public void createOrderWithoutAccessToken(){
        generalIngredients = orderMethods.getIngredients().extract().path("data._id");
        for (int i = 0; i <3 ; i++) {
            orderIngredients.add(generalIngredients.get(i));
        }
        order = new Order(orderIngredients);
        response = orderMethods.create(order, "");
        response.assertThat().statusCode(200);
        response.assertThat().body("success", is(true));
    }


    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void createOrderWithoutIngredients(){
        order = new Order(orderIngredients);
        response = orderMethods.create(order, accessToken);
        response.assertThat().statusCode(400);
        response.assertThat().body("success", is(false));
        response.assertThat().body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа с неверными ингредиентами")
    public void createOrderWithWrongIngredients(){
        orderIngredients.add("1234");
        orderIngredients.add("5678");
        order = new Order(orderIngredients);
        response = orderMethods.create(order, accessToken);
        response.assertThat().statusCode(500);
    }

    @After
    public void tearDown(){
        userMethods.deleteUser(accessToken);
    }
}
