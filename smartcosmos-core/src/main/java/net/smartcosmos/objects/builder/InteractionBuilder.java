package net.smartcosmos.objects.builder;

/*
 * *#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*
 * SMART COSMOS Platform Core SDK
 * ===============================================================================
 * Copyright (C) 2013 - 2015 SMARTRAC Technology Fletcher, Inc.
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

import com.google.common.base.Preconditions;
import net.smartcosmos.builder.AbstractReferentialBuilder;
import net.smartcosmos.objects.model.context.IObject;
import net.smartcosmos.objects.model.context.IObjectInteraction;
import net.smartcosmos.objects.model.context.IObjectInteractionSession;
import net.smartcosmos.objects.pojo.context.ObjectInteraction;

/**
 * Convenience Builder pattern class for creating new
 * {@link net.smartcosmos.objects.model.context.IObjectInteraction} instances.
 * <p>
 * The minimum fields required to define a new instance are:
 * <ul>
 * <li>{@link net.smartcosmos.Field#TYPE_FIELD}</li>
 * </ul>
 */
public final class InteractionBuilder extends AbstractReferentialBuilder<IObjectInteraction, InteractionBuilder>
{
    public InteractionBuilder(long recordedTimestamp)
    {
        super(new ObjectInteraction());
        instance.setRecordedTimestamp(recordedTimestamp);
    }

    public InteractionBuilder setType(String type)
    {
        instance.setType(type);
        return this;
    }

    public InteractionBuilder setObjectInteractionSession(IObjectInteractionSession session)
    {
        instance.setObjectInteractionSession(session);
        return this;
    }

    public InteractionBuilder setObject(IObject interactedObject)
    {
        instance.setObject(interactedObject);
        return this;
    }

    @Override
    protected void onValidate()
    {
        Preconditions.checkNotNull(instance.getType(), "type must not be null");
        Preconditions.checkState(instance.getRecordedTimestamp() > 0, "recordedTimestamp must be set");
    }
}
