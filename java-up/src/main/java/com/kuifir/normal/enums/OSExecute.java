package com.kuifir.normal.enums;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author kuifir
 * @date 2023/5/30 23:27
 */
public class OSExecute {
    public static void command(String command) {
        boolean err = false;
        try {
            Process process = new ProcessBuilder(
                    command.split(" ")).start();
            try (
                    BufferedReader results = new BufferedReader(
                            new InputStreamReader(
                                    process.getInputStream()));
                    BufferedReader errors = new BufferedReader(
                            new InputStreamReader(
                                    process.getErrorStream()))
            ) {
                results.lines()
                        .forEach(System.out::println);
                err = errors.lines()
                        .peek(System.err::println)
                        .count() > 0;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (err) {
            throw new OSExecuteException(
                    "Errors executing " + command);
        }
    }
}
