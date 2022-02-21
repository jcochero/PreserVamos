<?php
require("connect.php");

$mysqli = new mysqli($host, $user, $pw, $db);
$mysqli->query("SET CHARACTER SET utf8");
$mysqli->query("SET NAMES 'utf8'");
if ($mysqli->connect_error){
    die("Connection failed: " . $mysqli->connect_error);
}

$res = $mysqli->query("SELECT id, nombre, localidad, provincia, tipo, admin FROM organizaciones");
if (!$res) {
    print json_encode("Error");
    echo "<br />" . $mysql->error;
    exit;
}
else {
    if (mysqli_num_rows($res) == 0) {
        print json_encode("Not Found");
        exit;
}
	
	print json_encode("OrgsOK");
	$rowcount = mysqli_num_rows($res);
	print json_encode($rowcount);
	while($row = mysqli_fetch_array($res)) {
		$arr = array('id' => $row["id"], 'nombre' => $row["nombre"], 'localidad' => $row["localidad"], 'provincia' => $row["provincia"], 'tipo' => $row["tipo"], 'admin' => $row["admin"]);
        print json_encode($arr);
	}  
	
}
?>				