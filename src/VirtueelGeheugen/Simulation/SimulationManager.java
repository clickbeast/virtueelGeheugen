package VirtueelGeheugen.Simulation;

import VirtueelGeheugen.DataProcessing.Containers.InstructionInfo;
import VirtueelGeheugen.DataProcessing.Containers.InstructionList;
import VirtueelGeheugen.MainWindowViewController;
import VirtueelGeheugen.Simulation.Hardware.Ram.RAM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class SimulationManager {
//======================================================================================================================
    //class specs

    private MainWindowViewController mainWindowController;

    private InstructionList instructionList;
    private ArrayList<Process> processList;

    private HashMap<String, Function<InstructionInfo, Void>> options;

    private int delay;

    private int currentTime;
    private RAM ram;

    public SimulationManager(){
        this.instructionList = new InstructionList();
        this.processList = new ArrayList<>();
        this.ram = new RAM();

        //HashMap to be a little bit more efficient compared to if-then-else.
        this.options = new HashMap<>();
        this.options.put("Start", (instructionInfo) -> {

            processList.add(instructionInfo.getProcessId(), new Process(instructionInfo.getProcessId()));
            ram.addProcess(processList.get(instructionInfo.getProcessId()), getCurrentTime(), getCurrentTime());

            return null;
        });
        this.options.put("Terminate", (instructionInfo) -> {

            Process process = processList.remove(instructionInfo.getProcessId());

            //TODO return removed process to be displayed.

            process.removeAllPagesFromRAM();
            ram.terminate(process);

            return null;
        });
        this.options.put("Read", (instructionInfo) -> {

            ram.read(processList.get(instructionInfo.getProcessId()), instructionInfo.getVirtualAdressValue(), getCurrentTime());

            return null;
        });
        this.options.put("Write", (instructionInfo) -> {

            ram.write(processList.get(instructionInfo.getProcessId()), instructionInfo.getVirtualAdressValue(), getCurrentTime());

            return null;
        });
    }

    public void setMainWindowController(MainWindowViewController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
//======================================================================================================================
    //getters

    public int getCurrentTime() {
        return currentTime;
    }

    public int getDelay() {
        return delay;
    }
//======================================================================================================================
    //private functions
//======================================================================================================================
    //public functions

    /**
     * Run entire XML file at once and display results at the end.
     */
    public void runComplete(){

        while(!instructionList.isEmpty()) runStep();
    }

    /**
     * Method to run the simulation to completion on a timer.
     *
     * @param millis The delay for the simulation.
     */
    public void runOnTimer(int millis){

        //TODO set to pause on negative!
    }

    /**
     * Run one instruction from the list of instructions.
     */
    public void runStep(){

        InstructionInfo instruction = instructionList.removeFirst();
        options.get(instruction.getOperation()).apply(instruction);
        currentTime++;
    }

    /**
     * <p>
     *     Method to calculate the total amount of times processes have written to RAM and persistent memory.
     * </p>
     *
     * <p>
     *     Returns results as Array of 2 integer values. First the amount of times pages were written to RAM, secondly
     *     how many times pages were written from RAM to persistent memory.
     * </p>
     *
     * @return  Array containing 2 integers.
     */
    public int[] getTotalCounters(){

        int[] counters = new int[]{0, 0};

        for(Process process: processList){
            counters[0] += process.getWriteTos();
            counters[1] += process.getWriteBacks();
        }

        return counters;
    }
}
