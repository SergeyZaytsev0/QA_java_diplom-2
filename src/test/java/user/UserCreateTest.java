package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import praktikum.stellarburgers.dto.UserCreateDTO;
import praktikum.stellarburgers.user.UserGenerator;
import praktikum.stellarburgers.user.UserRequest;
import praktikum.stellarburgers.user.UserResponce;

public class UserCreateTest {

    private final UserGenerator userGenerator = new UserGenerator();
    private final UserRequest userRequest = new UserRequest();
    private final UserResponce userResponce = new UserResponce();
    UserCreateDTO uniqueUser = userGenerator.randomDataCourier();
    private String accessToken;

    @After
    public void cleanUpUser() {
        if (accessToken != null) {
            ValidatableResponse response = userRequest.delete(accessToken);
            userResponce.assertDeleteSusses(response);
        }
    }

    @Test
    @DisplayName("Verifying successful user creation")
    public void verifySuccessfulUserCreation() {
        ValidatableResponse create = userRequest.create(uniqueUser);
        accessToken = userResponce.assertCreationSusses(create);
    }

    @Test
    @DisplayName("Checking the impossibility of creating a double user")
    public void verifyFailedDuplicateUserCreation() {
        ValidatableResponse create = userRequest.create(uniqueUser);
        accessToken = userResponce.assertCreationSusses(create);

        ValidatableResponse create2 = userRequest.create(uniqueUser);
        userResponce.assertCreationDoubleUserFailed(create2);
    }

    @Test
    @DisplayName("Checking the impossibility of creating a user without e-mail")
    public void verifyFailedUserCreationWithoutEmail() {
        uniqueUser.setEmail(null);
        ValidatableResponse create = userRequest.create(uniqueUser);
        userResponce.assertCreationUserNoRequiredField(create);
    }

    @Test
    @DisplayName("Checking the impossibility of creating a user without a password")
    public void verifyFailedUserCreationWithoutPassword() {
        uniqueUser.setPassword(null);
        ValidatableResponse create = userRequest.create(uniqueUser);
        userResponce.assertCreationUserNoRequiredField(create);
    }

    @Test
    @DisplayName("Checking the impossibility of creating a user without a name")
    public void verifyFailedUserCreationWithoutName() {
        uniqueUser.setName(null);
        ValidatableResponse create = userRequest.create(uniqueUser);
        userResponce.assertCreationUserNoRequiredField(create);
    }
}