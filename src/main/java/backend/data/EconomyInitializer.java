package data;

import java.util.*;
import economies.*;
import events.*;

public class EconomyInitializer {

    public static HashMap<String, StateEconomy> createStates() {

        HashMap<String, StateEconomy> states = new HashMap<>();

        EventRisk risks = new EventRisk();
        // ================= SOUTH WEST =================

        StateAttributes lagosAttr = new StateAttributes(1.25,1.18,0.95,0.96,0.13,1.06);
        states.put("Lagos", new StateEconomy("Lagos",
                11000000,11000000,200000,72,0.32,1800000,0,950000,0,25,
                0,0,0,0,0,0.16,0,0,0,0,GovType.GROWTH_FOCUSED,0.018,lagosAttr, risks, ""));

        StateAttributes ogunAttr = new StateAttributes(1.20,1.10,0.98,0.94,0.15,1.04);
        states.put("Ogun", new StateEconomy("Ogun",
                3200000,3200000,70000,65,0.27,420000,0,220000,0,20,
                0,0,0,0,0,0.13,0,0,0,0,GovType.GROWTH_FOCUSED,0.021,ogunAttr, risks, ""));

        StateAttributes oyoAttr = new StateAttributes(1.16,1.12,1.02,0.93,0.16,1.05);
        states.put("Oyo", new StateEconomy("Oyo",
                3800000,3800000,75000,64,0.26,500000,0,240000,0,19,
                0,0,0,0,0,0.13,0,0,0,0,GovType.SOCIALIST,0.020,oyoAttr, risks, ""));

        StateAttributes osunAttr = new StateAttributes(1.07,1.09,1.01,0.94,0.16,1.02);
        states.put("Osun", new StateEconomy("Osun",
                1400000,1400000,40000,58,0.23,220000,0,120000,0,16,
                0,0,0,0,0,0.15,0,0,0,0,GovType.FISCAL_CONSERVATIVE,0.024,osunAttr, risks, ""));

        StateAttributes ondoAttr = new StateAttributes(1.08,1.06,0.945,0.92,0.17,1.03);
        states.put("Ondo", new StateEconomy("Ondo",
                1800000,1800000,50000,60,0.24,250000,0,140000,0,17,
                0,0,0,0,0,0.14,0,0,0,0,GovType.DEBT_FIGHTER,0.022,ondoAttr, risks, ""));

        StateAttributes ekitiAttr = new StateAttributes(1.06,1.12,1.0325,0.95,0.16,1.01);
        states.put("Ekiti", new StateEconomy("Ekiti",
                900000,900000,30000,61,0.22,150000,0,80000,0,14,
                0,0,0,0,0,0.17,0,0,0,0,GovType.FISCAL_CONSERVATIVE,0.025,ekitiAttr, risks, ""));

        // ================= SOUTH SOUTH =================

        StateAttributes riversAttr = new StateAttributes(1.15,1.10,1.025,0.90,0.15,1.05);
        states.put("Rivers", new StateEconomy("Rivers",
                4500000,4500000,75000,66,0.28,600000,0,350000,0,21,
                0,0,0,0,0,0.13,0,0,0,0,GovType.GROWTH_FOCUSED,0.023,riversAttr, risks, ""));

        StateAttributes deltaAttr = new StateAttributes(1.14,1.08,0.955,0.90,0.20,1.04);
        states.put("Delta", new StateEconomy("Delta",
                3900000,3900000,55000,63,0.27,520000,0,300000,0,20,
                0,0,0,0,0,0.13,0,0,0,0,GovType.GROWTH_FOCUSED,0.024,deltaAttr, risks, ""));

        StateAttributes akwaAttr = new StateAttributes(1.12,1.05,1.01,0.93,0.17,1.02);
        states.put("Akwa Ibom", new StateEconomy("Akwa Ibom",
                3000000,3000000,55000,65,0.26,450000,0,250000,0,19,
                0,0,0,0,0,0.15,0,0,0,0,GovType.GROWTH_FOCUSED,0.022,akwaAttr, risks, ""));

        StateAttributes bayelsaAttr = new StateAttributes(1.10,1.02,0.98,0.88,0.21,1.06);
        states.put("Bayelsa", new StateEconomy("Bayelsa",
                1200000,1200000,20000,58,0.25,210000,0,110000,0,16,
                0,0,0,0,0,0.18,0,0,0,0,GovType.DEBT_FIGHTER,0.026,bayelsaAttr, risks, ""));

        StateAttributes edoAttr = new StateAttributes(1.08,1.05,0.97,0.92,0.18,1.04);
        states.put("Edo", new StateEconomy("Edo",
                2100000,2100000,45000,61,0.24,300000,0,160000,0,18,
                0,0,0,0,0,0.14,0,0,0,0,GovType.FISCAL_CONSERVATIVE,0.023,edoAttr, risks, ""));

        StateAttributes crossAttr = new StateAttributes(1.07,1.04,0.945,0.91,0.18,1.05);
        states.put("Cross River", new StateEconomy("Cross River",
                1100000,1100000,40000,57,0.22,190000,0,95000,0,15,
                0,0,0,0,0,0.17,0,0,0,0,GovType.SOCIALIST,0.025,crossAttr, risks, ""));

        // ================= SOUTH EAST =================

        StateAttributes anambraAttr = new StateAttributes(1.18,1.15,0.93,0.94,0.14,1.04);
        states.put("Anambra", new StateEconomy("Anambra",
                3500000,3500000,60000,70,0.30,400000,0,260000,0,21,
                0,0,0,0,0,0.11,0,0,0,0,GovType.GROWTH_FOCUSED,0.019,anambraAttr, risks, ""));

        StateAttributes imoAttr = new StateAttributes(1.09,1.11,0.967,0.91,0.17,1.03);
        states.put("Imo", new StateEconomy("Imo",
                2000000,2000000,55000,60,0.24,280000,0,150000,0,17,
                0,0,0,0,0,0.14,0,0,0,0,GovType.SOCIALIST,0.023,imoAttr, risks, ""));

        StateAttributes enuguAttr = new StateAttributes(1.10,1.14,1.04,0.93,0.15,1.03);
        states.put("Enugu", new StateEconomy("Enugu",
                2300000,2300000,45000,63,0.25,300000,0,170000,0,18,
                0,0,0,0,0,0.13,0,0,0,0,GovType.SOCIALIST,0.022,enuguAttr, risks, ""));

        StateAttributes abiaAttr = new StateAttributes(1.05,1.08,1.02,0.92,0.18,1.03);
        states.put("Abia", new StateEconomy("Abia",
                1800000,1800000,40000,58,0.23,260000,0,140000,0,16,
                0,0,0,0,0,0.14,0,0,0,0,GovType.FISCAL_CONSERVATIVE,0.024,abiaAttr, risks, ""));

        StateAttributes ebonyiAttr = new StateAttributes(0.97,0.93,1.01,0.89,0.21,1.07);
        states.put("Ebonyi", new StateEconomy("Ebonyi",
                900000,900000,30000,55,0.21,150000,0,75000,0,14,
                0,0,0,0,0,0.17,0,0,0,0,GovType.FISCAL_CONSERVATIVE,0.026,ebonyiAttr, risks, ""));

        // ================= NORTH WEST =================

        StateAttributes kanoAttr = new StateAttributes(1.15,1.00,0.985,0.87,0.21,1.12);
        states.put("Kano", new StateEconomy("Kano",
                4200000,4200000,140000,63,0.24,52000,0,260000,0,19,
                0,0,0,0,0,0.12,0,0,0,0,GovType.GROWTH_FOCUSED,0.022,kanoAttr, risks, ""));

        StateAttributes kadunaAttr = new StateAttributes(1.12,1.06,0.995,0.88,0.19,1.05);
        states.put("Kaduna", new StateEconomy("Kaduna",
                3000000,3000000,90000,60,0.23,450000,0,220000,0,18,
                0,0,0,0,0,0.15,0,0,0,0,GovType.SOCIALIST,0.024,kadunaAttr, risks, ""));

        StateAttributes katsinaAttr = new StateAttributes(0.94,0.89,0.972,0.82,0.22,1.13);
        states.put("Katsina", new StateEconomy("Katsina",
                1200000,1200000,80000,52,0.19,200000,0,90000,0,15,
                0,0,0,0,0,0.17,0,0,0,0,GovType.SECURITY_ORIENTED,0.028,katsinaAttr, risks, ""));

        StateAttributes kebbiAttr = new StateAttributes(0.96,0.90,0.935,0.88,0.20,1.10);
        states.put("Kebbi", new StateEconomy("Kebbi",
                1000000,1000000,60000,53,0.20,180000,0,85000,0,14,
                0,0,0,0,0,0.18,0,0,0,0,GovType.DEBT_FIGHTER,0.027,kebbiAttr, risks, ""));

        StateAttributes sokotoAttr = new StateAttributes(0.93,0.87,1.02,0.91,0.18,1.12);
        states.put("Sokoto", new StateEconomy("Sokoto",
                1100000,1100000,55000,54,0.20,190000,0,90000,0,14,
                0,0,0,0,0,0.17,0,0,0,0,GovType.SECURITY_ORIENTED,0.027,sokotoAttr, risks, ""));

        StateAttributes zamfaraAttr = new StateAttributes(0.91,0.85,1.00,0.78,0.24,1.15);
        states.put("Zamfara", new StateEconomy("Zamfara",
                900000,900000,50000,50,0.18,170000,0,75000,0,13,
                0,0,0,0,0,0.19,0,0,0,0,GovType.SECURITY_ORIENTED,0.030,zamfaraAttr, risks, ""));

        StateAttributes jigawaAttr = new StateAttributes(0.95,0.88,1.03,0.90,0.19,1.11);
        states.put("Jigawa", new StateEconomy("Jigawa",
                1000000,1000000,65000,55,0.20,180000,0,85000,0,14,
                0,0,0,0,0,0.18,0,0,0,0,GovType.SECURITY_ORIENTED,0.027,jigawaAttr, risks, ""));

        // ================= NORTH EAST =================

        StateAttributes bornoAttr = new StateAttributes(0.90,0.85,1.00,0.75,0.23,1.12);
        states.put("Borno", new StateEconomy("Borno",
                1200000,1200000,70000,48,0.18,220000,0,90000,0,12,
                0,0,0,0,0,0.18,0,0,0,0,GovType.SECURITY_ORIENTED,0.032,bornoAttr, risks, ""));

        StateAttributes yobeAttr = new StateAttributes(0.92,0.86,1.01,0.80,0.23,1.14);
        states.put("Yobe", new StateEconomy("Yobe",
                800000,800000,45000,50,0.18,160000,0,70000,0,12,
                0,0,0,0,0,0.20,0,0,0,0,GovType.SECURITY_ORIENTED,0.031,yobeAttr, risks, ""));

        StateAttributes adamawaAttr = new StateAttributes(0.98,0.95,0.96,0.85,0.20,1.08);
        states.put("Adamawa", new StateEconomy("Adamawa",
                1100000,1100000,60000,53,0.19,200000,0,85000,0,14,
                0,0,0,0,0,0.18,0,0,0,0,GovType.DEBT_FIGHTER,0.029,adamawaAttr, risks, ""));

        StateAttributes gombeAttr = new StateAttributes(0.99,0.94,0.98,0.86,0.20,1.09);
        states.put("Gombe", new StateEconomy("Gombe",
                900000,900000,40000,54,0.20,170000,0,75000,0,13,
                0,0,0,0,0,0.19,0,0,0,0,GovType.SECURITY_ORIENTED,0.028,gombeAttr, risks, ""));

        StateAttributes tarabaAttr = new StateAttributes(0.97,0.92,0.945,0.86,0.20,1.10);
        states.put("Taraba", new StateEconomy("Taraba",
                800000,800000,35000,52,0.19,150000,0,70000,0,13,
                0,0,0,0,0,0.19,0,0,0,0,GovType.DEBT_FIGHTER,0.029,tarabaAttr, risks, ""));

        StateAttributes bauchiAttr = new StateAttributes(0.96,0.90,1.035,0.84,0.22,1.10);
        states.put("Bauchi", new StateEconomy("Bauchi",
                1400000,1400000,80000,55,0.20,230000,0,95000,0,15,
                0,0,0,0,0,0.16,0,0,0,0,GovType.SECURITY_ORIENTED,0.028,bauchiAttr, risks, ""));

        // ================= NORTH CENTRAL =================

        StateAttributes nigerAttr = new StateAttributes(1.03,0.97,0.94,0.89,0.18,1.08);
        states.put("Niger", new StateEconomy("Niger",
                1500000,1500000,65000,58,0.22,250000,0,110000,0,16,
                0,0,0,0,0,0.16,0,0,0,0,GovType.DEBT_FIGHTER,0.026,nigerAttr, risks, ""));

        StateAttributes benueAttr = new StateAttributes(1.00,0.97,0.985,0.87,0.19,1.09);
        states.put("Benue", new StateEconomy("Benue",
                1300000,1300000,60000,56,0.21,210000,0,100000,0,15,
                0,0,0,0,0,0.16,0,0,0,0,GovType.SOCIALIST,0.027,benueAttr, risks, ""));

        StateAttributes plateauAttr = new StateAttributes(1.04,1.00,0.955,0.88,0.19,1.06);
        states.put("Plateau", new StateEconomy("Plateau",
                1200000,1200000,50000,57,0.21,200000,0,95000,0,15,
                0,0,0,0,0,0.17,0,0,0,0,GovType.SOCIALIST,0.026,plateauAttr, risks, ""));

        StateAttributes kwaraAttr = new StateAttributes(1.05,1.03,1.025,0.93,0.17,1.04);
        states.put("Kwara", new StateEconomy("Kwara",
                1000000,1000000,40000,60,0.22,180000,0,90000,0,14,
                0,0,0,0,0,0.18,0,0,0,0,GovType.FISCAL_CONSERVATIVE,0.025,kwaraAttr, risks, ""));

        StateAttributes kogiAttr = new StateAttributes(1.02,0.98,1.03,0.89,0.19,1.08);
        states.put("Kogi", new StateEconomy("Kogi",
                1200000,1200000,45000,58,0.21,200000,0,95000,0,15,
                0,0,0,0,0,0.17,0,0,0,0,GovType.FISCAL_CONSERVATIVE,0.026,kogiAttr, risks, ""));

        StateAttributes nasarawaAttr = new StateAttributes(1.00,0.96,1.02,0.90,0.18,1.07);
        states.put("Nasarawa", new StateEconomy("Nasarawa",
                900000,900000,30000,59,0.21,170000,0,80000,0,14,
                0,0,0,0,0,0.19,0,0,0,0,GovType.FISCAL_CONSERVATIVE,0.025,nasarawaAttr, risks, ""));

        StateAttributes fctAttr = new StateAttributes(1.22,1.15,1.04,0.97,0.12,1.02);
        states.put("Abuja", new StateEconomy("Abuja",
                3000000,3000000,40000,75,0.31,350000,0,220000,0,23,
                0,0,0,0,0,0.12,0,0,0,0,GovType.GROWTH_FOCUSED,0.019,fctAttr, risks, ""));

        return states;
    }

    public static FederalEconomy createFederal() {
        return new FederalEconomy(0,1068,0,0,0,0,0,0,0,0,0, 0.1);
    }
}