package VirtueelGeheugen.Simulation;

import VirtueelGeheugen.DataProcessing.Containers.InstructionInfo;
import VirtueelGeheugen.DataProcessing.Containers.InstructionList;
import VirtueelGeheugen.Interfaces.SimulationInterface;
import VirtueelGeheugen.MainWindowViewController;
import VirtueelGeheugen.Simulation.Hardware.PageTable.PageTable;
import VirtueelGeheugen.Simulation.Hardware.PageTable.PageTableEntry;
import VirtueelGeheugen.Simulation.Hardware.Ram.Frame;
import VirtueelGeheugen.Simulation.Hardware.Ram.RAM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.function.Function;

import static VirtueelGeheugen.Simulation.Hardware.PageTable.PageTable.getOffset;
import static VirtueelGeheugen.Simulation.Hardware.PageTable.PageTable.translateAddressToPage;

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

    public void runDemandPaging(){
        ram.setPrePaging(false);
    }

    public void runPrePaging(){
        ram.setPrePaging(true);
    }

    /**
     * Run one instruction from the list of instructions.
     */
    public SimulationState runStep(){

        try {

            InstructionInfo instruction = instructionList.removeFirst();
            options.get(instruction.getOperation()).apply(instruction);
            currentTime++;

            Process running = processList.get(instruction.getProcessId());
            int offset = getOffset(instruction.getVirtualAdressValue());
            instruction.setPhysicalAddress(
                    offset + running.getPageTable().get(translateAddressToPage(instruction.getVirtualAdressValue())).getFrameNumber() * 4096
            );
            return new SimulationState(
                    instruction,
                    running,
                    removedProcess,
                    ram.getFrames(),
                    getCurrentTime(),
                    writesToRAM,
                    writesToPersistent
            );
        } catch (NoSuchElementException e){
            System.out.println("=========================================");
            System.out.println(writesToRAM);
            System.out.println(writesToPersistent);
            return null;
        }
    }
}
