/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.utc.k59.it3.repositories;

import org.utc.k59.it3.models.Candidate;

/**
 *
 * @author JewCat
 */
public class CandidateRepositoryImpl extends CrudRepositoryImpl<Candidate> implements CandidateRepository {
    private static CandidateRepositoryImpl instance;

    private CandidateRepositoryImpl(Class<Candidate> clazz) {
        super(clazz);
    }

    public static CandidateRepositoryImpl getInstance() {
        return instance == null ? new CandidateRepositoryImpl(Candidate.class) : instance;
    }
}
