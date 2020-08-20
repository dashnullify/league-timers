package me.brennan.timers.objects;

import com.merakianalytics.orianna.types.core.staticdata.SummonerSpell;

/**
 * made for LOLTimers
 *
 * @author Brennan
 * @since 5/10/2020
 **/
public class Champion {
    private final String name;
    private final SummonerSpell dSpell, fSpell;

    public Champion(String name, SummonerSpell dSpell, SummonerSpell fSpell) {
        this.name = name;
        this.dSpell = dSpell;
        this.fSpell = fSpell;
    }

    public String getName() {
        return name;
    }

    public SummonerSpell getDSpell() {
        return dSpell;
    }

    public SummonerSpell getFSpell() {
        return fSpell;
    }
}
