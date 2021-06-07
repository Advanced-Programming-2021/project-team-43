package controller;
import model.*;
import java.io.*;


public class SetCards {

    public static void readingCSVFileMonster() {
        String filePath = "Monster.csv";
        try {
            BufferedReader readFile = new BufferedReader(new FileReader(filePath));
            String readFileRow;
            int r = 0;
            String[] data = new String[10];
            while ((readFileRow = readFile.readLine()) != null) {
                data[r] = readFileRow;
                if (r == 8) {
                    int level = Integer.parseInt(data[1]);
                    int attack = Integer.parseInt(data[5]);
                    int defend = Integer.parseInt(data[6]);
                    int price = Integer.parseInt(data[8]);
                    boolean isScanner = false;
                    if (data[0].equals("Scanner")) {
                        isScanner = true;
                    }
                    new MonsterCard(data[2], data[0], level, data[3], attack, defend, "Monster", data[4], isScanner, data[7], price,null);
                    r = 0;
                    continue;
                }
                r++;
            }
            readFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readingCSVFileTrapSpell() {
        String filePath = "SpellTrap.csv";
        try {
            BufferedReader readFile = new BufferedReader(new FileReader(filePath));
            String readFileRow;
            int t = 0;
            String[] data = new String[6];
            while ((readFileRow = readFile.readLine()) != null) {
                data[t] = readFileRow;
                if (t == 5) {
                    int price = Integer.parseInt(data[5]);
                    if (data[1].equals("Trap")) {
                        new TrapCard(data[0], data[1], data[2], data[3], price, data[4],null);
                    }
                    if (data[1].equals("Spell")) {
                        new SpellCard(data[0], data[1], data[2], data[3], price, data[4],null);
                    }
                    t = 0;
                    continue;
                }
                t++;
            }
            readFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
