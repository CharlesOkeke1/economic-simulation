package game.data;

import java.util.*;
import game.economies.*;

public class EconomyInitializer {

    public static HashMap<String, StateEconomy> createStates() {
        HashMap<String, StateEconomy> states = new HashMap<>();

        states.put("Lagos", new StateEconomy("Lagos",
        9900000.0,9900000.0,22000,60.0,0.29,1380000.0,0.0,780000.0,0,22,
        0.0,0.0,0.0,0.0,0.0,0.40f,0.0,0,0.0,0.0,GovType.GROWTH_FOCUSED,0.0275));

        states.put("Rivers", new StateEconomy("Rivers",
        9500000.0,9500000.0,70000,58.0,0.26,1320000.0,0.0,760000.0,0,19,
        0.0,0.0,0.0,0.0,0.0,0.39f,0.0,0,0.0,0.0,GovType.GROWTH_FOCUSED,0.045));

        states.put("Kano", new StateEconomy("Kano",
        9200000.0,9200000.0,14000,52.0,0.21,1290000.0,0.0,720000.0,0,17,
        0.0,0.0,0.0,0.0,0.0,0.41f,0.0,0,0.0,0.0,GovType.GROWTH_FOCUSED,0.1025));

        states.put("Imo", new StateEconomy("Imo",
        9000000.0,9000000.0,54000,50.0,0.24,1250000.0,0.0,700000.0,0,16,
        0.0,0.0,0.0,0.0,0.0,0.36f,0.0,0,0.0,0.0,GovType.SOCIALIST,0.05));

        states.put("Enugu", new StateEconomy("Enugu",
        9100000.0,9100000.0,50000,54.0,0.22,1240000.0,0.0,690000.0,0,16,
        0.0,0.0,0.0,0.0,0.0,0.40f,0.0,0,0.0,0.0,GovType.SOCIALIST,0.064));

        states.put("Oyo", new StateEconomy("Oyo",
        9700000.0,9700000.0,82500,55.0,0.23,1350000.0,0.0,790000.0,0,18,
        0.0,0.0,0.0,0.0,0.0,0.38f,0.0,0,0.0,0.0,GovType.SOCIALIST,0.036));

        states.put("Katsina", new StateEconomy("Katsina",
        8800000.0,8800000.0,93000,47.0,0.19,1180000.0,0.0,640000.0,0,14,
        0.0,0.0,0.0,0.0,0.0,0.36f,0.0,0,0.0,0.0,GovType.SECURITY_ORIENTED,0.035));

        states.put("Benue", new StateEconomy("Benue",
        8900000.0,8900000.0,50000,49.0,0.20,1200000.0,0.0,670000.0,0,15,
        0.0,0.0,0.0,0.0,0.0,0.39f,0.0,0,0.0,0.0,GovType.SOCIALIST,0.025));

        states.put("Anambra", new StateEconomy("Anambra",
        9600000.0,9600000.0,53000,62.0,0.27,1370000.0,0.0,800000.0,0,20,
        0.0,0.0,0.0,0.0,0.0,0.37f,0.0,0,0.0,0.0,GovType.GROWTH_FOCUSED,0.037));

        states.put("Delta", new StateEconomy("Delta",
        10000000.0,10000000.0,41000,59.0,0.25,1400000.0,0.0,800000.0,0,21,
        0.0,0.0,0.0,0.0,0.0,0.42f,0.0,0,0.0,0.0,GovType.GROWTH_FOCUSED,0.043));

        states.put("Ogun", new StateEconomy("Ogun",
        9800000.0,9800000.0,36000,63.0,0.28,1390000.0,0.0,790000.0,0,21,
        0.0,0.0,0.0,0.0,0.0,0.43f,0.0,0,0.0,0.0,GovType.GROWTH_FOCUSED,0.1));

        states.put("Bayelsa", new StateEconomy("Bayelsa",
        9300000.0,9300000.0,24000,57.0,0.24,1310000.0,0.0,730000.0,0,18,
        0.0,0.0,0.0,0.0,0.0,0.40f,0.0,0,0.0,0.0,GovType.DEBT_FIGHTER,0.070));

        states.put("Niger", new StateEconomy("Niger",
        9400000.0,9400000.0,62000,50.0,0.22,1340000.0,0.0,760000.0,0,17,
        0.0,0.0,0.0,0.0,0.0,0.41f,0.0,0,0.0,0.0,GovType.DEBT_FIGHTER,0.0575));

        states.put("Kaduna", new StateEconomy("Kaduna",
        9500000.0,9500000.0,80000,53.0,0.23,1330000.0,0.0,750000.0,0,18,
        0.0,0.0,0.0,0.0,0.0,0.39f,0.0,0,0.0,0.0,GovType.SOCIALIST,0.070));

        states.put("Sokoto", new StateEconomy("Sokoto",
        8600000.0,8600000.0,49000,45.0,0.18,1150000.0,0.0,610000.0,0,14,
        0.0,0.0,0.0,0.0,0.0,0.38f,0.0,0,0.0,0.0,GovType.SECURITY_ORIENTED,0.021));

        states.put("Abia", new StateEconomy("Abia",
        8900000.0,8900000.0,42000,52.0,0.21,1230000.0,0.0,680000.0,0,16,
        0.0,0.0,0.0,0.0,0.0,0.39f,0.0,0,0.0,0.0,GovType.FISCAL_CONSERVATIVE,0.028));

        states.put("Adamawa", new StateEconomy("Adamawa",
        8700000.0,8700000.0,39000,48.0,0.19,1170000.0,0.0,650000.0,0,14,
        0.0,0.0,0.0,0.0,0.0,0.38f,0.0,0,0.0,0.0,GovType.DEBT_FIGHTER,0.0175));

        states.put("Akwa Ibom", new StateEconomy("Akwa Ibom",
        9800000.0,9800000.0,55000,61.0,0.27,1390000.0,0.0,790000.0,0,20,
        0.0,0.0,0.0,0.0,0.0,0.43f,0.0,0,0.0,0.0,GovType.GROWTH_FOCUSED,0.03));

        states.put("Bauchi", new StateEconomy("Bauchi",
        8800000.0,8800000.0,60000,47.0,0.18,1180000.0,0.0,630000.0,0,13,
        0.0,0.0,0.0,0.0,0.0,0.37f,0.0,0,0.0,0.0,GovType.SECURITY_ORIENTED,0.0925));

        states.put("Borno", new StateEconomy("Borno",
        8500000.0,8500000.0,58000,45.0,0.17,1120000.0,0.0,600000.0,0,10,
        0.0,0.0,0.0,0.0,0.0,0.36f,0.0,0,0.0,0.0,GovType.SECURITY_ORIENTED,0.095));

        states.put("Cross River", new StateEconomy("Cross River",
        9000000.0,9000000.0,35000,50.0,0.20,1250000.0,0.0,690000.0,0,15,
        0.0,0.0,0.0,0.0,0.0,0.39f,0.0,0,0.0,0.0,GovType.SOCIALIST,0.075));

        states.put("Ebonyi", new StateEconomy("Ebonyi",
        8700000.0,8700000.0,29000,49.0,0.19,1190000.0,0.0,650000.0,0,14,
        0.0,0.0,0.0,0.0,0.0,0.38f,0.0,0,0.0,0.0,GovType.FISCAL_CONSERVATIVE,0.080));

        states.put("Edo", new StateEconomy("Edo",
        9400000.0,9400000.0,43000,56.0,0.23,1340000.0,0.0,760000.0,0,18,
        0.0,0.0,0.0,0.0,0.0,0.41f,0.0,0,0.0,0.0,GovType.FISCAL_CONSERVATIVE,0.060));

        states.put("Ekiti", new StateEconomy("Ekiti",
        8600000.0,8600000.0,28000,55.0,0.22,1160000.0,0.0,620000.0,0,15,
        0.0,0.0,0.0,0.0,0.0,0.37f,0.0,0,0.0,0.0,GovType.FISCAL_CONSERVATIVE,0.065));

        states.put("Gombe", new StateEconomy("Gombe",
        8800000.0,8800000.0,30000,48.0,0.18,1180000.0,0.0,640000.0,0,13,
        0.0,0.0,0.0,0.0,0.0,0.38f,0.0,0,0.0,0.0,GovType.SECURITY_ORIENTED,0.05));

        states.put("Jigawa", new StateEconomy("Jigawa",
        8700000.0,8700000.0,56000,46.0,0.18,1170000.0,0.0,630000.0,0,12,
        0.0,0.0,0.0,0.0,0.0,0.37f,0.0,0,0.0,0.0,GovType.SECURITY_ORIENTED,0.0685));

        states.put("Kebbi", new StateEconomy("Kebbi",
        8800000.0,8800000.0,42000,47.0,0.19,1200000.0,0.0,660000.0,0,13,
        0.0,0.0,0.0,0.0,0.0,0.38f,0.0,0,0.0,0.0,GovType.DEBT_FIGHTER,0.0525));

        states.put("Kogi", new StateEconomy("Kogi",
        9100000.0,9100000.0,39000,50.0,0.20,1260000.0,0.0,700000.0,0,16,
        0.0,0.0,0.0,0.0,0.0,0.40f,0.0,0,0.0,0.0,GovType.FISCAL_CONSERVATIVE,0.082));

        states.put("Kwara", new StateEconomy("Kwara",
        9000000.0,9000000.0,31000,54.0,0.21,1240000.0,0.0,690000.0,0,16,
        0.0,0.0,0.0,0.0,0.0,0.39f,0.0,0,0.0,0.0,GovType.FISCAL_CONSERVATIVE,0.07));

        states.put("Nasarawa", new StateEconomy("Nasarawa",
        8900000.0,8900000.0,28000,51.0,0.20,1220000.0,0.0,680000.0,0,15,
        0.0,0.0,0.0,0.0,0.0,0.38f,0.0,0,0.0,0.0,GovType.FISCAL_CONSERVATIVE,0.11));

        states.put("Ondo", new StateEconomy("Ondo",
        9200000.0,9200000.0,41000,58.0,0.24,1300000.0,0.0,740000.0,0,18,
        0.0,0.0,0.0,0.0,0.0,0.41f,0.0,0,0.0,0.0,GovType.DEBT_FIGHTER,0.01));

        states.put("Osun", new StateEconomy("Osun",
        9000000.0,9000000.0,35000,56.0,0.22,1250000.0,0.0,700000.0,0,17,
        0.0,0.0,0.0,0.0,0.0,0.39f,0.0,0,0.0,0.0,GovType.FISCAL_CONSERVATIVE,0.078));

        states.put("Plateau", new StateEconomy("Plateau",
        8800000.0,8800000.0,32000,50.0,0.21,1210000.0,0.0,670000.0,0,15,
        0.0,0.0,0.0,0.0,0.0,0.38f,0.0,0,0.0,0.0,GovType.SOCIALIST,0.064));

        states.put("Taraba", new StateEconomy("Taraba",
        8600000.0,8600000.0,24000,46.0,0.18,1140000.0,0.0,620000.0,0,12,
        0.0,0.0,0.0,0.0,0.0,0.37f,0.0,0,0.0,0.0,GovType.DEBT_FIGHTER,0.05));

        states.put("Yobe", new StateEconomy("Yobe",
        8500000.0,8500000.0,30000,45.0,0.17,1100000.0,0.0,600000.0,0,11,
        0.0,0.0,0.0,0.0,0.0,0.36f,0.0,0,0.0,0.0,GovType.SECURITY_ORIENTED,0.045));

        states.put("Zamfara", new StateEconomy("Zamfara",
        8700000.0,8700000.0,35000,45.0,0.17,1150000.0,0.0,630000.0,0,11,
        0.0,0.0,0.0,0.0,0.0,0.37f,0.0,0,0.0,0.0,GovType.SECURITY_ORIENTED,0.1));

        states.put("Abuja", new StateEconomy("Abuja",
        10000000.0,10000000.0,45000,65.0,0.30,1400000.0,0.0,800000.0,0,23,
        0.0,0.0,0.0,0.0,0.0,0.44f,0.0,0,0.0,0.0,GovType.GROWTH_FOCUSED,0.095));

        return states;
    }

    public static FederalEconomy createFederal() {
        FederalEconomy fed = new FederalEconomy(0,1068,0.0,0.0,0.0,0.0,0,0.0);

        return fed;
    }
}