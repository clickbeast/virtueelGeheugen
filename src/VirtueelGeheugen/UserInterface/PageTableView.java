package VirtueelGeheugen.UserInterface;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class PageTableView extends ListView<HBox> {


    public PageTableView() {
        super();


        //generate HBOX


        HBox hbox = new HBox();

        //Label config

        Label c1 = new Label("bonjour");
        Label c2 = new Label("bonjour");
        Label c3 = new Label("bonjour");
        Label c4 = new Label("bonjour");

        //Wrap in HBOX again
        HBox w1 = new HBox(c1);
        HBox w2 = new HBox(c2);
        HBox w3 = new HBox(c3);
        HBox w4 = new HBox(c4);


        HBox.setHgrow(w1, Priority.ALWAYS);
        HBox.setHgrow(w2, Priority.ALWAYS);
        HBox.setHgrow(w3, Priority.ALWAYS);
        HBox.setHgrow(w4, Priority.ALWAYS);


        hbox.getChildren().add(w1);
        hbox.getChildren().add(w2);
        hbox.getChildren().add(w3);
        hbox.getChildren().add(w4);



        this.getItems().add(hbox);


        ObservableList<String> names = FXCollections.observableArrayList(
                "Julia", "Ian", "Sue", "Matthew", "Hannah", "Stephan", "Denise");




        this.getItems().add(hbox);


        this.setPlaceholder(new Label("No Content In List"));



        System.out.println("GENERATIN DONE");

    }


    public void configurePageTable() {

    }


    //import the right data
    public void fillWithData() {

    }

    public void prepareCell() {


    }
}
