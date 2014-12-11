SELECT events.id, events.time, events.doctor_id
    FROM events
    WHERE events.id != :id AND events.time = :time AND events.doctor_id = :doctor_id; 
