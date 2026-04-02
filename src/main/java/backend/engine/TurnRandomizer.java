package engine;
import data.Constants;

import java.util.*;

public class TurnRandomizer {
    public static String randomP() {
        String[] policies = Constants.POLICY_LIST;
        Random rand = new Random();
        return policies[rand.nextInt(9)];
    }
}