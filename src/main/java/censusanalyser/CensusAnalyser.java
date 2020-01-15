package censusanalyser;

import com.google.gson.Gson;
import csvbuilder.*;
import java.io.IOException;
import java.util.*;

import static java.util.stream.Collectors.toCollection;

public class CensusAnalyser {

    public enum Country {INDIA,US}

    Map<String,CensusDAO> censusStateMap = new HashMap<>();

    public int loadCensusData(Country country, String... csvFilePath) throws CensusAnalyserException {
        censusStateMap = CensusAdapterFactory.getCensusData(country, csvFilePath);
        return censusStateMap.size();
    }

    public String getSortedDataByStateName() throws IOException, CSVBuilderException, CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CensusAnalyserException("No Cencus Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        List<CensusDAO> resultList = censusStateMap.values().stream()
                        .sorted(Comparator.comparing(census -> census.state))
                        .collect(toCollection(ArrayList::new));
        String jsonString = new Gson().toJson(resultList);
        System.out.println(jsonString);
        return jsonString;
    }

    public String getSortedDataByStateCode() throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CensusAnalyserException( "No State Data", CensusAnalyserException.ExceptionType.NO_STATE_DATA );
        }
        //Comparator<CensusDAO> csvComparator = ( a, b ) -> (( a.stateCode.compareTo( b.stateCode )) < 0 ) ? -1 : 1;
        List<CensusDAO> resultList = censusStateMap.values().stream()
                .sorted(Comparator.comparing(census -> census.stateCode))
                .collect(toCollection(ArrayList::new));
        String jsonString = new Gson().toJson(resultList);
        System.out.println(jsonString);
        return jsonString;
    }

    public String getSortedDataByPopulation() throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CensusAnalyserException("No Cencus Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        List<CensusDAO> resultList = censusStateMap.values().stream()
                .collect(toCollection(ArrayList::new));
        Comparator<CensusDAO> censusCSVComparator = (a, b ) -> (( a.population - b.population) > 0 ) ? -1 : 1;
        Collections.sort(resultList, censusCSVComparator);
        String jsonString = new Gson().toJson(resultList);
        System.out.println(jsonString);
        return jsonString;
    }

    public String getSortedDataByPopulationDensity() throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CensusAnalyserException("No Cencus Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        List<CensusDAO> resultList = censusStateMap.values()
                .stream()
                .collect(toCollection(ArrayList::new));
        Comparator<CensusDAO> censusCSVComparator = (a, b ) -> (( a.densityPerSqKm - b.densityPerSqKm) > 0 ) ? -1 : 1;
        Collections.sort(resultList, censusCSVComparator);
        String jsonString = new Gson().toJson(resultList);
        System.out.println(jsonString);
        return jsonString;
    }

    public String getSortedDataByArea() throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CensusAnalyserException("No Cencus Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        List<CensusDAO> resultList = censusStateMap.values().stream()
                .collect(toCollection(ArrayList::new));
        Comparator<CensusDAO> censusCSVComparator = (a, b ) -> (( a.areaInSqKm - b.areaInSqKm) > 0 ) ? -1 : 1;
        Collections.sort(resultList, censusCSVComparator);
        String jsonString = new Gson().toJson(resultList);
        System.out.println(jsonString);
        return jsonString;
    }
}
