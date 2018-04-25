package VirtueelGeheugen.Interfaces;

import VirtueelGeheugen.Simulation.Process;

public interface ProcessRAMInterface{
    int add(Process process, int pageNumber);
    void remove(int frame);
}
