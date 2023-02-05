package praktikum.stellarburgers.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;

public class OrderCreateSteps {
    @Step("Cоздания заказа")
    public void createOrderSuccess(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("success", is(true));
    }

    @Step("Cоздания заказа без ингредиентов")
    public void createOrderNoIngredients(ValidatableResponse response) {
        response.assertThat()
                .statusCode(400)
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Step("Cоздания заказа с неверным хешем ингредиентов")
    public void createOrderInvalidHashIngredients(ValidatableResponse response) {
        response.assertThat()
                .statusCode(500);
    }

    @Step("Получение списка заказов зарегистрированным пользователем")
    public void getOrderListInLoginUser(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("success", is(true));
    }

    @Step("Получение списка заказов незарегистрированным пользователем")
    public void getOrderListNoLoginUser(ValidatableResponse response) {
        response.assertThat()
                .statusCode(401)
                .body("message", equalTo("You should be authorised"));
    }
}