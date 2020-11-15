package dtype;

public class ReturnedData {

    boolean success;
    Data recoveredData;

    public ReturnedData(boolean success, Data recoveredData) {
        this.success = success;
        this.recoveredData = recoveredData;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public Data getRecoveredData() {
        return this.recoveredData;
    }

}
