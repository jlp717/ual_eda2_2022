package practica_1_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.Test;
import practica_1.*;

public class NbaStatsJUnit5 {

	private static String ruta = System.getProperty("user.dir") + File.separator + "src" + File.separator;

	@Test
	public void testA_ComprobarTop10Jugadores() {

		System.out.println("***TOP 10 MEJORES JUGADORES CON ORDEN NORMAL***");
		DYV.cargarArchivo(ruta + "NbaStats.txt");
		
		ArrayList<Player> arrayMetodo = new ArrayList<Player>();
		DYV.n = 10;
		arrayMetodo = DYV.diezMejores();
		
		ArrayList<Player> arrayEsperado = new ArrayList<Player>();
		arrayEsperado.add(new Player("Wilt Chamberlain*", "SFW, PHI, LAL, TOT, PHW", "C", 1153));
		arrayEsperado.add(new Player("Kareem Abdul-Jabbar*", "MIL, LAL", "C", 1076));
		arrayEsperado.add(new Player("Michael Jordan*", "CHI, WAS", "SF, SG", 1075));
		arrayEsperado.add(new Player("George Gervin*", "SAS, CHI", "SF, SG", 1059));
		arrayEsperado.add(new Player("LeBron James", "MIA, CLE", "SF, SG, PF", 1034));
		arrayEsperado.add(new Player("Karl Malone*", "UTA, LAL", "PF", 1005));
		arrayEsperado.add(new Player("Karl-Anthony Towns", "MIN", "C", 965));
		arrayEsperado.add(new Player("Kevin Durant", "OKC, GSW, SEA", "SF, SG", 935));
		arrayEsperado.add(new Player("Oscar Robertson*", "CIN, MIL", "PG", 925));
		arrayEsperado.add(new Player("Jerry West*", "LAL", "SG, PG", 854));
		
		//System.out.println("Array metodo: " + arrayMetodo);
		//System.out.println("Array esperado: " + arrayEsperado);
		assertEquals(arrayEsperado.toString(), arrayMetodo.toString());
	}


	@Test
	public void testB_ComprobarTop10Jugadores() {

		System.out.println("\n***TOP 10 MEJORES JUGADORES CON REVERSE***");
		DYV.n = 10;
		DYV.cargarArchivo(ruta + "NbaStats.txt");
		
		ArrayList<Player> arrayMetodo = new ArrayList<Player>();
		arrayMetodo = DYV.diezMejores();

		ArrayList<Player> arrayEsperado = new ArrayList<Player>();
		arrayEsperado.add(new Player("Jerry West*", "LAL", "SG, PG", 854));
		arrayEsperado.add(new Player("Oscar Robertson*", "CIN, MIL", "PG", 925));
		arrayEsperado.add(new Player("Kevin Durant", "OKC, GSW, SEA", "SF, SG", 935));
		arrayEsperado.add(new Player("Karl-Anthony Towns", "MIN", "C", 965));
		arrayEsperado.add(new Player("Karl Malone*", "UTA, LAL", "PF", 1005));
		arrayEsperado.add(new Player("LeBron James", "MIA, CLE", "SF, SG, PF", 1034));
		arrayEsperado.add(new Player("George Gervin*", "SAS, CHI", "SF, SG", 1059));
		arrayEsperado.add(new Player("Michael Jordan*", "CHI, WAS", "SF, SG", 1075));
		arrayEsperado.add(new Player("Kareem Abdul-Jabbar*", "MIL, LAL", "C", 1076));
		arrayEsperado.add(new Player("Wilt Chamberlain*", "SFW, PHI, LAL, TOT, PHW", "C", 1153));

		Collections.reverse(arrayMetodo); // en vez de sacarlo de menor a mayor score, lo sacamos cambiado de orden
		//System.out.println("Array metodo: " + arrayMetodo);
		//System.out.println("Array esperado: " + arrayEsperado);
		assertEquals(arrayEsperado.toString(), arrayMetodo.toString());
	}

	@Test
	public void testMejorJugadorTop10() {
		DYV.cargarArchivo(ruta + "NbaStats.txt");
		DYV.n = 1;
		ArrayList<Player> prueba = DYV.diezMejores();
		assertEquals("Wilt Chamberlain*", prueba.get(0).getPlayerName());
	}

	@Test
	public void testPeorJugadorTop10() {
		DYV.cargarArchivo(ruta+"NbaStats.txt");
		DYV.n = 10;
		ArrayList<Player> prueba = DYV.diezMejores();
		assertTrue(prueba.size()==10);
		assertEquals("Jerry West*",prueba.get(9).getPlayerName());
	}
	
	@Test
	public void testArchivoSinDatos() {
		DYV.cargarArchivo(ruta + "ArchivoSinDatos.txt");
		DYV.n = 20;
		try {
			DYV.diezMejores();
			fail("Tendría que haber saltado una excepción. Inténtalo de nuevo.");
		} catch (RuntimeException ex) {
			assertEquals("No hay datos.", ex.getMessage());
		}
	}

}
