package VirtueelGeheugen.UserInterface.ListObjects;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

/**
 * custom to include pageId and processId on top for color profile
 */
public class HBoxRam extends HBox {


    private String pageId;
    private String processId;

    public HBoxRam() {
        super();
    }

    public HBoxRam(double spacing) {
        super(spacing);
    }

    public HBoxRam(Node... children) {
        super(children);
    }

    public HBoxRam(double spacing, Node... children) {
        super(spacing, children);
    }


    public void setPageIdAndProcessId(String pageId, String processId) {
        this.pageId = pageId;
        this.processId = processId;
    }

    public String getPageId() {
        return pageId;
    }

    public String getProcessId() {
        return processId;
    }
}
