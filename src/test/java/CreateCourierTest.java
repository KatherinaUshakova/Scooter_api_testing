import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.*;

public class CreateCourierTest extends ParentTest{

    @Before
    public void setUp() {
        RestAssured.baseURI = URLs.COURIER_URL;
    }

    @After
    public void deleteCourier() {
        Response response = loginCourier(FilePaths.COURIER_DATA);

        if (response.getStatusCode() == ResponseCodes.OK_CODE) {
            deleteCourier(this.getCourierId(response));
        }
    }

    protected Integer getCourierId(Response loginCourier) {
        return loginCourier
                .then().extract().path("id");
    }

    protected void deleteCourier(Integer courierId) {
        given()
                .header("Content-type", "application/json")
                .delete(URLs.CREATE_COURIER + "/" + courierId);
    }

    //курьера можно создать
    //запрос возвращает правильный код ответа
    @Test
    public void createNewCourierTest() {
        this.createCourier(FilePaths.COURIER_DATA)
                .then()
                .statusCode(ResponseCodes.CREATED_SUCCESS_CODE);
    }

    //чтобы создать курьера, нужно передать в ручку все обязательные поля//
    @Test
    public void createInvalidCourierTest() {
        this.createCourier(FilePaths.COURIER_LACK_OF_PASSWORD_DATA)
                .then()
                .assertThat()
                .body("message", equalTo(ErrorMessages.ERROR_MESSAGE_REGISTRATION_LACK_OF_DATA));
    }

    //нельзя создать двух одинаковых курьеров
    //если создать пользователя с логином, который уже есть, возвращается ошибка
    @Test
    public void createSimilarCouriers() {
        this.createCourier(FilePaths.COURIER_DATA);
        this.createCourier(FilePaths.COURIER_DATA)
                .then()
                .assertThat()
                .body("message", equalTo(ErrorMessages.ERROR_MESSAGE_ALREADY_EXIST))
                .and()
                .statusCode(ResponseCodes.CONFLICT_ERROR_CODE);
    }

    //успешный запрос возвращает ok: true //
    @Test
    public void createCourierResponseTest() {
        this.createCourier(FilePaths.COURIER_DATA)
                .then()
                .assertThat()
                .body("ok", equalTo(true));
    }

    //если одного из полей нет, запрос возвращает ошибку//
    @Test
    public void createInvalidCourierErrorTest(){
        this.createCourier(FilePaths.COURIER_LACK_OF_PASSWORD_DATA)
                .then()
                .statusCode(ResponseCodes.LACK_OF_DATA_ERROR_CODE);
    }
}
