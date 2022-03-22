package practica_1_Agustin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import practica_1_Agustin.Player;

public class DYV {
    
    private static String ruta = System.getProperty("user.dir") + File.separator + "src" +
            File.separator + File.separator + "NbaStats.txt";
    
    private static ArrayList<Player> estadisticas;
    
    public static int n = 10;
    
    	public static void main(String [] args) {
    		cargarArchivo(ruta);
            long startNano = System.nanoTime();
            long startMili = System.currentTimeMillis();
            diezMejores();
            long endNano = System.nanoTime();
            long endMili = System.currentTimeMillis();
            System.out.println("\nTiempo de ejecuciónn: " + (endNano-startNano) + " nanosegundos. || " + (endMili-startMili) + " milisegundos.");
    	}
    

    public static ArrayList<Player> diezMejores() {
        if(estadisticas.size() == 0) {
            throw new RuntimeException("No hay datos.");
        } else {
            ArrayList<Player> ganadores = diezMejores(0,estadisticas.size()-1);
            //ganadores.forEach((n) -> System.out.println(n));
            return ganadores;
        }
    }
    
    private static ArrayList<Player> diezMejores(int inicio, int fin) {
        ArrayList <Player> resultado = new ArrayList<Player>(n);
        if (inicio == fin) { resultado.add(estadisticas.get(inicio)); 
        } else {
            int mitad = (inicio+fin)/2;
            ArrayList<Player> parteIzq = diezMejores(inicio,mitad);
            ArrayList<Player> parteDer = diezMejores(mitad+1,fin);
            int i=0, j=0;
            while (resultado.size() < n && i <= parteIzq.size()-1 && j <= parteDer.size()-1) {
                if(parteIzq.get(i).getScore() > parteDer.get(j).getScore()) {
                    resultado.add(parteIzq.get(i));
                    i++;
                } else {
                    resultado.add(parteDer.get(j));
                    j++;
                }
            }
            while (resultado.size() < n && i <= parteIzq.size()-1) {
                resultado.add(parteIzq.get(i));
                i++;
            }
            while (resultado.size() < n && j <= parteDer.size()-1) {
                resultado.add(parteDer.get(j));
                j++;
            }
        }
        return resultado;    
    }
//    private static void diezMejores(int inicio, int fin, ArrayList<Player> auxiliar) {
//        if (inicio == fin) {
//            auxiliar.add(estadisticas.get(inicio));
//            return;
//        }
//        int mitad = (inicio + fin) / 2;
//        diezMejores(inicio,mitad,auxiliar);
//        diezMejores(mitad+1,fin,auxiliar);
//        auxiliar.sort(new ComparadorPuntaje());
//        while(auxiliar.size()>10) {
//            auxiliar.remove(10);
//        }
//    }
    
    public static void cargarArchivo(String ruta) {
        estadisticas = new ArrayList<Player>();
        File f = new File(ruta);
        String[] items = null;
        try {
            Scanner scan = new Scanner(f);
            String linea = "";
            Player ultimoJugadorCargado = null;
            String ultimoNombreCargado = "";
            while(scan.hasNextLine()) {
                linea = scan.nextLine().trim();
                if (linea.isEmpty() || linea.startsWith("#")) continue;
                items = linea.split(";");
                if (items.length != 9) continue;
                double fg = comprobarValor(items[7]);
                double pts = comprobarValor(items[8]);
                if(!items[2].equals(ultimoNombreCargado)) {
                    ultimoJugadorCargado = new Player(items[2], items[6], items[4], (int) (fg*pts/100));
                    estadisticas.add(ultimoJugadorCargado);
                    ultimoNombreCargado = items[2];
                } else {
                    ultimoJugadorCargado.add(items[6], items[4], (int) (fg*pts/100));
                }
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private static double comprobarValor (String value) {
        if(value.isEmpty()) return 0;
        try {
            double d = Double.parseDouble(value.replace(",","."));
            return d;
        } catch ( NumberFormatException e ) {
            return 0;
        }
    }
}
