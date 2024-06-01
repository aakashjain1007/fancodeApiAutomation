package tests;

import apiMethodsImplementation.GetTodosApiMethods;
import apiMethodsImplementation.GetUsersApiMethods;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pojos.GetUsersResponsePojo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static apiMethodsImplementation.GetTodosApiMethods.*;

public class TodosTest extends BaseTest {
    private List<Integer> fancodeUserIds = new ArrayList<>();

    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: To get Fancode Users where lat (-40 to 5) and lng (5 to 100)")
    @BeforeClass
    private void getAllFanCodeUsers() {
        GetUsersApiMethods getUsersApi = new GetUsersApiMethods();
        GetUsersResponsePojo[] users = getUsersApi.getUsersResponse();
        fancodeUserIds = getUsersApi.getAllFanCodeUsers(users, -40, 5, 5, 100);
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: To verify Fancode Users Completed Task is more than 50%")
    @Test(dataProvider = "userId")
    private void verifyFancodeUsersCompletedTaskPercentShouldBeGreaterThan50(int userId) {
        SoftAssert softAssert = new SoftAssert();
        GetTodosApiMethods getTodosApi = new GetTodosApiMethods();
        Response responseTodos = getAllTodoRequest(userId);
        softAssert.assertEquals(responseTodos.getStatusCode(), 200, "Expected Status Code was 200 but found " + responseTodos.getStatusCode());
        verifyResponseSchema(responseTodos, "getTodosResponseSchema.json");

        List<Integer> actualFancodeUserId = com.jayway.jsonpath.JsonPath.read(responseTodos.asString(), "$..userId");
        softAssert.assertTrue(getTodosApi.verifyUserIdListContainsOnlySameUsers(actualFancodeUserId, userId), "List of Users Id contains incorrect User ID: " + actualFancodeUserId);

        List<Boolean> completedTasks = com.jayway.jsonpath.JsonPath.read(responseTodos.asString(), "$..completed");
        softAssert.assertTrue(getTodosApi.verifyCompletedTaskStatus(completedTasks), "List of Completed Task contains invalid values: " + completedTasks);

        long completedCount = completedTasks.stream().filter(Boolean::booleanValue).count();
        double completionPercentage = (completedCount * 100.0) / completedTasks.size();

        softAssert.assertTrue(completionPercentage >= 50.0, "User completion percentage is less than 50% for UserId: " + userId + " | Actual Completed Percent " + completionPercentage);
        softAssert.assertAll();
    }

    @DataProvider(name = "userId")
    private Iterator<Integer> userIdProvider() {
        return fancodeUserIds.iterator();
    }
}