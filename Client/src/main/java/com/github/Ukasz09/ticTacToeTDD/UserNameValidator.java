package com.github.Ukasz09.ticTacToeTDD;

import java.util.regex.Pattern;

public class UserNameValidator {
    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_-]{3,12}$";
    private static Pattern pattern = Pattern.compile(USERNAME_PATTERN);

    public static boolean validate(final String username) {
        return pattern.matcher(username).matches();
    }
}
