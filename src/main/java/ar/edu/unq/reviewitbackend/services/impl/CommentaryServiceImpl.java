package ar.edu.unq.reviewitbackend.services.impl;

import org.springframework.stereotype.Service;

import ar.edu.unq.reviewitbackend.entities.Commentary;
import ar.edu.unq.reviewitbackend.repositories.CommentaryRepository;
import ar.edu.unq.reviewitbackend.services.CommentaryService;

@Service
public class CommentaryServiceImpl extends CommonServiceImpl<Commentary, CommentaryRepository> implements CommentaryService{

}
