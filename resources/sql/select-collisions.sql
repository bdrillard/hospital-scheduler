SELECT events.id, events.start, events.patient, events.doctor_id
    FROM events
    WHERE events.id != :id AND events.start = :start AND (events.patient = :patient OR events.doctor_id = :doctor_id); 
