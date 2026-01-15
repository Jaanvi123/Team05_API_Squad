package payload;

import pojo.SkillMasterPojo;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class SkillMasterPayload {

    public static SkillMasterPojo buildCreateSkillPayload(String skillName, String creationTime, String lastModTime) {
        SkillMasterPojo pojo = new SkillMasterPojo();
        pojo.setSkillName(skillName);
        pojo.setCreationTime(creationTime);
        pojo.setLastModTime(lastModTime);
        return pojo;
    }

    public static String generateRandomSkillName() {
        return "Skill_" + UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }

    public static String generateCurrentIsoTime() {
        return OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
    public static SkillMasterPojo buildUpdateSkillPayload(String uniqueSkillName) {

        SkillMasterPojo pojo = new SkillMasterPojo();

        pojo.setSkillName(uniqueSkillName);
        pojo.setCreationTime("2021-10-04T18:14:01.957+00:00");
        pojo.setLastModTime("2021-10-04T18:14:01.957+00:00");

        return pojo;
    }

}
