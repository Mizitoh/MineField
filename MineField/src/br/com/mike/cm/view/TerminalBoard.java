package br.com.mike.cm.view;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.mike.cm.exception.ExplosionException;
import br.com.mike.cm.exception.GetOutException;
import br.com.mike.cm.model.Board;

public class TerminalBoard {
	
	private Board board;
	private Scanner keyboard = new Scanner(System.in);
	
	public TerminalBoard(Board board) {
		this.board = board;
		
		gameExecute();
	}

	private void gameExecute() {
		try {
			boolean continues = true;
			
			while (continues) {
				
				gameLoop();
				
				System.out.println("Do you wanna play again? (S/n)");
				String answer = keyboard.nextLine();
				if("n".equalsIgnoreCase(answer)) {
					continues = false;
				}else {
					board.reestarter();
				}
			}
		} catch (GetOutException e) {
			System.out.println("Good Bye!!");
		}finally {
			keyboard.close();
		}
	}
	
	private void gameLoop() {
		try {
			
			while(!board.objectiveSuccess()) {
				System.out.println(board.toString());
				String typed = getTypedValue("Type value of the lines and columns (x, y)\nThe max value to lines 'x' and columns 'y' is '5'");
				
				Iterator<Integer> xy = Arrays.stream(typed.split(",")).
						map(elements -> Integer.parseInt(elements.trim())).
						iterator();
				
				typed = getTypedValue("1 to open, 2 to mark off");
				
				if("1".equals(typed)) {
					board.open(xy.next(), xy.next());
				} else if ("2".equals(typed)) {
					board.mark(xy.next(), xy.next());
				}
				
			}
			System.out.println(board);
			System.out.println("You Win!");
		} catch (ExplosionException e) {
			System.out.println(board);
			System.out.println("Loser!");
		}
	}
	
	public String getTypedValue(String text) {
		System.out.print(text);
		String typed = keyboard.nextLine();
		
		if("sair".equalsIgnoreCase(typed)) {
			throw new GetOutException();
		}
		return typed;
	}
	
	

}
