package net.smartcosmos.platform.api.visitor;

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

import net.smartcosmos.model.event.EventType;
import net.smartcosmos.objects.model.context.IObjectInteractionSession;

public class VisitableObjectInteractionSession extends AbstractVisitable<IObjectInteractionSession>
        implements IObjectInteractionSession
{
    public VisitableObjectInteractionSession(EventType eventType, IObjectInteractionSession instance)
    {
        super(eventType, instance);
    }

    @Override
    public long getStartTimestamp()
    {
        return instance.getStartTimestamp();
    }

    @Override
    public void setStartTimestamp(long timestamp)
    {
        throw new UnsupportedOperationException("operation is not supported in an IVisitable reference");
    }

    @Override
    public long getStopTimestamp()
    {
        return instance.getStopTimestamp();
    }

    @Override
    public void setStopTimestamp(long timestamp)
    {
        throw new UnsupportedOperationException("operation is not supported in an IVisitable reference");
    }

    @Override
    public String getName()
    {
        return instance.getName();
    }

    @Override
    public void setName(String name)
    {
        throw new UnsupportedOperationException("operation is not supported in an IVisitable reference");
    }

    @Override
    public String getDescription()
    {
        return instance.getDescription();
    }

    @Override
    public void setDescription(String description)
    {
        throw new UnsupportedOperationException("operation is not supported in an IVisitable reference");
    }

    @Override
    public boolean isActive()
    {
        return instance.isActive();
    }

    @Override
    public void setActive(boolean flag)
    {
        throw new UnsupportedOperationException("operation is not supported in an IVisitable reference");
    }

    @Override
    public String getType()
    {
        return instance.getType();
    }

    @Override
    public void setType(String type)
    {
        throw new UnsupportedOperationException("operation is not supported in an IVisitable reference");
    }
}
