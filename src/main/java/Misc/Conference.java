package Misc;

import Schools.University;

import java.util.ArrayList;
import java.util.List;

public class Conference {
    // Required fields
    public String conferenceName;
    public double prestige;

    // List of schools (conference can be empty)
    public List<University> schools = new ArrayList<University>();

    // Constructors
    public Conference(String conferenceName) {
        this.conferenceName = conferenceName;
        this.prestige = 50;
    }

    public Conference(String conferenceName, double prestige) {
        this.conferenceName = conferenceName;
        if (prestige > 100) {
            this.prestige = 100;
        } else if (prestige < 0) {
            this.prestige = 0;
        } else {
            this.prestige = prestige;
        }
    }

    // Function to add university
    public void addTeam(University university) {
        this.schools.add(university);
    }


}
