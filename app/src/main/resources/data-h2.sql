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
truncate table app_client;
insert into app_client (client_name, client_email, api_key)
values ('Client 1', 'mail1@domain.com', 'b3182e26-517e-408f-8560-99442c83356d'),
       ('Client 2', 'mail2@domain.com', '11d9ff31-453b-4646-9799-d46d595d9ae7'),
       ('Client 3', 'mail3@domain.com', 'e9a7094c-015c-49b2-8f1c-785bb0f7c3d6'),
       ('Client 4', 'mail4@domain.com', '5082d22c-aa7c-4628-bc8c-376c3f52d4ab'),
       ('Client 5', 'mail5@domain.com', '90f4a4fe-c4f7-43cd-92f0-0c55811a64ea');

truncate table job_position;
insert into job_position (pos_name, pos_location)
values ('Name 1', 'London'),
       ('Name 2', 'Portsmouth'),
       ('Name 3', 'Oxford'),
       ('Name 4', 'Leeds');
