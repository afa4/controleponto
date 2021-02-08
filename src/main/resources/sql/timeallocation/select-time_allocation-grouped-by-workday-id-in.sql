SELECT workday_id,
       JSON_ARRAYAGG(
               JSON_OBJECT('description': description,
                           'secondsAllocated': seconds_allocated)
           ) as timeAllocationSummary
FROM TIME_ALLOCATION
WHERE workday_id in (:workdaysIds)
GROUP BY workday_id;
