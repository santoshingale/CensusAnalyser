package censusanalyser;

import csvbuilder.CSVBuilderException;
import csvbuilder.CSVBuilderFactory;
import csvbuilder.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CensusLoader {

    List<CensusDAO> censusCSVList = new ArrayList<CensusDAO>();;

    public <E> List<CensusDAO> loadCensusData(String csvFilePath, Class<E> censusCSVClass) throws CensusAnalyserException {

        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<E> listCSVFile = csvBuilder.getListCSVFile(reader, censusCSVClass);

            if (censusCSVClass.getName().equals("censusanalyser.IndiaCensusCSV")) {
                listCSVFile.stream()
                        .filter(censusData -> censusCSVList.add(new CensusDAO((IndiaCensusCSV) censusData)))
                        .collect(Collectors.toList());
            } else if (censusCSVClass.getName().equals("censusanalyser.USCensusCSV")) {
                listCSVFile.stream()
                        .filter(censusData -> censusCSVList.add(new CensusDAO((USCensusCSV) censusData)))
                        .collect(Collectors.toList());
            }
            return this.censusCSVList;
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
}
