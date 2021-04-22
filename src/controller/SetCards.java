package controller;

import model.MonsterCard;
import model.SpellCard;
import model.TrapCard;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SetCards {

    //////////CSV
    public static void readingCSVFileMonster() {
        String filePath = "C:\\Users\\Partiran\\IdeaProjects\\ap11\\Monster.csv";
        try {
            BufferedReader readFile = new BufferedReader(new FileReader(filePath));
            String readFileRow;
            while ((readFileRow = readFile.readLine()) != null) {
                String[] data = readFileRow.split(",");
                int level = Integer.parseInt(data[1]);
                int attack = Integer.parseInt(data[5]);
                int defend = Integer.parseInt(data[6]);
                int price = Integer.parseInt(data[8]);
                boolean isScanner = false;
                if (data[0].equals("Scanner")) {
                    isScanner = true;
                }
                new MonsterCard(data[2], data[0], level, data[3], attack, defend, "Monster", data[4], isScanner, data[7], price);
            }
            readFile.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readingCSVFileTrapSpell() {
        String filePath = "C:\\Users\\Partiran\\IdeaProjects\\ap11\\SpellTrap.csv";
        try {
            BufferedReader readFile = new BufferedReader(new FileReader(filePath));
            String readFileRow;
            while ((readFileRow = readFile.readLine()) != null) {
                String[] data = readFileRow.split(",");
                int price = Integer.parseInt(data[5]);
                if (data[1].equals("Trap")) {
                    new TrapCard(data[0], data[1], data[2], data[3], price, data[4]);
                }
                if (data[1].equals("Spell")) {
                    new SpellCard(data[0], data[1], data[2], data[3], price, data[4]);
                }
            }
            readFile.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
