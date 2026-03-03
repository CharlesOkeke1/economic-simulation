package game.engine;
import game.economies.FederalEconomy;
import game.economies.StateEconomy;
import game.data.*;
import java.util.*;

public class RankingEngine {
    public static final double SCALE_CONSTANT = 250000.0;
    public static void calculateRank(Map<String, StateEconomy> states, FederalEconomy fed) {

        List<StateEconomy> list = new ArrayList<>(states.values());

        // First pass: compute min/max for normalization
        double maxGdpPerCap = Double.MIN_VALUE;
        double maxFiscal = Double.MIN_VALUE;
        double maxInfra = Double.MIN_VALUE;

        for (StateEconomy s : list) {

            double gdpPerCap = s.gdp / s.population;

            double fiscalHealth =
                    (s.monthlyProfit * 0.5)
                    + (s.stateReserve * 0.3)
                    - (s.debt * 0.2);   // penalize debt

          
            maxGdpPerCap = Math.max(maxGdpPerCap, gdpPerCap);
            maxFiscal = Math.max(maxFiscal, fiscalHealth);
            maxInfra = Math.max(maxInfra, s.infrastructure);
        }

        // Second pass: compute weighted ranking score
        for (StateEconomy s : list) {

            double gdpPerCap = s.gdp / s.population;
            double fiscalHealth =
                    (s.monthlyProfit * 0.4)
                    + (s.stateReserve * 0.3)
                    - (s.debt * 0.3);

            // Normalize to 0–1
            double gdpScore = Math.min(1.0, gdpPerCap / Constants.TARGET_GDP_PER_CAP);

            double fiscalScore = (Math.abs(maxFiscal) > 1e-9)
                    ? fiscalHealth / (fiscalHealth + SCALE_CONSTANT) 
                    : 0.0;

            double infraScore = (maxInfra > 0) ? s.infrastructure / maxInfra : 0.0;

            double stabilityScore = Math.min(100, s.stability) / 100.0;
            double growthScore = Math.max(-1, Math.min(1, Math.tanh(s.gdpGrowth * 10)));

            s.rankingScore =
                    (0.30 * gdpScore) +
                    (0.20 * fiscalScore) +
                    (0.125 * stabilityScore) +
                    (0.15 * growthScore) +
                    (0.125 * infraScore) -
                    (0.50 * Math.abs(s.inflationRate));

            if (Double.isNaN(s.rankingScore) || Double.isInfinite(s.rankingScore)) {
                s.rankingScore = 0.10; // hard fallback
            }

        }

        // Sort descending
        list.sort((a, b) ->
                Double.compare(b.rankingScore, a.rankingScore));

        // Assign positions
        for (int i = 0; i < list.size(); i++) {
            list.get(i).position = i + 1;
        }
    }
}