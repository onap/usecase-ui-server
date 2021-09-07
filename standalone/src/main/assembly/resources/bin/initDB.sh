#!/bin/sh
#
# Copyright (C) 2019 CMCC, Inc. and others. All rights reserved.
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
user_pg=$3
user_uui=$4

echo "setting postgres database password"
#su - $user_pg <<EOF
psql --command "alter user $user_pg with password '$user_pg';"
#EOF

echo "start create usecase-ui database..."
dbscripts_path="$main_path/resources/dbscripts/postgres"
psql "host=$host port=$port user=$user_pg password=$user_pg dbname=$user_pg" -f $dbscripts_path/uui_create_db.sql
sql_result=$?
if [ $sql_result -ne 0 ]; then
    echo "failed to create usecase-ui database!"
    exit 1
else
    echo "usecase-ui database created successfully!"
fi

echo "start create usecase-ui tables..."
psql "host=$host port=$port user=$user_uui password=$user_uui dbname=$user_uui" -f $dbscripts_path/uui_create_table.sql
sql_result=$?
if [ $sql_result -ne 0 ]; then
    echo "failed to create usecase-ui table!"
    exit 1
else
    echo "usecase-ui tables created successfully!"
fi

echo "start insert initial data into uui-server database..."
psql "host=$host port=$port user=$user_uui password=$user_uui dbname=$user_uui" -f $dbscripts_path/uui_init_data.sql
sql_result=$?
if [ $sql_result -ne 0 ]; then
    echo "failed to insert initial data!"
    exit 1
else
    echo "usecase-ui database initial data import succeed!"
fi
