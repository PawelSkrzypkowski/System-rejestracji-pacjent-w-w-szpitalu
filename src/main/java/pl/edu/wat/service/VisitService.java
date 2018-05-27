package pl.edu.wat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.wat.exception.NotFoundException;
import pl.edu.wat.model.User;
import pl.edu.wat.model.Visit;
import pl.edu.wat.repository.UserRepository;
import pl.edu.wat.repository.VisitRepository;
import pl.edu.wat.security.UserDetailsProvider;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VisitService {

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private UserRepository userRepository;

    public Visit getVisit(Long id){
        return visitRepository.findById(id).orElseThrow(() -> new NotFoundException());
    }

    public boolean bookVisitSuccesfully(long id){
        Visit visit = visitRepository.findById(id).orElseThrow(() -> new NotFoundException());
        User user = userRepository.findByLogin(UserDetailsProvider.getCurrentUserUsername());

        if(user==null)
            return false;

        visit.setBusyVisit(true);
        user.getVisits().add(visit);
        userRepository.save(user);
        return true;
    }

    public void releaseVisit(long id){
        Visit visit = visitRepository.findById(id).orElseThrow(()->new NotFoundException());
        User user = userRepository.findByLogin(UserDetailsProvider.getCurrentUserUsername());
        user.getVisits().remove(visit);
        userRepository.save(user);
        visit.setBusyVisit(false);
        visitRepository.save(visit);
    }
}
