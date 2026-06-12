-- genero
CREATE TYPE public.genero AS ENUM ('MASCULINO', 'FEMININO', 'OUTRO');
ALTER TABLE public.usuarios ADD COLUMN genero public.genero;

ALTER TABLE public.usuarios ADD COLUMN cpf varchar(11);

-- backfill a partir de pacientes
UPDATE public.usuarios u
SET cpf = p.cpf
FROM public.pacientes p
WHERE p.usuario_id = u.id;

-- backfill a partir de recepcionistas
UPDATE public.usuarios u
SET cpf = r.cpf
FROM public.recepcionistas r
WHERE r.usuario_id = u.id;

-- constraints
ALTER TABLE public.usuarios ALTER COLUMN cpf SET NOT NULL;
ALTER TABLE public.usuarios ADD CONSTRAINT usuarios_cpf_key UNIQUE (cpf);
CREATE INDEX idx_usuarios_cpf ON public.usuarios USING btree (cpf);

-- remove colunas duplicadas
ALTER TABLE public.pacientes DROP CONSTRAINT pacientes_cpf_key;
DROP INDEX IF EXISTS idx_pacientes_cpf;
ALTER TABLE public.pacientes DROP COLUMN cpf;

ALTER TABLE public.recepcionistas DROP CONSTRAINT recepcionistas_cpf_key;
ALTER TABLE public.recepcionistas DROP COLUMN cpf;