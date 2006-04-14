
    create table account (
        username varchar(255) not null,
        password varchar(200) not null,
        emailaddress varchar(200) not null,
        active bit not null,
        accesslevel smallint not null,
        primary key (username)
    ) type=InnoDB;

    create table armor (
        id bigint not null,
        damage_reducing integer not null,
        min_skill_value integer not null,
        primary key (id)
    ) type=InnoDB;

    create table character_item_mapping (
        id bigint not null auto_increment,
        position varchar(1) not null,
        currentcondition integer not null,
        deposit_item integer not null,
        item_id bigint,
        characterdata_id bigint,
        primary key (id)
    ) type=InnoDB;

    create table character_skill_mapping (
        characterdata_id bigint not null,
        skill_id integer not null,
        value integer not null,
        primary key (characterdata_id, skill_id)
    ) type=InnoDB;

    create table character_status (
        id bigint not null auto_increment,
        charstatus varchar(1) not null,
        pvp bit not null,
        gamestatus varchar(1) not null,
        coordinate_x integer not null,
        coordinate_y integer not null,
        coordinate_z integer not null,
        currentmana integer not null,
        currenthealth integer not null,
        currentstamina integer not null,
        map_id integer,
        primary key (id)
    ) type=InnoDB;

    create table character_visualappearance (
        id bigint not null auto_increment,
        height integer not null,
        skin_color integer not null,
        face_type integer not null,
        hair_color integer not null,
        hair_style integer not null,
        hair_facial integer not null,
        item_head integer not null,
        item_shoulders integer not null,
        item_shirt integer not null,
        item_chest integer not null,
        item_belt integer not null,
        item_legs integer not null,
        item_boots integer not null,
        item_bracers integer not null,
        item_gloves integer not null,
        item_cape integer not null,
        item_hand_left integer not null,
        item_hand_right integer not null,
        primary key (id)
    ) type=InnoDB;

    create table character_worldobject_mapping (
        worldobject_id bigint not null,
        characterdata_id bigint not null,
        primary key (worldobject_id, characterdata_id)
    ) type=InnoDB;

    create table characterdata (
        id bigint not null auto_increment,
        charactername varchar(30) not null,
        gender varchar(1) not null,
        health integer not null,
        mana integer not null,
        stamina integer not null,
        age smallint not null,
        race_id integer,
        account_username varchar(255),
        primary key (id)
    ) type=InnoDB;

    create table configuration (
        id integer not null,
        value varchar(255) not null,
        primary key (id)
    ) type=InnoDB;

    create table item (
        id bigint not null auto_increment,
        name varchar(45) not null,
        max_condition integer not null,
        weight bigint not null,
        item_size bigint not null,
        containersize integer not null,
        item_type_id integer,
        primary key (id)
    ) type=InnoDB;

    create table item_type (
        id integer not null auto_increment,
        name varchar(255) not null,
        wearable bit not null,
        primary key (id)
    ) type=InnoDB;

    create table map (
        id integer not null,
        name varchar(45) not null,
        filename varchar(255) not null,
        primary key (id)
    ) type=InnoDB;

    create table npc (
        id bigint not null auto_increment,
        name varchar(40) not null,
        health integer not null,
        mana integer not null,
        stamina integer not null,
        age integer not null,
        isspecial bit not null,
        race_id integer,
        primary key (id)
    ) type=InnoDB;

    create table npc_skill_mapping (
        npc_id bigint not null,
        skill_id integer not null,
        value integer not null,
        primary key (npc_id, skill_id)
    ) type=InnoDB;

    create table npc_status (
        npc_id bigint not null,
        npcstatus varchar(1) not null,
        coordinate_x integer,
        coordinate_z integer,
        currentmana integer,
        currenthealth integer,
        currentstamina integer,
        map_id integer,
        primary key (npc_id)
    ) type=InnoDB;

    create table npc_worldobject_mapping (
        worldobject_id bigint not null,
        npc_id bigint not null,
        primary key (worldobject_id, npc_id)
    ) type=InnoDB;

    create table race (
        id integer not null auto_increment,
        name varchar(45) not null,
        basehealth integer not null,
        basemana integer not null,
        basestamina integer not null,
        isplayable bit not null,
        primary key (id)
    ) type=InnoDB;

    create table skill (
        id integer not null auto_increment,
        name varchar(255),
        usage_cost_health integer not null,
        usage_cost_stamina integer not null,
        usage_cost_mana integer not null,
        usage_cost_aggression integer not null,
        fp_costs_buying integer not null,
        gold_costs_buying integer not null,
        use_utomatic bit not null,
        use__delay_time integer not null,
        effect_onetime bit not null,
        effect_interval integer not null,
        use_duration integer not null,
        controller_class varchar(255) not null,
        skill_group_id integer,
        primary key (id)
    ) type=InnoDB;

    create table skill_group (
        id integer not null auto_increment,
        primary key (id)
    ) type=InnoDB;

    create table skill_race_mapping (
        race_id integer not null,
        skill_id integer not null,
        available bit not null,
        availableatstartup bit not null,
        autogainatstartup bit not null,
        max_category smallint,
        onetime_modifier integer,
        enhancement_modifier integer,
        primary key (race_id, skill_id)
    ) type=InnoDB;

    create table weapon (
        id bigint not null,
        damage_factor integer not null,
        min_skill_value integer not null,
        range integer,
        primary key (id)
    ) type=InnoDB;

    create table worldobject (
        id bigint not null,
        name varchar(255) not null,
        description text not null,
        coordinate_x integer not null,
        coordinate_y integer not null,
        coordinate_z integer not null,
        worldobject_type_id integer,
        map_id integer,
        primary key (id)
    ) type=InnoDB;

    create table worldobject_type (
        id integer not null,
        name varchar(255) not null,
        moveable tinyint not null,
        maxspeed integer,
        stamina integer,
        primary key (id)
    ) type=InnoDB;

    create table zoneserver (
        id integer not null,
        name varchar(45) not null,
        ip varchar(15) not null,
        port integer not null,
        primary key (id)
    ) type=InnoDB;

    create table zoneserver_map_mapping (
        map_id integer not null,
        zoneserver_id integer not null,
        active tinyint not null,
        primary key (map_id, zoneserver_id)
    ) type=InnoDB;

    alter table armor 
        add index FK58C613FD518DD14 (id), 
        add constraint FK58C613FD518DD14 
        foreign key (id) 
        references item (id);

    alter table character_item_mapping 
        add index FKCC2750F8533B89C0 (item_id), 
        add constraint FKCC2750F8533B89C0 
        foreign key (item_id) 
        references item (id);

    alter table character_item_mapping 
        add index FKCC2750F8B69CAED4 (characterdata_id), 
        add constraint FKCC2750F8B69CAED4 
        foreign key (characterdata_id) 
        references characterdata (id);

    alter table character_skill_mapping 
        add index FK5E49FA4A4E3D4BF4 (skill_id), 
        add constraint FK5E49FA4A4E3D4BF4 
        foreign key (skill_id) 
        references skill (id);

    alter table character_skill_mapping 
        add index FK5E49FA4AB69CAED4 (characterdata_id), 
        add constraint FK5E49FA4AB69CAED4 
        foreign key (characterdata_id) 
        references characterdata (id);

    alter table character_status 
        add index FKE579E1C86B949A94 (map_id), 
        add constraint FKE579E1C86B949A94 
        foreign key (map_id) 
        references map (id);

    alter table character_status 
        add index FKE579E1C8E75B3D68 (id), 
        add constraint FKE579E1C8E75B3D68 
        foreign key (id) 
        references characterdata (id);

    alter table character_visualappearance 
        add index FKCD5971FAE75B3D68 (id), 
        add constraint FKCD5971FAE75B3D68 
        foreign key (id) 
        references characterdata (id);

    alter table character_worldobject_mapping 
        add index FKBBF741CA83362074 (worldobject_id), 
        add constraint FKBBF741CA83362074 
        foreign key (worldobject_id) 
        references worldobject (id);

    alter table character_worldobject_mapping 
        add index FKBBF741CAB69CAED4 (characterdata_id), 
        add constraint FKBBF741CAB69CAED4 
        foreign key (characterdata_id) 
        references characterdata (id);

    alter table characterdata 
        add index FK9556EE53ECB8900 (race_id), 
        add constraint FK9556EE53ECB8900 
        foreign key (race_id) 
        references race (id);

    alter table characterdata 
        add index FK9556EE53AF23962F (account_username), 
        add constraint FK9556EE53AF23962F 
        foreign key (account_username) 
        references account (username);

    alter table item 
        add index FK317B1377476DE7 (item_type_id), 
        add constraint FK317B1377476DE7 
        foreign key (item_type_id) 
        references item_type (id);

    alter table npc 
        add index FK1AAE1ECB8900 (race_id), 
        add constraint FK1AAE1ECB8900 
        foreign key (race_id) 
        references race (id);

    alter table npc_skill_mapping 
        add index FKB23608024E3D4BF4 (skill_id), 
        add constraint FKB23608024E3D4BF4 
        foreign key (skill_id) 
        references skill (id);

    alter table npc_skill_mapping 
        add index FKB23608026E16F074 (npc_id), 
        add constraint FKB23608026E16F074 
        foreign key (npc_id) 
        references npc (id);

    alter table npc_status 
        add index FK6DD153106B949A94 (map_id), 
        add constraint FK6DD153106B949A94 
        foreign key (map_id) 
        references map (id);

    alter table npc_status 
        add index FK6DD153106E16F074 (npc_id), 
        add constraint FK6DD153106E16F074 
        foreign key (npc_id) 
        references npc (id);

    alter table npc_worldobject_mapping 
        add index FK2560258283362074 (worldobject_id), 
        add constraint FK2560258283362074 
        foreign key (worldobject_id) 
        references worldobject (id);

    alter table npc_worldobject_mapping 
        add index FK256025826E16F074 (npc_id), 
        add constraint FK256025826E16F074 
        foreign key (npc_id) 
        references npc (id);

    alter table skill 
        add index FK686CA51EBC801D (skill_group_id), 
        add constraint FK686CA51EBC801D 
        foreign key (skill_group_id) 
        references skill_group (id);

    alter table skill_race_mapping 
        add index FKCBC3548E4E3D4BF4 (skill_id), 
        add constraint FKCBC3548E4E3D4BF4 
        foreign key (skill_id) 
        references skill (id);

    alter table skill_race_mapping 
        add index FKCBC3548EECB8900 (race_id), 
        add constraint FKCBC3548EECB8900 
        foreign key (race_id) 
        references race (id);

    alter table weapon 
        add index FKD0CDC21CD518DD14 (id), 
        add constraint FKD0CDC21CD518DD14 
        foreign key (id) 
        references item (id);

    alter table worldobject 
        add index FK688F4951DB8AEAD7 (worldobject_type_id), 
        add constraint FK688F4951DB8AEAD7 
        foreign key (worldobject_type_id) 
        references worldobject_type (id);

    alter table worldobject 
        add index FK688F49516B949A94 (map_id), 
        add constraint FK688F49516B949A94 
        foreign key (map_id) 
        references map (id);

    alter table zoneserver_map_mapping 
        add index FKFBDB1A9BAE2807C0 (zoneserver_id), 
        add constraint FKFBDB1A9BAE2807C0 
        foreign key (zoneserver_id) 
        references zoneserver (id);

    alter table zoneserver_map_mapping 
        add index FKFBDB1A9B6B949A94 (map_id), 
        add constraint FKFBDB1A9B6B949A94 
        foreign key (map_id) 
        references map (id);
