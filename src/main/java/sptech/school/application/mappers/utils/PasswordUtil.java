package sptech.school.application.mappers.utils;

import sptech.school.domain.exception.WeakPasswordException;

import java.util.regex.Pattern;

public final class PasswordUtil {

    private static final Pattern STRONG_PWD = Pattern.compile(
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,64}$"
    );

    public static void validatePasswordStrength(String pwd) {
        if (pwd == null || !STRONG_PWD.matcher(pwd).matches()) {
            throw new WeakPasswordException(
                    "Senha fraca: use ≥ 8 e ≤ 64 caracteres, sem espaços, com letra maiúscula, minúscula, dígito e símbolo."
            );
        }
    }
}
