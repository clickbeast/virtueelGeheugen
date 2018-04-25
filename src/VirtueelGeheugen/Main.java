package VirtueelGeheugen;

import VirtueelGeheugen.DataProcessing.Containers.InstructionList;
import VirtueelGeheugen.DataProcessing.Processing.XMLProcessor;
import VirtueelGeheugen.Simulation.SimulationManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private SimulationManager simulationManager;


    //keep a reference to the main  window controller
    public MainWindowViewController mainwindow;


    @Override
    public void start(Stage primaryStage) throws Exception{


        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));

        //provide the controller with a reference of the simulationManager
        loader.setControllerFactory( c -> {
            if(c == MainWindowViewController.class) {
                MainWindowViewController mc = new MainWindowViewController();
                mc.setSimulationManager(this.simulationManager);
                mainwindow = mc;
                return mc;
            }else{
                try {
                    return c.newInstance();
                }catch (Exception exc){
                    throw new RuntimeException(exc);
                }
            }
        });

/*
        Parent flowPane = loader.load();

        primaryStage.setTitle("Scheduler");
        //primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(flowPane, 1000, 700));
        primaryStage.show();
        this.simulationManager.setMainWindowController(mainwindow);
        mainwindow.scene = primaryStage.getScene();
        this.mainwindow.startFinished();
*/
        InstructionList list = (new XMLProcessor().generateProcessListBasedOnXML("Instructions_30_3.xml"));

        /**
         *
         *  RUN YOUR TESTS BELOW THIS , COMMENT  ABOVE CODE WHEN NO NEED FOR UI (temp)
         */

    }


    public static void main(String[] args) {
        launch(args);
    }
}