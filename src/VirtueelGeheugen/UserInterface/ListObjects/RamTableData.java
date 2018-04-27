package VirtueelGeheugen.UserInterface.ListObjects;

public class RamTableData {
    //TODO


    private String frameId;
    private String processId;

    public RamTableData(String frameId, String processId) {
        this.frameId = frameId;
        this.processId = processId;
    }

    public String getFrameId() {
        return frameId;
    }

    public String getProcessId() {
        return processId;
    }
}
