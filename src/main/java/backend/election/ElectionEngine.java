package election;

import economies.*;
import gui.AppMain;


import java.util.*;

public class ElectionEngine {

    private static double electionCost;
    private static final Random rand = new Random();



    public static void runElection() {
        HashMap<String, StateEconomy> states = AppMain.getStateMap();
        for (StateEconomy state : states.values()) {

            /* ========================
               1. ELECTION COST
               ======================== */

            double baseCost = state.getGdp() * 0.015;     // 1.5% of GDP
            double reserveHit = state.getStateReserve() * 0.40;


            setElectionCost(baseCost + reserveHit);
            double reserve = state.getStateReserve();
            double cash = state.getCash();
            if (state.getStateReserve() > 0) reserve -= electionCost;
            else cash -= (cash * 0.15) + baseCost;
            state.setStateReserve(reserve);
            state.setCash(cash);

            /* ========================
               2. PERFORMANCE SCORE
               ======================== */

            double score = 0;

            score += state.getGdpGrowth() * 2;
            score -= state.getInflationRate() * 2;
            score += state.getStability() * 0.4;

            /* ========================
               3. INCUMBENT WIN CHANCE
               ======================== */

            double winChance = sigmoid(score);

            if (rand.nextDouble() > winChance) {

                /* ========================
                   GOVERNMENT CHANGE
                   ======================== */

                GovType newGov = randomGov(state.getGovernmentType());
                state.setGovernmentType(newGov);

                // instability after transition
                state.setStability(state.getStability() - 2);

                // transition spending - 5%
                state.setCash(state.getCash() - (state.getGdp() * 0.05));

            } else {

                // incumbency advantage
                state.setStability(state.getStability() + 0.5);

            }
        }
    }

    /* ========================
       RANDOM NEW GOVERNMENT
       ======================== */

    private static GovType randomGov(GovType current) {
        GovType[] values = GovType.values();
        GovType next;

        do {
            next = values[rand.nextInt(values.length)];
        } while (next == current);

        return next;
    }

    /* ========================
       SIGMOID FUNCTION
       ======================== */

    private static double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    public static void setElectionCost(double cost) {electionCost = cost;}
    public static double getElectionCost() {return electionCost;}

}