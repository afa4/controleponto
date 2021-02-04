SELECT (id,
        started_at as startedAt,
        paused_at as pauseAt,
        returned_at as returnedAt,
        ended_at as endedAt,
        seconds_worked as secondsWorked)
from workday
where cast(started_at as date) = :reference_date