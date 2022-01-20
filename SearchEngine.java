/**
 * David Man 111940002 RO3
 * SearchEngine class
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SearchEngine {
	//Static final string variable representing pages file
	public static final String PAGES_FILE = "pages.txt";
	//Static final string variable representing links file
	public static final String LINKS_FILE = "links.txt";
	//WebGraph class object representing the web of graphs
	private WebGraph web;
	
	/**
	 * No argument constructor for SearchEngine class
	 */
	public SearchEngine() {
		web = new WebGraph();
	}
	
	public WebGraph getWeb() {
		return web;
	}
	/**
	 * Web mutator method
	 * @param web
	 */
	public void setWeb(WebGraph web) {
		this.web = web;
	}
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		//SearchEngine class object for main method
		SearchEngine searchEngine = new SearchEngine();
		try {
			//Building files
			searchEngine.setWeb(
			  WebGraph.buildFromFiles(PAGES_FILE, LINKS_FILE));
		}
		catch (IllegalArgumentException | IOException e) {
			System.out.println(e.getMessage());;
		}
		String s = "";
		
		while (!s.equals("Q") || !s.equals("q")) {
			//Menu
			System.out.println("\nMenu:\n" + 
			  "    (AP) - Add a new page to the graph.\n" + 
			  "    (RP) - Remove a page from the graph.\n" + 
			  "    (AL) - Add a link between pages in the graph.\n" + 
			  "    (RL) - Remove a link between pages in the graph.\n" + 
			  "    (P)  - Print the graph.\n" + 
			  "    (S)  - Search for pages with a keyword.\n" + 
			  "    (Q)  - Quit.\n");
			
			System.out.print("Please select an option: ");
			s = input.nextLine();
			
			//Add page
			if (s.equals("AP")) {
				System.out.print("Enter a URL: ");
				String url = input.nextLine();
				
				System.out.print("Enter keywords (space-separated): ");
				String keywords = input.nextLine();
				
				ArrayList<String> temp = new ArrayList<String>();
				
				while (keywords.contains(" ")) {
					temp.add(keywords.substring(0, keywords.indexOf(" ")));
					keywords = keywords.substring(keywords.indexOf(" ") + 1);
				}
				temp.add(keywords);
				
				try {
					searchEngine.getWeb().addPage(url, temp);
				}
				catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			
			//Remove page
			if (s.equals("RP")) {
				System.out.print("Enter a URL: ");
				String url = input.nextLine();
				
				searchEngine.getWeb().removePage(url);
			}
			
			//Add link
			if (s.equals("AL")) {
				System.out.print("Enter a source URL: ");
				String source = input.nextLine();
				
				System.out.print("Enter a destination URL: ");
				String destination = input.nextLine();
				
				try {
					searchEngine.getWeb().addLink(source, destination);
				}
				catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			
			//Remove link
			if (s.equals("RL")) {
				System.out.print("Enter a source URL: ");
				String source = input.nextLine();
				
				System.out.print("Enter a destination URL: ");
				String destination = input.nextLine();
				
				searchEngine.getWeb().removeLink(source, destination);
			}
			
			//Print all web pages in web graph in a formatted table
			if (s.equals("P")) {
				System.out.println("\n    (I) Sort based on index (ASC)\n" +
								 "    (U) Sort based on URL (ASC)\n" + 
								 "    (R) Sort based on rank (DSC)\n");
				
				System.out.print("Please select an option: ");
				String temp = input.nextLine();

				searchEngine.getWeb().printTable();
				
				//Print by index
				if (temp.equals("I")) {
					searchEngine.getWeb().printByIndex();
				}
				//Print by URL
				if (temp.equals("U")) {
					searchEngine.getWeb().printByURL();
				}
				//Print by rank
				if (temp.equals("R")) {
					searchEngine.getWeb().printByRank();
				}
			}
			
			//Search by keyword
			if (s.equals("S")) {
				System.out.print("Search keyword: ");
				String keyword = input.nextLine();
				
				searchEngine.getWeb().keywordSearch(keyword);
			}
			
			//Terminate program
			if (s.equals("Q")) {
				System.out.print("\nGoodbye.");
				System.exit(0);
			}
		}
	}
}
