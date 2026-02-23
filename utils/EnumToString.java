package utils;

import java.util.*;
import game.economies.*;

public class EnumToString {
    public static String govtType(GovType type) {

        StringBuilder sb = new StringBuilder();
        //Take the government type and split it at every undescore into an array 
        for (String word : type.name().split("_")) {
            //For each extract append the first character, followed by the next 
            //in lower case and concatenate each one into the string builder
            sb.append(word.substring(0,1))
            .append(word.substring(1).toLowerCase())
            .append(" ");
        }

        return sb.toString().trim();    
    }
}