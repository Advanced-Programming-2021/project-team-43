package controller;

import model.MonsterCard;
import model.SpellCard;
import model.TrapCard;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class SetCardsTest {

    @Test
    public void readingCSVFileMonster() {
        HashMap<String, MonsterCard> monstersBeforeAdd = MonsterCard.getAllMonsters();
        int beforeAddSize = monstersBeforeAdd.size();
        SetCards.readingCSVFileMonster();
        HashMap<String, MonsterCard> monstersAfterAdd = MonsterCard.getAllMonsters();
        int afterAddSize = monstersAfterAdd.size();
        assertNotEquals(beforeAddSize, afterAddSize);
    }

    @Test
    public void readingCSVFileTrapSpell() {
        HashMap<String, SpellCard> spellsBeforeAdd = SpellCard.getSpellCards();
        int beforeAddSpellsSize = spellsBeforeAdd.size();
        HashMap<String, TrapCard>  trapsBeforeAdd = TrapCard.getTrapCards();
        int beforeAddTrapSize = trapsBeforeAdd.size();
        SetCards.readingCSVFileTrapSpell();
        HashMap<String, SpellCard> spellsAfterAdd = SpellCard.getSpellCards();
        int afterAddSpellsSize = spellsAfterAdd.size();
        HashMap<String, TrapCard>  trapsAfterAdd = TrapCard.getTrapCards();
        int afterAddTrapSize = trapsAfterAdd.size();
        int beforeAddSize=beforeAddSpellsSize+ beforeAddTrapSize;
        int afterAddSize= afterAddSpellsSize+afterAddTrapSize;
        assertNotEquals(beforeAddSize, afterAddSize);
    }
}