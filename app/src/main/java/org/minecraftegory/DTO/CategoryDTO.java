package org.minecraftegory.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class CategoryDTO {

    private int id;

    private Date creationDate;

    private int parentId;

    private List<Integer> childrenIds;
}
