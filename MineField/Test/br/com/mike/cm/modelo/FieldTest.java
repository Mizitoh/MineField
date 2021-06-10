	package br.com.mike.cm.modelo;

	import static org.junit.jupiter.api.Assertions.*;

	import org.junit.jupiter.api.BeforeEach;
	import org.junit.jupiter.api.Test;

import br.com.mike.cm.exception.ExplosionException;
import br.com.mike.cm.model.Field;


	public class FieldTest {
		
		public static final String ANSI_RED = "\u001B[31m";
		private Field field;
		
		@BeforeEach
		void startField() {
			field = new Field(3, 3);
		}
		
		@Test
		void testNeighborDistanceLeft() {
			Field neighbor = new Field (3, 2);
			boolean result = field.addNeighbor(neighbor);
			
			assertTrue(result);
		}
		
		@Test
		void testNeighborDistanceRight() {
			Field neighbor = new Field (3, 4);
			boolean result = field.addNeighbor(neighbor);
			
			assertTrue(result);
		}
		
		@Test
		void testNeighborDistanceUp() {
			Field neighbor = new Field (2, 3);
			boolean result = field.addNeighbor(neighbor);
			
			assertTrue(result);
		}
		
		@Test
		void testNeighborDistanceDown() {
			Field neighbor = new Field (4, 3);
			boolean result = field.addNeighbor(neighbor);
			
			assertTrue(result);
		}
		
		@Test
		void testNotNeighbor() {
			Field neighbor = new Field (1, 1);
			boolean result = field.addNeighbor(neighbor);
			
			assertFalse(result);
		}

		@Test
		void testValueOfAttributeMarked() {
			assertFalse(field.isMarked());
		}
		
		@Test
		void testMarkAlternation() {
			field.changeMark();
			assertTrue(field.isMarked());
		}
		
		@Test
		void testMarkAlternationTwoLayers() {
			field.changeMark();
			field.changeMark();
			assertFalse(field.isMarked());
		}
		
		@Test
		void testOpenNotMinedNotMarked() {
			assertTrue(field.open());
		}
		
		@Test
		void testOpenNotMinedButMarked() {
			field.changeMark();
			assertFalse(field.open());
		}
		
		@Test
		void testOpenNotMinedMarked() {
			field.changeMark();
			field.hasMine();
			assertFalse(field.open());
		}
		
		@Test
		void testOpenMinedNotMarked() {
			field.hasMine();
			
			assertThrows(ExplosionException.class, () ->{
				field.open();
			});
			
			assertFalse(field.open());
		}
		
		@Test
		void testOpenWithNeighbors() {
			
			Field field11 = new Field(1, 1);
			Field field22 = new Field(2, 2);
			
			field22.addNeighbor(field11);
			field.addNeighbor(field22);
			field.open();

			assertTrue(field22.isItOpen() && field11.isItOpen());
		}
		
		@Test
		void testOpenWithNeighborsMined() {
			
			Field field11 = new Field(1, 1);
			Field field12 = new Field(1, 1);
			Field field22 = new Field(2, 2);
			
			field12.hasMine();
			field22.addNeighbor(field11);
			field22.addNeighbor(field12);
			
			field.addNeighbor(field22);
			field.open();

			assertTrue(field22.isItOpen() && field11.isItClosed());
		}
		
		@Test
		void testReestart() {
			assertFalse(!field.isItOpen() && !field.isItMined() && !field.isMarked());
		}
		
		@Test
		void testToString() {
				if (field.isMarked()) {
				assertTrue(field.toString().equals("X"));
			} else if (field.isItOpen() && field.isItMined()) {
				assertTrue(field.toString().equals(ANSI_RED + "*"));
			}  else if (field.isItOpen()) {
				assertTrue(field.toString().equals(" "));
			} else {
				field.toString().equals("?");
			}
		}
}


