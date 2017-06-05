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
if ($_GET['studyRoomNumber'] && $_GET['studyRoomReservationTime'] && $_GET['user1'] && $_GET['user2'] && $_GET['user3'] && $_GET['user4'] && $_GET['user5']){ 

 $sql = "SELECT * FROM StudyRoomReservation";
 $result = $conn->query($sql);
 $number = 0;
 if ($result->num_rows > 0) {
  while($row = $result->fetch_assoc()) {
   $number = $row["id"];
  }
 } 
 $number+=1;

 $sql ="insert into StudyRoomReservation values(".$number.",1, ". $_GET['studyRoomNumber']. ",".$_GET['studyRoomReservationTime'].")"; 
//"INSERT INTO UserInfo VALUES ('glhk5895@naver.com', 201224521, 'HeekyoLee','test', 0)";

 if ($conn->query($sql) === TRUE) {

  $sql = "insert into StudyRoomReservationUserInfo(id,StudyRoomReservationInfoID, user1, user2, user3, user4, user5) values(".$number.", ".$number.", ". $_GET['user1']. ",".$_GET['user2'].",".$_GET['user3'].",".$_GET['user4'].",".$_GET['user5'].")";
  if($conn->query($sql) === TRUE) {
    echo "0";
  } else {
      echo "2";
  }
 } else {
    echo "1";
 }
}

$conn->close();
?> 
