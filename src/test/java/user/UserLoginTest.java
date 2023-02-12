package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.stellarburgers.dto.UserCreateDTO;
import praktikum.stellarburgers.dto.UserLoginDTO;
import praktikum.stellarburgers.user.UserGenerator;
import praktikum.stellarburgers.user.UserRequest;
import praktikum.stellarburgers.user.UserResponce;

public class UserLoginTest {
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
    @DisplayName("Verification of successful login under the existing username and password of the user")
    public void verifySuccessfulUserLogin() {
        UserLoginDTO userLoginDTO = UserLoginDTO.from(uniqueUser);
        ValidatableResponse login = userRequest.login(userLoginDTO);
        userResponce.loginInSusses(login);
    }

    @Test
    @DisplayName("Checking for an error when logging in with an invalid email")
    public void verifyLoginErrorWithIncorrectEmail() {
        UserLoginDTO userLoginDTO = UserLoginDTO.from(uniqueUser);
        userLoginDTO.setEmail(userLoginDTO.getEmail() + "1");
        ValidatableResponse login = userRequest.login(userLoginDTO);
        userResponce.loginInFailed(login);
    }

    @Test
    @DisplayName("Checking for an error when logging in with an incorrect password")
    public void verifyLoginErrorWithIncorrectPassword() {
        UserLoginDTO userLoginDTO = UserLoginDTO.from(uniqueUser);
        userLoginDTO.setPassword(userLoginDTO.getPassword() + "1");
        ValidatableResponse login = userRequest.login(userLoginDTO);
        userResponce.loginInFailed(login);
    }
}
