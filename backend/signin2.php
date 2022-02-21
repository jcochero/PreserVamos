<?php
require("connect.php");

$mysqli = new mysqli($host, $user, $pw, $db);
$mysqli->query("SET CHARACTER SET utf8");
$mysqli->query("SET NAMES 'utf8'");

if ($mysqli->connect_error){
    die("Connection failed: " . $mysqli->connect_error);
}

$uid = $mysqli->real_escape_string($_GET["user_id"]);
$pwd = $mysqli->real_escape_string($_GET["password"]);

$res = $mysqli->query("SELECT * FROM usuarios WHERE user_name= '$uid' AND user_pw= '$pwd'");

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
	print json_encode("Login OK");
    while($row = mysqli_fetch_array($res)) {
        $arr = array('id' => $row["user_id"], 'username' => $row["user_name"], 'user_fullname' => $row["user_fullname"], 'location' => $row["location"], 'email' => $row["email"], 'org' => $row["org"], 'group' => $row["usergroup"], 'tipousuario' => $row["tipousuario"], 'password' => $row["user_pw"], 'msjprivado' => $row["msjprivado"], 'evalsTotales' => $row["evalsTotales"], 'evalsok' => $row["numevalsok"], 'evalsCorregidas' => $row["evalsCorregidas"], 'evalsIncorrectas' => $row["evalsIncorrectas"], 'puntostotales' => $row["puntostotales"], 'puntosevals' => $row["puntosevals"], 'puntosfotos' => $row["puntosfotos"], 'numriollanura' => $row["numriollanura"], 'numriomontana' => $row["numriomontana"], 'numlaguna' => $row["numlaguna"], 'numestuario' => $row["numestuario"], 'numshares' => $row["numshares"]);
        print json_encode($arr);
    }
}
mysqli_close($mysqli);
?>
