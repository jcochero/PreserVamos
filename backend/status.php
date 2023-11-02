<?
//SCRIPT PARA EL TOP 10
require("connect.php");
$mysqli = new mysqli($host, $user, $pw, $db);
$mysqli->query("SET CHARACTER SET utf8");
$mysqli->query("SET NAMES 'utf8'");
if ($mysqli->connect_error){
   die("Connection failed: " . $mysqli->connect_error);
}

$res = $mysqli->query("SELECT user_id, user_name, email FROM usuarios WHERE user_id > '796' ORDER BY user_id DESC");

echo "TOTAL DE USUARIOS:";
echo mysqli_num_rows($res);
echo "<table {border: 2px outset grey / darkgray; padding: 1px }>";
echo "<tr>";
echo "<th> Id </th>";
echo "<th> User </th>";
echo "<th> Email </th>";
echo "</tr>";

while($row = mysqli_fetch_array($res))
{
   echo "<tr>";
   echo "<td>" . $row["user_id"] . "</td>";
   echo "<td>" . $row["user_name"] . "</td>";
   echo "<td>" . $row["email"] . "</td>";
   echo "</tr>";
}	
echo("</table>");
mysqli_close($mysqli);
?>				


<?php
require("connectmapa.php");

$mysqlimap = new mysqli($host, $user, $pw, $db);
if ($mysqlimap->connect_error){
   die("Connection failed: " . $mysqlimap->connect_error);
}
$uid = $mysqlimap->real_escape_string($_GET["user_id"]);

$resmapa = $mysqlimap->query("SELECT * FROM markers WHERE id > '990' ORDER BY Id");

echo "<table {border: 2px outset grey / darkgray; padding: 1px }>";
echo "<br>";
echo "</br>";
echo "TOTAL DE PUNTOS:";
echo mysqli_num_rows($resmapa);
echo "<tr>";
echo "<th> Id </th>";
echo "<th> lat </th>";
echo "<th> long </th>";
echo "<th> dateandtime </th>";
echo "<th> indice </th>";
echo "<th> usuario </th>";
echo "</tr>";
	
	while($rowmapa = mysqli_fetch_array($resmapa)) {
   echo "<tr>";
   echo "<td>" . $rowmapa["id"] . "</td>";
   echo "<td>" . $rowmapa["lat"] . "</td>";
   echo "<td>" . $rowmapa["lng"] . "</td>";
   echo "<td>" . $rowmapa["dateandtime"] . "</td>";
   echo "<td>" . $rowmapa["indice"] . "</td>";
   echo "<td>" . $rowmapa["username"] . "</td>";

   echo "</tr>";
	}
	mysqli_close($mysqlimap);
?>