package com.crio.starter.repositoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.crio.starter.data.MemeEntity;
import com.crio.starter.dto.MemeDto;
import com.crio.starter.repository.MemeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Log4j2
public class MemeRepositoryServiceImpl implements MemeRepositoryService{

    @Autowired
    private final MemeRepository memeRepository;

    @Autowired
    private ModelMapper modelMapper;

    public MemeDto postMeme(MemeEntity memeEntity) {
        MemeEntity meme = null;

        log.info("Meme in dbServiceImpl " + memeEntity);
        // if any of the field in meme is already present this will throw exception and null will be
        // returned
        try {
            meme = memeRepository.insert(memeEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        log.info("Meme generated with id " + meme);

        if(meme == null){
            return new MemeDto();
        }

        return this.modelMapper.map(meme, MemeDto.class);
    }

    @Override
    public List<MemeDto> getMemes() {
        List<MemeEntity> memeEntities = memeRepository.findAll();

        List<MemeDto> memeDtos = new ArrayList<>();

        for(MemeEntity memeEntity: memeEntities){
            memeDtos.add(this.modelMapper.map(memeEntity, MemeDto.class));
        }
        return memeDtos;
    }

    @Override
    public MemeDto getMeme(String memeId){
        Optional<MemeEntity> meme = memeRepository.findById(memeId);

        if(meme.isEmpty()){
            return new MemeDto();
        }

        log.info("Meme for memeId: " + memeId + " is " + meme);

        return this.modelMapper.map(meme.get(), MemeDto.class);
    }

}
