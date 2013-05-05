package com.example.myfirstapp;

import java.util.*;

/**
 * A wrapper class with a few associated calculations to do with combat
 * checks and scores such as armor class, hit points and CMB/CMD
 * 
 * @author lokiw
 *
 */
public class Combat {
	//totalHP field does not include constitution
	public int baseHP;
	public int damageReduction;
	//currentHP includes constitution
	public int lethalDamage;
	public int bludeningDamage;
	public Map<String,Integer> armorModifiers;
	public Map<String,Integer> hpModifiers;
	//Speed stored in feet
	public int speed;
	public int initModifiers;
	public int bAb;
	
}
