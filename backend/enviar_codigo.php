<?php
$email = htmlspecialchars($_GET["email"]);
$codigo = htmlspecialchars($_GET["codigo"]);

///Email sending

$subject = "PreserVamos - Código de acceso";
$message = "<b>Has requerido un código de acceso</b>";
$message .= "<br></br>";
$message .= "Tu código es: ";
$message .= $codigo;
$message .= "<br></br>";
$header = "From:hola@preservamos.ar \r\n";
$header .= "MIME-Version: 1.0\r\n";
$header .= "Content-type: text/html; charset=utf-8 \r\n";
$retval = mail ($email,$subject,$message,$header);
    if( $retval == true ) {
		 print json_encode ("CodigoOk");
    }else {
		 print json_encode ("CodigoNotOk");    
    }
?>