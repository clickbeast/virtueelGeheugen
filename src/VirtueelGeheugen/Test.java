package VirtueelGeheugen;

import VirtueelGeheugen.DataProcessing.Processing.XMLProcessor;
import VirtueelGeheugen.Simulation.SimulationManager;
import VirtueelGeheugen.Simulation.SimulationState;

public class Test {

    public static void main(String[] args){

        SimulationManager manager = new SimulationManager();
        manager.setInstructionList(new XMLProcessor().generateProcessListBasedOnXML("Instructions_20000_4.xml"));
        //manager.runDemandPaging();
        SimulationState simulationState;
        simulationState = manager.runStep();
        while (simulationState != null){
            simulationState = manager.runStep();
        }
    }
}
