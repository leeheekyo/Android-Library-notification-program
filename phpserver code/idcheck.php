<?php
$servername = "localhost";
$username = "root";
$password = "qkdldh3469";
$dbname = "temp";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
} 

$sql = "SELECT password FROM UserInfo WHERE id=". $_GET['id'];
$result = $conn->query($sql);

if ( $result->num_rows > 0 ) {
        echo $result->fetch_assoc()["password"];
}
else{
    echo "0";
}
$conn->close();
?>
