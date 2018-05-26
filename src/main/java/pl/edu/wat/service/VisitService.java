package pl.edu.wat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.exception.NotFoundException;
import pl.edu.wat.model.User;
import pl.edu.wat.model.Visit;
import pl.edu.wat.repository.UserRepository;
import pl.edu.wat.repository.VisitRepository;
import pl.edu.wat.security.UserDetailsProvider;

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

        user.getVisits().add(visit);
        visit.setBusyVisit(true);
        userRepository.save(user);
        visitRepository.save(visit);

        return true;
    }
}
