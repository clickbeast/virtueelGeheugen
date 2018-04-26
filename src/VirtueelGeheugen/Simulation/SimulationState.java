package VirtueelGeheugen.Simulation;

import VirtueelGeheugen.DataProcessing.Containers.InstructionInfo;
import VirtueelGeheugen.Simulation.Hardware.PageTable.PageTable;



/**
 *
 * Contains needed variables that are usefull to the userInterface
 */
public class SimulationState {


    private InstructionInfo instructionInfo;
    private PageTable pageTable;
    private Process processBeingExecuted;
    private Process processLeavingRam;


    //reference to the simulationManagerItself
    private SimulationManager simulationManager;


    /**
     *
     * @param instructionInfo REQUIRED
     * @param pageTable REQUIRED
     * @param processBeingExecuted REQUIRED
     * @param processLeavingRam    OPTIONAL
     * @param simulationManager  REQUIRED
     */
    public SimulationState(InstructionInfo instructionInfo, PageTable pageTable, Process processBeingExecuted, Process processLeavingRam, SimulationManager simulationManager) {
        this.instructionInfo = instructionInfo;
        this.pageTable = pageTable;
        this.processBeingExecuted = processBeingExecuted;
        this.processLeavingRam = processLeavingRam;
        this.simulationManager = simulationManager;
    }


}
