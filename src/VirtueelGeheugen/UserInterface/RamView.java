package VirtueelGeheugen.UserInterface;


import VirtueelGeheugen.UserInterface.ListObjects.PageTableData;
import VirtueelGeheugen.UserInterface.ListObjects.RamTableData;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.List;

public class RamView extends ListView<HBox> {


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
        this.addCellWithData(new RamTableData("Frame Id","Process Id"));
    }

    private void addCellWithData(RamTableData ramTableData) {
        HBox hbox = new HBox();

        //Label config
        Label c1 = new Label(ramTableData.getProcessId());
        Label c2 = new Label(ramTableData.getFrameId());

        HBox.setHgrow(c1, Priority.ALWAYS);
        HBox.setHgrow(c2, Priority.ALWAYS);


        //Wrap in HBOX again
        HBox w1 = new HBox(c1);
        HBox w2 = new HBox(c2);


        HBox.setHgrow(w1, Priority.ALWAYS);
        HBox.setHgrow(w2, Priority.ALWAYS);


        hbox.getChildren().add(w1);
        hbox.getChildren().add(w2);


        this.getItems().add(hbox);
    }

    public void reset() {
        this.getItems().clear();
    }
}
