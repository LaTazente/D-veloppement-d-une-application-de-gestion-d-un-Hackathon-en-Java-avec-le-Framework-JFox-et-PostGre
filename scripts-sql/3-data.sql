SET search_path TO hackathon;


-- Supprimer toutes les données
DELETE FROM service;
DELETE FROM abonner;
DELETE FROM memo;
DELETE FROM telephone;
DELETE FROM personne;
DELETE FROM categorie;
DELETE FROM role;
DELETE FROM compte;


-- Compte

INSERT INTO compte (idcompte, pseudo, motdepasse, email ) VALUES 
  (1, 'geek', 'geek', 'geek@jfox.fr' ),
  (2, 'chef', 'chef', 'chef@jfox.fr' ),
  (3, 'job', 'job', 'job@jfox.fr' );

ALTER TABLE compte ALTER COLUMN idcompte RESTART WITH 4;


-- Role

INSERT INTO role (idcompte, role) VALUES 
  ( 1, 'ADMINISTRATEUR' ),
  ( 1, 'UTILISATEUR' ),
  ( 2, 'UTILISATEUR' ),
  ( 3, 'UTILISATEUR' );


-- Categorie
  
INSERT INTO categorie (idcategorie, libelle, debut ) VALUES 
  (1, 'Employés', {d '2021-02-25' } ),
  (2, 'Partenaires', NULL ),
  (3, 'Clients', NULL ),
  (4, 'Fournisseurs', {d '2021-02-25' } ),
  (5, 'Dirigeants', {d '2021-02-25' } );

ALTER TABLE categorie ALTER COLUMN idcategorie RESTART WITH 6;


-- Personne

INSERT INTO personne (idpersonne, idcategorie, nom, prenom) VALUES 
  ( 1, 1, 'GRASSET', 'Jérôme' ),
  ( 2, 1, 'BOUBY', 'Claude' ),
  ( 3, 1, 'AMBLARD', 'Emmanuel' );

ALTER TABLE personne ALTER COLUMN idpersonne RESTART WITH 4;


-- Telephone

INSERT INTO telephone (idtelephone, idpersonne, libelle, numero ) VALUES 
  ( 11, 1, 'Portable', '06 11 11 11 11' ),
  ( 12, 1, 'Fax', '05 55 99 11 11' ),
  ( 13, 1, 'Bureau', '05 55 11 11 11' ),
  ( 21, 2, 'Portable', '06 22 22 22 22' ),
  ( 22, 2, 'Fax', '05 55 99 22 22' ),
  ( 23, 2, 'Bureau', '05 55 22 22 22' ),
  ( 31, 3, 'Portable', '06 33 33 33 33' ),
  ( 32, 3, 'Fax', '05 55 99 33 33' ),
  ( 33, 3, 'Bureau', '05 55 33 33 33' );

ALTER TABLE telephone ALTER COLUMN idtelephone RESTART WITH 100;


-- mémo

INSERT INTO memo (idmemo, titre, description, flagurgent, idcategorie, statut, effectif, budget, echeance, heure ) VALUES 
  (1, 'Mémo n°1', 'Texte du mémo n°1', TRUE, 1 , 'F', 2, 1234.56, {d '2022-02-25' }, {t '18:30' } ),
  (2, 'Mémo n°2', 'Texte du mémo n°2', FALSE, 1, 'E', 4, 5000.00, {d '2022-05-18' }, {t '09:15' } ),
  (3, 'Mémo n°3', NULL, TRUE, NULL, 'A',   NULL,   NULL,   NULL, NULL );

ALTER TABLE memo ALTER COLUMN idmemo RESTART WITH 4;


-- abonner

INSERT INTO abonner (idmemo, idCompte) VALUES 
  ( 1, 1 ),
  ( 1, 2 ),
  ( 1, 3 ),
  ( 2, 1 ),
  ( 2, 2 );


-- abonner

INSERT INTO agir (idmemo, idpersonne, fonction, debut) VALUES 
  ( 1, 1, 'pilote', {d '2022-01-01' } ),
  ( 1, 2, 'secrétaire', {d '2022-01-06' } ),
  ( 1, 3, 'trésorier', {d '2022-01-15' } ),
  ( 2, 1, NULL, NULL ),
  ( 2, 2, 'pilote', NULL );

-- Service

INSERT INTO service ( idservice, nom, anneecreation, flagsiege, idpersonne ) VALUES 
  ( 1, 'Direction', 2007, TRUE, 1 ),
  ( 2, 'Comptabilité', NULL, TRUE, 2 ),
  ( 3, 'Agence Limoges', 2008, FALSE, 3 ),
  ( 4, 'Agence Brive', 2014, FALSE, NULL );

ALTER TABLE service ALTER COLUMN idservice RESTART WITH 5;

