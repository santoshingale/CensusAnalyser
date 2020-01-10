package censusanalyser;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import csvbuilder.CSVBuilderException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.io.IOException;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String INVALID_CSV_FILE_EXTENTION_PATH = "./src/test/resources/IndiaStateCensusData.txt";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INCORRECT_DATA_CSV_FILE_PATH = "./src/test/resources/IncorrectIndiaStateCensusData.csv";
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,numOfRecords);
            System.out.println(numOfRecords);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileType_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INVALID_CSV_FILE_EXTENTION_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithIncorrectDelimiter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INCORRECT_DATA_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_FILE_DATA,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithIncorrectHeader_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INCORRECT_DATA_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_FILE_DATA,e.type);
        }
    }

    @Test
    public void givenIndianStateCodeCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaStateCodeData(INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(37,numOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCode_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaStateCodeData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndianStateCode_WithWrongFileType_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaStateCodeData(INVALID_CSV_FILE_EXTENTION_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndianStateCode_WithIncorrectDelimiter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaStateCodeData(INCORRECT_DATA_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_FILE_DATA,e.type);
        }
    }

    @Test
    public void givenIndianStateCode_WithIncorrectHeader_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaStateCodeData(INCORRECT_DATA_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_FILE_DATA,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_shouldReturnSortedData() throws IOException, CSVBuilderException, CensusAnalyserException {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            IndiaCensusCSV[] indiaCensusCSVS = new Gson().fromJson(censusAnalyser.getSortedDataByStateName(), IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", indiaCensusCSVS[0].state);
            Assert.assertEquals("West Bengal", indiaCensusCSVS[28].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCode_shouldReturnSortedData() throws IOException, CSVBuilderException, CensusAnalyserException {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaStateCodeData(INDIA_STATE_CODE_CSV_FILE_PATH);
            CSVStates[] csvStates = new Gson().fromJson(censusAnalyser.getSortedDataByStateCode(), CSVStates[].class);
            Assert.assertEquals("Andhra Pradesh New", csvStates[0].stateName);
            Assert.assertEquals("West Bengal", csvStates[36].stateName);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_whenNoCSVData_shouldReturnException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            IndiaCensusCSV[] indiaCensusCSVS = new Gson().fromJson(censusAnalyser.getSortedDataByStateName(), IndiaCensusCSV[].class);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA,e.type);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateData_whenNoCSVData_shouldReturnException() throws CensusAnalyserException {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            CSVStates[] csvStates = new Gson().fromJson(censusAnalyser.getSortedDataByStateCode(), CSVStates[].class);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_STATE_DATA,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_shouldReturnSortedData_ByPopulation() throws IOException, CSVBuilderException, CensusAnalyserException {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            IndiaCensusCSV[] indiaCensusCSVS = new Gson().fromJson(censusAnalyser.getSortedDataByPopulation(), IndiaCensusCSV[].class);
            Assert.assertEquals("Uttar Pradesh", indiaCensusCSVS[0].state);
            Assert.assertEquals("Sikkim", indiaCensusCSVS[28].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_whenNoCSVData_sortMethod_shouldReturnException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            IndiaCensusCSV[] indiaCensusCSVS = new Gson().fromJson(censusAnalyser.getSortedDataByPopulation(), IndiaCensusCSV[].class);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_shouldReturnSortedData_ByPopulationDensity() throws IOException, CSVBuilderException, CensusAnalyserException {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            IndiaCensusCSV[] indiaCensusCSVS = new Gson().fromJson(censusAnalyser.getSortedDataByPopulationDensity(), IndiaCensusCSV[].class);
            Assert.assertEquals("Bihar", indiaCensusCSVS[0].state);
            Assert.assertEquals("Arunachal Pradesh", indiaCensusCSVS[28].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_shouldReturnSortedData_ByArea() throws IOException, CSVBuilderException, CensusAnalyserException {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            IndiaCensusCSV[] indiaCensusCSVS = new Gson().fromJson(censusAnalyser.getSortedDataByArea(), IndiaCensusCSV[].class);
            Assert.assertEquals("Rajasthan", indiaCensusCSVS[0].state);
            Assert.assertEquals("Goa", indiaCensusCSVS[28].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
}
