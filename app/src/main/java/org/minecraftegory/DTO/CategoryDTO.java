package org.minecraftegory.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class CategoryDTO {

    private int id;

    private String name;

    private Date creationDate;

    private boolean root;

    private List<Integer> childrenId;

    private int parentId;
}
