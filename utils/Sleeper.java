package utils;
import java.util.*;

public class Sleeper {
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // preserve interrupt status
        }
    }
}