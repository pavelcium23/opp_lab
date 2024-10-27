package oop.practice;

import java.util.List;

public class Creature {
    private String name;
    private boolean isHumanoid;
    private String planet;
    private int age;
    private List<String> traits;

    // Constructor
    public Creature(String name, boolean isHumanoid, String planet, int age, List<String> traits) {
        this.name = name;
        this.isHumanoid = isHumanoid;
        this.planet = planet;
        this.age = age;
        this.traits = traits;
    }

    // Getters
    public String getName(){
        return name;
    }

    public boolean isHumanoid(){
        return isHumanoid;
    }

    public String getPlanet(){
        return planet;
    }

    public int getAge(){
        return age;
    }

    public List<String> getTraits(){
        return traits;
    }
    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setHumanoid(boolean isHumanoid) {
        this.isHumanoid = isHumanoid;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setTraits(List<String> traits) {
        this.traits = traits;
    }


    @Override
    public String toString() {
        return "Name: " + name + "\n" +
                "isHumanoid: " + isHumanoid + "\n" +
                "Planet: " + planet + "\n" +
                "Age: " + age + "\n" +
                "Traits: " + traits;
    }
}





