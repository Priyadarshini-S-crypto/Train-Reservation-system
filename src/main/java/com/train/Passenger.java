
package com.train;

public class Passenger {
    static int idCounter = 1;
    public String name;
    public int age;
    public String berthPreference;
    public int passengerId;
    public String allocated;
    public int number;

    public Passenger() {}

    public Passenger(String name, int age, String berthpreference) {
        this.name = name;
        this.age = age;
        this.berthPreference = berthpreference;
        this.passengerId = idCounter++;
        this.allocated = " ";
        this.number = 0;
    }
}
