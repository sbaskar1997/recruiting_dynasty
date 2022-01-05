package People;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Traits {
    LEADER,
    ECCENTRIC,
    RECKLESS,
    CALM, ANXIOUS, JOKESTER,
    COCKY,
    INTELLIGENT, BRAVE, OUTSPOKEN,
    HONEST, COWARDLY, AMBITIOUS,
    FLEXIBLE, FRIENDLY, HOSTILE;

//    ArrayList<Traits> traits = player.getTraits();
//    for (int j = 0; j < traits.length(); j++) {
//        trait = traits.get(j);
//        player.applyTrait(trait); //we want to apply trait when we add it to player
//    }
    // Define fields
    private List<Double> values = new ArrayList<Double>();
    private List<String> traits = new ArrayList<String>();

    // Constructors
    private Traits() {}
    private Traits(double value, String trait) {
        this.values.add(value);
        this.traits.add(trait);
    }

    private Traits(List<Double> values, List<String> traits) {
        if (values.size() != traits.size()) {
            System.out.println("Applying only first value");
            this.values.add(values.get(0));
        } else {
            this.values = values;
        }
        this.traits = traits;
    }

}
