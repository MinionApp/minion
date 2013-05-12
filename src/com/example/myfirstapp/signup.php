<?php
$un=$_POST['username'];  
$pw=$_POST['password']; 
$question=$_POST['question']; 
$answer= $_POST['answer'];

//connect to the db
$user = "elefse";
$pswd = "5jL9rFwi";
$db = "test";
$db = new PDO("mysql:dbname=test;host=cubist.cs.washington.edu", "elefse", "5jL9rFwi");

$rows = $db->query($sql = "SELECT * FROM testLogin WHERE username = '$un'");

if($count = $rows->rowCount() > 0) {
	echo 0; // for username unavailable
} else  {
	$sql = "INSERT INTO testLogin (username, password, question, answer) VALUES ('$un', '$pw', '$question', '$answer')";
	$result = $db->exec($sql);
	echo 1;  // for username available
}
?>