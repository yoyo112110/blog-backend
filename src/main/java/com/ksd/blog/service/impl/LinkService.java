package com.ksd.blog.service.impl;

import com.ksd.blog.entity.Link;
import com.ksd.blog.repository.LinkRepository;
import com.ksd.blog.service.ILinkService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LinkService implements ILinkService {
    private final LinkRepository linkRepository;

    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Override
    public List<Link> list() {
        return linkRepository.findAll();
    }

    @Override
    public Link getById(String id) {
        return linkRepository.findById(id).orElse(null);
    }

    @Override
    public Link save(Link link) {
        if (link.getLinkAddTime() == null) {
            link.setLinkAddTime(LocalDateTime.now());
        }
        return linkRepository.save(link);
    }

    @Override
    public void deleteById(String id) {
        linkRepository.deleteById(id);
    }
}
