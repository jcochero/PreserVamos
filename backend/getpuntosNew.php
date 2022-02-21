<?php
require("connect.php");
$mysqli = new mysqli($host, $user, $pw, $db);
$mysqli->query("SET CHARACTER SET utf8");
$mysqli->query("SET NAMES 'utf8'");
if ($mysqli->connect_error){
    die("Connection failed: " . $mysqli->connect_error);
}

$uid = $mysqli->real_escape_string($_GET["user_id"]);
$res = $mysqli->query("SELECT puntostotales, puntosfotos, puntosevals, numevalsok, numriollanura, numriomontana, numlaguna, numestuario, numshares FROM usuarios WHERE user_name= '$uid'");
if (!$res) {
    print json_encode("Error");
    echo "<br />" . $mysqli->error;
    exit;
}
else {
    if (mysqli_num_rows($res) == 0) {
        print json_encode("Not Found");
        exit;
    }
    print json_encode("GetPuntos OK");
	while($row = mysqli_fetch_array($res)) {
        $arr = array('puntostotales' => $row["puntostotales"], 'puntosfotos' => $row["puntosfotos"],
            'numevalsok' => $row["numevalsok"], 'puntosevals' => $row["puntosevals"], 'numriollanura' => $row["numriollanura"], 'numriomontana' => $row["numriomontana"],
            'numlaguna' => $row["numlaguna"], 'numestuario' => $row["numestuario"], 'numshares' => $row["numshares"]);
        print json_encode($arr);
    }
}
?>				