package de.mbws.server.data.db.generated;
// Generated 14.04.2006 22:07:26 by Hibernate Tools 3.1.0.beta4



/**
 * CharacterWorldobjectMappingPK generated by hbm2java
 */

public class CharacterWorldobjectMappingPK  implements java.io.Serializable {


    // Fields    

     private Long worldobjectId;
     private Long characterdataId;


    // Constructors

    /** default constructor */
    public CharacterWorldobjectMappingPK() {
    }

    
    /** full constructor */
    public CharacterWorldobjectMappingPK(Long worldobjectId, Long characterdataId) {
        this.worldobjectId = worldobjectId;
        this.characterdataId = characterdataId;
    }
    

   
    // Property accessors

    public Long getWorldobjectId() {
        return this.worldobjectId;
    }
    
    public void setWorldobjectId(Long worldobjectId) {
        this.worldobjectId = worldobjectId;
    }

    public Long getCharacterdataId() {
        return this.characterdataId;
    }
    
    public void setCharacterdataId(Long characterdataId) {
        this.characterdataId = characterdataId;
    }
   








}
