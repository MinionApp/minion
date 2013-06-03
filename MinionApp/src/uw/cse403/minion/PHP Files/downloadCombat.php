<?php
$un=$_POST['un'];

try {
	//connect to the db
	$db = new PDO("mysql:dbname=sahabp_minion;host=cubist.cs.washington.edu", "sahabp", "9BJju8xn");

	$rows = $db->query($sql = "SELECT * FROM `char_combat` `cc` INNER JOIN `Characters` `c` ON `c`.`_id` = `cc`.`char_id` NATURAL JOIN `char_armor` `ca` WHERE `c`.`username` = '$un'");
	$results = $rows->fetchAll(PDO::FETCH_OBJ);
	echo '{"combatInfo":'. json_encode($results) .'}';
} catch (PDOException $e) {
	print "Error!: " . $e->getMessage() . "<br/>";
	die();
}
?>