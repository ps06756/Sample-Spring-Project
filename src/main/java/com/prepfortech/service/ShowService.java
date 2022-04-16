package com.prepfortech.service;

import com.prepfortech.accessor.ShowAccessor;
import com.prepfortech.accessor.models.ShowDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShowService {

    @Autowired
    private ShowAccessor showAccessor;

    public List<ShowDTO> findByName(String name) {
        return showAccessor.findByName(name);
    }
}
