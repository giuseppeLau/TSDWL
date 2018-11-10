/* 
 * File:   tvShow.java
 * Author: Giuseppe
 *
 */

import java.util.Random;
import java.util.*;

public class tvShow {

	private String name;
	private int episodes_number;
	private ArrayList<episode> tvShow;

	public tvShow(String _name, int _episodes_number){

		this.name = _name;
		this.episodes_number = _episodes_number;
		tvShow = new ArrayList<episode>(this.episodes_number);

		for(int i = 0; i < episodes_number; i++){

			tvShow.add(new episode(i));
		}
	}

	public String getName(){

		return this.name;
	}

	public int getEpisodesNumber(){

		return this.episodes_number;
	}

	public episode getEpisode(int ep_number){

		return tvShow.get(ep_number);
	}

	public Boolean checkEpAvailability(int ep_number){

		if(tvShow.get(ep_number).getAvailability()==true) return true;
		else return false;

	}

	public void printShowInfo(){

		System.out.println("Title: " + this.name);
		System.out.println("Number of episodes: " + this.episodes_number);

		for(int i = 0; i < this.episodes_number; i++){

			System.out.println("Episode number: " + (tvShow.get(i).getId()+1) + ". " + "Episode's title: " + tvShow.get(i).getTitle());
		}
	}
}
