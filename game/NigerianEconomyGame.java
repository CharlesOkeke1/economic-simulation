package game;
import java.util.*;

import game.economies.FederalEconomy;
import game.economies.GovType;
import game.economies.StateEconomy;
import game.economies.SimulationResult;
import game.initialization.EconomyInitializer;
import utils.MyUtils;
import utils.Sleeper;
import utils.StateSelecter;
import game.engine.SimulationEngine;
import game.engine.TurnRandomizer;
import game.ui.Initializer;
import game.ui.PrintReports;



public class NigerianEconomyGame {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HashMap<String, StateEconomy> states = EconomyInitializer.createStates();
        FederalEconomy fed = EconomyInitializer.createFederal();
        HashMap<String, StateEconomy> initialStates = new HashMap<>();
        List<StateEconomy> list = new ArrayList<>(states.values());    
        int stateNo;
        int numMonths;
        int currentMonth;
        String policy = "";
        String stateChoice = "";
        String state = "";
        String admin = "admin"; //Prompt user for admin password which is used for testing purposes

        System.out.println("Are you a developer? If so enter the developer password. If you are not a developer just enter no: ");
        String devPass = sc.nextLine();
        if (devPass.equals(admin) == false) {
            Initializer.initializer();
        }

        MyUtils.SteppedPrinting("Now choose a state: All 36 states are available", 30);
        MyUtils.SteppedPrinting("To select a state, type out the state name with the First letter in caps. Eg Lagos: ", 30);
        stateChoice = sc.nextLine();

        if (stateChoice.equals("")) {
            MyUtils.SteppedPrinting("ERROR, Invalid state!", 30);
            MyUtils.SteppedPrinting("Now choose a state: ", 30);
            stateChoice = sc.nextLine();
        }

        state = StateSelecter.stateSelecter(stateChoice);

        System.out.println("How many months do you want to run this simulation for?: ");
        numMonths = sc.nextInt();
        sc.nextLine();
                
        currentMonth = 0;
        for (Map.Entry<String, StateEconomy> entry : states.entrySet()) {
            initialStates.put(entry.getKey(), new StateEconomy(entry.getValue()));
            
        }

        while (currentMonth < numMonths) {
            if (currentMonth == 0) {
                //Print Initial Report for user to decide first policy
                PrintReports.printStateReport(states, fed, state, currentMonth, "Initial"); 
                PrintReports.printFederationReport(fed, numMonths, "Initial");   
            } 

            if (devPass.equals(admin)) {
                policy = TurnRandomizer.randomP();
                MyUtils.SteppedPrinting("Your random policy for month " + MyUtils.Ordinalize(currentMonth + 1) + " is: " + policy, 30);  
            } else { 
                MyUtils.SteppedPrinting("What is your " + MyUtils.Ordinalize(currentMonth + 1) + " month's policy?: ", 30);
                policy = sc.nextLine();
            }
            
            SimulationResult result = SimulationEngine.simulate(states, fed, state, policy, currentMonth); 
            List<StateEconomy> sorted = new ArrayList<>(states.values());
            sorted.sort(Comparator.comparingInt(s -> s.position));
            PrintReports.printStateReport(states, fed, state, currentMonth, "Monthly"); 
            System.out.println(" ");
            PrintReports.printFederationReport(fed, currentMonth, "Monthly");   
            
            
            /*
            MyUtils.SteppedPrinting("===== PEFORMANCE OF OTHER STATES THIS MONTH =====", 30);
            for (StateEconomy s : sorted) {              
                if (s.position < 6) {
                    MyUtils.SteppedPrinting(s.name + "'s peformance score this month : " + String.format("%.2f", s.rankingScore) + " coming in " + MyUtils.Ordinalize(s.position) + " position.", 5);
                }
            }
            */
            currentMonth++;        

        }

        List<StateEconomy> sorted = new ArrayList<>(states.values());
        sorted.sort(Comparator.comparingInt(s -> s.position));
        PrintReports.printStateReport(states, fed, state, currentMonth, "Final"); 
        for (StateEconomy s : sorted) {              
            MyUtils.SteppedPrinting(s.name + "'s peformance score final month : " + String.format("%.2f", s.rankingScore) + " coming in " + MyUtils.Ordinalize(s.position) + " position.", 5);
            MyUtils.SteppedPrinting(s.name + "'s Real GDP is: " + MyUtils.formatNumber(s.gdp) + " Naira, State Reserve is: " + MyUtils.formatNumber(s.stateReserve) + " Naira and Operating Cash " + MyUtils.formatNumber(s.cash) + " Naira" , 5);
             
        }  

        System.out.println(" ");
        PrintReports.printFederationReport(fed, numMonths, "Final");   

        
    }
}
