package com.github.Ukasz09.ticTacToeTDD.applicationLogic.ticTacToeGame;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserNameValidator {
    private Pattern pattern;
    private Matcher matcher;

    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_-]{3,12}$";

    public UserNameValidator(){
        pattern = Pattern.compile(USERNAME_PATTERN);
    }

    public boolean validate(final String username){
        matcher = pattern.matcher(username);
        return matcher.matches();

    }
}
