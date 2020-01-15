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

public class IndiaCensusAdapter extends CensusAdapter {

    @Override
    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        Map<String, CensusDAO> censusStateMap = super.loadCensusData(IndiaCensusCSV.class,csvFilePath[0]);
        this.loadIndiaStateCodeData(censusStateMap,csvFilePath[1]);
        return censusStateMap;
    }

    public int loadIndiaStateCodeData( Map<String, CensusDAO> censusStateMap,String csvFilePath) throws CensusAnalyserException {

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
