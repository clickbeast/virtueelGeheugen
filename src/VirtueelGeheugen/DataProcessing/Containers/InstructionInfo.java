package VirtueelGeheugen.DataProcessing.Containers;


/*
    Class that contains info about an incoming process, when working with the process, TAT and wait time can be added
    after a testrun

 */
public class InstructionInfo {

    private String operation;
    private int virtualAdressValue;
    private int physicalAddress;
    private int processId;


    public InstructionInfo(String operation, int virtualAdressValue, int processId) {
        this.operation = operation;
        this.virtualAdressValue = virtualAdressValue;
        this.processId = processId;
    }

    public InstructionInfo(InstructionInfo instructionInfo) {

        this.operation = instructionInfo.operation;
        this.virtualAdressValue = instructionInfo.processId;
        this.processId = instructionInfo.processId;
    }

    public int getPhysicalAddress() {
        return physicalAddress;
    }

    public String getOperation() {
        return operation;
    }

    public int getVirtualAdressValue() {
        return virtualAdressValue;
    }

    public int getProcessId() {
        return processId;
    }

    public void setPhysicalAddress(int physicalAddress) {
        this.physicalAddress = physicalAddress;
    }
}
