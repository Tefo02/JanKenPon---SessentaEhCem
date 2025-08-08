package br.edu.ifnmg.sld2.sessentaehcem;

/**
 * @author Stefano Lopes Delgado
 * @version 0.1
 * @since 0.1, Aug 08, 2025
 */
import io.github.guisso.jankenpon.AbstractPlayer;
import io.github.guisso.jankenpon.Move;
import java.util.Random;

public class SessentaEhCem 
        extends AbstractPlayer {

    private static final Random RANDOM = new Random();
    
    private Move myLastMove;
    private Move opponentLastMove; 
    private Double winRate;
    private Integer rounds;
    private Integer nonDefeat;
    
    
    @Override
    public String getDeveloperName() {
        return "Stefano Lopes Delgado";
    }

    private void calculateWinRate() {
        if (rounds != 0) {
            winRate =  nonDefeat.doubleValue() / rounds; 
        }     
        
    }
    
    private Move setup() {
        opponentLastMove = Move.NONE;
        rounds = 0;
        winRate = 0.0;
        nonDefeat = 0;
        return Move.PAPER;
    } 
    
    private void resolvePreviousMatch() {
        if (opponentLastMove == myLastMove || opponentLastMove == Move.NONE || myLastMove == null) {
            nonDefeat++;
            calculateWinRate();
            return;
        }

        switch (opponentLastMove) {
            case ROCK -> {
                if (myLastMove != Move.SCISSORS) nonDefeat++;
            }
            case PAPER -> {
                if (myLastMove != Move.ROCK) nonDefeat++;
            }
            case SCISSORS -> {
                if (myLastMove != Move.PAPER) nonDefeat++;
            }

        }

        calculateWinRate();
    }


    private Move getRandomMove() {
        Move[] moves = {Move.ROCK, Move.PAPER, Move.SCISSORS};
        return moves[RANDOM.nextInt(moves.length)];
    }

   
    @Override
    public Move makeMyMove(Move opponentPreviousMove) {
        opponentLastMove = opponentPreviousMove;

        if (opponentPreviousMove == null || opponentPreviousMove == Move.NONE) {
            return setup(); 
        }

        resolvePreviousMatch();

        Move myMove = Move.PAPER;

        if (rounds <= 10 || winRate >= 0.6) {
            myMove = switch (opponentPreviousMove) {
                case ROCK -> Move.PAPER;
                case PAPER -> Move.SCISSORS;
                case SCISSORS -> Move.ROCK;
                default -> Move.PAPER;
            };
        } else {
            myMove = getRandomMove();
        }

        myLastMove = myMove;
        rounds++;
        return myMove;
    }
}   
