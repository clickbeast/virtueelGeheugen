package VirtueelGeheugen.DataProcessing.Processing;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import VirtueelGeheugen.DataProcessing.Containers.InstructionInfo;
import VirtueelGeheugen.DataProcessing.Containers.InstructionList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.nio.file.Paths;

/*
    Main task: Process a supplied xml document and generate an approptiate list of process Objects that can be handled later
 */
public class XMLProcessor {

    //initialise class
    public XMLProcessor() {

    }

    //TODO fully read file and fully generate list
    public InstructionList generateProcessListBasedOnXML(String fileName) {

        InstructionList instructionList = new InstructionList();

        //read the xml file
        try {

            String path = System.getProperty("user.dir");
            File file = new File(path + File.separator + "src", fileName);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(file);

            Element root = doc.getDocumentElement();
            NodeList list = root.getElementsByTagName("instruction");

            for(int i = 0; i < list.getLength(); i++){
                //aanpassen
                //TODO CHECK
                String id = list.item(i).getChildNodes().item(1).getTextContent();
                String operation = list.item(i).getChildNodes().item(3).getTextContent();
                String address = list.item(i).getChildNodes().item(5).getTextContent();
                instructionList.add(new InstructionInfo(operation,Integer.parseInt(address),Integer.parseInt(id)));
            }

            System.out.println("XML file Generated");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return instructionList;
    }
}
