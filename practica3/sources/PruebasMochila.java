package practica_3;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import org.junit.Test;

public class PruebasMochila {
	
//	@Test
//	public void ComprobarCargarArchivo() {
//		try {
//			Mochila prueba = new Mochila ("p04");
//		} catch (Exception excepcion) {
//			assertEquals(excepcion.getMessage(), "Error al cargar el archivo. El sistema no puede encontrar el archivo _c.txt.");
//		}
//	}
//	
	
	@Test
	public void ComprobarKnapsackInteger() {
		Mochila prueba = new Mochila ("p01");
		ArrayList<Objeto> expected = new ArrayList<>();
		expected.add(new Objeto("Objeto4", 39.0, 76.1689798466821));
		expected.add(new Objeto("Objeto8", 25.0, 83.3569025009662));
		expected.add(new Objeto("Objeto2", 13.0, 44.828880387670075));
		expected.add(new Objeto("Objeto6", 9.0, 52.33336343224318));
		expected.add(new Objeto("Objeto7", 7.0, 41.509603766651935));
		expected.add(new Objeto("Objeto9", 2.0, 2.1079736086844316));
		ArrayList<Objeto> resultado = prueba.knapsack();
		assertEquals(expected.toString(), resultado.toString());
	}
	
	@Test
	public void ComprobarKnapsackSinLimites() {
		Mochila prueba = new Mochila ("p04");
		ArrayList<Objeto> expected = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			expected.add(new Objeto("Objeto5", 3.25, 98.95524908438604));
		}
		ArrayList<Objeto> resultado = prueba.knapsackSinLimites();
		assertEquals(expected.toString(), resultado.toString());
	}
	
	@Test
	public void ComprobarKnapsackDouble() {
		Mochila prueba = new Mochila ("p04");
		ArrayList<Objeto> expected = new ArrayList<>();
		expected.add(new Objeto("Objeto10", 5.0, 60.03309111058198));
		expected.add(new Objeto("Objeto5", 3.25, 98.95524908438604));
		expected.add(new Objeto("Objeto9", 3.0, 74.95389829996067));
		expected.add(new Objeto("Objeto7", 3.0, 59.71474395202063));
		expected.add(new Objeto("Objeto4", 2.75, 46.947796548954024));
		ArrayList<Objeto> resultado = prueba.knapsack();
		assertEquals(expected.toString(), resultado.toString());
	}
	
	@Test
	public void ComprobarGreedyKnapsack() {
		Mochila prueba = new Mochila ("p03");
		ArrayList<Objeto> expected = new ArrayList<>();
		expected.add(new Objeto("Objeto3", 11.0, 23.0));
		expected.add(new Objeto("Objeto1", 12.0, 24.0));
		expected.add(new Objeto("Objeto4", 3.0, 5.625));
		ArrayList<Objeto> resultado = prueba.greedyKnapsack();
		assertEquals(expected.toString(), resultado.toString());
	}
	
}
