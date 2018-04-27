package VirtueelGeheugen.Simulation;

import VirtueelGeheugen.Interfaces.ProcessRAMInterface;
import VirtueelGeheugen.Simulation.Hardware.PageTable.PageTable;
import VirtueelGeheugen.Simulation.Hardware.PageTable.PageTableEntry;

/**
 * <pre>
 *     A model representing a process. Each process is assumed to be able to have 16 pages and will get a page table
 *     to facilitate 16 pages.
 *
 *     Each process contains.
 * </pre>
 *
 * <ul>
 *     <li>
 *         The id of the process.
 *     </li>
 *     <li>
 *         A page table.
 *     </li>
 *     <li>
 *         An interface connecting the process back to the RAM.
 *     </li>
 *     <li>
 *         The amount of times the process has written a page to the RAM.
 *     </li>
 *     <li>
 *         The amount of time a process has written back pages to the persistence memory.
 *     </li>
 * </ul>
 */

public class Process {
//======================================================================================================================
    //class specs

    private int pId;
    private PageTable pageTable;

    private ProcessRAMInterface processRAMInterface;

    private int writeTos;
    private int writeBacks;

    public Process(int pId) {
        this.pId = pId;
        this.pageTable = new PageTable();

        writeTos = 0;
        writeBacks = 0;
    }
//======================================================================================================================
    //getters

    public ProcessRAMInterface getProcessRAMInterface() {
        return processRAMInterface;
    }

    public int getpId() {
        return pId;
    }

    public PageTable getPageTable() {
        return pageTable;
    }

    public int getWriteTos() {
        return writeTos;
    }

    public int getWriteBacks() {
        return writeBacks;
    }
//======================================================================================================================
    //setters

    public void setpId(int pId) {
        this.pId = pId;
    }

    public void setPageTable(PageTable pageTable) {
        this.pageTable = pageTable;
    }

    public void setProcessRAMInterface(ProcessRAMInterface processRAMInterface) {
        this.processRAMInterface = processRAMInterface;
    }

    public void setWriteTos(int writeTos) {
        this.writeTos = writeTos;
    }

    public void setWriteBacks(int writeBacks) {
        this.writeBacks = writeBacks;
    }
//======================================================================================================================
    //private functions

    /**
     * Method to add pages until the entire allocated in the ram is filled.
     *
     * @param present    The amount of pages currently present.
     * @param pageCount  The amount of pages that has to be matched.
     * @param address    The address of the page that has to be put in RAM
     * @param accessTime The current time of the clock.
     */
    private void addPagesToMatch(int present, int pageCount, int address, int accessTime){

        while (present < pageCount) {

            addPage(address, accessTime);
            writeTos++;
        }
    }

    /**
     * Method to remove pages until the amount of pages currently in RAM does no longer exceed the allowed limit.
     *
     * @param present    The amount of pages currently present.
     * @param pageCount  The amount of pages that has to be matched.
     */
    private void removePagesToMatch(int present, int pageCount){

        while (present > pageCount) {

            removePage();
            present--;
        }
    }

    /**
     * <p>
     *     remove the LRY page from RAM.
     * </p>
     *
     * <p>
     *     Increase the amount of pages written back to the persistent memory if the page has been changed while it
     *     was in RAM.
     * </p>
     */
    private void removePage(){

        PageTableEntry entry = pageTable.removeLRU();
        if(entry.isModified()) writeBacks++;
        processRAMInterface.remove(entry.getFrameNumber());
    }

    /**
     * Method to be called when adding to add a page.
     *
     * @param address    The address of the instruction being called.
     * @param accessTime The current clock time.
     */
    private void addPage(int address, int accessTime){

        pageTable.addToRAM(address, this, accessTime);
    }
//======================================================================================================================
    //public functions

    /**
     * Method to find out when the process was last used.
     *
     * @return  Time when the process was last used.
     */

    public int getLastUsed(){

        return pageTable.getLastUsed();
    }

    /**
     * <p>
     *     Sets how many pages of the given process are currently in RAM. If 0 is specified all pages will be removed from
     *     RAM.
     * </p>
     *
     * <p>
     *     If there are too many pages currently in RAM, the excess will will be removed from RAM. Otherwise pages will
     *     be added that follow the last used page.
     * </p>
     *
     * <p>
     *     If a page is newly added to the RAM, the time it was last accessed is set to the current clock time.
     * </p>
     *
     * @param pageCount  The amount of pages.
     * @param address    The address in the first page that has to be put in RAM.
     * @param accessTime The current time of the clock.
     */

    public void scalePagesToFit(int pageCount, int address, int accessTime){

        int present = pageTable.getCurrentlyInRAM();

        //2 if tests to ignore this function if the amount is equal.
        if (present >= pageCount) {

            addPage(address, accessTime);
            removePagesToMatch(present, pageCount);
        } else if (present < pageCount){

            addPagesToMatch(present, pageCount, address, accessTime);
        }
    }

    /**
     * Call when removing process from RAM.
     */
    public void removeAllPagesFromRAM(){

        while (pageTable.getCurrentlyInRAM() > 0) removePage();
    }

    /**
     * Call when a write operation occurs.
     *
     * @param address Addreess to be written to.
     */
    public void write(int address){

        pageTable.setPageTableAsModified(address);
    }
}
