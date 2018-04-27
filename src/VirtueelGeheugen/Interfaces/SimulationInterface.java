package VirtueelGeheugen.Interfaces;

public interface SimulationInterface {
    void writeToRAM();
    void writeToPersistent();
    void addressTranslated(int physicalAddress);
}
