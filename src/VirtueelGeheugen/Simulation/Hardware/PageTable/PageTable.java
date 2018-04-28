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
     * Gets the last time the process to which this table belongs was used.
     *
     * @return The clock time of the last used page.
     */
    public int getLastUsed(){

        return getLastUsedPage().getLastAccessTime();
    }

    /**
     * @return The last page used by the process=.
     */
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
     * @param address    Adrdess of the instruction being executed.
     * @param process    The process owning this page table.
     * @param accessTime The current clock time.
     *
     */
    public void addToRAM(int address, Process process, int accessTime){

        this.get(address).addToRAM(process.getProcessRAMInterface().add(process, address), accessTime);
    }

    /**
     * Used to swap 2 pages from the same process. Other functions same as addToRAM(int address, Process process, int accessTime)
     *
     * @param address    Adrdess of the instruction being executed.
     * @param process    The process owning this page table.
     * @param accessTime The current clock time.
     * @param frame      The frame of the page to be replaced.
     */
    public void addToRAM(int address, Process process, int accessTime, int frame){

        process.getProcessRAMInterface().overWrite(process, address, frame);
        this.get(address).addToRAM(frame, accessTime);
    }
//======================================================================================================================
    //private functions

    /**
     * Translate a virtual address to a page number.
     *
     * @param address The virtual address.
     * @return        Corresponding page number.
     */
    public static int translateAddressToPage(int address){
        return address / PAGE_SIZE;
    }

    /**
     * Gets the offset of the address within a page.
     *
     * @param address The virtual address.
     * @return        The offset.
     */
    public static int getOffset(int address){
        return address % PAGE_SIZE;
    }
}
