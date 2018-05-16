package VirtueelGeheugen.UserInterface;


import javax.sound.midi.Soundbank;
import java.util.ArrayList;

//keeps track of the previous states
public class HistoryManager  {

    //TODO maybe handy to LIMIT max amount of steps that can be returned to limit memory consumption


    private int currentIndex;
    private ArrayList<UIState> previousStates;

    //configure the hostirymanager
    public HistoryManager() {
        previousStates = new ArrayList<UIState>();
        this.currentIndex = 0;
    }


    public ArrayList<UIState> getPreviousStates() {
        return previousStates;
    }

    //adds a new state
    public void addNewState(UIState state) {

        //System.out.println("ADDING NEW STATE TO HISTORY");
        previousStates.add(state);
        //set back to the present
        this.backToPresent();
    }

    public void reset() {
        previousStates.clear();
        this.currentIndex = 0;
    }


    public void backToPresent() {
        //System.out.println("Current Size:"  + this.previousStates.size());
        currentIndex = this.previousStates.size() - 1;

        //System.out.println("Current Index:" + this.currentIndex);
    }

    public UIState latestState() {
        this.currentIndex = this.previousStates.size() - 1;
        return this.previousStates.get(this.currentIndex);
    }


    public UIState firstState() {
        this.currentIndex = 0;
        return this.previousStates.get(this.currentIndex);
    }

    public UIState nextState() {
        this.currentIndex = (this.currentIndex + 1) % this.previousStates.size();
        return this.previousStates.get(this.currentIndex);
    }



    //gives back the previous state
    public UIState goToPreviousState() {

        //System.out.println("PREVIOUS STATE DEMANDED");

        if(this.previousStates.size() == 0) {
            return  null;
        }

        if(this.currentIndex - 1 >= 0) {
            //System.out.println("Current Index:" + this.currentIndex);

            this.currentIndex = this.currentIndex - 1;
        }

        //System.out.println("GETTting previous state with index + " + this.currentIndex);


        return this.previousStates.get(this.currentIndex);
    }


    public UIState accessPreviousState() {
        //System.out.println("ACCESSING PREVIOUS STATE");
        if(this.currentIndex - 1 >= 0) {
            //System.out.println("Current Index:" + this.currentIndex);
            return this.previousStates.get(this.currentIndex - 1);
        }

        return null;
    }


    public boolean isFirstState() {
        //System.out.println("CHECKING IS FIRST STATE");
        //System.out.println(currentIndex);
        return currentIndex == 0;
    }

    public boolean isLastState() {
        //System.out.println("checking is last state->" + currentIndex);
        //System.out.println(this.previousStates.size() - 1);

        if(currentIndex == this.previousStates.size()-1) {
            //System.out.println("YUP");
            return true;
        }

        return currentIndex == (this.previousStates.size() - 1) || this.previousStates.size() == 0;
    }

    public boolean isPresent() {
        return currentIndex ==(this.previousStates.size() - 1);
    }
}
