/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.utc.k59.it3.repositories;

import org.utc.k59.it3.dto.CandidateDTO;
import org.utc.k59.it3.models.Candidate;

import java.util.List;

/**
 *
 * @author JewCat
 */
public interface CandidateRepository extends CrudRepository<Candidate> {
    List<CandidateDTO> findByProvince(String provinceName);
    List<CandidateDTO> getListCandidates();
    CandidateDTO getCandidate(Integer id);
}
