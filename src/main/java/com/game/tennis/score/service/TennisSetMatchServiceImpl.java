package com.game.tennis.score.service;

import com.game.tennis.score.pojo.PlayerPoint;
import com.game.tennis.score.pojo.ScoreCard;
import com.game.tennis.score.pojo.SetMatchScoreCard;
import com.game.tennis.score.pojo.SetMatchScoreInput;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TennisSetMatchServiceImpl implements SetMatchService {

    @Autowired private GameScoreService gameScoreService;

    Map<String, List<SetMatchScoreCard>> setScoreCardsMap = new HashMap<>();

    /**
     * Processing set match score via Individual game score
     * @param setMatchScoreInput
     * @return
     */
    @Override
    public List<SetMatchScoreCard> processSetMatchGamePoint(@NotNull SetMatchScoreInput setMatchScoreInput) {
        if (setMatchScoreInput.getPlayerPoints() == null ||
                setMatchScoreInput.getPlayerPoints().size() != 2) {
            throw new RuntimeException("Invalid arguments passed to process");
        }

        List<SetMatchScoreCard> setScoreCards = new ArrayList<>();
        // Build this When Set Started/First Match Started
        if (!setScoreCardsMap.containsKey(setMatchScoreInput.getSetMatchName())) {
            List<ScoreCard> gameStart = gameScoreService.processGameScore(setMatchScoreInput);
            setScoreCards = buildSetScoreCards(gameStart,
                    setMatchScoreInput.getSetMatchName());
        } else {
            setScoreCards = setScoreCardsMap.get(setMatchScoreInput.getSetMatchName());
            if (tieBreakActivated(setScoreCards)) {
                updateTieBreakScore(setScoreCards, setMatchScoreInput);
                return executeTieBreakRules(setScoreCards);
            } else {
                // Process Current Game Points.
                List<ScoreCard> gameResult = gameScoreService.processGameScore(setMatchScoreInput);
                if (getWinnerIfMatchOver(gameResult) != null ) {
                    ScoreCard winnerScoreCard = getWinnerIfMatchOver(gameResult);
                    updateSetScore(setScoreCards, winnerScoreCard);
                }
            }
        }
        return evaluateSetWinner(setScoreCards);
    }

    private boolean tieBreakActivated(List<SetMatchScoreCard> setScoreCards) {
        SetMatchScoreCard  playerOneScoreCard = setScoreCards.get(0);
        SetMatchScoreCard  playerTwoScoreCard = setScoreCards.get(1);

        return (playerOneScoreCard.getSetScore() == 6 && playerOneScoreCard.getSetScore() == 6);
    }


    /**
     * Update Tie-Break score based on current point a player acquired
     * @param setScoreCards
     * @param setMatchScoreInput
     * @return
     */
    private List<SetMatchScoreCard> updateTieBreakScore(List<SetMatchScoreCard> setScoreCards,
                                                        SetMatchScoreInput setMatchScoreInput) {

        PlayerPoint winPoint = setMatchScoreInput.getPlayerPoints().stream().filter(pp -> {
            return pp.isPointSecured();
        }).findFirst().orElse(null);

        if (winPoint == null) {
            throw new RuntimeException("Not a valid input for tie-break");
        }

        SetMatchScoreCard setMatchScoreCard = setScoreCards.stream().
                filter(sc-> sc.getPlayerID().
                        equalsIgnoreCase(winPoint.getPlayerID())).findFirst().get();

        setMatchScoreCard.setTieBreakScore(setMatchScoreCard.getTieBreakScore() + 1);

        return setScoreCards;
    }

    /**
     * Evaluate Set score followed by Winner,
     * if Set score is 6 for both players it will trigger Tie-break
     * @param setScoreCards
     * @return
     */
    private List<SetMatchScoreCard> evaluateSetWinner(List<SetMatchScoreCard> setScoreCards) {
        SetMatchScoreCard  playerOneScoreCard = setScoreCards.get(0);
        SetMatchScoreCard  playerTwoScoreCard = setScoreCards.get(1);

        if (playerOneScoreCard.getTieBreakScore() != null) {

        }

        if ((playerOneScoreCard.getSetScore() == 6 ||playerTwoScoreCard.getSetScore() == 6) &&
                Math.abs(playerOneScoreCard.getSetScore() - playerTwoScoreCard.getSetScore()) >= 2) {
            if (playerOneScoreCard.getSetScore().compareTo(playerTwoScoreCard.getSetScore()) > 1) {
                playerOneScoreCard.setWinner(true);
            } else {
                playerTwoScoreCard.setWinner(true);
            }
            return setScoreCards;
        }

        if (playerOneScoreCard.getSetScore() == 6 && playerOneScoreCard.getSetScore() == 6) {
            setScoreCards = activateTiBreak(setScoreCards);
        }

        if (playerOneScoreCard.getSetScore() == 7) {
            playerOneScoreCard.setWinner(true);
        }

        if (playerTwoScoreCard.getSetScore() == 7) {
            playerTwoScoreCard.setWinner(true);
        }
        return setScoreCards;
   }

    /**
     * Sets Tie-Break score for each player
     * @param setScoreCards
     * @return
     */
    private List<SetMatchScoreCard> activateTiBreak(List<SetMatchScoreCard> setScoreCards) {
        setScoreCards.stream().forEach(stc-> {
            stc.setTieBreakScore(0);
        });
        return setScoreCards;
    }

    /**
     * Execute Tie-Break rule send score cards back
      * @param setScoreCards
     * @return
     */
    private List<SetMatchScoreCard> executeTieBreakRules(List<SetMatchScoreCard> setScoreCards) {
        SetMatchScoreCard player1=  setScoreCards.get(0);
        SetMatchScoreCard player2=  setScoreCards.get(1);

        if (player1.getTieBreakScore().compareTo(6) >= 0 &&
                player1.getTieBreakScore() - player2.getTieBreakScore() >= 2) {
            player1.setWinner(true);
        } else if (player2.getTieBreakScore().compareTo(6) >= 0 &&
                player2.getTieBreakScore() - player1.getTieBreakScore() >= 2) {
            player2.setWinner(true);
        }

        return  setScoreCards;
    }

    private List<SetMatchScoreCard> updateSetScore(List<SetMatchScoreCard> setScoreCards, ScoreCard winnerScoreCard) {

        SetMatchScoreCard setMatchScoreCard = setScoreCards.stream().filter(sc-> {
            return sc.getPlayerID().equalsIgnoreCase(winnerScoreCard.getPlayerID());
        }).findFirst().get();

        setMatchScoreCard.setSetScore(setMatchScoreCard.getSetScore() + 1);

        return setScoreCards;
    }

    private List<SetMatchScoreCard> buildSetScoreCards(List<ScoreCard> gameResult, String setMatchName) {
        List<SetMatchScoreCard> setMatchScoreCards =  new ArrayList<>();
        gameResult.stream().forEach(scoreCard -> {
            setMatchScoreCards.add(new SetMatchScoreCard(scoreCard.getPlayerID(), scoreCard, 0));
        });

        setScoreCardsMap.put(setMatchName, setMatchScoreCards);

        return setMatchScoreCards;
    }

    private ScoreCard getWinnerIfMatchOver(List<ScoreCard> gameResult) {
        return gameResult.stream().filter(scoreCard -> {
           return scoreCard.isWinner();
        }).findFirst().orElse(null);
    }
}
