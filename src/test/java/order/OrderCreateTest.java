package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.stellarburgers.dto.OrderCreateDTO;
import praktikum.stellarburgers.dto.UserCreateDTO;
import praktikum.stellarburgers.order.OrderClientSteps;
import praktikum.stellarburgers.order.OrderCreateSteps;
import praktikum.stellarburgers.user.UserGenerator;
import praktikum.stellarburgers.user.UserRequest;
import praktikum.stellarburgers.user.UserResponse;

import java.util.List;

public class OrderCreateTest {
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

    @DisplayName("Checking the creation of an order by a registered user")
    @Test
    public void verifyOrderCreationByRegisteredUser() {
        List<String> ingredients = List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f");
        OrderCreateDTO orderCreateDTO = new OrderCreateDTO(ingredients);
        ValidatableResponse response = orderClientSteps.createOrders(accessToken, orderCreateDTO);
        orderCreateSteps.createOrderSuccess(response);
    }

    @DisplayName("Checking the creation of an order by an unregistered user")
    @Test
    public void verifyOrderCreationByUnregisteredUser() {
        List<String> ingredients = List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f");
        OrderCreateDTO orderCreateDTO = new OrderCreateDTO(ingredients);
        ValidatableResponse response = orderClientSteps.createOrders("1", orderCreateDTO);
        orderCreateSteps.createOrderSuccess(response);
    }

    @DisplayName("Checking for an error when trying to create an order without ingredients")
    @Test
    public void verifyOrderCreationErrorWithoutIngredients() {
        OrderCreateDTO orderCreateDTO = new OrderCreateDTO();
        ValidatableResponse response = orderClientSteps.createOrders(accessToken, orderCreateDTO);
        orderCreateSteps.createOrderNoIngredients(response);
    }

    @DisplayName("Checking for an error when trying to create an order with invalid ingredient hashes")
    @Test
    public void verifyOrderCreationErrorWithInvalidIngredientHashes() {
        List<String> ingredients = List.of("61c0c5a71d1f820dff01bdaaa6d", "61c0c5a71d1f82dfsdf001bdaaa6f");
        OrderCreateDTO orderCreateDTO = new OrderCreateDTO(ingredients);
        ValidatableResponse response = orderClientSteps.createOrders(accessToken, orderCreateDTO);
        orderCreateSteps.createOrderInvalidHashIngredients(response);
    }
}
