package oop.practice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class Main {

  public static void main(String[] args) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      // Read the JSON file
      File inputFile = new File("/Users/suleimanpasa/oop-course-repo/lab-papers-please/java-classifcation/src/main/resources/test-input.json");
      JsonNode root = mapper.readTree(inputFile);
      JsonNode data = root.get("data");

      // Check if data is not null and is an array
      if (data != null && data.isArray()) {
        // Iterate over each JSON object
        for (JsonNode individual : data) {
          // Safely get values with default fallbacks
          int id = getIntValue(individual, "id", -1);
          boolean isHumanoid = getBooleanValue(individual, "isHumanoid", false);
          String planet = getValue(individual, "planet", "Unknown");
          int age = getIntValue(individual, "age", -1);

          // Extract traits safely
          List<String> traits = new ArrayList<>();
          if (individual.has("traits") && individual.get("traits").isArray()) {
            for (JsonNode trait : individual.get("traits")) {
              traits.add(trait.asText());
            }
          }

          // Create Creature instance and print the details
          Creature creature = new Creature(id, isHumanoid, planet, age, traits);
          System.out.println(creature);
          System.out.println("-----------------------");
        }
      } else {
        System.out.println("No data found in the JSON file or 'data' is not an array.");
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Helper method to get a String value from JsonNode with a default
  private static String getValue(JsonNode node, String key, String defaultValue) {
    return node.has(key) ? node.get(key).asText() : defaultValue;
  }

  // Helper method to get a boolean value from JsonNode with a default
  private static boolean getBooleanValue(JsonNode node, String key, boolean defaultValue) {
    return node.has(key) ? node.get(key).asBoolean() : defaultValue;
  }

  // Helper method to get an int value from JsonNode with a default
  private static int getIntValue(JsonNode node, String key, int defaultValue) {
    return node.has(key) ? node.get(key).asInt() : defaultValue;
  }
}


class Creature {
  private int id;
  private boolean isHumanoid;
  private String planet;
  private int age;
  private List<String> traits;

  // Constructor
  public Creature(int id, boolean isHumanoid, String planet, int age, List<String> traits) {
    this.id = id;
    this.isHumanoid = isHumanoid;
    this.planet = planet;
    this.age = age;
    this.traits = traits;
  }

  // Getters
  public int getName() {
    return id;
  }

  public boolean isHumanoid() {
    return isHumanoid;
  }

  public String getPlanet() {
    return planet;
  }

  public int getAge() {
    return age;
  }

  public List<String> getTraits() {
    return traits;
  }

  @Override
  public String toString() {
    return "Id: " + id + "\n" +
            "isHumanoid: " + isHumanoid + "\n" +
            "Planet: " + planet + "\n" +
            "Age: " + age + "\n" +
            "Traits: " + traits;
  }
}
