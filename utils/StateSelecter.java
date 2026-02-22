package utils;
import java.util.*;

public class StateSelecter {
    public static String stateSelecter(String selectedState) {
        String[] availableStates = {"Abia", "Adamawa", "Akwa Ibom", "Anambra", "Bauchi", "Bayelsa", "Benue", "Borno", "Cross River", "Delta", "Ebonyi",
        "Edo", "Ekiti", "Enugu", "Gombe", "Imo", "Jigawa", "Kaduna", "Kano", "Katsina", "Kebbi", "Kogi", "Kwara", "Lagos", "Nasarawa", "Niger", "Ogun",
        "Ondo", "Osun", "Oyo", "Plateau", "Rivers", "Sokoto", "Taraba", "Yobe", "Zamfara", "Abuja"};
        
        String result = "";
        
        for (int i = 0; i < availableStates.length; i++) {
            if (selectedState.equals(availableStates[i])) {
                result = availableStates[i];
            }
        }
        return result;
    }
}