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

 $sql = "SELECT * FROM StudyRoomReservation";
 $result = $conn->query($sql);

 if ($result->num_rows > 0) {
  while($row = $result->fetch_assoc()) {
   echo $row["studyRoomNumber"]. "." . $row["studyRoomReservationTime"]. "/";
  }
 } else {
   echo "0";
 }
 $conn->close();
?>
