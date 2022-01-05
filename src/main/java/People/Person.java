package People;

import Misc.City;
import Schools.HighSchool;
import Schools.University;

public class Person {
    // Required fields
    public String name;
    public int age;
    public University university;
    public HighSchool highSchool;
    public City homeTown;

    public Person() {
        this.name = "";
        this.age = -1;
        this.university = null;
        this.homeTown = null;
    }

    public Person(String name, int age, HighSchool highSchool, City hometown) {
        this.name = name;
        this.age = age;
        this.highSchool = highSchool;
        this.homeTown = hometown;
    }

    public Person(String name, int age, University university, HighSchool highSchool, City hometown) {
        this.name = name;
        this.age = age;
        this.university = university;
        this.highSchool = highSchool;
        this.homeTown = hometown;
    }

    public void changeUniversity(University university) {
        this.university = university;
    }
}
