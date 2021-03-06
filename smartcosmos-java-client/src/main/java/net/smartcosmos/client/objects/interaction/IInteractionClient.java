package net.smartcosmos.client.objects.interaction;

/*
 * *#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*
 * SMART COSMOS Platform Client
 * ===============================================================================
 * Copyright (C) 2013 - 2014 SMARTRAC Technology Fletcher, Inc.
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

import java.util.Collection;

import net.smartcosmos.client.connectivity.ServiceException;
import net.smartcosmos.client.impl.ICreateableBaseClient;
import net.smartcosmos.objects.model.context.IObjectInteraction;
import net.smartcosmos.util.json.ViewType;

/**
 * Defines or queries for {@link net.smartcosmos.objects.model.context.IObjectInteraction} instances.
 */
public interface IInteractionClient extends ICreateableBaseClient<IObjectInteraction>
{
    /**
     * Lists all known object interactions under the authenticated account using a
     * {@link net.smartcosmos.util.json.ViewType#Standard} view
     * <p>
     * <b>NOTE:</b> Use caution on this method as it may return a large amount of data!
     *
     * @return Non-null (but possibly empty) collection of object interaction definitions
     * @throws ServiceException to indicate the interaction has no content or the URN specified cannot be located
     */
    Collection<IObjectInteraction> listAll() throws ServiceException;

    /**
     * Lists all known object interactions under the authenticated account using the specified field verbosity.
     * <p>
     * <b>NOTE:</b> Use caution on this method as it may return a large amount of data!
     *
     * @param viewType Field verbosity
     * @return Non-null (but possibly empty) collection of object interaction definitions
     * @throws ServiceException to indicate the interaction has no content or the URN specified cannot be located
     */
    Collection<IObjectInteraction> listAll(ViewType viewType) throws ServiceException;

    /**
     * Lists all known object interactions under the authenticated account with an
     * {@link net.smartcosmos.objects.model.context.IObject#getObjectUrn()} that exactly
     * matches to the specified objectUrn parameter using a
     * {@link net.smartcosmos.util.json.ViewType#Standard} view.
     *
     * @param objectUrn Object URN
     * @return Non-null (but possibly empty) collection of object interaction definitions
     * @throws ServiceException to indicate the interaction has no content or the URN specified cannot be located
     */
    Collection<IObjectInteraction> findByObjectUrn(String objectUrn) throws ServiceException;

    /**
     * Lists all known object interactions under the authenticated account with an
     * {@link net.smartcosmos.objects.model.context.IObject#getObjectUrn()} that exactly
     * matches to the specified objectUrn parameter using the specified field verbosity.
     *
     * @param objectUrn Object URN
     * @param viewType      Field verbosity
     * @return Non-null (but possibly empty) collection of object interaction definitions
     * @throws ServiceException to indicate the interaction has no content or the URN specified cannot be located
     */
    Collection<IObjectInteraction> findByObjectUrn(String objectUrn, ViewType viewType) throws ServiceException;
}
