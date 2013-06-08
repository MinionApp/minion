<?php 
$groupname=$_POST['groupName'];
$newGroupname=$_POST['newGroupName'];

try {
	//connect to the db
	$db = new PDO("mysql:dbname=sahabp_minion;host=cubist.cs.washington.edu", "sahabp", "9BJju8xn");

	$sql = "UPDATE `Group` SET `groupname` = '$newGroupname' WHERE groupname = '$groupname'";
	$result = $db->exec($sql);

	echo 1;  // for username available
} catch (PDOException $e) {
	print "Error!: " . $e->getMessage() . "<br/>";
	die();
}
?>