package com.example.securingweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class MovieTransactions {
    final private MovieRepository movierepo;

    @Autowired
    public MovieTransactions(MovieRepository movierepo) {
        this.movierepo = movierepo;
    }

    public void addRating(Long movieid, double rating){
        Object ob = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User primaryUser = (User)ob;

        // check if the update exists or not
        boolean existsOrNot = movierepo.checkIfRatingExists(movieid,primaryUser.getUsername());
        System.out.println(existsOrNot);
        if (existsOrNot){
            movierepo.updateFeedbackRelationship(primaryUser.getUsername(),movieid, rating);
        }
        else{
            movierepo.addFeedbackRelationship(primaryUser.getUsername(),movieid, rating);
        }
    }


}
