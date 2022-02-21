<?php
require("connect.php");

$mysqli = new mysqli($host, $user, $pw, $db);
$mysqli->query("SET CHARACTER SET utf8");
$mysqli->query("SET NAMES 'utf8'");
if ($mysqli->connect_error){
    die("Connection failed: " . $mysqli->connect_error);
}

$email = $mysqli->real_escape_string($_GET["email"]);

$res = $mysqli->query("SELECT user_name, user_fullname, email, user_pw FROM usuarios WHERE email= '$email'");
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
    print json_encode("RecoverOK"); 
    $row = mysqli_fetch_array($res);
 	mail($email, "Recuperar clave PreserVamos", "Hola! Por si olvidaste tu clave de PreserVamos, es: " . $row[3]);

}
?>				