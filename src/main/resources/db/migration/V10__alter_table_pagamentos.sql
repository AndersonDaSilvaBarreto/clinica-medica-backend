-- adiciona novas colunas

ALTER TABLE pagamentos
ADD COLUMN forma_pagamento VARCHAR(50);

ALTER TABLE pagamentos
ADD COLUMN payment_id_mp VARCHAR(255);

ALTER TABLE pagamentos
ADD COLUMN status_detail VARCHAR(255);

ALTER TABLE pagamentos
ADD COLUMN qr_code TEXT;

ALTER TABLE pagamentos
ADD COLUMN qr_code_base64 TEXT;

-- remove colunas antigas que não existem mais na entidade

ALTER TABLE pagamentos
DROP COLUMN IF EXISTS gateway;

ALTER TABLE pagamentos
DROP COLUMN IF EXISTS codigo_transacao;

ALTER TABLE pagamentos
DROP COLUMN IF EXISTS data_pagamento;

ALTER TABLE pagamentos
DROP COLUMN IF EXISTS paciente_id;