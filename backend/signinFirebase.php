<?php
require("connect.php");
// CONECTA A LA DB
$mysqli = new mysqli($host, $user, $pw, $db);
$mysqli->query("SET CHARACTER SET utf8");
$mysqli->query("SET NAMES 'utf8'");
if ($mysqli->connect_error){
	die("Connection failed: " . $mysqli->connect_error);
}
// FETCH  USUARIO (EMAIL) DE LA APP, EL QUE USO PARA CONECTARSE POR FIREBASE
$uid = $mysqli->real_escape_string($_GET["user_id"]);

// BUSCA EL USUARIO EN LA DB DE AppEAR
$res = $mysqli->query("SELECT * FROM usuarios WHERE user_name= '$uid'");

// ERROR CON LA DB
if (!$res) {
    print json_encode("Error");
    echo "<br />" . $mysql->error;
    exit;
}
// NO HAY ERROR EN LA DB
else {
	// EL USUARIO NO EXISTE
    if (mysqli_num_rows($res) == 0) {
		// CREA UNO NUEVO EN LA DB DE APPEAR, CON LOS DATOS DE FIREBASE
		
		$user = $uid;
        $email = $uid;
       // $password = $mysqli->real_escape_string($_GET["Password"]);
        $fullname = $mysqli->real_escape_string($_GET["FullName"]);
        $usertipo = "Google profile";
		$deviceID = $mysqli->real_escape_string($_GET["deviceID"]);
        
		$q = $mysqli->query("SELECT * FROM usuarios WHERE email = '$uid' or user_name = '$uid'");
        $count = mysqli_num_rows($q);
          
        if ($count == 0)
            {          
				$res = $mysqli->query("INSERT INTO usuarios (user_name, user_fullname, email, deviceID) VALUES ('$user','$fullname', '$email', '$deviceID')");
				print json_encode ("Mail");
				//CIERRA
				exit;
            }          
        else {print json_encode ("MailInUse");}
		
		//CIERRA
		exit;
} else {
	// EL USUARIO EXISTE
	print json_encode("Login OK");
	while($row = mysqli_fetch_array($res)) {
		$arr = array('user_id' => $row["user_id"], 'username' => $row["user_name"], 'user_fullname' => $row["user_fullname"], 'location' => $row["location"], 'email' => $row["email"], 
		'org' => $row["org"], 'tipousuario' => $row["tipousuario"], 'puntostotales' => $row["puntostotales"], 'numfotosok' => $row["numfotosok"], 'puntosfotos' => $row["puntosfotos"], 
		'numevalsok' => $row["numevalsok"], 'puntosevals' => $row["puntosevals"], 'numriollanura' => $row["numriollanura"], 'numriomontana' => $row["numriomontana"], 
		'numlaguna' => $row["numlaguna"], 'numestuario' => $row["numestuario"], 'numshares' => $row["numshares"], 'msjprivado' => $row["msjprivado"], 'user_pw' => $row["user_pw"]);
        print json_encode($arr);
	}
}
}
?>				