-- =============================================================================
-- V1__create_schema.sql
-- Sistema de Clínica Médica — Estrutura completa
-- =============================================================================

-- ─────────────────────────────────────────────────────────────────────────────
-- ENUMS
-- ─────────────────────────────────────────────────────────────────────────────

CREATE TYPE perfil_usuario AS ENUM (
    'ADMINISTRADOR',
    'RECEPCIONISTA',
    'MEDICO',
    'PACIENTE'
);

CREATE TYPE status_consulta AS ENUM (
    'AGUARDANDO_PAGAMENTO',
    'AGENDADA',
    'PRESENTE',
    'EM_ATENDIMENTO',
    'FINALIZADA',
    'CANCELADA',
    'REAGENDADA',
    'FALTOU'
);

CREATE TYPE status_pagamento AS ENUM (
    'PENDENTE',
    'APROVADO',
    'RECUSADO',
    'ESTORNADO'
);

CREATE TYPE status_fila AS ENUM (
    'AGUARDANDO',
    'CHAMADO',
    'EM_ATENDIMENTO',
    'FINALIZADO',
    'AUSENTE'
);

-- ─────────────────────────────────────────────────────────────────────────────
-- 1. usuarios
-- ─────────────────────────────────────────────────────────────────────────────

CREATE TABLE usuarios (
    id            UUID                     PRIMARY KEY DEFAULT gen_random_uuid(),
    nome          VARCHAR(150)             NOT NULL,
    email         VARCHAR(255)             NOT NULL UNIQUE,
    senha         VARCHAR(255)             NOT NULL,
    perfil        perfil_usuario           NOT NULL,
    telefone      VARCHAR(20),
    ativo         BOOLEAN                  NOT NULL DEFAULT TRUE,
    data_criacao  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- ─────────────────────────────────────────────────────────────────────────────
-- 2. convenios
-- ─────────────────────────────────────────────────────────────────────────────

CREATE TABLE convenios (
    id        UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    nome      VARCHAR(150) NOT NULL,
    telefone  VARCHAR(20),
    descricao VARCHAR(500)
);

-- ─────────────────────────────────────────────────────────────────────────────
-- 3. pacientes
-- ─────────────────────────────────────────────────────────────────────────────

CREATE TABLE pacientes (
    id               UUID                     PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id       UUID                     NOT NULL UNIQUE REFERENCES usuarios(id) ON DELETE CASCADE,
    cpf              VARCHAR(14)              NOT NULL UNIQUE,
    data_nascimento  DATE,
    endereco         VARCHAR(500),
    convenio_id      UUID                     REFERENCES convenios(id) ON DELETE SET NULL,
    data_cadastro    TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- ─────────────────────────────────────────────────────────────────────────────
-- 4. especialidades
-- ─────────────────────────────────────────────────────────────────────────────

CREATE TABLE especialidades (
    id               UUID           PRIMARY KEY DEFAULT gen_random_uuid(),
    nome             VARCHAR(150)   NOT NULL UNIQUE,
    descricao        VARCHAR(500),
    exige_pagamento  BOOLEAN        NOT NULL DEFAULT TRUE,
    valor_consulta   NUMERIC(10, 2)
);

-- ─────────────────────────────────────────────────────────────────────────────
-- 5. medicos
-- ─────────────────────────────────────────────────────────────────────────────

CREATE TABLE medicos (
    id                      UUID                     PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id              UUID                     NOT NULL UNIQUE REFERENCES usuarios(id) ON DELETE CASCADE,
    crm                     VARCHAR(20)              NOT NULL UNIQUE,
    tempo_consulta_minutos  INTEGER                  NOT NULL DEFAULT 30,
    ativo                   BOOLEAN                  NOT NULL DEFAULT TRUE,
    data_cadastro           TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- ─────────────────────────────────────────────────────────────────────────────
-- 6. medico_especialidades
-- ─────────────────────────────────────────────────────────────────────────────

CREATE TABLE medico_especialidades (
    medico_id        UUID NOT NULL REFERENCES medicos(id) ON DELETE CASCADE,
    especialidade_id UUID NOT NULL REFERENCES especialidades(id) ON DELETE RESTRICT,
    PRIMARY KEY (medico_id, especialidade_id)
);

-- ─────────────────────────────────────────────────────────────────────────────
-- 7. recepcionistas
-- ─────────────────────────────────────────────────────────────────────────────

CREATE TABLE recepcionistas (
    id            UUID                     PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id    UUID                     NOT NULL UNIQUE REFERENCES usuarios(id) ON DELETE CASCADE,
    matricula     VARCHAR(50)              NOT NULL UNIQUE,
    data_cadastro TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- ─────────────────────────────────────────────────────────────────────────────
-- 8. agendas_medicos
-- ─────────────────────────────────────────────────────────────────────────────

CREATE TABLE agendas_medicos (
    id          UUID    PRIMARY KEY DEFAULT gen_random_uuid(),
    medico_id   UUID    NOT NULL REFERENCES medicos(id) ON DELETE CASCADE,
    dia_semana  INTEGER NOT NULL CHECK (dia_semana BETWEEN 0 AND 6),
    hora_inicio TIME    NOT NULL,
    hora_fim    TIME    NOT NULL,
    CONSTRAINT chk_horario         CHECK (hora_fim > hora_inicio),
    CONSTRAINT uq_agenda_medico    UNIQUE (medico_id, dia_semana, hora_inicio)
);

-- ─────────────────────────────────────────────────────────────────────────────
-- 9. bloqueios_agenda
-- ─────────────────────────────────────────────────────────────────────────────

CREATE TABLE bloqueios_agenda (
    id          UUID                     PRIMARY KEY DEFAULT gen_random_uuid(),
    medico_id   UUID                     NOT NULL REFERENCES medicos(id) ON DELETE CASCADE,
    data_inicio TIMESTAMP WITH TIME ZONE NOT NULL,
    data_fim    TIMESTAMP WITH TIME ZONE NOT NULL,
    motivo      VARCHAR(500),
    criado_em   TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    CONSTRAINT chk_periodo CHECK (data_fim > data_inicio)
);

-- ─────────────────────────────────────────────────────────────────────────────
-- 10. salas_atendimento
-- ─────────────────────────────────────────────────────────────────────────────

CREATE TABLE salas_atendimento (
    id        UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    nome      VARCHAR(100) NOT NULL,
    descricao VARCHAR(300),
    ativa     BOOLEAN      NOT NULL DEFAULT TRUE
);

-- ─────────────────────────────────────────────────────────────────────────────
-- 11. consultas
-- (pagamento_id adicionado depois via ALTER para evitar referência circular)
-- ─────────────────────────────────────────────────────────────────────────────

CREATE TABLE consultas (
    id                  UUID                     PRIMARY KEY DEFAULT gen_random_uuid(),
    paciente_id         UUID                     NOT NULL REFERENCES pacientes(id) ON DELETE RESTRICT,
    medico_id           UUID                     NOT NULL REFERENCES medicos(id) ON DELETE RESTRICT,
    data_hora_inicio    TIMESTAMP WITH TIME ZONE NOT NULL,
    data_hora_fim       TIMESTAMP WITH TIME ZONE NOT NULL,
    status              status_consulta          NOT NULL DEFAULT 'AGUARDANDO_PAGAMENTO',
    observacao          TEXT,
    criado_por          UUID                     NOT NULL REFERENCES usuarios(id) ON DELETE RESTRICT,
    data_criacao        TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    pagamento_convenio  BOOLEAN                  NOT NULL DEFAULT FALSE,
    pagamento_id        UUID,
    CONSTRAINT chk_horario_consulta CHECK (data_hora_fim > data_hora_inicio)
);

-- ─────────────────────────────────────────────────────────────────────────────
-- 12. pagamentos
-- ─────────────────────────────────────────────────────────────────────────────

CREATE TABLE pagamentos (
    id                UUID                     PRIMARY KEY DEFAULT gen_random_uuid(),
    consulta_id       UUID                     NOT NULL REFERENCES consultas(id) ON DELETE RESTRICT,
    paciente_id       UUID                     NOT NULL REFERENCES pacientes(id) ON DELETE RESTRICT,
    valor             NUMERIC(10, 2)           NOT NULL,
    status            status_pagamento         NOT NULL DEFAULT 'PENDENTE',
    gateway           VARCHAR(50),
    data_pagamento    TIMESTAMP WITH TIME ZONE,
    codigo_transacao  VARCHAR(255)
);

-- Resolve referência circular entre consultas e pagamentos
ALTER TABLE consultas
    ADD CONSTRAINT fk_consultas_pagamento
    FOREIGN KEY (pagamento_id) REFERENCES pagamentos(id) ON DELETE SET NULL;

-- ─────────────────────────────────────────────────────────────────────────────
-- 13. historico_reagendamentos
-- ─────────────────────────────────────────────────────────────────────────────

CREATE TABLE historico_reagendamentos (
    id                  UUID                     PRIMARY KEY DEFAULT gen_random_uuid(),
    consulta_id         UUID                     NOT NULL REFERENCES consultas(id) ON DELETE CASCADE,
    data_antiga         TIMESTAMP WITH TIME ZONE NOT NULL,
    data_nova           TIMESTAMP WITH TIME ZONE NOT NULL,
    motivo              VARCHAR(500),
    realizado_por       UUID                     NOT NULL REFERENCES usuarios(id) ON DELETE RESTRICT,
    data_reagendamento  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- ─────────────────────────────────────────────────────────────────────────────
-- 14. confirmacoes_presenca
-- ─────────────────────────────────────────────────────────────────────────────

CREATE TABLE confirmacoes_presenca (
    id                   UUID                     PRIMARY KEY DEFAULT gen_random_uuid(),
    consulta_id          UUID                     NOT NULL UNIQUE REFERENCES consultas(id) ON DELETE CASCADE,
    recepcionista_id     UUID                     NOT NULL REFERENCES recepcionistas(id) ON DELETE RESTRICT,
    horario_confirmacao  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- ─────────────────────────────────────────────────────────────────────────────
-- 15. fila_atendimento
-- ─────────────────────────────────────────────────────────────────────────────

CREATE TABLE fila_atendimento (
    id               UUID                     PRIMARY KEY DEFAULT gen_random_uuid(),
    consulta_id      UUID                     NOT NULL UNIQUE REFERENCES consultas(id) ON DELETE CASCADE,
    medico_id        UUID                     NOT NULL REFERENCES medicos(id) ON DELETE RESTRICT,
    sala_id          UUID                     REFERENCES salas_atendimento(id) ON DELETE SET NULL,
    ordem_fila       INTEGER                  NOT NULL,
    horario_chamada  TIMESTAMP WITH TIME ZONE,
    status           status_fila              NOT NULL DEFAULT 'AGUARDANDO',
    CONSTRAINT uq_fila_ordem UNIQUE (medico_id, ordem_fila)
);

-- ─────────────────────────────────────────────────────────────────────────────
-- 16. prontuarios
-- ─────────────────────────────────────────────────────────────────────────────

CREATE TABLE prontuarios (
    id             UUID                     PRIMARY KEY DEFAULT gen_random_uuid(),
    consulta_id    UUID                     NOT NULL UNIQUE REFERENCES consultas(id) ON DELETE RESTRICT,
    diagnostico    TEXT                     NOT NULL,
    observacoes    TEXT,
    receita        TEXT,
    data_registro  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- ─────────────────────────────────────────────────────────────────────────────
-- 17. notificacoes
-- ─────────────────────────────────────────────────────────────────────────────

CREATE TABLE notificacoes (
    id          UUID                     PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id  UUID                     NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    tipo        VARCHAR(60)              NOT NULL,
    mensagem    TEXT                     NOT NULL,
    lida        BOOLEAN                  NOT NULL DEFAULT FALSE,
    data_envio  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- =============================================================================
-- ÍNDICES
-- =============================================================================

-- usuarios
CREATE INDEX idx_usuarios_email  ON usuarios(email);
CREATE INDEX idx_usuarios_perfil ON usuarios(perfil);

-- pacientes
CREATE INDEX idx_pacientes_cpf      ON pacientes(cpf);
CREATE INDEX idx_pacientes_convenio ON pacientes(convenio_id);

-- medicos
CREATE INDEX idx_medicos_ativo ON medicos(ativo);

-- medico_especialidades
CREATE INDEX idx_medico_especialidades_medico        ON medico_especialidades(medico_id);
CREATE INDEX idx_medico_especialidades_especialidade ON medico_especialidades(especialidade_id);

-- agendas_medicos
CREATE INDEX idx_agendas_medico_dia ON agendas_medicos(medico_id, dia_semana);

-- bloqueios_agenda
CREATE INDEX idx_bloqueios_medico  ON bloqueios_agenda(medico_id);
CREATE INDEX idx_bloqueios_periodo ON bloqueios_agenda(data_inicio, data_fim);

-- consultas
CREATE INDEX idx_consultas_paciente    ON consultas(paciente_id);
CREATE INDEX idx_consultas_medico      ON consultas(medico_id);
CREATE INDEX idx_consultas_status      ON consultas(status);
CREATE INDEX idx_consultas_data        ON consultas(data_hora_inicio);
CREATE INDEX idx_consultas_medico_data ON consultas(medico_id, data_hora_inicio);

-- Índice parcial: impede agendamento duplo no mesmo horário (proteção contra concorrência)
CREATE UNIQUE INDEX idx_consulta_unica
    ON consultas (medico_id, data_hora_inicio)
    WHERE status NOT IN ('CANCELADA', 'REAGENDADA');

-- pagamentos
CREATE INDEX idx_pagamentos_consulta ON pagamentos(consulta_id);
CREATE INDEX idx_pagamentos_status   ON pagamentos(status);

-- fila_atendimento
CREATE INDEX idx_fila_medico_status ON fila_atendimento(medico_id, status);

-- notificacoes
CREATE INDEX idx_notificacoes_usuario ON notificacoes(usuario_id);
CREATE INDEX idx_notificacoes_lida    ON notificacoes(usuario_id, lida);

-- historico_reagendamentos
CREATE INDEX idx_reagendamentos_consulta ON historico_reagendamentos(consulta_id);
