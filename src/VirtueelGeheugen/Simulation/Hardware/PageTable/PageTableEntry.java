package VirtueelGeheugen.Simulation.Hardware.PageTable;

public class PageTableEntry {
//======================================================================================================================
    //class specs

    private boolean present;
    private boolean modified;
    private int lastAccessTime;
    private int frameNumber;

    public PageTableEntry() {
        this.present = false;
        this.modified = false;

        //default values don't matter, page not present.
        this.lastAccessTime = 0;
        this.frameNumber = 0;
    }
//======================================================================================================================
    //setters

    public void setPresent(boolean present) {
        this.present = present;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    public void setLastAccessTime(int lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public void setFrameNumber(int frameNumber) {
        this.frameNumber = frameNumber;
    }
//======================================================================================================================
    //getters

    public boolean isPresent() {
        return present;
    }

    public boolean isModified() {
        return modified;
    }

    public int getLastAccessTime() {
        return lastAccessTime;
    }

    public int getFrameNumber() {
        return frameNumber;
    }
//======================================================================================================================
    //public methods

    public void addToRAM(int frame, int accessTime){
        this.present = true;
        this.modified = false;
        this.lastAccessTime = accessTime;
        this.frameNumber = frame;
    }
}
