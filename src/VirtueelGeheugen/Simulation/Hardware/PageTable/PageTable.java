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

    public int getLastUsed(){
        int max = 0;
        for(PageTableEntry pageTableEntry: this){

            if(max < pageTableEntry.getLastAccessTime()) max = pageTableEntry.getLastAccessTime();
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

        PageTableEntry LRU = this.get(0);
        for(PageTableEntry entry: this){
            if(entry.getLastAccessTime() < LRU.getLastAccessTime()){
                LRU = entry;
            }
        }
        LRU.setPresent(false);
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

        int pageNumber = translateAdressToPage(address);
        this.get(pageNumber).addToRAM(process.getProcessRAMInterface().add(process, pageNumber), accessTime);
    }

    /**
     * Set a page containing a certain address as modified.
     *
     * @param address Address in a certain page.
     */
    public void setPageTableAsModified(int address){
        this.get(translateAdressToPage(address)).setModified(true);
    }
//======================================================================================================================
    //private functions

    private int translateAdressToPage(int address){
        return address / PAGE_SIZE;
    }

    private int getOffset(int address){
        return address % PAGE_SIZE;
    }
}
