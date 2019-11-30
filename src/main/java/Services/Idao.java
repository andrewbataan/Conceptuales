/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import java.util.List;

/**
 *
 * @author User
 */
public interface Idao {

    public interface IDao<T> {

        public void insert(T data);

        public void delete(T data);

        public void update(T data);

    }
}
