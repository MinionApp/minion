<?php 
$un=$_POST['username'];

try {
	//connect to the db  
	$db = new PDO("mysql:dbname=sahabp_minion;host=cubist.cs.washington.edu", "sahabp", "9BJju8xn");
	  
	$rows = $db->query($sql = "SELECT question FROM User WHERE username = '$un'");
	
	if($count = $rows->rowCount() > 0) {
		foreach ($rows as $row) {
			echo strtr($row["question"]," ", "_"); //replaces spaces with underscores
		}  
	} else  {
		echo 0; 
	}
} catch (PDOException $e) {
	print "Error!: " . $e->getMessage() . "<br/>";
	die();
}
?> 