package snakeladder.game;

import java.util.HashMap;
import java.util.Map;

public class Statistics {
    //user rolling statistics
     Map<Integer, Integer> userRollingStatistics = new HashMap<Integer, Integer>();
     Map<String, Integer> connectionStatistics = new HashMap<String, Integer>();


    Statistics(int numberOfDice){
        initializeStatistics(numberOfDice);
    }
    //initialize user rolling statistics
    void initializeStatistics(int numberOfDice) {
        for (int i = 1; i <= 6*numberOfDice; i++) {
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

    Integer getConnectionsStats(String direction){
        return connectionStatistics.get(direction);
    }

    Integer getUserStats(int i){
        return userRollingStatistics.get(i);
    }




}
