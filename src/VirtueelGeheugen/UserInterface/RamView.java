package VirtueelGeheugen.UserInterface;


import VirtueelGeheugen.UserInterface.ListObjects.HBoxRam;
import VirtueelGeheugen.UserInterface.ListObjects.RamTableData;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.List;

public class RamView extends ListView<HBoxRam> {


    public RamView(){
        super();
        this.configureRamTable();
    }

    public void configureRamTable() {
        this.setMouseTransparent( true );
        this.setFocusTraversable( false );
        this.setPlaceholder(new Label("No Content In Ram"));

        this.fillWithData(null);

        System.out.println("GENERATIN DONE");
    }


    //import the right data
    public void fillWithData(List<RamTableData> ramTableCells) {

        //clear current data
        this.getItems().clear();
        this.addPlaceHolderCell();
        if(ramTableCells != null && ramTableCells.size() == 12) {
            for (RamTableData ramTableData : ramTableCells) {
                this.addCellWithData(ramTableData);
            }
        }
    }

    private void addPlaceHolderCell() {
        this.addCellWithData(new RamTableData("Page Number","Process ID"));
    }

    private void addCellWithData(RamTableData ramTableData) {
        HBoxRam hbox = new HBoxRam();

        //Label config
        Label c1 = new Label(ramTableData.getProcessId());
        Label c2 = new Label(ramTableData.getPageNumber());

        HBox.setHgrow(c1, Priority.ALWAYS);
        HBox.setHgrow(c2, Priority.ALWAYS);


        //Wrap in HBOX again
        HBox w1 = new HBox(c1);
        HBox w2 = new HBox(c2);


        HBox.setHgrow(w1, Priority.ALWAYS);
        HBox.setHgrow(w2, Priority.ALWAYS);


        hbox.getChildren().add(w1);
        hbox.getChildren().add(w2);

        hbox.setPageIdAndProcessId(ramTableData.getPageNumber(),ramTableData.getProcessId());
        this.getItems().add(hbox);

    }

    public void reset() {
        this.getItems().clear();
    }
}
