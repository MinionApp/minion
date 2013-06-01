<?php
$un=$_POST['username'];   
$basicInfo=json_decode($_POST['basicInfo']);
$abilities=json_decode($_POST['abilities']);
$skills=json_decode($_POST['skills']);
$combat=json_decode($_POST['combat']);
$savingThrows=json_decode($_POST['savingThrows']);

try {
	//connect to the db
	$db = new PDO("mysql:dbname=sahabp_minion;host=cubist.cs.washington.edu", "sahabp", "9BJju8xn");
	$charID = $basicInfo->local_id;
	$rows = $db->query($sql = "SELECT * FROM `Characters` WHERE `username` = '$un' AND `local_id` = '$charID'");
	if($count = $rows->rowCount() > 0) {
		$rows = $db->query($sql = "SELECT `_id` FROM `Characters` WHERE `username` = '$un' AND `local_id` = '$charID'");
		$id = 0;
		foreach ($rows as $row) {
			$id = $row["_id"];
		}
		// Character does exist, update
		
		// basic info
		$name = $basicInfo->name;
		$alignment = $basicInfo->alignment;
		$level = $basicInfo->level;
		$diety = $basicInfo->diety;
		$homeland = $basicInfo->homeland;
		$race = $basicInfo->race;
		$size = $basicInfo->size;
		$gender = $basicInfo->gender;
		$age = $basicInfo->age;
		$height = $basicInfo->height;
		$weight = $basicInfo->weight;
		$hair = $basicInfo->hair;
		$eyes = $basicInfo->eyes;
		$sql = "UPDATE `char_basic` SET `name` = '$name', `alignment` = '$alignment', `level` = '$level', `diety` = '$diety', `homeland` = '$homeland', `race` = '$race', `size` = '$size', `gender` = '$gender', `age` = '$age', `height` = '$height', `weight` = '$weight', `hair` = '$hair', `eyes` = '$eyes' WHERE `char_id` = '$id'";
		$result = $db->exec($sql);
		
		// abilities
		foreach($abilities->abilities as $ability) {
			$name = $ability->name;
			$mod = $ability->mod;
			$ref_id = $ability->ref_id;
			$sql = "UPDATE `char_ability_mod` SET `name` = '$name', `mod` = '$mod' WHERE `char_id` = '$id' AND `ref_id` = '$ref_id'";
			$result = $db->exec($sql);
			$score = $ability->score;
			$sql = "UPDATE `char_ability` SET `score` = '$score' WHERE `char_id` = '$id' AND `ref_id` = '$ref_id'";
			$result = $db->exec($sql);
		}
		
		// skills
		foreach($skills->skills as $skill) {
			$ref_id = $skill->ref_id;
			$ranks = $skill->ranks;
			$misc_mod = $ability->score;
			$sql = "DELETE FROM `char_skills` WHERE `char_id` = '$id' AND `ref_id` = '$ref_id'";
			$result = $db->exec($sql);
			$sql = "INSERT INTO `char_skills` (`char_id`, `ref_id`, `ranks`, `misc_mod`) VALUES ('$id', '$ref_id', '$ranks', '$misc_mod')";
			$result = $db->exec($sql);
		}
		
		// combat and armor
		$hp_total = $combat->hp_total;
		$hp_dr = $combat->hp_dr;
		$speed_base = $combat->speed_base;
		$speed_armor = $combat->speed_armor;
		$init_misc_mod = $combat->init_misc_mod;
		$base_attack_bonus = $combat->base_attack_bonus;
		
		$armor_bonus = $combat->armor_bonus;
		$shield_bonus = $combat->shield_bonus;
		$nat_armor = $combat->nat_armor;
		$deflection_mod = $combat->deflection_mod;
		$misc_mod = $combat->misc_mod;
		
		$sql = "UPDATE `char_combat` SET `hp_total` = $hp_total, `hp_dr` = '$hp_dr', `speed_base` = '$speed_base', `speed_armor` = '$speed_armor', `init_misc_mod` = '$init_misc_mod', `base_attack_bonus` = '$base_attack_bonus' WHERE `char_id` = '$id'";
		$result = $db->exec($sql);
		$sql = "UPDATE `char_armor` SET `armor_bonus` = $armor_bonus, `shield_bonus` = '$shield_bonus', `nat_armor` = '$nat_armor', `deflection_mod` = '$deflection_mod', `misc_mod` = '$misc_mod' WHERE `char_id` = '$id'";
		$result = $db->exec($sql);
		
		// saving throws
		foreach($savingThrows->savingThrows as $savingThrow) {
			$ref_id = $savingThrow->ref_id;
			$name = $savingThrow->name;
			$base_save = $savingThrow->base_save;
			$magic_mod = $savingThrow->magic_mod;
			$misc_mod = $savingThrow->misc_mod;
			$temp_mod = $savingThrow->temp_mod;
			$sql = "UPDATE `char_saving_throws` SET `name` = '$name', `base_save` = '$base_save', `magic_mod` = '$magic_mod', `misc_mod` = '$misc_mod', `temp_mod` = '$temp_mod' WHERE `char_id` = '$id' AND `ref_id` = '$ref_id'";
			$result = $db->exec($sql);
		}
		
		echo 0;
	} else  {
		// Character doesn't exist
		$sql = "INSERT INTO `Characters` (`username`, `local_id`) VALUES ('$un', '$charID')";
		$result = $db->exec($sql);
		
		$rows = $db->query($sql = "SELECT `_id` FROM `Characters` WHERE `username` = '$un' AND `local_id` = '$charID'");
		$id = 0;
		foreach ($rows as $row) {
			$id = $row["_id"];
		}
		// basic info
		$name = $basicInfo->name;
		$alignment = $basicInfo->alignment;
		$level = $basicInfo->level;
		$diety = $basicInfo->diety;
		$homeland = $basicInfo->homeland;
		$race = $basicInfo->race;
		$size = $basicInfo->size;
		$gender = $basicInfo->gender;
		$age = $basicInfo->age;
		$height = $basicInfo->height;
		$weight = $basicInfo->weight;
		$hair = $basicInfo->hair;
		$eyes = $basicInfo->eyes;
		
		$sql = "INSERT INTO char_basic (char_id, name, alignment, level, diety, homeland, race, size, gender, age, height, weight, hair, eyes) VALUES ('$id', '$name', '$alignment', '$level', '$diety', '$homeland', '$race', '$size', '$gender', '$age', '$height', '$weight', '$hair', '$eyes')";
		$result = $db->exec($sql);
		
		// abilities
		$i = 0;
		foreach($abilities->abilities as $ability) {
			$name = $ability->name;
			$mod = $ability->mod;
			$sql = "INSERT INTO `char_ability_mod` (`char_id`, `ref_id`, `name`, `mod`) VALUES ('$id', '$i', '$name', '$mod')";
			$result = $db->exec($sql);
			$score = $ability->score;
			$sql = "INSERT INTO char_ability (char_id, ref_id, score) VALUES ('$id', '$i', '$score')";
			$result = $db->exec($sql);
			$i++;
		}
		
		// skills
		foreach($skills->skills as $skill) {
			$ref_id = $skill->ref_id;
			$ranks = $skill->ranks;
			$misc_mod = $ability->score;
			$sql = "INSERT INTO `char_skills` (`char_id`, `ref_id`, `ranks`, `misc_mod`) VALUES ('$id', '$ref_id', '$ranks', '$misc_mod')";
			$result = $db->exec($sql);
		}
		
		// combat and armor
		$hp_total = $combat->hp_total;
		$hp_dr = $combat->hp_dr;
		$speed_base = $combat->speed_base;
		$speed_armor = $combat->speed_armor;
		$init_misc_mod = $combat->init_misc_mod;
		$base_attack_bonus = $combat->base_attack_bonus;
		
		$armor_bonus = $combat->armor_bonus;
		$shield_bonus = $combat->shield_bonus;
		$nat_armor = $combat->nat_armor;
		$deflection_mod = $combat->deflection_mod;
		$misc_mod = $combat->misc_mod;
		
		$sql = "INSERT INTO `char_combat` (`char_id`, `hp_total`, `hp_dr`, `speed_base`, `speed_armor`, `init_misc_mod`, `base_attack_bonus`) VALUES ('$id', '$hp_total', '$hp_dr', '$speed_base', '$speed_armor', '$init_misc_mod', '$base_attack_bonus')";
		$result = $db->exec($sql);
		$sql = "INSERT INTO `char_armor` (`char_id`, `armor_bonus`, `shield_bonus`, `nat_armor`, `deflection_mod`, `misc_mod`) VALUES ('$id', '$armor_bonus', '$shield_bonus', '$nat_armor', '$deflection_mod', '$misc_mod')";
		$result = $db->exec($sql);
		
		// saving throws
		foreach($savingThrows->savingThrows as $savingThrow) {
			$ref_id = $savingThrow->ref_id;
			$name = $savingThrow->name;
			$base_save = $savingThrow->base_save;
			$magic_mod = $savingThrow->magic_mod;
			$misc_mod = $savingThrow->misc_mod;
			$temp_mod = $savingThrow->temp_mod;
			$sql = "INSERT INTO `char_saving_throws` (`char_id`, `ref_id`, `name`, `base_save`, `magic_mod`, `misc_mod`, `temp_mod`) VALUES ('$id', '$ref_id', '$name', '$base_save', '$magic_mod', '$misc_mod', '$temp_mod')";
			$result = $db->exec($sql);
		}
		
		echo 1;
	}
} catch (PDOException $e) {
	print "Error!: " . $e->getMessage() . "<br/>";
	die();
}
?>