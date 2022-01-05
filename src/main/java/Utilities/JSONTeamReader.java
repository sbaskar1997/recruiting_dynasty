package Utilities;

import Misc.City;
import Misc.State;
import Misc.Team;
import People.Player;
import People.Traits;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JSONTeamReader extends JSONReader {
    public static void main(String[] args) {
        String path = "/json/TestTeam.json";
        JSONTeamReader test = new JSONTeamReader(path);
        System.out.println();
    }
    public JSONTeamReader() { super(); }
    public JSONTeamReader(String path) {
        // Inherit From JSONReader
        super(path);

        // Parse name
        String teamName = (String) this.document.get("name");
        List<Map> players = (List<Map>) this.document.get("players");
        List<Player> roster = new ArrayList<Player>();

        // Iterate through players
        for (int i = 0; i < players.size(); i++) {
            Map currentPlayerMap = players.get(i);
            Player tmp = processPlayer(currentPlayerMap);
            roster.add(tmp);
        }

    }

    public Player processPlayer(Map playerJSON) {
        // Get trait list (string)
        List<String> traitListStr = (List<String>) playerJSON.get("traits");
        List<Traits> traitList = new ArrayList<Traits>();

        // Convert to array of traits
        for (int i = 0; i < traitListStr.size(); i++) {
            Traits t = Traits.valueOf(traitListStr.get(i));
            traitList.add(t);
        }

        // Get player name
        String playerName = (String) playerJSON.get("name");

        // Get map of attributes
        Map attributes = (Map) playerJSON.get("attributes");

        // Get home city/state
        State homeState = new State((String) playerJSON.get("state"));
        City homeCity = new City((String) playerJSON.get("city"), homeState);

        // Get age and position
        int age = (int) playerJSON.get("age");
        Player.Position pos = Player.Position.valueOf((String) playerJSON.get("position"));

        // Initialize player
        Player player = new Player(playerName, age, null, null, pos);

        // Add attributes
        player.setAttributes(attributes);

        // Add traits
        player.addTrait(traitList);

        return player;
    }
}
