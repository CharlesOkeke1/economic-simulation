package game;

import game.data.EconomyInitializer;
import game.economies.FederalEconomy;
import game.economies.StateEconomy;
import game.config.GameConfig;
import game.config.GameSetup;
import game.engine.SimulationEngine;

import java.util.HashMap;

public class NigerianEconomyGame {
    public static void main(String[] args) {

        HashMap<String, StateEconomy> states = EconomyInitializer.createStates();
        FederalEconomy fed = EconomyInitializer.createFederal();
        GameConfig config = GameSetup.setup(states);

        SimulationEngine engine = new SimulationEngine(states, fed, config);
        
        engine.run();
    }
}