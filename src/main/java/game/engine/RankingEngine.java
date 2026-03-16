package game.engine;

import game.economies.FederalEconomy;
import game.economies.StateEconomy;
import java.util.*;

public class RankingEngine {

    /* ----- NORMALIZATION SCALES (tune these) ----- */
    private static final double GDP_PER_CAP_SCALE = 50000.0;
    private static final double FISCAL_SCALE = 500000.0;
    private static final double INFRA_SCALE = 100.0;
    private static final double GROWTH_SCALE = 0.05;

    public static void calculateRank(Map<String, StateEconomy> states, FederalEconomy fed) {

        List<StateEconomy> list = new ArrayList<>(states.values());

        for (StateEconomy s : list) {

            /* ----- Prosperity ----- */
            double gdpPerCap = s.gdp / Math.max(1, s.population);
            double gdpScore = Math.tanh(gdpPerCap / GDP_PER_CAP_SCALE);

            /* ----- Fiscal Health ----- */
            double fiscalBalance =
                    s.monthlyProfit +
                            (s.stateReserve * 0.3) -
                            (s.debt * 0.2);

            double fiscalScore = Math.tanh(fiscalBalance / FISCAL_SCALE);

            /* ----- Infrastructure ----- */
            double infraScore = Math.tanh(s.infrastructure / INFRA_SCALE);

            /* ----- Growth ----- */
            double growthScore = Math.tanh(s.gdpGrowth / GROWTH_SCALE);

            /* ----- Stability ----- */
            double stabilityScore = (s.stability / 50.0) - 1.0;

            /* ----- Inflation penalty ----- */
            double inflationPenalty = Math.tanh(Math.abs(s.inflationRate) * 8);

            /* ----- Final weighted score ----- */
            double score =
                    (0.30 * gdpScore) +
                            (0.25 * fiscalScore) +
                            (0.15 * infraScore) +
                            (0.15 * growthScore) +
                            (0.15 * stabilityScore) -
                            (0.20 * inflationPenalty);

            /* Clamp to [-1,1] */
            score = Math.max(-1, Math.min(1, score));

            /* Optional smoothing to prevent jumps */
            s.rankingScore = 0.75 * s.rankingScore + 0.25 * score;

            if (Double.isNaN(s.rankingScore) || Double.isInfinite(s.rankingScore)) {
                s.rankingScore = 0;
            }
        }

        /* ----- Sort descending ----- */
        list.sort((a, b) -> Double.compare(b.rankingScore, a.rankingScore));

        /* ----- Assign positions ----- */
        for (int i = 0; i < list.size(); i++) {
            list.get(i).position = i + 1;
        }
    }
}