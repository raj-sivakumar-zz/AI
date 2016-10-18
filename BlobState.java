package blob;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;
import aima.core.util.datastructure.XYLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;

/**
 * Created by orcsy on 10/10/16.
 */
public class BlobState implements Cloneable {
    public static final String X = "X";
    public static final String O = "O";
    public static final String EMPTY = "-";

    public static Action ADD_ABOVE = new DynamicAction("Add Above");
    public static Action ADD_BELOW = new DynamicAction("Add Below");
    public static Action ADD_LEFT = new DynamicAction("Add Left");
    public static Action ADD_RIGHT = new DynamicAction("Add Right");

    public static Action JUMP_ABOVE = new DynamicAction("Jump Above");
    public static Action JUMP_TWO_ABOVE = new DynamicAction("Jump Two Above");

    public static Action JUMP_BELOW = new DynamicAction("Jump Below");
    public static Action JUMP_TWO_BELOW = new DynamicAction("Jump Two Below");

    public static Action JUMP_LEFT = new DynamicAction("Jump Left");
    public static Action JUMP_TWO_LEFT = new DynamicAction("Jump Two Left");

    public static Action JUMP_RIGHT = new DynamicAction("Jump Right");
    public static Action JUMP_TWO_RIGHT = new DynamicAction("Jump Two Right");

    public static Action JUMP_AL = new DynamicAction("Jump Above Left");
    public static Action JUMP_TWO_AL = new DynamicAction("Jump Two Above Left");

    public static Action JUMP_BL = new DynamicAction("Jump Below Left");
    public static Action JUMP_TWO_BL = new DynamicAction("Jump Two Below Left");

    public static Action JUMP_AR = new DynamicAction("Jump Above Right");
    public static Action JUMP_TWO_AR = new DynamicAction("Jump Two Above Right");

    public static Action JUMP_BR = new DynamicAction("Jump Below Right");
    public static Action JUMP_TWO_BR = new DynamicAction("Jump Two Below Right");

    //
    private String[] board = new String[]{
            X, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, O,
            EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,
            EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,
            EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,
            EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,
            EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY,
            O, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, X};

    private String playerToMove = X;
    private double utility = -1; // > 0.5 win for X, <.5 win for O, 0.5: draw

    public String getPlayerToMove() {
        return playerToMove;
    }

    public boolean isEmpty(int col, int row) {
        return board[getAbsPosition(col, row)] == EMPTY;
    }

    public String getValue(int col, int row) {
        return board[getAbsPosition(col, row)];
    }

    public double getUtility() {
        return utility;
    }

    public void mark(Entry<Action, XYLocation> actionPair) {
        XYLocation start = actionPair.getValue();
        Action a = actionPair.getKey();

        int col = start.getXCoOrdinate();
        int row = start.getYCoOrdinate();
        int endcol = col;
        int endrow = row;
        if (a == ADD_ABOVE && getAvailablePositions().contains(new XYLocation(col, row-1)) ) {
            endcol = col;
            endrow = row-1;

        } else if ( a == ADD_BELOW && getAvailablePositions().contains(new XYLocation(col, row+1))) {
            endcol = col;
            endrow = row+1;

        } else if ( a == ADD_LEFT && getAvailablePositions().contains(new XYLocation(col-1, row))) {
            endcol = col-1;
            endrow = row;

        }else if ( a == ADD_RIGHT && getAvailablePositions().contains(new XYLocation(col+1, row))) {
            endcol = col+1;
            endrow = row;
        }
        if (utility == -1 && getValue(col, row) == EMPTY) {
            board[getAbsPosition(endcol, endrow)] = playerToMove;
            analyzeUtility();
            playerToMove = (playerToMove == X ? O : X);
        }
    }
    public void jumpOne(Entry<Action, XYLocation> actionPair) {
        XYLocation start = actionPair.getValue();
        Action a = actionPair.getKey();

        int col = start.getXCoOrdinate();
        int row = start.getYCoOrdinate();
        int endcol = -1;
        int endrow = -1;
        if (a == JUMP_ABOVE && getAvailablePositions().contains(new XYLocation(col, row-1))) {
            endcol = col;
            endrow = row-1;
        } else if ( a == JUMP_BELOW && getAvailablePositions().contains(new XYLocation(col, row+1))) {
            endcol = col;
            endrow = row+1;
        } else if ( a == JUMP_LEFT && getAvailablePositions().contains(new XYLocation(col-1, row))) {
            endcol = col-1;
            endrow = row;
        }else if ( a == JUMP_RIGHT && getAvailablePositions().contains(new XYLocation(col+1, row))) {
            endcol = col+1;
            endrow = row;
        } else if (a == JUMP_AL && getAvailablePositions().contains(new XYLocation(col-1, row-1))) {
            endcol = col-1;
            endrow = row-1;
        } else if ( a == JUMP_BL&& getAvailablePositions().contains(new XYLocation(col-1, row+1))) {
            endcol = col-1;
            endrow = row+1;
        } else if ( a == JUMP_AR && getAvailablePositions().contains(new XYLocation(col+1, row-1))) {
            endcol = col+1;
            endrow = row-1;
        }else if ( a == JUMP_BR && getAvailablePositions().contains(new XYLocation(col+1, row+1))) {
            endcol = col+1;
            endrow = row+1;
        }
        if (utility == -1 && getValue(col, row) == EMPTY) {
            board[getAbsPosition(endcol, endrow)] = playerToMove;
            board[getAbsPosition(col,row)] = EMPTY;
            analyzeUtility();
            playerToMove = (playerToMove == X ? O : X);
        }

    }

    public void jumpTwo(Entry<Action, XYLocation> actionPair) {
        XYLocation start = actionPair.getValue();
        Action a = actionPair.getKey();
        int col = start.getXCoOrdinate();
        int row = start.getYCoOrdinate();
        int endcol = col;
        int endrow = row;
        if (a == JUMP_TWO_ABOVE && getAvailablePositions().contains(new XYLocation(col, row-2))) {
            endcol = col;
            endrow = row-2;
        } else if ( a == JUMP_TWO_BELOW && getAvailablePositions().contains(new XYLocation(col, row+2))) {
            endcol = col;
            endrow = row+2;
        } else if ( a == JUMP_TWO_LEFT && getAvailablePositions().contains(new XYLocation(col-2, row))) {
            endcol = col-2;
            endrow = row;
        }else if ( a == JUMP_TWO_RIGHT && getAvailablePositions().contains(new XYLocation(col+2, row))) {
            endcol = col+2;
            endrow = row;
        } else if (a == JUMP_TWO_AL && getAvailablePositions().contains(new XYLocation(col-2, row-2))) {
            endcol = col-2;
            endrow = row-2;
        } else if ( a == JUMP_TWO_BL&& getAvailablePositions().contains(new XYLocation(col-2, row+2))) {
            endcol = col-2;
            endrow = row+2;
        } else if ( a == JUMP_TWO_AR && getAvailablePositions().contains(new XYLocation(col+2, row-2))) {
            endcol = col+2;
            endrow = row-2;
        }else if ( a == JUMP_TWO_BR && getAvailablePositions().contains(new XYLocation(col+2, row+2))) {
            endcol = col+2;
            endrow = row+2;
        }
        //if you aren't at terminal state, complete action and analyze utility(if move puts at terminal state
        if (utility == -1 && getValue(col, row).equals(EMPTY)) {
            board[getAbsPosition(endcol, endrow)] = playerToMove;
            board[getAbsPosition(col,row)] = EMPTY;
            analyzeUtility();
            playerToMove = (playerToMove == X ? O : X);

        }
    }

    private void analyzeUtility() {
        int num = 0;
        if ((getAvailablePositions(X).size() == 0 && getAvailablePositions(O).size() ==0)
                || getNumberOfMarkedPositions() == 49) {
            for (int i = 0; i<49; i++) {
                if (board[i] == X) {
                    num++;
                }
            }
            utility = num/getNumberOfMarkedPositions();
        }

    }


    public List<Entry<Action, XYLocation>> getAvailableActions() {
        List<Entry<Action, XYLocation>> result = new ArrayList<Entry<Action, XYLocation>>();

        for (int col = 0; col < 7; col++) {
            for (int row = 0; row < 7; row++) {
                if (!(isEmpty(col, row)) && getValue(col, row) == playerToMove) {

                    //spots you can add to or jump to
                    if(isEmpty(col+1, row) && col+1 <=6) {
                        result.add(new SimpleEntry<Action, XYLocation>(ADD_RIGHT, new XYLocation(col, row)));
                        result.add(new SimpleEntry<Action, XYLocation>(JUMP_RIGHT,new XYLocation(col,row)));
                    }
                    if(isEmpty(col-1, row) && col-1 >=0) {
                        result.add(new SimpleEntry<Action, XYLocation>(ADD_LEFT, new XYLocation(col, row)));
                        result.add(new SimpleEntry<Action, XYLocation>(JUMP_LEFT, new XYLocation(col, row)));
                    }
                    if(isEmpty(col, row+1) && row+1 <=6) {
                        result.add(new SimpleEntry<Action, XYLocation>(ADD_BELOW, new XYLocation(col, row)));
                        result.add(new SimpleEntry<Action, XYLocation>(JUMP_BELOW, new XYLocation(col, row)));
                    }
                    if(isEmpty(col, row-1) && row-1 >=0) {
                        result.add(new SimpleEntry<Action, XYLocation>(ADD_ABOVE, new XYLocation(col, row)));
                        result.add(new SimpleEntry<Action, XYLocation>(JUMP_ABOVE, new XYLocation(col, row)));
                    }

                    if(isEmpty(col+1, row+1) && col+1 <=6 && row+1<=6) {
                        result.add(new SimpleEntry<Action, XYLocation>(JUMP_BR, new XYLocation(col, row)));
                    }
                    if(isEmpty(col-1, row-1) && col-1 >=0 && row-1>=0) {
                        result.add(new SimpleEntry<Action, XYLocation>(JUMP_AL, new XYLocation(col, row)));
                    }
                    if(isEmpty(col-1, row+1) && row+1 <=6 && col-1 >=0) {
                        result.add(new SimpleEntry<Action, XYLocation>(JUMP_BL, new XYLocation(col, row)));
                    }
                    if(isEmpty(col+1, row-1) && row-1 >=0 && col+1 <=6) {
                        result.add(new SimpleEntry<Action, XYLocation>(JUMP_AR, new XYLocation(col, row)));
                    }

                    //spots you can only jump to
                    if(isEmpty(col+2, row) && col+2 <=6) {
                        result.add(new SimpleEntry<Action, XYLocation>(JUMP_TWO_RIGHT, new XYLocation(col, row)));
                    }
                    if(isEmpty(col-2, row) && col-2 >=0) {
                        result.add(new SimpleEntry<Action, XYLocation>(JUMP_TWO_LEFT, new XYLocation(col, row)));
                    }
                    if(isEmpty(col, row+2) && row+2 <=6) {
                        result.add(new SimpleEntry<Action, XYLocation>(JUMP_TWO_BELOW, new XYLocation(col, row)));
                    }
                    if(isEmpty(col, row-2) && row-2 >=0) {
                        result.add(new SimpleEntry<Action, XYLocation>(JUMP_TWO_ABOVE, new XYLocation(col, row)));
                    }

                    if(isEmpty(col+2, row+2) && col+2 <=6 && row+2<=6) {
                        result.add(new SimpleEntry<Action, XYLocation>(JUMP_TWO_BR, new XYLocation(col, row)));
                    }
                    if(isEmpty(col-2, row-2) && col-2 >=0 && row-2>=0) {
                        result.add(new SimpleEntry<Action, XYLocation>(JUMP_TWO_AL, new XYLocation(col, row)));
                    }
                    if(isEmpty(col-2, row+2) && row+2 <=6 && col-2 >=0) {
                        result.add(new SimpleEntry<Action, XYLocation>(JUMP_TWO_BL, new XYLocation(col, row)));
                    }
                    if(isEmpty(col+2, row-2) && row-2 >=0 && col+2 <=6) {
                        result.add(new SimpleEntry<Action, XYLocation>(JUMP_TWO_AR, new XYLocation(col, row)));
                    }
                }
            }
        }
        return result;
    }



    public List<XYLocation> getAvailablePositions() {
        List<XYLocation> result = new ArrayList<XYLocation>();
        for (int col = 0; col < 7; col++) {
            for (int row = 0; row < 7; row++) {
                if (!(isEmpty(col, row)) && getValue(col, row) == playerToMove) {

                    //spots you can add to or jump to
                    if(isEmpty(col+1, row) && col+1 <=6) {
                        result.add(new XYLocation(col+1, row));
                    }
                    if(isEmpty(col-1, row) && col-1 >=0) {
                        result.add(new XYLocation(col-1, row));
                    }
                    if(isEmpty(col, row+1) && row+1 <=6) {
                        result.add(new XYLocation(col, row+1));
                    }
                    if(isEmpty(col, row-1) && row-1 >=0) {
                        result.add(new XYLocation(col, row-1));
                    }

                    if(isEmpty(col+1, row+1) && col+1 <=6 && row+1<=6) {
                        result.add(new XYLocation(col+1, row+1));
                    }
                    if(isEmpty(col-1, row-1) && col-1 >=0 && row-1>=0) {
                        result.add(new XYLocation(col-1, row-1));
                    }
                    if(isEmpty(col-1, row+1) && row+1 <=6 && col-1 >=0) {
                        result.add(new XYLocation(col-1, row+1));
                    }
                    if(isEmpty(col+1, row-1) && row-1 >=0 && col+1 <=6) {
                        result.add(new XYLocation(col+1, row-1));
                    }

                    //spots you can only jump to
                    if(isEmpty(col+2, row) && col+2 <=6) {
                        result.add(new XYLocation(col+2, row));
                    }
                    if(isEmpty(col-2, row) && col-2 >=0) {
                        result.add(new XYLocation(col-2, row));
                    }
                    if(isEmpty(col, row+2) && row+2 <=6) {
                        result.add(new XYLocation(col, row+2));
                    }
                    if(isEmpty(col, row-2) && row-2 >=0) {
                        result.add(new XYLocation(col, row-2));
                    }

                    if(isEmpty(col+2, row+2) && col+2 <=6 && row+2<=6) {
                        result.add(new XYLocation(col+2, row+2));
                    }
                    if(isEmpty(col-2, row-2) && col-2 >=0 && row-2>=0) {
                        result.add(new XYLocation(col-2, row-2));
                    }
                    if(isEmpty(col-2, row+2) && row+2 <=6 && col-2 >=0) {
                        result.add(new XYLocation(col-2, row+2));
                    }
                    if(isEmpty(col+2, row-2) && row-2 >=0 && col+2 <=6) {
                        result.add(new XYLocation(col+2, row-2));
                    }
                }
            }
        }
        return result;
    }

    public List<XYLocation> getAvailablePositions(String player) {
        List<XYLocation> result = new ArrayList<XYLocation>();
        for (int col = 0; col < 7; col++) {
            for (int row = 0; row < 7; row++) {
                if (!(isEmpty(col, row)) && getValue(col, row) == player) {

                    //spots you can add to or jump to
                    if(isEmpty(col+1, row) && col+1 <=6) {
                        result.add(new XYLocation(col+1, row));
                    }
                    if(isEmpty(col-1, row) && col-1 >=0) {
                        result.add(new XYLocation(col-1, row));
                    }
                    if(isEmpty(col, row+1) && row+1 <=6) {
                        result.add(new XYLocation(col, row+1));
                    }
                    if(isEmpty(col, row-1) && row-1 >=0) {
                        result.add(new XYLocation(col, row-1));
                    }

                    if(isEmpty(col+1, row+1) && col+1 <=6 && row+1<=6) {
                        result.add(new XYLocation(col+1, row+1));
                    }
                    if(isEmpty(col-1, row-1) && col-1 >=0 && row-1>=0) {
                        result.add(new XYLocation(col-1, row-1));
                    }
                    if(isEmpty(col-1, row+1) && row+1 <=6 && col-1 >=0) {
                        result.add(new XYLocation(col-1, row+1));
                    }
                    if(isEmpty(col+1, row-1) && row-1 >=0 && col+1 <=6) {
                        result.add(new XYLocation(col+1, row-1));
                    }

                    //spots you can only jump to
                    if(isEmpty(col+2, row) && col+2 <=6) {
                        result.add(new XYLocation(col+2, row));
                    }
                    if(isEmpty(col-2, row) && col-2 >=0) {
                        result.add(new XYLocation(col-2, row));
                    }
                    if(isEmpty(col, row+2) && row+2 <=6) {
                        result.add(new XYLocation(col, row+2));
                    }
                    if(isEmpty(col, row-2) && row-2 >=0) {
                        result.add(new XYLocation(col, row-2));
                    }

                    if(isEmpty(col+2, row+2) && col+2 <=6 && row+2<=6) {
                        result.add(new XYLocation(col+2, row+2));
                    }
                    if(isEmpty(col-2, row-2) && col-2 >=0 && row-2>=0) {
                        result.add(new XYLocation(col-2, row-2));
                    }
                    if(isEmpty(col-2, row+2) && row+2 <=6 && col-2 >=0) {
                        result.add(new XYLocation(col-2, row+2));
                    }
                    if(isEmpty(col+2, row-2) && row-2 >=0 && col+2 <=6) {
                        result.add(new XYLocation(col+2, row-2));
                    }
                }
            }
        }
        return result;
    }

    public int getNumberOfMarkedPositions() {
        int retVal = 0;
        for (int col = 0; col < 7; col++) {
            for (int row = 0; row < 7; row++) {
                if (!(isEmpty(col, row))) {
                    retVal++;
                }
            }
        }
        return retVal;
    }



    @Override
    public BlobState clone() {
        BlobState copy = null;
        try {
            copy = (BlobState) super.clone();
            copy.board = Arrays.copyOf(board, board.length);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace(); // should never happen...
        }
        return copy;
    }

    @Override
    public boolean equals(Object anObj) {
        if (anObj != null && anObj.getClass() == getClass()) {
            BlobState anotherState = (BlobState) anObj;
            for (int i = 0; i < 9; i++) {
                if (board[i] != anotherState.board[i]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        // Need to ensure equal objects have equivalent hashcodes (Issue 77).
        return toString().hashCode();
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 7; col++) {
                strBuilder.append(getValue(col, row) + " ");
            }
            strBuilder.append("\n");
        }
        return strBuilder.toString();
    }

    ///
    ///Private Methods
    ///
    private int getAbsPosition(int col, int row) {
        return row * 7 + col;
    }


}
