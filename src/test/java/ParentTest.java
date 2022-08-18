import io.restassured.response.Response;

import java.io.File;

import static io.restassured.RestAssured.given;

public class ParentTest {

    public Response createCourier(String pathToJson) {
        File json = new File(pathToJson);

        return given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(URLs.CREATE_COURIER);
    }

    public Response loginCourier(String pathToJson) {
        File json = new File(pathToJson);
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(URLs.LOGIN_COURIER);
    }
}
