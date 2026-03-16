package game.ui;
import utils.MyUtils;
import game.data.Constants;

public class Initializer {
    /*Function to display initial information to the user*/	
	public static void initializer() {
        String Welcome = "===== WELCOME TO THE NIGERIAN FEDERATION'S ECONOMIC DASHBOARD =====";
        String task = "Your task is to pick a state and manage the state over a period of months that you choose. The more months you choose, the more months you work and so I advise you to not choose too much. This is not an easy task as you will be analysing a lot of information. Precisely about 20 variables.";
        String example = "For example, say you are the governor of Zamfara state, you will be managing information such as:";
        String[] zamfaraExample = {"===== Zamfara Month 1 Economic Statistics =====", "Debt: 1.2Bn Naira", "Operating Cash: 19.5Bn Naira", "Population: 5.8 Mn", "Monthly Revenue: 8.72 Bn Naira", "Number of Economic crashes: 3 etc."};
        MyUtils.SteppedPrinting(Welcome, 50);
        MyUtils.SteppedPrinting(task, Constants.REPORT_DELAY_TIME);
        MyUtils.SteppedPrinting(example, Constants.REPORT_DELAY_TIME);

        for (int i = 0; i < zamfaraExample.length; i++) {
            MyUtils.SteppedPrinting(zamfaraExample[i], Constants.REPORT_DELAY_TIME);
        }

        MyUtils.SteppedPrinting("Good luck!", Constants.REPORT_DELAY_TIME);  

        
    }
}