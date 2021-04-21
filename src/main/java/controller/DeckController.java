package main.java.controller;

import main.java.model.DeckModel;
import main.java.model.UserModel;
import main.java.view.DeckView;

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
            pattern = Pattern.compile("^deck add-card --deck (.+?) --card (.+?) --side$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                addCardToMainDeck(matcher.group(2), matcher.group(1));
                continue;
            }






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
                            UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName)<3){

                        UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(deckName).addCardToMain(cardName);
                    }
                    else{
                        DeckView.showInput("there are already three cards with name "+cardName+" in deck "+deckName);
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
                            UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName)<3){

                        UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(deckName).addCardToSide(cardName);
                    }
                    else{
                        DeckView.showInput("there are already three cards with name "+cardName+" in deck "+deckName);
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

}