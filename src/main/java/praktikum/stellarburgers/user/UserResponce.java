package praktikum.stellarburgers.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;

public class UserResponce {

    @Step("Успешный ответ на запрос по созданию пользователя.")
    public String assertCreationSusses(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(200)
                .body("success", is(true))
                .extract().path("accessToken");
    }

    @Step("Успешный ответ ответа на запрос по удалению пользователя.")
    public void assertDeleteSusses(ValidatableResponse response) {
        response.assertThat()
                .statusCode(202)
                .body("message", equalTo("User successfully removed"));
    }

    @Step("Ошибка по запросу на создание ранее зарегистрированного пользователя.")
    public void assertCreationDoubleUserFailed(ValidatableResponse response) {
        response.assertThat()
                .statusCode(403)
                .body("message", equalTo("User already exists"));
    }

    @Step("Ошибка по запросу на созданию пользователя c незаполненным обязательным полем")
    public void assertCreationUserNoRequiredField(ValidatableResponse response) {
        response.assertThat()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Step("Успешный ответ на запрос входа")
    public void loginInSusses(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("accessToken", notNullValue());
    }

    @Step("Ошибка при указании неверного логина или пароля")
    public void loginInFailed(ValidatableResponse response) {
        response.assertThat()
                .statusCode(401)
                .body("message", equalTo("email or password are incorrect"));
    }

    @Step("Успешный ответ на запрос изменения данных зарегестрированным пользователем")
    public void updateDateUserInSusses(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("success", is(true));
    }

    @Step("Успешный ответ на запрос изменения данных зарегестрированным пользователем")
    public void updateDateUserNotInFailed(ValidatableResponse response) {
        response.assertThat()
                .statusCode(401)
                .body("message", equalTo("You should be authorised"));
    }
}