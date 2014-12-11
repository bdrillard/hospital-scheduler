UPDATE IGNORE events
    SET time            = IFNULL(:time, time),
        patient         = IFNULL(:patient, patient), 
        doctor_id       = IFNULL(:doctor_id, doctor_id),
        procedure_id    = IFNULL(:procedure_id, procedure_id), 
        descr           = IFNULL(:descr, descr)
    WHERE id = :id;
