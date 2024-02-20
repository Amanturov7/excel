package com.example.excel.service;

import com.example.excel.model.Channels;
import com.example.excel.model.Hydropost;
import com.example.excel.repository.ChannelsRepository;
import com.example.excel.repository.HydropostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelsService {
    private final ChannelsRepository repository;

    @Autowired
    public ChannelsService(ChannelsRepository repository) {
        this.repository = repository;
    }

    public void saveChannels(List<Channels> channels) {
        repository.saveAll(channels);
    }
}
