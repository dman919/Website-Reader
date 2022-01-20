/**
 * David Man 111940002 RO3
 * WebPage class
 */

import java.util.ArrayList;

public class WebPage {
	//String variable representing the URL
	private String url;
	//Integer variable representing the index
	private int index;
	//Integer variable representing the rank
	private int rank;
	//ArrayList representing the keywords
	private ArrayList<String> keywords;
	
	/**
	 * No argument constructor for WebPage class
	 */
	public WebPage() {
		url = "";
		index = 0;
		rank = 0;
		keywords = new ArrayList<String>();
	}
	
	/**
	 * Argument constructor for WebPage class
	 * @param url
	 * @param index
	 * @param rank
	 * @param keywords
	 */
	public WebPage(String url, int index, int rank,
	  ArrayList<String> keywords) {
		this.setUrl(url);
		this.setIndex(index);
		this.setRank(rank);
		this.setKeywords(keywords);
	}
	
	public String getUrl() {
		return url;
	}
	/**
	 * Url mutator method
	 * @param url
	 *   Url string variable
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	public int getIndex() {
		return index;
	}
	/**
	 * Index mutator method
	 * @param index
	 *   Index integer variable
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	public int getRank() {
		return rank;
	}
	/**
	 * Rank mutator method
	 * @param rank
	 *   Rank integer variable
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public ArrayList<String> getKeywords() {
		return keywords;
	}
	/**
	 * Keywords mutator method
	 * @param
	 *   Keywords ArrayList
	 */
	public void setKeywords(ArrayList<String> keywords) {
		this.keywords = (ArrayList<String>) keywords;
	}
	
	/**
	 * WebPage toString method
	 */
	public String toString() {
		String s = index + " | " + url + " |***| ";
		for (int i = 0; i < keywords.size(); i++) {
			s += keywords.get(i) + ", ";
		}
		
		return s;
	}
}