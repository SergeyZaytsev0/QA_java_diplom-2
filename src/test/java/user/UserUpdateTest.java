package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.stellarburgers.dto.UserCreateDTO;
import praktikum.stellarburgers.user.UserGenerator;
import praktikum.stellarburgers.user.UserRequest;
import praktikum.stellarburgers.user.UserResponse;

public class UserUpdateTest {
    private final UserGenerator userGenerator = new UserGenerator();
    private final UserRequest userRequest = new UserRequest();
    private final UserResponse userResponse = new UserResponse();
    private final UserCreateDTO uniqueUser = userGenerator.randomDataCourier();
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

    @Test
    @DisplayName("Checking the possibility of changing email by a registered user")
    public void verifyUpdateRegisteredUserEmail() {
        uniqueUser.setEmail(uniqueUser.getEmail() + "1");
        ValidatableResponse response = userRequest.update(accessToken, uniqueUser);
        userResponse.updateDateUserInSusses(response);
    }

    @Test
    @DisplayName("Checking the possibility of changing the password by a registered user")
    public void verifyUpdateRegisteredUserPassword() {
        uniqueUser.setPassword(uniqueUser.getPassword() + "1");
        ValidatableResponse response = userRequest.update(accessToken, uniqueUser);
        userResponse.updateDateUserInSusses(response);
    }

    @Test
    @DisplayName("Checking the possibility of changing the password by a registered user")
    public void verifyUpdateRegisteredUserName() {
        uniqueUser.setName(uniqueUser.getName() + "1");
        ValidatableResponse response = userRequest.update(accessToken, uniqueUser);
        userResponse.updateDateUserInSusses(response);
    }

    @Test
    @DisplayName("Checking the possibility of changing the password by an unregistered user")
    public void verifyUpdateUnregisteredUserName() {
        uniqueUser.setName(uniqueUser.getName() + "1");
        ValidatableResponse response = userRequest.update("1", uniqueUser);
        userResponse.updateDateUserNotInFailed(response);
    }

    @Test
    @DisplayName("Checking the possibility of changing the password by a registered user")
    public void verifyUpdateUnregisteredUserPassword() {
        uniqueUser.setPassword(uniqueUser.getPassword() + "1");
        ValidatableResponse response = userRequest.update("1", uniqueUser);
        userResponse.updateDateUserNotInFailed(response);
    }

    @Test
    @DisplayName("Checking the possibility of changing email by a registered user")
    public void verifyUpdateUnregisteredUserEmail() {
        uniqueUser.setEmail(uniqueUser.getEmail() + "1");
        ValidatableResponse response = userRequest.update("1", uniqueUser);
        userResponse.updateDateUserNotInFailed(response);
    }
}