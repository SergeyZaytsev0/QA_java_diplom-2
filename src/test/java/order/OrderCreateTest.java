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
import praktikum.stellarburgers.user.UserResponce;

import java.util.List;

public class OrderCreateTest {
    private final UserGenerator userGenerator = new UserGenerator();
    private final UserRequest userRequest = new UserRequest();
    private final UserResponce userResponce = new UserResponce();
    private final OrderClientSteps orderClientSteps = new OrderClientSteps();
    UserCreateDTO uniqueUser = userGenerator.randomDataCourier();
    OrderCreateSteps orderCreateSteps = new OrderCreateSteps();
    private String accessToken;


    @Before
    public void setUpUser() {
        ValidatableResponse create = userRequest.create(uniqueUser);
        accessToken = userResponce.assertCreationSusses(create);
    }

    @After
    public void cleanUpUser() {
        if (accessToken != null) {
            ValidatableResponse response = userRequest.delete(accessToken);
            userResponce.assertDeleteSusses(response);
        }
    }

    @DisplayName("Проверка создания заказа зарегестрированным пользователем")
    @Test
    public void verifyOrderCreationByRegisteredUser() {
        List<String> ingredients = List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f");
        OrderCreateDTO orderCreateDTO = new OrderCreateDTO(ingredients);
        ValidatableResponse response = orderClientSteps.createOrders(accessToken, orderCreateDTO);
        orderCreateSteps.createOrderSuccess(response);
    }

    @DisplayName("Проверка создания заказа незарегестрированным пользователем")
    @Test
    public void verifyOrderCreationByUnregisteredUser() {
        List<String> ingredients = List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f");
        OrderCreateDTO orderCreateDTO = new OrderCreateDTO(ingredients);
        ValidatableResponse response = orderClientSteps.createOrders("1", orderCreateDTO);
        orderCreateSteps.createOrderSuccess(response);
    }

    @DisplayName("Проверка получения ошибки при попытке создания заказа без ингредиентов")
    @Test
    public void verifyOrderCreationErrorWithoutIngredients() {
        OrderCreateDTO orderCreateDTO = new OrderCreateDTO();
        ValidatableResponse response = orderClientSteps.createOrders(accessToken, orderCreateDTO);
        orderCreateSteps.createOrderNoIngredients(response);
    }

    @DisplayName("Проверка получения ошибки при попытке создания заказа c неверным хешами ингредиентов")
    @Test
    public void verifyOrderCreationErrorWithInvalidIngredientHashes() {
        List<String> ingredients = List.of("61c0c5a71d1f820dff01bdaaa6d", "61c0c5a71d1f82dfsdf001bdaaa6f");
        OrderCreateDTO orderCreateDTO = new OrderCreateDTO(ingredients);
        ValidatableResponse response = orderClientSteps.createOrders(accessToken, orderCreateDTO);
        orderCreateSteps.createOrderInvalidHashIngredients(response);
    }

}
