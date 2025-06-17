package org.com;

import org.com.config.GameConfig;
import org.com.core.BfsTubeSolver;
import org.com.model.Tube;
import org.com.ui.ConsoleRenderer;

import java.util.List;

public class LiquidSorterApp {
    public static void main(String[] args) {
        var tubes = GameConfig.initialState();
        ConsoleRenderer.render(tubes);

        var solver = new BfsTubeSolver(cloneTubes(tubes));

        solver.solve();

        System.out.println("\nРешение найдено за " + solver.getMoves().size() + " шагов:");
        solver.getMoves().forEach(System.out::println);

        ConsoleRenderer.render(solver.getTubes());
    }

    private static List<Tube> cloneTubes(List<Tube> original) {
        return original.stream()
                .map(Tube::cloneTube)
                .toList();
    }
}
