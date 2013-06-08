<?php
$un=$_POST['un'];

try {
	//connect to the db
	$db = new PDO("mysql:dbname=sahabp_minion;host=cubist.cs.washington.edu", "sahabp", "9BJju8xn");
	$rows = $db->query($sql = "SELECT `cs`.`ref_id`, `cs`.`ranks`, `cs`.`misc_mod`, `cs`.`title` FROM `char_skills` `cs` INNER JOIN `Characters` `c` ON `c`.`_id` = `cs`.`char_id` WHERE `c`.`username` = '$un'");
	//$results = $rows->fetchAll(PDO::FETCH_OBJ);
	
	$json_string = '';
	$first_loop = true;
	$i = 0;
	while ($row = $rows->fetch(PDO::FETCH_ASSOC, PDO::FETCH_ORI_NEXT)) {
		if ($i == 0 && $first_loop) {
			$json_string = $json_string . '{"skills":[';
			$json_string = $json_string . json_encode($row);
			$i++;
		} else if ($i == 0 && $first_loop == false) {
			$json_string = $json_string . ',{"skills":[';
			$json_string = $json_string . json_encode($row);
			$i++;
		} else if ($i == 38) {
			$json_string = $json_string . ',' . json_encode($row) . ']}';
			$i = 0;
			$first_loop = false;
		} else {
			$json_string = $json_string . ',' . json_encode($row);
			$i++;
		}
	}
	echo '{"skillsInfo":['. $json_string .']}';
} catch (PDOException $e) {
	print "Error!: " . $e->getMessage() . "<br/>";
	die();
}
?>