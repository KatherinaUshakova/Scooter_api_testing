import io.restassured.RestAssured;
import org.junit.*;
import io.restassured.response.Response;
import org.junit.Before;
import static org.apache.http.HttpStatus.*;
import static io.restassured.RestAssured.given;

public class OrderListTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = URLs.COURIER_URL;
    }

@Test
    public void getOrderList(){
       Response response = given().get(URLs.GET_ORDER_LIST);

       Assert.assertNotNull(response.getBody());
       response.then().statusCode(SC_OK);
    }
}
