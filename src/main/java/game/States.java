/* THESE ARE THE AVAILABLE STATES WITH REAL WORLD SCALED DOWN VALUES FOR EASE OF SIMULATION
        states.put("Lagos", new StateEconomy("Lagos",10001000.0,22000,65.0,0.29,3600000.0,0.0,1000100.0,0,22,
        0.0,0.0,0.0,0.0,0.0,0.360f,0.0,0,0.0,0.0,GovType.GROWTH_FOCUSED));

        states.put("Rivers", new StateEconomy("Rivers",1972000.0,70000,58.0,0.26,640000.0,0.0,197200.0,0,19,
        0.0,0.0,0.0,0.0,0.0,0.325f,0.0,0,0.0,0.0,GovType.GROWTH_FOCUSED));

        states.put("Kano", new StateEconomy("Kano",1040000.0,14000,52.0,0.21,360000.0,0.0,104000.0,0,17,
        0.0,0.0,0.0,0.0,0.0,0.346f,0.0,0,0.0,0.0,GovType.GROWTH_FOCUSED));

        states.put("Imo", new StateEconomy("Imo",1902000.0,54000,49.0,0.24,840000.0,0.0,190200.0,0,16,
        0.0,0.0,0.0,0.0,0.0,0.442f,0.0,0,0.0,0.0,GovType.SOCIALIST));

        states.put("Enugu", new StateEconomy("Enugu",972000.0,50000,54.0,0.22,340000.0,0.0,97200.0,0,16,
        0.0,0.0,0.0,0.0,0.0,0.350f,0.0,0,0.0,0.0,GovType.SOCIALIST));

        states.put("Oyo", new StateEconomy("Oyo",911000.0,82500,55.0,0.23,280000.0,0.0,91100.0,0,18,
        0.0,0.0,0.0,0.0,0.0,0.307f,0.0,0,0.0,0.0,GovType.SOCIALIST));

        states.put("Katsina", new StateEconomy("Katsina",822000.0,93000,44.0,0.19,240000.0,0.0,82200.0,0,14,
        0.0,0.0,0.0,0.0,0.0,0.292f,0.0,0,0.0,0.0,GovType.SECURITY_ORIENTED));

        states.put("Benue", new StateEconomy("Benue",1058000.0,50000,48.0,0.20,360000.0,0.0,105800.0,0,15,
        0.0,0.0,0.0,0.0,0.0,0.340f,0.0,0,0.0,0.0,GovType.SOCIALIST));

        states.put("Anambra", new StateEconomy("Anambra",1273000.0,53000,60.0,0.27,380000.0,0.0,127300.0,0,20,
        0.0,0.0,0.0,0.0,0.0,0.299f,0.0,0,0.0,0.0,GovType.GROWTH_FOCUSED));

        states.put("Delta", new StateEconomy("Delta",6190000.0,41000,59.0,0.25,2080000.0,0.0,619000.0,0,21,
        0.0,0.0,0.0,0.0,0.0,0.336f,0.0,0,0.0,0.0,GovType.GROWTH_FOCUSED));

        states.put("Ogun", new StateEconomy("Ogun",5030000.0,36000,63.0,0.28,1720000.0,0.0,503000.0,0,21,
        0.0,0.0,0.0,0.0,0.0,0.342f,0.0,0,0.0,0.0,GovType.GROWTH_FOCUSED));

        states.put("Bayelsa", new StateEconomy("Bayelsa",4630000.0,24000,57.0,0.24,1560000.0,0.0,463000.0,0,18,
        0.0,0.0,0.0,0.0,0.0,0.337f,0.0,0,0.0,0.0,GovType.DEBT_FIGHTER));

        states.put("Niger", new StateEconomy("Niger",4580000.0,62000,50.0,0.22,1400000.0,0.0,458000.0,0,17,
        0.0,0.0,0.0,0.0,0.0,0.306f,0.0,0,0.0,0.0,GovType.DEBT_FIGHTER));

        states.put("Kaduna", new StateEconomy("Kaduna",4310000.0,80000,53.0,0.23,1560000.0,0.0,431000.0,0,18,
        0.0,0.0,0.0,0.0,0.0,0.362f,0.0,0,0.0,0.0,GovType.SOCIALIST));

        states.put("Sokoto", new StateEconomy("Sokoto",2850000.0,49000,45.0,0.18,840000.0,0.0,285000.0,0,14,
        0.0,0.0,0.0,0.0,0.0,0.295f,0.0,0,0.0,0.0,GovType.SECURITY_ORIENTED));

        states.put("Abia", new StateEconomy("Abia",820000.0,42000,52.0,0.21,320000.0,0.0,82000.0,0,16,
        0.0,0.0,0.0,0.0,0.0,0.390f,0.0,0,0.0,0.0,GovType.FISCAL_CONSERVATIVE));

        states.put("Adamawa", new StateEconomy("Adamawa",780000.0,39000,47.0,0.19,280000.0,0.0,78000.0,0,14,
        0.0,0.0,0.0,0.0,0.0,0.359f,0.0,0,0.0,0.0,GovType.DEBT_FIGHTER));

        states.put("Akwa Ibom", new StateEconomy("Akwa Ibom",5400000.0,55000,61.0,0.27,1800000.0,0.0,540000.0,0,20,
        0.0,0.0,0.0,0.0,0.0,0.333f,0.0,0,0.00.0,GovType.GROWTH_FOCUSED));

        states.put("Bauchi", new StateEconomy("Bauchi",750000.0,60000,46.0,0.18,260000.0,0.0,75000.0,0,13,
        0.0,0.0,0.0,0.0,0.0,0.347f,0.0,0,0.0,0.0,GovType.SECURITY_ORIENTED));

        states.put("Borno", new StateEconomy("Borno",690000.0,58000,35.0,0.17,300000.0,0.0,69000.0,0,10,
        0.0,0.0,0.0,0.0,0.0,0.435f,0.0,0,0.0,0.0,GovType.SECURITY_ORIENTED));

        states.put("Cross River", new StateEconomy("Cross River",890000.0,35000,50.0,0.20,320000.0,0.0,89000.0,0,15,
        0.0,0.0,0.0,0.0,0.0,0.360f,0.0,0,0.0,0.0,GovType.SOCIALIST));

        states.put("Ebonyi", new StateEconomy("Ebonyi",620000.0,29000,49.0,0.19,200000.0,0.0,62000.0,0,14,
        0.0,0.0,0.0,0.0,0.0,0.323f,0.0,0,0.0,0.0,GovType.FISCAL_CONSERVATIVE));

        states.put("Edo", new StateEconomy("Edo",1700000.0,43000,56.0,0.23,600000.0,0.0,170000.0,0,18,
        0.0,0.0,0.0,0.0,0.0,0.353f,0.0,0,0.0,0.0,GovType.FISCAL_CONSERVATIVE));

        states.put("Ekiti", new StateEconomy("Ekiti",610000.0,28000,55.0,0.22,200000.0,0.0,61000.0,0,15,
        0.0,0.0,0.0,0.0,0.0,0.328f,0.0,0,0.0,0.0,GovType.FISCAL_CONSERVATIVE));

        states.put("Gombe", new StateEconomy("Gombe",700000.0,30000,48.0,0.18,240000.0,0.0,70000.0,0,13,
        0.0,0.0,0.0,0.0,0.0,0.343f,0.0,0,0.0,0.0,GovType.SECURITY_ORIENTED));

        states.put("Jigawa", new StateEconomy("Jigawa",680000.0,56000,44.0,0.18,220000.0,0.0,68000.0,0,12,
        0.0,0.0,0.0,0.0,0.0,0.324f,0.0,0,0.0,0.0,GovType.SECURITY_ORIENTED));

        states.put("Kebbi", new StateEconomy("Kebbi",640000.0,42000,46.0,0.19,200000.0,0.0,64000.0,0,13,
        0.0,0.0,0.0,0.0,0.0,0.313f,0.0,0,0.0,0.0,GovType.DEBT_FIGHTER));

        states.put("Kogi", new StateEconomy("Kogi",900000.0,39000,50.0,0.20,320000.0,0.0,90000.0,0,16,
        0.0,0.0,0.0,0.0,0.0,0.356f,0.0,0,0.0,0.0,GovType.FISCAL_CONSERVATIVE));

        states.put("Kwara", new StateEconomy("Kwara",820000.0,31000,54.0,0.21,260000.0,0.0,82000.0,0,16,
        0.0,0.0,0.0,0.0,0.0,0.317f,0.0,0,0.0,0.0,GovType.FISCAL_CONSERVATIVE));

        states.put("Nasarawa", new StateEconomy("Nasarawa",740000.0,28000,51.0,0.20,240000.0,0.0,74000.0,0,15,
        0.0,0.0,0.0,0.0,0.0,0.324f,0.0,0,0.0,0.0,GovType.FISCAL_CONSERVATIVE));

        states.put("Ondo", new StateEconomy("Ondo",1200000.0,41000,58.0,0.24,400000.0,0.0,120000.0,0,18,
        0.0,0.0,0.0,0.0,0.0,0.333f,0.0,0,0.0,0.0,GovType.DEBT_FIGHTER));

        states.put("Osun", new StateEconomy("Osun", 980000.0,35000,56.0,0.22,300000.0,0.0,98000.0,0,17,
        0.0,0.0,0.0,0.0,0.0,0.306f,0.0,0,0.0,0.0,GovType.FISCAL_CONSERVATIVE));

        states.put("Plateau", new StateEconomy("Plateau",850000.0,32000,50.0,0.21,260000.0,0.0,85000.0,0,15,
        0.0,0.0,0.0,0.0,0.0,0.306f,0.0,0,0.0,0.0,GovType.SOCIALIST));

        states.put("Taraba", new StateEconomy("Taraba",600000.0,24000,45.0,0.18,180000.0,0.0,60000.0,0,12,
        0.0,0.0,0.0,0.0,0.0,0.300f,0.0,0,0.0,0.0,GovType.DEBT_FIGHTER));

        states.put("Yobe", new StateEconomy("Yobe",550000.0,30000,38.0,0.17,180000.0,0.0,55000.0,0,11,
        0.0,0.0,0.0,0.0,0.0,0.327f,0.0,0,0.0,0.0,GovType.SECURITY_ORIENTED));

        states.put("Zamfara", new StateEconomy("Zamfara",580000.0,35000,37.0,0.17,200000.0,0.0,58000.0,0,11,
        0.0,0.0,0.0,0.0,0.0,0.345f,0.0,0,0.0,0.0,GovType.SECURITY_ORIENTED));

        states.put("Abuja", new StateEconomy("Abuja",6500000.0,45000,70.0,0.30,2200000.0,0.0,650000.0,0,23,
        0.0,0.0,0.0,0.0,0.0,0.338f,0.0,0,0.0,0.0,GovType.GROWTH_FOCUSED));
 */