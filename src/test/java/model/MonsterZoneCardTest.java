package model;
import controller.*;
import org.junit.*;
import java.util.*;
import static org.junit.Assert.*;



public class MonsterZoneCardTest {

    MonsterZoneCard monster1;
    MonsterZoneCard monster2;
    Player player;
    Player player2;

    @Before
    public void before() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        DeckModel deckModel = new DeckModel("deck");
        new UserModel("me", "p", "me");
        MainMenuController.username = "me";
        GameMatController.onlineUser = "me";
        GameMatController.rivalUser = "me2";
        for (int i = 0; i < 5; i++) {
            deckModel.addCardToMain("Yami");
            deckModel.addCardToMain("Trap Hole");
            deckModel.addCardToMain("Suijin");
            deckModel.addCardToMain("Magic Jammer");
        }
        player = new Player("me", deckModel, true, 1);
        new UserModel("me2", "p", "me2");
        player2 = new Player("me2", deckModel, false, 1);
        monster1 = new MonsterZoneCard("me2", "Battle warrior", "OO", false, false);
        monster2 = new MonsterZoneCard("me2", "Battle warrior", "DO", false, false);
    }

    @Test
    public void getMonsterName() {
        Assert.assertEquals("Battle warrior", monster1.getMonsterName());
    }

    @Test
    public void getAddress() {
        assertEquals(1, monster1.getAddress());
    }

    @Test
    public void getMode() {
        assertEquals("OO", monster1.getMode());
    }

    @Test
    public void setMode() {
        monster1.setMode("DH");
        assertEquals("DH", monster1.getMode());
    }

    @Test
    public void getAttack() {
        assertEquals(700, monster1.getAttack());
    }

    @Test
    public void setAttack() {
        monster1.setAttack(2000);
        assertEquals(2000, monster1.getAttack());
    }

    @Test
    public void changeAttack() {
        monster1.changeAttack(300);
        assertEquals(1000, monster1.getAttack());
    }

    @Test
    public void getDefend() {
        assertEquals(1000, monster1.getDefend());
    }

    @Test
    public void changeDefend() {
        monster1.changeDefend(-200);
        assertEquals(800, monster1.getDefend());
    }

    @Test
    public void getLevel() {
        assertEquals(3, monster1.getLevel());
    }

    @Test
    public void getMonsterType() {
        assertEquals("Warrior", monster1.getMonsterType());
    }

    @Test
    public void getHaveChangedPositionThisTurn() {
        assertFalse(monster1.getHaveAttackThisTurn());
    }

    @Test
    public void setHaveChangedPositionThisTurn() {
        monster1.setHaveChangedPositionThisTurn(true);
        assertTrue(monster1.getHaveChangedPositionThisTurn());
    }

    @Test
    public void getHaveAttackThisTurn() {
        assertFalse(monster1.getHaveAttackThisTurn());
    }

    @Test
    public void setHaveAttackThisTurn() {
        monster1.setHaveAttackThisTurn(true);
        assertTrue(monster1.getHaveAttackThisTurn());
    }

    @Test
    public void getCanAttack() {
        assertTrue(monster1.getCanAttack());
    }

    @Test
    public void setCanAttack() {
        monster1.setCanAttack(false);
        assertFalse(monster1.getCanAttack());
    }

    @Test
    public void getCanAttackToThisMonster() {
        assertTrue(monster1.getCanAttackToThisMonster());
    }

    @Test
    public void setCanAttackToThisMonster() {
        monster1.setCanAttackToThisMonster(true);
        assertTrue(monster1.getCanAttackToThisMonster());
    }

    @Test
    public void getIsEffectUsed() {
        assertFalse(monster1.getIsEffectUsed());
    }

    @Test
    public void setIsEffectUsed() {
        monster1.setIsEffectUsed(true);
        assertTrue(monster1.getIsEffectUsed());
    }

    @Test
    public void getIsForOneTurn() {
        assertFalse(monster1.getIsForOneTurn());
    }

    @Test
    public void getNumberOfFullHouse() {
        Assert.assertEquals(0, MonsterZoneCard.getNumberOfFullHouse("me"));
        new MonsterZoneCard("me", "Crawling dragon", "OO", false, false);
        Assert.assertEquals(1, MonsterZoneCard.getNumberOfFullHouse("me"));
        Assert.assertEquals(0, MonsterZoneCard.getNumberOfFullHouse("sth"));
    }

    @Test
    public void getAllEffectedMonster() {
        assertEquals(1, monster1.allEffectiveSpell.size());
    }

    @Test
    public void setAllEffectedMonster() {
        List<Integer> allEffectiveSpell = new ArrayList<>();
        allEffectiveSpell.add(1);
        allEffectiveSpell.add(2);
        monster1.setAllEffectedMonster("me", allEffectiveSpell);
        assertEquals(allEffectiveSpell, monster1.getAllEffectedMonster("me"));
    }

    @Test
    public void removeMonsterFromZone() {
        monster1.removeMonsterFromZone();
        assertNull(MonsterZoneCard.getAllMonstersByPlayerName("me").get(1));
    }

    @Test
    public void changeOneTurnMonstersIsEffectUsed() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Texchanger");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard ee = new MonsterZoneCard("roya", "Texchanger", "DH", false, false);
        MonsterZoneCard.changeOneTurnMonstersIsEffectUsed("roya");
        assertFalse(ee.getIsEffectUsed());
    }

    @Test
    public void getAddressByMonsterName() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Texchanger");
        }
        new Player("roya", deck, true, 1);
        new MonsterZoneCard("roya", "Texchanger", "DH", false, false);
        assertEquals(1, MonsterZoneCard.getAddressByMonsterName("roya", "Texchanger"));/////////
    }

    @Test
    public void testToString() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Texchanger");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard se = new MonsterZoneCard("roya", "Texchanger", "DH", false, false);
        assertEquals(234, se.toString().length());

    }

    @Test
    public void getAllMonstersMode() {
        Assert.assertEquals(6, MonsterZoneCard.getAllMonstersMode("me").length);
        Assert.assertEquals(6, MonsterZoneCard.getAllMonstersMode("sth").length);
    }

    @Test
    public void isThisMonsterTypeExisted() {
        new MonsterZoneCard("me", "Battle warrior", "OO", true, false);
        assertTrue(MonsterZoneCard.isThisMonsterTypeExisted("Warrior", "me"));
        assertFalse(MonsterZoneCard.isThisMonsterTypeExisted("Spellcaster", "me"));
    }

    @Test
    public void removeUselessMonster() {
        new MonsterZoneCard("me", "Battle warrior", "OO", true, true);
        MonsterZoneCard.removeUselessMonster("me");
        Assert.assertEquals(0, MonsterZoneCard.allMonsterCards.get("me").size());
    }

    @Test
    public void getMonsterCardByAddress() {
        MonsterZoneCard monster = new MonsterZoneCard("me", "Scanner", "OO", true, false);
        assertEquals(monster, MonsterZoneCard.getMonsterCardByAddress(1, "me"));
    }

    @Test
    public void getAllMonstersByPlayerName() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Texchanger");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard se = new MonsterZoneCard("roya", "Texchanger", "DH", false, false);
        HashMap<Integer, MonsterZoneCard> allCard = new HashMap<>();
        allCard.put(1, se);
        assertEquals(se, MonsterZoneCard.getMonsterCardByAddress(se.getAddress(), "roya"));
    }

    @Test
    public void getSumOfMonstersLevel() {
        SetCards.readingCSVFileTrapSpell();
        SetCards.readingCSVFileMonster();
        new UserModel("roya", "p", "roya");
        MainMenuController.username = "roya";
        DeckModel deck = new DeckModel("deck");
        for (int i = 0; i < 41; i++) {
            deck.addCardToMain("Texchanger");
        }
        new Player("roya", deck, true, 1);
        MonsterZoneCard se = new MonsterZoneCard("roya", "Texchanger", "DH", false, false);
        assertEquals(1, MonsterZoneCard.getSumOfMonstersLevel("roya"));
    }

    @Test
    public void changeTurn() {
        MonsterZoneCard monster = new MonsterZoneCard("me", "Mirage Dragon", "OO", false, false);
        MonsterZoneCard.changeTurn("me");
        assertTrue(monster.getCanAttackToThisMonster());
    }


    @Test
    public void getIsScanner() {
        new MonsterZoneCard("me", "Scanner", "OO", true, false);
        Assert.assertEquals(true, MonsterZoneCard.getMonsterCardByAddress(1, "me").getIsScanner());
    }

    @Test
    public void changeTheMonsterFace() {
        monster1.changeTheMonsterFace("Bitron");
        Assert.assertEquals("Bitron", monster1.getMonsterName());
    }

    @Test
    public void getAddressOfScanner() {
        new MonsterZoneCard("me", "Scanner", "OO", true, false);
        Assert.assertEquals(1, MonsterZoneCard.getAddressOfScanner("me"));
    }

}