ALTER TABLE public.usuarios ADD COLUMN data_nascimento date;

UPDATE public.usuarios u
SET data_nascimento = p.data_nascimento
    FROM public.pacientes p
WHERE p.usuario_id = u.id;
UPDATE public.usuarios u
SET data_nascimento = r.data_nascimento
    FROM public.recepcionistas r
WHERE r.usuario_id = u.id;

-- remove colunas duplicadas
ALTER TABLE public.pacientes DROP COLUMN data_nascimento;
ALTER TABLE public.recepcionistas DROP COLUMN data_nascimento;