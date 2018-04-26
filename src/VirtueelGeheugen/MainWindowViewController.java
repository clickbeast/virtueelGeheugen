package VirtueelGeheugen;

import VirtueelGeheugen.Simulation.Hardware.PageTable.PageTable;
import VirtueelGeheugen.Simulation.SimulationManager;
import VirtueelGeheugen.Simulation.SimulationState;
import VirtueelGeheugen.UserInterface.HistoryManager;
import VirtueelGeheugen.UserInterface.RamView;
import VirtueelGeheugen.UserInterface.UIState;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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


    //public TitledPane currentPageTablePane;
    //public PageTable pageTableView;


    public RamView ramView;

    //Last removed Process
    public Label lastRemovedProcessIdLabel;
    public Label lastRemovedProcessAmountOfWritesToRam;
    public Label lastRemovedProcessAmountOfWritesToPercistent;


    //View Option Bar
    public ToggleButton highlightPagesButton;
    public ToggleButton highlightProcessesButton;










    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initializing...");
        this.configureUIElements();

        //Confiure Hisotry Manager
        this.historyManager = new HistoryManager();


    }


    //Configures all Elements when starting the application
    public void configureUIElements() {
        System.out.println("Configuring UI Elements");

        //configure xml select button
        choiceBox.setItems(FXCollections.observableArrayList("30 Instructies","20000_4 Instructies","20 000_20 Instructies"));
        choiceBox.getSelectionModel().selectFirst();


        //keep the xml xhoice box in het oog wanneer er iets veranderd
        choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                switch ((int) newValue){
                    case 0:

                        break;
                    case 1:

                        break;
                    default:

                        break;
                }


            }
        });


        //configure list views



        //configure others

    }

    //get called when Main is finshed with doing its things
    public void startFinished() {
        System.out.println("Finished setup of program");

    }


    public void setSimulationManager(SimulationManager simulationManager) {
        this.simulationManager = simulationManager;
    }





    /**
     * UI ACTIONS
     *
     */



    public void runNextInstruction() {

        System.out.println("Running next instruction");

        //DISABLE UI
        this.freezeUI();

        //get the new simulatoon state after instruction
        SimulationState simulationState = null;

        //If simulation state is zero then we have done the complete simulation , give message to user and reset



        //generate UI Appropiate data based on current simulation state
        UIState newUIState = new  UIState(simulationState);

        this.updateUIBasedOnNewState(newUIState);
        this.historyManager.addNewState(newUIState);
        this.disableCheck();
        this.unFreezeUI();

    }

    //Runs each instruction
    public void runAllInstructions() {

        System.out.println("Running All Instructions after eachother");

        new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure you want to run all Instructions? Current running simulation will be removed and history will be lost.",
                ButtonType.CANCEL,ButtonType.OK
        ).showAndWait();




        //output summary results
        //TODO WAIT FOR JONAS





    }


    public void backHistoryAction(ActionEvent actionEvent) {
        this.updateUIBasedOnNewState(this.historyManager.previousState());
    }

    public void forwardHistoryAction(ActionEvent actionEvent) {
        this.updateUIBasedOnNewState(this.historyManager.nextState());
    }


    //TODO wanneer nog tijd voor
    public void playHistoryBackAction(ActionEvent actionEvent) {
        //preparing for playing historyManager

    }


    public void reset() {
        System.out.println("Resetting");

    }



    //gets called when all instructions have been done
    public void runningDone () {
        new Alert(
                Alert.AlertType.INFORMATION,
                "No more instuctions Left. You can restart or browse the hisotry further.",
                ButtonType.OK
        ).showAndWait();
    }

    //XML change Listener




    /**
     *
     * UI CONFIG
     *
     */


    //checks and disables certain buttons when needed
    public void disableCheck() {
        if(historyManager.isFirstState()) {
            this.backwardHistoryButton.setDisable(true);
        }

        if(historyManager.isLastState()) {
            this.forwardHistoryButton.setDisable(true);
        }
    }


    /**
     *
     * UI FILL
     *
     */


    //changes the content being shown by the UI
    public void updateUIBasedOnNewState(UIState uiState) {
        if(uiState != null) {
            //TODO IMPORTANTEEEE
        }
    }


    public void fillRamRepresenation() {
        //TODO

    }

    public void fillPageTableRepresentation() {
        //TODO

    }



    /**
     *
     * UI CHANGES
     *
     *
     */


    //TODO ACTIONS  FOR  COLOR TOGGLE   -> If becomes to ewtended make color editing class etc.

    public void hideAllHighlighting() {
        //reset listviews
    }


    //
    public void highlightSamePages() {

    }

    //
    public void highlightSameProcesses() {

    }

    //
    public void generateUniqueColor() {

    }



    //prevent user from using History, commonly used while running algorithm
    public void freezeUI() {
        //TODO
    }

    //allow user to use History again
    public void unFreezeUI() {
        //TODO

    }



    /**
     *  Alerts
     */


    //display info messages in the History while running the app
    public void displayInfoMessage(String message) {
        alertInfoLabel.setText("Info: " + message);
    }


}
