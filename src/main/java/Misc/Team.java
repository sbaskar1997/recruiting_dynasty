package Misc;

import People.Player;
import Utilities.XMLTeamReader;

import java.util.List;

public class Team {
    List<Player> teamMembers;

    public Team() {}

    public Team(String xmlFilePath) {
        //TODO: Read xml
        XMLTeamReader teamXML = new XMLTeamReader(xmlFilePath);
        this.teamMembers = teamXML.getTeam();
    }

    public List<Player> list() {
        return this.teamMembers;
    }
    public static void main(String[] args) {
        String path = "/xml/teams/TestTeam.xml";
        Team team = new Team(path);
        System.out.println(team.list());
    }
}
