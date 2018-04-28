package VirtueelGeheugen.Simulation;

import VirtueelGeheugen.DataProcessing.Containers.InstructionInfo;
import VirtueelGeheugen.Simulation.Hardware.Ram.Frame;

import java.util.ArrayList;


/**
 * Contains needed variables that are useful to the userInterface
 */
public class SimulationState {


    private InstructionInfo instructionInfo;
    private Process processBeingExecuted;
    private Process processLeavingRam;
    private ArrayList<Frame> frames;
    private int clock;
    private int writesToRAM;
    private int writeToPersistent;

    public SimulationState(InstructionInfo instructionInfo, Process processBeingExecuted, Process processLeavingRam, ArrayList<Frame> frames, int clock, int writesToRAM, int writeToPersistent) {
        this.instructionInfo = instructionInfo;
        this.processBeingExecuted = processBeingExecuted;
        this.processLeavingRam = processLeavingRam;
        this.frames = frames;
        this.clock = clock;
        this.writesToRAM = writesToRAM;
        this.writeToPersistent = writeToPersistent;
    }
//======================================================================================================================
    //needed getters & setters

    public InstructionInfo getInstructionInfo() {
        return instructionInfo;
    }

    public Process getProcessBeingExecuted() {
        return processBeingExecuted;
    }

    public Process getProcessLeavingRam() {
        return processLeavingRam;
    }

    public ArrayList<Frame> getFrames() {
        return frames;
    }

    public int getClock() {
        return clock;
    }

    public int getWritesToRAM() {
        return writesToRAM;
    }

    public int getWriteToPersistent() {
        return writeToPersistent;
    }
}
