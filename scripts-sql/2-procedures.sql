SET search_path TO hackathon;


-- Supprime tous les triggers du schema

DO
$code$
    DECLARE
        r RECORD;
    BEGIN
        FOR r IN
            SELECT 'DROP TRIGGER ' || trigger_name || ' ON ' || event_object_table AS sql
			FROM information_schema.triggers t
            WHERE trigger_schema = CURRENT_SCHEMA
            GROUP BY event_object_table, trigger_name
            LOOP
                EXECUTE r.sql;
            END LOOP;
    END;
$code$;


-- Supprime toutes les fonctions du schema

DO $code$
DECLARE 
	r RECORD;
BEGIN
	FOR r IN
		SELECT 'DROP FUNCTION ' || ns.nspname || '.' || proname 
	       || '(' || oidvectortypes(proargtypes) || ')' AS sql
		FROM pg_proc INNER JOIN pg_namespace ns ON (pg_proc.pronamespace = ns.oid)
		WHERE ns.nspname = current_schema  
	LOOP
		EXECUTE r.sql;
	END LOOP;
END;
$code$;

-- Mes fonctions Gilles

-- Fonction dureeActivites
CREATE OR REPLACE FUNCTION activite_dureeActivites(p_idevenement VARCHAR)
 RETURNS interval
AS $code$
    SELECT SUM(duree) FROM activite WHERE code=p_idevenement;
$code$
LANGUAGE sql;


-- Fonction taux_participation_presence
CREATE OR REPLACE FUNCTION s_inscrire_taux_participation_presence(p_idevenement VARCHAR)
 RETURNS double precision
AS $code$
    DECLARE taux_total float;
    DECLARE taux_presence float;
    BEGIN
        SELECT count(*) INTO taux_total FROM s_inscrire WHERE code=p_idevenement;
        SELECT count(*) INTO taux_presence FROM s_inscrire where code=p_idevenement AND presence=TRUE;
        IF taux_presence = 0.0::double precision OR taux_total = 0.0::double precision THEN
        	taux_presence = 0.0::double precision;
        ELSE
        	taux_presence = (taux_presence/taux_total) * 100;
        END IF;
        return taux_presence;
    END
$code$
 LANGUAGE plpgsql
;

-- Fonction taux_participation_absence
CREATE OR REPLACE FUNCTION s_inscrire_taux_participation_absence(p_idevenement VARCHAR)
 RETURNS double precision
AS $code$
    DECLARE taux_total float;
    DECLARE taux_absence float;
    BEGIN
        SELECT count(*) INTO taux_total FROM s_inscrire WHERE code=p_idevenement;
        SELECT count(*) INTO taux_absence FROM s_inscrire where code=p_idevenement AND presence=FALSE;
        IF taux_absence = 0.0::double precision OR taux_total = 0.0::double precision THEN
        	taux_absence = 0.0::double precision;
        ELSE
        	taux_absence = (taux_absence/taux_total) * 100;
        END IF;
        return taux_absence;
    END
$code$
 LANGUAGE plpgsql
;

-- Fonction Nbre_groupe
CREATE OR REPLACE FUNCTION participant_nbre_groupe(p_idgroupe VARCHAR)
 RETURNS boolean
 
AS $code$
    DECLARE p_nbmembre integer;
    DECLARE p_nombre integer;
    DECLARE p_verif boolean;
    BEGIN
        SELECT count(*) INTO p_nombre FROM participant WHERE id_groupe = p_idgroupe;
        SELECT nbre_menbres INTO p_nbmembre FROM groupe WHERE id_groupe = p_idgroupe;

        IF p_nombre <= p_nbmembre THEN
            return TRUE;
        ELSE
            return FALSE;
        END IF;
    END
$code$
LANGUAGE plpgsql
;

-- Fonction recherche_participants
CREATE OR REPLACE FUNCTION participant_recherche_participants(p_idevenement VARCHAR)
 RETURNS SETOF participant
AS $code$
BEGIN 
 		RETURN QUERY
        SELECT participant.id, nom, PRENOM, ROLE, sexe, email, TELEPHONE , ID_GROUPE FROM participant INNER JOIN s_inscrire on s_inscrire.id = participant.id WHERE code=p_idevenement GROUP BY id_groupe, participant.id, s_inscrire .code, s_inscrire .ID;
END
$code$
 LANGUAGE plpgsql
;

-- Fonction  recherche_partenaires
CREATE OR REPLACE FUNCTION partenaire_recherche_partenaires(p_idevenement VARCHAR)
RETURNS SETOF partenaire 
AS
$code$
BEGIN 
	RETURN QUERY
    SELECT partenaire.nom_partenaire, description FROM partenaire INNER JOIN assister on partenaire.nom_partenaire = assister.nom_partenaire WHERE code=p_idevenement;
END
$code$
LANGUAGE plpgsql;

-- Fonction  recherche_groupes
CREATE OR REPLACE FUNCTION groupe_recherche_groupes(p_idevenement VARCHAR)
RETURNS SETOF groupe 
AS
$code$
BEGIN 
	RETURN QUERY
    SELECT groupe.ID_GROUPE , nom, NBRE_MENBRES , groupe.code  FROM groupe  WHERE code=p_idevenement;
END
$code$
LANGUAGE plpgsql;

