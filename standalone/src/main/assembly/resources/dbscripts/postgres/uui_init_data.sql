--
-- Copyright (C) 2019 CMCC, Inc. and others. All rights reserved.
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

-- ----------------------------
-- import initial data for sort_master
-- ----------------------------
INSERT INTO sort_master (sort_type, sort_code, sort_value, language) VALUES ('operationType', '1001', 'Creating', 'en');
INSERT INTO sort_master (sort_type, sort_code, sort_value, language) VALUES ('operationType', '1002', 'Deleting', 'en');
INSERT INTO sort_master (sort_type, sort_code, sort_value, language) VALUES ('operationType', '1003', 'Scaling', 'en');
INSERT INTO sort_master (sort_type, sort_code, sort_value, language) VALUES ('operationType', '1004', 'Healing', 'en');
INSERT INTO sort_master (sort_type, sort_code, sort_value, language) VALUES ('operationType', '1005', 'Updating', 'en');
INSERT INTO sort_master (sort_type, sort_code, sort_value, language) VALUES ('operationType', '1001', '创建', 'cn');
INSERT INTO sort_master (sort_type, sort_code, sort_value, language) VALUES ('operationType', '1002', '删除', 'cn');
INSERT INTO sort_master (sort_type, sort_code, sort_value, language) VALUES ('operationType', '1003', '缩扩容', 'cn');
INSERT INTO sort_master (sort_type, sort_code, sort_value, language) VALUES ('operationType', '1004', '自愈', 'cn');
INSERT INTO sort_master (sort_type, sort_code, sort_value, language) VALUES ('operationType', '1005', '更新', 'cn');
INSERT INTO sort_master (sort_type, sort_code, sort_value, language) VALUES ('operationResult', '2001', 'Successful', 'en');
INSERT INTO sort_master (sort_type, sort_code, sort_value, language) VALUES ('operationResult', '2002', 'Failed', 'en');
INSERT INTO sort_master (sort_type, sort_code, sort_value, language) VALUES ('operationResult', '2003', 'In Progress', 'en');
INSERT INTO sort_master (sort_type, sort_code, sort_value, language) VALUES ('operationResult', '2001', '成功', 'cn');
INSERT INTO sort_master (sort_type, sort_code, sort_value, language) VALUES ('operationResult', '2002', '失败', 'cn');
INSERT INTO sort_master (sort_type, sort_code, sort_value, language) VALUES ('operationResult', '2003', '执行中', 'cn');
