package com.example.a20213170_lab4.models;

public class EventsModel {

    private String idEvent;
    private String strEvent;
    private String intRound;
    private String strLeague;
    private String strLeagueBadge; //logo de la competencia
    private String strHomeTeam;
    private String strAwayTeam;
    private String intHomeScore;
    private String intAwayScore;
    private String dateEvent;
    private String intSpectators;

    //Getter
    public String getIdEvent() {
        return idEvent;
    }

    public String getStrEvent() {
        return strEvent;
    }

    public String getIntRound() {
        return intRound;
    }

    public String getStrLeague() {
        return strLeague;
    }

    public String getStrLeagueBadge() {
        return strLeagueBadge;
    }

    public String getStrHomeTeam() {
        return strHomeTeam;
    }

    public String getStrAwayTeam() {
        return strAwayTeam;
    }

    public String getIntHomeScore() {
        return intHomeScore;
    }

    public String getIntAwayScore() {
        return intAwayScore;
    }

    public String getDateEvent() {
        return dateEvent;
    }

    public String getIntSpectators() {
        return intSpectators;
    }

    public EventsModel(String idEvent, String strEvent, String intRound, String strLeague, String strLeagueBadge, String strHomeTeam, String strAwayTeam, String intHomeScore, String intAwayScore, String dateEvent, String intSpectators) {
        this.idEvent = idEvent;
        this.strEvent = strEvent;
        this.intRound = intRound;
        this.strLeague = strLeague;
        this.strLeagueBadge = strLeagueBadge;
        this.strHomeTeam = strHomeTeam;
        this.strAwayTeam = strAwayTeam;
        this.intHomeScore = intHomeScore;
        this.intAwayScore = intAwayScore;
        this.dateEvent = dateEvent;
        this.intSpectators = intSpectators;
    }
}
