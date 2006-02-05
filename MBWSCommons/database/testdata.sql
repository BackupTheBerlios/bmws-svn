# MySQL-Front Dump 2.2
#
# Host: localhost   Database: game
#--------------------------------------------------------
# Server version 4.1.12a


#
# Dumping data for table 'account'
#
INSERT INTO account VALUES('minisack','minisack','minisack@sack.de');
INSERT INTO account VALUES('sack','sack','sack@sack.de');


#
# Dumping data for table 'character_item_mapping'
#


#
# Dumping data for table 'character_skill_mapping'
#



#
# Dumping data for table 'characterdata'
#
INSERT INTO characterdata VALUES('1','Sir Sackerus','1','sack','0','0','0','0','0','0','0','0','0');
INSERT INTO characterdata VALUES('2','Mr SugaMaDig','1','minisack','0','0','0','0','0','0','0','0','0');
INSERT INTO characterdata VALUES('3','Mag ma Dig','1','sack','0','0','0','0','0','0','0','0','0');
#
# Dumping data for table 'character_status'
#
INSERT INTO character_status VALUES('1','A','0','0','1','1','10','0','0','0','0','0','0','0','0','0');
INSERT INTO character_status VALUES('2','A','0','0','1','10','1','0','0','0','0','0','0','0','0','0');
INSERT INTO character_status VALUES('3','A','0','0','1','1','10','0','0','0','0','0','0','0','0','0');

#
# Dumping data for table 'character_visualappearance'
#
INSERT INTO character_visualappearance VALUES('1','1','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0');
INSERT INTO character_visualappearance VALUES('2','2','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0');
INSERT INTO character_visualappearance VALUES('3','3','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0');

#
# Dumping data for table 'character_worldobject_mapping'
#



#
# Dumping data for table 'configuration'
#


#
# Dumping data for table 'item_type'
#
INSERT INTO item_type values('1','sword','0','0');


#
# Dumping data for table 'item'
#
INSERT INTO item values('1','1','Runesword','100','3','1','My Runesword is the best','/models/weapons/rune_sword.3ds','/icons/weapons/rune_sword.png');
#
# Dumping data for table 'map'
#
INSERT INTO map VALUES('1','Testkarte','');


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
INSERT INTO race VALUES('1','human','0','0','0','0','0','0','0','0','Ganz toll, zerstren alles was ihnen in den Weg kommt.');


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
INSERT INTO language_text_mapping (text_key,languages_id,text) VALUES('race.name.1','1','Mensch');
INSERT INTO language_text_mapping (text_key,languages_id,text) VALUES('race.name.1','2','Human');
INSERT INTO language_text_mapping (text_key,languages_id, text) VALUES('race.description.1','1','Sie zerstören naturbeding fast alles innerhalb ihres kurzen Lebens.');
INSERT INTO language_text_mapping (text_key,languages_id, text) VALUES('race.description.1','2','They naturally destroy almost everything during their short life.');
insert into language_text_mapping (text_key,languages_id, text) values ('yes',1,'ja');
insert into language_text_mapping (text_key,languages_id, text) values ('yes',2,'yes');
insert into language_text_mapping (text_key,languages_id, text) values ('no',1,'nein');
insert into language_text_mapping (text_key,languages_id, text) values ('no',2,'no');
insert into language_text_mapping (text_key,languages_id, text) values ('confirm_delete',1,'Wirklich löschen ?');
insert into language_text_mapping (text_key,languages_id, text) values ('confirm_delete',2,'Really delete ?');
insert into language_text_mapping (text_key,languages_id, text) values ('menu.button.login',1,'Anmelden');
insert into language_text_mapping (text_key,languages_id, text) values ('menu.button.login',2,'Login');
insert into language_text_mapping (text_key,languages_id, text) values ('menu.label.password',1,'Passwort');
insert into language_text_mapping (text_key,languages_id, text) values ('menu.label.password',2,'Password');
insert into language_text_mapping (text_key,languages_id, text) values ('menu.button.create.account',1,'Konto anlegen');
insert into language_text_mapping (text_key,languages_id, text) values ('menu.button.create.account',2,'Create Account');
insert into language_text_mapping (text_key,languages_id, text) values ('cancel',1,'Abbrechen');
insert into language_text_mapping (text_key,languages_id, text) values ('cancel',2,'Cancel');
insert into language_text_mapping (text_key,languages_id, text) values ('delete',1,'Löschen');
insert into language_text_mapping (text_key,languages_id, text) values ('delete',2,'Delete');
insert into language_text_mapping (text_key,languages_id, text) values ('create',1,'Anlegen');
insert into language_text_mapping (text_key,languages_id, text) values ('create',2,'Create');
insert into language_text_mapping (text_key,languages_id, text) values ('start',1,'Start');
insert into language_text_mapping (text_key,languages_id, text) values ('start',2,'Start');
insert into language_text_mapping (text_key,languages_id, text) values ('menu.button.exit',1,'Beenden');
insert into language_text_mapping (text_key,languages_id, text) values ('menu.button.exit',2,'Exit');
insert into language_text_mapping (text_key,languages_id, text) values ('menu.button.options',1,'Einstellungen');
insert into language_text_mapping (text_key,languages_id, text) values ('menu.button.options',2,'Options');
insert into language_text_mapping (text_key,languages_id, text) values ('menu.label.username',1,'Benutzername');
insert into language_text_mapping (text_key,languages_id, text) values ('menu.label.username',2,'Username');
insert into language_text_mapping (text_key,languages_id, text) values ('character.selection.button.logout',1,'Abmelden');
insert into language_text_mapping (text_key,languages_id, text) values ('character.selection.button.logout',2,'Logout');
insert into language_text_mapping (text_key,languages_id, text) values ('character.selection.button.enterworld',1,'Welt betreten');
insert into language_text_mapping (text_key,languages_id, text) values ('character.selection.button.enterworld',2,'Enter World');
insert into language_text_mapping (text_key,languages_id, text) values ('character.selection.button.createcharacter',1,'Erstellen');
insert into language_text_mapping (text_key,languages_id, text) values ('character.selection.button.createcharacter',2,'Create');
insert into language_text_mapping (text_key,languages_id, text) values ('character.selection.button.deletecharacter',1,'Lschen');
insert into language_text_mapping (text_key,languages_id, text) values ('character.selection.button.deletecharacter',2,'Delete');
insert into language_text_mapping (text_key,languages_id, text) values ('character.selection.label.menuheader',1,'Charakter Menu');
insert into language_text_mapping (text_key,languages_id, text) values ('character.selection.label.menuheader',2,'Character Menu');
insert into language_text_mapping (text_key,languages_id, text) values ('loadingscreen.label',1,'laden');
insert into language_text_mapping (text_key,languages_id, text) values ('loadingscreen.label',2,'loading');
insert into language_text_mapping (text_key,languages_id, text) values ('generic.button.back',1,'Zurück');
insert into language_text_mapping (text_key,languages_id, text) values ('generic.button.back',2,'Back');
insert into language_text_mapping (text_key,languages_id, text) values ('character.creation.button.create',1,'Erstellen');
insert into language_text_mapping (text_key,languages_id, text) values ('character.creation.button.create',2,'Create');
insert into language_text_mapping (text_key,languages_id, text) values ('account.creation.create',1,'Erstellen');
insert into language_text_mapping (text_key,languages_id, text) values ('account.creation.create',2,'Create');
insert into language_text_mapping (text_key,languages_id, text) values ('account.creation.label.passwordverification',1,'Passwort Bestätigung');
insert into language_text_mapping (text_key,languages_id, text) values ('account.creation.label.passwordverification',2,'Password Confirmation');
insert into language_text_mapping (text_key,languages_id, text) values ('account.creation.label.emailaddress',1,'E-Mail-Adresse');
insert into language_text_mapping (text_key,languages_id, text) values ('account.creation.label.emailaddress',2,'Emailaddress');

#
# Dumping data for table 'languages'
#
INSERT INTO languages VALUES('1','de','Deutsch');
INSERT INTO languages VALUES('2','en','Englisch');