#!/bin/sh
#
# Copyright 2020 CMCC Corporation.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

main_path="/home/uui"

add_user() {
  #useradd postgres
  echo "add_user postgres..."
  chown -R postgres:postgres $main_path
}

modify_owner() {
  dbScript="$main_path/resources/bin/initDB.sh"
  chmod 755 $dbScript
  chown -R postgres:postgres /var/run/postgresql
  chown -R postgres:postgres $PG_HOME
  chown -R postgres:postgres $PG_VAR_LIB
  chown -R postgres:postgres $PG_USR_LIB
  chown -R postgres:postgres $PG_LOGDIR
  chmod -R 0700 $PG_HOME
  echo "modify files owner..."
}

add_user
modify_owner
