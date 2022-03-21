package practica_1;

import java.util.Comparator;

	public class ComparatorPuntuacion implements Comparator<Player> {

		@Override
		public int compare(Player o1, Player o2) {
			int compare = - Integer.compare(o1.getScore(), o2.getScore());
			return compare == 0 ? o1.getPlayerName().compareTo(o2.getPlayerName()) : compare;
		}
}
