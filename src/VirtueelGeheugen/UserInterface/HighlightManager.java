package VirtueelGeheugen.UserInterface;

import VirtueelGeheugen.Simulation.Hardware.PageTable.PageTable;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Takes care of highlithing certain UI Elements when needed to be able to have a nice overview
 */
public class HighlightManager {

    //TODO


    //list of colors
    private ArrayList<String> colors;




    //Reference to tables
    PageTableView pageTableView;
    RamView ramView;


    public HighlightManager(PageTableView pageTableView, RamView ramView) {

        this.colors = new ArrayList<>();


        this.colors.add("#B3C2ED");
        this.colors.add("#D6FCBE");
        this.colors.add("#DDC7A5");
        this.colors.add("#8DC7E1");
        this.colors.add("#EAC8D1");
        this.colors.add("#88AF99");
        this.colors.add("#E7E79B");
        this.colors.add("#FDF1EC");
        this.colors.add("#BCE4B8");
        this.colors.add("#D67E79");
        this.colors.add("#DBEDF7");
        this.colors.add("#BD706C");


        this.pageTableView = pageTableView;
        this.ramView = ramView;
    }




    public void highlightSameProcesses() {
//        HBox hbox  = this.pageTableView.getItems().get(0);
//        hbox.setStyle("-fx-background-color: #D6FCBE");
//
//        for() {
//
//        }

    }
}
