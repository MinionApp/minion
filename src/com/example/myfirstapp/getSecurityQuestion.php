<?php 
$un=$_POST['username'];    
//connect to the db  
$user = "elefse";  
$pswd = "5jL9rFwi";  
$db = "test";
$db = new PDO("mysql:dbname=test;host=cubist.cs.washington.edu", "elefse", "5jL9rFwi");
  
$rows = $db->query($sql = "SELECT question FROM testLogin WHERE username = '$un'");

if($count = $rows->rowCount() > 0) {
	foreach ($rows as $row) {
		echo strtr($row["question"]," ", "_"); //replaces spaces with underscores
	}  
} else  {
	echo 0; 
}
?> 