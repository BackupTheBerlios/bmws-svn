package de.mbws.server.data.db.generated;
// Generated 14.04.2006 22:07:26 by Hibernate Tools 3.1.0.beta4



/**
 * NpcSkillMappingPK generated by hbm2java
 */

public class NpcSkillMappingPK  implements java.io.Serializable {


    // Fields    

     private Long npcId;
     private Integer skillId;


    // Constructors

    /** default constructor */
    public NpcSkillMappingPK() {
    }

    
    /** full constructor */
    public NpcSkillMappingPK(Long npcId, Integer skillId) {
        this.npcId = npcId;
        this.skillId = skillId;
    }
    

   
    // Property accessors

    public Long getNpcId() {
        return this.npcId;
    }
    
    public void setNpcId(Long npcId) {
        this.npcId = npcId;
    }

    public Integer getSkillId() {
        return this.skillId;
    }
    
    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
    }
   








}
