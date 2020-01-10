package censusanalyser;

public class IndiaStateCodeDAO {
    public int srNo;
    public String stateName;
    public int tin;
    public String stateCode;

    public IndiaStateCodeDAO(CSVStates csvStates) {
        this.srNo = csvStates.srNo;
        this.stateName = csvStates.stateName;
        this.tin = csvStates.tin;
        this.stateCode = csvStates.stateCode;
    }
}
