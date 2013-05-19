<?php
$group=$_POST['group'];
$u1=$_POST['user1'];
$u2=$_POST['user2'];
$u3=$_POST['user3'];
$u4=$_POST['user4'];
$u5=$_POST['user5'];
$gm=$_POST['gm'];

try {
	//connect to the db
	$db = new PDO("mysql:dbname=sahabp_minion;host=cubist.cs.washington.edu", "sahabp", "9BJju8xn");
	
	$sql = "INSERT INTO `Group` (`groupname`, `num_users`, `gm`) VALUES ('$group', '1', '$gm')";
	$result = $db->exec($sql);
	
	$sql = "INSERT INTO invites (groupname, username) VALUES ('$group', '$u1'), ('$group', '$u2'), ('$group', '$u3'), ('$group', '$u4'), ('$group', '$u5')";
	$result = $db->exec($sql);
	echo 1; 
} catch (PDOException $e) {
	print "Error!: " . $e->getMessage() . "<br/>";
	die();
}
?>