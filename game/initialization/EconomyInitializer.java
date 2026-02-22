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

        states.put("Abuja", new StateEconomy("Abuja",2750000.0,45000,65.0,0.30,1200000.0,0.0,1000000.0,0,23,
        0.0,0.0,0.0,0.0,0.0,0.44f,0.0,0,0.0,0.0,GovType.GROWTH_FOCUSED));
        return states;
    }

    public static FederalEconomy createFederal() {
        FederalEconomy fed = new FederalEconomy(0, 1068, 0.0, 0.0, 0.0, 0.0, 0);

        return fed;
    }
}