SELECT events.id, events.start, events.end, events.patient, events.descr, doctors.doctor, procedures.procedure 
    FROM events 
    INNER JOIN doctors 
        ON events.doctor_id=doctors.id 
    INNER JOIN procedures 
        ON events.procedure_id=procedures.id;
