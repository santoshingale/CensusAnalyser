package censusanalyser;

public class CensusDAO {
    public int areaInSqKm;
    public String state;
    public int population;
    public int densityPerSqKm;
    public String stateCode;
    public int housingUnits;
    public double waterArea;
    public double landArea;

    public CensusDAO(IndiaCensusCSV indiaCensusCSV) {
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        state = indiaCensusCSV.state;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
        population = indiaCensusCSV.population;
    }

    public CensusDAO(USCensusCSV usCensusCSV) {
        stateCode = usCensusCSV.stateCode;
        state = usCensusCSV.state;
        population = usCensusCSV.population;
        housingUnits = usCensusCSV.housingUnits;
        areaInSqKm = (int) usCensusCSV.totalArea;
        waterArea = usCensusCSV.waterArea;
        landArea = usCensusCSV.landArea;
        densityPerSqKm = (int) usCensusCSV.populationDensity;
    }

}
