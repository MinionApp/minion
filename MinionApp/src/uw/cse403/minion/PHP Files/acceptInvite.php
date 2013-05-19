<?php
$un=$_POST['un']; 
$group=$_POST['group'];  

try {
	//connect to the db
	$db = new PDO("mysql:dbname=sahabp_minion;host=cubist.cs.washington.edu", "sahabp", "9BJju8xn");
	$sql = "SELECT `num_users` FROM `Group` WHERE `groupname` = '$group'";
	$rows = $db->query($sql);
	foreach ($rows as $row) {
		$num = $row["num_users"];
	}
	$num++;
	$sql = "UPDATE `Group` SET `num_users` = '$num' WHERE groupname = '$group'";
	$result = $db->exec($sql);
	$sql = "INSERT INTO group_user (groupname, username) VALUES ('$group', '$un')";
	$result = $db->exec($sql);
	$sql = "DELETE FROM invites WHERE groupname = '$group' AND username = '$un'";
	$result = $db->exec($sql);
	echo $num;
} catch (PDOException $e) {
	print "Error!: " . $e->getMessage() . "<br/>";
	die();
}
?>