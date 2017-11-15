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

use usercaseui;

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for alarms_additionalinformation
-- ----------------------------
DROP TABLE IF EXISTS `alarms_additionalinformation`;
CREATE TABLE `alarms_additionalinformation` (
  `name` varchar(500),
  `value` varchar(500),
  `eventId` varchar(500),
  `createTime` datetime,
  `updateTime` datetime,
  `id` int(10) NOT NULL auto_increment,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for alarms_commoneventheader
-- ----------------------------
DROP TABLE IF EXISTS `alarms_commoneventheader`;
CREATE TABLE `alarms_commoneventheader` (
  `version` varchar(500),
  `eventName` varchar(500),
  `domain` varchar(500),
  `eventId` varchar(500),
  `eventType` varchar(500),
  `nfcNamingCode` varchar(500) DEFAULT NULL,
  `nfNamingCode` varchar(500) DEFAULT NULL,
  `sourceId` varchar(500),
  `sourceName` varchar(500),
  `reportingEntityId` varchar(500),
  `reportingEntityName` varchar(500),
  `priority` varchar(50),
  `startEpochMicrosec` varchar(500),
  `lastEpochMicroSec` varchar(500),
  `sequence` varchar(500),
  `faultFieldsVersion` varchar(500),
  `eventServrity` varchar(500),
  `eventSourceType` varchar(500),
  `eventCategory` varchar(500),
  `alarmCondition` varchar(500),
  `specificProblem` varchar(500),
  `vfStatus` varchar(500),
  `alarmInterfaceA` varchar(500),
  `status` varchar(50),
  `createTime` datetime,
  `updateTime` datetime,
  `id` int(10) NOT NULL auto_increment,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for performance_additionalinformation
-- ----------------------------
DROP TABLE IF EXISTS `performance_additionalinformation`;
CREATE TABLE `performance_additionalinformation` (
  `name` varchar(500),
  `value` varchar(500),
  `eventId` varchar(500),
  `createTime` datetime,
  `updateTime` datetime,
  `id` int(10) NOT NULL auto_increment,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for performance_commoneventheader
-- ----------------------------
DROP TABLE IF EXISTS `performance_commoneventheader`;
CREATE TABLE `performance_commoneventheader` (
  `version` varchar(500),
  `eventName` varchar(500),
  `domain` varchar(500),
  `eventId` varchar(500),
  `eventType` varchar(500),
  `nfcNamingCode` varchar(500) DEFAULT NULL,
  `nfNamingCode` varchar(500) DEFAULT NULL,
  `sourceId` varchar(500),
  `sourceName` varchar(500),
  `reportingEntityId` varchar(500),
  `reportingEntityName` varchar(500),
  `priority` varchar(50),
  `startEpochMicrosec` varchar(500),
  `lastEpochMicroSec` varchar(500),
  `sequence` varchar(500),
  `measurementsForVfScalingVersion` varchar(500),
  `measurementInterval` varchar(500),
  `createTime` datetime,
  `updateTime` datetime,
  `id` int(10) NOT NULL auto_increment,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
