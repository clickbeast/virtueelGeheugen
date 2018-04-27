package VirtueelGeheugen.UserInterface;

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

    }



    public void processSimulationState(SimulationState simulationState) {

        //TODO !!!

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
