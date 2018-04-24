package VirtueelGeheugen.Simulation.Hardware.Ram;

import java.util.Comparator;
import java.util.PriorityQueue;

import VirtueelGeheugen.Interfaces.ProcessRAMInterface;
import VirtueelGeheugen.Simulation.Process;

/**
 * Simulatie van het ram geheugen.
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

    private final int FRAME_COUNT = 12;
    private boolean[] frames = new boolean[FRAME_COUNT];
    private PriorityQueue<Process> processes;


    public RAM(){
        this.processes = new PriorityQueue<>(new LRUProcess());
    }
//======================================================================================================================
    //public functions

    /**
     *
     * @param process    Process on which the
     * @param address
     * @param accessTime
     */
    public void write(Process process, int address, int accessTime){

        if(!this.processes.contains(process)) this.addProcess(process, address, accessTime);
        process.write(address);
    }

    public void read(Process process, int address, int accessTime){

        if(!this.processes.contains(process)) this.addProcess(process, address, accessTime);
    }

    /**
     * Removes process from the RAM's list.
     *
     * @param process Process to remove.
     */
    public void terminate(Process process){

        if(processes.contains(process)) processes.remove(process);
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

        processes.add(process);
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
     *     When more than 4 processes are in RAM, the LRU process will be removed.
     * </p>
     *
     * @param address    First page to be put in RAM.
     * @param accessTime Time at which the RAM is accessed.
     */
    private void scalePages(int address, int accessTime){

        //4 being the maximum number of processes that can be in RAM.
        if (processes.size() > 4){
            Process process = processes.poll();
            process.removeAllPagesFromRAM();
        }

        for (Process RAMProcess: processes) {
            RAMProcess.setProcessRAMInterface(processToRAMInterface);
            RAMProcess.scalePagesToFit(FRAME_COUNT / processes.size(), address, accessTime);
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
