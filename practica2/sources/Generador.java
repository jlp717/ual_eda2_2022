package practica_2_codigo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Generador {

	private static String ruta = System.getProperty("user.dir") + File.separator + "src" + File.separator;
	
	private static ArrayList<AristaGenerador> resultadoFinal = new ArrayList<AristaGenerador>();
	private static ArrayList<String> temp = new ArrayList<String>();

	
	public static void main(String[] args) throws FileNotFoundException {
		long startNano = System.nanoTime();
		long startMili = System.currentTimeMillis();
		grafoNoDirigido(5000,"Prueba01.txt");
		long endNano = System.nanoTime();
		long endMili = System.currentTimeMillis();
		System.out.println("Tiempo de ejecución: " + (endNano-startNano) + " nanosegundos. || " + (endMili-startMili) + " milisegundos.");
	}
	
	private static void grafoNoDirigido(int nVertices, String nombreArchivo) throws FileNotFoundException {
		//CONFIGURACION INICIAL
		Random random = new Random();
		PrintWriter pw = new PrintWriter (new File (ruta + nombreArchivo));
		pw.println("0");
		pw.println(nVertices);
		LinkedList<Arista> aux = new LinkedList<Arista>();
		int nAristas = nVertices*(random.nextInt(2)+1);
		
		for (int i = 0; i < nVertices; i++) {
			pw.println(i);
		}
		
		for (int i = 0; i < nVertices; i++) {
			for (int j = i+1; j < nVertices; j++) {
				int pesoAleatorio = random.nextInt(100)+1; //Se castea a entero para que el peso no tenga decimales (OPCIONAL)
				if(i+1 == j) {//Si son numeros consecutivos, se agrega
					resultadoFinal.add(new AristaGenerador (i, j, pesoAleatorio));
				}else {//Si no, pasan a la lista auxiliar de aristas restantes
					aux.add(new Arista(i+"", j+"", pesoAleatorio));
				}
			}
		}
		
		pw.println(nAristas);
		
		int elementosAristas = 0;
		while(elementosAristas < nAristas) {
			int desde = random.nextInt(nVertices);
			int hasta = random.nextInt(nVertices);
			while ((desde == hasta) || contieneArista(temp, resultadoFinal, desde, hasta)) {
				desde = random.nextInt(nVertices);
				hasta = random.nextInt(nVertices);
			} 
			int pesoAleatorio = random.nextInt(100)+1;
			
			resultadoFinal.add(new AristaGenerador (desde, hasta, pesoAleatorio));
			elementosAristas++;
		}
		
		for (AristaGenerador a : resultadoFinal) {
			pw.println(a);
		}
		
		pw.close();
	}
	
	public static boolean contieneArista(ArrayList<String> t, ArrayList<AristaGenerador> c, int desde, int hasta ) {
    	String myStr2 = desde + " " + hasta;
    	String myStr3 = hasta + " " + desde;
		if(t.contains(myStr2) || t.contains(myStr3)) {
			return true;
		} else {
			for(AristaGenerador o : c) {
				String myStr1 = o.getOrigen() + " " + o.getDestino();
				if (myStr1.equals(myStr2)) {
					t.add(myStr2);
					return true;
				} else if(myStr1.equals(myStr3)) {
					t.add(myStr3);
					return true;
				}
			}
		}
		t.add(myStr2);
	    return false;
	}
}