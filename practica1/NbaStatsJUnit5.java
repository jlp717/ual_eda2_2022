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
		
		ArrayList<Player> arrayADevolver;
		String salidaEsperada = "[Player [Name = Wilt Chamberlain*| Score=785| Teams=[PHW, SFW, TOT, PHI, LAL]| Positions=[C], " +
				"Player [Name = DeMar DeRozan| Score=820| Teams=[TOR]| Positions=[SG], " + 
				"Player [Name = Damian Lillard| Score=825| Teams=[POR]| Positions=[PG], " +
				"Player [Name = Kevin Durant| Score=864| Teams=[SEA, OKC, GSW]| Positions=[SG, SF], " +
				"Player [Name = Anthony Davis| Score=894| Teams=[NOH, NOP]| Positions=[PF, C], " +
				"Player [Name = Russell Westbrook| Score=931| Teams=[OKC]| Positions=[PG], " +
				"Player [Name = Karl-Anthony Towns| Score=965| Teams=[MIN]| Positions=[C], " +
				"Player [Name = Stephen Curry| Score=975| Teams=[GSW]| Positions=[PG], " +
				"Player [Name = James Harden| Score=994| Teams=[OKC, HOU]| Positions=[SG, PG], " + 
				"Player [Name = LeBron James| Score=1031| Teams=[CLE, MIA]| Positions=[SG, SF, PF]]";
		
		DyVSolution.cargarArchivo("C:\\WORKSPACES\\EDA_2022\\practica_1\\NbaStats.txt");
		arrayADevolver = DyVSolution.diezMejoresJugadores();
		//System.out.println("\nArray a devolver: " + arrayADevolver);
		//System.out.println("Salida Esperada: " + salidaEsperada);
		assertEquals(salidaEsperada, arrayADevolver.toString());
	}
	
	@Test
	public void testB_ComprobarTop10Jugadores_Reverse() {
		
		System.out.println("\n***TOP 10 MEJORES JUGADORES CON REVERSE***");
		
		ArrayList<Player> arrayADevolver;
		String salidaEsperada = "[Player [Name = LeBron James| Score=1031| Teams=[CLE, MIA]| Positions=[SG, SF, PF], " +
				"Player [Name = James Harden| Score=994| Teams=[OKC, HOU]| Positions=[SG, PG], " +
				"Player [Name = Stephen Curry| Score=975| Teams=[GSW]| Positions=[PG], " +
				"Player [Name = Karl-Anthony Towns| Score=965| Teams=[MIN]| Positions=[C], " +
				"Player [Name = Russell Westbrook| Score=931| Teams=[OKC]| Positions=[PG], " +
				"Player [Name = Anthony Davis| Score=894| Teams=[NOH, NOP]| Positions=[PF, C], " +
				"Player [Name = Kevin Durant| Score=864| Teams=[SEA, OKC, GSW]| Positions=[SG, SF], " +
				"Player [Name = Damian Lillard| Score=825| Teams=[POR]| Positions=[PG], " + 
				"Player [Name = DeMar DeRozan| Score=820| Teams=[TOR]| Positions=[SG], " +
				"Player [Name = Wilt Chamberlain*| Score=785| Teams=[PHW, SFW, TOT, PHI, LAL]| Positions=[C]]";
		
		DyVSolution.cargarArchivo("C:\\WORKSPACES\\EDA_2022\\practica_1\\NbaStats.txt");
		arrayADevolver = DyVSolution.diezMejoresJugadores();
		Collections.reverse(arrayADevolver); //en vez de sacarlo de menor a mayor score, lo sacamos cambiado de sentido
		//System.out.println("\nArray a devolver: " + arrayADevolver);
		//System.out.println("Salida Esperada: " + salidaEsperada);
		assertEquals(salidaEsperada, arrayADevolver.toString());
	}

}
