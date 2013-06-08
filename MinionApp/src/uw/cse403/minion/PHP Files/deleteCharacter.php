<?php 
$characterInfo=json_decode($_POST['characterInfo']);

try {
	//connect to the db
	$db = new PDO("mysql:dbname=sahabp_minion;host=cubist.cs.washington.edu", "sahabp", "9BJju8xn");

	$username = $characterInfo->username;
	$local_id = $characterInfo->local_id;
	$sql = "DELETE FROM `Characters` WHERE `username` = '$username' AND `local_id` = '$local_id'";
	$result = $db->exec($sql);
	echo $local_id; 
} catch (PDOException $e) {
	print "Error!: " . $e->getMessage() . "<br/>";
	die();
}
?>