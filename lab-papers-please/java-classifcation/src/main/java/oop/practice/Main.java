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
        File inputFile = new File("/Users/suleimanpasa/oop-course-repo/lab-papers-please/java-classifcation/src/main/resources/input.json");
        JsonNode data = mapper.readTree(inputFile).get("data");

        // Initialize universes with names and empty individual lists
        Universe starWars = new Universe("StarWars", new ArrayList<>());
        Universe hitchhikers = new Universe("HitchHiker", new ArrayList<>());
        Universe marvel = new Universe("Marvel", new ArrayList<>());
        Universe rings = new Universe("Rings", new ArrayList<>());

        // Process each individual and classify based on criteria
        for (JsonNode entry : data) {
            Creature creature = new Creature(entry);
            Criteria criteria = new Criteria(entry);

            classifyCreature(creature, criteria, starWars, hitchhikers, marvel, rings);
        }

        // Write each universe to its own JSON file
        mapper.writeValue(new File("lab-papers-please/java-classifcation/src/main/resources/output/starwars.json"), starWars);
        mapper.writeValue(new File("lab-papers-please/java-classifcation/src/main/resources/output/hitchhiker.json"), hitchhikers);
        mapper.writeValue(new File("lab-papers-please/java-classifcation/src/main/resources/output/marvel.json"), marvel);
        mapper.writeValue(new File("lab-papers-please/java-classifcation/src/main/resources/output/rings.json"), rings);
    }

    // Classification logic
    private static void classifyCreature(Creature creature, Criteria criteria, Universe starWars, Universe hitchhikers, Universe marvel, Universe rings) {
        if (criteria.planet.equals("Endor") || criteria.planet.equals("Kashyyyk")|| criteria.hasTraits("HAIRY", "TALL","SHORT")) {
            starWars.addIndividual(creature); // Star Wars: Individuals from Tatooine or with specific traits
        } else if (criteria.planet.equals("Betelgeuse") && criteria.isHumanoid || criteria.planet.equals("Asgard") && criteria.isHumanoid || criteria.hasTraits("BLONDE", "TALL")) {
            marvel.addIndividual(creature); // Marvel: Asgardians or strong, immortal humanoids
        } else if (criteria.planet.equals("Earth") || criteria.isHumanoid || criteria.age > 200 && criteria.hasTraits("BLONDE","POINTY_EARS","SHORT","BULKY")) {
            rings.addIndividual(creature); // Rings: Ancient humanoids from Earth
        } else if (criteria.planet.equals("Vogsphere") || criteria.age < 200 && criteria.hasTraits("EXTRA_HEAD","GREEN","EXTRA_ARMS","BULKY")) {
            hitchhikers.addIndividual(creature); // Hitchhiker: Characters from Betelgeuse or unusual traits like extra head
        } else {
            // Default case if no criteria match
            hitchhikers.addIndividual(creature);
        }
    }
}

// Creature class representing each character with attributes
class Creature {
    private int id;
    private boolean isHumanoid;
    private String planet;
    private int age;
    private List<String> traits;

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

// Criteria class to filter and check creature's properties
class Criteria {
    public String planet;
    public boolean isHumanoid;
    public int age;
    public List<String> traits;

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

    public boolean hasTraits(String... traitsToCheck) {
        for (String trait : traitsToCheck) {
            if (!this.traits.contains(trait)) {
                return false;
            }
        }
        return true;
    }
}

// Universe class to store creatures of each universe
class Universe {
    private String name;
    private List<Creature> individuals;

    public Universe(String name, List<Creature> individuals) {
        this.name = name;
        this.individuals = individuals;
    }

    public void addIndividual(Creature creature) {
        individuals.add(creature);
    }

    public String getName() { return name; }
    public List<Creature> getIndividuals() { return individuals; }
}
