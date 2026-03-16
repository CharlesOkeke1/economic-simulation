package game.election;

import game.economies.FederalEconomy;
import game.economies.StateEconomy;
import game.economies.GovType;

import java.util.*;

public class ElectionEngine {

    private static double electionCost;
    private static final Random rand = new Random();

    public void setElectionCost(double cost) {electionCost = cost;}
    public double getElectionCost() {return electionCost;}

    public static void elect(Map<String, StateEconomy> states, FederalEconomy federal) {

        for (StateEconomy state : states.values()) {

            /* ========================
               1. ELECTION COST
               ======================== */

            double baseCost = state.gdp * 0.015;     // 1.5% of GDP
            double reserveHit = state.stateReserve * 0.40;

            double electionCost = baseCost + reserveHit;
            if (state.stateReserve > 0) state.stateReserve -= electionCost;
            else state.cash -= (state.cash * 0.15) + baseCost;

            /* ========================
               2. PERFORMANCE SCORE
               ======================== */

            double score = 0;

            score += state.gdpGrowth * 2;
            score -= state.inflationRate * 2;
            score += state.stability * 0.4;

            /* ========================
               3. INCUMBENT WIN CHANCE
               ======================== */

            double winChance = sigmoid(score);

            if (rand.nextDouble() > winChance) {

                /* ========================
                   GOVERNMENT CHANGE
                   ======================== */

                GovType newGov = randomGov(state.governmentType);
                state.governmentType = newGov;

                // instability after transition
                state.stability -= 2;

                // transition spending
                state.cash -= state.gdp * 0.01;

            } else {

                // incumbency advantage
                state.stability += 0.5;

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

}