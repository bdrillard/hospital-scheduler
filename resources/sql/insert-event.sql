INSERT INTO events
    (`id`, `time`, `patient`, `doctor_id`, `procedure_id`, `descr`)
    VALUES (NULL, :time, :patient, :doctor_id, :procedure_id, :descr);
