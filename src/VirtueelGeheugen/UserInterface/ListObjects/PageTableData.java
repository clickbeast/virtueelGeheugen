package VirtueelGeheugen.UserInterface.ListObjects;

public class PageTableData {
    private String presentBit;
    private String modifyBit;
    private String lastAccessTime;
    private String frameNumber;

    public PageTableData(String presentBit, String modifyBit, String lastAccessTime, String frameNumber) {
        this.presentBit = presentBit;
        this.modifyBit = modifyBit;
        this.lastAccessTime = lastAccessTime;
        this.frameNumber = frameNumber;
    }

    public String getPresentBit() {
        return presentBit;
    }

    public String getModifyBit() {
        return modifyBit;
    }

    public String getLastAccessTime() {
        return lastAccessTime;
    }

    public String getFrameNumber() {
        return frameNumber;
    }
}
