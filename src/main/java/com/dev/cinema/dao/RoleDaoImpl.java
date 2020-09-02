package com.dev.cinema.dao;

import com.dev.cinema.exception.DataProcessingException;
import com.dev.cinema.model.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl extends GenericDaoImpl<Role> implements RoleDao {
    private final SessionFactory factory;

    public RoleDaoImpl(SessionFactory factory) {
        super(factory);
        this.factory = factory;
    }

    @Override
    public Role add(Role role) {
        role = super.add(role);
        return role;
    }

    @Override
    public Role getById(Long id) {
        return super.getById(id, Role.class);
    }

    @Override
    public Role getRoleByName(String roleName) {
        try (Session session = factory.openSession()) {
            Query<Role> query = session
                    .createQuery("from Role r "
                            + " where r.roleName =: roleName", Role.class);
            query.setParameter("roleName", Role.RoleName.valueOf(roleName));
            return query.uniqueResult();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find role with name "
                    + roleName, e);
        }
    }
}
