<?php
$un=$_POST['username'];  
$pw=$_POST['password']; 
$question=$_POST['question']; 
$answer= $_POST['answer'];

//connect to the db
$db = new PDO("mysql:dbname=sahabp_minion;host=cubist.cs.washington.edu", "sahabp", "9BJju8xn");

$rows = $db->query($sql = "SELECT * FROM User WHERE username = '$un'");

if($count = $rows->rowCount() > 0) {
	echo 0; // for username unavailable
} else  {
	$sql = "INSERT INTO User (username, password, question, answer) VALUES ('$un', '$pw', '$question', '$answer')";
	$result = $db->exec($sql);
	echo 1;  // for username available
}
?>