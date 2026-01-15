package pojo;

public class SkillMasterPojo {

    private int skillId;
    private String skillName;
    private String creationTime;
    private String lastModTime;

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getLastModTime() {
        return lastModTime;
    }

    public void setLastModTime(String lastModTime) {
        this.lastModTime = lastModTime;
    }

    @Override
    public String toString() {
        return "SkillMasterPojo{" +
                "skillId=" + skillId +
                ", skillName='" + skillName + '\'' +
                ", creationTime='" + creationTime + '\'' +
                ", lastModTime='" + lastModTime + '\'' +
                '}';
    }
}
