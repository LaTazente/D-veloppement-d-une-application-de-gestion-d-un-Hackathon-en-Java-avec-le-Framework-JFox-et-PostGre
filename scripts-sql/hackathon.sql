------------------------------------------------------------
--        Script Postgre 
------------------------------------------------------------
DROP SCHEMA IF EXISTS hackathon CASCADE;
CREATE SCHEMA hackathon;


------------------------------------------------------------
-- Table: Evenement
------------------------------------------------------------
CREATE TABLE public.Evenement(
	code         VARCHAR (30) NOT NULL ,
	nom          VARCHAR (30) NOT NULL ,
	theme        VARCHAR (30) NOT NULL ,
	lieu         VARCHAR (30) NOT NULL ,
	date_debut   DATE  NOT NULL ,
	date_fin     DATE  NOT NULL  ,
	CONSTRAINT Evenement_PK PRIMARY KEY (code)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Utilisateur
------------------------------------------------------------
CREATE TABLE public.Utilisateur(
	id_user      VARCHAR (30) NOT NULL ,
	nom          VARCHAR (30) NOT NULL ,
	prenom       VARCHAR (30) NOT NULL ,
	profession   VARCHAR (30) NOT NULL ,
	telephone    VARCHAR (15) NOT NULL  ,
	CONSTRAINT Utilisateur_PK PRIMARY KEY (id_user)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Activite
------------------------------------------------------------
CREATE TABLE public.Activite(
	id_activite   SERIAL NOT NULL ,
	nom           VARCHAR (25) NOT NULL ,
	type          CHAR (5)  NOT NULL ,
	duree         TIMETZ  NOT NULL ,
	code          VARCHAR (30) NOT NULL  ,
	CONSTRAINT Activite_PK PRIMARY KEY (id_activite)

	,CONSTRAINT Activite_Evenement_FK FOREIGN KEY (code) REFERENCES public.Evenement(code)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Jury
------------------------------------------------------------
CREATE TABLE public.Jury(
	id_jury          VARCHAR (10) NOT NULL ,
	nbre_memenbres   INT  NOT NULL ,
	code             VARCHAR (30) NOT NULL  ,
	CONSTRAINT Jury_PK PRIMARY KEY (id_jury)

	,CONSTRAINT Jury_Evenement_FK FOREIGN KEY (code) REFERENCES public.Evenement(code)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Groupe
------------------------------------------------------------
CREATE TABLE public.Groupe(
	id_groupe      VARCHAR (25) NOT NULL ,
	nom            VARCHAR (25) NOT NULL ,
	nbre_menbres   INT  NOT NULL ,
	id_jury        VARCHAR (10) NOT NULL  ,
	CONSTRAINT Groupe_PK PRIMARY KEY (id_groupe)

	,CONSTRAINT Groupe_Jury_FK FOREIGN KEY (id_jury) REFERENCES public.Jury(id_jury)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Participant
------------------------------------------------------------
CREATE TABLE public.Participant(
	id          SERIAL NOT NULL ,
	nom         VARCHAR (30) NOT NULL ,
	prenom      VARCHAR (30) NOT NULL ,
	role        VARCHAR (15) NOT NULL ,
	sexe        CHAR (1)  NOT NULL ,
	email       VARCHAR (50) NOT NULL ,
	telephone   VARCHAR (10) NOT NULL ,
	presence    BOOL  NOT NULL ,
	id_groupe   VARCHAR (25) NOT NULL  ,
	CONSTRAINT Participant_PK PRIMARY KEY (id)

	,CONSTRAINT Participant_Groupe_FK FOREIGN KEY (id_groupe) REFERENCES public.Groupe(id_groupe)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Membre_jury
------------------------------------------------------------
CREATE TABLE public.Membre_jury(
	code_membre   VARCHAR (10) NOT NULL ,
	nom           VARCHAR (25) NOT NULL ,
	prenom        VARCHAR (25) NOT NULL ,
	email         VARCHAR (30) NOT NULL ,
	telephone     VARCHAR (15) NOT NULL ,
	profession    VARCHAR (25) NOT NULL ,
	id_jury       VARCHAR (10) NOT NULL  ,
	CONSTRAINT Membre_jury_PK PRIMARY KEY (code_membre)

	,CONSTRAINT Membre_jury_Jury_FK FOREIGN KEY (id_jury) REFERENCES public.Jury(id_jury)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: partenaire
------------------------------------------------------------
CREATE TABLE public.partenaire(
	nom_partenaire   VARCHAR (25) NOT NULL ,
	description      VARCHAR (2000)  NOT NULL  ,
	CONSTRAINT partenaire_PK PRIMARY KEY (nom_partenaire)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: s'inscrire
------------------------------------------------------------
CREATE TABLE public.s_inscrire(
	code   VARCHAR (30) NOT NULL ,
	id     INT  NOT NULL ,
	date   DATE  NOT NULL  ,
	CONSTRAINT s_inscrire_PK PRIMARY KEY (code,id)

	,CONSTRAINT s_inscrire_Evenement_FK FOREIGN KEY (code) REFERENCES public.Evenement(code)
	,CONSTRAINT s_inscrire_Participant0_FK FOREIGN KEY (id) REFERENCES public.Participant(id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: intervenir
------------------------------------------------------------
CREATE TABLE public.intervenir(
	id_user   VARCHAR (30) NOT NULL ,
	code      VARCHAR (30) NOT NULL ,
	role      VARCHAR (50) NOT NULL  ,
	CONSTRAINT intervenir_PK PRIMARY KEY (id_user,code)

	,CONSTRAINT intervenir_Utilisateur_FK FOREIGN KEY (id_user) REFERENCES public.Utilisateur(id_user)
	,CONSTRAINT intervenir_Evenement0_FK FOREIGN KEY (code) REFERENCES public.Evenement(code)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: evaluer
------------------------------------------------------------
CREATE TABLE public.evaluer(
	id_groupe     VARCHAR (25) NOT NULL ,
	code_membre   VARCHAR (10) NOT NULL ,
	id_activite   INT  NOT NULL ,
	note          FLOAT  NOT NULL  ,
	CONSTRAINT evaluer_PK PRIMARY KEY (id_groupe,code_membre,id_activite)

	,CONSTRAINT evaluer_Groupe_FK FOREIGN KEY (id_groupe) REFERENCES public.Groupe(id_groupe)
	,CONSTRAINT evaluer_Membre_jury0_FK FOREIGN KEY (code_membre) REFERENCES public.Membre_jury(code_membre)
	,CONSTRAINT evaluer_Activite1_FK FOREIGN KEY (id_activite) REFERENCES public.Activite(id_activite)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: effectuer
------------------------------------------------------------
CREATE TABLE public.effectuer(
	id_groupe     VARCHAR (25) NOT NULL ,
	id_activite   INT  NOT NULL  ,
	CONSTRAINT effectuer_PK PRIMARY KEY (id_groupe,id_activite)

	,CONSTRAINT effectuer_Groupe_FK FOREIGN KEY (id_groupe) REFERENCES public.Groupe(id_groupe)
	,CONSTRAINT effectuer_Activite0_FK FOREIGN KEY (id_activite) REFERENCES public.Activite(id_activite)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: proposer
------------------------------------------------------------
CREATE TABLE public.proposer(
	id_activite      INT  NOT NULL ,
	nom_partenaire   VARCHAR (25) NOT NULL  ,
	CONSTRAINT proposer_PK PRIMARY KEY (id_activite,nom_partenaire)

	,CONSTRAINT proposer_Activite_FK FOREIGN KEY (id_activite) REFERENCES public.Activite(id_activite)
	,CONSTRAINT proposer_partenaire0_FK FOREIGN KEY (nom_partenaire) REFERENCES public.partenaire(nom_partenaire)
)WITHOUT OIDS;

--------------------------------------------------------------
-- Table : participer
--------------------------------------------------------------
CREATE TABLE public.participer(
	id_groupe	VARCHAR(50) NOT NULL,
	code		VARCHAR(30) NOT NULL,
	CONSTRAINT participer_PK PRIMARY KEY(id_groupe,code)

	,CONSTRAINT participer_Groupe_FK FOREIGN KEY (id_groupe) REFERENCES public.Groupe(id_groupe)
	,CONSTRAINT participer_Evenement_FK FOREIGN KEY (code) REFERENCES public.Evenement(code)
)WITHOUT OIDS;

--------------------------------------------------------------
-- Table : assister
--------------------------------------------------------------
CREATE TABLE public.assister(
	nom_partenaire	VARCHAR(25) NOT NULL,
	code		VARCHAR(30) NOT NULL,
	CONSTRAINT assister_PK PRIMARY KEY(nom_partenaire,code)

	,CONSTRAINT assister_Partenaire_FK FOREIGN KEY (nom_partenaire) REFERENCES public.Partenaire(nom_partenaire)
	,CONSTRAINT assister_Evenement_FK FOREIGN KEY (code) REFERENCES public.Evenement(code)
)WITHOUT OIDS;