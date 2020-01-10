package censusanalyser;

public class IndiaCensusDAO {
    public int areaInSqKm;
    public String state;
    public int population;
    public int densityPerSqKm;

    public IndiaCensusDAO(IndiaCensusCSV indiaCensusCSV) {
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        state = indiaCensusCSV.state;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
        population = indiaCensusCSV.population;
    }
}
