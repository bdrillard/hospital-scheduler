SELECT events.id, events.start, events.doctor_id
    FROM events
    WHERE events.id != :id AND events.start = :start AND events.doctor_id = :doctor_id; 
