package VirtueelGeheugen;

import VirtueelGeheugen.Simulation.SimulationManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowViewController implements Initializable {

    public Scene scene;

    //handles the main simulation of the RAM etc.
    private SimulationManager simulationManager;
    private HistoryManager historyManager;


    /**
        VARIABLES -  ELEMENTS

     */


    //Top Control Bar
    public Label alertInfoLabel;
    public ChoiceBox choiceBox;
    public Button executeAllInstructionsButton;
    public Button executeNextInstructionButton;
    public Button backwardHistoryButton;
    public Button forwardHistoryButton;
    public Button playHistoryButton;


    //Summary Windows
    public Label timer;
    public Label totalAmountOfWritesToRamLabel;
    public Label totalAmountsOfWritesToPercistentLabel;

    //STATE WINDOW
    public TitledPane currentStatePane;


    //Current instruction
    public Label currentInstructionOperationLabel;
    public Label currentInstructionVirtualAdressLabel;
    public Label currentInstructionPhysicalAdressLabel;
    public Label currentInstructionProcessIdLabel;
    public Label currentInstructionProcessWritesToRamLabel;
    public Label currentInstructionProcessWritesToPercistentLabel;


    //Previous Instrcution 
    public Label previousInstructionOperationLabel;
    public Label previousInstructionVirtualAdressLabel;
    public Label previousInstructionPhysicalAdressLabel;
    public Label previousInstructionProcessIdLabel;
    public Label previousInstructionProcessWritesToRamLabel;
    public Label previousInstructionProcessWritesToPercistentLabel;



    //TODO
    //LIST VIEW MET MEERDERE TABELLEN => Zoek op JAVFX


    //page table represenatation  //3 columns
    //public ListView<Label>;


    //ram  representation   //2 culumns
    //public ListView<Label>;

    //Last removed Process
    public Label lastRemovedProcessIdLabel;
    public Label lastRemovedProcessAmountOfWritesToRam;
    public Label lastRemovedProcessAmountOfWritesToPercistent;


    //View Option Bar
    public ToggleButton highlightPagesButton;
    public ToggleButton highlightProcessesButton;










    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.configureUIElements();
    }


    //Configures all Elements when starting the application
    public void configureUIElements() {



    }

    //get called when Main is finshed with doing its things
    public void startFinished() {

    }







    /**
     *  History Actions
     *
     */



    public void runNextInstruction() {

        //DISABLE UI
        this.freezeUI();


        this.forwardHistoryButton.setDisable(true);
        this.historyManager.backToPresent();
        this.unFreezeUI();

    }

    //Runs each instruction
    public void runAllInstructions() {
        //output summary results
        

    }


    public void backHistoryAction(ActionEvent actionEvent) {

    }


    public void forwardHistoryAction(ActionEvent actionEvent) {

    }


    //TODO wanneer nog tijd voor
    public void playHistoryBackAction(ActionEvent actionEvent) {
        //preparing for playing historyManager

        for(UIState uiState: this.historyManager.getPreviousStates()) {
            this.fillUIBasedOnNewState(uiState);
        }
    }


    /**
     *
     * UI CONFIG
     *
     */


    /**
     *
     * UI FILL
     *
     */


    //changes the content being shown by the UI
    public void fillUIBasedOnNewState(UIState uiState) {

    }


    public void fillRamRepresenation() {


    }

    public void fillPageTableRepresentation() {



    }

    /**
     *
     * UI CHANGES
     *
     *
     */






    //TODO ACTIONS  FOR  COLOR TOGGLE




    //Updates the current summary on screen
    public void updateDisplayInfo() {
        

    }








    //prevent user from using History, commonly used while running algorithm
    public void freezeUI() {

    }

    //allow user to use History again
    public void unFreezeUI() {


    }



    /**
     *  Alerts
     */


    //display info messages in the History while running the app
    public void displayInfoMessage(String message) {
        alertInfoLabel.setText("Info: " + message);
    }


    public void setSimulationManager(SimulationManager simulationManager) {
        this.simulationManager = simulationManager;
    }



}
