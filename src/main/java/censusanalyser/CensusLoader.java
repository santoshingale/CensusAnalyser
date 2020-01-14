package censusanalyser;

import csvbuilder.CSVBuilderException;
import csvbuilder.CSVBuilderFactory;
import csvbuilder.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.StreamSupport;

public class CensusLoader {



    public <E> Map<String, CensusDAO> loadCensusData(Class<E> censusCSVClass, String... csvFilePath) throws CensusAnalyserException {

        Map<String, CensusDAO> censusStateMap = new TreeMap<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<E> listCSVFile = csvBuilder.getListCSVFile(reader, censusCSVClass);

            if (censusCSVClass.getName().equals("censusanalyser.IndiaCensusCSV")) {
                StreamSupport.stream(listCSVFile.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusData -> censusStateMap.put(censusData.state, new CensusDAO(censusData)));
            } else if (censusCSVClass.getName().equals("censusanalyser.USCensusCSV")) {
                StreamSupport.stream(listCSVFile.spliterator(), false)
                        .map(USCensusCSV.class::cast)
                        .forEach(censusData -> censusStateMap.put(censusData.state, new CensusDAO(censusData)));
            }
            if (csvFilePath.length == 1) return censusStateMap;
            this.loadIndiaStateCodeData(csvFilePath[1], censusStateMap);
            return censusStateMap;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INCORRECT_FILE_DATA);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.exceptionType.name());
        }
    }


    public int loadIndiaStateCodeData(String csvFilePath, Map<String, CensusDAO> censusStateMap) throws CensusAnalyserException {

        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<CSVStates> stateCodeDataList = csvBuilder.getListCSVFile(reader, CSVStates.class);
            StreamSupport.stream(stateCodeDataList.spliterator(),false)
                    .filter(csvStates -> censusStateMap.get(csvStates.state)!=null)
                    .forEach(censusData -> censusStateMap.get(censusData.state).stateCode = censusData.stateCode);
            return censusStateMap.size();

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INCORRECT_FILE_DATA);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.exceptionType.name());
        }
    }
}
