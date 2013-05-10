<?php
$un=$_POST['username'];  
$pw=$_POST['password'];  
//connect to the db
$user = "elefse";
$pswd = "5jL9rFwi";
$db = "test";
$db = new PDO("mysql:dbname=test;host=cubist.cs.washington.edu", "elefse", "5jL9rFwi");
//$conn = mysql_connect("cubist.cs.washington.edu", $user, $pswd);
$rows = $db->query($sql = "SELECT * FROM testLogin WHERE username = '$un'");
//$result = $rows->fetch(PDO::FETCH_ASSOC);
if($count = $rows->rowCount() > 0) {
	echo 0; // for username unavailable
} else  {
	$sql = "INSERT INTO testLogin (username, password) VALUES ('$un', '$pw')";
	$result = $db->exec($sql);
	echo 1;  // for username available
}
?>