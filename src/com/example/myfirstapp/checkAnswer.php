<?php
$un=$_POST['username'];
$question=$_POST['question'];  
$answer=$_POST['answer'];

//connect to the db  
$user = "elefse";  
$pswd = "5jL9rFwi";  
$db = "test";
$db = new PDO("mysql:dbname=test;host=cubist.cs.washington.edu", "elefse", "5jL9rFwi");

$rows = $db->query($sql = "SELECT * FROM testLogin WHERE (username = '$un' AND question = '$question' AND answer = '$answer')");

if($count = $rows->rowCount() > 0) {
	echo 1;  // for correct login response  
} else  {
	echo 0; // for incorrect login response  
}
?> 