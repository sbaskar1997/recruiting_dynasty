package Schools;

import Misc.City;
import Misc.State;
import Misc.Team;
import People.Player;

import java.util.ArrayList;
import java.util.List;

public class HighSchool {
    public String name; public City city; public String nickname; public State state;
    private List<Player> team = new ArrayList<Player>();

    public HighSchool() {
        this.name = null;
        this.city = null;
        this.nickname = null;
    }

    public HighSchool(String name, String nickname, String city, String state) {
        this.name = name;
        this.city = new City(city, state);
        this.state = new State(state);
        this.nickname = nickname;
    }

    // Getter for team
    public List<Player> getTeam() {
        return team;
    }

    // Team adder
    public void addPlayer(Player player) {
        if (this.team.contains(player)) {
            System.out.println("Player already in team");
        } else {
            this.team.add(player);
        }
    }

    // Team remover
    public void removePlayer(Player player) {
        if (this.team.contains(player)) {
            this.team.remove(player);
        } else {
            System.out.println("Player not on team");
        }
    }

    // Team setter
    public void setTeam(Team team) {
        this.team = team.list();
    }
}
