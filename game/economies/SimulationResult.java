package game.economies;
import game.economies.FederalEconomy;
import game.economies.StateEconomy;
import java.util.*;

//This object contains the federal and state economy results each month
public class SimulationResult {
    public Map<String, StateEconomy> province;
    public FederalEconomy federation;

    public SimulationResult(Map<String, StateEconomy> province, FederalEconomy federation) {
        this.province = province;
        this.federation = federation;
    }

}