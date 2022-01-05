package Utilities;

import Misc.City;
import Misc.State;
import People.Player;
import People.Traits;

import java.util.ArrayList;
import java.util.Map;

public class JSONPlayerReader extends JSONReader {
    public JSONPlayerReader() {};

    public JSONPlayerReader(String path) {
        // Inherit instantiation
        super(path);

        // Get traits
        ArrayList<String> traitListStr = (ArrayList<String>) this.document.get("traits");
        ArrayList<Traits> traitList = new ArrayList<Traits>();
        for (int i = 0; i < traitListStr.size(); i++) {
            Traits trait = Traits.valueOf(traitListStr.get(i));
            traitList.add(trait);
        }

        // Get player name
        String playerName = (String) this.document.get("name");

        // Get map of attributes
        Map attributes = (Map) this.document.get("attributes");

        // Get home city/state
        State homeState = new State((String) this.document.get("state"));
        City homeCity = new City((String) this.document.get("city"), homeState);

        // Get age and position
        int age = (int) this.document.get("age");
        Player.Position pos = Player.Position.valueOf((String) this.document.get("position"));

        // Initialize player
        Player player = new Player(playerName, age, null, null, pos);

        // Add attributes
        player.setAttributes(attributes);

        // Add traits
        player.addTrait(traitList);
    }
}
