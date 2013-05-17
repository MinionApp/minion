<?php
$un=$_POST['username'];  
$pw=$_POST['password']; 

//connect to the db
$db = new PDO("mysql:dbname=sahabp_minion;host=cubist.cs.washington.edu", "sahabp", "9BJju8xn");

$sql = "UPDATE User SET password = '$pw' WHERE username = '$un'";
$result = $db->exec($sql);
echo 1;  // for username available
?>