package me.brennan.timers;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.spectator.CurrentMatch;
import com.merakianalytics.orianna.types.core.spectator.CurrentMatchTeam;
import com.merakianalytics.orianna.types.core.spectator.Player;
import com.merakianalytics.orianna.types.core.summoner.Summoner;
import me.brennan.timers.filemanager.FileManager;
import me.brennan.timers.objects.Champion;
import me.brennan.timers.ui.OverlayFrame;

import javax.swing.*;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

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
        Orianna.setRiotAPIKey(getApiKey());
        Orianna.setDefaultRegion(Region.NORTH_AMERICA);

        final Summoner summoner = Orianna.summonerNamed(getUsername()).get();
        final CurrentMatch currentMatch = summoner.getCurrentMatch();

        if(currentMatch != null) {
            final List<Player> playerList = currentMatch.getParticipants();
            final List<Champion> champions = new LinkedList<>();

            playerList
                    .stream()
                    .filter(player -> player.getSummoner().getName().equalsIgnoreCase(summoner.getName()))
                    .forEach(player -> currentTeam = player.getTeam());

            for(Player player : playerList) {
                if(player.getTeam() != currentTeam) {
                    System.out.println(String.format("----------[%s]----------", player.getChampion().getName()));
                    System.out.println(player.getChampion());
                    System.out.println(player.getSummonerSpellD().getName());
                    System.out.println(player.getSummonerSpellD().getCooldowns().size());
                    System.out.println(player.getSummonerSpellF().getName());
                    System.out.println(player.getSummonerSpellF().getCooldowns().size());
                    System.out.println("--------------------");

                    final Champion champion = new Champion(player.getChampion().getName(), player.getSummonerSpellD(), player.getSummonerSpellF());
                    champions.add(champion);
                }
            }

            new OverlayFrame(champions);
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
