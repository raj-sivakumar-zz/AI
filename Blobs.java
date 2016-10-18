package blob;

/**
 * Created by orcsy on 10/10/16.
 */
import aima.core.search.adversarial.Game;
import aima.core.util.datastructure.XYLocation;
import aima.core.agent.Action;
import java.util.Map.Entry;

import java.util.AbstractMap.SimpleEntry;

import java.util.List;

public class Blobs implements Game<BlobState, Entry<Action, XYLocation>, String> {

    BlobState initialState = new BlobState();

    @Override
    public BlobState getInitialState() {
        return initialState;
    }

    @Override
    public String[] getPlayers() {
        return new String[] { BlobState.X, BlobState.O };
    }

    @Override
    public String getPlayer(BlobState state) {
        return state.getPlayerToMove();
    }


    @Override
    public List<Entry<Action, XYLocation>> getActions(BlobState state) {
        return state.getAvailableActions();
    }


    @Override
    public BlobState getResult(BlobState state, Entry<Action, XYLocation> actionPair) {
        BlobState result = state.clone();
        Action action = actionPair.getKey();
        if (state.ADD_ABOVE.equals(action) || state.ADD_BELOW.equals(action) || state.ADD_LEFT.equals(action) || state.ADD_RIGHT.equals(action)){
            result.mark(actionPair);
        } else if(state.JUMP_ABOVE.equals(action) || state.JUMP_BELOW.equals(action) || state.JUMP_LEFT.equals(action) || state.JUMP_RIGHT.equals(action)
                || state.JUMP_AL.equals(action) || state.JUMP_AR.equals(action) || state.JUMP_BL.equals(action) || state.JUMP_BR.equals(action)) {
            result.jumpOne(actionPair);
        }else if(state.JUMP_TWO_ABOVE.equals(action) || state.JUMP_TWO_BELOW.equals(action) || state.JUMP_TWO_LEFT.equals(action) || state.JUMP_TWO_RIGHT.equals(action)
                || state.JUMP_TWO_AL.equals(action) || state.JUMP_TWO_AR.equals(action) || state.JUMP_TWO_BL.equals(action) || state.JUMP_TWO_BR.equals(action)) {
            result.jumpTwo(actionPair);
        }
        return result;
    }

    @Override
    public boolean isTerminal(BlobState state) {
        return state.getUtility() != -1;
    }

    @Override
    public double getUtility(BlobState state, String player) {
        double result = state.getUtility();
        if (result != -1) {
            if (player == BlobState.O)
                result = 1 - result;
        } else {
            throw new IllegalArgumentException("State is not terminal.");
        }
        return result;
    }

}
