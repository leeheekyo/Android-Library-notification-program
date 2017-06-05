<?php
$servername = "localhost";
$username = "root";
$password = "qkdldh3469";
$dbname = "temp";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

$count = 0;
// Check connection
if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
} 

$uid = $_GET['id'];
$sql = "SELECT * FROM StudyRoomReservationUserInfo WHERE user1=".$uid." OR user2=".$uid." OR user3=".$uid." OR user4=".$uid." OR user5=".$uid;
$result = $conn->query($sql);

if ($result->num_rows > 0) {
 while($row = $result->fetch_assoc()) {
  $sql2 = "SELECT * FROM StudyRoomReservation WHERE id=".$row["id"];
  $result2=$conn->query($sql2);
  if($row2 = $result2->fetch_assoc()){
       //echo $row2['rentalPeriod'] . "<br />";
   echo $row2["studyRoomNumber"]. "." . $row2["studyRoomReservationTime"]. "/";
  }
 }
} else {
  echo "0";
}
$conn->close();
?>
