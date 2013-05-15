<?php 
$un=$_POST['username'];
   
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
?> 