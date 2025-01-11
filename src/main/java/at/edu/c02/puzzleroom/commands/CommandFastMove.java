package at.edu.c02.puzzleroom.commands;

import at.edu.c02.puzzleroom.GameBoard;
import at.edu.c02.puzzleroom.Player;
import at.edu.c02.puzzleroom.exceptions.PuzzleRoomException;
import at.edu.c02.puzzleroom.exceptions.PuzzleRoomInvalidArgumentsException;
import at.edu.c02.puzzleroom.exceptions.PuzzleRoomInvalidMoveException;

/**
 * This command allows the player to move up/down/left/right one step
 * Example usage: `move left`
 */
public class CommandFastMove implements Command {
    private String directions;

    public CommandFastMove(String[] arguments) throws PuzzleRoomException {

        directions = String.join("", arguments).replaceAll("\\s", "");
    }

    public void execute(GameBoard gameBoard) throws PuzzleRoomException {
        // The player handles all movement logic, we just parse the input and
        // call the correct function
        Player player = gameBoard.getPlayer();
        if(player == null) {
            throw new PuzzleRoomInvalidMoveException();
        }

        char[] direction = directions.toCharArray();

        for (int i = 0; i < direction.length; i++) {
                boolean success = switch (direction[i]) {
                    case 'l' -> player.moveLeft();
                    case 'r' -> player.moveRight();
                    case 'u' -> player.moveUp();
                    case 'd' -> player.moveDown();
                    default -> throw new PuzzleRoomInvalidArgumentsException();
                };


                if (success) {
                    // If the move was successful, automatically execute a show command
                    // to show the player the new state
                    CommandShow showCommand = new CommandShow();
                    showCommand.execute(gameBoard);
                } else {
                    // If the move was not successful - e.g. because the player moved
                    // into a wall, or already finished the maze, then throw an Exception
                    throw new PuzzleRoomInvalidMoveException();
                }
            }
        }
    }

