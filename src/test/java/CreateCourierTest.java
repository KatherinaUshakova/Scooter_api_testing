import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = URLs.COURIER_URL;
    }

    @After
    public void deleteCourier() {
        Response response = CourierApi.loginCourierSuccessfully();

        if (response.getStatusCode() == SC_OK) {
            int id = CourierApi.getCourierId(response);
            CourierApi.deleteCourier(id);
        }
    }

    //курьера можно создать
    //запрос возвращает правильный код ответа
    @Test
    public void createNewCourierTest() {
        CourierApi.createCourierSuccessfully()
                .then()
                .statusCode(SC_CREATED);
    }

    //чтобы создать курьера, нужно передать в ручку все обязательные поля//
    @Test
    public void createInvalidCourierTest() {
        CourierApi.createCourier(CourierConstantsData.LOGIN, "", CourierConstantsData.NAME)
                .then()
                .assertThat()
                .body("message", equalTo(ErrorMessages.ERROR_MESSAGE_REGISTRATION_LACK_OF_DATA));
    }

    //нельзя создать двух одинаковых курьеров
    //если создать пользователя с логином, который уже есть, возвращается ошибка
    @Test
    public void createSimilarCouriers() {
        CourierApi.createCourierSuccessfully();
        CourierApi.createCourierSuccessfully()
                .then()
                .assertThat()
                .body("message", equalTo(ErrorMessages.ERROR_MESSAGE_ALREADY_EXIST))
                .and()
                .statusCode(SC_CONFLICT);
    }

    //успешный запрос возвращает ok: true //
    @Test
    public void createCourierResponseTest() {
        CourierApi.createCourierSuccessfully()
                .then()
                .assertThat()
                .body("ok", equalTo(true));
    }

    //если одного из полей нет, запрос возвращает ошибку//
    @Test
    public void createInvalidCourierErrorTest(){
        CourierApi.createCourier(CourierConstantsData.LOGIN, "", CourierConstantsData.NAME)
                .then()
                .statusCode(SC_BAD_REQUEST);
    }
}
