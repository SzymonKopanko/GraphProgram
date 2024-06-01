package com.projekt;


import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Testy {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(GrafTest.class,AlgorytmyTest.class);

        System.out.print("Zakończono testy.\n\t Wyniki: \n\t\tBłędy: " +
                result.getFailureCount() + ". \n\t\tLiczba testów: " +
                result.getRunCount() + ". \n\t\tCzas: " +
                result.getRunTime() + "ms.\n\n");

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
//        System.out.println(result.wasSuccessful());
    }
}
