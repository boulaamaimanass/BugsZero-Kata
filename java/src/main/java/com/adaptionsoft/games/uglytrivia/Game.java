package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Game {

	public static final String POP_QUESTION = "Pop Question ";
	public static final String SCIENCE_QUESTION = "Science Question ";
	public static final String SPORTS_QUESTION = "Sports Question ";
	public static final String ROCK_QUESTION = "Rock Question ";
	public static final String IS_THE_CURRENT_PLAYER = " is the current player";
	public static final String THEY_HAVE_ROLLED_A = "They have rolled a ";
	public static final String IS_GETTING_OUT_OF_THE_PENALTY_BOX = " is getting out of the penalty box";
	public static final String IS_NOT_GETTING_OUT_OF_THE_PENALTY_BOX = " is not getting out of the penalty box";

	private List<Player> players;
	private Player currentPlayer;


	private LinkedList popQuestions;
	private LinkedList scienceQuestions;
	private LinkedList sportsQuestions;
	private LinkedList rockQuestions;

	boolean isGettingOutOfPenaltyBox;

	public  Game(){
		this.players = new ArrayList();
		initQuestions(50);
	}

	private void initQuestions(int numberOfQuestions) {
		for (int i = 0; i < numberOfQuestions; i++) {
			popQuestions.add(POP_QUESTION + i);
			scienceQuestions.add((SCIENCE_QUESTION + i));
			sportsQuestions.add((SPORTS_QUESTION + i));
			rockQuestions.add(ROCK_QUESTION + i);
		}

	}

	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	public boolean add(String playerName) {
		Player newPlayer = new Player(playerName);
		players.add(newPlayer);

		System.out.println(newPlayer.getPlayerName() + " was added");
		System.out.println("They are player number " + players.size());
		return true;
	}

	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		Optional<Player> currentPlayerOptional = getCurrentPlayer();
		if(!currentPlayerOptional.isPresent()){
			return;
		}
		Player currentPlayer = currentPlayerOptional.get();

		System.out.println(currentPlayer.getPlayerName() + IS_THE_CURRENT_PLAYER);
		System.out.println(THEY_HAVE_ROLLED_A + roll);

		if (currentPlayer.isInPenaltyBox()) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;

				System.out.println(currentPlayer.getPlayerName() + IS_GETTING_OUT_OF_THE_PENALTY_BOX);
				movePlayerAndAskQuestion(roll);
			} else {
				System.out.println(currentPlayer.getPlayerName() + IS_NOT_GETTING_OUT_OF_THE_PENALTY_BOX);
				isGettingOutOfPenaltyBox = false;
			}

		} else {

			movePlayerAndAskQuestion(roll);
		}

	}

	private void movePlayerAndAskQuestion(int roll) {

		places[currentPlayer] = places[currentPlayer] + roll;
		if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

		System.out.println(players.get(currentPlayer)
				+ "'s new location is "
				+ places[currentPlayer]);
		System.out.println("The category is " + currentCategory());
		askQuestion();
	}

	private void askQuestion() {
		if (currentCategory() == "Pop")
			System.out.println(popQuestions.removeFirst());
		if (currentCategory() == "Science")
			System.out.println(scienceQuestions.removeFirst());
		if (currentCategory() == "Sports")
			System.out.println(sportsQuestions.removeFirst());
		if (currentCategory() == "Rock")
			System.out.println(rockQuestions.removeFirst());
	}


	private String currentCategory() {
		if (places[currentPlayer] == 0) return "Pop";
		if (places[currentPlayer] == 4) return "Pop";
		if (places[currentPlayer] == 8) return "Pop";
		if (places[currentPlayer] == 1) return "Science";
		if (places[currentPlayer] == 5) return "Science";
		if (places[currentPlayer] == 9) return "Science";
		if (places[currentPlayer] == 2) return "Sports";
		if (places[currentPlayer] == 6) return "Sports";
		if (places[currentPlayer] == 10) return "Sports";
		return "Rock";
	}

	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				purses[currentPlayer]++;
				System.out.println(players.get(currentPlayer)
						+ " now has "
						+ purses[currentPlayer]
						+ " Gold Coins.");

				boolean winner = didPlayerWin();

				return winner;
			} else {
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				return true;
			}



		} else {

			System.out.println("Answer was corrent!!!!");
			purses[currentPlayer]++;
			System.out.println(players.get(currentPlayer)
					+ " now has "
					+ purses[currentPlayer]
					+ " Gold Coins.");

			boolean winner = didPlayerWin();
			currentPlayer++;
			if (currentPlayer == players.size()) currentPlayer = 0;

			return winner;
		}
	}

	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		Optional<Player> currentPlayer = getCurrentPlayer();
		if(currentPlayer.isPresent()){
			currentPlayer.get().setInPenaltyBox(true);
			goToNextPlayer();
		}

		return true;
	}

	private void goToNextPlayer() {
		currentPlayer = players.get(getIndexOfNextPlayer());
	}

	private int getIndexOfNextPlayer() {
		int indexOfCorrentPlayer = players.indexOf(currentPlayer);
		return (indexOfCorrentPlayer+1) % players.size();
	}

	private Optional<Player> getCurrentPlayer() {
		// to handle game without user
		if(players == null || players.isEmpty()){
			return Optional.empty();
		}
		if(currentPlayer == null ){
			currentPlayer = players.get(0);
		}

		return Optional.of(currentPlayer);
	}


	private boolean didPlayerWin() {
		return !(purses[currentPlayer] == 6);
	}


}

