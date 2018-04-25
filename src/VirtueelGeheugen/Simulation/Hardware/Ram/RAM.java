package VirtueelGeheugen.Simulation.Hardware.Ram;

import java.util.Comparator;
import java.util.PriorityQueue;

import VirtueelGeheugen.Interfaces.ProcessRAMInterface;
import VirtueelGeheugen.Simulation.Process;

/**
 * <pre>
 * Model for the RAM.
 *
 * contains:
 * </pre>
 * <ul>
 *     <li>
 *         The maximum amount of frames allowed in the RAM at once.
 *     </li>
 *     <li>
 *         The maximum amount of processes allowed in he RAM at once.
 *     </li>
 *     <li>
 *         An array representing which frames are currently taken.
 *     </li>
 *     <li>
 *         A PriorityQueue of processes currently in RAM, keeping the LRU process at the front.
 *     </li>
 * </ul>
 */

public class RAM {
//======================================================================================================================
    //interface

    private ProcessRAMInterface processToRAMInterface = new ProcessRAMInterface() {
        @Override
        public int add() {
            return RAM.this.addPage();
        }

        @Override
        public void remove(int frame) {
            RAM.this.removePage(frame);
        }
    };
//======================================================================================================================
    //class specs

    private final static int FRAME_COUNT = 12;
    private final static int MAX_PROCESSES = 4;
    private boolean[] frames = new boolean[FRAME_COUNT];
    private PriorityQueue<Process> processList;

    public RAM(){
        this.processList = new PriorityQueue<>(new LRUProcess());
    }
//======================================================================================================================
    //public functions

    /**
     * Called when a write operation occurs.
     *
     * @param process    Process on which the the operation is called.
     * @param address    Address of the operation.
     * @param accessTime Current clock time.
     */
    public void write(Process process, int address, int accessTime){

        if(!this.processList.contains(process)) this.addProcess(process, address, accessTime);
        process.write(address);
    }

    /**
     * Called when a read operation occurs.
     *
     * @param process    Process on which the the operation is called.
     * @param address    Address of the operation.
     * @param accessTime Current clock time.
     */
    public void read(Process process, int address, int accessTime){

        if(!this.processList.contains(process)) this.addProcess(process, address, accessTime);
    }

    /**
     * Removes process from the RAM's list.
     *
     * @param process Process to remove.
     */
    public void terminate(Process process){

        if(processList.contains(process)) processList.remove(process);
    }

    /**
     * <p>
     *      Adding a process to the list. Used when process instruction is called.
     * </p>
     *
     * <p>
     *     Calls scalePages(int address, int accessTime) to reorganise the RAM.
     * </p>
     *
     * @param process    Process to be added.
     * @param address    The address that has to be moved to RAM.
     * @param accessTime The current time of the running simulation.
     */

    public void addProcess(Process process, int address, int accessTime){

        processList.add(process);
        scalePages(address, accessTime);
    }

    /**
     * <p>
     *     Method to set a certain frame as taken, also sets the frame as taken.
     * </p>
     *
     * @return The frame number now taken.
     */
    private int addPage(){

        for(int i = 0; i < FRAME_COUNT; i++){

            if(!frames[i]){
                frames[i] = true;
                return i;
            }
        }
        return -1;
    }

    /**
     * <p>
     *     Method to remove one frame from the RAM.
     * </p>
     *
     * @param frame The frame that has to be removed.
     */
    private void removePage(int frame){

        //Safety to prevent index bounds violoation
        if(frame > -1 && frame < FRAME_COUNT) frames[frame] = false;
    }

    /**
     * <p>
     *     Method to change the amount of pages currently in RAM for every process with pages in RAM. Use the specified
     *     address to determine first page to be put in RAM.
     * </p>
     *
     * <p>
     *     When more than 4 processList are in RAM, the LRU process will be removed.
     * </p>
     *
     * @param address    First page to be put in RAM.
     * @param accessTime Time at which the RAM is accessed.
     */
    private void scalePages(int address, int accessTime){

        //4 being the maximum number of processList that can be in RAM.
        if (processList.size() > MAX_PROCESSES){
            Process process = processList.poll();
            process.removeAllPagesFromRAM();
        }

        for (Process RAMProcess: processList) {
            RAMProcess.setProcessRAMInterface(processToRAMInterface);
            RAMProcess.scalePagesToFit(FRAME_COUNT / processList.size(), address, accessTime);
        }
    }
//======================================================================================================================
    //comparator

    /**
     * Comparator used with the process list. Comparator will keep least recently used process at the front of the
     * queue.
     */
    private class LRUProcess implements Comparator<Process>{

        @Override
        public int compare(Process process1, Process process2) {
            return process1.getLastUsed() - process2.getLastUsed();
        }
    }
}
