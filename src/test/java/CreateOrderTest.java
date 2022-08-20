import io.restassured.RestAssured;
import org.junit.Assert;
import org.junit.Before;
import io.restassured.response.Response;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static io.restassured.RestAssured.given;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private final String[] color;

    public CreateOrderTest(String[] color){
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object getColor() {
        return new Object[][]{
                {new String[]{OrderConstants.COLOR_GREY, OrderConstants.COLOR_BLACK}},
                {new String[]{OrderConstants.COLOR_GREY}},
                {new String[]{OrderConstants.COLOR_BLACK}},
                {new String[]{}}
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = URLs.COURIER_URL;
    }

    @Test
    public void createOrderTest() {

        OrderSerial order = new OrderSerial(
                OrderConstants.FIRST_NAME,
                OrderConstants.LAST_NAME,
                OrderConstants.ADDRESS,
                OrderConstants.STATION,
                OrderConstants.PHONE,
                OrderConstants.RENT_TIME,
                OrderConstants.DELIVERY_DATE,
                OrderConstants.COMMENT,
                this.color
        );

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post(URLs.CREATE_ORDER);

        response.then().statusCode(SC_CREATED);
        Assert.assertNotNull(response.then().extract().path("track"));
    }
}
