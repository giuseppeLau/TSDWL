/* 
 * File:   episode.java
 * Author: Giuseppe
 *
 */

import java.util.Random;

public class episode {
	
	private int id;
	private String title;
	private Boolean isAvailable;

	private String generate_title(){

		String characters = "qwertyuiopasdfghjklzxcvbnm";
		StringBuilder builder = new StringBuilder();
		Random rand = new Random();
		int n = rand.nextInt(characters.length());

		for(int i = 0; i < n; i++){

			int index = rand.nextInt(characters.length());
			builder.append(characters.charAt(index));
		}
		return builder.toString();
	}

	public episode(int _id){

		id = _id;
		title = generate_title();
		Random rand_2 = new Random();
		int av = rand_2.nextInt(100);
		if(av%2 == 0) isAvailable = false;
		else isAvailable = true;
	}

	public String getTitle(){

		return this.title;		
	}

	public int getId(){

		return this.id;
	}

	public Boolean getAvailability(){

		return this.isAvailable;
	}
}
