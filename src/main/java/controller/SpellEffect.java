package main.java.controller;

public class SpellEffect {
public static void spellEffectController(String spellCardName){
    if(spellCardName.equals("Monster Reborn")){
        monsterReborn();
    }
    if(spellCardName.equals("Terraforming")){
    terraforming();
    }
    if(spellCardName.equals("Pot of Greed")){
       potOfGreed();
    }

    if(spellCardName.equals("Raigeki")){
        raigeki();
    }
    if(spellCardName.equals("Change of Heart")){
      changeOfHeart();
    }
    if(spellCardName.equals("Harpie’s Feather Duster")){
      harpieFeatherDuster();
    }

    if(spellCardName.equals("Swords of Revealing Light")){
      swordsOfRevealingLight();
    }
    if(spellCardName.equals("Dark Hole")){
        darkHole();
    }
    if(spellCardName.equals("Spell Absorption")){
        spellAbsorption();
    }
    if(spellCardName.equals("Messenger of peace")){
       messengerOfPeace();
    }
    if(spellCardName.equals("Twin Twisters")){
        twinTwisters();
    }

    if(spellCardName.equals("Mystical space typhoon")){
     mysticalSpaceTyphoon();
    }
    if(spellCardName.equals("Yami")){
        yami();
    }
    if(spellCardName.equals("Forest")){
      forest();
    }
    if(spellCardName.equals("Closed Forest")){
    closedForest();
    }


    if(spellCardName.equals("UMIIRUKA")){
     UMIIRUKA();
    }

    if(spellCardName.equals("Sword of Dark Destruction")){
    swordOfDarkDestruction();
    }

    if(spellCardName.equals("Black Pendant")){
     blackPendant();
    }

    if(spellCardName.equals("United We Stand")){
       unitedWeStand();
    }
    if(spellCardName.equals("Magnum Shield")){
    magnumShield();
    }

}
    public static void monsterReborn() {

    }

    public static void terraforming() {

    }

    public static void potOfGreed() {

    }

   public static void raigeki() {//6
       String[] keys = GameMatModel.getGameMatModelByNumber(2).getAllMonstersZone().keySet();
       for (String key : keys) {
           GameMatModel.getGameMatModelByNumber(2).deleteMonsterZone(key);
       }

   }


   public static void changeOfHeart() {

   }

   public static void harpieFeatherDuster() {//6
       String[] spell = GameMatModel.getGameMatModelByNumber(2).getAllSpellsZone().keySet();
       String[] trap = GameMatModel.getGameMatModelByNumber(2).getAllTrapZone().keySet();
       for (String key :spell) {
           GameMatModel.getGameMatModelByNumber(2).deleteSpellZone(key);
       }
       for (String key :trap) {
           GameMatModel.getGameMatModelByNumber(2).deleteTrapZone(key);
       }

   }

   public static void swordsOfRevealingLight() {//naghes 6
       String[] keys = GameMatModel.getGameMatModelByNumber(2).getAllMonstersZone().keySet();
       for (String key : keys) {
           GameMatModel.getGameMatModelByNumber(2).getMonsterByName(key).setBackAndForth("face up");//defend
       }
       if(  GameMatModel.getGameMatModelByNumber(1).getMonsterByName("Swords of Revealing Light").getBackAndForth().equals("face up"))
       {
           for (String key : keys) {
               GameMatModel.getGameMatModelByNumber(2).getMonsterByName(key).setCanAttack(false);
           }
       }
   }

    public static void darkHole() {

    }


    public static void spellAbsorption() {

    }

    public static void messengerOfPeace() {

    }

    public static void twinTwisters() {

    }

    public static void mysticalSpaceTyphoon() {

    }

    public static void yami() {

    }

    public static void forest() {

    }

    public static void closedForest() {

    }

    public static void UMIIRUKA() {

    }

    public static void swordOfDarkDestruction() {

    }

    public static void blackPendant() {

    }

    public static void unitedWeStand() {

    }

    public static void magnumShield() {

    }


}
