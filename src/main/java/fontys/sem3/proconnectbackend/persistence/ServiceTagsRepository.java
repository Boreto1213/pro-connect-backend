package fontys.sem3.proconnectbackend.persistence;

public interface ServiceTagsRepository {
    void addTagToService(Long serviceId, Long tagId);
    void removeTagToService(Long serviceId, Long tagId);
    void getTagsByServiceId(Long serviceId);
}
