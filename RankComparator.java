/**
 * David Man 111940002 RO3
 * Rank Comparator class
 */

import java.util.Comparator;

public class RankComparator implements Comparator {
	public int compare(Object o1, Object o2) {
        WebPage rank1 = (WebPage) o1;
        WebPage rank2 = (WebPage) o2;
        if (rank1.getRank() == rank2.getRank())
            return 0;
        else if (rank1.getRank() < rank2.getRank())
            return 1;
        else
            return -1;
	}
}