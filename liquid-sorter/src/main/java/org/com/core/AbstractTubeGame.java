package org.com.core;

import org.com.model.Move;
import org.com.model.Tube;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTubeGame implements GameSolver {
    protected final List<Tube> tubes;
    protected final List<Move> moves = new ArrayList<>();

    public AbstractTubeGame(List<Tube> tubes) {
        this.tubes = tubes;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public List<Tube> getTubes() {
        return tubes;
    }
}