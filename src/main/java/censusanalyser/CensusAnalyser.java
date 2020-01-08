package censusanalyser;

import com.google.gson.Gson;
import csvbuilder.*;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {

        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> censusCSVIterator = csvBuilder.getCSVFileIterator(reader,IndiaCensusCSV.class);
            return getCount(censusCSVIterator);

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

    public int loadIndiaStateCodeData(String csvFilePath) throws CensusAnalyserException {

        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStates> stateCSVIterator = csvBuilder.getCSVFileIterator(reader,CSVStates.class);
            return getCount(stateCSVIterator);

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

    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> csvItrable = () -> iterator;
        int numOfEateries = (int) StreamSupport.stream(csvItrable.spliterator(), false).count();
        return numOfEateries;
    }

    public String getSortedDataByStateName(String csvFilePath) throws IOException, CSVBuilderException  {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> censusCSVIterator = csvBuilder.getCSVFileIterator(reader,IndiaCensusCSV.class);
            Iterable<IndiaCensusCSV> iterable = () -> censusCSVIterator;
            List<IndiaCensusCSV> list = StreamSupport
                    .stream(iterable.spliterator(), false)
                    .collect(Collectors.toList());
            Comparator<IndiaCensusCSV> censusCSVComparator = (a,b) -> ((a.toString().compareTo(b.toString())) < 0 ) ? -1 : 1;
            Collections.sort(list,censusCSVComparator);
            String jsonString = new Gson().toJson(list);
            System.out.println(jsonString);
            return jsonString;
        }
    }
}
