package com.github.Ukasz09.ticTacToe.controller;

public class Logger {
    public static void logError(Class classInWithError, String info) {
        System.err.println(classInWithError.getSimpleName() + "-> Error: " + info);
    }

    public static void logCommunicate(String info) {
        System.out.println(info);
    }
}
