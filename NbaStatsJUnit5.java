package practica1_test;

import static org.junit.Assert.*;

import org.junit.Test;

import practica_1.DyVSolution;
import practica_1.Player;

import java.util.ArrayList;
import java.util.Collections;

public class NbaStatsJUnit5 {
	
	@Test
	public void testA_ComprobarTop10Jugadores() {
		
		ArrayList<Player> arrayADevolver;
		String salidaEsperadaArray = "[Player [Name = Wilt Chamberlain*| Score=785], "+
				"Player [Name = DeMar DeRozan| Score=820], "+
				"Player [Name = Damian Lillard| Score=825], "+
				"Player [Name = Kevin Durant| Score=864], "+
				"Player [Name = Anthony Davis| Score=894], "+
				"Player [Name = Russell Westbrook| Score=931], "+
				"Player [Name = Karl-Anthony Towns| Score=965], "+
				"Player [Name = Stephen Curry| Score=975], "+
				"Player [Name = James Harden| Score=994], "+
				"Player [Name = LeBron James| Score=1031]"
				+ "]";
		
		DyVSolution.cargarArchivo("C:\\WORKSPACES\\EDA_2022\\practica_1\\NbaStats.txt");
		arrayADevolver = DyVSolution.diezMejoresJugadores();
		assertEquals(salidaEsperadaArray, arrayADevolver.toString());
	}
	
	@Test
	public void testB_ComprobarTop10Jugadores_Reverse() {
		ArrayList<Player> arrayADevolver;
		String salidaEsperadaArray = "[Player [Name = LeBron James| Score=1031], " +
				"Player [Name = James Harden| Score=994], " +
				"Player [Name = Stephen Curry| Score=975], " +
				"Player [Name = Karl-Anthony Towns| Score=965], " +
				"Player [Name = Russell Westbrook| Score=931], " + 
				"Player [Name = Anthony Davis| Score=894], " +
				"Player [Name = Kevin Durant| Score=864], " +
				"Player [Name = Damian Lillard| Score=825], " +
				"Player [Name = DeMar DeRozan| Score=820], " + 
				"Player [Name = Wilt Chamberlain*| Score=785]"
				+ "]";
		
		DyVSolution.cargarArchivo("C:\\WORKSPACES\\EDA_2022\\practica_1\\NbaStats.txt");
		arrayADevolver = DyVSolution.diezMejoresJugadores();
		Collections.reverse(arrayADevolver); //en vez de sacarlo de menor a mayor score, lo sacamos cambiado de sentido
		assertEquals(salidaEsperadaArray, arrayADevolver.toString());
	}

}
