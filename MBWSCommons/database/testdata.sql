# MySQL-Front Dump 2.2
#
# Host: localhost   Database: game
#--------------------------------------------------------
# Server version 4.1.12a


#
# Dumping data for table 'account'
#
INSERT INTO account VALUES("minisack","minisack","minisack@sack.de");
INSERT INTO account VALUES("sack","sack","sack@sack.de");


#
# Dumping data for table 'character_item_mapping'
#


#
# Dumping data for table 'character_skill_mapping'
#



#
# Dumping data for table 'characterdata'
#
INSERT INTO characterdata VALUES("1","Sir Sackerus","1","sack","0","0","0","0","0","0","0","0","0");
INSERT INTO characterdata VALUES("2","Mr SugaMaDig","1","minisack","0","0","0","0","0","0","0","0","0");
INSERT INTO characterdata VALUES("3","Mag ma Dig","1","sack","0","0","0","0","0","0","0","0","0");
#
# Dumping data for table 'character_status'
#
INSERT INTO character_status VALUES("1","A","0","0","1","1","10","0","0","0","0","0","0","0","0","0");
INSERT INTO character_status VALUES("2","A","0","0","1","10","1","0","0","0","0","0","0","0","0","0");
INSERT INTO character_status VALUES("3","A","0","0","1","1","10","0","0","0","0","0","0","0","0","0");

#
# Dumping data for table 'character_visualappearance'
#
INSERT INTO character_visualappearance VALUES("1","1","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0");
INSERT INTO character_visualappearance VALUES("2","2","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0");
INSERT INTO character_visualappearance VALUES("3","3","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0");

#
# Dumping data for table 'character_worldobject_mapping'
#



#
# Dumping data for table 'configuration'
#


#
# Dumping data for table 'item_type'
#
INSERT INTO item_type values("1","sword","0","0");


#
# Dumping data for table 'item'
#
INSERT INTO item values("1","1","Runesword","100","3","1","My Runesword is the best","/models/weapons/rune_sword.3ds","/icons/weapons/rune_sword.png");
#
# Dumping data for table 'map'
#
INSERT INTO map VALUES("1","Testkarte","");


#
# Dumping data for table 'npc'
#


#
# Dumping data for table 'npc_skill_mapping'
#


#
# Dumping data for table 'npc_status'
#


#
# Dumping data for table 'npc_worldobject_mapping'
#


#
# Dumping data for table 'race'
#
INSERT INTO race VALUES("1","human","0","0","0","0","0","0","0","0","Ganz toll, zerstren alles was ihnen in den Weg kommt.");


#
# Dumping data for table 'skill'
#


#
# Dumping data for table 'skill_group'
#


#
# Dumping data for table 'skill_race_mapping'
#


#
# Dumping data for table 'worldobject'
#


#
# Dumping data for table 'worldobject_type'
#


#
# Dumping data for table 'zoneserver'
#


#
# Dumping data for table 'zoneserver_map_mapping'
#


#
# Dumping data for table 'language_text_mapping'
#
INSERT INTO language_text_mapping VALUES("1","race.1","1","Mensch");
INSERT INTO language_text_mapping VALUES("2","race.1","2","Human");
insert into language_text_mapping (id,text_key,languages_id,text) values (3,'yes',1,'ja');
insert into language_text_mapping (id,text_key,languages_id,text) values (4,'yes',2,'yes');
insert into language_text_mapping (id,text_key,languages_id,text) values (5,'no',1,'nein');
insert into language_text_mapping (id,text_key,languages_id,text) values (6,'no',2,'no');
insert into language_text_mapping (id,text_key,languages_id,text) values (7,'confirm_delete',1,'Wirklich lschen ?');
insert into language_text_mapping (id,text_key,languages_id,text) values (8,'confirm_delete',2,'Really delete ?');
insert into language_text_mapping (id,text_key,languages_id,text) values (9,'login',1,'Login');
insert into language_text_mapping (id,text_key,languages_id,text) values (10,'login',2,'Login');
insert into language_text_mapping (id,text_key,languages_id,text) values (11,'password',1,'Passwort');
insert into language_text_mapping (id,text_key,languages_id,text) values (12,'password',2,'Password');
insert into language_text_mapping (id,text_key,languages_id,text) values (13,'create_account',1,'Konto anlegen');
insert into language_text_mapping (id,text_key,languages_id,text) values (14,'create_account',2,'Create Account');
insert into language_text_mapping (id,text_key,languages_id,text) values (15,'cancel',1,'Abbrechen');
insert into language_text_mapping (id,text_key,languages_id,text) values (16,'cancel',2,'Cancel');
insert into language_text_mapping (id,text_key,languages_id,text) values (17,'delete',1,'Lschen');
insert into language_text_mapping (id,text_key,languages_id,text) values (18,'delete',2,'Delete');
insert into language_text_mapping (id,text_key,languages_id,text) values (19,'create',1,'Anlegen');
insert into language_text_mapping (id,text_key,languages_id,text) values (20,'create',2,'Create');
insert into language_text_mapping (id,text_key,languages_id,text) values (21,'start',1,'Start');
insert into language_text_mapping (id,text_key,languages_id,text) values (22,'start',2,'Start');



#
# Dumping data for table 'languages'
#
INSERT INTO languages VALUES("1","de","Deutsch");
INSERT INTO languages VALUES("2","en","Englisch");