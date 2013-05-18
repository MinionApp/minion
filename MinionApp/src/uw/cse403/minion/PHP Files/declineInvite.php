<?php
$un=$_POST['un']; 
$group=$_POST['group'];

try {
	//connect to the db
	$db = new PDO("mysql:dbname=sahabp_minion;host=cubist.cs.washington.edu", "sahabp", "9BJju8xn");
	
	$sql = "DELETE FROM group_user WHERE groupname = '$group' AND username = '$un'";
	$result = $db->exec($sql);
	echo 1;
} catch (PDOException $e) {
	print "Error!: " . $e->getMessage() . "<br/>";
	die();
}
?>