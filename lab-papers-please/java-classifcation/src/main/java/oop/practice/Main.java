package oop.practice;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Main {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File inputFile = new File("/Users/suleimanpasa/oop-course-repo/lab-papers-please/java-classifcation/src/main/resources/test-input.json");
        JsonNode data = mapper.readTree(inputFile).get("data");

        // Initialize universes
        Universe starWars = new Universe("StarWars", new ArrayList<>());
        Universe hitchhikers = new Universe("HitchHiker", new ArrayList<>());
        Universe marvel = new Universe("Marvel", new ArrayList<>());
        Universe rings = new Universe("Rings", new ArrayList<>());

        // Process each individual and classify into universes
        for (JsonNode entry : data) {
            Creature creature = new Creature(entry);
            Criteria criteria = new Criteria(entry);

            // Example classification logic
            if (criteria.age > 100) {
                rings.addIndividual(creature);  // Example: creatures over age 100 go to the Rings universe
            } else if (criteria.hasTraits("force-sensitive")) {
                starWars.addIndividual(creature);  // Example: creatures with "force-sensitive" trait go to Star Wars
            } else if (criteria.planet.equals("Earth") && criteria.isHumanoid) {
                marvel.addIndividual(creature);  // Example: humanoids from Earth go to Marvel
            } else {
                hitchhikers.addIndividual(creature);  // Default case for Hitchhiker's universe
            }
        }

        // Write each universe to its own JSON file
        mapper.writeValue(new File("lab-papers-please/java-classifcation/src/main/resources/output/starwars.json"), starWars);
        mapper.writeValue(new File("lab-papers-please/java-classifcation/src/main/resources/output/hitchhiker.json"), hitchhikers);
        mapper.writeValue(new File("lab-papers-please/java-classifcation/src/main/resources/output/marvel.json"), marvel);
        mapper.writeValue(new File("lab-papers-please/java-classifcation/src/main/resources/output/rings.json"), rings);
    }
}

// Creature class
class Creature {
    private int id;
    private boolean isHumanoid;
    private String planet;
    private int age;
    private List<String> traits;

    // Constructor from JsonNode
    public Creature(JsonNode entry) {
        this.id = entry.has("id") ? entry.get("id").asInt() : -1;
        this.isHumanoid = entry.has("isHumanoid") ? entry.get("isHumanoid").asBoolean() : false;
        this.planet = entry.has("planet") ? entry.get("planet").asText() : "Unknown";
        this.age = entry.has("age") ? entry.get("age").asInt() : -1;
        this.traits = new ArrayList<>();
        if (entry.has("traits") && entry.get("traits").isArray()) {
            for (JsonNode trait : entry.get("traits")) {
                traits.add(trait.asText());
            }
        }
    }

    // Getters and toString for debugging purposes
    public int getId() { return id; }
    public boolean isHumanoid() { return isHumanoid; }
    public String getPlanet() { return planet; }
    public int getAge() { return age; }
    public List<String> getTraits() { return traits; }

    @Override
    public String toString() {
        return "Id: " + id + "\n" +
                "isHumanoid: " + isHumanoid + "\n" +
                "Planet: " + planet + "\n" +
                "Age: " + age + "\n" +
                "Traits: " + traits;
    }
}

// Criteria class for applying filters
class Criteria {
    public String planet;
    public boolean isHumanoid;
    public int age;
    public List<String> traits;

    // Constructor initializes fields with default values if missing in JSON
    public Criteria(JsonNode entry) {
        this.planet = entry.has("planet") ? entry.get("planet").asText() : "";
        this.isHumanoid = entry.has("isHumanoid") ? entry.get("isHumanoid").asBoolean() : false;
        this.age = entry.has("age") ? entry.get("age").asInt() : 0;
        this.traits = new ArrayList<>();
        if (entry.has("traits") && entry.get("traits").isArray()) {
            for (JsonNode trait : entry.get("traits")) {
                traits.add(trait.asText());
            }
        }
    }

    // Checks if traits contain specific values
    public boolean hasTraits(String... traitsToCheck) {
        for (String trait : traitsToCheck) {
            if (!this.traits.contains(trait)) {
                return false;
            }
        }
        return true;
    }
}

// Universe class to store creatures belonging to each universe
class Universe {
    private String name;
    private List<Creature> individuals;

    public Universe(String name, List<Creature> individuals) {
        this.name = name;
        this.individuals = individuals;
    }

    // Add individual to universe
    public void addIndividual(Creature creature) {
        individuals.add(creature);
    }

    // Getters for serialization
    public String getName() { return name; }
    public List<Creature> getIndividuals() { return individuals; }
}
