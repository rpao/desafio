CREATE SEQUENCE usuario_id_seq;

CREATE TABLE usuario (
id integer NOT NULL DEFAULT nextval('usuario_id_seq'),
nome character varying(100) NOT NULL,
email character varying(100) NOT NULL,
senha character varying(12) NOT NULL,
CONSTRAINT usuario_pk PRIMARY KEY (id)
);

ALTER SEQUENCE usuario_id_seq
OWNED BY usuario.id;

CREATE SEQUENCE telefone_id_seq;

CREATE TABLE telefone (
id integer NOT NULL DEFAULT nextval('usuario_id_seq'),
DDD integer NOT NULL,
numero character varying(9) NOT NULL,
tipo character varying(20),
usuario integer NOT NULL,
CONSTRAINT telefone_fk FOREIGN KEY (usuario) REFERENCES usuario(id),
CONSTRAINT telefone_pk PRIMARY KEY (id)
);

ALTER SEQUENCE telefone_id_seq
OWNED BY telefone.id;