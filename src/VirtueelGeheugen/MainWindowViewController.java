package VirtueelGeheugen;

import VirtueelGeheugen.Simulation.SimulationManager;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowViewController implements Initializable {

    public Scene scene;

    /**
        VARIABLES
     */

    public Label alertInfoLabel;
    public ChoiceBox choiceBox;

    //handles the main simulation of the RAM etc.
    private SimulationManager simulationManager;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceBox.setItems(FXCollections.observableArrayList("10000 Processen","50000 Processen"));
        choiceBox.getSelectionModel().selectFirst();
    }

    public void startFinished() {

    }




    /**
     *  UI Actions
     *
     */

    public void runNextInstruction() {
        //ask simulation manager to exacute next insturction

        //Read state change that is of interest
        //ATTENTION in case talkback needs to happen even more instant include this class
        //and refernce to it in simulationmanager etc.



    }

    //Runs each instruction
    public void runAllInstructions() {
        //output summary results
    }


    //Updates the current summary on screen
    public void updateDisplayInfo() {


    }



    //prevent user from using UI, commonly used while running algorithm
    public void freezeUI() {

    }

    //allow user to use UI again
    public void unFreezeUI() {

    }







    /**
     *  Alerts
     */


    //display info messages in the UI while running the app
    public void displayInfoMessage(String message) {
        alertInfoLabel.setText("Info: " + message);
    }


    public void setSimulationManager(SimulationManager simulationManager) {
        this.simulationManager = simulationManager;
    }


}
