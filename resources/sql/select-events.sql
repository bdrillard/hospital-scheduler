SELECT events.id, events.start, events.end, events.patient, events.descr, doctors.doctor, events.doctor_id, procedures.procedure, events.procedure_id
    FROM events 
    INNER JOIN doctors 
        ON events.doctor_id=doctors.id 
    INNER JOIN procedures 
        ON events.procedure_id=procedures.id;
