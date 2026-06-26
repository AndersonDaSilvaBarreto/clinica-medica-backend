ALTER TABLE pacientes ALTER COLUMN cpf TYPE VARCHAR(11);

ALTER TABLE recepcionistas ADD COLUMN cpf VARCHAR(11) NOT NULL UNIQUE;
ALTER TABLE recepcionistas ADD COLUMN data_nascimento DATE;

ALTER TABLE pacientes RENAME COLUMN data_cadastro TO data_criacao;
ALTER TABLE medicos RENAME COLUMN data_cadastro TO data_criacao;
ALTER TABLE recepcionistas RENAME COLUMN data_cadastro TO data_criacao;
ALTER TABLE bloqueios_agenda RENAME COLUMN criado_em TO data_criacao;
ALTER TABLE historico_reagendamentos RENAME COLUMN data_reagendamento TO data_criacao;
ALTER TABLE prontuarios RENAME COLUMN data_registro TO data_criacao;
ALTER TABLE notificacoes RENAME COLUMN data_envio TO data_criacao;

ALTER TABLE pagamentos ADD COLUMN data_criacao TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW();