package httpMethods;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class HttpUtility {

    /**
     * @param path
     * @return
     * @description This method is used to Get the HTTP method
     */
    public static Response getHttpMethod(String path) {
        return RestAssured.given().
                when().
                headers("Content-Type", ContentType.JSON).
                filter(new AllureRestAssured()).
                get(path);
    }

    /**
     * @param path
     * @param body
     * @return
     * @Description This method is used to Post the HTTP method
     */
    public static Response postHttpMethod(String path, String body) {
        return RestAssured.given().
                when().
                headers("Content-Type", ContentType.JSON).
                filter(new AllureRestAssured()).
                body(body).
                post(path);
    }

    /**
     * @param path
     * @param body
     * @return
     * @Description This method is used to Put the HTTP method
     */
    public static Response putHttpMethod(String path, String body) {
        return RestAssured.given().
                when().
                headers("Content-Type", ContentType.JSON).
                filter(new AllureRestAssured()).
                body(body).
                put(path);
    }

    /**
     * @param path
     * @return
     * @Description This method is used to Delete the HTTP method
     */
    public static Response deleteHttpMethod(String path) {
        return RestAssured.given().
                when().
                headers("Content-Type", ContentType.JSON).
                filter(new AllureRestAssured()).
                delete(path);
    }
}
