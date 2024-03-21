package fontys.sem3.proconnectbackend.business.converters;

import fontys.sem3.proconnectbackend.domain.Tag;
import fontys.sem3.proconnectbackend.persistence.entity.TagEntity;

public class TagConverter {
    public static Tag convert(TagEntity tagEntity) {
        Tag tag = new Tag();
        tag.setId(tagEntity.getId());
        tag.setText(tagEntity.getText());

        return tag;
    }
}
