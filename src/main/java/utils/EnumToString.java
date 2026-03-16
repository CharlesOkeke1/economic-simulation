package utils;
import java.util.*;


public class EnumToString {
    public static <E extends Enum<E>> String convert(E type, String regex) {
        StringBuilder sb = new StringBuilder();
        //Take the government type and split it at every undescore into an array 
        for (String word : type.name().split(regex)) {
            //For each extract append the first character, followed by the next 
            //in lower case and concatenate each one into the string builder
            sb.append(word.substring(0,1))
            .append(word.substring(1).toLowerCase())
            .append(" ");
        }

        return sb.toString().trim();    
    }
}