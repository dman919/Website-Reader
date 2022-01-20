/**
 * David Man 111940002 RO3
 * Index Comparator class
 */

import java.util.Comparator;

public class IndexComparator implements Comparator{
	public int compare(Object o1, Object o2) {
        WebPage page1 = (WebPage) o1;
        WebPage page2 = (WebPage) o2;
        if (page1.getIndex() == page2.getIndex())
            return 0;
        else if (page1.getIndex() > page2.getIndex())
            return 1;
        else
            return -1;
	}
}