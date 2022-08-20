import io.restassured.RestAssured;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class LoginCourierTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = URLs.COURIER_URL;
        CourierApi.createCourierSuccessfully();
    }

    @Test
    @Description("курьер может авторизоваться; успешный запрос возвращает id")
    public void logInCourier() {
        CourierApi.loginCourierSuccessfully().then().extract().path("id");
    }

    @Test
    @Description("для авторизации нужно передать все обязательные поля; " +
            "если какого-то поля нет, запрос возвращает ошибку")
    public void logInErrorWithMissingLogin() {
        Courier courier = new Courier();
        courier.setPassword(CourierConstantsData.PASSWORD);

        CourierApi.loginCourier(courier) //na login
                .then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo(ErrorMessages.ERROR_MESSAGE_LOGIN_LACK_OF_DATA));
    }

    @Test
    public void logInErrorWithMissingPassword() {
        Courier courier = new Courier();
        courier.setLogin(CourierConstantsData.LOGIN);

        CourierApi.loginCourier(courier) //na password
                .then()
                .statusCode(SC_GATEWAY_TIMEOUT);
    }

    @Test
    @Description("система вернёт ошибку, если неправильно указать логин или пароль")
    public void logInErrorInvalidData() {
        CourierApi.loginCourier(CourierConstantsData.LOGIN, CourierConstantsData.WRONG_PASSWORD)
                .then()
                .assertThat()
                .body("message", equalTo(ErrorMessages.ERROR_MESSAGE_PROFILE_NOT_FOUND))
                .and()
                .statusCode(SC_NOT_FOUND);
    }

    @Test
    @Description("если авторизоваться под несуществующим пользователем, запрос возвращает ошибку")
    public void logInNotExistCourierError() {
        CourierApi.loginCourier(CourierConstantsData.WRONG_LOGIN, CourierConstantsData.PASSWORD)
                .then()
                .assertThat()
                .body("message", equalTo(ErrorMessages.ERROR_MESSAGE_PROFILE_NOT_FOUND))
                .and()
                .statusCode(SC_NOT_FOUND);
    }
}
