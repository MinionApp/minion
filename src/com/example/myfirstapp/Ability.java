package com.example.myfirstapp;

import java.util.*;

/**
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
	
	/**
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
	
	/**
	 * Initializes an ability with the given name and given default score.
	 * 
	 * @param name	the name of the ability being stores, such as Strength (str),
	 * 				Dexterity (dex), ect...
	 * @param score	the value to initialize the base stat
	 * 
	 */
	public Ability(String name, int score){
		//TODO: Consider ability values < 0
		this.name = name;
		this.base = score;
	}
	
	/**
	 * Get the name of this ability
	 * 
	 * @return a String name of the ability
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Get the current base stat for the ability, not including any temporary
	 * modifiers to the base stat
	 * 
	 * @return an int ability score with no temp modifiers
	 */
	public int getBase(){
		return base;
	}
	
	/**
	 * Add the given value to the base permanently 
	 * 
	 * @param modifier	Adds given number to the base stat
	 * @modifies base
	 */
	public void addToBase(int modifier){
		//TODO: Consider ability values < 0
		base += modifier;
	}
	
	/**
	 * Sets base stat to the given value permanently
	 * 
	 * @param newBase	an int whose value set over the old base stat
	 * @modifies this
	 */
	public void setBase(int newBase){
		//TODO: Consider ability values < 0
		base = newBase;
	}
	
	/**
	 * Returns the modifier under the given name. Can return both negative
	 * and positive modifiers. These modifiers represent values that will be
	 * either added or subtracted from the base stat.
	 * 
	 * @param tempName the name of the temporary modifier whose value is retrieved
	 * @return 	the value associated with the given String, may be either negative
	 * 			or positive. Returns 0 if no temporary modifier of the given name
	 * 			was found
	 */
	public int getTempModifier(String tempName){
		if (tempModifiers.containsKey(tempName)) {
			return tempModifiers.get(tempName);
		}
		
		return 0;
	}
	
	/**
	 * Removes the modifier under the given name as well as the record of that name.
	 * 
	 * @param tempName	the name of the temporary modifier to remove
	 * @modifies this
	 */
	public void removeTempModifier(String tempName){
		if (tempModifiers.containsKey(tempName)) {
			tempModifiers.remove(tempName);
		}
	}
	
	/**
	 * Adds a new Temporary Modifier with the given name and value
	 * 
	 * @param tempName	the name of the temporary modifier
	 * @param tempValue	the value of the modifier
	 * @modifies this
	 */
	public void addTempModifier(String tempName, int tempValue){
		//TODO: Consider already existing values
		tempModifiers.put(tempName, tempValue);
	}
	
	/**
	 * Gets the total ability score including the base stat value and all
	 * temporary modifiers currently stored.
	 * 
	 * @return an int score representing the total ability score
	 */
	public int getScore(){
		//TODO: consider capping return at 0 instead of giving negative scores
		int score = base;
		Collection<Integer> temps = tempModifiers.values();
		Iterator<Integer> it = temps.iterator();
		while (it.hasNext()) {
			score += it.next();
		}
		return score;
	}
	
	/**
	 * Gets the modifier of the ability score with all base and temporary values
	 * considered.
	 * 
	 * @return an int representing the ability modifier 
	 * 
	 */
	public int getMod(){
		//TODO: Consider ability values < 0
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
