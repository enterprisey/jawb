package com.jawb.models;

import java.util.*;
import java.util.function.Predicate;

/**
 * Holds statistics.
 */
public class StatisticsModel {
    private static final long ONE_MINUTE_IN_MILLISECONDS = 60000;

    private List<Action> actions;

    public StatisticsModel() {
        actions = new ArrayList<>();
    }

    public int getEdits() {
        return getActionsOfType( ActionType.EDIT );
    }

    public int getSkipped() {
        return getActionsOfType( ActionType.SKIP );
    }

    public int getNew() {
        return getActionsOfType( ActionType.NEW );
    }

    public int getRecentEdits() {
        long oneMinuteAgo = new Date().getTime() - ONE_MINUTE_IN_MILLISECONDS;
        return getFilteredActionsCount( action -> ( ( action.getType() == ActionType.EDIT ) &&
                ( action.getTime().getTime() >= oneMinuteAgo ) ) );
    }

    public int getRecentActions() {
        long oneMinuteAgo = new Date().getTime() - ONE_MINUTE_IN_MILLISECONDS;
        return getFilteredActionsCount( action -> action.getTime().getTime() >= oneMinuteAgo );
    }

    public void incrementEdits() {
        addNewAction( ActionType.EDIT );
    }

    public void incrementSkipped() {
        addNewAction( ActionType.SKIP );
    }

    private int getActionsOfType( ActionType type ) {
        return getFilteredActionsCount( action -> action.getType() == type );
    }

    private int getFilteredActionsCount( Predicate<Action> predicate ) {
        return actions
                .stream()
                .filter( predicate )
                .toArray()
                .length;
    }

    private void addNewAction( ActionType type ) {
        actions.add( new Action( type ) );
    }

    private class Action {
        private ActionType type;
        private Date time;

        public Action( ActionType type ) {
            this.type = type;
            this.time = new Date();
        }

        public ActionType getType() {
            return type;
        }

        public Date getTime() {
            return time;
        }
    }

    private enum ActionType {
        NEW,
        SKIP,
        EDIT
    }
}
