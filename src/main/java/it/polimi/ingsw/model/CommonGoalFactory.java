package it.polimi.ingsw.model;

import it.polimi.ingsw.model.commonGoal.*;

import java.util.Random;

public class CommonGoalFactory {

    public static CommonGoal getCommonGoal() {
        Random random = new Random();
        int i = random.nextInt(1, 13);
        return switch (i) {
            case 1 -> new CommonGoal1();
            case 2 -> new CommonGoal2();
            case 3 -> new CommonGoal3();
            case 4 -> new CommonGoal4();
            case 5 -> new CommonGoal5();
            case 6 -> new CommonGoal6();
            case 7 -> new CommonGoal7();
            case 8 -> new CommonGoal8();
            case 9 -> new CommonGoal9();
            case 10 -> new CommonGoal10();
            case 11 -> new CommonGoal11();
            case 12 -> new CommonGoal12();
            default -> throw new IllegalStateException("Unexpected value: " + i);
        };
    }

}
