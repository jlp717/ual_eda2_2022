package practica_3;

import java.util.Comparator;

public class Comparador implements Comparator<Objeto> {

	@Override
	public int compare(Objeto o1, Objeto o2) {
		int compare = -Double.compare(o1.getPeso(), o2.getPeso());
		return (compare == 0 ? o1.getBeneficio().compareTo(o2.getBeneficio()) : compare);
	}

}
