package model;

public class UserModel {
    class UserModel {
        private String username;
        private String password;
        private String nickname;
        private int userScore;
        private int userCoin;
        private HashMap<String, Integer> userAllCards;
        private ArrayList<DeckModel> userAllDecks;
        private String activeDeck;
        private String currentMenu;
        private String onlineUser;
        public HashMap<String, UserModel> allUsersInfo;
        public ArrayList<String> allUsernames;
        public ArrayList<String> allUsersNicknames;


        public UserModel(String username, String password, String nickname) {

        }


        public final HashMap<String, Integer> getUserAllCards() {

        }


        public final int getUserCoin() {

        }


        public final void setUserState(String state) {

        }


        public final String getUserState() {

        }


        public final String getUsername() {

        }


        public final void setUsername(String username) {

        }


        public final String getPassword() {

        }


        public final void setPassword(String password) {

        }


        public final String getNickname() {

        }


        public final void setNickname(String nickname) {

        }


        public final int getUserScore() {

        }


        public final void setUserScore(int userScore) {

        }


        public final DeckModel getUserAllDecks() {

        }


        public final void addDeck(DeckModel deck) {

        }


        public final void changeUserScore(int userScore) {

        }


        public final void changePassword(String newPassword) {

        }


        public final void changeNickname(String nickname) {

        }


        public final void decreaseUserCard(int amount) {

        }


        public final void increaseUserCard(int amount) {

        }


        public final void changeUserCoin(int amount) {

        }


        public final void setActiveDeck(String deckName) {

        }


        public final String getActiveDeck() {

        }


        public final void deleteDeck(String deckName) {

        }


        public final String getCurrentMenu() {

        }


        public final void setCurrentMenu(String currentMenu) {

        }


        public final void setOnlineUser(String onlineUser) {

        }


        public final String getOnlineUser() {

        }


        public final UserModel getUserByUsername(String username) {

        }


        public final boolean isRepeatedUsername(String username) {

        }


        public final boolean isRepeatedNickname(String nickname) {

        }
    }

}
