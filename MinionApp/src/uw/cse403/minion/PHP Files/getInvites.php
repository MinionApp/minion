<?php
$un=$_POST['un'];

try {
	//connect to the db
	$db = new PDO("mysql:dbname=sahabp_minion;host=cubist.cs.washington.edu", "sahabp", "9BJju8xn");

	$rows = $db->query($sql = "SELECT * FROM group_user WHERE username = '$un'");
	if($count = $rows->rowCount() > 0) {
		foreach ($rows as $row) {
			echo $row["groupname"]; // for pending invites
		}
	} else  {
		echo 0; // for no pending invites
	}
} catch (PDOException $e) {
	print "Error!: " . $e->getMessage() . "<br/>";
	die();
}
?>