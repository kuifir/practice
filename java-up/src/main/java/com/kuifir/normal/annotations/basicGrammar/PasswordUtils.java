package com.kuifir.normal.annotations.basicGrammar;

import java.util.List;

/**
 * 类中方法被{@link UseCase}注解为用例：
 *
 * @author kuifir
 * @date 2023/6/12 22:12
 * @see UseCase
 */
public class PasswordUtils {
    @UseCase(id = 47, description = "Password must contain at least one numeric")
    public boolean validatePassword(String passwd) {
        return passwd.matches("\\w*\\d\\w*");
    }

    @UseCase(id = 48)
    public String encryptPassword(String passwd) {
        return new StringBuilder(passwd).reverse().toString();
    }

    @UseCase(id = 49, description = "New passwords can't equal previously used ones")
    public boolean checkForNewPassword(List<String> prevPasswords, String passwd) {
        return !prevPasswords.contains(passwd);
    }
}
