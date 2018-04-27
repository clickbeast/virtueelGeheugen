package VirtueelGeheugen;

import VirtueelGeheugen.DataProcessing.Processing.XMLProcessor;
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
    public TitledPane currentPageTablePane;

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
    private String document;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initializing...");
        this.configureUIElements();

        //Confiure Hisotry Manager
        this.historyManager = new HistoryManager();
        document = "Instructions_30_3.xml";

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
                            document = "Instructions_30_3.xml";
                            changeXMLWarning();
                            break;
                        case 1:
                            System.out.println("1");
                            document = "Instructions_20000_4.xml";
                            changeXMLWarning();
                            break;
                        default:
                            System.out.println("2");
                            document = "Instructions_20000_20.xml";
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
        SimulationState simulationState = simulationManager.runStep();

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
            this.reset();


            //EXECUTION

            SimulationState simulationState = null;


            if(simulationState != null) {

                //Convert
                UIState uiState = new UIState(simulationState);
                this.historyManager.addNewState(uiState);


                while (simulationState != null) {
                    simulationState = null;
                    this.historyManager.addNewState(new UIState(simulationState));
                }

                //SHOW UI
                this.updateUIBasedOnNewState(this.historyManager.latestState());
            }


        }else{
            //cancel
            System.out.println("Canceled");

        }


    }


    public void backHistoryAction(ActionEvent actionEvent) {
        this.updateUIBasedOnNewState(this.historyManager.previousState());
    }

    public void forwardHistoryAction(ActionEvent actionEvent) {
        this.updateUIBasedOnNewState(this.historyManager.nextState());
    }



    /*
        Gets called when the system wants to start again
     */
    public void reset() {
        System.out.println("RESETTING");
        this.historyManager.reset();


        //reset manager
        simulationManager = new SimulationManager();

        //Load in XML DOC
        System.out.println("Loading XML Document");
        simulationManager.setInstructionList(new XMLProcessor().generateProcessListBasedOnXML("Instructions_20000_20.xml"));



        //reset UI
        this.updateUIBasedOnNewState(new UIState(null));


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
            this.reset();
        }else{
            //do nothing
            System.out.println("NO CALLED");
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

            //SET SUMMARY
            this.timer.setText("Timer: " + String.valueOf(uiState.getTimerValue()));
            this.totalAmountOfWritesToRamLabel.setText(" #Writes To Ram: " + String.valueOf(uiState.getTotalAmountOfWritesToRam()));
            this.totalAmountsOfWritesToPercistentLabel.setText("#Writes to Percitent: " + String.valueOf(uiState.getTotalAmountOfWritesToPercistent()));

            //current instruction
            this.currentInstructionOperationLabel.setText(uiState.getCurrentInstructionOperation());
            this.currentInstructionVirtualAdressLabel.setText(uiState.getCurrentInstructionVirtualAdress());
            this.currentInstructionPhysicalAdressLabel.setText(uiState.getCurrentInstructionPhysicalAdress());
            
            this.currentInstructionProcessIdLabel.setText("Id: " + String.valueOf(uiState.getCurrentInstructionProcessId()));
            this.currentInstructionProcessWritesToRamLabel.setText("#Writes To Ram: " + String.valueOf(uiState.getCurrentInsructionProcessNumberOfWritesToRam()));
            this.currentInstructionProcessWritesToPercistentLabel.setText("#Writes To Percistent: " + String.valueOf(uiState.getCurrentInstructionProcessNumberOfWritesToPercistent()));
            

            //previous insruction

            //check hisotryManager if there is a previous state
            if(this.historyManager.previousState() != null) {
                this.previousInstructionOperationLabel.setText(this.historyManager.previousState().getCurrentInstructionOperation());
                this.previousInstructionVirtualAdressLabel.setText(this.historyManager.previousState().getCurrentInstructionVirtualAdress());
                this.previousInstructionPhysicalAdressLabel.setText(this.historyManager.previousState().getCurrentInstructionPhysicalAdress());

                this.previousInstructionProcessIdLabel.setText("Id: " +
                        String.valueOf(this.historyManager.previousState().getCurrentInstructionProcessId()));
                this.previousInstructionProcessWritesToRamLabel.setText("#Writes To Ram: " +
                        String.valueOf(this.historyManager.previousState().getCurrentInsructionProcessNumberOfWritesToRam()));
                this.previousInstructionProcessWritesToPercistentLabel.setText("#Writes To Percistent: " +
                         String.valueOf(this.historyManager.previousState().getCurrentInstructionProcessNumberOfWritesToPercistent()));

            }

            //pagetable
            this.currentPageTablePane.setText("Page Table Of Executed Process " + "<" + String.valueOf(uiState.getCurrentInstructionProcessId()) + ">");
            this.pageTableView.fillWithData(uiState.getPageTableCells());

            //ram representation
            this.ramView.fillWithData(uiState.getRamTableCells());

            //leaving progress
            this.lastRemovedProcessIdLabel.setText(uiState.getLastProcessRemovedFromRamId());
            this.lastRemovedProcessAmountOfWritesToRam.setText(uiState.getLastProcessRemovedFromRamNumberOfWritesToRam());
            this.lastRemovedProcessAmountOfWritesToPercistent.setText(uiState.getLastProcessRemovedFromRamNumberOfWritesToPercistent());

        }else{
            basicFill();
        }
    }

    public void basicFill() {
        //when no data is present-
        this.currentStatePane.setText("No Instruction executed");
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
