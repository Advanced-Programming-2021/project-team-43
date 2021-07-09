package controller;
import view.*;
import model.*;

import java.io.IOException;
import java.util.*;
import java.util.regex.*;


public class DeckController {


    public static void setActivate(String deckName) {
        UserModel user = UserModel.getUserByUsername(MainMenuController.username);
        if (user.isUserHaveThisDeck(deckName)) {
            user.setActiveDeck(deckName);
            UserModel.allUsersInfo.replace(MainMenuController.username, user);
            Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
        }
    }

    public static void deleteDeck(String deckName) {
        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("D "+MainMenuController.token+"deck delete "+deckName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String createDeck(String deckName) {
//        if (UserModel.getUserByUsername(MainMenuController.username).isUserHaveThisDeck(deckName)) {
//            return "deck with name " + deckName + " already exists";
//        } else {
//            UserModel user = UserModel.getUserByUsername(MainMenuController.username);
//            user.addDeck(new DeckModel(deckName));
//            UserModel.allUsersInfo.replace(MainMenuController.username, user);
//            Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
//            return "Deck created successfully";
//        }

        try {
            RegisterAndLoginView.dataOutputStream.writeUTF("D "+MainMenuController.token+"deck create "+deckName);

            return RegisterAndLoginView.dataInputStream.readUTF();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String addCardToMainDeck(String cardName, String deckName) {
        UserModel user = UserModel.getUserByUsername(MainMenuController.username);
        if (UserModel.getUserByUsername(MainMenuController.username).isUserHaveThisDeck(deckName)) {
            if (user.isUserHaveCard(cardName) && user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                    user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) < user.userAllCards.get(cardName)) {
                if (user.userAllDecks.get(deckName).getMainAllCardNumber() < 60 && user.isUserHaveCard(cardName)) {
                    if (Card.getCardsByName(cardName).getCardModel().equals("Monster")) {
                        if (user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                                user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) < 3) {
                            DeckModel deckModel = user.userAllDecks.get(deckName);
                            deckModel.addCardToMain(cardName);
                            user.userAllDecks.replace(deckName, deckModel);
                            UserModel.allUsersInfo.replace(MainMenuController.username, user);
                            Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                            return ("card added to deck successfully");

                        } else {
                            return ("there are already three cards with name " + cardName + " in deck " + deckName);
                        }
                    } else if (Card.getCardsByName(cardName).getCardModel().equals("Spell")) {
                        if (SpellCard.getSpellCardByName(cardName).getStatus().equals("Unlimited")) {
                            if (user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                                    user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) < 3) {
                                DeckModel deckModel = user.userAllDecks.get(deckName);
                                deckModel.addCardToMain(cardName);
                                user.userAllDecks.replace(deckName, deckModel);
                                UserModel.allUsersInfo.replace(MainMenuController.username, user);
                                Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                                return ("card added to deck successfully");

                            } else {
                                return ("there are already three cards with name " + cardName + " in deck " + deckName);
                            }
                        } else if (SpellCard.getSpellCardByName(cardName).getStatus().equals("Limited")) {
                            if (user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                                    user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) < 1) {
                                DeckModel deckModel = user.userAllDecks.get(deckName);
                                deckModel.addCardToMain(cardName);
                                user.userAllDecks.replace(deckName, deckModel);
                                UserModel.allUsersInfo.replace(MainMenuController.username, user);
                                Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                                return ("card added to deck successfully");

                            } else {
                                return ("there are already one cards with name " + cardName + " in deck " + deckName);
                            }
                        } else {
                            if (user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                                    user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) < 2) {
                                DeckModel deckModel = user.userAllDecks.get(deckName);
                                deckModel.addCardToMain(cardName);
                                user.userAllDecks.replace(deckName, deckModel);
                                UserModel.allUsersInfo.replace(MainMenuController.username, user);
                                Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                                return ("card added to deck successfully");


                            } else {
                                return ("there are already two cards with name " + cardName + " in deck " + deckName);
                            }
                        }

                    } else {
                        if (TrapCard.getTrapCardByName(cardName).getStatus().equals("Unlimited")) {
                            if (user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                                    user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) < 3) {
                                DeckModel deckModel = user.userAllDecks.get(deckName);
                                deckModel.addCardToMain(cardName);
                                user.userAllDecks.replace(deckName, deckModel);
                                UserModel.allUsersInfo.replace(MainMenuController.username, user);
                                Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                                return ("card added to deck successfully");

                            } else {
                                return ("there are already three cards with name " + cardName + " in deck " + deckName);
                            }
                        } else if (TrapCard.getTrapCardByName(cardName).getStatus().equals("Limited")) {
                            if (user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                                    user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) < 1) {
                                DeckModel deckModel = user.userAllDecks.get(deckName);
                                deckModel.addCardToMain(cardName);
                                user.userAllDecks.replace(deckName, deckModel);
                                UserModel.allUsersInfo.replace(MainMenuController.username, user);
                                Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                                return ("card added to deck successfully");

                            } else {
                                return ("there are already one cards with name " + cardName + " in deck " + deckName);
                            }
                        } else {
                            if (user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                                    user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) < 2) {
                                DeckModel deckModel = user.userAllDecks.get(deckName);
                                deckModel.addCardToMain(cardName);
                                user.userAllDecks.replace(deckName, deckModel);
                                UserModel.allUsersInfo.replace(MainMenuController.username, user);
                                Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                                return ("card added to deck successfully");

                            } else {
                                return ("there are already two cards with name " + cardName + " in deck " + deckName);
                            }
                        }
                    }
                } else {
                    return ("main deck is full");
                }
            } else {
                return ("card with name " + cardName + " does not exist");
            }
        } else {
            return ("deck with name " + deckName + " does not exist");
        }
    }

    public static String addCardToSideDeck(String cardName, String deckName) {
        UserModel user = UserModel.getUserByUsername(MainMenuController.username);
        if (UserModel.getUserByUsername(MainMenuController.username).isUserHaveThisDeck(deckName)) {

            if (user.isUserHaveCard(cardName) && user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                    user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) < user.userAllCards.get(cardName)) {
                if (user.userAllDecks.get(deckName).getSideAllCardNumber() < 15 && user.isUserHaveCard(cardName)) {
                    if (Card.getCardsByName(cardName).getCardModel().equals("Monster")) {
                        if (user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) + user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) < 3) {
                            DeckModel deckModel = user.userAllDecks.get(deckName);
                            deckModel.addCardToSide(cardName);
                            user.userAllDecks.replace(deckName, deckModel);
                            UserModel.allUsersInfo.replace(MainMenuController.username, user);
                            Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                            return ("card added to deck successfully");

                        } else {
                            return ("there are already three cards with name " + cardName + " in deck " + deckName);
                        }
                    } else if (Card.getCardsByName(cardName).getCardModel().equals("Spell")) {
                        if (SpellCard.getSpellCardByName(cardName).getStatus().equals("Unlimited")) {
                            if (user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                                    user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) < 3) {
                                DeckModel deckModel = user.userAllDecks.get(deckName);
                                deckModel.addCardToSide(cardName);
                                user.userAllDecks.replace(deckName, deckModel);
                                UserModel.allUsersInfo.replace(MainMenuController.username, user);
                                Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                                return ("card added to deck successfully");

                            } else {
                                return ("there are already three cards with name " + cardName + " in deck " + deckName);
                            }
                        } else if (SpellCard.getSpellCardByName(cardName).getStatus().equals("Limited")) {
                            if (user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                                    user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) < 1) {

                                DeckModel deckModel = user.userAllDecks.get(deckName);
                                deckModel.addCardToSide(cardName);

                                user.userAllDecks.replace(deckName, deckModel);
                                UserModel.allUsersInfo.replace(MainMenuController.username, user);
                                Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                                return ("card added to deck successfully");

                            } else {
                                return ("there are already one cards with name " + cardName + " in deck " + deckName);
                            }
                        } else {
                            if (user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                                    user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) < 2) {
                                DeckModel deckModel = user.userAllDecks.get(deckName);
                                deckModel.addCardToSide(cardName);
                                user.userAllDecks.replace(deckName, deckModel);
                                UserModel.allUsersInfo.replace(MainMenuController.username, user);
                                Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                                return ("card added to deck successfully");

                            } else {
                                return ("there are already two cards with name " + cardName + " in deck " + deckName);
                            }
                        }
                    } else {
                        if (TrapCard.getTrapCardByName(cardName).getStatus().equals("Unlimited")) {
                            if (user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                                    user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) < 3) {
                                DeckModel deckModel = user.userAllDecks.get(deckName);
                                deckModel.addCardToSide(cardName);
                                user.userAllDecks.replace(deckName, deckModel);
                                UserModel.allUsersInfo.replace(MainMenuController.username, user);
                                Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                                return ("card added to deck successfully");

                            } else {
                                return ("there are already three cards with name " + cardName + " in deck " + deckName);
                            }
                        } else if (TrapCard.getTrapCardByName(cardName).getStatus().equals("Limited")) {
                            if (user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                                    user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) < 1) {
                                DeckModel deckModel = user.userAllDecks.get(deckName);
                                deckModel.addCardToSide(cardName);
                                user.userAllDecks.replace(deckName, deckModel);
                                UserModel.allUsersInfo.replace(MainMenuController.username, user);
                                Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                                return ("card added to deck successfully");


                            } else {
                                return ("there are already one cards with name " + cardName + " in deck " + deckName);
                            }
                        } else {
                            if (user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                                    user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) < 2) {
                                DeckModel deckModel = user.userAllDecks.get(deckName);
                                deckModel.addCardToSide(cardName);
                                user.userAllDecks.replace(deckName, deckModel);
                                UserModel.allUsersInfo.replace(MainMenuController.username, user);
                                Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                                return ("card added to deck successfully");

                            } else {
                                return ("there are already two cards with name " + cardName + " in deck " + deckName);
                            }
                        }
                    }

                } else {
                    return ("side deck is full");
                }
            } else {
                return ("card with name " + cardName + " does not exist");
            }
        } else {
            return ("deck with name " + deckName + " does not exist");
        }
    }

    public static void removeCardFromMainDeck(String cardName, String deckName) {
        UserModel user = UserModel.getUserByUsername(MainMenuController.username);
        if (user.isUserHaveThisDeck(deckName)) {
            if (user.userAllDecks.get(deckName).isMainDeckHaveThisCard(cardName)) {
                DeckModel deckModel = user.userAllDecks.get(deckName);
                deckModel.removeCardFromMain(cardName);
                user.userAllDecks.replace(deckName, deckModel);
                UserModel.allUsersInfo.replace(MainMenuController.username, user);
                Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
               // DeckView.showInput("card removed form deck successfully");
            } else {
               // DeckView.showInput("card with name " + cardName + " does not exist in main deck");
            }
        } else {
            //DeckView.showInput("deck with name " + deckName + " does not exist");
        }
        UserModel.allUsersInfo.replace(MainMenuController.username, user);
        Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
    }

    public static void removeCardFromSideDeck(String cardName, String deckName) {
        UserModel user = UserModel.getUserByUsername(MainMenuController.username);
        if (user.isUserHaveThisDeck(deckName)) {
            if (user.userAllDecks.get(deckName).isSideDeckHaveThisCard(cardName)) {
                DeckModel deckModel = user.userAllDecks.get(deckName);
                deckModel.removeCardFromSide(cardName);
                user.userAllDecks.replace(deckName, deckModel);
                UserModel.allUsersInfo.replace(MainMenuController.username, user);
                Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
               // DeckView.showInput("card removed form deck successfully");
            } else {
             //   DeckView.showInput("card with name " + cardName + " does not exist in side deck");

            }
        } else {
           // DeckView.showInput("deck with name " + deckName + " does not exist");
        }
        UserModel.allUsersInfo.replace(MainMenuController.username, user);
        Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
    }

    public static void showAllDeck() {
        UserModel user = UserModel.getUserByUsername(MainMenuController.username);
        String[] keys;
        keys = UserModel.getUserByUsername(MainMenuController.username).userAllDecks.keySet().toArray(new String[0]);
        Arrays.sort(keys);
      //  DeckView.showInput("Decks:");
        //DeckView.showInput("Active deck:");
        if (!user.getActiveDeck().equals("")) {
            DeckModel deck = UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(user.getActiveDeck());
           // DeckView.showInput(user.getActiveDeck() + ": main deck " + deck.getMainAllCardNumber() + ", side deck " + deck.getSideAllCardNumber() + " " + deck.validOrInvalid());
        }
        //DeckView.showInput("Other decks:");
        for (String key : keys) {
            if (user.getActiveDeck().equals(key)) {
                continue;
            }
            DeckModel deck = UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(key);
           // DeckView.showInput(key + ": main deck " + deck.getMainAllCardNumber() + ", side deck " + deck.getSideAllCardNumber() + " " + deck.validOrInvalid());
        }

    }

    public static void showMainDeck(String deckName) {
        if (UserModel.getUserByUsername(MainMenuController.username).isUserHaveThisDeck(deckName)) {
            DeckModel deck = UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(deckName);
//            DeckView.showInput("Deck: " + deckName);
//            DeckView.showInput("Main deck:");
//            DeckView.showInput("Monsters:");
            String[] cardNames;
            cardNames = deck.cardsInMainDeck.keySet().toArray(new String[0]);
            Arrays.sort(cardNames);
            for (String cardName : cardNames) {
                if (!Card.getCardsByName(cardName).getCardModel().equals("Monster")) {
                    continue;
                }
                //DeckView.showInput(cardName + ": " + Card.getCardsByName(cardName).getDescription() + " (" + deck.cardsInMainDeck.get(cardName) + ")");
            }
           // DeckView.showInput("Spell and Traps:");
            for (String cardName : cardNames) {
                if (Card.getCardsByName(cardName).getCardModel().equals("Monster")) {
                    continue;
                }
               // DeckView.showInput(cardName + ": " + Card.getCardsByName(cardName).getDescription() + " (" + deck.cardsInMainDeck.get(cardName) + ")");
            }
        } else {
          //  DeckView.showInput("deck with name " + deckName + " does not exist");
        }

    }

    public static void showSideDeck(String deckName) {
        if (UserModel.getUserByUsername(MainMenuController.username).isUserHaveThisDeck(deckName)) {

            DeckModel deck = UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(deckName);

//            DeckView.showInput("Deck: " + deckName);
//            DeckView.showInput("Side deck:");
//            DeckView.showInput("Monsters:");
            String[] cardNames;
            cardNames = deck.cardsInSideDeck.keySet().toArray(new String[0]);
            Arrays.sort(cardNames);
            for (String cardName : cardNames) {
                if (!Card.getCardsByName(cardName).getCardModel().equals("Monster")) {
                    continue;
                }
               // DeckView.showInput(cardName + ": " + Card.getCardsByName(cardName).getDescription() + " (" + deck.cardsInSideDeck.get(cardName) + ")");
            }
          //  DeckView.showInput("Spell and Traps:");
            for (String cardName : cardNames) {
                if (Card.getCardsByName(cardName).getCardModel().equals("Monster")) {
                    continue;
                }
             //   DeckView.showInput(cardName + ": " + Card.getCardsByName(cardName).getDescription() + " (" + deck.cardsInSideDeck.get(cardName) + ")");
            }

        } else {
           // DeckView.showInput("deck with name " + deckName + " does not exist");
        }
    }

    public static void showCards() {
        HashMap<String, Integer> hashMap = UserModel.getUserByUsername(MainMenuController.username).userAllCards;
        String[] keys = hashMap.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        for (String key : keys) {
           // DeckView.showInput(key + ":" + Card.getCards().get(key).getDescription() + "  " + hashMap.get(key));
        }
    }

}