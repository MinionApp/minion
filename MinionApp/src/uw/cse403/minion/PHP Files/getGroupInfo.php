<?php
$un=$_POST['un'];
$group=$_POST['groupName'];

try {
	//connect to the db
	$db = new PDO("mysql:dbname=sahabp_minion;host=cubist.cs.washington.edu", "sahabp", "9BJju8xn");

	$rows = $db->query($sql = "SELECT `username`, `character` FROM `group_user` WHERE `groupname` = '$group'");
	$results = $rows->fetchAll(PDO::FETCH_OBJ);
	
	$rows = $db->query($sql = "SELECT * FROM `invites` WHERE `username` = '$un' AND `groupname` = '$group'");
	$pendingInvite = false;
	if($count = $rows->rowCount() > 0) {
		$pendingInvite = true;
	}
	
	$rows = $db->query($sql = "SELECT * FROM `Group` WHERE `gm` = '$un' AND `groupname` = '$group'");
	$isGM = false;
	if($count = $rows->rowCount() > 0) {
		$isGM = true;
	}
	echo '{"items":'. json_encode($results) .', "pendingInvite":'. json_encode($pendingInvite) .', "isGM":'. json_encode($isGM) .'}';
} catch (PDOException $e) {
	print "Error!: " . $e->getMessage() . "<br/>";
	die();
}
?>