import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class CourierApi {

   static public Response createCourier(String login, String password, String name) {

        Courier data = new Courier(login, password, name);
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(data)
                .when()
                .post(URLs.CREATE_COURIER);
    }

    static public Response loginCourier(String login, String password) {

        Courier courier = new Courier();
            courier.setLogin(login)
                    .setPassword(password);

        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(URLs.LOGIN_COURIER);
    }

    public static Response loginCourier(Courier courier){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(URLs.LOGIN_COURIER);
    }


    static public Integer getCourierId(Response loginCourier) {
        return loginCourier
                .then().extract().path("id");
    }

    static public void deleteCourier(int courierId) {
        given()
                .header("Content-type", "application/json")
                .delete(URLs.CREATE_COURIER + "/" + courierId);
    }

    static public Response createCourierSuccessfully() {
        return createCourier(CourierConstantsData.LOGIN, CourierConstantsData.PASSWORD, CourierConstantsData.NAME);
    }

    static public Response loginCourierSuccessfully() {
        return loginCourier(CourierConstantsData.LOGIN, CourierConstantsData.PASSWORD);
    }
}
