/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.utc.k59.it3.repositories;

import java.util.List;
import org.utc.k59.it3.models.Candidator;

/**
 *
 * @author JewCat
 */
public interface CandidatorRepository {
    List<Candidator> findAll();
    
    Candidator find(Integer id);
    
    void save(Candidator candidator);
    
    void save(List<Candidator> candidators);
    
    void update(Candidator candidator);
    
    void update(List<Candidator> candidators);
    
    Candidator delete(Integer id);
}
