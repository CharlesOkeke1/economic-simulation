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
        Scanner sc = new Scanner(System.in);

        MyUtils.SteppedPrinting("Are you a developer? (Yes/No): ", Constants.REPORT_DELAY_TIME);
        String dev = sc.nextLine();

        if (dev.toLowerCase().equals("yes")) {
            devType = true;
        } else {
            Initializer.initializer();
            devType = false;                
        }
        
        MyUtils.SteppedPrinting("Now choose a state: All 36 states are available", Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting("To select a state, type out the state name with the First letter in caps. eg, Lagos", Constants.REPORT_DELAY_TIME);
        stateChoice = sc.nextLine();
       
        if (!states.containsKey(stateChoice)) {
            MyUtils.SteppedPrinting("ERROR, Invalid state! Choose a valid state", Constants.REPORT_DELAY_TIME);
            stateChoice = sc.nextLine();
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