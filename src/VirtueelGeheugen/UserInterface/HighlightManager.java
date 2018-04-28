package VirtueelGeheugen.UserInterface;

import VirtueelGeheugen.Simulation.Hardware.PageTable.PageTable;
import VirtueelGeheugen.UserInterface.ListObjects.HBoxRam;
import VirtueelGeheugen.UserInterface.ListObjects.RamTableData;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private String aDefault;


    public HighlightManager(PageTableView pageTableView, RamView ramView) {
        System.out.println("Setting up Highlight Manager");
        this.aDefault = "highlightPage";
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
        this.colors.add("#B7C3ED");


        this.pageTableView = pageTableView;
        this.ramView = ramView;


    }




    public void highlightSameProcesses() {
        int currentColorIndex = 0;
        Map<String,String> colorMapProcess  = new HashMap<>();
        for(HBoxRam hBoxRam: this.ramView.getItems()) {
            if(!colorMapProcess.containsKey(hBoxRam.getProcessId())) {
               colorMapProcess.put(hBoxRam.getProcessId(),this.colors.get((currentColorIndex)% this.colors.size()));
               currentColorIndex++;
            }
            hBoxRam.setStyle("-fx-background-color:" + colorMapProcess.get(hBoxRam.getProcessId()) + ";" + "-fx-background-radius: 4");
        }

    }

    public void highlightSameFramesAndPages() {
        int currentColorIndex = 0;
        Map<String,String> colorMapPages = new HashMap<>();


        for(HBoxRam hBoxRam: this.ramView.getItems()) {
            if(hBoxRam.getPageId().matches(".*\\d+.*")) {

                if(Integer.parseInt(hBoxRam.getProcessId()) == this.pageTableView.getProcessId()) {

                    if (!colorMapPages.containsKey(hBoxRam.getPageId())) {
                        colorMapPages.put(hBoxRam.getPageId(), this.colors.get((currentColorIndex) % this.colors.size()));
                        currentColorIndex++;
                    }

                    //check if its a number
                    hBoxRam.setStyle("-fx-background-color:" + colorMapPages.get(hBoxRam.getPageId()) + ";" + "-fx-background-radius: 4");

                    //highlight pagtable cell with index and color
                    System.out.println(hBoxRam.getPageId());
                    this.highlightPageTableCellWithIndexAndColor(Integer.parseInt(hBoxRam.getPageId()), colorMapPages.get(hBoxRam.getPageId()));


                    //highlight inside pagtable too
                }
            }
        }


    }

    public void highlightPageTableCellWithIndexAndColor(int index, String color) {

        int indexPage = -1;
        System.out.println(index);
        //eerste overslagen beceause of placehgolder cell
        for(HBox hbox: this.pageTableView.getItems()) {
            System.out.println(color);
            if(index == indexPage) {
                hbox.setStyle("-fx-background-color:" + color + ";" + "-fx-background-radius: 4");
                //hbox.setStyle("-fx-background-radius: 4");

            }
            indexPage++;
        }
    }

    public void removeHighlights() {
        for(HBox hbox: this.pageTableView.getItems()) {
            hbox.setStyle("");
        }
        for(HBoxRam hBoxRam: this.ramView.getItems()) {
            hBoxRam.setStyle("");
        }
    }

    public void setDefault(String aDefault) {
        this.aDefault = aDefault;
    }

    public void highlightDefault() {

        if(aDefault.equals("highlightPage")) {
            this.highlightSameFramesAndPages();
        }else if(aDefault.equals("highlightProcess")) {
            this.highlightSameProcesses();
        }else{
            this.removeHighlights();
        }

    }
}
