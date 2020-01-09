package censusanalyser;

import com.google.gson.Gson;
import csvbuilder.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    List<IndiaCensusCSV> indianCensusDataList = null;
    List<CSVStates> stateCodeDataList = null;

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {

        try (Reader reader = Files.newBufferedReader( Paths.get( csvFilePath ) );) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            indianCensusDataList = csvBuilder.getListCSVFile( reader, IndiaCensusCSV.class );
            return indianCensusDataList.size();

        } catch ( IOException e ) {
            throw new CensusAnalyserException( e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM );
        } catch ( RuntimeException e ) {
            throw new CensusAnalyserException( e.getMessage(),
                    CensusAnalyserException.ExceptionType.INCORRECT_FILE_DATA );
        } catch ( CSVBuilderException e ) {
            throw new CensusAnalyserException( e.getMessage(), e.exceptionType.name() );
        }
    }

    public int loadIndiaStateCodeData( String csvFilePath ) throws CensusAnalyserException {

        try ( Reader reader = Files.newBufferedReader( Paths.get( csvFilePath ) );) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            stateCodeDataList = csvBuilder.getListCSVFile( reader, CSVStates.class );
            return stateCodeDataList.size();

        } catch ( IOException e ) {
            throw new CensusAnalyserException( e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM );
        } catch ( RuntimeException e ) {
            throw new CensusAnalyserException( e.getMessage(),
                    CensusAnalyserException.ExceptionType.INCORRECT_FILE_DATA );
        } catch ( CSVBuilderException e ) {
            throw new CensusAnalyserException( e.getMessage(),e.exceptionType.name() );
        }
    }

    private <E> int getCount( Iterator<E> iterator ) {
        Iterable<E> csvItrable = () -> iterator;
        int numOfEateries = (int) StreamSupport.stream( csvItrable.spliterator(), false ).count();
        return numOfEateries;
    }

    public String getSortedDataByStateName() throws IOException, CSVBuilderException, CensusAnalyserException {
        if ( indianCensusDataList == null || indianCensusDataList.size() == 0 ) {
            throw new CensusAnalyserException( "No Cencus Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA );
        }
        Comparator<IndiaCensusCSV> censusCSVComparator = ( a, b ) -> ( ( a.toString().compareTo(b.toString() ) ) < 0 ) ? -1 : 1;
        Collections.sort( indianCensusDataList, censusCSVComparator );
        String jsonString = new Gson().toJson( indianCensusDataList );
        System.out.println( jsonString );
        return jsonString;
    }

    public String getSortedDataByStateCode() throws CensusAnalyserException {
        if ( stateCodeDataList == null || stateCodeDataList.size() == 0 ) {
            throw new CensusAnalyserException( "No State Data", CensusAnalyserException.ExceptionType.NO_STATE_DATA );
        }
        Comparator<CSVStates> csvComparator = ( a, b ) -> (( a.stateCode.compareTo( b.stateCode )) < 0 ) ? -1 : 1;
        Collections.sort( stateCodeDataList, csvComparator );
        String jsonString = new Gson().toJson( stateCodeDataList );
        System.out.println( jsonString );
        return jsonString;
    }
}
