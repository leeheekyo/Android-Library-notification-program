-- UserInfo Table Create SQL
CREATE TABLE UserInfo
(
    `email`     VARCHAR(45)    NOT NULL, 
    `id`        INT(9)         NULL        AUTO_INCREMENT, 
    `name`      VARCHAR(45)    NULL, 
    `password`  VARCHAR(45)    NULL, 
    `userType`  int(1)         NULL, 
    PRIMARY KEY (id)
);

-- StudyRoomReservationUserInfo Table Create SQL
CREATE TABLE StudyRoomReservationUserInfo
(
    `id`                          INT       NULL        AUTO_INCREMENT, 
    `StudyRoomReservationInfoID`  INT       NULL, 
    `user1`                       INT(9)    NULL, 
    `user2`                       INT(9)    NULL, 
    `user3`                       INT(9)    NULL, 
    `user4`                       INT(9)    NULL, 
    `user5`                       INT(9)    NULL, 
    PRIMARY KEY (id)
);

ALTER TABLE StudyRoomReservationUserInfo ADD CONSTRAINT FK_StudyRoomReservationUserInfo_user1_UserInfo_id FOREIGN KEY (user1)
 REFERENCES UserInfo (id)  ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE StudyRoomReservationUserInfo ADD CONSTRAINT FK_StudyRoomReservationUserInfo_user2_UserInfo_id FOREIGN KEY (user2)
 REFERENCES UserInfo (id)  ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE StudyRoomReservationUserInfo ADD CONSTRAINT FK_StudyRoomReservationUserInfo_user3_UserInfo_id FOREIGN KEY (user3)
 REFERENCES UserInfo (id)  ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE StudyRoomReservationUserInfo ADD CONSTRAINT FK_StudyRoomReservationUserInfo_user4_UserInfo_id FOREIGN KEY (user4)
 REFERENCES UserInfo (id)  ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE StudyRoomReservationUserInfo ADD CONSTRAINT FK_StudyRoomReservationUserInfo_user5_UserInfo_id FOREIGN KEY (user5)
 REFERENCES UserInfo (id)  ON DELETE RESTRICT ON UPDATE RESTRICT;

