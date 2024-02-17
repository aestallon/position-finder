-- Copyright 2024 Szabolcs Bazil Papp
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
drop all objects;
create table if not exists app_client
(
    id           serial      not null primary key,
    client_name  varchar(50) not null default '',
    client_email varchar(50) not null default '',
    api_key      uuid        not null,
    created_at   timestamp   not null default now()
);

create unique index if not exists client_name_idx on app_client (client_name);
create unique index if not exists api_key_idx on app_client (api_key);

create table if not exists job_position
(
    id           serial    not null primary key,
    pos_name     text      not null default '',
    pos_location text      not null default '',
    created_at   timestamp not null default now()
);
