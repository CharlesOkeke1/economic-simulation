package engine;
import java.util.*;

public class TurnRandomizer {
    public static String randomP() {
        String[] policies = {"Raise Taxes", "Cut Taxes", "Invest Infrastructure", "Invest Education", "Boost Security", "Borrow", "Austerity", "Market Trader Support", "Subsidise Transport & Food"};
        Random rand = new Random();
        return policies[rand.nextInt(9)];
    }
}