package Misc;

public class City {
    public String name; public State state;

    public City() {
        this.name = null;
        this.state = new State();
    }

    public City(String cityName, State state) {
        this.name = cityName;
        this.state = state;
    }

    public City(String cityName, String stateName) {
        this.name = cityName;
        this.state = new State(stateName);
    }
}
