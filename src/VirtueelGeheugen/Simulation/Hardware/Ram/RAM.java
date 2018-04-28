package VirtueelGeheugen.Simulation.Hardware.Ram;

import java.util.ArrayList;
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
 *         A list of Frame objects.
 *     </li>
 *     <li>
 *         A PriorityQueue of processes currently in RAM, keeping the LRU process at the front.
 *     </li>
 *     <li>
 *         A boolean defining which algorithm to use.
 *     </li>
 * </ul>
 */

public class RAM {
//======================================================================================================================
    //interface

    private ProcessRAMInterface processToRAMInterface = new ProcessRAMInterface() {
        @Override
        public int add(Process process, int pageNumber) {
            return RAM.this.addPage(process, pageNumber);
        }

        @Override
        public void remove(int frame) {
            RAM.this.removePage(frame);
        }

        @Override
        public void overWrite(Process process, int pageNumber, int frame){
            frames.get(frame).fillPage(process, pageNumber);
        }
    };
//======================================================================================================================
    //class specs

    private final static int FRAME_COUNT = 12;
    private final static int MAX_PROCESSES = 4;
    private ArrayList<Frame> frames;
    private PriorityQueue<Process> processList;
    private boolean prePaging;

    public RAM(){
        this.processList = new PriorityQueue<>(new LRUProcess());
        this.frames = new ArrayList<>(FRAME_COUNT);
        this.prePaging = true;

        //Safety: set all Frames to default.
        for(int i = 0; i < FRAME_COUNT; i++){

            frames.add(i, new Frame());
        }
    }
//======================================================================================================================
    //setter

    public void setPrePaging(boolean prePaging) {
        this.prePaging = prePaging;
    }

//======================================================================================================================
    //getters

    public ProcessRAMInterface getProcessToRAMInterface() {
        return processToRAMInterface;
    }

    public ArrayList<Frame> getFrames() {
        return frames;
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
    public Process write(Process process, int address, int accessTime){

        Process removedProcess = null;
        if(!this.processList.contains(process)) removedProcess = this.addProcess(process, address, accessTime);
        if(process.getPageTable().getCurrentlyInRAM() == 0){
            process.setLimit(FRAME_COUNT / processList.size());
            process.scalePagesToFit(address, accessTime, prePaging);
        }
        process.write(address, accessTime);
        return removedProcess;
    }

    /**
     * Called when a read operation occurs.
     *
     * @param process    Process on which the the operation is called.
     * @param address    Address of the operation.
     * @param accessTime Current clock time.
     */
    public Process read(Process process, int address, int accessTime){

        Process removedProcess = null;
        if(!this.processList.contains(process)) removedProcess = this.addProcess(process, address, accessTime);
        if(process.getPageTable().getCurrentlyInRAM() == 0){
            process.setLimit(FRAME_COUNT / processList.size());
            process.scalePagesToFit(address, accessTime, prePaging);
        }
        process.read(address, accessTime);
        return removedProcess;
    }

    /**
     * Removes process from the RAM's list.
     *
     * @param process Process to remove.
     */
    public void terminate(Process process, int accessTime){

        if(processList.contains(process)) processList.remove(process);
        for(Process RAMProcess: processList){
            RAMProcess.setLimit(FRAME_COUNT / processList.size());
            RAMProcess.scalePagesToFit(-1, accessTime, prePaging);
        }
    }

    /**
     * Adding a process to the list. Used when process instruction is called.
     * Calls scalePages(int address, int accessTime, boolean repage).
     *
     * @param process    Process to be added.
     * @param address    The address that has to be moved to RAM.
     * @param accessTime The current time of the running simulation.
     */

    public Process addProcess(Process process, int address, int accessTime){

        Process removedProcess = null;

        //4 being the maximum number of processList that can be in RAM.
        if (processList.size() >= MAX_PROCESSES){
            removedProcess = processList.poll();
            removedProcess.removeAllPagesFromRAM();
        }

        //add process
        processList.add(process);
        process.setLimit(FRAME_COUNT / processList.size());
        for (Process RAMProcess : processList) {
            if (RAMProcess != process) {
                RAMProcess.setLimit(FRAME_COUNT / processList.size());
                RAMProcess.scalePagesToFit(-1, accessTime, prePaging);
            }
        }

        return removedProcess;
    }

    /**
     * Method to set the information of a certain frame.
     *
     * @param process    The process reference to be added to the Frame.
     * @param pageNumber The page number of the page being added.
     * @return           The frame number now taken.
     */
    private int addPage(Process process, int pageNumber){

        for(int i = 0; i < FRAME_COUNT; i++){

            if(!frames.get(i).isTaken()){
                frames.get(i).fillPage(process, pageNumber);
                return i;
            }
        }
        return -1;
    }

    /**
     * Method to remove one frame from the RAM.
     *
     * @param frame The frame that has to be removed.
     */
    private void removePage(int frame){

        //Safety to prevent index bounds violoation
        if(frame > -1 && frame < FRAME_COUNT){
            frames.get(frame).emptyPage();
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
