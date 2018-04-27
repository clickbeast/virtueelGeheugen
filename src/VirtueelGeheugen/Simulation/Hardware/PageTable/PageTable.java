package VirtueelGeheugen.Simulation.Hardware.PageTable;

import VirtueelGeheugen.Simulation.Process;

import java.util.ArrayList;

public class PageTable extends ArrayList<PageTableEntry> {

    private static final int PAGE_SIZE = (int) Math.pow(2, 12);

    public PageTable() {

        for (int i = 0; i < 16; i++) {

            this.add(new PageTableEntry());
        }
    }
//======================================================================================================================
    //public methods

    /**
     * TODO
     * @return The clock time of the last used page.
     */
    public int getLastUsed(){

        return getLastUsedPage().getLastAccessTime();
    }

    public PageTableEntry getLastUsedPage(){

        PageTableEntry max = null;
        for(PageTableEntry pageTableEntry: this){

            if(max == null) max = pageTableEntry;
            else if(max.getLastAccessTime() < pageTableEntry.getLastAccessTime()) max = pageTableEntry;
        }
        return max;
    }

    /**
     * @return Amount of pages that are currently in RAM.
     */
    public int getCurrentlyInRAM(){

        int present = 0;
        for(PageTableEntry entry: this){
            if(entry.isPresent()) present++;
        }
        return present;
    }

    /**
     * Remove the LRU page and returns this page to extract information.
     *
     * @return The entry that was removed.
     */
    public PageTableEntry removeLRU(){

        PageTableEntry LRU = null;
        for(PageTableEntry entry: this){
            if(LRU == null) {
                if (entry.isPresent()) LRU = entry;
            }
            else if(entry.getLastAccessTime() < LRU.getLastAccessTime() && entry.isPresent()){
                LRU = entry;
            }
        }
        if(LRU != null) LRU.setPresent(false);
        return LRU;
    }

    /**
     * <p>
     *     Method that will add a certain page to RAM by setting:
     * </p>
     * <ul>
     *     <li>
     *         The present bit to 1 (true).
     *     </li>
     *     <li>
     *         The modified but to 0 (false).
     *     </li>
     *     <li>
     *         The last accessed time to the current clock time.
     *     </li>
     *     <li>
     *         The frame number given by the RAM.
     *     </li>
     * </ul>
     *
     * @param address    Adress of the instruction being executed.
     * @param process    The process owning this page table.
     * @param accessTime The current clock time.
     *
     */
    public void addToRAM(int address, Process process, int accessTime){

        this.get(address).addToRAM(process.getProcessRAMInterface().add(process, address), accessTime);
    }

    public void addToRAM(int address, Process process, int accessTime, int frame){

        process.getProcessRAMInterface().overWrite(process, address, frame);
        this.get(address).addToRAM(frame, accessTime);
    }

    /**
     * TODO remove
     * Set a page containing a certain address as modified.
     *
     * @param address Address in a certain page.
     */
    @Deprecated
    public void setPageTableAsModified(int address, int accessTime){

        PageTableEntry entry = this.get(translateAddressToPage(address));
        entry.setModified(true);
        entry.setLastAccessTime(accessTime);

    }
//======================================================================================================================
    //private functions

    public static int translateAddressToPage(int address){
        return address / PAGE_SIZE;
    }

    public static int getOffset(int address){
        return address % PAGE_SIZE;
    }
}
