package controller;

import model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class GameMatControllerTest {

    Player player1;

    @Before
    public void beforeAllClassTest() {
        DeckModel deckModel;
        SetCards.readingCSVFileMonster();
        SetCards.readingCSVFileTrapSpell();
        new UserModel("Guy", "123", "me");
        new UserModel("Guy2", "123", "me2");
        MainMenuController.username = "Guy";
        deckModel = new DeckModel("myDeck");
        for (int i = 0; i < 10; i++)
            deckModel.addCardToMain("Yami");
        deckModel.addCardToSide("Yami");
        for (int i = 0; i < 5; i++)
            deckModel.addCardToSide("Battle Ox");
        player1 = new Player("me", deckModel, true, 1);
        new Player("me2", deckModel, false, 1);
        GameMatController.onlineUser = "me";
        GameMatController.rivalUser = "me2";
        String input = "menu exit";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    }

    @Test
    public void run() {
        String input = "menu exit";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals(0, GameMatController.run("me", "me2"));
    }

    @Test
    public void commandController() {
        MainMenuController.username2 = "Guy2";
        assertEquals(39, GameMatController.commandController("menu exit"));
        assertEquals(1, GameMatController.commandController("select --monster 5 --opponent"));
        assertEquals(2, GameMatController.commandController("select --monster 2"));
        assertEquals(3, GameMatController.commandController("select --opponent --monster 1"));
        assertEquals(4, GameMatController.commandController("select --opponent 2 --monster"));
        assertEquals(5, GameMatController.commandController("select --monster --opponent 2"));
        assertEquals(6, GameMatController.commandController("select 2 --monster --opponent"));
        assertEquals(7, GameMatController.commandController("select 2 --opponent --monster"));
        assertEquals(8, GameMatController.commandController("select --spell 1 --opponent"));
        assertEquals(9, GameMatController.commandController("select --spell 1"));
        assertEquals(10, GameMatController.commandController("select --opponent --spell 1"));
        assertEquals(11, GameMatController.commandController("select --spell --opponent 1"));
        assertEquals(12, GameMatController.commandController("select --opponent 1 --spell"));
        assertEquals(13, GameMatController.commandController("select 1 --opponent --spell"));
        assertEquals(14, GameMatController.commandController("select 1 --spell --opponent"));
        assertEquals(15, GameMatController.commandController("s -f"));
        assertEquals(16, GameMatController.commandController("select --field --opponent"));
        assertEquals(17, GameMatController.commandController("select --hand 1"));
        assertEquals(18, GameMatController.commandController("select 1 --hand"));
        assertEquals(19, GameMatController.commandController("select -d"));
        assertEquals(20, GameMatController.commandController("next phase"));
        assertEquals(21, GameMatController.commandController("summon"));
        assertEquals(22, GameMatController.commandController("set"));
        assertEquals(40, GameMatController.commandController(""));
        assertEquals(24, GameMatController.commandController("flip-summon"));
        assertEquals(26, GameMatController.commandController("attack direct"));
        assertEquals(27, GameMatController.commandController("activate effect"));
        System.setIn(new ByteArrayInputStream("back".getBytes()));
        assertEquals(28, GameMatController.commandController("card show --selected"));
        System.setIn(new ByteArrayInputStream("back".getBytes()));
        assertEquals(29, GameMatController.commandController("show graveyard"));
        System.setIn(new ByteArrayInputStream("back".getBytes()));
        assertEquals(30, GameMatController.commandController("show graveyard --opponent"));
        System.setIn(new ByteArrayInputStream("back".getBytes()));
        assertEquals(31, GameMatController.commandController("show main deck"));
        System.setIn(new ByteArrayInputStream("back".getBytes()));
        assertEquals(32, GameMatController.commandController("show side deck"));
        System.setIn(new ByteArrayInputStream("back".getBytes()));
        assertEquals(33, GameMatController.commandController("show my hand"));
        assertEquals(37, GameMatController.commandController("increase --LP 12133"));
        assertEquals(40, GameMatController.commandController(""));
        System.setIn(new ByteArrayInputStream("menu exit".getBytes()));
        assertEquals(34, GameMatController.commandController("surrender"));
        System.setIn(new ByteArrayInputStream("menu exit".getBytes()));
        assertEquals(35, GameMatController.commandController("duel set-winner me"));
        System.setIn(new ByteArrayInputStream("menu exit".getBytes()));
        assertEquals(38, GameMatController.commandController("select --hand Battle warrior --force"));
    }

    @Test
    public void AI() {
        GameMatController.AI();
        assertEquals("next phase", GameMatController.command);
        GameMatController.changePhase(GameMatModel.getGameMatByNickname("me").getPhase());
        GameMatController.AI();
        assertEquals("next phase", GameMatController.command);
        GameMatController.changePhase(GameMatModel.getGameMatByNickname("me").getPhase());
        GameMatController.AI();
        assertEquals("select --hand 1", GameMatController.command);
        GameMatController.AI();
        assertEquals("summon", GameMatController.command);
        GameMatController.AI();
        assertEquals("next phase", GameMatController.command);
        GameMatController.AI();
        assertEquals("next phase", GameMatController.command);
        GameMatController.changePhase(GameMatModel.getGameMatByNickname("me").getPhase());
        GameMatController.AI();
        assertEquals("next phase", GameMatController.command);
        GameMatController.changePhase(GameMatModel.getGameMatByNickname("me").getPhase());
        GameMatController.AI();
        assertEquals("next phase", GameMatController.command);
        GameMatController.changePhase(GameMatModel.getGameMatByNickname("me").getPhase());
        GameMatController.AI();
        assertEquals("next phase", GameMatController.command);
        GameMatController.changePhase(GameMatModel.getGameMatByNickname("me").getPhase());
        GameMatController.AI();
        assertEquals("next phase", GameMatController.command);
        GameMatController.changePhase(GameMatModel.getGameMatByNickname("me").getPhase());
        GameMatController.AI();
        assertEquals("next phase", GameMatController.command);
        GameMatController.AI();
        assertEquals("next phase", GameMatController.command);
        GameMatController.changePhase(GameMatModel.getGameMatByNickname("me").getPhase());
    }

    @Test
    public void increaseLP() {
        int life = Player.getPlayerByName("me").getLifePoint();
        GameMatController.increaseLP(3, "me");
        assertEquals(life + 3, Player.getPlayerByName("me").getLifePoint());
    }

    @Test
    public void getRivalUser() {
        GameMatController.rivalUser = "me2";
        assertEquals("me2", GameMatController.rivalUser);
    }

    @Test
    public void getMatcher() {
        GameMatController.rivalUser = "me2";
        assertTrue(GameMatController.getMatcher("duel  set-winner  me2", "duel \\s*set-winner \\s*me2").find());
    }

    @Test
    public void selectMonsterCard() {
        GameMatController.selectMonsterCard(1, true);
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        ByteArrayOutputStream show = new ByteArrayOutputStream();
        System.setOut(new PrintStream(show));
        assertNotEquals("no card found in the given position", show.toString().substring(0, show.toString().length()));
        new MonsterZoneCard("me", "Battle OX", "DH", false, true);
        GameMatController.selectMonsterCard(1, true);
        final ByteArrayOutputStream outContent1 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent1));
        ByteArrayOutputStream show1 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(show1));
        assertNotEquals("card selected", show1.toString().substring(0, show1.toString().length()));
        GameMatController.rivalUser = "me2";
        GameMatController.selectMonsterCard(1, false);
        final ByteArrayOutputStream outContent2 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent2));
        ByteArrayOutputStream show2 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(show2));
        assertNotEquals("no card found in the given position", show2.toString().substring(0, show2.toString().length()));
        new MonsterZoneCard("me2", "Battle OX", "DH", false, true);
        GameMatController.selectMonsterCard(1, false);
        final ByteArrayOutputStream outContent3 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent3));
        ByteArrayOutputStream show3 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(show1));
        assertNotEquals("card selected", show3.toString().substring(0, show3.toString().length()));
    }

    @Test
    public void selectSpellCard() {
        GameMatController.selectSpellCard(1, true);
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        ByteArrayOutputStream show = new ByteArrayOutputStream();
        System.setOut(new PrintStream(show));
        assertNotEquals("no card found in the given position", show.toString().substring(0, show.toString().length()));
        new SpellTrapZoneCard("me", "Trap Hole", "H");
        GameMatController.selectSpellCard(1, true);
        final ByteArrayOutputStream outContent1 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent1));
        ByteArrayOutputStream show1 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(show1));
        assertNotEquals("card selected", show1.toString().substring(0, show1.toString().length()));
        GameMatController.rivalUser = "me2";
        GameMatController.selectSpellCard(1, false);
        final ByteArrayOutputStream outContent2 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent2));
        ByteArrayOutputStream show2 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(show2));
        assertNotEquals("no card found in the given position", show2.toString().substring(0, show2.toString().length()));
        new SpellTrapZoneCard("me2", "Trap Hole", "H");
        GameMatController.selectSpellCard(1, false);
        final ByteArrayOutputStream outContent3 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent3));
        ByteArrayOutputStream show3 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(show1));
        assertNotEquals("card selected", show3.toString().substring(0, show3.toString().length()));
    }

    @Test
    public void selectFieldCard() {
        GameMatController.selectFieldCard(true);
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        ByteArrayOutputStream show = new ByteArrayOutputStream();
        System.setOut(new PrintStream(show));
        assertNotEquals("invalid selection", show.toString().substring(0, show.toString().length()));
        GameMatModel.getGameMatByNickname("me").addToFieldZone("Trap Hole", "H");
        GameMatController.selectFieldCard(true);
        final ByteArrayOutputStream outContent1 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent1));
        ByteArrayOutputStream show1 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(show1));
        assertNotEquals("card selected", show1.toString().substring(0, show1.toString().length()));
        GameMatController.rivalUser = "me2";
        GameMatController.selectFieldCard(false);
        final ByteArrayOutputStream outContent3 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent3));
        ByteArrayOutputStream show3 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(show));
        assertNotEquals("invalid selection", show3.toString().substring(0, show3.toString().length()));
        GameMatModel.getGameMatByNickname("me2").addToFieldZone("Trap Hole", "H");
        GameMatController.selectFieldCard(false);
        final ByteArrayOutputStream outContent4 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent4));
        ByteArrayOutputStream show4 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(show1));
        assertNotEquals("card selected", show4.toString().substring(0, show4.toString().length()));
    }

    @Test
    public void selectHandCard() {
        GameMatController.selectHandCard(1);
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        ByteArrayOutputStream show = new ByteArrayOutputStream();
        System.setOut(new PrintStream(show));
        assertNotEquals("card selected", show.toString().substring(0, show.toString().length()));
    }

    @Test
    public void selectDelete() {
        GameMatController.rivalUser = "me2";
        GameMatController.selectDelete();
        final ByteArrayOutputStream outContent2 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent2));
        ByteArrayOutputStream show2 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(show2));
        assertNotEquals("no card is selected yet", show2.toString().substring(0, show2.toString().length()));
        GameMatController.selectMonsterCard(1, false);
        GameMatController.selectDelete();
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        ByteArrayOutputStream show = new ByteArrayOutputStream();
        System.setOut(new PrintStream(show));
        assertNotEquals("card deselected", show.toString().substring(0, show.toString().length()));
        GameMatController.selectMonsterCard(1, true);
        GameMatController.selectDelete();
        final ByteArrayOutputStream outContent1 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent1));
        ByteArrayOutputStream show1 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(show1));
        assertNotEquals("card deselected", show1.toString().substring(0, show1.toString().length()));
    }

    @Test
    public void errorOfNoCardSelected() {
        GameMatController.selectedOwnCard = "";
        assertFalse(GameMatController.errorOfNoCardSelected("own"));
        new MonsterZoneCard("me", "Battle OX", "DH", false, true);
        GameMatController.selectMonsterCard(1, true);
        assertTrue(GameMatController.errorOfNoCardSelected("own"));
        GameMatController.rivalUser = "me2";
        GameMatController.selectedRivalCard = "";
        assertFalse(GameMatController.errorOfNoCardSelected("rival"));
        new MonsterZoneCard("me2", "Battle OX", "DH", false, true);
        GameMatController.selectMonsterCard(1, false);
        assertTrue(GameMatController.errorOfNoCardSelected("rival"));
    }

    @Test
    public void activateSpellEffect() {
        GameMatController.rivalUser = "me2";
        assertEquals(1, GameMatController.activateSpellEffect(Phase.Draw_Phase));
        GameMatController.selectedOwnCard = "Hand/Magic Jammer/1";
        assertEquals(2, GameMatController.activateSpellEffect(Phase.Draw_Phase));
        GameMatController.selectedOwnCard = "Monster/Axe Raider/1";
        assertEquals(3, GameMatController.activateSpellEffect(Phase.Draw_Phase));
        GameMatController.selectedOwnCard = "sdg";
        assertEquals(4, GameMatController.activateSpellEffect(Phase.Draw_Phase));
        GameMatController.selectedOwnCard = "Trap/Trap/1";
        GameMatController.addToSpellTrapZoneCard("Magic Jammer", "H");
        SpellTrapZoneCard ownSpell = SpellTrapZoneCard.getSpellCardByAddress(1, "me");
        ownSpell.setIsSetInThisTurn(false);
        Player.getPlayerByName("me").setCanUseTrap(true);
        assertEquals(5, GameMatController.activateSpellEffect(Phase.Main_Phase1));
        GameMatController.selectedOwnCard = "Spell/Trap/1";
        ownSpell.setMode("O");
        assertEquals(6, GameMatController.activateSpellEffect(Phase.Main_Phase1));
        GameMatController.selectedOwnCard = "Spell/Advanced Ritual Art/1";
        ownSpell.setMode("H");
        assertEquals(7, GameMatController.activateSpellEffect(Phase.Main_Phase1));


        GameMatController.selectedOwnCard = "Field/Ring of defense/1";
        ownSpell.setIsSetInThisTurn(true);
        assertEquals(0, GameMatController.activateSpellEffect(Phase.Main_Phase1));

        GameMatController.selectedOwnCard = "Hand/Messenger of peace/1";
        ownSpell.setIsSetInThisTurn(true);
        assertEquals(0, GameMatController.activateSpellEffect(Phase.Main_Phase1));
    }

    @Test
    public void errorOfInvalidSelection() {
        assertFalse(GameMatController.errorOfInvalidSelection(6, "Monster"));
        assertTrue(GameMatController.errorOfInvalidSelection(1, "Monster"));
        assertFalse(GameMatController.errorOfInvalidSelection(0, "Monster"));
        assertFalse(GameMatController.errorOfInvalidSelection(6, "Spell"));
        assertTrue(GameMatController.errorOfInvalidSelection(1, "Spell"));
        assertFalse(GameMatController.errorOfInvalidSelection(14, "Hand"));
        assertTrue(GameMatController.errorOfInvalidSelection(1, "Hand"));
        assertTrue(GameMatController.errorOfInvalidSelection(1, "field"));
    }

    @Test
    public void summon() {

    }

    @Test
    public void summonInHand() {
        GameMatController.onlineUser = "me";
        GameMatController.rivalUser = "me2";
        GameMatModel.getGameMatByNickname("me").setPhase(Phase.Main_Phase1);
        new HandCardZone("me", "Battle OX");
        GameMatController.selectHandCard(6);
        System.out.println(GameMatController.selectedOwnCard);
        assertEquals(5, GameMatController.summonInHand(Player.getPlayerByName("me"), Phase.Main_Phase1));
    }

    @Test
    public void summonInHandSuccessfully() {
        GameMatController.rivalUser = "me2";
        new SpellTrapZoneCard("me", "Trap Hole", "H");
        new SpellTrapZoneCard("me", "Trap Hole", "H");
        new SpellTrapZoneCard("me", "Trap Hole", "H");
        new SpellTrapZoneCard("me", "Trap Hole", "H");
        new SpellTrapZoneCard("me", "Trap Hole", "H");
        new SpellTrapZoneCard("me", "Trap Hole", "H");
        MonsterZoneCard oo = new MonsterZoneCard("me", "Battle OX", "DO", false, true);
        GameMatController.summonInMonsterZoneSuccessfully(player1, oo);
    }

    @Test
    public void summonInMonsterZone() {
        GameMatController.onlineUser = "me2";
        GameMatController.rivalUser = "me";
        GameMatModel.getGameMatByNickname("me2").setPhase(Phase.Main_Phase1);
        MonsterZoneCard monsterZoneCard = new MonsterZoneCard("me2", "Battle OX", "DH", false, false);
        new MonsterZoneCard("me2", "Battle OX", "OO", false, false);
        GameMatController.selectMonsterCard(2, true);
        assertEquals(1, GameMatController.summonInMonsterZone(Player.getPlayerByName("me"), Phase.Main_Phase1));
        monsterZoneCard.setHaveChangedPositionThisTurn(false);
        GameMatController.selectMonsterCard(1, true);
        assertEquals(0, GameMatController.summonInMonsterZone(Player.getPlayerByName("me"), Phase.Main_Phase1));
    }

    @Test
    public void summonInMonsterZoneSuccessfully() {
    }

    @Test
    public void changeMonsterPosition() {
        MonsterZoneCard mm = new MonsterZoneCard("me", "Battle OX", "OO", false, true);
        assertEquals(1, GameMatController.changeMonsterPosition("set --position attack", Phase.Battle_Phase));
        GameMatController.selectedOwnCard = "Modnster/Battle OX/1";
        assertEquals(1, GameMatController.changeMonsterPosition("set --position attack", Phase.Battle_Phase));
        GameMatController.selectedOwnCard = "Monster/Battle OX/1";
        assertEquals(1, GameMatController.changeMonsterPosition("set --position attack", Phase.Battle_Phase));
        GameMatController.selectedOwnCard = "Monster/Battle OX/1";
        assertEquals(1, GameMatController.changeMonsterPosition("set --position attack", Phase.Main_Phase1));
        mm.setMode("DO");
        mm.setHaveChangedPositionThisTurn(true);
        assertEquals(1, GameMatController.changeMonsterPosition("set --position attack", Phase.Main_Phase1));
        mm.setHaveChangedPositionThisTurn(false);
        assertEquals(1, GameMatController.changeMonsterPosition("set --position attack", Phase.Main_Phase1));
    }

    @Test
    public void set() {
        new HandCardZone("me", "Battle OX");
        assertEquals(5, GameMatController.set(Phase.Main_Phase1));////////////

        GameMatController.selectedOwnCard = "Modnster/Battle OX/1";
        assertEquals(2, GameMatController.set(Phase.Main_Phase1));

        GameMatController.selectedOwnCard = "Hand/Battle OX/1";
        assertEquals(3, GameMatController.set(Phase.Draw_Phase));

        GameMatController.selectedOwnCard = "Hand/Battle OX/1";
        Player.getPlayerByName("me").setCanSetSummonMonster(true);
        assertEquals(0, GameMatController.set(Phase.Main_Phase1));

        GameMatController.selectedOwnCard = "Hand/Trap Hole/1";
        assertEquals(0, GameMatController.set(Phase.Main_Phase1));
    }

    @Test
    public void addToMonsterZoneCard() {
//        assertEquals(1,GameMatController.addToMonsterZoneCard("Battle OX","OO"));
//        assertEquals(0,GameMatController.addToMonsterZoneCard("Scanner","OO"));
    }

    @Test
    public void addToSpellTrapZoneCard() {
        SpellTrapZoneCard trap = new SpellTrapZoneCard("me", "Trap Hole", "O");
        Assert.assertEquals(trap, SpellTrapZoneCard.getSpellCardByAddress(1, "me"));
        assertEquals(0, GameMatController.addToSpellTrapZoneCard("Yami", "O"));
    }

    @Test
    public void errorOfWrongPhase() {
    }

    @Test
    public void errorOfFullZone() {
        assertTrue(GameMatController.errorOfFullZone("Monster"));
        assertTrue(GameMatController.errorOfFullZone("Spell"));
        assertTrue(GameMatController.errorOfFullZone("Trap"));
    }

    @Test
    public void specialSummon() {
    }

    @Test
    public void ritualSummon() {
        assertEquals(-1, GameMatController.ritualSummon());
        new HandCardZone("me", "Skull Guardian");
        new HandCardZone("me2", "Skull Guardian");
        assertEquals(-1, GameMatController.ritualSummon());
        new MonsterZoneCard("me", "Skull Guardian", "OO", false, false);
        System.setIn(new ByteArrayInputStream("cancel".getBytes()));
        assertEquals(-1, GameMatController.ritualSummon());
    }

    @Test
    public void tributeMonster() {
        System.setIn(new ByteArrayInputStream("cancel".getBytes()));
        assertEquals(0, GameMatController.tributeMonster(-1, "Ritual"));
        assertEquals(1, GameMatController.tributeMonster(-1, "Normal"));
    }

    @Test
    public void flipSummon() {
        GameMatController.rivalUser = "me2";
        GameMatModel.getGameMatByNickname(GameMatController.onlineUser).setPhase(Phase.Main_Phase1);
        assertEquals(1, GameMatController.flipSummon(Phase.Main_Phase1));
        MonsterZoneCard monsterZoneCard = new MonsterZoneCard(GameMatController.onlineUser, "Battle OX", "DH", false, false);
        monsterZoneCard.setHaveChangedPositionThisTurn(false);
        GameMatController.selectMonsterCard(1, true);
        assertEquals(4, GameMatController.flipSummon(Phase.Main_Phase1));
    }

    @Test
    public void attack() {
        GameMatController.rivalUser = "me2";
        MonsterZoneCard rival = new MonsterZoneCard("me2", "Yomi Ship", "OO", false, true);
        new MonsterZoneCard("me2", "Battle OX", "OO", false, true);
        assertEquals(1, GameMatController.attack("attack 1", Phase.Main_Phase1));
        GameMatController.selectedOwnCard = "Hand/Magic Cylinder/1";
        assertEquals(1, GameMatController.attack("attack 1", Phase.Main_Phase1));
        GameMatController.selectedOwnCard = "Monster/Battle OX/1";
        assertEquals(1, GameMatController.attack("attack 1", Phase.Draw_Phase));
        MonsterZoneCard own = new MonsterZoneCard("me", "Battle OX", "DH", false, true);
        GameMatController.selectedOwnCard = "Monster/Battle OX/1";
        assertEquals(1, GameMatController.attack("attack 1", Phase.Battle_Phase));
        GameMatController.selectedOwnCard = "Monster/Battle OX/1";
        own.setHaveAttackThisTurn(true);
        own.setMode("OO");
        assertEquals(1, GameMatController.attack("attack 1", Phase.Battle_Phase));
        GameMatController.selectedOwnCard = "Monster/Battle OX/1";
        own.setCanAttack(false);
        own.setHaveAttackThisTurn(false);
        assertEquals(1, GameMatController.attack("attack 1", Phase.Battle_Phase));
        GameMatController.selectedOwnCard = "Monster/Battle OX/1";
        own.setCanAttack(true);
        assertEquals(1, GameMatController.attack("attack 1", Phase.Battle_Phase));
        MonsterZoneCard bb = new MonsterZoneCard("me2", "Exploder Dragon", "OO", false, true);
        bb.setCanAttackToThisMonster(false);
        assertEquals(1, GameMatController.attack("attack 1", Phase.Battle_Phase));
        bb.setCanAttackToThisMonster(true);
        assertEquals(1, GameMatController.attack("attack 1", Phase.Battle_Phase));
        assertEquals(1, GameMatController.attack("attack 2", Phase.Battle_Phase));
        assertEquals(1, GameMatController.attack("attack 2", Phase.Battle_Phase));
        bb.setMode("DH");
        assertEquals(0, GameMatController.attack("attacik 2", Phase.Battle_Phase));
    }

    @Test
    public void attackDirect() {
        GameMatController.rivalUser = "me2";
        MonsterZoneCard ownMonster = new MonsterZoneCard("me", "Battle OX", "DH", false, true);
        assertEquals(1, GameMatController.attackDirect(Phase.Draw_Phase));
        GameMatController.selectedOwnCard = "Hand/Magic Cylinder/1";
        assertEquals(2, GameMatController.attackDirect(Phase.Draw_Phase));
        GameMatController.selectedOwnCard = "Monster/Battle OX/1";
        assertEquals(3, GameMatController.attackDirect(Phase.Draw_Phase));
        ownMonster.setHaveAttackThisTurn(true);
        GameMatController.selectedOwnCard = "Monster/Battle OX/1";
        assertEquals(0, GameMatController.attackDirect(Phase.Battle_Phase));
        ownMonster.setHaveAttackThisTurn(false);
        GameMatController.selectedOwnCard = "Monster/Battle OX/1";
        ownMonster.setCanAttack(false);
        assertEquals(0, GameMatController.attackDirect(Phase.Battle_Phase));
        ownMonster.setHaveAttackThisTurn(false);
        GameMatController.selectedOwnCard = "Monster/Battle OX/1";
        ownMonster.setCanAttack(true);
        assertEquals(0, GameMatController.attackDirect(Phase.Battle_Phase));
        GameMatController.selectedOwnCard = "Monster/Battle OX/1";
        Player.getPlayerByName("me2").changeLifePoint(-100000);
        UserModel.getUserByUsername("Guy").setUserScore(1);
        MainMenuController.username2 = "Guy2";
        System.setIn(new ByteArrayInputStream("menu  exit".getBytes()));
        assertEquals(0, GameMatController.attackDirect(Phase.Battle_Phase));
    }

    @Test
    public void checkForSetTrapToActivateInRivalTurn() {
    }

    @Test
    public void checkForQuickSpellInRivalTurn() {
    }

    @Test
    public void activateTrapEffect() {
        GameMatController.rivalUser = "me2";
        MonsterZoneCard mm = new MonsterZoneCard("me2", "Battle OX", "DO", false, true);
        MonsterZoneCard mm2 = new MonsterZoneCard("me", "Battle OX", "DO", false, true);

        new SpellTrapZoneCard("me2", "Magic Cylinder", "O");
        new SpellTrapZoneCard("me2", "Mirror Force", "O");
        new SpellTrapZoneCard("me2", "Negate Attack", "O");
        new SpellTrapZoneCard("me2", "Torrential Tribute", "O");

        new SpellTrapZoneCard("me", "Magic Cylinder", "O");
        new SpellTrapZoneCard("me", "Mirror Force", "O");
        new SpellTrapZoneCard("me", "Negate Attack", "O");
        new SpellTrapZoneCard("me", "Torrential Tribute", "O");

        GameMatController.selectedRivalCard = "Hand/Magic Cylinder/1";
        assertEquals(1, GameMatController.activateTrapEffect(false, mm));

        GameMatController.selectedRivalCard = "Hand/Mirror Force/2";
        assertEquals(1, GameMatController.activateTrapEffect(false, mm));

        GameMatController.selectedRivalCard = "Hand/Negate Attack/3";
        assertEquals(1, GameMatController.activateTrapEffect(false, mm));

        GameMatController.selectedRivalCard = "Hand/Torrential Tributev/4";
        assertEquals(0, GameMatController.activateTrapEffect(false, mm));

        new SpellTrapZoneCard("me2", "Time Seal", "O");
        GameMatController.selectedRivalCard = "Hand/Time Seal/1";
        assertEquals(0, GameMatController.activateTrapEffect(false, mm));

        GameMatController.selectedOwnCard = "Hand/Magic Cylinder/1";
        assertEquals(1, GameMatController.activateTrapEffect(true, mm2));

        GameMatController.selectedOwnCard = "Hand/Mirror Force/2";
        assertEquals(1, GameMatController.activateTrapEffect(true, mm2));

        GameMatController.selectedOwnCard = "Hand/Negate Attack/3";
        assertEquals(1, GameMatController.activateTrapEffect(true, mm2));

        GameMatController.selectedOwnCard = "Hand/Torrential Tributev/4";
        assertEquals(0, GameMatController.activateTrapEffect(true, mm2));

        new SpellTrapZoneCard("me", "Time Seal", "O");
        GameMatController.selectedOwnCard = "Hand/Time Seal/1";
        assertEquals(0, GameMatController.activateTrapEffect(true, mm2));
    }


    @Test
    public void changePhase() {
    }

    @Test
    public void getAddressOfTributeMonster() {
        System.setIn(new ByteArrayInputStream("cancel".getBytes()));
        assertEquals(1, GameMatController.getAddressOfTributeMonster(1).size());
        new MonsterZoneCard(GameMatController.onlineUser, "Battle OX", "OO", false, false);
        System.setIn(new ByteArrayInputStream("1".getBytes()));
        assertEquals(1, GameMatController.getAddressOfTributeMonster(1).size());
    }


    @Test
    public void changeTurn() {
        GameMatController.onlineUser = "me";
        GameMatController.rivalUser = "me2";
        GameMatController.changeTurn();
        assertEquals("me2", GameMatController.onlineUser);
    }

    @Test
    public void endGame() {
    }


    @Test
    public void showGameBoard() {
    }

    @Test
    public void showSelectedCard() {
        GameMatController.onlineUser = "me";
        GameMatController.rivalUser = "me2";
        MonsterZoneCard.allMonsterCards.get("me").clear();
        MonsterZoneCard.allMonsterCards.get("me2").clear();
        HandCardZone.allHandCards.get("me").clear();
        HandCardZone.allHandCards.get("me2").clear();
        new MonsterZoneCard("me", "Battle OX", "OO", false, false);
        new MonsterZoneCard("me", "Battle OX", "OO", false, false);
        new MonsterZoneCard("me2", "Battle OX", "OO", false, false);
        new MonsterZoneCard("me2", "Battle OX", "OO", false, false);
        new HandCardZone("me", "Battle OX");
        GameMatController.selectMonsterCard(1, false);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        GameMatController.showSelectedCard();
        assertEquals(158, outContent.size());
        outContent.reset();
        GameMatController.selectHandCard(1);
        GameMatController.showSelectedCard();
        assertEquals(173, outContent.size());
    }

    @Test
    public void backCommand() {
    }

    @Test
    public void checkForSpellAbsorption() {
        GameMatController.rivalUser = "me2";
        assertEquals(1, GameMatController.checkForSpellAbsorption());
    }

    @Test
    public void checkForMessengerOfPeace() {
        GameMatController.rivalUser = "me2";
        String input = "yes1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        System.setIn(new ByteArrayInputStream("no".getBytes()));
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        ByteArrayOutputStream show = new ByteArrayOutputStream();
        System.setOut(new PrintStream(show));
        assertNotEquals("Do you want to destroy Messenger of peace? (yes/no)", show.toString().substring(0, show.toString().length()));
        assertEquals(1, GameMatController.checkForMessengerOfPeace());
    }

    @Test
    public void checkForSupplySquad() {
        GameMatController.rivalUser = "me2";
        assertEquals(1, GameMatController.checkForSupplySquad());
    }

    @Test
    public void chooseSpellEffectController() {
        GameMatController.rivalUser = "me2";
        SpellTrapZoneCard card = new SpellTrapZoneCard("me", "Monster Reborn", "H");
        assertEquals(1, GameMatController.chooseSpellEffectController("Quick-play", card));
        assertEquals(1, GameMatController.chooseSpellEffectController("Norimal", card));
    }

    @Test
    public void getAddressOfRelatedMonster() {
        System.setIn(new ByteArrayInputStream("cancel".getBytes()));
        SpellTrapZoneCard card = new SpellTrapZoneCard("me", "Monster Reborn", "H");
        assertEquals(0, GameMatController.getAddressOfRelatedMonster(card));
    }

    @Test
    public void getResponseForEquipSpell() {
        System.setIn(new ByteArrayInputStream("cancel".getBytes()));
        assertEquals(0, GameMatController.getResponseForEquipSpell("own", "Monster Reborn"));
    }


    @Test
    public void isAnyMonsterInGraveyard() {
        assertFalse(GameMatModel.getGameMatByNickname("me").isAnyMonsterInGraveyard());
        assertFalse(GameMatModel.getGameMatByNickname("me2").isAnyMonsterInGraveyard());
        GameMatModel.getGameMatByNickname("me").addToGraveyard("Battle OX");
        assertEquals(true, GameMatModel.getGameMatByNickname("me").isAnyMonsterInGraveyard());
        assertEquals("Monster", GameMatModel.getGameMatByNickname("me").getKindOfDeadCardByAddress(0));
        assertEquals(1, GameMatModel.getGameMatByNickname("me").getNumberOfDeadCards());
        GameMatModel.getGameMatByNickname("me").removeFromGraveyardByAddress(0);
        assertEquals(false, GameMatModel.getGameMatByNickname("me").isAnyMonsterInGraveyard());

    }

    @Test
    public void exchangeCard() {
        assertEquals(0, GameMatController.exchangeCard("me", "asdsd"));
    }

    @Test
    public void changeTurnINPlayer() {
        Player.getPlayerByName("me").changeTurn();
        assertEquals("me2", GameMatController.rivalUser);
    }

}