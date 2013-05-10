<?php 
$un=$_POST['username'];  
$pw=$_POST['password'];  
//connect to the db  
$user = "elefse";  
$pswd = "5jL9rFwi";  
$db = "test";
$db = new PDO("mysql:dbname=test;host=cubist.cs.washington.edu", "elefse", "5jL9rFwi");
//$conn = mysql_connect("cubist.cs.washington.edu", $user, $pswd);  
$rows = $db->query($sql = "SELECT * FROM testLogin WHERE username = '$un' AND password = '$pw'");
//var_dump($rows);
//run the query to search for the username and password the match  
//$query = "SELECT * FROM testLogin WHERE username = test AND password = abcDEF123@";  
//$result = mysql_query($query) or die("Unable to verify user because : " . mysql_error());  
//this is where the actual verification happens 
$result = $rows->fetch(PDO::FETCH_ASSOC); 
if($count = $rows->rowCount() > 0) {
// ( $un == “ajay” && $pw == “ajay”)  
	echo 1;  // for correct login response  
} else  {
	echo 0; // for incorrect login response  
}
?> 