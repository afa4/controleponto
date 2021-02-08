SELECT id,
       workday_id        as workdayId,
       description,
       seconds_allocated as secondsAllocated
FROM time_allocation
where workday_id = :workdayId;