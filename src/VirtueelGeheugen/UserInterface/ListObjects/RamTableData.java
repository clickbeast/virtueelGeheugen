package VirtueelGeheugen.UserInterface.ListObjects;

public class RamTableData {
    //TODO


    private String pageNumber;
    private String processId;

    public RamTableData(String pageNumber, String processId) {
        this.pageNumber = pageNumber;
        this.processId = processId;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public String getProcessId() {
        return processId;
    }
}
