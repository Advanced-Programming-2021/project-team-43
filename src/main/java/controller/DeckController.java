package controller;



import view.DeckView;
import view.MainMenuView;
import model.*;


import java.util.Arrays;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeckController {
    public static void findMatcher() {
        while (true) {
            String command = DeckView.getCommand();
            Pattern pattern = Pattern.compile("^deck create (.+?)$");
            Matcher matcher = pattern.matcher(command);
            if (matcher.find()) {
                createDeck(matcher);
                continue;
            }
            pattern = Pattern.compile("^deck delete (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                deleteDeck(matcher);
                continue;
            }

            pattern = Pattern.compile("^deck set-activate (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                setActivate(matcher.group(1));
                continue;
            }


            pattern = Pattern.compile("^deck add-card --card (.+?) --deck (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                addCardToMainDeck(matcher.group(1), matcher.group(2));
                continue;
            }
            pattern = Pattern.compile("^deck add-card --deck (.+?) --card (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                addCardToMainDeck(matcher.group(2), matcher.group(1));
                continue;
            }


            pattern = Pattern.compile("^deck add-card --card (.+?) --deck (.+?) --side$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                addCardToSideDeck(matcher.group(1), matcher.group(2));
                continue;
            }

            pattern = Pattern.compile("^deck add-card --card (.+?) --side --deck (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                addCardToSideDeck(matcher.group(1), matcher.group(2));
                continue;
            }
            pattern = Pattern.compile("^deck add-card --side --card (.+?) --deck (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                addCardToSideDeck(matcher.group(1), matcher.group(2));
                continue;
            }
            pattern = Pattern.compile("^deck add-card --side --deck (.+?) --card (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                addCardToMainDeck(matcher.group(2), matcher.group(1));
                continue;
            }
            pattern = Pattern.compile("^deck add-card --deck (.+?) --side --card (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                addCardToMainDeck(matcher.group(2), matcher.group(1));
                continue;
            }
            pattern = Pattern.compile("^deck add-card --deck (.+?)--card (.+?) --side$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                addCardToMainDeck(matcher.group(2), matcher.group(1));
                continue;
            }


            pattern = Pattern.compile("^deck rm-card --card (.+?) --deck (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                removeCardFromMainDeck(matcher.group(1), matcher.group(2));
                continue;
            }
            pattern = Pattern.compile("^deck rm-card --deck (.+?) --card (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                removeCardFromMainDeck(matcher.group(2), matcher.group(1));
                continue;
            }


            pattern = Pattern.compile("^deck rm-card --card (.+?) --deck (.+?) --side$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                removeCardFromSideDeck(matcher.group(1), matcher.group(2));
                continue;
            }

            pattern = Pattern.compile("^deck rm-card --card (.+?) --side --deck (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                removeCardFromSideDeck(matcher.group(1), matcher.group(2));
                continue;
            }
            pattern = Pattern.compile("^deck rm-card --side --card (.+?) --deck (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                removeCardFromSideDeck(matcher.group(1), matcher.group(2));
                continue;
            }
            pattern = Pattern.compile("^deck rm-card --side --deck (.+?) --card (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                removeCardFromSideDeck(matcher.group(2), matcher.group(1));
                continue;
            }
            pattern = Pattern.compile("^deck rm-card --deck (.+?) --side --card (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                removeCardFromSideDeck(matcher.group(2), matcher.group(1));
                continue;
            }
            pattern = Pattern.compile("^deck rm-card --deck (.+?)--card (.+?) --side$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                removeCardFromSideDeck(matcher.group(2), matcher.group(1));
                continue;
            }
            pattern = Pattern.compile("^deck show --all$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                showAllDeck();
                continue;
            }
            pattern = Pattern.compile("^deck show --deck-name (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                showMainDeck(matcher.group(1));
                continue;
            }




            pattern = Pattern.compile("^deck show --side --deck-name (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                showSideDeck(matcher.group(1));
                continue;
            }
            pattern = Pattern.compile("^deck show --deck-name (.+?) --side$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                showSideDeck(matcher.group(1));
                continue;
            }


             pattern = Pattern.compile("^menu exit$");
             matcher = pattern.matcher(command);
            if (matcher.find()) {
                findMatcher();
                break;
            }

            pattern = Pattern.compile("^menu enter (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                if (matcher.group(1).equals("duel") || matcher.group(1).equals("deck") || matcher.group(1).equals("profile") || matcher.group(1).equals("shop") || matcher.group(1).equals("scoreboard")) {
                    DeckView.showInput("menu navigation is not possible");
                } else {
                    MainMenuView.showInput("invalid command");
                }
                continue;
            }
            DeckView.showInput("invalid command");




        }
    }


    private static void setActivate(String deckName) {
        UserModel user = UserModel.getUserByUsername(MainMenuController.username);
        if (user.isUserHaveThisDeck(deckName)) {
            user.setActiveDeck(deckName);
            UserModel.allUsersInfo.replace(MainMenuController.username, user);
            DeckView.showInput("deck activated successfully");
        } else {
            DeckView.showInput("deck with name " + deckName + " does not exist");
        }

    }

    private static void deleteDeck(Matcher matcher) {
        UserModel user = UserModel.getUserByUsername(MainMenuController.username);
        if (user.isUserHaveThisDeck(matcher.group(1))) {
            user.deleteDeck(matcher.group(1));
            UserModel.allUsersInfo.replace(MainMenuController.username, user);
            DeckView.showInput("deck deleted successfully");
        } else {
            DeckView.showInput("deck with name " + matcher.group(1) + " does not exist");

        }
    }

    private static void createDeck(Matcher matcher) {
        if (UserModel.getUserByUsername(MainMenuController.username).isUserHaveThisDeck(matcher.group(1))) {
            DeckView.showInput("deck with name " + matcher.group(1) + " already exists");
        } else {
            UserModel user = UserModel.getUserByUsername(MainMenuController.username);
            user.addDeck(new DeckModel(matcher.group(1)));
            UserModel.allUsersInfo.replace(MainMenuController.username, user);
            DeckView.showInput("deck created successfully!");

        }
    }

    private static void addCardToMainDeck(String cardName, String deckName) {
        if (UserModel.getUserByUsername(MainMenuController.username).isUserHaveThisDeck(deckName)) {
            if (UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                    UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) <
                    UserModel.getUserByUsername(MainMenuController.username).userAllCards.get(cardName)) {
                if (UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(deckName).getMainAllCardNumber() < 60) {

                    if (UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                            UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) < 3) {

                        UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(deckName).addCardToMain(cardName);
                    } else {
                        DeckView.showInput("there are already three cards with name " + cardName + " in deck " + deckName);
                    }


                } else {
                    DeckView.showInput("main deck is full");
                }


            } else {
                DeckView.showInput("card with name " + cardName + " does not exist");
            }

        } else {
            DeckView.showInput("deck with name " + deckName + " does not exist");
        }
    }


    private static void addCardToSideDeck(String cardName, String deckName) {
        if (UserModel.getUserByUsername(MainMenuController.username).isUserHaveThisDeck(deckName)) {
            if (UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                    UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) <
                    UserModel.getUserByUsername(MainMenuController.username).userAllCards.get(cardName)) {
                if (UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(deckName).getSideAllCardNumber() < 15) {

                    if (UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                            UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) < 3) {

                        UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(deckName).addCardToSide(cardName);
                    } else {
                        DeckView.showInput("there are already three cards with name " + cardName + " in deck " + deckName);
                    }


                } else {
                    DeckView.showInput("side deck is full");
                }


            } else {
                DeckView.showInput("card with name " + cardName + " does not exist");
            }

        } else {
            DeckView.showInput("deck with name " + deckName + " does not exist");
        }
    }

    private static void removeCardFromMainDeck(String cardName, String deckName) {
        UserModel user = UserModel.getUserByUsername(MainMenuController.username);

        if (user.isUserHaveThisDeck(deckName)) {
            if (user.userAllDecks.get(deckName).isMainDeckHaveThisCard(cardName)) {
                user.userAllDecks.get(deckName).removeCardFromMain(cardName);
                DeckView.showInput("card removed form deck successfully");
            } else {
                DeckView.showInput("card with name " + cardName + " does not exist in main deck");

            }
        } else {
            DeckView.showInput("deck with name " + deckName + " does not exist");
        }

    }


    private static void removeCardFromSideDeck(String cardName, String deckName) {
        UserModel user = UserModel.getUserByUsername(MainMenuController.username);

        if (user.isUserHaveThisDeck(deckName)) {
            if (user.userAllDecks.get(deckName).isSideDeckHaveThisCard(cardName)) {
                user.userAllDecks.get(deckName).removeCardFromSide(cardName);
                DeckView.showInput("card removed form deck successfully");
            } else {
                DeckView.showInput("card with name " + cardName + " does not exist in side deck");

            }
        } else {
            DeckView.showInput("deck with name " + deckName + " does not exist");
        }

    }

    private static void showAllDeck() {
        UserModel user = UserModel.getUserByUsername(MainMenuController.username);
        String[] keys;
        keys = UserModel.getUserByUsername(MainMenuController.username).userAllDecks.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        DeckView.showInput("Decks:");
        DeckView.showInput("Active deck:");
        if (!user.getActiveDeck().equals("")) {
            DeckModel deck = UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(user.getActiveDeck());
            DeckView.showInput(user.getActiveDeck() + ": main deck " + deck.getMainAllCardNumber() + ", side deck " + deck.getSideAllCardNumber() + " " + deck.validOrInvalid());
        }
        DeckView.showInput("Other decks:");
        for (String key : keys) {
            DeckModel deck = UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(key);
            DeckView.showInput(user.getActiveDeck() + ": main deck " + deck.getMainAllCardNumber() + ", side deck " + deck.getSideAllCardNumber() + " " + deck.validOrInvalid());
        }


    }


    private static void showMainDeck(String deckName) {
        if (UserModel.getUserByUsername(MainMenuController.username).isUserHaveThisDeck(deckName)) {

            DeckModel deck = UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(deckName);

            DeckView.showInput("Deck: " + deckName);
            DeckView.showInput("Main deck:");
            DeckView.showInput("Monsters:");
            String[] cardNames;
            cardNames=deck.cardsInMainDeck.keySet().toArray(new String[0]);
            Arrays.sort(cardNames);
            for (String cardName : cardNames){
                if (!Card.getCardsByName(cardName).getCardModel().equals("Monster")){continue;}
                DeckView.showInput(cardName+": "+ Card.getCardsByName(cardName).getDescription()+" ("+deck.cardsInMainDeck.get(cardName)+")");
            }
            DeckView.showInput("Spell and Traps:");
            for (String cardName : cardNames){
                if (Card.getCardsByName(cardName).getCardModel().equals("Monster")){continue;}
                DeckView.showInput(cardName+": "+ Card.getCardsByName(cardName).getDescription()+" ("+deck.cardsInMainDeck.get(cardName)+")");
            }



        } else {
            DeckView.showInput("deck with name " + deckName + " does not exist");
        }

    }


    private static void showSideDeck(String deckName) {
        if (UserModel.getUserByUsername(MainMenuController.username).isUserHaveThisDeck(deckName)) {

            DeckModel deck = UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(deckName);

            DeckView.showInput("Deck: " + deckName);
            DeckView.showInput("Side deck:");
            DeckView.showInput("Monsters:");
            String[] cardNames;
            cardNames=deck.cardsInSideDeck.keySet().toArray(new String[0]);
            Arrays.sort(cardNames);
            for (String cardName : cardNames){
                if (!Card.getCardsByName(cardName).getCardModel().equals("Monster")){continue;}
                DeckView.showInput(cardName+": "+ Card.getCardsByName(cardName).getDescription()+" ("+deck.cardsInSideDeck.get(cardName)+")");
            }
            DeckView.showInput("Spell and Traps:");
            for (String cardName : cardNames){
                if (Card.getCardsByName(cardName).getCardModel().equals("Monster")){continue;}
                DeckView.showInput(cardName+": "+ Card.getCardsByName(cardName).getDescription()+" ("+deck.cardsInSideDeck.get(cardName)+")");
            }



        } else {
            DeckView.showInput("deck with name " + deckName + " does not exist");
        }

    }
}