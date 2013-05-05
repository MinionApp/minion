package com.example.myfirstapp;

import java.util.*;

/*
 * Ability is a class to represent a single ability on a character sheet 
 * which has a base value and temporary modifiers. This class handles both
 * the raw stat and the modifier from that stat.
 * 
 * @author Loki White (lokiw)
 */
public class Ability {
	private String name;
	private int base;
	private Map<String,Integer> tempModifiers;
	
	/*
	 * Initializes an ability with the given name and defaults the base stat to
	 * 10. No temporary modifiers are initialized.
	 * 
	 * @param name	the name of the ability being stores, such as Strength (str),
	 * 				Dexterity (dex), ect...
	 */
	public Ability(String name){
		this.name = name;
		base = 10;
		tempModifiers = new HashMap<String,Integer>();
	}
	
	/*
	 * Initializes an ability with the given name and given default score.
	 * 
	 * @param name	the name of the ability being stores, such as Strength (str),
	 * 				Dexterity (dex), ect...
	 * @param score	the value to initialize the base stat
	 * 
	 */
	public Ability(String name, int score){
		this.name = name;
		this.base = score;
	}
	
	/*
	 * Get the name of this ability
	 * 
	 * @return a String name of the ability
	 */
	public String getName(){
		return name;
	}
	
	/*
	 * Get the current base stat for the ability, not including any temporary
	 * modifiers to the base stat
	 * 
	 * @return an int ability score with no temp modifiers
	 */
	public int getBase(){
		return base;
	}
	
	/*
	 * Add the given number to the base permanently 
	 */
	public void addToBase(int modifier){
		base += modifier;
	}
	
	/*
	 * 
	 */
	public void setBase(int newBase){
		
	}
	
	/*
	 * 
	 */
	public int getTempModifier(String tempName){
		if (tempModifiers.containsKey(tempName)) {
			return tempModifiers.get(tempName);
		}
		
		return 0;
	}
	
	/*
	 * 
	 */
	public void removeTempModifier(String tempName){
		if (tempModifiers.containsKey(tempName)) {
			tempModifiers.remove(tempName);
		}
	}
	
	/*
	 * 
	 */
	public int getScore(){
		int score = base;
		Collection<Integer> temps = tempModifiers.values();
		Iterator<Integer> it = temps.iterator();
		while (it.hasNext()) {
			score += it.next();
		}
		return score;
	}
	
	/*
	 * 
	 */
	public int getMod(){
		int mod;
		int score = getScore();
		if (score >= 10) {
			mod = (score - 10) / 2;
		} else {
			mod = (11 - score) / 2;
		}
		
		return mod;
	}
	
}
