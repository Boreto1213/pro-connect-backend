package fontys.sem3.proconnectbackend.business.usecases.service.impl;

import fontys.sem3.proconnectbackend.business.converters.TagConverter;
import fontys.sem3.proconnectbackend.business.usecases.service.GetAllTagsUseCase;
import fontys.sem3.proconnectbackend.domain.Tag;
import fontys.sem3.proconnectbackend.persistence.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetAllTagsUseCaseImpl implements GetAllTagsUseCase {
    private final TagRepository tagRepository;
    @Override
    public ArrayList<Tag> getAllTags() {
        return tagRepository.findAll().stream()
                .map(TagConverter::convert)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
