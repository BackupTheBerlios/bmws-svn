package de.mbws.server.data.db.generated;
// Generated 14.04.2006 22:07:26 by Hibernate Tools 3.1.0.beta4



/**
 * CharacterSkillMappingPK generated by hbm2java
 */

public class CharacterSkillMappingPK  implements java.io.Serializable {


    // Fields    

     private Long characterdataId;
     private Integer skillId;


    // Constructors

    /** default constructor */
    public CharacterSkillMappingPK() {
    }

    
    /** full constructor */
    public CharacterSkillMappingPK(Long characterdataId, Integer skillId) {
        this.characterdataId = characterdataId;
        this.skillId = skillId;
    }
    

   
    // Property accessors

    public Long getCharacterdataId() {
        return this.characterdataId;
    }
    
    public void setCharacterdataId(Long characterdataId) {
        this.characterdataId = characterdataId;
    }

    public Integer getSkillId() {
        return this.skillId;
    }
    
    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
    }
   








}
