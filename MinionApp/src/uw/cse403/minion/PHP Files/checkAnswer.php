<?php
$un=$_POST['username'];
$question=$_POST['question'];  
$answer=$_POST['answer'];

try {
	//connect to the db  
	$db = new PDO("mysql:dbname=sahabp_minion;host=cubist.cs.washington.edu", "sahabp", "9BJju8xn");
	
	$rows = $db->query($sql = "SELECT * FROM User WHERE (username = '$un' AND question = '$question' AND answer = '$answer')");
	
	if($count = $rows->rowCount() > 0) {
		echo 1;  // for correct login response  
	} else  {
		echo 0; // for incorrect login response  
	}
} catch (PDOException $e) {
	print "Error!: " . $e->getMessage() . "<br/>";
	die();
}
?> 