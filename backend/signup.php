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
    case "Register":
        $user = $mysqli->real_escape_string($_GET["UserID"]);
        $email = $mysqli->real_escape_string($_GET["Email"]);
        $password = $mysqli->real_escape_string($_GET["Password"]);
		$userTipoUsuario = $mysqli->real_escape_string($_GET["userTipoUsuario"]);
		$userOrg = $mysqli->real_escape_string($_GET["userOrg"]);
//        $fullname = $mysqli->real_escape_string($_GET["FullName"]);
//        $location = $mysqli->real_escape_string($_GET["Location"]);
//        $org = $mysqli->real_escape_string($_GET["Org"]);
//        $usertipo = $mysqli->real_escape_string($_GET["usertipo"]);
		$deviceID = $mysqli->real_escape_string($_GET["deviceID"]);		
      
        $q = $mysqli->query("SELECT * FROM usuarios WHERE email = '$email' or user_name = '$user'");
        $count = mysqli_num_rows($q);
            
        if ($count == 0)
            {          
				$res = $mysqli->query("INSERT INTO usuarios (user_name, user_pw, email, deviceID, org, tipousuario) VALUES ('$user', '$password','$email','$deviceID', $userOrg , $userTipoUsuario)");
				print json_encode ("Mail");
            }          
        else{
            print json_encode ("MailInUse");      
            }
}
?>