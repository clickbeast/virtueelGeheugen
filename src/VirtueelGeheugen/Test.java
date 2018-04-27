package VirtueelGeheugen;

import VirtueelGeheugen.DataProcessing.Processing.XMLProcessor;
import VirtueelGeheugen.Simulation.SimulationManager;

public class Test {

    public static void main(String[] args){

        SimulationManager manager = new SimulationManager();
        manager.setInstructionList(new XMLProcessor().generateProcessListBasedOnXML("Instructions_20000_20.xml"));
        manager.runComplete();
    }
}
