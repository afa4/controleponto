SELECT id,
       started_at,
       paused_at,
       returned_at,
       ended_at,
       seconds_worked
from workday w where cast(w.started_at as date) = :referenceDate;