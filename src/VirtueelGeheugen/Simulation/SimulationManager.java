package VirtueelGeheugen.Simulation;

import VirtueelGeheugen.DataProcessing.Containers.InstructionInfo;
import VirtueelGeheugen.DataProcessing.Containers.InstructionList;
import VirtueelGeheugen.Interfaces.SimulationInterface;
import VirtueelGeheugen.MainWindowViewController;
import VirtueelGeheugen.Simulation.Hardware.Ram.Frame;
import VirtueelGeheugen.Simulation.Hardware.Ram.RAM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class SimulationManager {
//======================================================================================================================
    //simulationInterface

    SimulationInterface simulationInterface = new SimulationInterface() {
        @Override
        public void writeToRAM() {
            writesToRAM++;
        }

        @Override
        public void writeToPersistent() {
            writesToPersistent++;
        }

        @Override
        public void addressTranslated(int physicalAddress){

        }
    };
//======================================================================================================================
    //class specs

    private MainWindowViewController mainWindowController;

    private InstructionList instructionList;
    private ArrayList<Process> processList;

    private HashMap<String, Function<InstructionInfo, Process>> options;

    private int currentTime;
    private RAM ram;
    private SimulationState nextState;
    private int writesToRAM;
    private int writesToPersistent;
    private Process removedProcess;

    public SimulationManager(){
        this.instructionList = new InstructionList();
        this.processList = new ArrayList<>();
        this.ram = new RAM();
        this.writesToRAM = 0;
        this.writesToPersistent = 0;
        this.removedProcess = null;

        //HashMap to be a little bit more efficient compared to if-then-else.
        this.options = new HashMap<>();
        this.options.put("Start", (instructionInfo) -> {

            Process process = new Process(instructionInfo.getProcessId());
            process.setProcessRAMInterface(ram.getProcessToRAMInterface());
            process.setSimulationInterface(simulationInterface);
            processList.add(instructionInfo.getProcessId(), process);
            removedProcess = ram.addProcess(process, 0, getCurrentTime());

            return null;
        });
        this.options.put("Terminate", (instructionInfo) -> {

            removedProcess = processList.get(instructionInfo.getProcessId());
            removedProcess.removeAllPagesFromRAM();
            ram.terminate(removedProcess, getCurrentTime());
            return removedProcess;
        });
        this.options.put("Read", (instructionInfo) -> {

            Process process = processList.get(instructionInfo.getProcessId());
            removedProcess = ram.read(process, instructionInfo.getVirtualAdressValue(), getCurrentTime());
            return null;
        });
        this.options.put("Write", (instructionInfo) -> {

            Process process = processList.get(instructionInfo.getProcessId());
            removedProcess = ram.write(process, instructionInfo.getVirtualAdressValue(), getCurrentTime());
            return null;
        });
    }

    public void setMainWindowController(MainWindowViewController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    public void setInstructionList(InstructionList instructionList) {
        this.instructionList = instructionList;
    }
//======================================================================================================================
    //getters

    public int getCurrentTime() {
        return currentTime;
    }

    public ArrayList<Process> getProcessList() {
        return processList;
    }
//======================================================================================================================
    //private functions
//======================================================================================================================
    //public functions

    /**
     * Run entire XML file at once and display results at the end.
     */
    public void runComplete(){

        Long start = System.currentTimeMillis();
        System.out.println(start);
        while(!instructionList.isEmpty()) runStep();
        System.out.println(System.currentTimeMillis() - start);
    }

    /**
     * Run one instruction from the list of instructions.
     */
    public SimulationState runStep(){

        InstructionInfo instruction = instructionList.removeFirst();
        if(instruction.getOperation().equals("Terminate")) {
            print(instruction);
        }
        options.get(instruction.getOperation()).apply(instruction);

        if(instruction.getOperation().equals("Terminate")) {
            print(instruction);
        }
        currentTime++;
        return new SimulationState(
                instruction,
                processList.get(instruction.getProcessId()),
                removedProcess,
                ram.getFrames(),
                getCurrentTime(),
                writesToRAM,
                writesToPersistent
        );
    }
//======================================================================================================================
    //debug //TODO remove

    private void print(InstructionInfo instructionInfo){

        System.out.println("instruction:");
        System.out.println("\tprocess id: " + instructionInfo.getProcessId() + " | instruction code : " + instructionInfo.getOperation() + " | address: " + instructionInfo.getVirtualAdressValue());

        System.out.println();
        System.out.print("\t\t");
        for(int i = 0; i < ram.getFrames().size(); i++){
            System.out.print(i + "\t");
        }
        System.out.println();
        System.out.print("pid: \t");
        for(Frame frame: ram.getFrames()){
            try {
                System.out.print(frame.getProcess().getpId() + "\t");
            } catch (NullPointerException e){
                System.out.print("\t");
            }
        }
        System.out.println();
        System.out.print("page: \t");
        for(Frame frame: ram.getFrames()){
            System.out.print(frame.getPageNumber() + "\t");
        }

        System.out.println();
        System.out.println("==============================================================");
    }
}
