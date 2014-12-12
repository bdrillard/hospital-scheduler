UPDATE IGNORE events
    SET start = COALESCE(:start, start), 
        end = COALESCE(:end, end),
        patient = COALESCE(:patient, patient), 
        doctor_id = COALESCE(:doctor_id, doctor_id),
        procedure_id = COALESCE(:procedure_id, procedure_id), 
        descr = COALESCE(:descr, descr)
    WHERE id = :id;
