/* 
 * File:   showList.java
 * Author: Giuseppe
 *
 */

import java.util.*;

public class showList {

	tvShow houseOfKarts = new tvShow("House of karts", 5);
	tvShow strangeThings = new tvShow("Strange Things", 10);
	tvShow bakingBrad = new tvShow("Baking Brad", 12);
	tvShow marcos = new tvShow("Marcos", 7);
	tvShow blackMails = new tvShow("Black Mails", 15);
	tvShow rickEMorto= new tvShow("Rick e Morto", 4);

	ArrayList<tvShow> tvShowsList = new ArrayList<tvShow>(6);

	public showList(){

		tvShowsList.add(houseOfKarts);
		tvShowsList.add(strangeThings);
		tvShowsList.add(bakingBrad);
		tvShowsList.add(marcos);
		tvShowsList.add(blackMails);
		tvShowsList.add(rickEMorto);
	}

	public Boolean isAvailable(String show_title, int ep_number){

		Boolean show_found = false;
		Boolean episode_found = false;
		Boolean episode_is_available;
		int index = 0;

		for(int i = 0; i < tvShowsList.size(); i++){

			if(tvShowsList.get(i).getName().equals(show_title)){
				show_found = true;
				index = i;
				break;
			}
		}

		if(show_found == false){
			System.out.println("Tv Show not found.");
			return false;
		} else {

			if(ep_number <= tvShowsList.get(index).getEpisodesNumber()){ 

				if(tvShowsList.get(index).checkEpAvailability(ep_number-1)==true){
					System.out.println("Episode " + ep_number + " is available.");
					return true;
				} else {
					System.out.println("Episode " + ep_number + " is not available.");
					return false;
				}

			} else {
				System.out.println("Episode not found.");
				return false;
			}
		} 
	}
}
