package game.analytics;

import java.time.LocalDateTime; // Import the LocalDateTime class
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class
import game.economies.StateEconomy;
import game.economies.FederalEconomy;
import game.gui.AppMain;
import utils.MyUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class Telemetry {

    private static final LocalDateTime time = LocalDateTime.now();
    private static final DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
    private static final String formattedTime = time.format(myFormatObj);
    private static final String FILE = "log/" + AppMain.getConfig().getChosenState() + "_" + formattedTime + ".json";
    private static boolean firstEntry = true;

    public static void logMonth(int month,
                                Map<String, StateEconomy> states,
                                FederalEconomy federal) {

        try (FileWriter writer = new FileWriter(FILE, true)) {


            if (firstEntry) {
                writer.write("[\n");
                firstEntry = false;
            } else {
                writer.write(",\n");
            }

            writer.write("{\n");
            writer.write("\"STATE\": {\n");

            int count = 0;
            for (StateEconomy s : states.values()) {

                if (s.name.equals(AppMain.getConfig().getChosenState())) {

                    writer.write("\"" + s.name.toUpperCase() + "\": {\n");
                    writer.write("\"Month\": " + month + ",\n");
                    writer.write("\"GDP\": " + MyUtils.formatNumber(s.gdp) + ",\n");
                    writer.write("\"Real Gdp\": " + MyUtils.formatNumber(s.realGdp) + ",\n");
                    writer.write("\"Growth\": " + MyUtils.formatNumber(s.gdpGrowth) + ",\n");
                    writer.write("\"Inflation\": " + MyUtils.formatNumber(s.inflationRate) + ",\n");
                    writer.write("\"Tax Rate\": " + MyUtils.formatNumber(s.taxRate) + ",\n");
                    writer.write("\"Stability\": " + MyUtils.formatNumber(s.stability) + ",\n");
                    writer.write("\"Debt\": " + MyUtils.formatNumber(s.debt) + ",\n");
                    writer.write("\"Cash\": " + MyUtils.formatNumber(s.cash) + ",\n");
                    writer.write("\"Population\": " + MyUtils.formatNumber(s.population) + ",\n");
                    writer.write("\"Infrastructure\": " + MyUtils.formatNumber(s.infrastructure) + "\n");
                    writer.write("}");

                    count++;
                    if (count < states.size()) writer.write(",");
                    writer.write("\n");
                }
            }

            writer.write("},\n");

            writer.write("\"Federal Reserve\": " + MyUtils.formatNumber(federal.federalReserve) + ",\n");
            writer.write("\"Federal Operating Cash\": " + MyUtils.formatNumber(federal.operatingCash) + "\n");
            writer.write("\"Federal Crash Count\": " + MyUtils.formatNumber(federal.nationalCrashes) + "\n");
            writer.write("\"Federal GDP\": " + MyUtils.formatNumber(federal.nationalGDP) + "\n");

            writer.write("}");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        try (FileWriter writer = new FileWriter(FILE, true)) {
            writer.write("\n]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}