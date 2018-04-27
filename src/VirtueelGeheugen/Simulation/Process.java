package VirtueelGeheugen.Simulation;

import VirtueelGeheugen.Interfaces.ProcessRAMInterface;
import VirtueelGeheugen.Interfaces.SimulationInterface;
import VirtueelGeheugen.Simulation.Hardware.PageTable.PageTable;
import VirtueelGeheugen.Simulation.Hardware.PageTable.PageTableEntry;
import jdk.nashorn.internal.ir.annotations.Ignore;

import static VirtueelGeheugen.Simulation.Hardware.PageTable.PageTable.translateAddressToPage;

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
    private int limit;

    private ProcessRAMInterface processRAMInterface;
    private SimulationInterface simulationInterface;

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

    public int getLimit() {
        return limit;
    }

    public SimulationInterface getSimulationInterface() {
        return simulationInterface;
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

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setSimulationInterface(SimulationInterface simulationInterface) {
        this.simulationInterface = simulationInterface;
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

        int page = translateAddressToPage(address);

        while (present < pageCount) {

            addPage(page, accessTime);
            writeTos++;
            simulationInterface.writeToRAM();
            page++;
            present++;
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
        if(entry.isModified()){
            writeBacks++;
            simulationInterface.writeToPersistent();
        }
        entry.setPresent(false);
        processRAMInterface.remove(entry.getFrameNumber());
    }

    /**
     * Method to be called when adding to add a page.
     *
     * @param page       the page number containing the address of the instruction being called.
     * @param accessTime The current clock time.
     */
    private void addPage(int page, int accessTime){

        pageTable.addToRAM(page, this, accessTime);
    }

    private PageTableEntry accessPage(int address, int accessTime){

        int page = translateAddressToPage(address);
        PageTableEntry entry = pageTable.get(page);
        if(!entry.isPresent()){
            if(pageTable.getCurrentlyInRAM() >= limit){
                removePage();
            }
            addPage(page, accessTime);
        }
        entry.setLastAccessTime(accessTime);
        return entry;
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
     * @param limit      The amount of pages allowed.
     * @param address    The address in the first page that has to be put in RAM.
     * @param accessTime The current time of the clock.
     */

    public void scalePagesToFit(int limit, int address, int accessTime){

        int present = pageTable.getCurrentlyInRAM();
        this.limit = limit;

        int page;
        if(address != -1) page = translateAddressToPage(address);
        else page = this.pageTable.indexOf(this.pageTable.getLastUsedPage());

        //2 if tests to ignore this function if the amount is equal.
        if (present >= limit) {

            //remove enough pages to make room for 1 more.
            removePagesToMatch(present, limit);
        } else if (present < limit){

            addPagesToMatch(present, limit, page, accessTime);
        }
    }

    /**
     * Call when removing process from RAM.
     */
    public void removeAllPagesFromRAM(){

        for(PageTableEntry entry: pageTable){
            if(entry.isPresent()) {
                processRAMInterface.remove(entry.getFrameNumber());
                if (entry.isModified()){
                    writeBacks++;
                    simulationInterface.writeToPersistent();
                }
                entry.setPresent(false);
            }
        }
    }

    /**
     * Call when a write operation occurs.
     *
     * @param address Addreess to be written to.
     * @param accessTime The current clock time.
     */
    public void write(int address, int accessTime){

        accessPage(address, accessTime).setModified(true);
    }

    /**
     * Call when a read operation occurs.
     *
     * @param address Addreess to be read from.
     * @param accessTime The current clock time.
     */
    public void read(int address, int accessTime){

        accessPage(address, accessTime);
    }
}
