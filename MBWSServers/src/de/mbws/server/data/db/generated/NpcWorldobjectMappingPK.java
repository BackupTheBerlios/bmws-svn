package de.mbws.server.data.db.generated;
// Generated 14.04.2006 22:07:26 by Hibernate Tools 3.1.0.beta4



/**
 * NpcWorldobjectMappingPK generated by hbm2java
 */

public class NpcWorldobjectMappingPK  implements java.io.Serializable {


    // Fields    

     private Long worldobjectId;
     private Long npcId;


    // Constructors

    /** default constructor */
    public NpcWorldobjectMappingPK() {
    }

    
    /** full constructor */
    public NpcWorldobjectMappingPK(Long worldobjectId, Long npcId) {
        this.worldobjectId = worldobjectId;
        this.npcId = npcId;
    }
    

   
    // Property accessors

    public Long getWorldobjectId() {
        return this.worldobjectId;
    }
    
    public void setWorldobjectId(Long worldobjectId) {
        this.worldobjectId = worldobjectId;
    }

    public Long getNpcId() {
        return this.npcId;
    }
    
    public void setNpcId(Long npcId) {
        this.npcId = npcId;
    }
   








}
