import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Config{

    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site";

    public static RequestSpecification getSpecification() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URL)
                .build().log().all();
    }
}
