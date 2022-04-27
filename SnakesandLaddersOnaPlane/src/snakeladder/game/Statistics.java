package snakeladder.game;

import java.util.HashMap;
import java.util.Map;

public class Statistics {
    //user rolling statistics
     Map<Integer, Integer> userRollingStatistics = new HashMap<Integer, Integer>();
     Map<String, Integer> connectionStatistics = new HashMap<String, Integer>();


    Statistics(){
        initializeStatistics();
    }
    //initialize user rolling statistics
    void initializeStatistics() {
        for (int i = 1; i <= 12; i++) {
            userRollingStatistics.put(i, 0);
        }
        connectionStatistics.put("up", 0);
        connectionStatistics.put("down", 0);

    }

    void updateConnectionStatistics(boolean isUp) {
        if (isUp) {
            connectionStatistics.put("up", connectionStatistics.get("up") + 1);
        } else {
            connectionStatistics.put("down", connectionStatistics.get("down") + 1);
        }

    }

    void updateUserRollingStatistics(int diceValue) {
        int count = userRollingStatistics.get(diceValue);
        userRollingStatistics.put(diceValue, count + 1);

    }




}
