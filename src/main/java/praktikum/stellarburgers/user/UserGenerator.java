package praktikum.stellarburgers.user;

import org.apache.commons.lang3.RandomStringUtils;
import praktikum.stellarburgers.dto.UserCreateDTO;

public class UserGenerator {
    public UserCreateDTO randomDataCourier() {
        return new UserCreateDTO(RandomStringUtils.randomAlphabetic(10) + "@gmail.ru", "PedroPascal1", "TheLastOfUs");
    }
}
