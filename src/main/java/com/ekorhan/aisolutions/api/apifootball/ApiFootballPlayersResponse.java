package com.ekorhan.aisolutions.api.apifootball;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApiFootballPlayersResponse {
    private List<Response> response;

    public class Response {
        public Player player;
        public ArrayList<Statistic> statistics;
    }

    public static class Birth {
        public String date;
        public String place;
        public String country;
    }

    public static class Cards {
        public int yellow;
        public int yellowred;
        public int red;
    }

    public static class Dribbles {
        public int attempts;
        public int success;
        public Object past;
    }

    public static class Duels {
        public int total;
        public int won;
    }

    public static class Fouls {
        public int drawn;
        public int committed;
    }

    public static class Games {
        public int appearences;
        public int lineups;
        public int minutes;
        public String position;
        public String rating;
        public boolean captain;
    }

    public static class Goals {
        public int total;
        public int conceded;
        public int assists;
        public int saves;
    }

    public static class League {
        public String name;
        public String country;
        public int season;
    }

    public static class Paging {
        public int current;
        public int total;
    }

    public static class Parameters {
        public String league;
        public String page;
        public String season;
    }

    public static class Passes {
        public int total;
        public int key;
        public int accuracy;
    }

    public static class Penalty {
        public int won;
        public int commited;
        public int scored;
        public int missed;
        public int saved;
    }

    public class Player {
        public String name;
        public String firstname;
        public String lastname;
        public int age;
        public Birth birth;
        public String nationality;
        public String height;
        public String weight;
        public boolean injured;
    }

    public static class Shots {
        public int total;
        public int on;
    }

    public class Statistic {
        public Team team;
        public League league;
        public Games games;
        public Substitutes substitutes;
        public Shots shots;
        public Goals goals;
        public Passes passes;
        public Tackles tackles;
        public Duels duels;
        public Dribbles dribbles;
        public Fouls fouls;
        public Cards cards;
        public Penalty penalty;
    }

    public static class Substitutes {
        public int in;
        public int out;
        public int bench;
    }

    public static class Tackles {
        public int total;
        public int blocks;
        public int interceptions;
    }

    public static class Team {
        public String name;
    }


}
