package controller;


import view.DeckView;
import view.MainMenuView;
import model.*;


import java.util.Arrays;


import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeckController {
    public static void findMatcher() {
        while (true) {
            String command = DeckView.getCommand();
            command = command.trim();
            Pattern pattern = Pattern.compile("^deck \\s*create \\s*(.+?)$");
            Matcher matcher = pattern.matcher(command);
            if (matcher.find()) {
                createDeck(matcher.group(1));
                continue;
            }


            pattern = Pattern.compile("^deck \\s*rm-card \\s*--card\\s* (.+?)\\s* --deck \\s*(.+?) \\s*--side$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                removeCardFromSideDeck(matcher.group(1), matcher.group(2));
                continue;
            }

            pattern = Pattern.compile("^deck \\s*rm-card \\s*--card \\s*(.+?) \\s*--side\\s* --deck\\s* (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                removeCardFromSideDeck(matcher.group(1), matcher.group(2));
                continue;
            }
            pattern = Pattern.compile("^deck\\s* rm-card\\s* --side\\s* --card \\s*(.+?) \\s*--deck \\s*(.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                removeCardFromSideDeck(matcher.group(1), matcher.group(2));
                continue;
            }
            pattern = Pattern.compile("^deck\\s* rm-card \\s*--side \\s*--deck\\s* (.+?)\\s* --card \\s*(.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                removeCardFromSideDeck(matcher.group(2), matcher.group(1));
                continue;
            }
            pattern = Pattern.compile("^deck\\s* rm-card\\s* --deck \\s*(.+?)\\s* --side\\s* --card\\s* (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                removeCardFromSideDeck(matcher.group(2), matcher.group(1));
                continue;
            }
            pattern = Pattern.compile("^deck\\s* rm-card\\s* --deck \\s*(.+?)--card\\s* (.+?) \\s*--side$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                removeCardFromSideDeck(matcher.group(2), matcher.group(1));
                continue;
            }


            pattern = Pattern.compile("^deck \\s*add-card \\s*--card \\s*(.+?)\\s* --deck \\s*(.+?) \\s*--side$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                addCardToSideDeck(matcher.group(1), matcher.group(2));
                continue;
            }

            pattern = Pattern.compile("^deck\\s* add-card \\s*--card \\s*(.+?)\\s* --side \\s*--deck\\s* (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                addCardToSideDeck(matcher.group(1), matcher.group(2));
                continue;
            }
            pattern = Pattern.compile("^deck\\s* add-card\\s* --side \\s*--card \\s*(.+?) \\s*--deck\\s* (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                addCardToSideDeck(matcher.group(1), matcher.group(2));
                continue;
            }
            pattern = Pattern.compile("^deck\\s* add-card\\s* --side\\s* --deck\\s* (.+?)\\s* --card\\s* (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                addCardToMainDeck(matcher.group(2), matcher.group(1));
                continue;
            }
            pattern = Pattern.compile("^deck \\s*add-card \\s*--deck\\s* (.+?)\\s* --side \\s*--card \\s*(.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                addCardToMainDeck(matcher.group(2), matcher.group(1));
                continue;
            }
            pattern = Pattern.compile("^deck \\s*add-card \\s*--deck\\s* (.+?)--card\\s* (.+?) \\s*--side$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                addCardToMainDeck(matcher.group(2), matcher.group(1));
                continue;
            }
            pattern = Pattern.compile("^deck \\s*show \\s*--side\\s* --deck-name \\s*(.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                showSideDeck(matcher.group(1));
                continue;
            }
            pattern = Pattern.compile("^deck \\s*show \\s*--deck-name\\s* (.+?) \\s*--side$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                showSideDeck(matcher.group(1));
                continue;
            }


            pattern = Pattern.compile("^deck\\s* delete \\s*(.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                deleteDeck(matcher.group(1));
                continue;
            }

            pattern = Pattern.compile("^deck\\s* set-activate \\s*(.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                setActivate(matcher.group(1));
                continue;
            }


            pattern = Pattern.compile("^deck\\s* add-card \\s*--card \\s*(.+?)\\s* --deck \\s*(.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                addCardToMainDeck(matcher.group(1), matcher.group(2));
                continue;
            }
            pattern = Pattern.compile("^deck \\s*add-card \\s*--deck\\s* (.+?)\\s* --card (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                addCardToMainDeck(matcher.group(2), matcher.group(1));
                continue;
            }


            pattern = Pattern.compile("^deck \\s*rm-card \\s*--card \\s*(.+?)\\s* --deck \\s*(.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                removeCardFromMainDeck(matcher.group(1), matcher.group(2));
                continue;
            }
            pattern = Pattern.compile("^deck\\s* rm-card \\s*--deck\\s* (.+?)\\s* --card\\s* (.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                removeCardFromMainDeck(matcher.group(2), matcher.group(1));
                continue;
            }


            pattern = Pattern.compile("^deck\\s* show \\s*--all$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                showAllDeck();
                continue;
            }
            pattern = Pattern.compile("^deck \\s*show \\s*--deck-name \\s*(.+?)$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                showMainDeck(matcher.group(1));
                continue;
            }


            pattern = Pattern.compile("^deck\\s* show\\s* --cards$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {
                showCards();
                continue;
            }


            pattern = Pattern.compile("^menu \\s*exit$");
            matcher = pattern.matcher(command);
            if (matcher.find()) {

                break;
            }

            pattern = Pattern.compile("^menu \\s*enter \\s*(.+?)$");
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


    public static void setActivate(String deckName) {
        UserModel user = UserModel.getUserByUsername(MainMenuController.username);
        if (user.isUserHaveThisDeck(deckName)) {
            user.setActiveDeck(deckName);
            UserModel.allUsersInfo.replace(MainMenuController.username, user);
            JSON.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
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
            JSON.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
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
            JSON.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
            DeckView.showInput("deck created successfully!");
        }
    }

    public static void addCardToMainDeck(String cardName, String deckName) {
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
                            DeckView.showInput("card added to deck successfully");
                            JSON.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
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
                                JSON.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
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
                                JSON.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                            } else {
                                DeckView.showInput("there are already one cards with name " + cardName + " in deck " + deckName);
                            }
                        } else {
                            if (user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                                    user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) < 2) {

                                DeckModel deckModel = user.userAllDecks.get(deckName);

                                deckModel.addCardToMain(cardName);

                                user.userAllDecks.replace(deckName, deckModel);

                                UserModel.allUsersInfo.replace(MainMenuController.username, user);
                                DeckView.showInput("card added to deck successfully");


                                JSON.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);

                            } else {
                                DeckView.showInput("there are already two cards with name " + cardName + " in deck " + deckName);
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
                                JSON.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
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
                                JSON.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                            } else {
                                DeckView.showInput("there are already one cards with name " + cardName + " in deck " + deckName);
                            }
                        } else {
                            if (user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                                    user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) < 2) {
                                DeckModel deckModel = user.userAllDecks.get(deckName);
                                deckModel.addCardToMain(cardName);
                                user.userAllDecks.replace(deckName, deckModel);
                                UserModel.allUsersInfo.replace(MainMenuController.username, user);
                                DeckView.showInput("card added to deck successfully");

                                JSON.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);

                            } else {
                                DeckView.showInput("there are already two cards with name " + cardName + " in deck " + deckName);
                            }
                        }
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

    public static void addCardToSideDeck(String cardName, String deckName) {
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
                            DeckView.showInput("card added to deck successfully");
                            JSON.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
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
                                JSON.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
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
                                JSON.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                            } else {
                                DeckView.showInput("there are already one cards with name " + cardName + " in deck " + deckName);
                            }
                        } else {
                            if (user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                                    user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) < 2) {

                                DeckModel deckModel = user.userAllDecks.get(deckName);

                                deckModel.addCardToSide(cardName);

                                user.userAllDecks.replace(deckName, deckModel);

                                UserModel.allUsersInfo.replace(MainMenuController.username, user);
                                DeckView.showInput("card added to deck successfully");

                                JSON.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);

                            } else {
                                DeckView.showInput("there are already two cards with name " + cardName + " in deck " + deckName);
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
                                JSON.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
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
                                JSON.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                            } else {
                                DeckView.showInput("there are already one cards with name " + cardName + " in deck " + deckName);
                            }
                        } else {
                            if (user.userAllDecks.get(deckName).getNumberOfCardInMainDeck(cardName) +
                                    user.userAllDecks.get(deckName).getNumberOfCardInSideDeck(cardName) < 2) {
                                DeckModel deckModel = user.userAllDecks.get(deckName);
                                deckModel.addCardToSide(cardName);
                                user.userAllDecks.replace(deckName, deckModel);
                                UserModel.allUsersInfo.replace(MainMenuController.username, user);
                                DeckView.showInput("card added to deck successfully");
                                JSON.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                            } else {
                                DeckView.showInput("there are already two cards with name " + cardName + " in deck " + deckName);
                            }
                        }
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
    public static void removeCardFromMainDeck(String cardName, String deckName) {
        UserModel user = UserModel.getUserByUsername(MainMenuController.username);
        if (user.isUserHaveThisDeck(deckName)) {
            if (user.userAllDecks.get(deckName).isMainDeckHaveThisCard(cardName)) {
                DeckModel deckModel = user.userAllDecks.get(deckName);
                deckModel.removeCardFromMain(cardName);
                user.userAllDecks.replace(deckName, deckModel);
                UserModel.allUsersInfo.replace(MainMenuController.username, user);
                JSON.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                DeckView.showInput("card removed form deck successfully");
            } else {
                DeckView.showInput("card with name " + cardName + " does not exist in main deck");
            }
        } else {
            DeckView.showInput("deck with name " + deckName + " does not exist");
        }
        UserModel.allUsersInfo.replace(MainMenuController.username, user);
        JSON.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
    }

    public static void removeCardFromSideDeck(String cardName, String deckName) {
        UserModel user = UserModel.getUserByUsername(MainMenuController.username);
        if (user.isUserHaveThisDeck(deckName)) {
            if (user.userAllDecks.get(deckName).isSideDeckHaveThisCard(cardName)) {
                DeckModel deckModel = user.userAllDecks.get(deckName);
                deckModel.removeCardFromSide(cardName);
                user.userAllDecks.replace(deckName, deckModel);
                UserModel.allUsersInfo.replace(MainMenuController.username, user);
                JSON.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
                DeckView.showInput("card removed form deck successfully");
            } else {
                DeckView.showInput("card with name " + cardName + " does not exist in side deck");

            }
        } else {
            DeckView.showInput("deck with name " + deckName + " does not exist");
        }
        UserModel.allUsersInfo.replace(MainMenuController.username, user);
        JSON.writeUserModelInfo(UserModel.allUsersInfo, UserModel.allUsernames, UserModel.allUsersNicknames);
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
            DeckView.showInput(user.getActiveDeck() + ": main deck " + deck.getMainAllCardNumber() + ", side deck " + deck.getSideAllCardNumber() + " " + deck.validOrInvalid());
        }
        DeckView.showInput("Other decks:");
        for (String key : keys) {
            if (user.getActiveDeck().equals(key)) {
                continue;
            }
            DeckModel deck = UserModel.getUserByUsername(MainMenuController.username).userAllDecks.get(key);
            DeckView.showInput(key + ": main deck " + deck.getMainAllCardNumber() + ", side deck " + deck.getSideAllCardNumber() + " " + deck.validOrInvalid());
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
            DeckView.showInput(key + ":" + Card.getCards().get(key).getDescription() + "  " + hashMap.get(key));
        }
    }
}