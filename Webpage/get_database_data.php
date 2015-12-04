<!DOCTYPE html>
<html>
<head>
<style>
table {
    width: 100%;
    border-collapse: collapse;
}

table, td, th {
    border: 1px solid black;
    padding: 5px;
}

th {
	text-align: left;
}
</style>
</head>
<body>
<?php
$part = intval($_GET['selection']);
$name = intval($_GET['search'])


$host = 'localhost';
$port = '5432';
$database = 'ohmbaseopenwei';
$user = 'postgres';
$password = '1234567890';

$connectString = 'host=' . $host . ' port=' . $port . ' dbname=' . $database . 
	' user=' . $user . ' password=' . $password;


$link = pg_connect ($connectString);
if (!$link)
{
	alert("Error no connect")
	die('Error: Could not connect: ' . pg_last_error());
}

if($name===""){
$query = "SELECT name,notes,quantity,last_modified,spec_sheets FROM ".$part."ORDER BY name ASC";
$result = pg_query($query);
}
else{
$query = "SELECT name,notes,quantity,last_modified,spec_sheets FROM ".$part." WHERE name like '%".$name."%' ORDER BY name ASC";
$result = pg_query($query);
if (empty($result)){
	$input = preg_split("/( |.|,|:|;) /",$name);
	$copy1 = $input
	foreach ($input as $temp){
		if(next($copy1))){
			$wherecaseand = $wherecaseand."name LIKE ".$temp." AND ";
		}
		else {
			$wherecaseand = $wherecaseand."name LIKE ".$temp;
		}
	}
	$result = pg_query($query);
		if (empty($result)){
			$copy2 = $input
			foreach ($input as $temp){
				if(next($copy2))){
					$wherecaseor = $wherecaseor."name LIKE ".$temp." OR ";
				}
				else {
					$wherecaseor = $wherecaseor."name LIKE ".$temp;
				}
			}
			$result = pg_query($query);
		}
	
}
}


echo "<table>
<tr>
<th>Name</th>
<th>Notes</th>
<th>Quantity</th>
<th>Last Modified</th>
<th>Spec Sheets</th>
</tr>";
while ($row = pg_fetch_row($result)) 
{
	echo '<tr>';
	$count = count($row);
	$y = 0;
	while ($y < $count)
	{
		$c_row = current($row);
		echo '<td>' . $c_row . '</td>';
		next($row);
		$y = $y + 1;
	}
	echo '</tr>';
	$i = $i + 1;
}
echo "</table>";
pg_free_result($result);
pg_close($link)
?>
</body>
</html>