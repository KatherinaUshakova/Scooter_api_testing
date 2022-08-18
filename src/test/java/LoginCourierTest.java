import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class LoginCourierTest extends ParentTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = URLs.COURIER_URL;
        this.createCourier(FilePaths.COURIER_DATA);
    }

    //курьер может авторизоваться; успешный запрос возвращает id.
    @Test
    public void logInCourier() {
        this.loginCourier(FilePaths.COURIER_DATA).then().extract().path("id");
    }

    //для авторизации нужно передать все обязательные поля;
// если какого-то поля нет, запрос возвращает ошибку;
    @Test
    public void logInErrorIfLackOfData() {
        this.loginCourier(FilePaths.COURIER_LACK_OF_LOGIN_DATA)
                .then()
                .assertThat()
                .statusCode(ResponseCodes.LACK_OF_DATA_ERROR_CODE)
                .and()
                .body("message", equalTo(ErrorMessages.ERROR_MESSAGE_LOGIN_LACK_OF_DATA));

        this.loginCourier(FilePaths.COURIER_LACK_OF_PASSWORD_DATA)
                .then()
                .statusCode(ResponseCodes.TIME_OUT_ERROR_CODE);
    }

    //система вернёт ошибку, если неправильно указать логин или пароль;
    @Test
    public void logInErrorInvalidData() {
        this.loginCourier(FilePaths.COURIER_WRONG_PASSWORD_DATA)
                .then()
                .assertThat()
                .body("message", equalTo(ErrorMessages.ERROR_MESSAGE_PROFILE_NOT_FOUND))
                .and()
                .statusCode(ResponseCodes.PROFILE_NOT_EXIST_ERROR_CODE);
    }

    //если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
    @Test
    public void logInNotExistCourierError() {
        this.loginCourier(FilePaths.COURIER_WRONG_LOGIN_DATA)
                .then()
                .assertThat()
                .body("message", equalTo(ErrorMessages.ERROR_MESSAGE_PROFILE_NOT_FOUND))
                .and()
                .statusCode(ResponseCodes.PROFILE_NOT_EXIST_ERROR_CODE);
    }
}