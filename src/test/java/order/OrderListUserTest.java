package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.stellarburgers.dto.UserCreateDTO;
import praktikum.stellarburgers.order.OrderClientSteps;
import praktikum.stellarburgers.order.OrderCreateSteps;
import praktikum.stellarburgers.user.UserGenerator;
import praktikum.stellarburgers.user.UserRequest;
import praktikum.stellarburgers.user.UserResponse;


public class OrderListUserTest {
    private final UserGenerator userGenerator = new UserGenerator();
    private final UserRequest userRequest = new UserRequest();
    private final UserResponse userResponse = new UserResponse();
    private final OrderClientSteps orderClientSteps = new OrderClientSteps();
    private final UserCreateDTO uniqueUser = userGenerator.randomDataCourier();
    private final OrderCreateSteps orderCreateSteps = new OrderCreateSteps();
    private String accessToken;

    @Before
    public void setUpUser() {
        ValidatableResponse create = userRequest.create(uniqueUser);
        accessToken = userResponse.assertCreationSusses(create);
    }

    @After
    public void cleanUpUser() {
        if (accessToken != null) {
            ValidatableResponse response = userRequest.delete(accessToken);
            userResponse.assertDeleteSusses(response);
        }
    }

    @DisplayName("Checking the receipt of a list of orders of a registered user")
    @Test
    public void testGetOrderListForRegisteredUser() {
        ValidatableResponse response = orderClientSteps.getOrdersListUser(accessToken);
        orderCreateSteps.getOrderListInLoginUser(response);
    }

    @DisplayName("Checking the impossibility of obtaining a list of orders by an unregistered user")
    @Test
    public void testUnableToGetOrderListForNonRegisteredUser() {
        ValidatableResponse response = orderClientSteps.getOrdersListUser("1");
        orderCreateSteps.getOrderListNoLoginUser(response);
    }
}
