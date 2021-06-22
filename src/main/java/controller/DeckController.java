package controller;
import model.*;
import view.*;
import java.util.*;
import java.util.regex.*;


public class DeckController {

    public static int run() {
        while (true) {
            String command = DeckView.getCommand();
            command = command.trim();
            int breaker = findMatcher(command);
            if (breaker == 0) {
                break;
            }
        }
        return 0;
    }

    public static int findMatcher(String command) {
        Matcher matcher;
        if ((matcher = getMatcher(command, "^deck \\s*create \\s*(.+?)$")).find()) {
            createDeck(matcher.group(1));
            return 1;
        }
        if ((matcher = getMatcher(command, "^deck \\s*rm-card \\s*--card\\s* (.+?)\\s* --deck \\s*(.+?) \\s*--side$")).find() || (matcher = getMatcher(command, "^deck \\s*rm-card \\s*-c\\s* (.+?)\\s* -d \\s*(.+?) \\s*-s$")).find()) {
            removeCardFromSideDeck(matcher.group(1), matcher.group(2));
            return 1;
        }
        if ((matcher = getMatcher(command, "^deck \\s*rm-card \\s*--card \\s*(.+?) \\s*--side\\s* --deck\\s* (.+?)$")).find() || (matcher = getMatcher(command, "^deck \\s*rm-card \\s*-c \\s*(.+?) \\s*-s\\s* -d\\s* (.+?)$")).find()) {
            removeCardFromSideDeck(matcher.group(1), matcher.group(2));
            return 1;
        }
        if ((matcher = getMatcher(command, "^deck\\s* rm-card\\s* --side\\s* --card \\s*(.+?) \\s*--deck \\s*(.+?)$")).find() || (matcher = getMatcher(command, "^deck\\s* rm-card\\s* -s\\s* -c \\s*(.+?) \\s*-d \\s*(.+?)$")).find()) {
            removeCardFromSideDeck(matcher.group(1), matcher.group(2));
            return 1;
        }
        if ((matcher = getMatcher(command, "^deck\\s* rm-card \\s*--side \\s*--deck\\s* (.+?)\\s* --card \\s*(.+?)$")).find() || (matcher = getMatcher(command, "^deck\\s* rm-card \\s*-s \\s*-d\\s* (.+?)\\s* -c \\s*(.+?)$")).find()) {
            removeCardFromSideDeck(matcher.group(2), matcher.group(1));
            return 1;
        }
        if ((matcher = getMatcher(command, "^deck\\s* rm-card\\s* --deck \\s*(.+?)\\s* --side\\s* --card\\s* (.+?)$")).find() || (matcher = getMatcher(command, "^deck\\s* rm-card\\s* -d \\s*(.+?)\\s* -s\\s* -c\\s* (.+?)$")).find()) {
            removeCardFromSideDeck(matcher.group(2), matcher.group(1));
            return 1;
        }
        if ((matcher = getMatcher(command, "^deck\\s* rm-card\\s* --deck \\s*(.+?)--card\\s* (.+?) \\s*--side$")).find() || (matcher = getMatcher(command, "^deck\\s* rm-card\\s* -d \\s*(.+?)-c\\s* (.+?) \\s*-s$")).find()) {
            removeCardFromSideDeck(matcher.group(2), matcher.group(1));
            return 1;
        }
        if ((matcher = getMatcher(command, "^deck \\s*add-card \\s*--card \\s*(.+?)\\s* --deck \\s*(.+?) \\s*--side$")).find() || (matcher = getMatcher(command, "^deck \\s*add-card \\s*-c \\s*(.+?)\\s* -d \\s*(.+?) \\s*-s$")).find()) {
            addCardToSideDeck(matcher.group(1), matcher.group(2));
            return 1;
        }
        if ((matcher = getMatcher(command, "^deck\\s* add-card \\s*--card \\s*(.+?)\\s* --side \\s*--deck\\s* (.+?)$")).find() || (matcher = getMatcher(command, "^deck\\s* add-card \\s*-c \\s*(.+?)\\s* -s \\s*-d\\s* (.+?)$")).find()) {
            addCardToSideDeck(matcher.group(1), matcher.group(2));
            return 1;
        }
        if ((matcher = getMatcher(command, "^deck\\s* add-card\\s* --side \\s*--card \\s*(.+?) \\s*--deck\\s* (.+?)$")).find() || (matcher = getMatcher(command, "^deck\\s* add-card\\s* -s \\s*-c \\s*(.+?) \\s*-d\\s* (.+?)$")).find()) {
            addCardToSideDeck(matcher.group(1), matcher.group(2));
            return 1;
        }
        if ((matcher = getMatcher(command, "^deck\\s* add-card\\s* --side\\s* --deck\\s* (.+?)\\s* --card\\s* (.+?)$")).find() || (matcher = getMatcher(command, "^deck\\s* add-card\\s* -s\\s* -d\\s* (.+?)\\s* -c\\s* (.+?)$")).find()) {
            addCardToMainDeck(matcher.group(2), matcher.group(1));
            return 1;
        }
        if ((matcher = getMatcher(command, "^deck \\s*add-card \\s*--deck\\s* (.+?)\\s* --side \\s*--card \\s*(.+?)$")).find() || (matcher = getMatcher(command, "^deck \\s*add-card \\s*-d\\s* (.+?)\\s* -s \\s*-c \\s*(.+?)$")).find()) {
            addCardToMainDeck(matcher.group(2), matcher.group(1));
            return 1;
        }
        if ((matcher = getMatcher(command, "^deck \\s*add-card \\s*--deck\\s* (.+?)--card\\s* (.+?) \\s*--side$")).find() || (matcher = getMatcher(command, "^deck \\s*add-card \\s*-d\\s* (.+?)-c\\s* (.+?) \\s*-s$")).find()) {
            addCardToMainDeck(matcher.group(2), matcher.group(1));
            return 1;
        }
        if ((matcher = getMatcher(command, "^deck \\s*show \\s*--side\\s* --deck-name \\s*(.+?)$")).find() || (matcher = getMatcher(command, "^deck \\s*show \\s*-s\\s* -d-n \\s*(.+?)$")).find()) {
            showSideDeck(matcher.group(1));
            return 1;
        }
        if ((matcher = getMatcher(command, "^deck \\s*show \\s*--deck-name\\s* (.+?) \\s*--side$")).find() || (matcher = getMatcher(command, "^deck \\s*show \\s*-d-n\\s* (.+?) \\s*-s$")).find()) {
            showSideDeck(matcher.group(1));
            return 1;
        }
        if ((matcher = getMatcher(command, "^deck\\s* delete \\s*(.+?)$")).find() || (matcher = getMatcher(command, "^deck\\s* d \\s*(.+?)$")).find()) {
            deleteDeck(matcher.group(1));
            return 1;
        }
        if ((matcher = getMatcher(command, "^deck\\s* set-activate \\s*(.+?)$")).find() || (matcher = getMatcher(command, "^deck\\s* s-a \\s*(.+?)$")).find()) {
            setActivate(matcher.group(1));
            return 1;
        }
        if ((matcher = getMatcher(command, "^deck\\s* add-card \\s*--card \\s*(.+?)\\s* --deck \\s*(.+?)$")).find() || (matcher = getMatcher(command, "^deck\\s* add-card \\s*-c \\s*(.+?)\\s* -d \\s*(.+?)$")).find()) {
            addCardToMainDeck(matcher.group(1), matcher.group(2));
            return 1;
        }
        if ((matcher = getMatcher(command, "^deck \\s*add-card \\s*--deck\\s* (.+?)\\s* --card (.+?)$")).find() || (matcher = getMatcher(command, "^deck \\s*add-card \\s*-d\\s* (.+?)\\s* -c (.+?)$")).find()) {
            addCardToMainDeck(matcher.group(2), matcher.group(1));
            return 1;
        }
        if ((matcher = getMatcher(command, "^deck \\s*rm-card \\s*--card \\s*(.+?)\\s* --deck \\s*(.+?)$")).find() || (matcher = getMatcher(command, "^deck \\s*rm-card \\s*-c \\s*(.+?)\\s* -d \\s*(.+?)$")).find()) {
            removeCardFromMainDeck(matcher.group(1), matcher.group(2));
            return 1;
        }
        if ((matcher = getMatcher(command, "^deck\\s* rm-card \\s*--deck\\s* (.+?)\\s* --card\\s* (.+?)$")).find() || (matcher = getMatcher(command, "^deck\\s* rm-card \\s*-d\\s* (.+?)\\s* -c\\s* (.+?)$")).find()) {
            removeCardFromMainDeck(matcher.group(2), matcher.group(1));
            return 1;
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
        if (getMatcher(command, "^menu \\s*exit$").find() || getMatcher(command, "^m \\s*ex$").find()) {
            return 0;
        }
        if (getMatcher(command, "^menu\\s* show-current$").find() || getMatcher(command, "^m\\s* s-c$").find()) {
            MainMenuView.showInput("Deck");
            return 1;
        }
        if ((matcher = getMatcher(command, "^menu \\s*enter \\s*(.+?)$")).find() || (matcher = getMatcher(command, "^m \\s*en \\s*(.+?)$")).find()) {
            if (matcher.group(1).equals("Duel") || matcher.group(1).equals("Deck") || matcher.group(1).equals("Profile") || matcher.group(1).equals("Shop") || matcher.group(1).equals("Scoreboard")) {
                DeckView.showInput("menu navigation is not possible");
            } else {
                MainMenuView.showInput("invalid command");
            }
            return 1;
        }
        DeckView.showInput("invalid command");
        return 1;
    }

    public static Matcher getMatcher(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }

    public static void setActivate(String deckName) {
        UserModel user = UserModel.getUserByUsername(MainMenuController.username);
        if (user.isUserHaveThisDeck(deckName)) {
            user.setActiveDeck(deckName);
            UserModel.allUsersInfo.replace(MainMenuController.username, user);
            Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
            DeckView.showInput("deck activated successfully");
        } else {
            DeckView.showInput("deck with name " + deckName + " does not exist");
        }

    }

    public static void deleteDeck(String deckName) {
        UserModel user = UserModel.getUserByUsername(MainMenuController.username);
        if (user.isUserHaveThisDeck(deckName)) {
            user.deleteDeck(deckName);
            UserModel.allUsersInfo.replace(MainMenuController.username, user);
            Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
            DeckView.showInput("deck deleted successfully");
        } else {
            DeckView.showInput("deck with name " + deckName + " does not exist");
        }
    }

    public static void createDeck(String deckName) {
        if (UserModel.getUserByUsername(MainMenuController.username).isUserHaveThisDeck(deckName)) {
            DeckView.showInput("deck with name " + deckName + " already exists");
        } else {
            UserModel user = UserModel.getUserByUsername(MainMenuController.username);
            user.addDeck(new DeckModel(deckName));
            UserModel.allUsersInfo.replace(MainMenuController.username, user);
            Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
            DeckView.showInput("deck created successfully!");
        }
    }

    public static void addCardToMainDeck(String cardName, String deckName) {
        UserModel user = UserModel.getUserByUsername(MainMenuController.username);
        int counter ;
        if (user.userAllDecks.get(deckName)==null){
            counter=0;
        }
        else {
            counter=user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                    user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName);
        }
        if (user.isUserHaveCard(cardName) && counter < user.userAllCards.get(cardName)) {
            if (UserModel.getUserByUsername(MainMenuController.username).isUserHaveThisDeck(deckName)) {
                if (user.userAllDecks.get(deckName).getMainAllCardNumber() < 60 && user.isUserHaveCard(cardName)) {
                    if (Card.getCardsByName(cardName).getCardModel().equals("Monster")) {
                        if (user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                                user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) < 3) {
                            DeckModel deckModel = user.userAllDecks.get(deckName);
                            deckModel.addCardToMain(cardName);
                            user.userAllDecks.replace(deckName, deckModel);
                            UserModel.allUsersInfo.replace(MainMenuController.username, user);
                            DeckView.showInput("card added to deck successfully");
                            Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                        } else {
                            DeckView.showInput("there are already three cards with name " + cardName + " in deck " + deckName);
                        }
                    } else if (Card.getCardsByName(cardName).getCardModel().equals("Spell")) {
                        if (SpellCard.getSpellCardByName(cardName).getStatus().equals("Unlimited")) {
                            if (user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                                    user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) < 3) {
                                DeckModel deckModel = user.userAllDecks.get(deckName);
                                deckModel.addCardToMain(cardName);
                                user.userAllDecks.replace(deckName, deckModel);
                                UserModel.allUsersInfo.replace(MainMenuController.username, user);
                                DeckView.showInput("card added to deck successfully");
                                Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                            } else {
                                DeckView.showInput("there are already three cards with name " + cardName + " in deck " + deckName);
                            }
                        } else if (SpellCard.getSpellCardByName(cardName).getStatus().equals("Limited")) {
                            if (user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                                    user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) < 1) {
                                DeckModel deckModel = user.userAllDecks.get(deckName);
                                deckModel.addCardToMain(cardName);
                                user.userAllDecks.replace(deckName, deckModel);
                                UserModel.allUsersInfo.replace(MainMenuController.username, user);
                                DeckView.showInput("card added to deck successfully");
                                Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                            } else {
                                DeckView.showInput("there are already one cards with name " + cardName + " in deck " + deckName);
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
                                DeckView.showInput("card added to deck successfully");
                                Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                            } else {
                                DeckView.showInput("there are already three cards with name " + cardName + " in deck " + deckName);
                            }
                        } else if (TrapCard.getTrapCardByName(cardName).getStatus().equals("Limited")) {
                            if (user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                                    user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) < 1) {
                                DeckModel deckModel = user.userAllDecks.get(deckName);
                                deckModel.addCardToMain(cardName);
                                user.userAllDecks.replace(deckName, deckModel);
                                UserModel.allUsersInfo.replace(MainMenuController.username, user);
                                DeckView.showInput("card added to deck successfully");
                                Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                            } else {
                                DeckView.showInput("there are already one cards with name " + cardName + " in deck " + deckName);
                            }
                        }
                    }
                } else {
                    DeckView.showInput("main deck is full");
                }
            } else {
                DeckView.showInput("deck with name " + deckName + " does not exist");
            }
        } else {
            DeckView.showInput("card with name " + cardName + " does not exist");
        }
    }

    public static void addCardToSideDeck(String cardName, String deckName) {
        UserModel user = UserModel.getUserByUsername(MainMenuController.username);
        int counter ;
        if (user.userAllDecks.get(deckName)==null){
            counter=0;
        }
        else {
            counter=user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                    user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName);
        }
        if (user.isUserHaveCard(cardName) && counter < user.userAllCards.get(cardName)) {
            if ( UserModel.getUserByUsername(MainMenuController.username).isUserHaveThisDeck(deckName)) {
                if (user.userAllDecks.get(deckName).getSideAllCardNumber() < 10 && user.isUserHaveCard(cardName)) {
                    if (Card.getCardsByName(cardName).getCardModel().equals("Monster")) {
                        if (user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) + user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) < 3) {
                            DeckModel deckModel = user.userAllDecks.get(deckName);
                            deckModel.addCardToSide(cardName);
                            user.userAllDecks.replace(deckName, deckModel);
                            UserModel.allUsersInfo.replace(MainMenuController.username, user);
                            DeckView.showInput("card added to deck successfully");
                            Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                        } else {
                            DeckView.showInput("there are already three cards with name " + cardName + " in deck " + deckName);
                        }
                    } else if (Card.getCardsByName(cardName).getCardModel().equals("Spell")) {
                        if (SpellCard.getSpellCardByName(cardName).getStatus().equals("Unlimited")) {
                            if (user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                                    user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) < 3) {
                                DeckModel deckModel = user.userAllDecks.get(deckName);
                                deckModel.addCardToSide(cardName);
                                user.userAllDecks.replace(deckName, deckModel);
                                UserModel.allUsersInfo.replace(MainMenuController.username, user);
                                DeckView.showInput("card added to deck successfully");
                                Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                            } else {
                                DeckView.showInput("there are already three cards with name " + cardName + " in deck " + deckName);
                            }
                        } else if (SpellCard.getSpellCardByName(cardName).getStatus().equals("Limited")) {
                            if (user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                                    user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) < 1) {

                                DeckModel deckModel = user.userAllDecks.get(deckName);
                                deckModel.addCardToSide(cardName);

                                user.userAllDecks.replace(deckName, deckModel);
                                UserModel.allUsersInfo.replace(MainMenuController.username, user);
                                DeckView.showInput("card added to deck successfully");
                                Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                            } else {
                                DeckView.showInput("there are already one cards with name " + cardName + " in deck " + deckName);
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
                                DeckView.showInput("card added to deck successfully");
                                Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                            } else {
                                DeckView.showInput("there are already three cards with name " + cardName + " in deck " + deckName);
                            }
                        } else if (TrapCard.getTrapCardByName(cardName).getStatus().equals("Limited")) {
                            if (user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                                    user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) < 1) {
                                DeckModel deckModel = user.userAllDecks.get(deckName);
                                deckModel.addCardToSide(cardName);
                                user.userAllDecks.replace(deckName, deckModel);
                                UserModel.allUsersInfo.replace(MainMenuController.username, user);
                                DeckView.showInput("card added to deck successfully");
                                Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                            } else {
                                DeckView.showInput("there are already one cards with name " + cardName + " in deck " + deckName);
                            }
                        }
                    }

                } else {
                    DeckView.showInput("side deck is full");
                }
            } else {
                DeckView.showInput("deck with name " + deckName + " does not exist");
            }
        } else {
            DeckView.showInput("card with name " + cardName + " does not exist");
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
                DeckView.showInput("card removed form deck successfully");
            } else {
                DeckView.showInput("card with name " + cardName + " does not exist in main deck");
            }
        } else {
            DeckView.showInput("deck with name " + deckName + " does not exist");
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
                DeckView.showInput("card removed form deck successfully");
            } else {
                DeckView.showInput("card with name " + cardName + " does not exist in side deck");

            }
        } else {
            DeckView.showInput("deck with name " + deckName + " does not exist");
        }
        UserModel.allUsersInfo.replace(MainMenuController.username, user);
        Json.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
    }

    public static void showAllDeck() {
        UserModel user = UserModel.getUserByUsername(MainMenuController.username);
        String[] keys;
        keys = UserModel.getUserByUsername(MainMenuController.username).userAllDecks.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        DeckView.showInput("Decks:");
        DeckView.showInput("Active deck:");
        if (!user.getActiveDeck().equals("")) {
            DeckModel deck = UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(user.getActiveDeck());
            DeckView.showInput(user.getActiveDeck() + ": main deck " + deck.getMainAllCardNumber() + ", side deck " + deck.getSideAllCardNumber() + ", " + deck.validOrInvalid());
        }
        DeckView.showInput("Other decks:");
        for (String key : keys) {
            if (user.getActiveDeck().equals(key)) {
                continue;
            }
            DeckModel deck = UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(key);
            DeckView.showInput(key + ": main deck " + deck.getMainAllCardNumber() + ", side deck " + deck.getSideAllCardNumber() + ", " + deck.validOrInvalid());
        }

    }

    public static void showMainDeck(String deckName) {
        if (UserModel.getUserByUsername(MainMenuController.username).isUserHaveThisDeck(deckName)) {
            DeckModel deck = UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(deckName);
            DeckView.showInput("Deck: " + deckName);
            DeckView.showInput("Main deck:");
            DeckView.showInput("Monsters:");
            String[] cardNames;
            cardNames = deck.cardsInMainDeck.keySet().toArray(new String[0]);
            Arrays.sort(cardNames);
            for (String cardName : cardNames) {
                if (!Card.getCardsByName(cardName).getCardModel().equals("Monster")) {
                    continue;
                }
                DeckView.showInput(cardName + ": " + Card.getCardsByName(cardName).getDescription() + " (" + deck.cardsInMainDeck.get(cardName) + ")");
            }
            DeckView.showInput("Spell and Traps:");
            for (String cardName : cardNames) {
                if (Card.getCardsByName(cardName).getCardModel().equals("Monster")) {
                    continue;
                }
                DeckView.showInput(cardName + ": " + Card.getCardsByName(cardName).getDescription() + " (" + deck.cardsInMainDeck.get(cardName) + ")");
            }
        } else {
            DeckView.showInput("deck with name " + deckName + " does not exist");
        }

    }

    public static void showSideDeck(String deckName) {
        if (UserModel.getUserByUsername(MainMenuController.username).isUserHaveThisDeck(deckName)) {
            DeckModel deck = UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(deckName);
            DeckView.showInput("Deck: " + deckName);
            DeckView.showInput("Side deck:");
            DeckView.showInput("Monsters:");
            String[] cardNames;
            cardNames = deck.cardsInSideDeck.keySet().toArray(new String[0]);
            Arrays.sort(cardNames);
            for (String cardName : cardNames) {
                if (!Card.getCardsByName(cardName).getCardModel().equals("Monster")) {
                    continue;
                }
                DeckView.showInput(cardName + ": " + Card.getCardsByName(cardName).getDescription() + " (" + deck.cardsInSideDeck.get(cardName) + ")");
            }
            DeckView.showInput("Spell and Traps:");
            for (String cardName : cardNames) {
                if (Card.getCardsByName(cardName).getCardModel().equals("Monster")) {
                    continue;
                }
                DeckView.showInput(cardName + ": " + Card.getCardsByName(cardName).getDescription() + " (" + deck.cardsInSideDeck.get(cardName) + ")");
            }

        } else {
            DeckView.showInput("deck with name " + deckName + " does not exist");
        }
    }

    public static void showCards() {
        HashMap<String, Integer> hashMap = UserModel.getUserByUsername(MainMenuController.username).userAllCards;
        String[] keys = hashMap.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        for (String key : keys) {
            DeckView.showInput(key + ": " + Card.getCards().get(key).getDescription() + " (" + hashMap.get(key) + ")");
        }
    }

}