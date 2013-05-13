<?php
$un=$_POST['username'];  
$pw=$_POST['password']; 

//connect to the db
$user = "elefse";
$pswd = "5jL9rFwi";
$db = "test";
$db = new PDO("mysql:dbname=test;host=cubist.cs.washington.edu", "elefse", "5jL9rFwi");

$sql = "UPDATE testLogin SET password = '$pw' WHERE username = '$un'";
$result = $db->exec($sql);
echo 1;  // for username available
?>