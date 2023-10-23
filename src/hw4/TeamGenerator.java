/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package hw4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * 
 * @author ivanp
 */

             //https://github.com/IvanPopoff/HW4

class Person {
    int id;
    String firstName;
    String lastName;
    String email;

    public Person(int id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}

class Team {
    String name;
    List<Person> members;

    public Team(String name) {
        this.name = name;
        this.members = new ArrayList<>();
    }

    public void addMember(Person person) {
        members.add(person);
    }

    public String getName() {
        return name;
    }

    public List<Person> getMembers() {
        return members;
    }
}

public class TeamGenerator {
    public static void main(String[] args) {
        List<Person> people = new ArrayList<>();

        // Read data from the file and load it into memory
        try (BufferedReader br = new BufferedReader(new FileReader("MOCK_DATA.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                String firstName = data[1];
                String lastName = data[2];
                String email = data[3];
                Person person = new Person(id, firstName, lastName, email);
                people.add(person);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Shuffle the list of people to randomize the selection
        Collections.shuffle(people);

        List<Team> teams = new ArrayList<>();
        int numTeams = 20;
        int teamSize = 5;

        for (int i = 1; i <= numTeams; i++) {
            Team team = new Team("Team " + i);
            Set<Integer> usedIds = new HashSet<>();

            for (int j = 0; j < teamSize; j++) {
                if (people.isEmpty()) {
                    System.out.println("Not enough people to form all teams.");
                    break;
                }

                Person person = people.remove(0);

                // Ensure no duplicate members in a team
                if (!usedIds.contains(person.id)) {
                    team.addMember(person);
                    usedIds.add(person.id);
                } else {
                    // If a duplicate is found, put the person back into the list
                    people.add(person);
                }
            }

            teams.add(team);
        }

        // Print each team (name and members) to the console
        for (Team team : teams) {
            System.out.println("Team Name: " + team.getName());
            System.out.println("Team Members:");
            for (Person member : team.getMembers()) {
                System.out.println(member.firstName + " " + member.lastName);
            }
            System.out.println();
        }
    }
}
