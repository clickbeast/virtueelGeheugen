package VirtueelGeheugen.Interfaces;

import VirtueelGeheugen.Simulation.Process;

public interface ProcessRAMInterface{
    int add(Process process, int pageNumber);
    void overWrite(Process process, int pageNumber, int frame);
    void remove(int frame);
}
