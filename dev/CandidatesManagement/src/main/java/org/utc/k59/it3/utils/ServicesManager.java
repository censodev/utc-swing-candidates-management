package org.utc.k59.it3.utils;

import org.utc.k59.it3.repositories.CandidateRepository;
import org.utc.k59.it3.repositories.CandidateRepositoryImpl;
import org.utc.k59.it3.repositories.ProvinceRepository;
import org.utc.k59.it3.repositories.ProvinceRepositoryImpl;

public class ServicesManager {
    /*
    Repositories register
     */
    public static ProvinceRepository provinceRepository = ProvinceRepositoryImpl.getInstance();
    public static CandidateRepository candidateRepository = CandidateRepositoryImpl.getInstance();

    /*
    Services register
     */

}
