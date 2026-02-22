package game.economies;
import game.economies.FederalEconomy;
import game.economies.StateEconomy;
import java.util.*;

public class SimulationResult {
    public Map<String, StateEconomy> province;
    public FederalEconomy federation;

    public SimulationResult(Map<String, StateEconomy> province, FederalEconomy federation) {
        this.province = province;
        this.federation = federation;
    }

}