package practica_2_codigo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class MSTTest {
	
	private static String ruta = System.getProperty("user.dir") + File.separator + "src" + File.separator + "org" + File.separator +
			"eda2" + File.separator + "practica02" + File.separator;

	@Test
	public void ComprobarSalidaPrim() {
		
		MST proceso = new MST (ruta + "graphPrimKruskal.txt", "A");
		
		List<Arista> arrayPrim = proceso.prim();
		
		ArrayList<Arista> arrayEsperado = new ArrayList<Arista>();
		arrayEsperado.add(new Arista("A", "C", 5));
		arrayEsperado.add(new Arista("C", "E", 17));
		arrayEsperado.add(new Arista("E", "B", 13));
		arrayEsperado.add(new Arista("C", "D", 21));
		arrayEsperado.add(new Arista("D", "F", 9));

		assertEquals(arrayEsperado.toString(), arrayPrim.toString());
	}
	
	@Test
	public void ComprobarSalidaPrimPQ() {
		
		MST proceso = new MST (ruta + "graphPrimKruskal.txt", "A");
		
		List<Arista> arrayPrimPQ = proceso.primPQ();
		
		ArrayList<Arista> arrayEsperado = new ArrayList<Arista>();
		arrayEsperado.add(new Arista("A", "C", 5));
		arrayEsperado.add(new Arista("C", "E", 17));
		arrayEsperado.add(new Arista("E", "B", 13));
		arrayEsperado.add(new Arista("C", "D", 21));
		arrayEsperado.add(new Arista("D", "F", 9));

		assertEquals(arrayEsperado.toString(), arrayPrimPQ.toString());
	}
	
	@Test
	public void ComprobarSalidaKruskal() {
		
		MST proceso = new MST (ruta + "graphPrimKruskal.txt", "A");
		
		List<Arista> arrayKruskal = proceso.kruskal();
		
		ArrayList<Arista> arrayEsperado = new ArrayList<Arista>();
		arrayEsperado.add(new Arista("B", "E", 13));
		arrayEsperado.add(new Arista("C", "A", 5));
		arrayEsperado.add(new Arista("D", "C", 21));
		arrayEsperado.add(new Arista("E", "C", 17));
		arrayEsperado.add(new Arista("F", "D", 9));

		assertEquals(arrayEsperado.toString(), arrayKruskal.toString());
	}
	
	@Test
	public void ComprobarMismoCoste() {
		MST proceso = new MST (ruta + "graphPrimKruskal.txt", "A");
		
		List<Arista> arrayPrim = proceso.prim();
		List<Arista> arrayPrimPQ = proceso.primPQ();
		List<Arista> arrayKruskal = proceso.kruskal();
		
		ContarCostes costesPrim = new ContarCostes(arrayPrim);
		ContarCostes costesPrimPQ = new ContarCostes(arrayPrimPQ);
		ContarCostes costesKruskal = new ContarCostes(arrayKruskal);

		assertTrue((costesPrim.getPrecioTotal() == costesPrimPQ.getPrecioTotal()) && (costesPrimPQ.getPrecioTotal() == costesKruskal.getPrecioTotal()));
	}
	
//	@Test
//	public void ArchivoIncorrecto(){
//		try {
//			MST proceso = new MST (ruta + "graphPrimKruskal.txt", "A");
//			fail("Tendría que haber saltado una excepción. Inténtalo de nuevo.");
//		} catch (Exception ex) {
//			assertEquals(ex.getMessage(), "Error al cargar el archivo. El sistema no puede encontrar el archivo especificado.");
//		}
//	}
	
	
	
	@Test
	public void PrimConOrigenEquivocado(){
		MST proceso = new MST (ruta + "graphPrimKruskal.txt", "1");
		try {
			proceso.prim();
			fail("Tendría que haber saltado una excepción. Inténtalo de nuevo.");
		} catch (Exception ex) {
			assertEquals(ex.getMessage(), "No puede ser nulo.");
		}
	}
	
	@Test
	public void PrimPQConOrigenEquivocado(){
		MST proceso = new MST (ruta + "graphPrimKruskal.txt", "1");
		try {
			proceso.primPQ();
			fail("Tendría que haber saltado una excepción. Inténtalo de nuevo.");
		} catch (Exception ex) {
			assertEquals(ex.getMessage(), "No puede ser nulo.");
		}
	}
	
	@Test
	public void KruskalConOrigenEquivocado(){
		MST proceso = new MST (ruta + "graphPrimKruskal.txt", "1");
		try {
			proceso.kruskal();
			fail("Tendría que haber saltado una excepción. Inténtalo de nuevo.");
		} catch (Exception ex) {
			assertEquals(ex.getMessage(), "No puede ser nulo.");
		}
	}
}
