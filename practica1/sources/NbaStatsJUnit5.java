package practica1_test;

import static org.junit.Assert.*;
import org.junit.Test;
import practica_1.*;
import java.util.ArrayList;
import java.util.Collections;

public class NbaStatsJUnit5 {
	
	@Test
	public void testA_ComprobarTop10Jugadores() {
		
		System.out.println("***TOP 10 MEJORES JUGADORES CON ORDEN NORMAL***");
		
		DyVSolution.cargarArchivo("C:\\WORKSPACES\\EDA_2022\\practica_1\\NbaStats.txt");
		ArrayList<Player> arrayADevolver = new ArrayList<Player>();
		arrayADevolver = DyVSolution.diezMejoresJugadores();
		
		ArrayList<Player> arrayEsperado = new ArrayList<Player>();
		arrayEsperado.add(new Player ("Wilt Chamberlain*", "PHW, SFW, TOT, PHI, LAL", "C", 785));
		arrayEsperado.add(new Player ("DeMar DeRozan", "TOR", "SG", 820));
		arrayEsperado.add(new Player ("Damian Lillard", "POR", "PG", 825));
		arrayEsperado.add(new Player ("Kevin Durant", "SEA, OKC, GSW", "SG, SF", 864));
		arrayEsperado.add(new Player ("Anthony Davis", "NOH, NOP", "PF, C", 894));
		arrayEsperado.add(new Player ("Russell Westbrook", "OKC", "PG", 931));
		arrayEsperado.add(new Player ("Karl-Anthony Towns", "MIN", "C", 965));
		arrayEsperado.add(new Player ("Stephen Curry", "GSW", "PG", 975));
		arrayEsperado.add(new Player ("James Harden", "OKC, HOU", "SG, PG", 994));
		arrayEsperado.add(new Player ("LeBron James", "CLE, MIA", "SG, SF, PF", 1031));
		
		//System.out.println("\nArray a devolver: " + arrayADevolver);
		//System.out.println("\nArray a devolver: " + arrayEsperado);
		assertEquals(arrayADevolver.toString(), arrayEsperado.toString());
	}
	
	@Test
	public void testB_ComprobarTop10Jugadores() {
		
		System.out.println("\n***TOP 10 MEJORES JUGADORES CON REVERSE***");
		
		DyVSolution.cargarArchivo("C:\\WORKSPACES\\EDA_2022\\practica_1\\NbaStats.txt");
		ArrayList<Player> arrayADevolver = new ArrayList<Player>();
		arrayADevolver = DyVSolution.diezMejoresJugadores();
		
		ArrayList<Player> arrayEsperado = new ArrayList<Player>();
		arrayEsperado.add(new Player ("LeBron James", "CLE, MIA", "SG, SF, PF", 1031));
		arrayEsperado.add(new Player ("James Harden", "OKC, HOU", "SG, PG", 994));
		arrayEsperado.add(new Player ("Stephen Curry", "GSW", "PG", 975));
		arrayEsperado.add(new Player ("Karl-Anthony Towns", "MIN", "C", 965));
		arrayEsperado.add(new Player ("Russell Westbrook", "OKC", "PG", 931));
		arrayEsperado.add(new Player ("Anthony Davis", "NOH, NOP", "PF, C", 894));
		arrayEsperado.add(new Player ("Kevin Durant", "SEA, OKC, GSW", "SG, SF", 864));
		arrayEsperado.add(new Player ("Damian Lillard", "POR", "PG", 825));
		arrayEsperado.add(new Player ("DeMar DeRozan", "TOR", "SG", 820));
		arrayEsperado.add(new Player ("Wilt Chamberlain*", "PHW, SFW, TOT, PHI, LAL", "C", 785));
		
		Collections.reverse(arrayADevolver); //en vez de sacarlo de menor a mayor score, lo sacamos cambiado de sentido
		
		//System.out.println("\nArray a devolver: " + arrayADevolver);
		//System.out.println("\nArray a devolver: " + arrayEsperado);
		assertEquals(arrayEsperado.toString(), arrayADevolver.toString());
	}
}
