drop table if exists droit cascade;
drop table if exists utilisateur cascade;
drop table if exists situation cascade;
drop table if exists titre cascade;
drop table if exists achatvente cascade;
drop table if exists transactions cascade;
drop table if exists marche cascade;
drop table if exists pouvoir cascade;

create table utilisateur
(
	iduser serial,
	nom varchar(30),
	prenom varchar(30),
	login varchar(30),
	mdp varchar(30),
	cash integer DEFAULT 10000,
	constraint pk_utilisateur primary key (iduser)
);

create table droit
(
	iddroit serial,
	libelle varchar(30),
	constraint pk_droit primary key (iddroit)
);

create table titre
(
	idtitre serial,
	iduser integer,
	description text,
	constraint pk_titre primary key (idtitre),
	constraint fk_utilisateur foreign key (iduser) references utilisateur(iduser) on update cascade
);

create table achatvente
(
	idachatvente serial,
	prix integer,
	constraint pk_achatvente primary key (idachatvente)
);

create table marche
(
	idmarche serial,
	libelle text,
	datefin date,
	constraint pk_marche primary key (idmarche)
);

create table situation
(
	idsituation serial,
	iduser integer,
	idmarche integer,
	statut varchar(30) DEFAULT 'EN COURS',
	constraint pk_situation primary key (idsituation),
	constraint fk_utilisateur foreign key (iduser) references utilisateur(iduser) on update cascade,
	constraint fk_marche foreign key (idmarche) references marche(idmarche) on update cascade
);

create table pouvoir
(
	idpouvoir serial,
	iduser integer,
	iddroit integer,
	constraint pk_pouvoir primary key (idpouvoir),
	constraint fk_utilisateur foreign key (iduser) references utilisateur(iduser) on update cascade,
	constraint fk_droit foreign key (iddroit) references droit(iddroit) on update cascade
);

create table transactions
(
	idtransaction serial,
	idtitre integer,
	idachatvente integer,
	constraint pk_transactions primary key (idtransaction),
	constraint fk_titre foreign key (idtitre) references titre(idtitre) on update cascade,
	constraint fk_achatvente foreign key (idachatvente) references achatvente(idachatvente) on update cascade
);

