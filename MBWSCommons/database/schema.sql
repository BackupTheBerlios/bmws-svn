CREATE TABLE race (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  basehealth INTEGER UNSIGNED NOT NULL,
  basemana INTEGER UNSIGNED NOT NULL,
  basestamina INTEGER UNSIGNED NOT NULL,
  modifier_strength INTEGER NOT NULL,
  modifier_intelligence INTEGER NOT NULL,
  modifier_dexterity INTEGER NOT NULL,
  modifier_constitution INTEGER NOT NULL,
  isplayable BOOL NOT NULL,
  description TEXT NOT NULL,
  PRIMARY KEY(id)
)
TYPE=InnoDB;

CREATE TABLE item_type (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  containersize INTEGER UNSIGNED NOT NULL,
  wearable BOOL NOT NULL,
  PRIMARY KEY(id)
)
TYPE=InnoDB;

CREATE TABLE configuration (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  value VARCHAR(255) NOT NULL,
  PRIMARY KEY(id)
)
TYPE=InnoDB;

CREATE TABLE map (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  filename VARCHAR(255) NOT NULL,
  PRIMARY KEY(id)
)
TYPE=InnoDB;

CREATE TABLE zoneserver (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  ip VARCHAR(15) NOT NULL,
  port INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(id)
)
TYPE=InnoDB;

CREATE TABLE account (
  username VARCHAR(20) NOT NULL,
  password VARCHAR(20) NOT NULL,
  emailaddress VARCHAR(45) NOT NULL,
  PRIMARY KEY(username)
)
TYPE=InnoDB;

CREATE TABLE skill_group (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY(id)
)
TYPE=InnoDB;

CREATE TABLE worldobject_type (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  moveable BOOL NOT NULL,
  maxspeed INTEGER UNSIGNED NULL,
  stamina INTEGER UNSIGNED NULL,
  PRIMARY KEY(id)
)
TYPE=InnoDB;

CREATE TABLE skill (
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  skill_group_id INTEGER UNSIGNED NOT NULL,
  name VARCHAR(45) NOT NULL,
  availableatstartup BOOL NOT NULL,
  oppositeskill INTEGER UNSIGNED NULL,
  costhealth INTEGER UNSIGNED NOT NULL,
  coststamina INTEGER UNSIGNED NOT NULL,
  costmana INTEGER UNSIGNED NOT NULL,
  xpcostsbuying INTEGER UNSIGNED NOT NULL,
  goldcostsbuying INTEGER UNSIGNED NOT NULL,
  xpcost_enhancement INTEGER UNSIGNED NOT NULL,
  basevalue_formula INTEGER UNSIGNED NOT NULL,
  use_duration INTEGER UNSIGNED NOT NULL,
  use_time INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(id),
  INDEX skill_FKIndex1(skill_group_id),
  FOREIGN KEY(skill_group_id)
    REFERENCES skill_group(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

CREATE TABLE npc (
  id BIGINT NOT NULL AUTO_INCREMENT,
  race_id INTEGER UNSIGNED NOT NULL,
  name VARCHAR(255) NOT NULL,
  health INTEGER UNSIGNED NOT NULL,
  mana INTEGER UNSIGNED NOT NULL,
  stamina INTEGER UNSIGNED NOT NULL,
  age INTEGER UNSIGNED NOT NULL,
  strength INTEGER UNSIGNED NOT NULL,
  intelligence INTEGER UNSIGNED NOT NULL,
  dexterity INTEGER UNSIGNED NOT NULL,
  constitution INTEGER UNSIGNED NOT NULL,
  isspecial BOOL NOT NULL,
  PRIMARY KEY(id),
  INDEX npc_FKIndex1(race_id),
  FOREIGN KEY(race_id)
    REFERENCES race(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

CREATE TABLE item (
  id BIGINT NOT NULL,
  item_type_id INTEGER UNSIGNED NOT NULL,
  name VARCHAR(45) NOT NULL,
  `condition` INTEGER UNSIGNED NOT NULL,
  weight DECIMAL NOT NULL,
  size DECIMAL NOT NULL,
  description TEXT NOT NULL,
  PRIMARY KEY(id),
  INDEX Item_FKIndex1(item_type_id),
  FOREIGN KEY(item_type_id)
    REFERENCES item_type(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

CREATE TABLE worldobject (
  id BIGINT NOT NULL AUTO_INCREMENT,
  map_id INTEGER UNSIGNED NOT NULL,
  worldobject_type_id INTEGER UNSIGNED NOT NULL,
  name VARCHAR(255) NOT NULL,
  description TEXT NOT NULL,
  coordinate_x INTEGER UNSIGNED NOT NULL,
  coordinate_y INTEGER UNSIGNED NOT NULL,
  coordinate_z INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(id),
  INDEX worldobject_FKIndex1(worldobject_type_id),
  INDEX worldobject_FKIndex2(map_id),
  FOREIGN KEY(worldobject_type_id)
    REFERENCES worldobject_type(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(map_id)
    REFERENCES map(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

CREATE TABLE skill_race_mapping (
  race_id INTEGER UNSIGNED NOT NULL,
  skill_id INTEGER UNSIGNED NOT NULL,
  hasonstartup BOOL NOT NULL,
  PRIMARY KEY(race_id, skill_id),
  INDEX skill_race_mapping_FKIndex1(skill_id),
  INDEX skill_race_mapping_FKIndex2(race_id),
  FOREIGN KEY(skill_id)
    REFERENCES skill(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(race_id)
    REFERENCES race(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

CREATE TABLE zoneserver_map_mapping (
  map_id INTEGER UNSIGNED NOT NULL,
  zoneserver_id INTEGER UNSIGNED NOT NULL,
  active BOOL NOT NULL,
  PRIMARY KEY(map_id, zoneserver_id),
  INDEX ZoneServer_Map_Mapping_FKIndex1(zoneserver_id),
  INDEX ZoneServer_Map_Mapping_FKIndex2(map_id),
  FOREIGN KEY(zoneserver_id)
    REFERENCES zoneserver(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(map_id)
    REFERENCES map(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

CREATE TABLE characterdata (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  charactername VARCHAR(30) NOT NULL,
  race_id INTEGER UNSIGNED NOT NULL,
  account_username VARCHAR(20) NOT NULL,
  gender CHAR NOT NULL,
  health INTEGER UNSIGNED NOT NULL,
  mana INTEGER UNSIGNED NOT NULL,
  stamina INTEGER UNSIGNED NOT NULL,
  age SMALLINT UNSIGNED NOT NULL,
  strength INTEGER UNSIGNED NOT NULL,
  intelligence INTEGER UNSIGNED NOT NULL,
  dexterity INTEGER UNSIGNED NOT NULL,
  constitution INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(id),
  INDEX Character_FKIndex1(account_username),
  INDEX Character_FKIndex2(race_id),
  FOREIGN KEY(account_username)
    REFERENCES account(username)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(race_id)
    REFERENCES race(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

CREATE TABLE character_item_mapping (
  id BIGINT NOT NULL,
  characterdata_id BIGINT UNSIGNED NOT NULL,
  item_id BIGINT NOT NULL,
  position CHAR NOT NULL,
  currentcondition INTEGER UNSIGNED NOT NULL,
  deposit_item INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(id),
  INDEX character_item_mapping_FKIndex1(item_id),
  INDEX character_item_mapping_FKIndex2(characterdata_id),
  FOREIGN KEY(item_id)
    REFERENCES item(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(characterdata_id)
    REFERENCES characterdata(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

CREATE TABLE character_skill_mapping (
  characterdata_id BIGINT UNSIGNED NOT NULL,
  skill_id INTEGER UNSIGNED NOT NULL,
  bonusvalue INTEGER UNSIGNED NOT NULL,
  basevalue INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(characterdata_id, skill_id),
  INDEX character_skill_mapping_FKIndex1(characterdata_id),
  INDEX character_skill_mapping_FKIndex2(skill_id),
  FOREIGN KEY(characterdata_id)
    REFERENCES characterdata(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(skill_id)
    REFERENCES skill(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

CREATE TABLE npc_skill_mapping (
  npc_id BIGINT NOT NULL,
  skill_id INTEGER UNSIGNED NOT NULL,
  value INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(npc_id, skill_id),
  INDEX npc_skill_mapping_FKIndex1(skill_id),
  INDEX npc_skill_mapping_FKIndex2(npc_id),
  FOREIGN KEY(skill_id)
    REFERENCES skill(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(npc_id)
    REFERENCES npc(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

CREATE TABLE character_worldobject_mapping (
  worldobject_id BIGINT NOT NULL,
  characterdata_id BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY(worldobject_id, characterdata_id),
  INDEX character_worldobject_mapping_FKIndex1(worldobject_id),
  INDEX character_worldobject_mapping_FKIndex2(characterdata_id),
  FOREIGN KEY(worldobject_id)
    REFERENCES worldobject(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(characterdata_id)
    REFERENCES characterdata(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

CREATE TABLE npc_status (
  npc_id BIGINT NOT NULL,
  npcstatus CHAR NOT NULL,
  map_id INTEGER UNSIGNED NOT NULL,
  gamestatus CHAR NULL,
  coordinate_x INTEGER UNSIGNED NULL,
  coordinate_z INTEGER UNSIGNED NULL,
  currentmana INTEGER UNSIGNED NULL,
  currenthelth INTEGER UNSIGNED NULL,
  currentstamina INTEGER UNSIGNED NULL,
  currentstrength INTEGER UNSIGNED NULL,
  currentintelligence INTEGER UNSIGNED NULL,
  currentdexterity INTEGER UNSIGNED NULL,
  currentconstitution INTEGER UNSIGNED NULL,
  PRIMARY KEY(npc_id),
  INDEX npc_status_FKIndex1(npc_id),
  INDEX npc_status_FKIndex2(map_id),
  FOREIGN KEY(npc_id)
    REFERENCES npc(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(map_id)
    REFERENCES map(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

CREATE TABLE character_status (
  id BIGINT UNSIGNED NOT NULL,
  charstatus CHAR NOT NULL,
  pvp CHAR NOT NULL,
  gamestatus CHAR NOT NULL,
  map_id INTEGER UNSIGNED NOT NULL,
  coordinate_x INTEGER UNSIGNED NOT NULL,
  coordinate_y INTEGER UNSIGNED NOT NULL,
  coordinate_z INTEGER UNSIGNED NOT NULL,
  currentmana INTEGER UNSIGNED NOT NULL,
  currenthealth INTEGER UNSIGNED NOT NULL,
  currentstamina INTEGER UNSIGNED NOT NULL,
  currentstrength INTEGER UNSIGNED NOT NULL,
  currentinteligence INTEGER UNSIGNED NOT NULL,
  currentdexterity INTEGER UNSIGNED NOT NULL,
  currentconstitution INTEGER UNSIGNED NOT NULL,
  freexp INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(id),
  INDEX Character_Status_FKIndex1(map_id),
  UNIQUE INDEX character_status_FKIndex2(id),
  FOREIGN KEY(map_id)
    REFERENCES map(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(id)
    REFERENCES characterdata(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

CREATE TABLE npc_worldobject_mapping (
  worldobject_id BIGINT NOT NULL,
  npc_id BIGINT NOT NULL,
  PRIMARY KEY(worldobject_id, npc_id),
  INDEX npc_worldobject_mapping_FKIndex1(npc_id),
  INDEX npc_worldobject_mapping_FKIndex2(worldobject_id),
  FOREIGN KEY(npc_id)
    REFERENCES npc(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(worldobject_id)
    REFERENCES worldobject(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

CREATE TABLE character_visualappearance (
  id BIGINT UNSIGNED NOT NULL,
  height INTEGER UNSIGNED NOT NULL,
  skin_color INTEGER UNSIGNED NOT NULL,
  face_type INTEGER UNSIGNED NOT NULL,
  hair_color INTEGER UNSIGNED NOT NULL,
  hair_style INTEGER UNSIGNED NOT NULL,
  hair_facial INTEGER UNSIGNED NOT NULL,
  item_head INTEGER UNSIGNED NOT NULL,
  item_shoulders INTEGER UNSIGNED NOT NULL,
  item_shirt INTEGER UNSIGNED NOT NULL,
  item_chest INTEGER UNSIGNED NOT NULL,
  item_belt INTEGER UNSIGNED NOT NULL,
  item_legs INTEGER UNSIGNED NOT NULL,
  item_boots INTEGER UNSIGNED NOT NULL,
  item_bracers INTEGER UNSIGNED NOT NULL,
  item_gloves INTEGER UNSIGNED NOT NULL,
  item_cape INTEGER UNSIGNED NOT NULL,
  item_hand_left INTEGER UNSIGNED NOT NULL,
  item_hand_right INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(id),
  FOREIGN KEY(id)
    REFERENCES characterdata(id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

