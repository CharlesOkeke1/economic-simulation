package game.config;

import game.data.Constants;
import game.economies.StateEconomy;
import utils.StateSelecter;
import game.ui.Initializer;
import utils.MyUtils;
import java.util.*; 

public class GameSetup {
    public static GameConfig setup(Map<String, StateEconomy> states) {
        String stateChoice;
        String state;
        int numMonths;
        boolean devType = false;
        GameDifficulty difficulty;
        String devPass = "admin123";
        Scanner sc = new Scanner(System.in);

        MyUtils.SteppedPrinting("Are you a developer? (Yes/No): ", Constants.REPORT_DELAY_TIME);
        String dev = sc.nextLine().trim();

        if (dev.toLowerCase().equals("yes")) {
            devType = true;
            MyUtils.SteppedPrinting("What is the developer password?: ", Constants.REPORT_DELAY_TIME);
            String pass = sc.nextLine().trim();
            if (pass.equals(devPass)) devType = true;
            else Initializer.initializer();
        } else {
            devType = false;
            Initializer.initializer();
                          
        }
        
        MyUtils.SteppedPrinting("Choose a state among all 37 states (including Abuja)", Constants.REPORT_DELAY_TIME);
        String choice = sc.nextLine();
        stateChoice = choice.substring(0, 1).toUpperCase() + choice.substring(1).toLowerCase();
       
        if (!states.containsKey(stateChoice)) {
            MyUtils.SteppedPrinting("ERROR, Invalid state! Choose a valid state", Constants.REPORT_DELAY_TIME);
            choice = sc.nextLine();
            stateChoice = choice.substring(0, 1).toUpperCase() + choice.substring(1).toLowerCase();  
        }

        state = StateSelecter.stateSelecter(stateChoice);

        MyUtils.SteppedPrinting("How many months do you want to run this simulation for?: ", Constants.REPORT_DELAY_TIME);
        numMonths = sc.nextInt();
        sc.nextLine();

        MyUtils.SteppedPrinting("What difficulty do you want to play at?: ", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("(Easy/Medium/Hard/Expert)", Constants.REPORT_DELAY_TIME);
        difficulty = GameDifficulty.valueOf(sc.nextLine().trim().toUpperCase());
        

        return new GameConfig(state, numMonths, difficulty, devType);
    }
}