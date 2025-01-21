package org.minecraftegory.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaginatedDTO {

    private int page;

    private int maxPage;

    private List<CategoryDTO> categories;
}
