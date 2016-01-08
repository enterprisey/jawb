package com.jawb.models;

/**
 * Holds statistics.
 */
public class StatisticsModel {
    private int edits;
    private int skipped;

    public int getEdits() {
        return edits;
    }

    public int getSkipped() {
        return skipped;
    }

    public void incrementEdits() {
        edits++;
    }

    public void incrementSkipped() {
        skipped++;
    }
}
