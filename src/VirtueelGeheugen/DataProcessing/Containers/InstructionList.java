package VirtueelGeheugen.DataProcessing.Containers;

import java.util.LinkedList;

public class InstructionList extends LinkedList<InstructionInfo> {

    public InstructionList() {
        super();
    }

    public InstructionList(InstructionList instructionList) {
        super();
        for(InstructionInfo instructionInfo : instructionList){
            this.add(new InstructionInfo(instructionInfo));
        }
    }
}
