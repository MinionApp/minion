<?php
$un=$_POST['username'];   
$basicInfo=json_decode($_POST['basicInfo']);
$abilities=json_decode($_POST['abilities']);

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
		$sql = "UPDATE `char_basic` SET `name` = '$name', `alignment` = '$alignment', `level` = '$level', `diety` = '$diety', `homeland` = '$homel', `race` = '$race', `size` = '$size', `gender` = '$gender', `age` = '$age', `height` = '$height', `weight` = '$weight', `hair` = '$hair', `eyes` = '$eyes', WHERE `char_id` = '$id'";
		$result = $db->exec($sql);
		
		// abilities
		$i = 0;
		foreach($abilities->abilities as $ability) {
			$name = $ability->name;
			$mod = $ability->mod;
			$sql = "UPDATE `char_ability_mod` SET `name` = '$name', `mod` = '$mod' WHERE `char_id` = '$id' AND `ref_id` = '$i'";
			$result = $db->exec($sql);
			$score = $ability->score;
			$sql = "UPDATE `char_ability` SET `score` = '$score' WHERE `char_id` = '$id' AND `ref_id` = '$i'";
			$result = $db->exec($sql);
			$i++;
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
		
		echo 1;
	}
} catch (PDOException $e) {
	print "Error!: " . $e->getMessage() . "<br/>";
	die();
}
?>