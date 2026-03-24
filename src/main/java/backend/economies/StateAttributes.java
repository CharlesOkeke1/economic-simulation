package economies;

public class StateAttributes {
    private final double infraEfficiency;
    private final double educationEfficiency;
    private final double taxSensitivity;
    private final double securitySensitivity;
    private final double corruptionFactor;
    private final double populationElasticity;  
    
    public StateAttributes(double infraEfficiency, double educationEfficiency, double taxSensitivity,
                            double securitySensitivity, double corruptionFactor, double populationElasticity) {
        this.infraEfficiency = infraEfficiency;
        this.educationEfficiency = educationEfficiency;
        this.taxSensitivity = taxSensitivity;
        this.securitySensitivity = securitySensitivity;
        this.corruptionFactor = corruptionFactor;
        this.populationElasticity = populationElasticity;

    }

    public double getInfraEfficiency() {
        return this.infraEfficiency;
    }

    public double getEducationEfficiency() {
        return this.educationEfficiency;
    }

    public double getTaxSensitivity() {
        return this.taxSensitivity;
    }

    public double getSecuritySensitivity() {
        return this.securitySensitivity;
    }

    public double getCorruptionFactor() {
        return this.corruptionFactor;
    }

    public double getPopulationElasticity() {
        return this.populationElasticity;
    }
}
