package org.com.config;

import org.com.model.Tube;

import java.util.List;

public class GameConfig {
    public static List<Tube> initialState() {
        return List.of(
                new Tube(4, List.of("R", "G", "B", "R")),
                new Tube(4, List.of("G", "B", "R", "G")),
                new Tube(4, List.of("B", "R", "G", "B")),
                new Tube(4, List.of()),
                new Tube(4, List.of()),
                new Tube(4, List.of())
        );
    }
}