package game.engine;
import game.economies.FederalEconomy;
import game.economies.StateEconomy;
import java.util.*;

public class RankingEngine {
    public static void calculateRank(Map<String, StateEconomy> states, FederalEconomy fed) {

        List<StateEconomy> list = new ArrayList<>(states.values());

        // First pass: compute min/max for normalization
        double maxGdpPerCap = Double.MIN_VALUE;
        double maxFiscal = Double.MIN_VALUE;
        double maxInfra = Double.MIN_VALUE;
        double maxPopIndex = Double.MIN_VALUE;

        for (StateEconomy s : list) {

            double gdpPerCap = s.gdp / s.population;

            double fiscalHealth =
                    (s.monthlyProfit * 0.5)
                    + (s.stateReserve * 0.3)
                    - (s.debt * 0.05);   // penalize debt

            double popIndex = s.population/fed.nationalPopulation;

            maxGdpPerCap = Math.max(maxGdpPerCap, gdpPerCap);
            maxFiscal = Math.max(maxFiscal, fiscalHealth);
            maxInfra = Math.max(maxInfra, s.infrastructure);
            maxPopIndex = Math.max(maxPopIndex, popIndex);
        }

        // Second pass: compute weighted ranking score
        for (StateEconomy s : list) {

            double gdpPerCap = s.gdp / s.population;
            double fiscalHealth =
                    (s.monthlyProfit * 0.5)
                    + (s.stateReserve * 0.3)
                    - (s.debt * 0.05);

            double popIndex = s.population/fed.nationalPopulation;


            // Normalize to 0–1
            double gdpScore = (maxGdpPerCap > 0) ? gdpPerCap / maxGdpPerCap : 0.0;

            double fiscalScore = (Math.abs(maxFiscal) > 1e-9)
                    ? fiscalHealth / maxFiscal
                    : 0.0;

            double infraScore = (maxInfra > 0) ? s.infrastructure / maxInfra : 0.0;

            double popScore = (Math.abs(maxPopIndex) > 1e-9)
                    ? popIndex / maxPopIndex
                    : 0.0;

            double stabilityScore = s.stability / 100.0;

            s.rankingScore =
                    (0.35 * gdpScore) +
                    (0.25 * fiscalScore) +
                    (0.20 * stabilityScore) +
                    (0.10 * infraScore) +
                    (0.10 * popScore);

            if (Double.isNaN(s.rankingScore) || Double.isInfinite(s.rankingScore)) {
                s.rankingScore = -5.0; // hard fallback
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