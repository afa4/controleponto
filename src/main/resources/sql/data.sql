INSERT INTO workday(started_at, paused_at, returned_at, ended_at, seconds_worked)
VALUES ('2018-08-22T08:00:00', '2018-08-22T012:00:00', '2018-08-22T13:00:00', '2018-08-22T17:00:00', 28800),
       ('2018-08-23T08:00:00', '2018-08-23T012:00:00', '2018-08-23T13:00:00', '2018-08-23T17:00:00', 28800),
       ('2018-08-24T08:00:00', '2018-08-24T012:00:00', '2018-08-24T13:00:00', '2018-08-24T17:00:00', 28800),
       ('2018-08-25T08:00:00', '2018-08-25T012:00:00', '2018-08-25T13:00:00', '2018-08-25T17:00:00', 28800);

INSERT INTO time_allocation(workday_id, description, seconds_allocated)
VALUES (1, 'ACME Corporation', 5000),
       (1, 'Ilia Project', 5000),
       (2, 'Death Star', 10000),
       (3, 'WAYNE Industries', 10000),
       (4, 'STARK Industries', 10000),
       (4, 'Stock S.A.', 10000);
