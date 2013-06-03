<?php
$un=$_POST['un'];

try {
	//connect to the db
	$db = new PDO("mysql:dbname=sahabp_minion;host=cubist.cs.washington.edu", "sahabp", "9BJju8xn");

	$rows = $db->query($sql = "SELECT * FROM `char_basic` `cb` INNER JOIN `Characters` `c` ON `c`.`_id` = `cb`.`char_id` WHERE `c`.`username` = '$un'");
	$results = $rows->fetchAll(PDO::FETCH_OBJ);
	echo '{"basicInfo":'. json_encode($results) .'}';
} catch (PDOException $e) {
	print "Error!: " . $e->getMessage() . "<br/>";
	die();
}
?>