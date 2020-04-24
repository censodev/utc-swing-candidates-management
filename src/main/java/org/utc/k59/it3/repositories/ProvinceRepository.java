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
public interface ProvinceRepository extends CrudRepository<Province> {
    Province findByName(String provinceName);
}
