package VirtueelGeheugen;

import VirtueelGeheugen.Simulation.Hardware.PageTable.PageTable;
import VirtueelGeheugen.Simulation.SimulationManager;
import VirtueelGeheugen.Simulation.SimulationState;
import VirtueelGeheugen.UserInterface.HistoryManager;
import VirtueelGeheugen.UserInterface.PageTableView;
import VirtueelGeheugen.UserInterface.RamView;
import VirtueelGeheugen.UserInterface.UIState;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Optional;
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

    //contains the view for pagetable // ram
    public PageTableView pageTableView;
    public RamView ramView;


    public AnchorPane pageTableAnchorPane;
    public AnchorPane ramTableAnchorPane;



    //Last removed Process
    public Label lastRemovedProcessIdLabel;
    public Label lastRemovedProcessAmountOfWritesToRam;
    public Label lastRemovedProcessAmountOfWritesToPercistent;


    //View Option Bar
    public ToggleButton highlightPagesButton;
    public ToggleButton highlightProcessesButton;

    //toolbar itself
    public ToolBar topToolBar;


    private int currentSelectedXMLIndex = 0;




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
        choiceBox.getSelectionModel().getSelectedIndex();
        this.currentSelectedXMLIndex = choiceBox.getSelectionModel().getSelectedIndex();

        //keep the xml xhoice box in het oog wanneer er iets veranderd
        choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                System.out.println("Preparing to load in new fxml -> generate warning");

                if(currentSelectedXMLIndex != newValue.intValue()) {
                    switch ((int) newValue) {
                        case 0:
                            System.out.println("0");
                            System.out.println("Preparing to load in new fxml -> generate warning");
                            changeXMLWarning();
                            break;
                        case 1:
                            System.out.println("1");
                            changeXMLWarning();
                            break;
                        default:
                            System.out.println("2");
                            changeXMLWarning();
                            break;
                    }
                }


            }




        });

        this.ramView = new RamView();
        this.pageTableView = new PageTableView();


        //configure bounds
        AnchorPane.setBottomAnchor(this.pageTableView, -10.0);
        AnchorPane.setTopAnchor(this.pageTableView,-10.0);
        AnchorPane.setRightAnchor(this.pageTableView,-10.0);
        AnchorPane.setLeftAnchor(this.pageTableView,-10.0);

        AnchorPane.setBottomAnchor(this.ramView, -10.0);
        AnchorPane.setTopAnchor(this.ramView,-10.0);
        AnchorPane.setRightAnchor(this.ramView,-10.0);
        AnchorPane.setLeftAnchor(this.ramView,-10.0);

        //configure list views
        this.pageTableAnchorPane.getChildren().add(pageTableView);
        this.ramTableAnchorPane.getChildren().add(ramView);



        //configure others


    }

    //Genertates a warning when the user is about to change xml file
    public void changeXMLWarning() {


        Optional<ButtonType> result = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure you want to change XML? Current Simulation Will be removed. And History will be lost",
                ButtonType.CANCEL,ButtonType.OK
        ).showAndWait();


        if(result.get() == ButtonType.OK) {
            System.out.println("Resetting and loading new xml");
            //TODO load in right XM
            this.currentSelectedXMLIndex = this.choiceBox.getSelectionModel().getSelectedIndex();

            this.reset();
        }else{
            //cancel
            System.out.println("CANCEL");
            this.choiceBox.getSelectionModel().select(this.currentSelectedXMLIndex);
        }
    }





    //get called when Main is finshed with doing its things
    public void startFinished() {
        System.out.println("Finished setup of program");

        //check how the buttons of the htsory have to be configured
        this.disableCheck();
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
        if(simulationState == null) {
            this.runningDoneAlert();
        }else {
            //generate UI Appropiate data based on current simulation state
            UIState newUIState = new UIState(simulationState);

            this.updateUIBasedOnNewState(newUIState);
            this.historyManager.addNewState(newUIState);

        }

        this.disableCheck();
        this.unFreezeUI();


//        if(simulationState == null) {
//            this.executeNextInstructionButton.setDisable(true);
//        }

    }

    //Runs each instruction
    public void runAllInstructions() {

        System.out.println("Running All Instructions after eachother");

        Optional<ButtonType> result = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure you want to run all Instructions? Current running simulation will be removed and history will be lost.",
                ButtonType.CANCEL,ButtonType.OK
        ).showAndWait();


        if(result.get() == ButtonType.OK) {
            System.out.println("Resetting everything" +
                    "");
            //TODO RUN ALL INSTRUCTIONS CALL A RESET
            this.reset();
        }else{
            //cancel



        }

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



    /*
        Gets called when the system wants to start again
     */
    public void reset() {
        System.out.println("Resetting");
        this.historyManager.reset();

        //Load in XML DOC
        System.out.println("Loading XML Document");


        //enable evetyhing again ready for use
        this.unFreezeUI();
        this.disableCheck();

    }




    //gets called when all instructions have been done
    public void runningDoneAlert () {
        Optional<ButtonType> result = new Alert(
                Alert.AlertType.INFORMATION,
                "No more instuctions Left. Do you wish to restart?",
                ButtonType.NO, ButtonType.YES
        ).showAndWait();

        if(result.get() == ButtonType.YES) {
            System.out.println("Resetting everything" + "");
            //TODO RUN ALL INSTRUCTIONS CALL A RESET


            //TODO CHANGE BUTTON TO RESTART BUTTON


            this.reset();
        }else{
            //do nothing

        }
    }





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

            if(historyManager.isPresent()) {
                this.currentStatePane.setText("Current State");

            }else{
                this.currentStatePane.setText("Viewing History State");

            }



        }else{
            basicFill();
        }
    }

    public void basicFill() {
        //when no data is present-
        this.currentStatePane.setText("No Instruction executed");
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
        this.forwardHistoryButton.setDisable(true);
        this.backwardHistoryButton.setDisable(true);
        this.executeNextInstructionButton.setDisable(true);
        this.executeAllInstructionsButton.setDisable(true);
        this.topToolBar.setDisable(true);


    }

    //allow user to use History again
    public void unFreezeUI() {
        this.forwardHistoryButton.setDisable(false);
        this.backwardHistoryButton.setDisable(false);
        this.executeNextInstructionButton.setDisable(false);
        this.executeAllInstructionsButton.setDisable(false);
        this.topToolBar.setDisable(false);
    }


    //TODO
    //Configure toggle buttons here



    /**
     *  Alerts
     */


    //display info messages in the History while running the app
    public void displayInfoMessage(String message) {
        alertInfoLabel.setText("Info: " + message);
    }


}
