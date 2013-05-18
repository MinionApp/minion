<?php
$un=$_POST['un'];

try {
	//connect to the db
	$db = new PDO("mysql:dbname=sahabp_minion;host=cubist.cs.washington.edu", "sahabp", "9BJju8xn");

	$rows = $db->query($sql = "SELECT groupname FROM group_user WHERE username = '$un'");
	$results = $rows->fetchAll(PDO::FETCH_OBJ);
	echo '{"items":'. json_encode($results) .'}';
} catch (PDOException $e) {
	print "Error!: " . $e->getMessage() . "<br/>";
	die();
}
?>