drop table if exists droit cascade;
drop table if exists utilisateur cascade;
drop table if exists situation cascade;
drop table if exists titre cascade;
drop table if exists achatvente cascade;
drop table if exists transactions cascade;
drop table if exists marche cascade;
drop table if exists pouvoir cascade;

create table droit
(
	iddroit serial,
	libelle varchar(30),
	constraint pk_droit primary key (iddroit)
);

create table utilisateur
(
	iduser serial,
	nom varchar(30),
	prenom varchar(30),
	login varchar(30),
	mdp varchar(30),
	cash integer DEFAULT 1000000,
	droit varchar(50) DEFAULT 'utilisateur',
	constraint pk_utilisateur primary key (iduser)
);

create table droit
(
	iddroit serial,
	login text,
	libelle varchar(30),
	constraint pk_droit primary key (iddroit)
);

create table titre
(
	idtitre serial,
	iduser integer,
	description text default 'achat',
	jourachat date default current_date,
	constraint pk_titre primary key (idtitre),
	constraint fk_utilisateur foreign key (iduser) references utilisateur(iduser) on update cascade
);

create table marche
(
	idmarche serial,
	libelle text,
	inverse text,
	datefin date,
	statut text default 'EN COURS',
	constraint pk_marche primary key (idmarche)
);

create table achatvente
(
	idachatvente serial,
	prix integer,
	quantite integer,
	idmarche integer,
	constraint pk_achatvente primary key (idachatvente),
	constraint fk_marche foreign key (idmarche) references marche(idmarche) on update cascade
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

insert into droit values(1,'Administrateur');
insert into droit values(2,'Utilisateur');
insert into droit values(3,'MarketMaker');

insert into utilisateur values (default, 'Souare' , 'Pape'  , 'souarep' , 'amoi',    default,default);
insert into utilisateur values (default, 'Caboche', 'Maxime', 'cabochem', 'trololo', default,default);
insert into utilisateur values (default, 'admin'  , 'admin' , 'admin'   , 'admin' ,  0      ,'administrateur');
insert into utilisateur values (default, 'mm'     , 'mm'    , 'mm'      ,  'mm'  ,  0      ,'marketmaker');

insert into marche values (default, 'Lille gagnera son prochain match', 'Lille fera match nul ou perdra son prochain match', '05-02-2014');
insert into marche values (default, 'Au RU à midi ça sera mangeable', 'Au UR le soir ça sera dégueu', '12-02-2014');

insert into titre values (default, 1, default, default);
insert into titre values (default, 1, default, default);
insert into titre values (default, 1, default, '2014-02-05');
insert into titre values (default, 1, default, '2014-02-04');
insert into titre values (default, 1, default, '2014-02-03');
insert into titre values (default, 1, default, '2014-02-02');
insert into titre values (default, 1, default, '2014-02-01');
insert into titre values (default, 1, default, '2014-01-31');

insert into titre values (default, 2, default, default);
insert into titre values (default, 2, default, default);

insert into achatvente values (default, 47, 61, 1);
insert into achatvente values (default, 49, 5, 1);
insert into achatvente values (default, 44, 563, 1);

insert into achatvente values (default, 12, 341, 1);
insert into achatvente values (default, 42, 69, 1);
insert into achatvente values (default, 31, 80, 1);
insert into achatvente values (default, 78, 125, 1);
insert into achatvente values (default, 59, 32, 1);
insert into achatvente values (default, 26, 55, 1);

insert into achatvente values (default, 36, 395, 2);

insert into transactions values (default, 1, 1);
insert into transactions values (default, 2, 2);
insert into transactions values (default, 3, 3);

insert into transactions values (default, 4, 4);

insert into transactions values (default, 5, 5);
insert into transactions values (default, 6, 6);
insert into transactions values (default, 7, 7);
insert into transactions values (default, 8, 8);
insert into transactions values (default, 9, 9);
insert into transactions values (default, 10, 10);