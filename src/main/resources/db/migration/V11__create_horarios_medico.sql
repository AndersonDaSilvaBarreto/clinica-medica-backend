

CREATE TABLE horarios_medico (
    id          UUID                     PRIMARY KEY DEFAULT gen_random_uuid(),
    medico_id   UUID                     NOT NULL REFERENCES medicos(id) ON DELETE CASCADE,
    data_hora   TIMESTAMP WITH TIME ZONE NOT NULL,
    status      VARCHAR(20)              NOT NULL DEFAULT 'DISPONIVEL'
                    CHECK (status IN ('DISPONIVEL', 'OCUPADO')),
    data_criacao TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),

    CONSTRAINT uq_horario_medico UNIQUE (medico_id, data_hora)
);

CREATE INDEX idx_horarios_medico_medico_id  ON horarios_medico (medico_id);
CREATE INDEX idx_horarios_medico_data_hora  ON horarios_medico (data_hora);
CREATE INDEX idx_horarios_medico_status     ON horarios_medico (status);
