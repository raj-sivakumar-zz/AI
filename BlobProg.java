package blob;

import aima.core.search.adversarial.AdversarialSearch;
import aima.core.search.adversarial.AlphaBetaSearch;
import aima.core.search.adversarial.MinimaxSearch;
import aima.core.util.datastructure.XYLocation;
import aima.core.agent.Action;

import java.util.Map.Entry;

import java.util.AbstractMap.SimpleEntry;

/**
 * Created by orcsy on 10/10/16.
 */
public class BlobProg {
    public static void main(String[] args) {
        System.out.println("TIC-TAC-TOE DEMO");
        System.out.println("");
        startMinimaxDemo();
        startAlphaBetaDemo();
    }

    private static void startMinimaxDemo() {
        System.out.println("MINI MAX DEMO\n");
        Blobs game = new Blobs();
        BlobState currState = game.getInitialState();
        AdversarialSearch<BlobState, Entry<Action, XYLocation>> search = MinimaxSearch
                .createFor(game);
        while (!(game.isTerminal(currState))) {
            System.out.println(game.getPlayer(currState) + "  playing ... ");
            Entry<Action, XYLocation> actionPair = search.makeDecision(currState);
            currState = game.getResult(currState, actionPair);
            System.out.println(currState);
        }
        System.out.println("MINI MAX DEMO done");
    }

    private static void startAlphaBetaDemo() {
        System.out.println("ALPHA BETA DEMO\n");
        Blobs game = new Blobs();
        BlobState currState = game.getInitialState();
        AdversarialSearch<BlobState, Entry<Action, XYLocation>> search = AlphaBetaSearch
                .createFor(game);
        while (!(game.isTerminal(currState))) {
            System.out.println(game.getPlayer(currState) + "  playing ... ");
            Entry<Action, XYLocation> actionPair = search.makeDecision(currState);
            currState = game.getResult(currState, actionPair);
            System.out.println(currState);
        }
        System.out.println("ALPHA BETA DEMO done");
    }
}
