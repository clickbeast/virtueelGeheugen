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

    private int currentTime;
    private RAM ram;

    public SimulationManager(){
        this.instructionList = new InstructionList();
        this.processList = new ArrayList<>();
        this.ram = new RAM();


        //HashMap to be more efficient than if-then-else.
        this.options = new HashMap<>();
        this.options.put("Start", (instructionInfo) -> {

            processList.add(instructionInfo.getProcessId(), new Process(instructionInfo.getProcessId()));
            ram.addProcess(processList.get(instructionInfo.getProcessId()), getCurrentTime(), getCurrentTime());

            return null;
        });
        this.options.put("Terminate", (instructionInfo) -> {

            Process process = processList.remove(instructionInfo.getProcessId());
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
//======================================================================================================================
    //getters

    public int getCurrentTime() {
        return currentTime;
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
     * Run one instruction from the list of instructions.
     */
    public void runStep(){

        InstructionInfo instruction = instructionList.removeFirst();
        options.get(instruction.getOperation()).apply(instruction);
        currentTime++;
    }


}
