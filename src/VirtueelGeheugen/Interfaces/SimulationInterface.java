package VirtueelGeheugen.Interfaces;

/**
 * Interface used to communicate from process back to the simulation.
 */
public interface SimulationInterface {
    void writeToRAM();
    void writeToPersistent();
}
