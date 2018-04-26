package VirtueelGeheugen.UserInterface;

import VirtueelGeheugen.DataProcessing.Containers.InstructionInfo;
import VirtueelGeheugen.Simulation.Hardware.PageTable.PageTable;
import VirtueelGeheugen.Simulation.Process;
import VirtueelGeheugen.Simulation.SimulationManager;
import VirtueelGeheugen.Simulation.SimulationState;

//keeps the text data that is or was on screen.
public class UIState {

    //DATA HERE
    private int timerValue;
    private int totalAmountOfWritesToRam;
    private int totalAmountOfWritesToPercistent;

    private String currentInstructionOperation;
    private String currentInstructionVirtualAdress;
    private String currentInstructionPhysicalAdress;

    private int currentInstructionProcessId;

    private String lastProcessRemovedFromRamId;
    private String lastProcessRemovedFromRamNumberOfWritesToRam;
    private String getLastProcessRemovedFromRamNumberOfWritesToPercistent;



    //PageTableData

    //Ram Data

//    public void configureStateWithData(InstructionInfo instructionInfo, PageTable pageTable, Process processBeingExcuted, int totalAmountOfWritesToPercistent, int totalAmountOfWritesToRam) {
//
//
//    }

    public UIState(SimulationState simulationState) {
        //fill and calculate what's needed



    }

    //TODO could be moved to simulationManager eventueel


    private int calculateTotalAmountOfWritesToRam(SimulationManager simulationManager) {
        System.out.println("Calculating total amounts of writes to ram");
        int count = 0;

//        //TODO
//        for(Process process: null)  {
//
//
//        }

        return count;
    }

    private int calculateTotalAmountOfWritesToPercistent(SimulationManager simulationManager) {

        System.out.println("Calculating total amounts of writes to percistent");
        int count = 0;


        return count;
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

    public String getGetLastProcessRemovedFromRamNumberOfWritesToPercistent() {
        return getLastProcessRemovedFromRamNumberOfWritesToPercistent;
    }
}
