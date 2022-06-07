SET search_path TO hackathon;


-- Schéma

DROP SCHEMA IF EXISTS hackathon CASCADE;
CREATE SCHEMA hackathon AUTHORIZATION hackathon;
GRANT ALL PRIVILEGES ON SCHEMA hackathon TO hackathon;


-- Tables
--CREATE SCHEMA hackathon;

CREATE TABLE hackathon.evenement (
	code varchar(30) NOT NULL,
	nom varchar(30) NOT NULL,
	theme varchar(30) NOT NULL,
	lieu varchar(30) NOT NULL,
	date_debut date NOT NULL,
	date_fin date NOT NULL,
	CONSTRAINT evenement_pk PRIMARY KEY (code)
);


-- hackathon.partenaire definition

-- Drop table

-- DROP TABLE hackathon.partenaire;

CREATE TABLE hackathon.partenaire (
	nom_partenaire varchar(25) NOT NULL,
	description varchar(2000) NOT NULL,
	CONSTRAINT partenaire_pk PRIMARY KEY (nom_partenaire)
);


-- hackathon.utilisateur definition

-- Drop table

-- DROP TABLE hackathon.utilisateur;

CREATE TABLE hackathon.utilisateur (
	id_user varchar(30) NOT NULL,
	nom varchar(30) NOT NULL,
	prenom varchar(30) NOT NULL,
	profession varchar(30) NOT NULL,
	telephone varchar(15) NOT NULL,
    mdp varchar(15) NOT NULL,
	CONSTRAINT utilisateur_pk PRIMARY KEY (id_user)
);


-- hackathon.activite definition

-- Drop table

-- DROP TABLE hackathon.activite;

CREATE TABLE hackathon.activite (
	id_activite serial4 NOT NULL,
	nom varchar(25) NOT NULL,
	"type" bpchar(5) NOT NULL,
	duree time NOT NULL,
	code varchar(30) NOT NULL,
	CONSTRAINT activite_pk PRIMARY KEY (id_activite),
	CONSTRAINT activite_evenement_fk FOREIGN KEY (code) REFERENCES hackathon.evenement(code)
);


-- hackathon.assister definition

-- Drop table

-- DROP TABLE hackathon.assister;

CREATE TABLE hackathon.assister (
	nom_partenaire varchar(25) NOT NULL,
	code varchar(30) NOT NULL,
	CONSTRAINT assister_pk PRIMARY KEY (nom_partenaire, code),
	CONSTRAINT assister_evenement_fk FOREIGN KEY (code) REFERENCES hackathon.evenement(code),
	CONSTRAINT assister_partenaire_fk FOREIGN KEY (nom_partenaire) REFERENCES hackathon.partenaire(nom_partenaire)
);


-- hackathon.intervenir definition

-- Drop table

-- DROP TABLE hackathon.intervenir;

CREATE TABLE hackathon.intervenir (
	id_user varchar(30) NOT NULL,
	code varchar(30) NOT NULL,
	"role" varchar(50) NOT NULL,
	CONSTRAINT intervenir_pk PRIMARY KEY (id_user, code),
	CONSTRAINT intervenir_evenement0_fk FOREIGN KEY (code) REFERENCES hackathon.evenement(code),
	CONSTRAINT intervenir_utilisateur_fk FOREIGN KEY (id_user) REFERENCES hackathon.utilisateur(id_user)
);


-- hackathon.jury definition

-- Drop table

-- DROP TABLE hackathon.jury;

CREATE TABLE hackathon.jury (
	id_jury varchar(10) NOT NULL,
	nbre_memenbres int4 NOT NULL,
	code varchar(30) NOT NULL,
	CONSTRAINT jury_pk PRIMARY KEY (id_jury),
	CONSTRAINT jury_evenement_fk FOREIGN KEY (code) REFERENCES hackathon.evenement(code)
);


-- hackathon.membre_jury definition

-- Drop table

-- DROP TABLE hackathon.membre_jury;

CREATE TABLE hackathon.membre_jury (
	code_membre varchar(10) NOT NULL,
	nom varchar(25) NOT NULL,
	prenom varchar(25) NOT NULL,
	email varchar(30) NOT NULL,
	telephone varchar(15) NOT NULL,
	profession varchar(25) NOT NULL,
	id_jury varchar(10) NOT NULL,
	CONSTRAINT membre_jury_pk PRIMARY KEY (code_membre),
	CONSTRAINT membre_jury_jury_fk FOREIGN KEY (id_jury) REFERENCES hackathon.jury(id_jury)
);


-- hackathon.proposer definition

-- Drop table

-- DROP TABLE hackathon.proposer;

CREATE TABLE hackathon.proposer (
	id_activite int4 NOT NULL,
	nom_partenaire varchar(25) NOT NULL,
	CONSTRAINT proposer_pk PRIMARY KEY (id_activite, nom_partenaire),
	CONSTRAINT proposer_activite_fk FOREIGN KEY (id_activite) REFERENCES hackathon.activite(id_activite),
	CONSTRAINT proposer_partenaire0_fk FOREIGN KEY (nom_partenaire) REFERENCES hackathon.partenaire(nom_partenaire)
);


-- hackathon.groupe definition

-- Drop table

-- DROP TABLE hackathon.groupe;

CREATE TABLE hackathon.groupe (
	id_groupe varchar(25) NOT NULL,
	nom varchar(25) NOT NULL,
	nbre_menbres int4 NOT NULL,
	code varchar(25) NOT NULL,
	CONSTRAINT groupe_pk PRIMARY KEY (id_groupe),
	CONSTRAINT groupe_evenement_fk FOREIGN KEY (code) REFERENCES hackathon.evenement(code)
);


-- hackathon.participant definition

-- Drop table

-- DROP TABLE hackathon.participant;

CREATE TABLE hackathon.participant (
	id serial4 NOT NULL,
	nom varchar(30) NOT NULL,
	prenom varchar(30) NOT NULL,
	"role" varchar(15) NOT NULL,
	sexe bpchar(1) NOT NULL,
	email varchar(50) NOT NULL,
	telephone varchar(10) NOT NULL,
	id_groupe varchar(25) NULL,
	CONSTRAINT participant_pk PRIMARY KEY (id),
	CONSTRAINT participant_groupe_fk FOREIGN KEY (id_groupe) REFERENCES hackathon.groupe(id_groupe)
);


-- hackathon.participer definition

-- Drop table

-- DROP TABLE hackathon.participer;

CREATE TABLE hackathon.participer (
	id_groupe varchar(50) NOT NULL,
	code varchar(30) NOT NULL,
	CONSTRAINT participer_pk PRIMARY KEY (id_groupe, code),
	CONSTRAINT participer_evenement_fk FOREIGN KEY (code) REFERENCES hackathon.evenement(code),
	CONSTRAINT participer_groupe_fk FOREIGN KEY (id_groupe) REFERENCES hackathon.groupe(id_groupe)
);


-- hackathon.s_inscrire definition

-- Drop table

-- DROP TABLE hackathon.s_inscrire;

CREATE TABLE hackathon.s_inscrire (
	code varchar(30) NOT NULL,
	id int4 NOT NULL,
	"date" date NOT NULL,
	presence bool NOT NULL DEFAULT false,
	CONSTRAINT s_inscrire_pk PRIMARY KEY (code, id),
	CONSTRAINT s_inscrire_evenement_fk FOREIGN KEY (code) REFERENCES hackathon.evenement(code),
	CONSTRAINT s_inscrire_participant0_fk FOREIGN KEY (id) REFERENCES hackathon.participant(id)
);


-- hackathon.effectuer definition

-- Drop table

-- DROP TABLE hackathon.effectuer;

CREATE TABLE hackathon.effectuer (
	id_groupe varchar(25) NOT NULL,
	id_activite int4 NOT NULL,
	CONSTRAINT effectuer_pk PRIMARY KEY (id_groupe, id_activite),
	CONSTRAINT effectuer_activite0_fk FOREIGN KEY (id_activite) REFERENCES hackathon.activite(id_activite),
	CONSTRAINT effectuer_groupe_fk FOREIGN KEY (id_groupe) REFERENCES hackathon.groupe(id_groupe)
);


-- hackathon.evaluer definition

-- Drop table

-- DROP TABLE hackathon.evaluer;

CREATE TABLE hackathon.evaluer (
	id_groupe varchar(25) NOT NULL,
	code_membre varchar(10) NOT NULL,
	id_activite int4 NOT NULL,
	note float8 NOT NULL,
	CONSTRAINT evaluer_pk PRIMARY KEY (id_groupe, code_membre, id_activite),
	CONSTRAINT evaluer_activite1_fk FOREIGN KEY (id_activite) REFERENCES hackathon.activite(id_activite),
	CONSTRAINT evaluer_groupe_fk FOREIGN KEY (id_groupe) REFERENCES hackathon.groupe(id_groupe),
	CONSTRAINT evaluer_membre_jury0_fk FOREIGN KEY (code_membre) REFERENCES hackathon.membre_jury(code_membre)
);

