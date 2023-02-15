package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import praktikum.stellarburgers.dto.UserCreateDTO;
import praktikum.stellarburgers.user.UserGenerator;
import praktikum.stellarburgers.user.UserRequest;
import praktikum.stellarburgers.user.UserResponse;

public class UserCreateTest {

    private final UserGenerator userGenerator = new UserGenerator();
    private final UserRequest userRequest = new UserRequest();
    private final UserResponse userResponse = new UserResponse();
    private final UserCreateDTO uniqueUser = userGenerator.randomDataCourier();
    private String accessToken;

    @After
    public void cleanUpUser() {
        if (accessToken != null) {
            ValidatableResponse response = userRequest.delete(accessToken);
            userResponse.assertDeleteSusses(response);
        }
    }

    @Test
    @DisplayName("Verifying successful user creation")
    public void verifySuccessfulUserCreation() {
        ValidatableResponse create = userRequest.create(uniqueUser);
        accessToken = userResponse.assertCreationSusses(create);
    }

    @Test
    @DisplayName("Checking the impossibility of creating a double user")
    public void verifyFailedDuplicateUserCreation() {
        ValidatableResponse create = userRequest.create(uniqueUser);
        accessToken = userResponse.assertCreationSusses(create);

        ValidatableResponse create2 = userRequest.create(uniqueUser);
        userResponse.assertCreationDoubleUserFailed(create2);
    }

    @Test
    @DisplayName("Checking the impossibility of creating a user without e-mail")
    public void verifyFailedUserCreationWithoutEmail() {
        uniqueUser.setEmail(null);
        ValidatableResponse create = userRequest.create(uniqueUser);
        userResponse.assertCreationUserNoRequiredField(create);
    }

    @Test
    @DisplayName("Checking the impossibility of creating a user without a password")
    public void verifyFailedUserCreationWithoutPassword() {
        uniqueUser.setPassword(null);
        ValidatableResponse create = userRequest.create(uniqueUser);
        userResponse.assertCreationUserNoRequiredField(create);
    }

    @Test
    @DisplayName("Checking the impossibility of creating a user without a name")
    public void verifyFailedUserCreationWithoutName() {
        uniqueUser.setName(null);
        ValidatableResponse create = userRequest.create(uniqueUser);
        userResponse.assertCreationUserNoRequiredField(create);
    }
}