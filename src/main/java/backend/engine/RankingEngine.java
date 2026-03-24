package engine;

import economies.FederalEconomy;
import economies.StateEconomy;
import gui.AppMain;

import java.util.*;

public class RankingEngine {

    /* ----- NORMALIZATION SCALES (tune these) ----- */
    private static final double GDP_PER_CAP_SCALE = 50000.0;
    private static final double FISCAL_SCALE = 500000.0;
    private static final double INFRA_SCALE = 100.0;
    private static final double GROWTH_SCALE = 0.05;

    public static void calculateRank() {
        HashMap<String, StateEconomy> states = AppMain.getStateMap();
        List<StateEconomy> list = new ArrayList<>(states.values());

        for (StateEconomy s : list) {

            /* ----- Prosperity ----- */
            double gdpPerCap = s.getGdp() / Math.max(1, s.getPopulation());
            double gdpScore = Math.tanh(gdpPerCap / GDP_PER_CAP_SCALE);

            /* ----- Fiscal Health ----- */
            double fiscalBalance =
                    s.getMonthlyProfit() +
                            (s.getStateReserve() * 0.3) -
                            (s.getDebt() * 0.2);

            double fiscalScore = Math.tanh(fiscalBalance / FISCAL_SCALE);

            /* ----- Infrastructure ----- */
            double infraScore = Math.tanh(s.getInfrastructure() / INFRA_SCALE);

            /* ----- Growth ----- */
            double growthScore = Math.tanh(s.getGdpGrowth() / GROWTH_SCALE);

            /* ----- Stability ----- */
            double stabilityScore = (s.getStability() / 50.0) - 1.0;

            /* ----- Inflation penalty ----- */
            double inflationPenalty = Math.tanh(Math.abs(s.getInflationRate()) * 8);

            /* ----- Final weighted score ----- */
            double score =
                    (0.50 * gdpScore) +
                            (0.15 * fiscalScore) +
                            (0.1 * infraScore) +
                            (0.1 * growthScore) +
                            (0.15 * stabilityScore) -
                            (0.20 * inflationPenalty);

            /* Clamp to [-1,1] */
            score = Math.max(-1, Math.min(1, score));

            /* Optional smoothing to prevent jumps */
            s.setRankingScore(0.75 * s.getRankingScore() + 0.25 * score);
            s.setRankingScore(PolicyEngine.weirdDoubleChecks(s.getRankingScore(), 0));
        }

        /* ----- Sort descending ----- */
        list.sort((a, b) -> Double.compare(b.getRankingScore(), a.getRankingScore()));

        /* ----- Assign positions ----- */
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setPosition(i + 1);
        }

    }
}