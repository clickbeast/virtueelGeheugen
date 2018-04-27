package VirtueelGeheugen.UserInterface;

import VirtueelGeheugen.UserInterface.ListObjects.PageTableData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.List;

public class PageTableView extends ListView<HBox> {


    public PageTableView() {
        super();
        configurePageTable();
    }


    public void configurePageTable() {
        this.setMouseTransparent( true );
        this.setFocusTraversable( false );
        this.setPlaceholder(new Label("No Content In PageTable"));

        this.fillWithData(null);

        System.out.println("GENERATIN DONE");
    }


    //import the right data
    public void fillWithData(List<PageTableData> pageTableCells) {

        //clear current data
        this.getItems().clear();
        this.addPlaceHolderCell();
        if(pageTableCells != null && pageTableCells.size() == 16) {
            for (PageTableData pageTableData : pageTableCells) {
                this.addCellWithData(pageTableData);
            }
        }
    }

    private void addPlaceHolderCell() {
        this.addCellWithData(new PageTableData("Present Bit","Modify Bit","Last Access Time","Frame Number"));
    }

    private void addCellWithData(PageTableData pageTableData) {
        HBox hbox = new HBox();

        //Label config
        Label c1 = new Label(pageTableData.getPresentBit());
        Label c2 = new Label(pageTableData.getModifyBit());
        Label c3 = new Label(pageTableData.getLastAccessTime());
        Label c4 = new Label(pageTableData.getFrameNumber());

        HBox.setHgrow(c1, Priority.ALWAYS);
        HBox.setHgrow(c2, Priority.ALWAYS);
        HBox.setHgrow(c3, Priority.ALWAYS);
        HBox.setHgrow(c4, Priority.ALWAYS);


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
    }

    public void reset() {
        this.getItems().clear();
    }


}
