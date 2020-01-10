package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateDAO {
    public int srNo;
    public String stateName;
    public int tin;
    public String stateCode;

    public IndiaStateDAO(CSVStates csvStates) {
        this.srNo = csvStates.srNo;
        this.stateName = csvStates.stateName;
        this.tin = csvStates.tin;
        this.stateCode = csvStates.stateCode;
    }
}
