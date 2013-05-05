package com.example.myfirstapp;

import java.util.HashMap;
import java.util.Map;

/**
 * A wrapper class with a few associated calculations to do with combat
 * checks and scores such as armor class, hit points and CMB/CMD
 * 
 * @author lokiw
 *
 */
public class Combat {
	//totalHP field does not include constitution
	private int baseHP;
	private int damageReduction;
	//currentHP includes constitution
	private int lethalDamage;
	private int bludgeningDamage;
	private Map<String,Integer> armorModifiers;
	private Map<String,Integer> hpModifiers;
	//Speed stored in feet
	private int speed;
	private int initModifiers;
	private int bAb;
	private int armorTotal;
	


	public Combat() {
		baseHP = 0;
		damageReduction = 0;
		lethalDamage = 0;
		bludgeningDamage = 0;
		armorModifiers = new HashMap<String, Integer>();
		hpModifiers = new HashMap<String, Integer>();
		speed = 0;
		initModifiers = 0;
		bAb = 0;
	}
	
	public int getBaseHP() {
		return baseHP;
	}

	public void setBaseHP(int baseHP) {
		this.baseHP = baseHP;
	}

	public int getDamageReduction() {
		return damageReduction;
	}

	public void setDamageReduction(int damageReduction) {
		this.damageReduction = damageReduction;
	}

	public int getLethalDamage() {
		return lethalDamage;
	}

	public void setLethalDamage(int lethalDamage) {
		this.lethalDamage = lethalDamage;
	}

	public int getBludgeningDamage() {
		return bludgeningDamage;
	}

	public void setBludgeningDamage(int bludgeningDamage) {
		this.bludgeningDamage = bludgeningDamage;
	}

	public Map<String, Integer> getArmorModifiers() {
		return armorModifiers;
	}

	public void addArmorModifiers(String type, int value) {
		armorModifiers.put(type, value);
	}

	public Map<String, Integer> getHpModifiers() {
		return hpModifiers;
	}

	public void setHpModifiers(String type, int value) {
		hpModifiers.put(type, value);
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getInitModifiers() {
		return initModifiers;
	}

	public void setInitModifiers(int initModifiers) {
		this.initModifiers = initModifiers;
	}

	public int getbAb() {
		return bAb;
	}

	public void setbAb(int bAb) {
		this.bAb = bAb;
	}

	public int getArmorTotal() {
		return armorTotal;
	}

	public void setArmorTotal(int armorTotal) {
		this.armorTotal = armorTotal;
	}
	
}
