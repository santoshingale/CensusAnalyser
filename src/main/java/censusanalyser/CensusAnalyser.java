package censusanalyser;

import com.google.gson.Gson;
import csvbuilder.*;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class CensusAnalyser {

    List<IndiaCensusDAO> censusCSVList = null;
    List<IndiaStateCodeDAO> stateCodeCSVList = null;

    public CensusAnalyser() {
        this.censusCSVList = new ArrayList<IndiaCensusDAO>();
        this.stateCodeCSVList = new ArrayList<IndiaStateCodeDAO>();
    }
    
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {

        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<IndiaCensusCSV> listCSVFile = csvBuilder.getListCSVFile(reader, IndiaCensusCSV.class);
            int i=0;
            while (i < listCSVFile.size()) {
               this.censusCSVList.add(new IndiaCensusDAO(listCSVFile.get(i)));
                i++;
            }
            return censusCSVList.size();
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

    public int loadIndiaStateCodeData(String csvFilePath) throws CensusAnalyserException {

        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<CSVStates> stateCodeDataList = csvBuilder.getListCSVFile(reader, CSVStates.class);
            int i=0;
            while (i < stateCodeDataList.size()) {
                this.stateCodeCSVList.add(new IndiaStateCodeDAO(stateCodeDataList.get(i)));
                i++;
            }
            return stateCodeCSVList.size();

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

    public String getSortedDataByStateName() throws IOException, CSVBuilderException, CensusAnalyserException {
        if (censusCSVList == null || censusCSVList.size() == 0) {
            throw new CensusAnalyserException("No Cencus Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDAO> censusCSVComparator = ( a, b ) -> (( a.state.compareTo(b.state)) < 0 ) ? -1 : 1;
        Collections.sort(censusCSVList, censusCSVComparator);
        String jsonString = new Gson().toJson(censusCSVList);
        System.out.println(jsonString);
        return jsonString;
    }

    public String getSortedDataByStateCode() throws CensusAnalyserException {
        if (stateCodeCSVList == null || stateCodeCSVList.size() == 0) {
            throw new CensusAnalyserException( "No State Data", CensusAnalyserException.ExceptionType.NO_STATE_DATA );
        }
        Comparator<IndiaStateCodeDAO> csvComparator = ( a, b ) -> (( a.stateCode.compareTo( b.stateCode )) < 0 ) ? -1 : 1;
        Collections.sort(stateCodeCSVList, csvComparator);
        String jsonString = new Gson().toJson(stateCodeCSVList);
        System.out.println(jsonString);
        return jsonString;
    }
}
