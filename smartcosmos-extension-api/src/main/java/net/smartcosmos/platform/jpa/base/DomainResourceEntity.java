package net.smartcosmos.platform.jpa.base;

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

import com.fasterxml.jackson.annotation.JsonView;
import net.smartcosmos.Field;
import net.smartcosmos.model.base.IDomainResource;
import net.smartcosmos.platform.jpa.integrator.IPostLoadHandler;
import net.smartcosmos.platform.jpa.integrator.IPrePersistHandler;
import net.smartcosmos.platform.jpa.integrator.IPreUpdateHandler;
import net.smartcosmos.util.json.JsonGenerationView;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
public abstract class DomainResourceEntity<T extends IDomainResource>
        implements IDomainResource<T>, IPrePersistHandler, IPreUpdateHandler, IPostLoadHandler
{
    @JsonView(JsonGenerationView.Restricted.class)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long uniqueId;

    @JsonView(JsonGenerationView.Minimum.class)
    @Column(length = 767, nullable = false, updatable = false)
    protected String urn;

    @JsonView(JsonGenerationView.Standard.class)
    @Basic
    protected long lastModifiedTimestamp;

    @JsonView(JsonGenerationView.Full.class)
    @Column(length = 2048, nullable = true, updatable = true)
    protected String moniker;

    @Override
    public long getUniqueId()
    {
        return uniqueId;
    }

    @Override
    public void setUniqueId(long uniqueId)
    {
        this.uniqueId = uniqueId;
    }

    @Override
    public String getUrn()
    {
        return urn;
    }

    @Override
    public void setUrn(String urn)
    {
        this.urn = urn;
    }

    @Override
    public void copy(T target)
    {
        this.urn = target.getUrn();
        this.uniqueId = target.getUniqueId();
        this.lastModifiedTimestamp = target.getLastModifiedTimestamp();
        this.moniker = target.getMoniker();
    }

    @Override
    public void onPostLoad()
    {

    }

    @Override
    public void onPrePersist()
    {
        lastModifiedTimestamp = System.currentTimeMillis();
        setUrn("urn:uuid:" + UUID.randomUUID().toString());

        if (null != moniker && moniker.equals(Field.NULL_MONIKER))
        {
            moniker = null;
        }
    }

    @Override
    public void onPreUpdate()
    {
        lastModifiedTimestamp = System.currentTimeMillis();

        if (null != moniker && moniker.equals(Field.NULL_MONIKER))
        {
            moniker = null;
        }
    }

    @Override
    public long getLastModifiedTimestamp()
    {
        return lastModifiedTimestamp;
    }

    @Override
    public String getMoniker()
    {
        return moniker;
    }

    @Override
    public void setMoniker(String moniker)
    {
        this.moniker = moniker;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DomainResourceEntity that = (DomainResourceEntity) o;

        if (uniqueId != that.uniqueId) return false;
        if (!urn.equals(that.urn)) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = (int) (uniqueId ^ (uniqueId >>> 32));
        result = 31 * result + urn.hashCode();
        return result;
    }
}
