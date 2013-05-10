<?php
// Connects to your Database
mysql_connect("cubist.cs.washington.edu","elefse","5jL9rFwi") or die(mysql_error());
mysql_select_db("test") or die(mysql_error());
$data = mysql_query("SELECT * FROM testLogin") or die(mysql_error());
while($row=mysql_fetch_assoc($data))
$output[]=$row;
print(json_encode($output));
mysql_close();
?>