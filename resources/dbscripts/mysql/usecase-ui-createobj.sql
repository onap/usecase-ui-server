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
  `name` varchar(50) NOT NULL,
  `value` varchar(500) NOT NULL,
  `eventId` varchar(30) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  `id` int(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for alarms_commoneventheader
-- ----------------------------
DROP TABLE IF EXISTS `alarms_commoneventheader`;
CREATE TABLE `alarms_commoneventheader` (
  `version` varchar(10) NOT NULL,
  `eventName` varchar(50) NOT NULL,
  `domain` varchar(30) NOT NULL,
  `eventId` varchar(30) NOT NULL,
  `eventType` varchar(30) NOT NULL,
  `nfcNamingCode` varchar(30) DEFAULT NULL,
  `nfNamingCode` varchar(30) DEFAULT NULL,
  `sourceId` varchar(50) NOT NULL,
  `sourceName` varchar(50) NOT NULL,
  `reportingEntityId` varchar(30) NOT NULL,
  `reportingEntityName` varchar(30) NOT NULL,
  `priority` varchar(20) NOT NULL,
  `startEpochMicrosec` varchar(20) NOT NULL,
  `lastEpochMicroSec` varchar(20) NOT NULL,
  `sequence` varchar(10) NOT NULL,
  `faultFieldsVersion` varchar(10) NOT NULL,
  `eventServrity` varchar(30) NOT NULL,
  `eventSourceType` varchar(30) NOT NULL,
  `eventCategory` varchar(30) NOT NULL,
  `alarmCondition` varchar(400) NOT NULL,
  `specificProblem` varchar(400) NOT NULL,
  `vfStatus` varchar(10) NOT NULL,
  `alarmInterfaceA` varchar(40) NOT NULL,
  `status` varchar(11) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`eventName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for performance_additionalinformation
-- ----------------------------
DROP TABLE IF EXISTS `performance_additionalinformation`;
CREATE TABLE `performance_additionalinformation` (
  `name` varchar(50) NOT NULL,
  `value` varchar(500) NOT NULL,
  `eventId` varchar(30) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  `id` int(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for performance_commoneventheader
-- ----------------------------
DROP TABLE IF EXISTS `performance_commoneventheader`;
CREATE TABLE `performance_commoneventheader` (
  `version` varchar(10) NOT NULL,
  `eventName` varchar(50) NOT NULL,
  `domain` varchar(30) NOT NULL,
  `eventId` varchar(30) NOT NULL,
  `eventType` varchar(30) NOT NULL,
  `nfcNamingCode` varchar(30) DEFAULT NULL,
  `nfNamingCode` varchar(30) DEFAULT NULL,
  `sourceId` varchar(50) NOT NULL,
  `sourceName` varchar(50) NOT NULL,
  `reportingEntityId` varchar(30) NOT NULL,
  `reportingEntityName` varchar(30) NOT NULL,
  `priority` varchar(20) NOT NULL,
  `startEpochMicrosec` varchar(20) NOT NULL,
  `lastEpochMicroSec` varchar(20) NOT NULL,
  `sequence` varchar(10) NOT NULL,
  `measurementsForVfScalingVersion` varchar(10) NOT NULL,
  `measurementInterval` varchar(10) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`eventName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Init Records 
-- ----------------------------