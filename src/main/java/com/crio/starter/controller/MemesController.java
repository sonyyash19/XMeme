package com.crio.starter.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import java.util.List;
import javax.validation.Valid;
import com.crio.starter.data.MemeEntity;
import com.crio.starter.dto.MemeDto;
import com.crio.starter.exchange.MemeCreated;
import com.crio.starter.exchange.ResponseDto;
import com.crio.starter.service.MemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Log4j2
@RequestMapping("/")
public class MemesController {

    @Autowired
    private final MemeService memeService;

    private final String MEMEAPIENDPOINT = "memes";

    @PostMapping(MEMEAPIENDPOINT)
    public ResponseEntity<?> postMeme(@RequestBody @Valid MemeEntity memeEntity){

        log.info("Meme Request received with {}" + memeEntity);
        
        MemeCreated memeId = memeService.postMeme(memeEntity);
        // if id is null means that out of the all the fields present in the memesEntity
        // any field is getting repeated.
        if(memeId.getId() == null){
            ResponseDto responseDto = new ResponseDto("Post Already Exist");
            return ResponseEntity.status(409).body(responseDto);
        }

        log.info("Meme request created with {}" + memeId);

        return ResponseEntity.ok().body(memeId);
    }

    @GetMapping(MEMEAPIENDPOINT)
    public ResponseEntity<List<MemeDto>> getMemes(){
        List<MemeDto> response = memeService.getMemes();
        log.info("Total memes Returned is " + response.size());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(MEMEAPIENDPOINT + "/{memeId}")
    public ResponseEntity<?> getMeme(@PathVariable @Valid String memeId){
        log.info("Get the meme for id: " + memeId);
        MemeDto meme = memeService.getMeme(memeId);

        if(meme.getId() == null){
            ResponseDto responseDto = new ResponseDto("Post Not Found.");
            return ResponseEntity.status(404).body(responseDto);
        }
        
        // return ResponseEntity.ok().body(meme);
        return new ResponseEntity<>(meme, HttpStatus.CREATED);
    }
    
}
