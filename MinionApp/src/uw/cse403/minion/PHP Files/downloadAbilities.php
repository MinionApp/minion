<?php
$un=$_POST['un'];

try {
	//connect to the db
	$db = new PDO("mysql:dbname=sahabp_minion;host=cubist.cs.washington.edu", "sahabp", "9BJju8xn");
	$rows = $db->query($sql = "SELECT `ca`.`ref_id`, `ca`.`score`, `cab`.`mod`, `cab`.`name` FROM `char_ability` `ca` INNER JOIN `Characters` `c` ON `c`.`_id` = `ca`.`char_id` NATURAL JOIN `char_ability_mod` `cab` WHERE `c`.`username` = '$un'");
	//$results = $rows->fetchAll(PDO::FETCH_OBJ);
	
	$json_string = '';
	$first_loop = true;
	$first_loop_for_abilities = true;
	$i = 0;
	while ($row = $rows->fetch(PDO::FETCH_ASSOC, PDO::FETCH_ORI_NEXT)) {
		if ($i == 0 && $first_loop) {
			$json_string = $json_string . '{"abilities":[';
			$json_string = $json_string . json_encode($row);
			$i++;
		} else if ($i == 0 && $first_loop == false) {
			$json_string = $json_string . ',{"abilities":[';
			$json_string = $json_string . json_encode($row);
			$i++;
		} else if ($i == 5) {
			$json_string = $json_string . ',' . json_encode($row) . ']}';
			$i = 0;
			$first_loop = false;
		} else {
			$json_string = $json_string . ',' . json_encode($row);
			$i++;
		}
	}
	echo '{"abilitiesInfo":['. $json_string .']}';
} catch (PDOException $e) {
	print "Error!: " . $e->getMessage() . "<br/>";
	die();
}
?>