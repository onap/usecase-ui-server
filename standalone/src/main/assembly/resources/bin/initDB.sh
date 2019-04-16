#!/bin/bash
#
# Copyright (C) 2017 CMCC, Inc. and others. All rights reserved.
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

echo "setting database init parameters"
main_path="/home/uui"
host=$1
port=$2
dbname=$3
user=$4
password=$5

echo "setting postgres database password"
su - $user <<EOF
psql --command "alter user $user with password '$password';"
EOF

echo "start create usecase-ui database..."
dbscripts_path="$main_path/resources/dbscripts/postgres"
psql "host=$host port=$port user=$user password=$password dbname=$dbname" -f $dbscripts_path/uui_create_db.sql
sql_result=$?
if [ $sql_result!=0 ]; then
    echo "failed to create usecase-ui database!"
    exit 1
else
    echo "usecase-ui database created successfully!"
fi

echo "start create usecase-ui tables..."
psql "host=$host port=$port user=$user password=$password dbname=$dbname" -f $dbscripts_path/uui_create_table.sql
sql_result=$?
if [ $sql_result!=0 ]; then
    echo "failed to create usecase-ui table!"
    exit 1
else
    echo "usecase-ui tables created successfully!"
fi
exit 0
