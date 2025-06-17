package org.com.ui;


import org.com.model.Tube;

import java.util.List;

public class ConsoleRenderer {
    public static void render(List<Tube> tubes) {
        System.out.println("Текущее состояние пробирок:");
        for (int i = 0; i < tubes.size(); i++) {
            System.out.printf("%2d: %s%n", i, tubes.get(i));
        }
    }
}