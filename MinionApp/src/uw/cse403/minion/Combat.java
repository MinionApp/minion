package uw.cse403.minion;

import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * A wrapper class with a few associated calculations to do with combat
 * checks and scores such as armor class, hit points and CMB/CMD
 * 
 * @author lokiw
 *
 */
public class Combat {
	public long charID;
	public boolean isNew; 
	
	//totalHP field does not include constitution
	private int baseHP;
	private int damageReduction;
	//currentHP includes constitution

	private int lethalDamage;
	private int bludgeningDamage;
	private Map<String,Integer> armorModifiers;
	private Map<String,Integer> hpModifiers;

	//Speed stored in feet
	public int speedBase;
	public int speedArmor;
	private int speed;
	private int initModifiers;
	private int bAb;
	private int armorTotal;
	

	public Combat() {
		this(9000);
	}
	
	public Combat(long id) {
		baseHP = 0;
		damageReduction = 0;
		lethalDamage = 0;
		bludgeningDamage = 0;
		armorModifiers = new HashMap<String, Integer>();
		hpModifiers = new HashMap<String, Integer>();
		speed = 0;
		initModifiers = 0;
		bAb = 0;
		
		loadFromDB();
	}
	
	/**
	 * Populate fields with values from DB
	 */
	private void loadFromDB() {
		isNew = true;
		// attempt to load from DB
		Cursor cursor = SQLiteHelperBasicInfo.db.query(SQLiteHelperBasicInfo.TABLE_NAME, SQLiteHelperBasicInfo.ALL_COLUMNS, 
				SQLiteHelperBasicInfo.COLUMN_ID + " = " + charID, null, null, null, null);
		if (cursor.moveToFirst()) {
			isNew = false;
			// Columns: COLUMN_CHAR_ID, COLUMN_HP_TOTAL, COLUMN_HP_DR, COLUMN_SPEED_BASE, COLUMN_SPEED_ARMOR,
			// COLUMN_INIT_MISC_MOD, COLUMN_BASE_ATTACK_BONUS
			baseHP 			= cursor.getInt(1);
			damageReduction = cursor.getInt(2);
			speedBase 		= cursor.getInt(3);
			speedArmor 		= cursor.getInt(4);
			initModifiers 	= cursor.getInt(5);
			bAb 			= cursor.getInt(6);
		}
		cursor.close();
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

	public void setSpeed(int speedBase, int speedArmor) {
		this.speedBase = speedBase;
		this.speedArmor = speedArmor;
		this.speed = speedBase - speedArmor;
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
	
	/** 
	 * Writes Combat to database. SHOULD ONLY BE CALLED BY CHARACTER
	 * @param id id of character
	 * @param db database to write into
	 */
	public void writeToDB(long id) {
		// TODO implement
		int skillID = 0; // get skill ID from ref db
		
		ContentValues values = new ContentValues();
		values.put(SQLiteHelperCombat.COLUMN_CHAR_ID, id);
		values.put(SQLiteHelperCombat.COLUMN_HP_TOTAL, baseHP);
		values.put(SQLiteHelperCombat.COLUMN_HP_DR, damageReduction);
		values.put(SQLiteHelperCombat.COLUMN_SPEED_BASE, speed);
		values.put(SQLiteHelperCombat.COLUMN_INIT_MISC_MOD, initModifiers);
		values.put(SQLiteHelperCombat.COLUMN_BASE_ATTACK_BONUS, bAb);
		// still need to do lethal/bludgeoning, and armor/hp mods
		SQLiteHelperCombat.db.insert(SQLiteHelperCombat.TABLE_NAME, null, values);
	}
}
