package br.com.mike.cm;

import br.com.mike.cm.model.Board;
import br.com.mike.cm.view.TerminalBoard;

public class Application {
	
	public static void main(String[] args) {
		Board board = new Board(6, 6 , 9);
		
		new TerminalBoard(board);
	}

}
