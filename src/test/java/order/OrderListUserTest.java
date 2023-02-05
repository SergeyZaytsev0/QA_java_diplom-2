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
import praktikum.stellarburgers.user.UserResponce;


public class OrderListUserTest {
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

    @DisplayName("Провека получения списка заказов зарегистрированного пользователя")
    @Test
    public void testGetOrderListForRegisteredUser() {
        ValidatableResponse response = orderClientSteps.getOrdersListUser(accessToken);
        orderCreateSteps.getOrderListInLoginUser(response);
    }

    @DisplayName("Провека невозможности получения списка заказов незарегистрированным пользователем")
    @Test
    public void testUnableToGetOrderListForNonRegisteredUser() {
        ValidatableResponse response = orderClientSteps.getOrdersListUser("1");
        orderCreateSteps.getOrderListNoLoginUser(response);
    }
}
