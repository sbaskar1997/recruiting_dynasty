package Schools;

import Misc.City;
import Misc.State;
import Misc.Team;
import People.Player;

import java.util.ArrayList;
import java.util.List;

public class University {
    // Define required fields
    public String name;
    public String nickname;
    public double prestige;
    public int yearFounded;
    public City city;
    public State state;

    // Define football team and attributes
    private List<Player> team = new ArrayList<Player>();
    private double avgRating; private double offRating; private double defRating;

    public University() {
        this.name = null;
        this.nickname = null;
        this.prestige = 0.0;
        this.yearFounded = 0;
        this.city = null;
        this.state = null;
    }

    public University(String name, String nickname, String city, String state) {
        this.name = name;
        this.nickname = nickname;
        this.city = new City(city, state);
        this.state = new State(state);
        this.yearFounded = 1900;
        this.prestige = 50.0;
    }

    public University(String name, String nickname, String city, String state, int yearFounded) {
        this.name = name;
        this.nickname = nickname;
        this.city = new City(city, state);
        this.state = new State(state);
        this.yearFounded = yearFounded;
        this.prestige = 50.0;
    }

    public University(String name, String nickname, String city, String state, int yearFounded, double prestige) {
        this.name = name;
        this.nickname = nickname;
        this.city = new City(city, state);
        this.state = new State(state);
        this.yearFounded = yearFounded;
        this.prestige = prestige;
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
            // Set university and add player to roster
            player.changeUniversity(this);
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
    public void setTeam(ArrayList<Player> team) {
        this.team = team;
        double offRating = 0; double defRating = 0;
        double offPlayerCount = 0; double defPlayerCount = 0;

        // Iterate through team and make sure to set university
        for (int i = 0; i < this.team.size(); i++) {
            Player player = this.team.get(i);
            player.changeUniversity(this);
            switch (player.position) {
                case LB: case DE: case NT: case DT: case S:
                case CB:
                    defRating += player.getRating();
                    defPlayerCount += 1;
                    break;
                case FB: case TE: case T: case WR: case RB:
                case QB: case G: case C:
                    offRating += player.getRating();
                    offPlayerCount += 1;
                    break;
            }
        }

        // Calculate averages
        this.offRating = offRating/offPlayerCount;
        this.defRating = defRating/defPlayerCount;
    }
}
