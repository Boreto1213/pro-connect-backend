package fontys.sem3.proconnectbackend.business.usecases.service;

import fontys.sem3.proconnectbackend.domain.Tag;

import java.util.ArrayList;

public interface GetAllTagsUseCase {
    ArrayList<Tag> getAllTags();
}
