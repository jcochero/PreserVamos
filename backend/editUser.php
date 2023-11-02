<?php
require("connect.php");

$mysqli = new mysqli($host, $user, $pw, $db);
$mysqli->query("SET CHARACTER SET utf8");
$mysqli->query("SET NAMES 'utf8'");
if ($mysqli->connect_error){
    die("Connection failed: " . $mysqli->connect_error);
}
$action = $_GET["Action"];
switch ($action) {
    case "EditPass":
        $userid = $mysqli->real_escape_string($_GET["UserID"]);
        $password = $mysqli->real_escape_string($_GET["Password"]);
        $fullname = $mysqli->real_escape_string($_GET["FullName"]);
        $location = $mysqli->real_escape_string($_GET["Location"]);
        $org = $mysqli->real_escape_string($_GET["usergroup"]);
        $usertipo = $mysqli->real_escape_string($_GET["usertipo"]);
		$deviceID = $mysqli->real_escape_string($_GET["deviceID"]);
		$useremail = $mysqli->real_escape_string($_GET["useremail"]);
      

		$res = $mysqli->query("UPDATE usuarios SET user_pw ='$password', user_fullname='$fullname', location='$location', org='$org', tipousuario='$usertipo', deviceID='$deviceID' WHERE email='$useremail'");

		if (!$res) {
			print json_encode("Error");
			echo "<br />" . $mysqli->error;
			exit;
		}
		else {
		  print json_encode ("EditOK");
		 
		}
    case "EditNoPass":
        $userid = $mysqli->real_escape_string($_GET["UserID"]);
        $fullname = $mysqli->real_escape_string($_GET["FullName"]);
        $location = $mysqli->real_escape_string($_GET["Location"]);
        $org = $mysqli->real_escape_string($_GET["usergroup"]);
        $usertipo = $mysqli->real_escape_string($_GET["usertipo"]);
		$deviceID = $mysqli->real_escape_string($_GET["deviceID"]);
		$useremail = $mysqli->real_escape_string($_GET["useremail"]);
      

		$res = $mysqli->query("UPDATE usuarios SET user_fullname='$fullname', location='$location', org='$org', tipousuario='$usertipo', deviceID='$deviceID' WHERE email='$useremail'");

		if (!$res) {
			print json_encode("Error");
			echo "<br />" . $mysqli->error;
			exit;
		}
		else {
		  print json_encode ("EditOK");
		 
		}
}
mysqli_close($mysqli);
?>
