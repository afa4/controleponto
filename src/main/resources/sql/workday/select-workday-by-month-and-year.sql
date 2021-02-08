SELECT id,
       started_at,
       paused_at,
       returned_at,
       ended_at,
       seconds_worked
from workday w
where
    year (cast (w.started_at as date)) = :y
  and
    month (cast (w.started_at as date)) = :m;