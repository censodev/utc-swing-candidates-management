/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.utc.k59.it3.repositories;

import org.utc.k59.it3.models.Province;

/**
 *
 * @author JewCat
 */
public class ProvinceRepositoryImpl extends CrudRepositoryImpl<Province> implements ProvinceRepository {
    private static ProvinceRepositoryImpl instance;

    private ProvinceRepositoryImpl(Class<Province> clazz) {
        super(clazz);
    }

    public static ProvinceRepositoryImpl getInstance() {
        return instance == null ? new ProvinceRepositoryImpl(Province.class) : instance;
    }
}
