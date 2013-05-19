<?php
$un=$_POST['username'];   
$basicInfo=json_decode($_POST['basicInfo']);
$abilities=json_decode($_POST['abilities']);

try {
	//connect to the db
	$db = new PDO("mysql:dbname=sahabp_minion;host=cubist.cs.washington.edu", "sahabp", "9BJju8xn");
	$charID = $basicInfo->local_id;
	$rows = $db->query($sql = "SELECT * FROM `Characters` WHERE `username` = 'un' AND `local_id` = '$charID'");
	if($count = $rows->rowCount() > 0) {
		// Character does exist, update
		$sql = "UPDATE `Characters` SET `local_id` = '100' WHERE `username` = 'test'";
		$result = $db->exec($sql);
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
		foreach($abilities->abilities as $ability) {
			$name = $ability->name;
			$mod = $ability->mod;
			$sql = "INSERT INTO `char_ability_mod` (`char_id`, `ref_id`, `name`, `mod`) VALUES ('$id', '$charID', '$name', '$mod')";
			$result = $db->exec($sql);
			$score = $ability->score;
			$sql = "INSERT INTO char_ability (char_id, ref_id, score) VALUES ('$id', '$charID', '$score')";
			$result = $db->exec($sql);
		}
		
		// skills
		
		echo 1;
	}
} catch (PDOException $e) {
	print "Error!: " . $e->getMessage() . "<br/>";
	die();
}
?>