package me.brennan.timers;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.spectator.CurrentMatch;
import com.merakianalytics.orianna.types.core.spectator.CurrentMatchTeam;
import com.merakianalytics.orianna.types.core.spectator.Player;
import com.merakianalytics.orianna.types.core.summoner.Summoner;
import me.brennan.timers.filemanager.FileManager;

import javax.swing.*;
import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * made for LOLTimers
 *
 * @author Brennan
 * @since 5/10/2020
 **/
public enum Timers {
    INSTANCE;

    private String apiKey, username;

    public FileManager fileManager;

    private Timer timer = new Timer();

    private CurrentMatchTeam currentTeam;

    public void start() throws Exception {
        fileManager = new FileManager(new File(System.getenv("APPDATA"), "loltimers"));
        fileManager.load();

        if(username == null) {
            final String usernameReturned = JOptionPane.showInputDialog("Please input your username.");
            setUsername(usernameReturned);
            fileManager.save();
        }

        if(apiKey == null) {
            final String apiKeyReturned = JOptionPane.showInputDialog("Please input your API key.");
            setApiKey(apiKeyReturned);
            fileManager.save();
        }
        Orianna.setRiotAPIKey(apiKey);
        Orianna.setDefaultRegion(Region.NORTH_AMERICA);

        final Summoner summoner = Orianna.summonerNamed(getUsername()).get();
        final CurrentMatch currentMatch = summoner.getCurrentMatch();

        if(currentMatch != null) {
            final List<Player> playerList = currentMatch.getParticipants();

            playerList
                    .stream()
                    .filter(player -> player.getSummoner().getName().equalsIgnoreCase(summoner.getName()))
                    .forEach(player -> currentTeam = player.getTeam());

            //to keep updating the cooldowns :)
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    for(Player player : playerList) {
                        if(player.getTeam() != currentTeam) {
                            System.out.println(String.format("----------[%s]----------", player.getChampion()));
                            System.out.println(player.getChampion());
                            System.out.println(player.getSummonerSpellD().getName());
                            System.out.println(player.getSummonerSpellD().getCooldowns().size());
                            System.out.println(player.getSummonerSpellF().getName());
                            System.out.println(player.getSummonerSpellF().getCooldowns().size());
                            System.out.println("--------------------");
                        }
                    }
                }
            }, 2, 5);
        } else {
            System.out.println("Not in a game.");
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }
}
