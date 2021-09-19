package com.game.tennis.score.service;

import com.game.tennis.score.dto.ScoreCard;
import com.game.tennis.score.dto.SetMatchScoreCard;
import com.game.tennis.score.dto.SetMatchScoreInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SetMatchServiceImpl implements SetMatchService {

    @Autowired private GameScoreService gameScoreService;

    Map<String, List<SetMatchScoreCard>> setScoreCardsMap = new HashMap<>();

    @Override
    public List<SetMatchScoreCard> processSetMatchGamePoint(SetMatchScoreInput setMatchScoreInput) {
        // Process Current Game Points.
        List<ScoreCard> gameResult = gameScoreService.processGameScore(setMatchScoreInput);
        List<SetMatchScoreCard> setScoreCards = new ArrayList<>();
        // Build this When Set Started/First Match Started
        if (!setScoreCardsMap.containsKey(setMatchScoreInput.getSetMatchName())) {
            setScoreCards = buildSetScoreCards(gameResult,
                    setMatchScoreInput.getSetMatchName());
        } else {
            setScoreCards = setScoreCardsMap.get(setMatchScoreInput.getSetMatchName());
        }

        ScoreCard winnerScoreCard = getWinnerIfMatchOver(gameResult);

        if (getWinnerIfMatchOver(gameResult) != null ) {
            updateSetScore(setScoreCards, winnerScoreCard);
        }
        return evaluateSetWinner(setScoreCards);
    }

    private List<SetMatchScoreCard> evaluateSetWinner(List<SetMatchScoreCard> setScoreCards) {
        SetMatchScoreCard  playerOneScoreCard = setScoreCards.get(0);
        SetMatchScoreCard  playerTwoScoreCard = setScoreCards.get(1);

        if ((playerOneScoreCard.getSetScore() == 6 ||playerTwoScoreCard.getSetScore() == 6) &&
                Math.abs(playerOneScoreCard.getSetScore() - playerTwoScoreCard.getSetScore()) >= 2) {
            if (playerOneScoreCard.getSetScore().compareTo(playerTwoScoreCard.getSetScore()) > 1) {
                playerOneScoreCard.setWinner(true);
            } else {
                playerTwoScoreCard.setWinner(true);
            }
            return setScoreCards;
        }

        if (playerOneScoreCard.getSetScore() == 7) {
            playerOneScoreCard.setWinner(true);
        }

        if (playerTwoScoreCard.getSetScore() == 7) {
            playerTwoScoreCard.setWinner(true);
        }
        return setScoreCards;
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
