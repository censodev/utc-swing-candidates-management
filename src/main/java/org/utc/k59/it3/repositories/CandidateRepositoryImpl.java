/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.utc.k59.it3.repositories;

import org.hibernate.Session;
import org.utc.k59.it3.dto.CandidateDTO;
import org.utc.k59.it3.models.Candidate;
import org.utc.k59.it3.utils.HibernateUtil;

import javax.persistence.Query;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Override
    public List<CandidateDTO> findByProvince(Integer provinceId) {
        Session ss = null;

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT c.id, c.name, p.name, c.birthDate, c.gender, c.mathMark, c.physicsMark, c.chemistryMark, ");
        sb.append("c.mathMark + c.physicsMark + c.chemistryMark \n");
        sb.append("FROM Candidate c JOIN Province p ON c.provinceId = p.id \n");
        sb.append("WHERE p.id = :provinceId");
        System.out.println(sb.toString());
        try {
            ss = HibernateUtil.getSessionFactory().openSession();
            ss.beginTransaction();
            Query query = ss.createQuery(sb.toString());
            query.setParameter("provinceId", provinceId);
            List<Object[]> rs = query.getResultList();
            ss.getTransaction().commit();

            List<CandidateDTO> candidateDTOList = new ArrayList<>();

            for (Object[] rsObj : rs) {
                CandidateDTO candidateDTO = new CandidateDTO();
                candidateDTO.setId(Integer.valueOf(String.valueOf(rsObj[0])));
                candidateDTO.setName(String.valueOf(rsObj[1]));
                candidateDTO.setProvinceName(String.valueOf(rsObj[2]));
                String dateStr = String.valueOf(rsObj[3]);
                String dateString = Arrays.stream(dateStr.split("-"))
                        .reduce("", (subString, element) -> "/" + element + subString)
                        .substring(1);
                candidateDTO.setBirthDate(dateString);
                candidateDTO.setGender(String.valueOf(rsObj[4]));
                candidateDTO.setMathMark(Double.parseDouble(String.valueOf(rsObj[5])));
                candidateDTO.setPhysicsMark(Double.parseDouble(String.valueOf(rsObj[6])));
                candidateDTO.setChemistryMark(Double.parseDouble(String.valueOf(rsObj[7])));
                candidateDTO.setTotalMark(Double.parseDouble(String.valueOf(rsObj[8])));
                candidateDTOList.add(candidateDTO);
            }

            return candidateDTOList;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        } finally {
            assert ss != null;
            ss.close();
        }
    }
}
