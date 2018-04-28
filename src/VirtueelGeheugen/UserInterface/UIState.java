package VirtueelGeheugen.UserInterface;

import VirtueelGeheugen.Simulation.Hardware.PageTable.PageTableEntry;
import VirtueelGeheugen.Simulation.Hardware.Ram.Frame;
import VirtueelGeheugen.Simulation.SimulationManager;
import VirtueelGeheugen.Simulation.SimulationState;
import VirtueelGeheugen.UserInterface.ListObjects.PageTableData;
import VirtueelGeheugen.UserInterface.ListObjects.RamTableData;

import java.util.ArrayList;

//keeps the text data that is or was on screen.
public class UIState {

    //DATA HERE
    private int timerValue = 0;
    private int totalAmountOfWritesToRam = 0;
    private int totalAmountOfWritesToPercistent = 0;

    private String currentInstructionOperation = "";
    private String currentInstructionVirtualAdress = "";
    private String currentInstructionPhysicalAdress = "";

    private int currentInstructionProcessId = 0;
    private int currentInsructionProcessNumberOfWritesToRam = 0;
    private int currentInstructionProcessNumberOfWritesToPercistent = 0;

    private String lastProcessRemovedFromRamId = "";
    private String lastProcessRemovedFromRamNumberOfWritesToRam= "";
    private String lastProcessRemovedFromRamNumberOfWritesToPercistent = "";


    //PageTableData
    private ArrayList<PageTableData> pageTableCells;
    private ArrayList<RamTableData> ramTableCells;


    //Ram Data

//    public void configureStateWithData(InstructionInfo instructionInfo, PageTable pageTable, Process processBeingExcuted, int totalAmountOfWritesToPercistent, int totalAmountOfWritesToRam) {
//
//
//    }

    public UIState(SimulationState simulationState) {
        //fill and calculate what's needed
        this.pageTableCells = new ArrayList<>();
        this.ramTableCells = new ArrayList<>();

        //Process simulation state
        if(simulationState != null) {
            processSimulationState(simulationState);
        }
    }


    public void processSimulationState(SimulationState simulationState) {

        System.out.println("READING SIMULATION STATE");
        System.out.println("- - - - - - -");

        this.timerValue = simulationState.getClock();
        this.totalAmountOfWritesToPercistent = simulationState.getWriteToPersistent();
        this.totalAmountOfWritesToRam = simulationState.getWritesToRAM();


        this.currentInstructionOperation = simulationState.getInstructionInfo().getOperation();
        this.currentInstructionVirtualAdress = String.valueOf(simulationState.getInstructionInfo().getVirtualAdressValue());
        this.currentInstructionPhysicalAdress = String.valueOf(simulationState.getInstructionInfo().getPhysicalAddress());

        this.currentInstructionProcessId = simulationState.getInstructionInfo().getProcessId();
        this.currentInsructionProcessNumberOfWritesToRam = simulationState.getProcessBeingExecuted().getWriteTos();
        this.currentInstructionProcessNumberOfWritesToPercistent = simulationState.getProcessBeingExecuted().getWriteBacks();

        //fill page table
        for(PageTableEntry pageTableEntry:  simulationState.getProcessBeingExecuted().getPageTable()) {

            String presentBit = String.valueOf(pageTableEntry.isPresent() ? 1 : 0);
            String modifyBit = String.valueOf(pageTableEntry.isModified() ? 1 : 0);

            this.pageTableCells.add(new PageTableData(
                    presentBit
                    ,modifyBit
                    ,String.valueOf(pageTableEntry.getLastAccessTime())
                    ,String.valueOf(pageTableEntry.getFrameNumber())));
        }


        //fill ram table
        for(Frame frame:  simulationState.getFrames()) {

            if(frame.getProcess() != null) {
                this.ramTableCells.add(new RamTableData(String.valueOf(frame.getPageNumber()), String.valueOf(frame.getProcess().getpId())));
            }else{
                this.ramTableCells.add(new RamTableData("",""));
            }
        }

        //fill previously executed

        if(simulationState.getProcessLeavingRam() != null) {
            this.lastProcessRemovedFromRamId = String.valueOf(simulationState.getProcessLeavingRam().getpId());
            this.lastProcessRemovedFromRamNumberOfWritesToRam = String.valueOf(simulationState.getProcessLeavingRam().getWriteTos());
            this.lastProcessRemovedFromRamNumberOfWritesToPercistent = String.valueOf(simulationState.getProcessLeavingRam().getWriteBacks());
        }

    }



    //
    // GETTERS
    // - - - - - -


    public int getTimerValue() {
        return timerValue;
    }

    public int getTotalAmountOfWritesToRam() {
        return totalAmountOfWritesToRam;
    }

    public int getTotalAmountOfWritesToPercistent() {
        return totalAmountOfWritesToPercistent;
    }

    public String getCurrentInstructionOperation() {
        return currentInstructionOperation;
    }

    public String getCurrentInstructionVirtualAdress() {
        return currentInstructionVirtualAdress;
    }

    public String getCurrentInstructionPhysicalAdress() {
        return currentInstructionPhysicalAdress;
    }

    public int getCurrentInstructionProcessId() {
        return currentInstructionProcessId;
    }

    public String getLastProcessRemovedFromRamId() {
        return lastProcessRemovedFromRamId;
    }

    public String getLastProcessRemovedFromRamNumberOfWritesToRam() {
        return lastProcessRemovedFromRamNumberOfWritesToRam;
    }

    public String getLastProcessRemovedFromRamNumberOfWritesToPercistent() {
        return lastProcessRemovedFromRamNumberOfWritesToPercistent;
    }

    public ArrayList<PageTableData> getPageTableCells() {
        return pageTableCells;
    }

    public ArrayList<RamTableData> getRamTableCells() {
        return ramTableCells;
    }

    public int getCurrentInsructionProcessNumberOfWritesToRam() {
        return currentInsructionProcessNumberOfWritesToRam;
    }

    public int getCurrentInstructionProcessNumberOfWritesToPercistent() {
        return currentInstructionProcessNumberOfWritesToPercistent;
    }
}
