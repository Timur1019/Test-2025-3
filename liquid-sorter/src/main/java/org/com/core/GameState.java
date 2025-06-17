package org.com.core;

import org.com.model.Move;
import org.com.model.Tube;

import java.util.*;

public class GameState {
    private final List<Tube> tubes;
    private final List<Move> history;

    public GameState(List<Tube> tubes, List<Move> history) {
        this.tubes = tubes;
        this.history = history;
    }

    public List<Tube> getTubes() {
        return tubes;
    }

    public List<Move> getHistory() {
        return history;
    }

    public boolean isSolved() {
        for (Tube tube : tubes) {
            if (tube.isEmpty()) continue;
            if (!tube.isSingleColorFilled()) return false;
        }
        return true;
    }

    public String encode() {
        StringBuilder sb = new StringBuilder();
        for (Tube tube : tubes) {
            sb.append("|");
            for (String drop : tube.getDrops()) {
                sb.append(drop);
            }
        }
        return sb.toString();
    }

    public GameState move(int from, int to) {
        List<Tube> newTubes = new ArrayList<>();
        for (Tube t : tubes) {
            newTubes.add(t.cloneTube());
        }

        Tube source = newTubes.get(from);
        Tube target = newTubes.get(to);
        String color = source.peekTop();
        int count = source.countTopSameColor();
        int space = target.getFreeSpace();

        int toPour = Math.min(count, space);
        List<String> moved = source.removeTop(color, toPour);
        target.pourIn(moved);

        List<Move> newHistory = new ArrayList<>(history);
        newHistory.add(new Move(from, to));

        return new GameState(newTubes, newHistory);
    }
}
