package com.example.a20213170_lab4.models;


import com.google.gson.annotations.SerializedName;

public class EquipoModel {

    private String strTeam;

    private int intRank;

    private String strBadge;

    private int intWin;

    private int intDraw;

    private int intLoss;

    private int intGoalsFor;

    private int intGoalsAgainst;

    private int intGoalDifference;

    public String getStrTeam() {
        return strTeam;
    }

    public void setStrTeam(String strTeam) {
        this.strTeam = strTeam;
    }

    public int getIntRank() {
        return intRank;
    }

    public void setIntRank(int intRank) {
        this.intRank = intRank;
    }

    public String getStrBadge() {
        return strBadge;
    }

    public void setStrBadge(String strBadge) {
        this.strBadge = strBadge;
    }

    public int getIntWin() {
        return intWin;
    }

    public void setIntWin(int intWin) {
        this.intWin = intWin;
    }

    public int getIntDraw() {
        return intDraw;
    }

    public void setIntDraw(int intDraw) {
        this.intDraw = intDraw;
    }

    public int getIntLoss() {
        return intLoss;
    }

    public void setIntLoss(int intLoss) {
        this.intLoss = intLoss;
    }

    public int getIntGoalsFor() {
        return intGoalsFor;
    }

    public void setIntGoalsFor(int intGoalsFor) {
        this.intGoalsFor = intGoalsFor;
    }

    public int getIntGoalsAgainst() {
        return intGoalsAgainst;
    }

    public void setIntGoalsAgainst(int intGoalsAgainst) {
        this.intGoalsAgainst = intGoalsAgainst;
    }

    public int getIntGoalDifference() {
        return intGoalDifference;
    }

    public void setIntGoalDifference(int intGoalDifference) {
        this.intGoalDifference = intGoalDifference;
    }

    public EquipoModel(String strTeam, int intRank, String strBadge, int intWin, int intDraw, int intLoss, int intGoalsFor, int intGoalsAgainst, int intGoalDifference) {
        this.strTeam = strTeam;
        this.intRank = intRank;
        this.strBadge = strBadge;
        this.intWin = intWin;
        this.intDraw = intDraw;
        this.intLoss = intLoss;
        this.intGoalsFor = intGoalsFor;
        this.intGoalsAgainst = intGoalsAgainst;
        this.intGoalDifference = intGoalDifference;
    }
}
