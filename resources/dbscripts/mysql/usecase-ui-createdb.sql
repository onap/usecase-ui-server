--
-- Copyright (C) 2017 CMCC, Inc. and others. All rights reserved.
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
--     http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

/******************drop old database and user***************************/
use mysql;
drop database IF EXISTS usercaseui;
delete from user where User='usecaseui';
FLUSH PRIVILEGES;

/******************create new database and user***************************/
create database usercaseui CHARACTER SET utf8;

GRANT ALL PRIVILEGES ON usercaseui.* TO 'usercaseui'@'%' IDENTIFIED BY 'usercaseui' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON mysql.* TO 'usercaseui'@'%' IDENTIFIED BY 'usercaseui' WITH GRANT OPTION;

GRANT ALL PRIVILEGES ON usercaseui.* TO 'usercaseui'@'localhost' IDENTIFIED BY 'usercaseui' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON mysql.* TO 'usercaseui'@'localhost' IDENTIFIED BY 'usercaseui' WITH GRANT OPTION;
FLUSH PRIVILEGES; 