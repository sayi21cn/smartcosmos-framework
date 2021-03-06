/*
 * *#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*
 * SMART COSMOS Platform Server API
 * ===============================================================================
 * Copyright (C) 2013 - 2015 Smartrac Technology Fletcher, Inc.
 * ===============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#
 */
package net.smartcosmos.platform.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.hibernate.HibernateQuery;

import io.dropwizard.hibernate.AbstractDAO;
import net.smartcosmos.model.base.IDomainResource;
import net.smartcosmos.model.context.IAccount;
import net.smartcosmos.platform.api.dao.IAdvancedDAO;
import net.smartcosmos.platform.api.dao.domain.IPage;
import net.smartcosmos.platform.dao.domain.PageEntry;
import net.smartcosmos.util.UuidUtil;

public abstract class AbstractDAOImpl<S extends IDomainResource<S>, T extends S> extends AbstractDAO<T>implements
        IAdvancedDAO<S>
{
    private static final Logger LOG = LoggerFactory.getLogger(AbstractDAOImpl.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    protected final boolean canDelete;

    private final Class<T> entityClass;

    protected AbstractDAOImpl(final Class<T> classInstance, final SessionFactory sessionFactory)
    {
        this(classInstance, sessionFactory, false);
    }

    protected AbstractDAOImpl(final Class<T> classInstance, final SessionFactory sessionFactory,
            final Boolean canDelete)
    {
        super(sessionFactory);
        this.entityClass = classInstance;
        this.canDelete = canDelete;
    }

    @Override
    public Collection<S> advancedQuery(final Predicate... predicates)
    {
        Collection<S> list = new ArrayList<>();

        HibernateQuery<T> query = new HibernateQuery<>(currentSession());

        for (T o : query.from(getPath()).where(predicates).fetch())
        {
            list.add((S) o);
        }

        return list;
    }

    @Override
    public boolean canDelete()
    {
        return canDelete;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.smartcosmos.platform.dao.IPageProvider#count()
     */
    @Override
    public Long count()
    {
        final Criteria criteriaCount = criteria();
        criteriaCount.setProjection(Projections.rowCount());

        Object result = criteriaCount.uniqueResult();
        if (result == null)
        {
            return 0L;
        } else
        {
            return (Long) criteriaCount.uniqueResult();
        }
    }

    @Override
    public void delete(final S object)
    {
        if (!canDelete)
        {
            LOG.warn("Attempt to delete an immutable object detected");
            throw new UnsupportedOperationException("Type does not support deletion");
        } else
        {

            S instance = findByUrn(getEntityClass(), object.getUrn());

            if (null != instance)
            {
                currentSession().delete(instance);
            } else
            {
                LOG.warn("Unable to locate object of type {} with unique ID {}", getEntityClass().getName(),
                        object.getUrn());
            }
        }
    }

    @Override
    public void deleteJson(final String object) throws JsonParseException, JsonMappingException, IOException
    {
        delete(objectMapper.readValue(object, entityClass));
    }
    @Override
    @SuppressWarnings("unchecked")
    public Collection<S> findByAccount(final Class<?> clazz, final IAccount account)
    {
        Collection<S> list = new ArrayList<>();

        String entityName = clazz.getName();

        /*
         * NOTE: The risk of SQL injection here is virtually zero because of the Java Language Specification 3.8, which
         * restricts special characters like semicolon (;), dash (-), parentheses, etc. as part of a class identifier.
         * 
         * See http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.8
         */
        Query listQuery = currentSession().createQuery("select m from " + entityName + " m " +
                "where m.account.systemUuid = :accountSystemUuid")
                .setParameter("accountSystemUuid", account.getSystemUuid());

        for (Object o : listQuery.list())
        {
            list.add((S) o);
        }

        return list;
    }

    @Override
    public Collection<String> findByAccountJson(final IAccount account) throws JsonProcessingException
    {
        final Collection<String> list = new ArrayList<>();

        for (Object o : criteria().add(Restrictions.eq("account.systemUuid", account.getSystemUuid())).list())
        {
            list.add(objectMapper.writeValueAsString(o));
        }

        return list;
    }

    @Override
    @SuppressWarnings("unchecked")
    public S findByUrn(final Class<?> clazz, final String urn)
    {
        S object = null;
        try
        {
            if (urn == null)
            {
                return null;
            }

            object = null;

            String entityName = clazz.getName();

            /*
             * NOTE: The risk of SQL injection here is virtually zero because of the Java Language Specification 3.8,
             * which restricts special characters like semicolon (;), dash (-), parentheses, etc. as part of a class
             * identifier.
             *
             * See http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.8
             */
            Query query = currentSession().createQuery("select e from " + entityName + " e " +
                    "where e.systemUuid = :systemUuid")
                    .setParameter("systemUuid", UuidUtil.getUuidFromUrn(urn));

            object = (S) query.uniqueResult();
        } catch (IllegalArgumentException iae)
        {
            // no need to print stack trace; bad urn will be reported further up the chain
            // iae.printStackTrace();
            return null;
        }

        return object;
    }

    @Override
    @SuppressWarnings("unchecked")
    public S findByUrn(final Class<?> clazz, final String urn, final IAccount account)
    {
        Preconditions.checkNotNull(account, "Parameter 'account' must not be null");
        S object = null;

        String entityName = clazz.getName();

        /*
         * NOTE: The risk of SQL injection here is virtually zero because of the Java Language Specification 3.8, which
         * restricts special characters like semicolon (;), dash (-), parentheses, etc. as part of a class identifier.
         * 
         * See http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.8
         */
        try
        {
            Query query = currentSession()
                    .createQuery("select e from " + entityName +
                            " e where e.account.systemUuid = :accountSystemUuid and e.systemUuid = :systemUuid")
                    .setParameter("accountSystemUuid", account.getSystemUuid())
                    .setParameter("systemUuid", UuidUtil.getUuidFromUrn(urn));

            object = (S) query.uniqueResult();
        } catch (IllegalArgumentException iae)
        {
            // no need to print stack trace; bad urn will be reported further up the chain
            // iae.printStackTrace();
            return null;
        }
        return object;
    }

    @Override
    public String findByUrnJson(final String urn) throws JsonProcessingException
    {
        return objectMapper.writeValueAsString(findByUrn(entityClass, urn));
    }

    @Override
    public String findByUrnJson(final String urn, final IAccount account) throws JsonProcessingException
    {
        return objectMapper.writeValueAsString(findByUrn(entityClass, urn, account));
    }

    @SuppressWarnings("unchecked")
    public Collection<S> findByUuids(final Collection<UUID> uuids, final IAccount account)
    {
        final Collection<S> list = new ArrayList<>();

        if (uuids == null || uuids.size() == 0)
        {
            return list;
        }

        for (Object o : criteria().add(Restrictions.in("systemUuid", uuids))
                .add(Restrictions.eq("account.systemUuid", account.getSystemUuid())).list())
        {
            list.add((S) o);
        }

        return list;
    }

    @Override
    public Collection<String> findByUuidsJson(final Collection<UUID> uuids, final IAccount account)
            throws JsonProcessingException
    {
        final Collection<String> list = new ArrayList<>();

        if (uuids == null || uuids.size() == 0)
        {
            return list;
        }

        for (Object o : criteria().add(Restrictions.in("systemUuid", uuids))
                .add(Restrictions.eq("account.systemUuid", account.getSystemUuid())).list())
        {
            list.add(objectMapper.writeValueAsString(o));
        }

        return list;
    }

    /**
     * Returns the entity class managed by this DAO.
     *
     * @return the entity class managed by this DAO
     */
    @Override
    public Class<T> getEntityClass()
    {
        return (Class<T>) entityClass;
    }

    protected EntityPath<T> getPath()
    {
        throw new UnsupportedOperationException(getClass() + " does not support advanced queries yet.");
    }

    @Override
    public S insert(final S object)
    {
        if (null == object)
        {
            throw new IllegalArgumentException("Parameter must not be null");
        }

        T instance = null;
        try
        {
            instance = getEntityClass().newInstance();
            instance.copy(object);

            instance = persist(instance);

        } catch (InstantiationException e)
        {
            LOG.warn("Unable to instantiate object of type {}", getEntityClass().getName());
            LOG.debug(e.getMessage(), e);
        } catch (IllegalAccessException e)
        {
            LOG.debug(e.getMessage(), e);
        }

        return (S) instance;
    }

    @Override
    public String insertJson(final String object) throws IOException
    {
        return objectMapper.writeValueAsString(insert(objectMapper.readValue(object, entityClass)));
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.smartcosmos.platform.dao.IPageProvider#page(int, int)
     */
    @Override
    @SuppressWarnings("unchecked")
    public IPage<S> page(final int page, final int pageSize)
    {
        if (page < 1)
        {
            throw new IllegalArgumentException("Pages start at 1.");
        }

        if (pageSize < 1)
        {
            throw new IllegalArgumentException("Page must contain at least 1 entry.");
        }

        Collection<S> list = new ArrayList<S>();

        final int totalSize = count().intValue();
        final int totalPages = Double.valueOf(Math.ceil(Double.valueOf(totalSize) / Double.valueOf(pageSize)))
                .intValue();

        Criteria criteria = criteria();
        criteria.setFirstResult((page - 1) * pageSize);
        criteria.setMaxResults(pageSize);

        for (Object o : criteria.list())
        {
            list.add((S) o);
        }

        IPage<S> pagination = new PageEntry<S>(list, totalPages, totalSize, page, pageSize);

        return pagination;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<S> searchByMoniker(final Class<?> clazz, final String monikerEquals, final IAccount account)
    {
        Collection<S> list = new ArrayList<>();

        String entityName = clazz.getName();

        /*
         * NOTE: The risk of SQL injection here is virtually zero because of the Java Language Specification 3.8, which
         * restricts special characters like semicolon (;), dash (-), parentheses, etc. as part of a class identifier.
         * 
         * See http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.8
         */
        Query listQuery = currentSession()
                .createQuery("select m from " + entityName + " m " +
                        "where m.account.systemUuid = :accountSystemUuid " +
                        "and m.moniker = :moniker")
                .setParameter("accountSystemUuid", account.getSystemUuid())
                .setParameter("moniker", monikerEquals);

        for (Object o : listQuery.list())
        {
            list.add((S) o);
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<S> searchByMonikerLike(final Class<?> clazz, final String monikerLike, final IAccount account)
    {
        Collection<S> list = new ArrayList<>();

        String entityName = clazz.getName();

        /*
         * NOTE: The risk of SQL injection here is virtually zero because of the Java Language Specification 3.8, which
         * restricts special characters like semicolon (;), dash (-), parentheses, etc. as part of a class identifier.
         * 
         * See http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.8
         */
        Query listQuery = currentSession()
                .createQuery(
                        "select m from " + entityName + " m " +
                                "where m.account.systemUuid = :accountSystemUuid " +
                                "and m.moniker like :moniker")
                .setParameter("accountSystemUuid", account.getSystemUuid())
                .setParameter("moniker", monikerLike + "%");

        for (Object o : listQuery.list())
        {
            list.add((S) o);
        }

        return list;
    }

    @Override
    @SuppressWarnings("unchecked")
    public S update(final S object)
    {
        T instance = null;

        try
        {
            instance = getEntityClass().newInstance();
            instance.copy(object);

            instance = (T) currentSession().merge(instance);

        } catch (InstantiationException e)
        {
            LOG.warn("Unable to instantiate object of type {}", getEntityClass().getName());
            LOG.debug(e.getMessage(), e);
        } catch (IllegalAccessException e)
        {
            LOG.debug(e.getMessage(), e);
        }
        return (S) instance;
    }

    @Override
    public String updateJson(final String object)
            throws JsonParseException, JsonMappingException, JsonProcessingException, IOException
    {
        return objectMapper.writeValueAsString(update(objectMapper.readValue(object, entityClass)));
    }

    @Override
    public S upsert(final S object)
    {
        if (null == object)
        {
            throw new IllegalArgumentException("Parameter must not be null");
        }

        S findResult = null;

        if (null != object.getUrn())
        {
            findResult = findByUrn(getEntityClass(), object.getUrn());
        }

        if (null != findResult)
        {

            update(object);

            return object;
        } else
        {
            // INSERT
            return insert(object);
        }
    }

    @Override
    public String upsertJson(final String object) throws IOException
    {
        return objectMapper.writeValueAsString(upsert(objectMapper.readValue(object, entityClass)));
    }

}
