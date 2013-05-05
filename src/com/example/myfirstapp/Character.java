package com.example.myfirstapp;

import java.util.*;

/**
 * A representation of a Pathfinder character with all of the information
 * stored on a standard character sheet.
 * 
 * @author Loki White (lokiw)
 * @author Thomas Eberlein (uwte)
 *
 */
public class Character {
	private long id;
	private CharacterDescription desc;
	private Ability abilityScores[];
	private Map<String,Skill> skills;
	private Combat combat;
	private int level;
	
	public Character(){
		abilityScores = new Ability[6];
		skills = new HashMap<String,Skill>();
		level = 1;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id){
		this.id = id;
	}
	
	public String getName(){
		return desc.name;
	}
	
	public void setName(String name){
		desc.name = name;
	}
	
	public int getCMD(){
		//TODO: Fill in this method
		return 10;
	}
	
	public int getCMB(){
		//TODO: Fill in this method
		return 0;
	}
	
	/**
	 * Fill in skills with the basic skills found on current pathfinder character sheets
	 * for ease of the user not having to insert them all themselves. These skills are:
	 * <p>
	 * Acrobatics, Appraise, Bluff, Climb, Craft_x, Diplomacy, Disable Device, Disguise,
	 * Escape Artist, Fly, Handle Animal, Heal, Intimidate, Knowledge_x, Linguistics, 
	 * Perception, Perform_x, Profession_x, Ride, Sense Motive, Sleight of Hand, 
	 * Spellcraft, Stealth, Survival, Swim, Use Magic Device
	 *  
	 */
	public void setSkillsToDefault(){
		//TODO: Fill in this method
		skills.put("Acrobatics", new Skill("Acrobatics",AbilityName.DEXTERITY));
	}
	
	/**
	 * get the total hit points of the character considering base, and
	 * constitution modifier per level
	 * 
	 * @return int for total hit points
	 */
	public int getTotalHitPoints(){
		
		//TODO: include hpModifiers
		return combat.baseHP + abilityScores[3].getMod()*level;
	}
	
	/**
	 * get the current hit points of the character based on total hit points
	 * and damage taken both lethal and non lethal.
	 * 
	 * @return	current hit points of character.
	 */
	public int getCurrentHitPoints(){
		return getTotalHitPoints() - (combat.lethalDamage + combat.bludeningDamage);
	}
	
	@Override
	public String toString(){
		return desc.name;
	}
}
