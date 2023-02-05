package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.stellarburgers.dto.UserCreateDTO;
import praktikum.stellarburgers.user.UserGenerator;
import praktikum.stellarburgers.user.UserRequest;
import praktikum.stellarburgers.user.UserResponce;

public class UserUpdateTest {
    private final UserGenerator userGenerator = new UserGenerator();
    private final UserRequest userRequest = new UserRequest();
    private final UserResponce userResponce = new UserResponce();
    UserCreateDTO uniqueUser = userGenerator.randomDataCourier();
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

    @Test
    @DisplayName("Проверка возможности изменения email зарегестрированным пользователем")
    public void verifyUpdateRegisteredUserEmail() {
        uniqueUser.setEmail(uniqueUser.getEmail() + "1");
        ValidatableResponse response = userRequest.update(accessToken, uniqueUser);
        userResponce.updateDateUserInSusses(response);
    }

    @Test
    @DisplayName("Проверка возможности изменения пароля зарегестрированным пользователем")
    public void verifyUpdateRegisteredUserPassword() {
        uniqueUser.setPassword(uniqueUser.getPassword() + "1");
        ValidatableResponse response = userRequest.update(accessToken, uniqueUser);
        userResponce.updateDateUserInSusses(response);
    }

    @Test
    @DisplayName("Проверка возможности изменения пароля зарегестрированным пользователем")
    public void verifyUpdateRegisteredUserName() {
        uniqueUser.setName(uniqueUser.getName() + "1");
        ValidatableResponse response = userRequest.update(accessToken, uniqueUser);
        userResponce.updateDateUserInSusses(response);
    }

    @Test
    @DisplayName("Проверка возможности изменения пароля незарегестрированным пользователем")
    public void verifyUpdateUnregisteredUserName() {
        uniqueUser.setName(uniqueUser.getName() + "1");
        ValidatableResponse response = userRequest.update("1", uniqueUser);
        userResponce.updateDateUserNotInFailed(response);
    }

    @Test
    @DisplayName("Проверка возможности изменения пароля зарегестрированным пользователем")
    public void verifyUpdateUnregisteredUserPassword() {
        uniqueUser.setPassword(uniqueUser.getPassword() + "1");
        ValidatableResponse response = userRequest.update("1", uniqueUser);
        userResponce.updateDateUserNotInFailed(response);
    }

    @Test
    @DisplayName("Проверка возможности изменения email зарегестрированным пользователем")
    public void verifyUpdateUnregisteredUserEmail() {
        uniqueUser.setEmail(uniqueUser.getEmail() + "1");
        ValidatableResponse response = userRequest.update("1", uniqueUser);
        userResponce.updateDateUserNotInFailed(response);
    }
}