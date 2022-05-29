package com.prepfortech.service;

import com.prepfortech.accessor.ShowAccessor;
import com.prepfortech.accessor.model.ShowDTO;
import com.prepfortech.controller.model.ShowOutput;
import com.prepfortech.mapper.ShowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ShowService {

    @Autowired
    private ShowAccessor showAccessor;

    @Autowired
    private ShowMapper showMapper;

    public List<ShowOutput> getShowsByName(final String showName) {
        List<ShowDTO> showDTOList = showAccessor.getShowsByName(showName);
        List<ShowOutput> showOutput = new ArrayList<>();

        for(ShowDTO showDTO: showDTOList) {
            showOutput.add(showMapper.mapShowDtoToOutput(showDTO));
        }

        return showOutput;
    }
}
