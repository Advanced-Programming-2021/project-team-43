package controller;

import model.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DeckController {

    public static String run(String input) {
        return findMatcher(input);
    }

    public static String findMatcher(String command) {
        Matcher matcher;
        if ((matcher = getMatcher(command, "^D (.+?)deck create (.+?)$")).find()) {
            return createDeck(matcher.group(2), matcher.group(1));
        }
        if ((matcher = getMatcher(command, "^D (.+?)deck rm-card --card (.+?) --deck (.+?) --side$")).find() || (matcher = getMatcher(command, "^deck \\s*rm-card \\s*-c\\s* (.+?)\\s* -d \\s*(.+?) \\s*-s$")).find()) {
            return removeCardFromSideDeck(matcher.group(2), matcher.group(3), matcher.group(1));

        }

        if ((matcher = getMatcher(command, "^D (.+?)deck \\s*add-card \\s*--card \\s*(.+?)\\s* --deck \\s*(.+?) \\s*--side$")).find() || (matcher = getMatcher(command, "^deck \\s*add-card \\s*-c \\s*(.+?)\\s* -d \\s*(.+?) \\s*-s$")).find()) {
            return addCardToSideDeck(matcher.group(2), matcher.group(3), matcher.group(1));

        }

        if ((matcher = getMatcher(command, "^D (.+?)deck\\s* delete \\s*(.+?)$")).find() || (matcher = getMatcher(command, "^deck\\s* d \\s*(.+?)$")).find()) {
            return deleteDeck(matcher.group(2), matcher.group(1));

        }
        if ((matcher = getMatcher(command, "^D (.+?)deck\\s* set-activate \\s*(.+?)$")).find() || (matcher = getMatcher(command, "^deck\\s* s-a \\s*(.+?)$")).find()) {
            return setActivate(matcher.group(2), matcher.group(1));

        }

        if ((matcher = getMatcher(command, "^D (.+?)deck\\s* add-card \\s*--card \\s*(.+?)\\s* --deck \\s*(.+?)$")).find() || (matcher = getMatcher(command, "^deck\\s* add-card \\s*-c \\s*(.+?)\\s* -d \\s*(.+?)$")).find()) {
            return addCardToMainDeck(matcher.group(2), matcher.group(3), matcher.group(1));

        }

        if ((matcher = getMatcher(command, "^D (.?)deck \\s*rm-card \\s*--card \\s*(.+?)\\s* --deck \\s*(.+?)$")).find() || (matcher = getMatcher(command, "^deck \\s*rm-card \\s*-c \\s*(.+?)\\s* -d \\s*(.+?)$")).find()) {
            return removeCardFromMainDeck(matcher.group(2), matcher.group(3),matcher.group(1));

        }

        if (getMatcher(command, "^deck\\s* show \\s*--all$").find() || getMatcher(command, "^deck\\s* show \\s*-a$").find()) {
            showAllDeck();
            return 1;
        }
        if ((matcher = getMatcher(command, "^deck \\s*show \\s*--deck-name \\s*(.+?)$")).find() || (matcher = getMatcher(command, "^deck \\s*show \\s*-d-n \\s*(.+?)$")).find()) {
            showMainDeck(matcher.group(1));
            return 1;
        }
        if (getMatcher(command, "^deck\\s* show\\s* --cards$").find() || getMatcher(command, "^deck\\s* show\\s* -c$").find()) {
            showCards();
            return 1;
        }

        if ((matcher = getMatcher(command, "^D (.+?)deck \\s*show \\s*--deck-name\\s* (.+?) \\s*--side$")).find() || (matcher = getMatcher(command, "^deck \\s*show \\s*-d-n\\s* (.+?) \\s*-s$")).find()) {
            showSideDeck(matcher.group(1));
            return 1;
        }
        return null;
    }

    public static Matcher getMatcher(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }

    public static String setActivate(String deckName, String token) {
        UserModel user = UserModel.getUserByUsername(RegisterAndLoginController.allOnlineUsers.get(token));
        if (user.isUserHaveThisDeck(deckName)) {
            user.setActiveDeck(deckName);
            UserModel.allUsersInfo.replace(RegisterAndLoginController.allOnlineUsers.get(token), user);
            Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
            return ("deck activated successfully");
        } else {
            return ("deck with name " + deckName + " does not exist");
        }

    }

    public static String deleteDeck(String deckName, String token) {
        UserModel user = UserModel.getUserByUsername(RegisterAndLoginController.allOnlineUsers.get(token));
        if (user.isUserHaveThisDeck(deckName)) {
            user.deleteDeck(deckName);
            UserModel.allUsersInfo.replace(RegisterAndLoginController.allOnlineUsers.get(token), user);
            Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
            return ("deck deleted successfully");
        } else {
            return ("deck with name " + deckName + " does not exist");
        }
    }

    public static String createDeck(String deckName, String token) {
        UserModel userModel = UserModel.getUserByUsername(RegisterAndLoginController.allOnlineUsers.get(token));
        if (userModel.isUserHaveThisDeck(deckName)) {
            return ("deck with name " + deckName + " already exists");
        } else {

            userModel.addDeck(new DeckModel(deckName));
            UserModel.allUsersInfo.replace(MainMenuController.username, userModel);
            Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
            return ("deck created successfully!");
        }
    }

    public static String addCardToMainDeck(String cardName, String deckName, String token) {
        UserModel user = UserModel.getUserByUsername(RegisterAndLoginController.allOnlineUsers.get(token));
        int counter;
        if (user.userAllDecks.get(deckName) == null) {
            counter = 0;
        } else {
            counter = user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                    user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName);
        }
        if (user.isUserHaveCard(cardName) && counter < user.userAllCards.get(cardName)) {
            if (UserModel.getUserByUsername(RegisterAndLoginController.allOnlineUsers.get(token)).isUserHaveThisDeck(deckName)) {
                if (user.userAllDecks.get(deckName).getMainAllCardNumber() < 60 && user.isUserHaveCard(cardName)) {
                    if (Card.getCardsByName(cardName).getCardModel().equals("Monster")) {
                        if (user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                                user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) < 3) {
                            DeckModel deckModel = user.userAllDecks.get(deckName);
                            deckModel.addCardToMain(cardName);
                            user.userAllDecks.replace(deckName, deckModel);
                            UserModel.allUsersInfo.replace(RegisterAndLoginController.allOnlineUsers.get(token), user);
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
                                UserModel.allUsersInfo.replace(RegisterAndLoginController.allOnlineUsers.get(token), user);
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
                                UserModel.allUsersInfo.replace(RegisterAndLoginController.allOnlineUsers.get(token), user);
                                Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                                return ("card added to deck successfully");

                            } else {
                                return ("there are already one cards with name " + cardName + " in deck " + deckName);
                            }
                        }
                    } else {
                        if (TrapCard.getTrapCardByName(cardName).getStatus().equals("Unlimited")) {
                            if (user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                                    user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) < 3) {
                                DeckModel deckModel = user.userAllDecks.get(deckName);
                                deckModel.addCardToMain(cardName);
                                user.userAllDecks.replace(deckName, deckModel);
                                UserModel.allUsersInfo.replace(RegisterAndLoginController.allOnlineUsers.get(token), user);
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
                                UserModel.allUsersInfo.replace(RegisterAndLoginController.allOnlineUsers.get(token), user);
                                Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                                return ("card added to deck successfully");

                            } else {
                                return ("there are already one cards with name " + cardName + " in deck " + deckName);
                            }
                        }
                    }
                } else {
                    return ("main deck is full");
                }
            } else {
                return ("deck with name " + deckName + " does not exist");
            }
        } else {
            return ("card with name " + cardName + " does not exist");
        }
        return null;
    }

    public static String addCardToSideDeck(String cardName, String deckName, String token) {
        UserModel user = UserModel.getUserByUsername(RegisterAndLoginController.allOnlineUsers.get(token));
        int counter;
        if (user.userAllDecks.get(deckName) == null) {
            counter = 0;
        } else {
            counter = user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                    user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName);
        }
        if (user.isUserHaveCard(cardName) && counter < user.userAllCards.get(cardName)) {
            if (UserModel.getUserByUsername(RegisterAndLoginController.allOnlineUsers.get(token)).isUserHaveThisDeck(deckName)) {
                if (user.userAllDecks.get(deckName).getSideAllCardNumber() < 10 && user.isUserHaveCard(cardName)) {
                    if (Card.getCardsByName(cardName).getCardModel().equals("Monster")) {
                        if (user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) + user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) < 3) {
                            DeckModel deckModel = user.userAllDecks.get(deckName);
                            deckModel.addCardToSide(cardName);
                            user.userAllDecks.replace(deckName, deckModel);
                            UserModel.allUsersInfo.replace(RegisterAndLoginController.allOnlineUsers.get(token), user);

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
                                UserModel.allUsersInfo.replace(RegisterAndLoginController.allOnlineUsers.get(token), user);
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
                                UserModel.allUsersInfo.replace(RegisterAndLoginController.allOnlineUsers.get(token), user);
                                Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                                return ("card added to deck successfully");

                            } else {
                                return ("there are already one cards with name " + cardName + " in deck " + deckName);
                            }
                        }
                    } else {
                        if (TrapCard.getTrapCardByName(cardName).getStatus().equals("Unlimited")) {
                            if (user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                                    user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) < 3) {
                                DeckModel deckModel = user.userAllDecks.get(deckName);
                                deckModel.addCardToSide(cardName);
                                user.userAllDecks.replace(deckName, deckModel);
                                UserModel.allUsersInfo.replace(RegisterAndLoginController.allOnlineUsers.get(token), user);
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
                                UserModel.allUsersInfo.replace(RegisterAndLoginController.allOnlineUsers.get(token), user);
                                Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                                return ("card added to deck successfully");

                            } else {
                                return ("there are already one cards with name " + cardName + " in deck " + deckName);
                            }
                        }
                    }

                } else {
                    return ("side deck is full");
                }
            } else {
                return ("deck with name " + deckName + " does not exist");
            }
        } else {
            return ("card with name " + cardName + " does not exist");
        }
        return null;
    }

    public static String  removeCardFromMainDeck(String cardName, String deckName,String token) {
        UserModel user = UserModel.getUserByUsername(RegisterAndLoginController.allOnlineUsers.get(token));
        if (user.isUserHaveThisDeck(deckName)) {
            if (user.userAllDecks.get(deckName).isMainDeckHaveThisCard(cardName)) {
                DeckModel deckModel = user.userAllDecks.get(deckName);
                deckModel.removeCardFromMain(cardName);
                user.userAllDecks.replace(deckName, deckModel);
                UserModel.allUsersInfo.replace(RegisterAndLoginController.allOnlineUsers.get(token), user);
                Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                return ("card removed form deck successfully");
            } else {
                return ("card with name " + cardName + " does not exist in main deck");
            }
        } else {
            return ("deck with name " + deckName + " does not exist");
        }

    }

    public static String removeCardFromSideDeck(String cardName, String deckName, String token) {
        UserModel user = UserModel.getUserByUsername(RegisterAndLoginController.allOnlineUsers.get(token));
        if (user.isUserHaveThisDeck(deckName)) {
            if (user.userAllDecks.get(deckName).isSideDeckHaveThisCard(cardName)) {
                DeckModel deckModel = user.userAllDecks.get(deckName);
                deckModel.removeCardFromSide(cardName);
                user.userAllDecks.replace(deckName, deckModel);
                UserModel.allUsersInfo.replace(RegisterAndLoginController.allOnlineUsers.get(token), user);
                Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                return ("card removed form deck successfully");
            } else {
                return ("card with name " + cardName + " does not exist in side deck");

            }
        } else {
            return ("deck with name " + deckName + " does not exist");
        }


    }

    public static void showAllDeck() {
        UserModel user = UserModel.getUserByUsername(MainMenuController.username);
        String[] keys;
        keys = UserModel.getUserByUsername(MainMenuController.username).userAllDecks.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        return ("Decks:");
        return ("Active deck:");
        if (!user.getActiveDeck().equals("")) {
            DeckModel deck = UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(user.getActiveDeck());
            return (user.getActiveDeck() + ": main deck " + deck.getMainAllCardNumber() + ", side deck " + deck.getSideAllCardNumber() + ", " + deck.validOrInvalid());
        }
        return ("Other decks:");
        for (String key : keys) {
            if (user.getActiveDeck().equals(key)) {
                continue;
            }
            DeckModel deck = UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(key);
            return (key + ": main deck " + deck.getMainAllCardNumber() + ", side deck " + deck.getSideAllCardNumber() + ", " + deck.validOrInvalid());
        }

    }

    public static void showMainDeck(String deckName) {
        if (UserModel.getUserByUsername(MainMenuController.username).isUserHaveThisDeck(deckName)) {
            DeckModel deck = UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(deckName);
            return ("Deck: " + deckName);
            return ("Main deck:");
            return ("Monsters:");
            String[] cardNames;
            cardNames = deck.cardsInMainDeck.keySet().toArray(new String[0]);
            Arrays.sort(cardNames);
            for (String cardName : cardNames) {
                if (!Card.getCardsByName(cardName).getCardModel().equals("Monster")) {
                    continue;
                }
                return (cardName + ": " + Card.getCardsByName(cardName).getDescription() + " (" + deck.cardsInMainDeck.get(cardName) + ")");
            }
            return ("Spell and Traps:");
            for (String cardName : cardNames) {
                if (Card.getCardsByName(cardName).getCardModel().equals("Monster")) {
                    continue;
                }
                return (cardName + ": " + Card.getCardsByName(cardName).getDescription() + " (" + deck.cardsInMainDeck.get(cardName) + ")");
            }
        } else {
            return ("deck with name " + deckName + " does not exist");
        }

    }

    public static void showSideDeck(String deckName) {
        if (UserModel.getUserByUsername(MainMenuController.username).isUserHaveThisDeck(deckName)) {
            DeckModel deck = UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(deckName);
            return ("Deck: " + deckName);
            return ("Side deck:");
            return ("Monsters:");
            String[] cardNames;
            cardNames = deck.cardsInSideDeck.keySet().toArray(new String[0]);
            Arrays.sort(cardNames);
            for (String cardName : cardNames) {
                if (!Card.getCardsByName(cardName).getCardModel().equals("Monster")) {
                    continue;
                }
                return (cardName + ": " + Card.getCardsByName(cardName).getDescription() + " (" + deck.cardsInSideDeck.get(cardName) + ")");
            }
            return ("Spell and Traps:");
            for (String cardName : cardNames) {
                if (Card.getCardsByName(cardName).getCardModel().equals("Monster")) {
                    continue;
                }
                return (cardName + ": " + Card.getCardsByName(cardName).getDescription() + " (" + deck.cardsInSideDeck.get(cardName) + ")");
            }

        } else {
            return ("deck with name " + deckName + " does not exist");
        }
    }

    public static void showCards() {
        HashMap<String, Integer> hashMap = UserModel.getUserByUsername(MainMenuController.username).userAllCards;
        String[] keys = hashMap.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        for (String key : keys) {
            return (key + ": " + Card.getCards().get(key).getDescription() + " (" + hashMap.get(key) + ")");
        }
    }

}