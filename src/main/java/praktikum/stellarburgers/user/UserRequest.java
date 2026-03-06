package praktikum.stellarburgers.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.stellarburgers.Config;
import praktikum.stellarburgers.dto.UserCreateDTO;
import praktikum.stellarburgers.dto.UserLoginDTO;

public class UserRequest extends Config {
    private final String ROOT = "/auth";
    private final String REGISTER = ROOT + "/register";
    private final String USER = ROOT + "/user";
    private final String LOGIN = ROOT + "/login";

    @Step("Request to create a user")
    public ValidatableResponse create(UserCreateDTO userCreateDTO) {
        return spec()
                .body(userCreateDTO)
                .when()
                .post(REGISTER)
                .then().log().all();
    }

    @Step("User delete request")
    public ValidatableResponse delete(String token) {
        return spec()
                .header("Authorization", token)
                .when()
                .delete(USER)
                .then().log().all();
    }

    @Step("Courier Entry Request")
    public ValidatableResponse login(UserLoginDTO userLoginDTO) {
        return spec()
                .body(userLoginDTO)
                .when()
                .post(LOGIN)
                .then().log().all();
    }

    @Step("Request to change user data")
    public ValidatableResponse update(String token, UserCreateDTO userCreateDTO) {
        return spec()
                .header("Authorization", token)
                .body(userCreateDTO)
                .when()
                .patch(USER)
                .then().log().all();
    }
}
