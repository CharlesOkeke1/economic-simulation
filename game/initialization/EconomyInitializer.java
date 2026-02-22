package game.initialization;

import java.util.*;
import game.economies.*;

public class EconomyInitializer {

    public static HashMap<String, StateEconomy> createStates() {
        HashMap<String, StateEconomy> states = new HashMap<>();

        states.put("Lagos", new StateEconomy("Lagos",2500000.0,22000,60.0,0.29,1000000.0,0.0,1000000.0,0,22,
        0.0,0.0,0.0,0.0,0.0,0.40f,0.0,0,0.0,0.0,GovType.GROWTH_FOCUSED));

        states.put("Rivers", new StateEconomy("Rivers",2300000.0,70000,58.0,0.26,900000.0,0.0,1000000.0,0,19,
        0.0,0.0,0.0,0.0,0.0,0.39f,0.0,0,0.0,0.0,GovType.GROWTH_FOCUSED));

        states.put("Kano", new StateEconomy("Kano",2100000.0,14000,52.0,0.21,850000.0,0.0,1000000.0,0,17,
        0.0,0.0,0.0,0.0,0.0,0.41f,0.0,0,0.0,0.0,GovType.GROWTH_FOCUSED));

        states.put("Imo", new StateEconomy("Imo",2200000.0,54000,50.0,0.24,800000.0,0.0,1000000.0,0,16,
        0.0,0.0,0.0,0.0,0.0,0.36f,0.0,0,0.0,0.0,GovType.SOCIALIST));

        states.put("Enugu", new StateEconomy("Enugu",2050000.0,50000,54.0,0.22,820000.0,0.0,1000000.0,0,16,
        0.0,0.0,0.0,0.0,0.0,0.40f,0.0,0,0.0,0.0,GovType.SOCIALIST));

        states.put("Oyo", new StateEconomy("Oyo",2400000.0,82500,55.0,0.23,950000.0,0.0,1000000.0,0,18,
        0.0,0.0,0.0,0.0,0.0,0.38f,0.0,0,0.0,0.0,GovType.SOCIALIST));

        states.put("Katsina", new StateEconomy("Katsina",2150000.0,93000,47.0,0.19,780000.0,0.0,1000000.0,0,14,
        0.0,0.0,0.0,0.0,0.0,0.36f,0.0,0,0.0,0.0,GovType.SECURITY_ORIENTED));

        states.put("Benue", new StateEconomy("Benue",2250000.0,50000,49.0,0.20,870000.0,0.0,1000000.0,0,15,
        0.0,0.0,0.0,0.0,0.0,0.39f,0.0,0,0.0,0.0,GovType.SOCIALIST));

        states.put("Anambra", new StateEconomy("Anambra",2450000.0,53000,62.0,0.27,1100000.0,0.0,1000000.0,0,20,
        0.0,0.0,0.0,0.0,0.0,0.37f,0.0,0,0.0,0.0,GovType.GROWTH_FOCUSED));

        states.put("Delta", new StateEconomy("Delta",2600000.0,41000,59.0,0.25,1200000.0,0.0,1000000.0,0,21,
        0.0,0.0,0.0,0.0,0.0,0.42f,0.0,0,0.0,0.0,GovType.GROWTH_FOCUSED));

        states.put("Ogun", new StateEconomy("Ogun",2550000.0,36000,63.0,0.28,1150000.0,0.0,1000000.0,0,21,
        0.0,0.0,0.0,0.0,0.0,0.43f,0.0,0,0.0,0.0,GovType.GROWTH_FOCUSED));

        states.put("Bayelsa", new StateEconomy("Bayelsa",2350000.0,24000,57.0,0.24,1000000.0,0.0,1000000.0,0,18,
        0.0,0.0,0.0,0.0,0.0,0.40f,0.0,0,0.0,0.0,GovType.DEBT_FIGHTER));

        states.put("Niger", new StateEconomy("Niger",2450000.0,62000,50.0,0.22,1050000.0,0.0,1000000.0,0,17,
        0.0,0.0,0.0,0.0,0.0,0.41f,0.0,0,0.0,0.0,GovType.DEBT_FIGHTER));

        states.put("Kaduna", new StateEconomy("Kaduna",2500000.0,80000,53.0,0.23,950000.0,0.0,1000000.0,0,18,
        0.0,0.0,0.0,0.0,0.0,0.39f,0.0,0,0.0,0.0,GovType.SOCIALIST));

        states.put("Sokoto", new StateEconomy("Sokoto",2100000.0,49000,45.0,0.18,800000.0,0.0,1000000.0,0,14,
        0.0,0.0,0.0,0.0,0.0,0.38f,0.0,0,0.0,0.0,GovType.SECURITY_ORIENTED));

        states.put("Abia", new StateEconomy("Abia",2200000.0,42000,52.0,0.21,900000.0,0.0,1000000.0,0,16,
        0.0,0.0,0.0,0.0,0.0,0.39f,0.0,0,0.0,0.0,GovType.FISCAL_CONSERVATIVE));

        states.put("Adamawa", new StateEconomy("Adamawa",2150000.0,39000,48.0,0.19,850000.0,0.0,1000000.0,0,14,
        0.0,0.0,0.0,0.0,0.0,0.38f,0.0,0,0.0,0.0,GovType.DEBT_FIGHTER));

        states.put("Akwa Ibom", new StateEconomy("Akwa Ibom",2650000.0,55000,61.0,0.27,1150000.0,0.0,1000000.0,0,20,
        0.0,0.0,0.0,0.0,0.0,0.43f,0.0,0,0.0,0.0,GovType.GROWTH_FOCUSED));

        states.put("Bauchi", new StateEconomy("Bauchi",2050000.0,60000,47.0,0.18,800000.0,0.0,1000000.0,0,13,
        0.0,0.0,0.0,0.0,0.0,0.37f,0.0,0,0.0,0.0,GovType.SECURITY_ORIENTED));

        states.put("Borno", new StateEconomy("Borno",2000000.0,58000,45.0,0.17,780000.0,0.0,1000000.0,0,10,
        0.0,0.0,0.0,0.0,0.0,0.36f,0.0,0,0.0,0.0,GovType.SECURITY_ORIENTED));

        states.put("Cross River", new StateEconomy("Cross River",2250000.0,35000,50.0,0.20,900000.0,0.0,1000000.0,0,15,
        0.0,0.0,0.0,0.0,0.0,0.39f,0.0,0,0.0,0.0,GovType.SOCIALIST));

        states.put("Ebonyi", new StateEconomy("Ebonyi",2100000.0,29000,49.0,0.19,820000.0,0.0,1000000.0,0,14,
        0.0,0.0,0.0,0.0,0.0,0.38f,0.0,0,0.0,0.0,GovType.FISCAL_CONSERVATIVE));

        states.put("Edo", new StateEconomy("Edo",2400000.0,43000,56.0,0.23,950000.0,0.0,1000000.0,0,18,
        0.0,0.0,0.0,0.0,0.0,0.41f,0.0,0,0.0,0.0,GovType.FISCAL_CONSERVATIVE));

        states.put("Ekiti", new StateEconomy("Ekiti",2050000.0,28000,55.0,0.22,800000.0,0.0,1000000.0,0,15,
        0.0,0.0,0.0,0.0,0.0,0.37f,0.0,0,0.0,0.0,GovType.FISCAL_CONSERVATIVE));

        states.put("Gombe", new StateEconomy("Gombe",2100000.0,30000,48.0,0.18,820000.0,0.0,1000000.0,0,13,
        0.0,0.0,0.0,0.0,0.0,0.38f,0.0,0,0.0,0.0,GovType.SECURITY_ORIENTED));

        states.put("Jigawa", new StateEconomy("Jigawa",2050000.0,56000,46.0,0.18,800000.0,0.0,1000000.0,0,12,
        0.0,0.0,0.0,0.0,0.0,0.37f,0.0,0,0.0,0.0,GovType.SECURITY_ORIENTED));

        states.put("Kebbi", new StateEconomy("Kebbi",2100000.0,42000,47.0,0.19,830000.0,0.0,1000000.0,0,13,
        0.0,0.0,0.0,0.0,0.0,0.38f,0.0,0,0.0,0.0,GovType.DEBT_FIGHTER));

        states.put("Kogi", new StateEconomy("Kogi",2300000.0,39000,50.0,0.20,920000.0,0.0,1000000.0,0,16,
        0.0,0.0,0.0,0.0,0.0,0.40f,0.0,0,0.0,0.0,GovType.FISCAL_CONSERVATIVE));

        states.put("Kwara", new StateEconomy("Kwara",2200000.0,31000,54.0,0.21,880000.0,0.0,1000000.0,0,16,
        0.0,0.0,0.0,0.0,0.0,0.39f,0.0,0,0.0,0.0,GovType.FISCAL_CONSERVATIVE));

        states.put("Nasarawa", new StateEconomy("Nasarawa",2150000.0,28000,51.0,0.20,850000.0,0.0,1000000.0,0,15,
        0.0,0.0,0.0,0.0,0.0,0.38f,0.0,0,0.0,0.0,GovType.FISCAL_CONSERVATIVE));

        states.put("Ondo", new StateEconomy("Ondo",2350000.0,41000,58.0,0.24,1000000.0,0.0,1000000.0,0,18,
        0.0,0.0,0.0,0.0,0.0,0.41f,0.0,0,0.0,0.0,GovType.DEBT_FIGHTER));

        states.put("Osun", new StateEconomy("Osun",2250000.0,35000,56.0,0.22,900000.0,0.0,1000000.0,0,17,
        0.0,0.0,0.0,0.0,0.0,0.39f,0.0,0,0.0,0.0,GovType.FISCAL_CONSERVATIVE));

        states.put("Plateau", new StateEconomy("Plateau",2200000.0,32000,50.0,0.21,880000.0,0.0,1000000.0,0,15,
        0.0,0.0,0.0,0.0,0.0,0.38f,0.0,0,0.0,0.0,GovType.SOCIALIST));

        states.put("Taraba", new StateEconomy("Taraba",2050000.0,24000,46.0,0.18,780000.0,0.0,1000000.0,0,12,
        0.0,0.0,0.0,0.0,0.0,0.37f,0.0,0,0.0,0.0,GovType.DEBT_FIGHTER));

        states.put("Yobe", new StateEconomy("Yobe",2000000.0,30000,45.0,0.17,760000.0,0.0,1000000.0,0,11,
        0.0,0.0,0.0,0.0,0.0,0.36f,0.0,0,0.0,0.0,GovType.SECURITY_ORIENTED));

        states.put("Zamfara", new StateEconomy("Zamfara",2050000.0,35000,45.0,0.17,780000.0,0.0,1000000.0,0,11,
        0.0,0.0,0.0,0.0,0.0,0.37f,0.0,0,0.0,0.0,GovType.SECURITY_ORIENTED));

        states.put("Abuja", new StateEconomy("Abuja",2750000.0,45000,65.0,0.30,1200000.0,0.0,1000000.0,0,23,
        0.0,0.0,0.0,0.0,0.0,0.44f,0.0,0,0.0,0.0,GovType.GROWTH_FOCUSED));
        return states;
    }

    public static FederalEconomy createFederal() {
        FederalEconomy fed = new FederalEconomy(0, 1068, 0.0, 0.0, 0.0, 0.0, 0);

        return fed;
    }
}