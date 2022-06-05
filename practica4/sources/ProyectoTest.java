package practica_4.test;

import static org.junit.Assert.assertEquals;
import java.io.File;
import org.junit.Test;
import practica_4.ProyectoFinal;

public class ProyectoTest {
	
	private static String ruta = System.getProperty("user.dir") + File.separator + "src" + File.separator + "practica_4" + File.separator + "test" + File.separator;


	@Test
	public void ComprobarBackTracking() {
		
		ProyectoFinal proceso = new ProyectoFinal (ruta + "graphPrimKruskal.txt", "A");
		
		proceso.BackTracking();
		
		String esperado = "{[A, C, B, E, F, D, A]=98.0, [A, B, E, C, F, D, A]=110.0, [A, C, D, F, E, B, A]=94.0, [A, D, F, E, C, B, A]=118.0, [A, D, F, E, B, C, A]=98.0, [A, B, C, E, F, D, A]=118.0, [A, D, F, C, E, B, A]=110.0, [A, B, E, F, D, C, A]=94.0, [A, D, C, F, E, B, A]=130.0, [A, B, E, F, C, D, A]=130.0}";

		assertEquals(esperado, proceso.getSolucionFinal().toString());
	}
	
	@Test
	public void ComprobarBackTrackingCantidadSoluciones() {
		
		ProyectoFinal proceso = new ProyectoFinal (ruta + "graphPrimKruskal.txt", "A");
		
		proceso.BackTracking();

		assertEquals(10, ProyectoFinal.tamArray);
	}
	
	@Test
	public void ComprobarBranchAndBound() {
		
		ProyectoFinal proceso = new ProyectoFinal (ruta + "graphPrimKruskal.txt", "A");
		
		String esperado = "[A, C, D, F, E, B, A]";

		assertEquals(esperado, proceso.TSPBaB("A").toString());
	}
	
	@Test
	public void ComprobarBranchAndBoundCostes() {
		
		ProyectoFinal proceso = new ProyectoFinal (ruta + "graphPrimKruskal.txt", "A");
		
		proceso.TSPBaB("A");

		assertEquals("94.0", ProyectoFinal.costes+"");
	}
}
