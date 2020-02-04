-- Script de création de la base de données ENCHERES
--   type :      SQL Server 2012
--

/*
Auteur : Yann Le Douget
Modifications : - Ajout de la contrainte 'identity' sur les primary key de toutes les tables sauf encheres
				- Ajout des contraintes 'unique' sur la table utilisateurs pour le pseudo, l'email et le telephone
				- Modification de la colonne email dans la table utilisateur passage à un varchar(30).
				- Ajout de la colonne montant dans la table ENCHERES.
*/

CREATE TABLE CATEGORIES (
    no_categorie   INTEGER IDENTITY NOT NULL,
    libelle        NVARCHAR(30) NOT NULL
)

ALTER TABLE CATEGORIES ADD constraint categorie_pk PRIMARY KEY (no_categorie)

CREATE TABLE ENCHERES (
    date_enchere      DATE NOT NULL,
    montant             INTEGER NOT NULL CONSTRAINT chk_montant_encheres CHECK (montant>0),
	no_utilisateur   INTEGER NOT NULL,
    no_vente             INTEGER NOT NULL,
	archive			 bit NOT NULL DEFAULT 0
)

ALTER TABLE ENCHERES ADD constraint enchere_pk PRIMARY KEY (no_utilisateur, no_vente)

CREATE TABLE UTILISATEURS (
    no_utilisateur   INTEGER IDENTITY NOT NULL,
    pseudo           NVARCHAR(30) NOT NULL UNIQUE,
    nom              NVARCHAR(30) NOT NULL,
    prenom           NVARCHAR(30) NOT NULL,
    email            NVARCHAR(30) NOT NULL UNIQUE,
    telephone        NVARCHAR(15) UNIQUE,
    rue              NVARCHAR(30) NOT NULL,
    code_postal      NVARCHAR(10) NOT NULL,
    ville            NVARCHAR(30) NOT NULL,
    mot_de_passe     NVARCHAR(30) NOT NULL,
    credit           INTEGER NOT NULL CONSTRAINT chk_credit_utilisateur CHECK (credit>0),
    administrateur   bit NOT NULL,
	ban				 bit NOT NULL DEFAULT 0,
	archive			 bit NOT NULL DEFAULT 0
)

ALTER TABLE UTILISATEURS ADD constraint utilisateur_pk PRIMARY KEY (no_utilisateur)


CREATE TABLE VENTES (
    no_vente                      INTEGER IDENTITY NOT NULL,
    nomarticle                    NVARCHAR(30) NOT NULL,
    description                   NVARCHAR(300) NOT NULL,
    date_fin_encheres             DATE NOT NULL,
    prix_initial                  INTEGER NOT NULL CONSTRAINT chk_prix_init CHECK (prix_initial>0),
    prix_vente                    INTEGER,
    no_utilisateur                INTEGER NOT NULL,
    no_categorie                  INTEGER NOT NULL,
	rue              			  NVARCHAR(30) NOT NULL,
    code_postal      			  NVARCHAR(15) NOT NULL,
    ville            			  NVARCHAR(30) NOT NULL,
	photo						  VARBINARY(MAX),
	archive			 			  bit NOT NULL DEFAULT 0
)

ALTER TABLE VENTES ADD constraint vente_pk PRIMARY KEY (no_vente)

ALTER TABLE ENCHERES
    ADD CONSTRAINT encheres_utilisateur_fk FOREIGN KEY ( no_utilisateur ) REFERENCES UTILISATEURS ( no_utilisateur )
ON DELETE NO ACTION 
    ON UPDATE no action 

ALTER TABLE ENCHERES
    ADD CONSTRAINT encheres_ventes_fk FOREIGN KEY ( no_vente )
        REFERENCES ventes ( no_vente )
ON DELETE NO ACTION 
    ON UPDATE no action 

ALTER TABLE VENTES
    ADD CONSTRAINT ventes_categories_fk FOREIGN KEY ( no_categorie )
        REFERENCES categories ( no_categorie )
ON DELETE NO ACTION 
    ON UPDATE no action 

ALTER TABLE VENTES
    ADD CONSTRAINT ventes_utilisateur_fk FOREIGN KEY ( no_utilisateur )
        REFERENCES utilisateurs ( no_utilisateur )
ON DELETE NO ACTION 
    ON UPDATE no action 

/* table Categories */
INSERT INTO categories (libelle) VALUES ('Vêtements')
INSERT INTO categories (libelle) VALUES ('Meubles')
INSERT INTO categories (libelle) VALUES ('Jouets')
INSERT INTO categories (libelle) VALUES ('Informatiques')
INSERT INTO categories (libelle) VALUES ('Décorations')
INSERT INTO categories (libelle) VALUES ('Autre')