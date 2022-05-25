package oh_heaven.utilities;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import oh_heaven.game.LegalStrategy;
import oh_heaven.game.Oh_Heaven;
import oh_heaven.game.PlayerStrategy;
import oh_heaven.game.RandomStrategy;
import oh_heaven.game.SmartStrategy;
import oh_heaven.game.Strategy;

public class PropertiesLoader {

    private final static String PATH = "../properties/";
    private final static String DEFAULT_PROPERTIES = "runmode.properties";
    private static PropertiesLoader single_instance = null;

    //singleton
    public static PropertiesLoader getInstance(){
        if (single_instance == null){
            single_instance = new PropertiesLoader();
        }
        return single_instance;
    }
    
    //loading properties
    public  Properties loadPropertiesFile(String fileName) {
        Properties properties = new Properties();
        if (fileName == null){
            try (InputStream input = new FileInputStream(PATH + DEFAULT_PROPERTIES)) {
                properties.load(input);
                fileName = PATH+properties.getProperty("current_mode");
                System.out.println("Loading properties from " + PATH + DEFAULT_PROPERTIES);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
       
        try (InputStream input = new FileInputStream(fileName)) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            return prop;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
        
    }

    public  Random getSeed(Properties prop){
        String seedProp = prop.getProperty("seed");
        Long seed = null;
        if (seedProp != null) seed = Long.parseLong(seedProp);
        return new Random(seed);
    }

    public  int getStartCards(Properties prop){
        String startCardsProp = prop.getProperty("nbStartCards");
        int startCards = 0;
        if (startCardsProp != null) startCards = Integer.parseInt(startCardsProp);
        return startCards;
    }

    public  int getRounds(Properties prop){
        String roundsProp = prop.getProperty("rounds");
        int rounds = 0;
        if (roundsProp != null) rounds = Integer.parseInt(roundsProp);
        return rounds;
    }

    public  boolean getEnforceRules(Properties prop){
        String enforceRuleProp = prop.getProperty("enforceRules");
        boolean enforceRule = false;
        if (enforceRuleProp != null) enforceRule = Boolean.parseBoolean(enforceRuleProp);
        return enforceRule;
    }

    public  Strategy[] getPlayers(Properties prop,int nbPlayers,Oh_Heaven oh_heaven,int thinkingTime){
        Strategy[] players = new Strategy[4] ;
		for (int i = 0; i < nbPlayers; i++) {
			String playerType = prop.getProperty("players." + i);
            if(playerType.equals("human")){
                players[i] =  new PlayerStrategy(oh_heaven, i);
            }
            else if(playerType.equals("legal")){
                players[i] =new LegalStrategy(oh_heaven, i, thinkingTime);
            }
            else if (playerType.equals("smart")){
                players[i] =new SmartStrategy(oh_heaven, i, thinkingTime);
            }
            else if (playerType.equals("random")){
                players[i] =new RandomStrategy(oh_heaven, i, thinkingTime);
            }
            else{
                System.out.println("Unknown player type: " + playerType);
            }
			
		}
        return  players;
    }

    
}
