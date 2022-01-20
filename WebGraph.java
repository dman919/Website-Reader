/**
 * David Man 111940002 RO3
 * WebGraph class
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class WebGraph {
	//static final integer variable representing the maximum page amount
	public static final int MAX_PAGES = 40;
	//ArrayList representing all web page class objects
	private ArrayList<WebPage> pages = new ArrayList<WebPage>();
	//Array representing the links of the web pages
	private int[][] edges = new int[MAX_PAGES][MAX_PAGES];
	
	public ArrayList<WebPage> getPages() {
		return pages;
	}
	public int[][] getEdges() {
		return edges;
	}
	
	@SuppressWarnings("resource")
	/**
	 * Build from files method
	 * @param pagesFile
	 *   String variable representing pages file
	 * @param linksFile
	 *   String variable representing links file
	 * @return
	 *   returns WebGraph class object
	 * @throws IllegalArgumentException
	 *   thrown when files are invalid
	 * @throws IOException
	 *   thrown when file cannot be read
	 */
	public static WebGraph buildFromFiles(String pagesFile, String linksFile)
	  throws IllegalArgumentException, IOException {
		WebGraph webGraph = new WebGraph();
		
	    System.out.println("Loading WebGraph data...");
	    
	    try {
	    	webGraph = readPagesFile(pagesFile);
	    	
			FileInputStream fis = new FileInputStream(linksFile);
		    InputStreamReader inStream = new InputStreamReader(fis);
		    BufferedReader reader = new BufferedReader(inStream);
		    String data = reader.readLine();
		    
		    while (data != null) {
		    	data = data.trim();
		    	String source = data.substring(0, data.indexOf(" "));
		    	String destination = data.substring(data.indexOf(" ") + 1);
		    	
		    	int x = -1;
		    	int y = -1;
		    	for (int i = 0; i < webGraph.getPages().size(); i++) {
		    		if (webGraph.getPages().get(i).getUrl().equals(source))
		    			x = i;
		    		if (webGraph.getPages().
		    		  get(i).getUrl().equals(destination))
		    			y = i;
		    	}
		    	
		    	if (x != -1 && y != -1)
		    		webGraph.getEdges()[x][y] = 1;
		    	
		    	data = reader.readLine();
		    }
		    
		    for (int i = 0; i < webGraph.getPages().size(); i++) {
			    for (int j = 0; j < webGraph.getPages().size(); j++) {
			    	if (webGraph.getEdges()[i][j] != 1) {
			    		webGraph.getEdges()[i][j] = 0;
			    	}
			    }
		    }
		    
		    webGraph.updatePageRanks();
		    
		    System.out.println("Success!");
			return webGraph;
	    }
	    catch (Exception e) {
	    	throw new IllegalArgumentException("Files cannot be read.");
	    }
	}
	
	/**
	 * Add page method
	 * @param url
	 *   String variable representing url
	 * @param keywords
	 *   ArrayList representing keywords in the web page
	 * @throws IllegalArgumentException
	 *   Thrown when url already exists or url/keywords are null
	 */
	public void addPage(String url, ArrayList<String> keywords)
	  throws IllegalArgumentException {
		if (findPage(url) != -1 || url.equals(null) ||
		  keywords.equals(null)) {
			throw new IllegalArgumentException("\nError: " + url +
			  " already exists in the WebGraph. Could not add new WebPage.");
		}
		
		WebPage newPage = new WebPage(url, pages.size(), 0, keywords);
		pages.add(newPage);

		for (int i = 0; i < pages.size(); i++) {
			edges[pages.size()][i] = 0;
			edges[i][pages.size()]= 0;
		}
		
		System.out.println("\n" + url +
		  " successfully added to the WebGraph!");
	}
	
	/**
	 * Add link method
	 * @param source
	 *   String variable representing source url
	 * @param destination
	 *   String variable representing destination url
	 * @throws IllegalArgumentException
	 *   Thrown when url is null or source/destination url could not be found
	 */
	public void addLink(String source, String destination)
	  throws IllegalArgumentException {
		int x = 0;
		int y = 0;
		
		if (findPage(source) != -1) {
			x = findPage(source);
		}
		if (findPage(destination) != -1) {
			y = findPage(destination);
		}
		
		if (source == null || destination == null) {
			throw new IllegalArgumentException("Error: URL is null");
		}
		else if (findPage(source) == -1) {
			throw new IllegalArgumentException("\nError: " + source +
			  " could not be found in the WebGraph.");
		}
		else if (findPage(destination) == -1) {
			throw new IllegalArgumentException("\nError: " + destination +
			  " could not be found in the WebGraph.");
		}
		else
			edges[x][y] = 1;
		
		System.out.println("\nLink successfully added from " + source +
		  " to " + destination + "!");
		updatePageRanks();
	}
	
	/**
	 * Remove page method
	 * @param url
	 *   String variable representing url
	 */
	@SuppressWarnings({ "unchecked"})
	public void removePage(String url) {
		Collections.sort(pages, new IndexComparator());
		
		if (url.equals(null) || findPage(url) == -1)
			return;
		
		int x = findPage(url);
		
		pages.remove(findPage(url));
		
		for (int i = 0; i < pages.size(); i++) {
			pages.get(i).setIndex(i);
		}
		
		int[][] temp = edges;
		
		for (int i = x; i < pages.size() + 1; i++) {
			for (int j = 0; j < pages.size() + 1; j++) {
				edges[i][j] = temp[i + 1][j];
			}
		}
				
		for (int i = x; i < pages.size() + 1; i++) {
			for (int j = 0; j < pages.size() + 1; j++) {
				edges[j][i] = temp[j][i + 1];
			}
		}
		
		System.out.println("\n" + url + " has been removed from the graph!");
		
		updatePageRanks();
	}
	
	/**
	 * Remove link method
	 * @param source
	 *   String variable representing the source url
	 * @param destination
	 *   String variable representing the destination url
	 */
	public void removeLink(String source, String destination) {
		if (findPage(source) == -1 || findPage(destination) == -1)
			return;
		else {
			edges[findPage(source)][findPage(destination)] = 0;
			System.out.println("\nLink removed from " + source +
			  " to " + destination + "!");
		}
		
		updatePageRanks();
	}
	
	/**
	 * Update page ranks method
	 */
	public void updatePageRanks() {
		for (int i = 0; i < pages.size(); i++) {
			int temp = 0;

			for (int j = 0; j < pages.size(); j++) {
				temp += edges[j][i];
			}
			
			pages.get(i).setRank(temp);
		}
	}
	
	/**
	 * Print table method
	 */
	public void printTable() {
		System.out.println("\nIndex     URL               " +
		  "PageRank  Links               Keywords\n" +
		  "--------------------------------------------------" +
		  "----------------------------------------------------");
	}
	
	/**
	 * Keyword search method
	 * @param keyword
	 *   String variable representing the keyword being searched
	 */
	@SuppressWarnings("unchecked")
	public void keywordSearch(String keyword) {
		Collections.sort(pages, new RankComparator());
		
		ArrayList<Integer> temp = new ArrayList<Integer>();
		int count = 0;
				
		for (int i = 0; i < pages.size(); i++) {
			if (pages.get(i).getKeywords().contains(keyword)) {
				temp.add(i);
				count++;
			}
		}
		
		if (count == 0)
			System.out.println("\nNo search results found for the keyword " +
			  keyword + ".");
		else {
			System.out.println("\nRank   PageRank    URL\n" + 
			  "---------------------------------------------");
			int rank = 1;
			for (int i = 0; i < pages.size(); i++) {
				if (pages.get(i).getKeywords().contains(keyword)) {
					System.out.println(String.format("  %-2s |    %-5s | %s",
					  rank,
					  pages.get(i).getRank(),
					  pages.get(i).getUrl()));
					
					rank++;
				}
			}
		}
	}
	
	/**
	 * Read pages file method
	 * @param pagesFile
	 *   String variable representing the pages file
	 * @return
	 *   Returns WebGraph class object
	 * @throws IOException
	 *   Thrown when file cannot be read
	 */
	@SuppressWarnings("resource")
	public static WebGraph readPagesFile(String pagesFile)
	  throws IOException {
		WebGraph webGraph = new WebGraph();
		
		FileInputStream fis = new FileInputStream(pagesFile);
	    InputStreamReader inStream = new InputStreamReader(fis);
	    BufferedReader reader = new BufferedReader(inStream);
	    String data = reader.readLine();
	    int index = 0;
	    
	    while (data != null) {
		    data = data.trim();

	    	String url = data.substring(0, data.indexOf(" "));
    		ArrayList<String> keywords = new ArrayList<String>();
    		data = data.substring(data.indexOf(" ") + 1);
	    	
	    	while (data.contains(" ")) {
	    		keywords.add(data.substring(0, data.indexOf(" ")));
	    		data = data.substring(data.indexOf(" ") + 1);
	    	}
    		keywords.add(data);
	    	
	    	WebPage page = new WebPage(url, index, 0, keywords);
	    	webGraph.getPages().add(page);
	    	
	    	index++;
	    	
	    	data = reader.readLine();
	    }
	    
    	for (int i = 0; i < webGraph.getPages().size(); i++) {
    		for (int j = 0; j < webGraph.getPages().size(); j++) {
    			webGraph.getEdges()[i][j] = 0;
    		}
    	}

    	return webGraph;
	}
	
	/**
	 * Print by index method
	 */
	@SuppressWarnings("unchecked")
	public void printByIndex() {
		Collections.sort(pages, new IndexComparator());
		
		for (int i = 0; i < pages.size(); i++) {
			String keywords = "";
			for (int j = 0; j < pages.get(i).getKeywords().size(); j++) {
				if (j == pages.get(i).getKeywords().size() - 1)
					keywords += pages.get(i).getKeywords().get(j);
				else
					keywords += pages.get(i).getKeywords().get(j) + ", ";
			}
			System.out.println(String.format(
			  "  %-3s | %-18s |    %-4s | %-18s| %s",
			  pages.get(i).getIndex(),
			  pages.get(i).getUrl(),
			  pages.get(i).getRank(),
			  findLinks(pages.get(i).getUrl()),
			  keywords));
		}
	}
	/**
	 * Print by url method
	 */
	@SuppressWarnings("unchecked")
	public void printByURL() {
		Collections.sort(pages, new URLComparator());
		
		for (int i = 0; i < pages.size(); i++) {
			String keywords = "";
			for (int j = 0; j < pages.get(i).getKeywords().size(); j++) {
				if (j == pages.get(i).getKeywords().size() - 1)
					keywords += pages.get(i).getKeywords().get(j);
				else
					keywords += pages.get(i).getKeywords().get(j) + ", ";
			}
			System.out.println(String.format(
			  "  %-3s | %-18s |    %-4s | %-18s| %s",
			  pages.get(i).getIndex(),
			  pages.get(i).getUrl(),
			  pages.get(i).getRank(),
			  findLinks(pages.get(i).getUrl()),
			  keywords));
			Collections.sort(pages, new URLComparator());
		}
	}
	/**
	 * Print by rank method
	 */
	@SuppressWarnings("unchecked")
	public void printByRank() {
		Collections.sort(pages, new RankComparator());

		for (int i = 0; i < pages.size(); i++) {
			String keywords = "";
			for (int j = 0; j < pages.get(i).getKeywords().size(); j++) {
				if (j == pages.get(i).getKeywords().size() - 1)
					keywords += pages.get(i).getKeywords().get(j);
				else
					keywords += pages.get(i).getKeywords().get(j) + ", ";
			}
			System.out.println(String.format(
			  "  %-3s | %-18s |    %-4s | %-18s| %s",
			  pages.get(i).getIndex(),
			  pages.get(i).getUrl(),
			  pages.get(i).getRank(),
			  findLinks(pages.get(i).getUrl()),
			  keywords));
			Collections.sort(pages, new RankComparator());
		}
	}
	
	/**
	 * Find links method
	 * @param temp
	 *   String variable representing links found for web page
	 * @return
	 *   Returns links of web page separated by a comma
	 */
	public String findLinks(String temp) {
		String s = "";
		
		int x = findPage(temp);
		
		for (int i = 0; i < pages.size(); i++) {
			if (edges[x][i] == 1) {
				s += i + ", ";
			}
		}
		
		if (s.contains(", "))
			s = s.substring(0, s.length() - 2);
		return s;
	}
	
	/**
	 * Find page method
	 * @param page
	 *   String variable representing name of page
	 * @return
	 *   Returns index of web page when found
	 */
	@SuppressWarnings("unchecked")
	public int findPage(String page) {
		Collections.sort(pages, new IndexComparator());

		int temp = -1;
		
		for (int i = 0; i < pages.size(); i++) {
			if (pages.get(i).getUrl().equals(page)) {
				temp = i;
			}
		}
		
		return temp;
	}
}