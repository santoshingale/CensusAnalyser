package censusanalyser;

import com.google.gson.Gson;
import java.util.*;
import static java.util.stream.Collectors.toCollection;

public class CensusAnalyser {

    public enum Country {INDIA,US}
    private Country country;
    Map<String,CensusDAO> censusStateMap = new HashMap<>();

    public CensusAnalyser(Country country) {
        this.country = country;
    }

    public int loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        censusStateMap = CensusAdapterFactory.getCensusData(country, csvFilePath);
        return censusStateMap.size();
    }

    public String getSortedDataByStateName() throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CensusAnalyserException("No Cencus Data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        List resultList = censusStateMap.values().stream()
                        .sorted(Comparator.comparing(censusDAO -> censusDAO.state))
                        .map(censusDAO -> censusDAO.getCensusDTO(country))
                        .collect(toCollection(ArrayList::new));
        String jsonString = new Gson().toJson(resultList);
        System.out.println(jsonString);
        return jsonString;
    }

    public String getSortedDataByStateCode() throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CensusAnalyserException( "No State Data",
                    CensusAnalyserException.ExceptionType.NO_STATE_DATA );
        }
        List resultList = censusStateMap.values().stream()
                .sorted(Comparator.comparing(censusDAO -> censusDAO.stateCode))
                .map(censusDAO -> censusDAO.getCensusDTO(country))
                .collect(toCollection(ArrayList::new));
        String jsonString = new Gson().toJson(resultList);
        System.out.println(jsonString);
        return jsonString;
    }

    public String getSortedDataByPopulation() throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CensusAnalyserException("No Cencus Data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        List resultList = censusStateMap.values().stream()
                .sorted(Comparator.comparing(censusDAO -> censusDAO.population))
                .map(censusDAO -> censusDAO.getCensusDTO(country))
                .collect(toCollection(ArrayList::new));
        Collections.reverse(resultList);
        String jsonString = new Gson().toJson(resultList);
        System.out.println(jsonString);
        return jsonString;
    }

    public String getSortedDataByPopulationDensity() throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CensusAnalyserException("No Cencus Data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        List resultList = censusStateMap.values().stream()
                .sorted(Comparator.comparing(censusDAO -> censusDAO.densityPerSqKm))
                .map(censusDAO -> censusDAO.getCensusDTO(country))
                .collect(toCollection(ArrayList::new));
        Collections.reverse(resultList);
        String jsonString = new Gson().toJson(resultList);
        System.out.println(jsonString);
        return jsonString;
    }

    public String getSortedDataByArea() throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CensusAnalyserException("No Cencus Data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        List resultList = censusStateMap.values().stream()
                .sorted(Comparator.comparing(censusDAO -> censusDAO.areaInSqKm))
                .map(censusDAO -> censusDAO.getCensusDTO(country))
                .collect(toCollection(ArrayList::new));
        Collections.reverse(resultList);
        String jsonString = new Gson().toJson(resultList);
        System.out.println(jsonString);
        return jsonString;
    }
}
