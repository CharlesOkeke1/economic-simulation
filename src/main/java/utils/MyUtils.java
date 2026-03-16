package utils;
import java.util.*;

public class MyUtils {

    /*Function that takes a string and a number and prints each character
    one by one with a delay of the number specified which ic time in milliseconds*/
    public static void SteppedPrinting(String str, int time) {
		String[] list = str.split("");
		int lastIdx = list.length - 1;

		/*Display the message with a delay using thread.sleep() */
		for (int i = 0; i < list.length; i++) {
			if (list[i] == list[lastIdx]) {
				System.out.println(list[i]);
			} else {
				System.out.print(list[i]);
				try {
					// Pause for specified time in milliseconds
					Thread.sleep(time); 
				} catch (InterruptedException e) {
					// Handle the interruption if needed
					Thread.currentThread().interrupt();
					System.err.println("Thread was interrupted.");
				}
			}
		}

	}

    /* Function to turn cardinal numbers into their ordinal counterparts */
	public static String Ordinalize(int number) {
		if ((number % 100 >= 11) && (number % 100 <= 13)) return number + "th";

		switch (number % 10) {
			case 1: return number + "st";
			case 2: return number + "nd";
			case 3: return number + "rd";
			default: return number + "th";
		}	
	}
    /* ==This is an example of how to use a custom object class to store 
    variables of different types together
    public static class Example {
        type var1;
        type var2;
        type var3;

        public SpinResult(type var1, type var2, type var3) {
            this.var1 = var1;
            this.var2 = var2;
            this.var3 = var3;
        }
    }
    
    return new Example(var1, var2, var3);

    == USAGE ==
    Example usage1 = function(param);
    */
    /*Take a number and return it with its coefficient */
	public static String formatNumber(double value) {

		if (value == 0) return "0.00";

		String[] units = {
			"", " K", " M", " B",
			" T", " Q", " Quintillion",
			" Sextillion", " Septillion", " Octillion",
			" Nonillion", "Decillion", "UnDecillion", "DuoDecillion"
		};

		double absValue = Math.abs(value);
		int unitIndex = 0;

		while (absValue >= 1000.0 && unitIndex < units.length - 1) {
			absValue /= 1000.0;
			unitIndex++;
		}

		String formatted = String.format("%.2f", absValue);

		return (value < 0 ? "-" : "") + formatted + units[unitIndex];
	}

}