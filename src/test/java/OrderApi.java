import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class OrderApi {

    static public Response createOrder(OrderSerial data) {

        return given()
                .header("Content-type", "application/json")
                .and()
                .body(data)
                .when()
                .post(URLs.CREATE_ORDER);
    }

    static public Integer getOrderTrack(Response order) {
       return order.then().extract().path("track");
    }
}
