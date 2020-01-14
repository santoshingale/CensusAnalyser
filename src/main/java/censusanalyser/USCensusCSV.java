package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV {

    @CsvBindByName(column = "State Id", required = true)
    public String stateCode;

    @CsvBindByName(column = "State", required = true)
    public String state;

    @CsvBindByName(column = "Population", required = true)
    public int population;

    @CsvBindByName(column = "Housing units", required = true)
    public int housingUnits;

    @CsvBindByName(column = "Total area", required = true)
    public double totalArea;
    //State Id,State,Population,Housing units,Total area,Water area,Land area,Population Density,Housing Density
    @CsvBindByName(column = "Water area", required = true)
    public double waterArea;

    @CsvBindByName(column = "Land area", required = true)
    public double landArea;

    @CsvBindByName(column = "Population Density", required = true)
    public double populationDensity;

    @CsvBindByName(column = "Housing Density", required = true)
    public double housingDensity;

    @Override
    public String toString() {
        return "USCensusCSV{" +
                "stateId='" + stateCode + '\'' +
                ", state='" + state + '\'' +
                ", population=" + population +
                ", housingUnits=" + housingUnits +
                ", totalArea=" + totalArea +
                ", waterArea=" + waterArea +
                ", landArea=" + landArea +
                ", populationDensity=" + populationDensity +
                ", housingDensity=" + housingDensity +
                '}';
    }
}

