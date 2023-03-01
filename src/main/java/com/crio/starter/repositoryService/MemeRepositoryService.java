package com.crio.starter.repositoryService;

import java.util.List;
import com.crio.starter.data.MemeEntity;
import com.crio.starter.dto.MemeDto;

public interface MemeRepositoryService {
    MemeDto postMeme(MemeEntity memeEntity);
    List<MemeDto> getMemes();
    MemeDto getMeme(String memeId);
}
