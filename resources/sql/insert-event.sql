INSERT INTO events
    (`id`, `start`, `end`, `patient`, `doctor_id`, `procedure_id`, `descr`)
    VALUES (NULL, :start, :end, :patient, :doctor_id, :procedure_id, :descr);
