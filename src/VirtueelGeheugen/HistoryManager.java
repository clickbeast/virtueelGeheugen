package VirtueelGeheugen;


import java.util.ArrayList;

//keeps track of the previous states
public class HistoryManager  {

    private int currentIndex;
    private ArrayList<UIState> previousStates;

    //configure the hostirymanager
    public HistoryManager() {
        previousStates = new ArrayList<UIState>();
    }


    public ArrayList<UIState> getPreviousStates() {
        return previousStates;
    }

    //adds a new state
    public void addNewState(UIState state) {
        previousStates.add(state);
    }


    public void reset() {
        previousStates.clear();
    }

    public void backToPresent() {
        currentIndex = this.previousStates.size();
    }


    public UIState nextState() {
        return null;
    }


    public UIState previousState() {

        return null;
    }
}
