package quru.qa.petproject.countries.countries.util;

@FunctionalInterface
public interface ThrowingRunnable {
    void run() throws Throwable;
}