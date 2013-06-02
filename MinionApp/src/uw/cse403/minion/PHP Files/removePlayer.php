<?php 
$groupname=$_POST['groupName']; 
$player=$_POST['player'];

try {
	//connect to the db
	$db = new PDO("mysql:dbname=sahabp_minion;host=cubist.cs.washington.edu", "sahabp", "9BJju8xn");
	$sql = "SELECT `num_users` FROM `Group` WHERE `groupname` = '$groupname'";
	$rows = $db->query($sql);
	foreach ($rows as $row) {
		$num = $row["num_users"];
	}
	$num--;
	$sql = "UPDATE `Group` SET `num_users` = '$num' WHERE groupname = '$groupname'";
	$result = $db->exec($sql);

	$sql = "DELETE FROM `group_user` WHERE `groupname` = '$groupname' AND `username` = '$player'";
	$result = $db->exec($sql);
	echo 1;  // for username available
} catch (PDOException $e) {
	print "Error!: " . $e->getMessage() . "<br/>";
	die();
}
?>