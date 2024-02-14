package info.charlieward.lousynetvanishplugin;

import redis.clients.jedis.Jedis;

import java.util.Objects;

public class test {

    public static Jedis jedis = new Jedis();
    public static String playerName = "NewName";
    public static String playerName2 = "D4rthMonkey";

    public static void setup() {
        jedis.set("VanishPlayers", "NewName");
    }

    public static void main(String[] args) {

        setup();
        runCommand();

    }

    public static void runCommand() {
        int place = 0;
        String newList = "";
        Boolean alreadyVanished = false;
        String vanishedPlayers = jedis.get("VanishPlayers");
        String[] vanishPlayersIndividual = vanishedPlayers.split(",");
        for (int i = 0; i < vanishPlayersIndividual.length; i++) {
            if (Objects.equals(playerName, vanishPlayersIndividual[i])) {
                alreadyVanished = true;
                place = i;
            }
        }
        if (alreadyVanished) {
            System.out.println("Already on list removing needs to be done");
            for (int i = 0; i < vanishPlayersIndividual.length; i++) {
                if (i != place) {
                    System.out.println(newList);
                    newList = newList + vanishPlayersIndividual[i] + ",";
                }
            }
            if (!newList.isEmpty()) {
                newList = newList.substring(0, newList.length()-1);
            }
            jedis.set("VanishPlayers", newList);
            System.out.println("Removed from list");
        } else {
            System.out.println(playerName + " added to list");
            if (Objects.equals(vanishPlayersIndividual[0], "")) {
                vanishedPlayers = playerName;
            } else {
                vanishedPlayers = vanishedPlayers + "," + playerName;
            }
            System.out.println(vanishedPlayers);
            jedis.set("VanishPlayers", vanishedPlayers);
        }
    }

}
