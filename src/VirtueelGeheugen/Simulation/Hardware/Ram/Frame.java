package VirtueelGeheugen.Simulation.Hardware.Ram;

import VirtueelGeheugen.Simulation.Process;

/**
 * An object representing one frame in the RAM. Each frame contains:
 * <ul>
 *     <li>
 *         A boolean representing if it's taken.
 *     </li>
 *     <li>
 *         A reference to a process.
 *     </li>
 *     <li>
 *         The number of a page in the process reference.
 *     </li>
 * </ul>
 */

public class Frame {
//======================================================================================================================
    //class specs

    private boolean taken;
    private Process process;
    private int pageNumber;

    public Frame(){

        taken = false;
        process = null;
        pageNumber = -1;
    }
//======================================================================================================================
    //setters

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
//======================================================================================================================
    //setters

    public boolean isTaken() {
        return taken;
    }

    public Process getProcess() {
        return process;
    }

    public int getPageNumber() {
        return pageNumber;
    }
//======================================================================================================================
    //public methods

    /**
     * A method used to set all the information of a page and set it as taken.
     *
     * @param process    The process reference.
     * @param pageNumber The page number.
     */
    public void fillPage(Process process, int pageNumber){

        this.taken = true;
        this.process = process;
        this.pageNumber = pageNumber;
    }

    /**
     * A method to call when removing a page from RAM, resets all information to defaults.
     */
    public void emptyPage(){

        this.taken = false;
        this.process = null;
        this.pageNumber = -1;
    }
}
