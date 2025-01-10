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

    private boolean isRoot;

    private int childrenNumber;

    private List<Integer> childrenId;

    private int parentId;
}
