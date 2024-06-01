package apiMethodsImplementation;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import pojos.GetUsersResponsePojo;

import java.util.ArrayList;
import java.util.List;

import static constantPaths.ApiEndpoints.GET_ALL_USERS;
import static httpMethods.HttpUtility.getHttpMethod;
import static tests.BaseTest.verifyResponseSchema;
import static utils.CommonUtils.parseDouble;

public class GetUsersApiMethods {
    public static Response getAllUsersRequest() {
        return getHttpMethod(GET_ALL_USERS);
    }

    public static GetUsersResponsePojo[] deserializeUserResponsePojo(Response response) {
        return response.getBody().as(GetUsersResponsePojo[].class);
    }

    public GetUsersResponsePojo[] getUsersResponse() {
        Response responseUsers = getAllUsersRequest();
        Assert.assertEquals(responseUsers.getStatusCode(), 200, "Expected Status Code is 200 but found " + responseUsers.getStatusCode());
        verifyResponseSchema(responseUsers, "getUsersResponseSchema.json");
        GetUsersResponsePojo[] usersList = deserializeUserResponsePojo(responseUsers);
        return usersList;
    }

    public List<Integer> getAllFanCodeUsers(GetUsersResponsePojo[] users, int minLat, int maxLat, int minLng, int maxLng) {
        List<Integer> userIdList = new ArrayList<>();
        for (GetUsersResponsePojo user : users) {
            double lat = parseDouble(user.getAddress().getGeo().getLat());
            double lng = parseDouble(user.getAddress().getGeo().getLng());

            if (lat >= minLat && lat <= maxLat && lng >= minLng && lng <= maxLng) {
                userIdList.add(user.getId());
            }
        }
        return userIdList;
    }
}