package censusanalyser;

public class IndiaStateCodeDAO {
    public int srNo;
    public String state;
    public int tin;
    public String stateCode;

    public IndiaStateCodeDAO(CSVStates csvStates) {
        this.srNo = csvStates.srNo;
        this.state = csvStates.state;
        this.tin = csvStates.tin;
        this.stateCode = csvStates.stateCode;
    }
}
