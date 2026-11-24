package org.example.models;
import java.util.List;
import java.util.Map;
public record PetData(long id, long categoryId, String categoryName, String name,
                      String photoUrl, long tagId, String tagName, String status) {
    public Map<String,Object> payload() {
        return Map.of("id",id,"category",Map.of("id",categoryId,"name",categoryName),
            "name",name,"photoUrls",List.of(photoUrl),"tags",List.of(Map.of("id",tagId,"name",tagName)),"status",status);
    }
    public PetData updated(String newName, String newStatus) {
        return new PetData(id,categoryId,categoryName,newName,photoUrl,tagId,tagName,newStatus);
    }
}
