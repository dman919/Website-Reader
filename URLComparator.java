/**
 * David Man 111940002 RO3
 * URL Comparator class
 */

import java.util.Comparator;

public class URLComparator implements Comparator {
	public int compare(Object o1, Object o2) {
        WebPage url1 = (WebPage) o1;
        WebPage url2 = (WebPage) o2;
        return (url1.getUrl().compareTo(url2.getUrl()));
	}
}