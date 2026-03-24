package utils;
import java.util.*;

public class StateSelecter {
    //Take a state as input and set it as the user's chosen state
    public static String stateSelecter(String selectedState) {
        String[] availableStates = {"Abia", "Adamawa", "Akwa Ibom", "Anambra", "Bauchi", "Bayelsa", "Benue", "Borno", "Cross River", "Delta", "Ebonyi",
        "Edo", "Ekiti", "Enugu", "Gombe", "Imo", "Jigawa", "Kaduna", "Kano", "Katsina", "Kebbi", "Kogi", "Kwara", "Lagos", "Nasarawa", "Niger", "Ogun",
        "Ondo", "Osun", "Oyo", "Plateau", "Rivers", "Sokoto", "Taraba", "Yobe", "Zamfara", "Abuja"};
        
        String result = "";

        for (String availableState : availableStates) {
            if (selectedState.equals(availableState)) {
                result = availableState;
                break;
            }
        }
        return result;
    }
}