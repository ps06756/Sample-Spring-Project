package com.prepfortech;

import com.prepfortech.accessor.models.ShowDTO;
import com.prepfortech.exception.DependencyFailureException;
import com.prepfortech.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShowController {

    @Autowired
    private ShowService showService;

    @GetMapping("/shows")
    public ResponseEntity findShowsByName(@RequestParam("name") String name) {
        try {
            List<ShowDTO> listOfShows = showService.findByName(name);
            return ResponseEntity.ok(listOfShows);
        }
        catch(DependencyFailureException ex) {
            System.out.println("Got a dependency failure exception!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getCause().getMessage());
        }
    }
}
