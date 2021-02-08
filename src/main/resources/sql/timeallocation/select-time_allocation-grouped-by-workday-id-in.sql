SELECT id,
       workday_id,
       description,
       seconds_allocated
FROM time_allocation
where workday_id in (:workdayId);
