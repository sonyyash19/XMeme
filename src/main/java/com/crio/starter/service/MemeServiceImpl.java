package com.crio.starter.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.crio.starter.data.MemeEntity;
import com.crio.starter.dto.MemeDto;
import com.crio.starter.exchange.MemeCreated;
import com.crio.starter.repositoryService.MemeRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Log4j2
public class MemeServiceImpl implements MemeService {

    @Autowired
    private final MemeRepositoryService memeRepositoryService;

    @Override
    public MemeCreated postMeme(MemeEntity memeEntity) {
        log.info("Meme Sent to db {}" + memeEntity);
        MemeDto meme = memeRepositoryService.postMeme(memeEntity);
        log.info("Meme received from db " + meme);
        return new MemeCreated(meme.getId());
    }

    @Override
    public List<MemeDto> getMemes() {
        List<MemeDto> meme = memeRepositoryService.getMemes();
        
        // sorted data in descending order
        meme = latestHundredData(meme);

        // if meme list size is greater then 100 we need to return latest 100 data
        if(meme.size() >= 100){
            List<MemeDto> latestHundredMeme = meme.subList(0, 100);
            return latestHundredMeme;
        }

        return meme;
    }

    // sort the data in descending order and return it
    private List<MemeDto> latestHundredData(List<MemeDto> meme){

        Collections.sort(meme, new SortMemeDto());
        return meme;
    }

    @Override
    public MemeDto getMeme(String memeId){
        MemeDto meme = memeRepositoryService.getMeme(memeId);
        log.info("Meme Received by Service " + meme);
        return meme;
    }

}

// Class to sort the meme list in the latest order
class SortMemeDto implements Comparator<MemeDto>{

    @Override
    public int compare(MemeDto o1, MemeDto o2){
        return o2.getId().compareTo(o1.getId());
    }
}
