package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class USCensusDAO {
    public String stateId;
    public String state;
    public int population;
    public int housingUnits;
    public double totalArea;
    public double waterArea;
    public double landArea;
    public double populationDensity;

    public USCensusDAO(USCensusCSV usCensusCSV) {
        stateId = usCensusCSV.stateId;
        state = usCensusCSV.state;
        population = usCensusCSV.population;
        housingUnits = usCensusCSV.housingUnits;
        totalArea = usCensusCSV.totalArea;
        waterArea = usCensusCSV.waterArea;
        landArea = usCensusCSV.landArea;
        populationDensity = usCensusCSV.populationDensity;
    }
}
