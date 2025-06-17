package org.com.core;

import org.com.model.Tube;

import java.util.*;

public class BfsTubeSolver extends AbstractTubeGame {

    public BfsTubeSolver(List<Tube> tubes) {
        super(tubes);
    }

    @Override
    public void solve() {
        Queue<GameState> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        GameState start = new GameState(tubes, new ArrayList<>());
        queue.add(start);
        visited.add(start.encode());

        while (!queue.isEmpty()) {
            GameState state = queue.poll();

            if (state.isSolved()) {
                moves.addAll(state.getHistory());
                for (int i = 0; i < tubes.size(); i++) {
                    Tube original = tubes.get(i);
                    Tube solved = state.getTubes().get(i);
                    original.getDrops().clear();
                    original.pourIn(solved.getDrops());
                }
                return;
            }

            for (int from = 0; from < state.getTubes().size(); from++) {
                Tube src = state.getTubes().get(from);
                if (src.isEmpty()) continue;

                for (int to = 0; to < state.getTubes().size(); to++) {
                    if (from == to) continue;
                    Tube dst = state.getTubes().get(to);
                    if (dst.isFull()) continue;

                    String top = src.peekTop();
                    if (dst.isEmpty() || dst.peekTop().equals(top)) {
                        GameState next = state.move(from, to);
                        String code = next.encode();
                        if (visited.add(code)) {
                            queue.add(next);
                        }
                    }
                }
            }
        }
    }
}
