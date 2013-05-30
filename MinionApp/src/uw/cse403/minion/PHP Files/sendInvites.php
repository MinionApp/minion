<?php
$group=$_POST['group'];
$players=json_decode($_POST['players']);
$gm=$_POST['gm'];

try {
	//connect to the db
	$db = new PDO("mysql:dbname=sahabp_minion;host=cubist.cs.washington.edu", "sahabp", "9BJju8xn");
	
	$sql = "INSERT INTO `Group` (`groupname`, `num_users`, `gm`) VALUES ('$group', '1', '$gm')";
	$result = $db->exec($sql);
	
	$sql = "INSERT INTO group_user (groupname, username) VALUES ('$group', '$gm')";
	$result = $db->exec($sql);
	
	// players
	$i = 0;
	foreach($players->players as $player) {
		$name = $player->player;
		$sql = "INSERT INTO invites (groupname, username) VALUES ('$group', '$name')";
		$result = $db->exec($sql);
		$i++;
	}
	echo 1; 
} catch (PDOException $e) {
	print "Error!: " . $e->getMessage() . "<br/>";
	die();
}
?>