package utils;
import java.util.*;

public class Sleeper {
    //Take a number in milliseconds and stop the thread for that amount of time
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // preserve interrupt status
        }
    }
}