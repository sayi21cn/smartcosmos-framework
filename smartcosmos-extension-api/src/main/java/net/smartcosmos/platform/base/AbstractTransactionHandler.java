package net.smartcosmos.platform.base;

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

import net.smartcosmos.platform.api.IContext;
import net.smartcosmos.platform.api.transaction.ITransactionHandler;

public abstract class AbstractTransactionHandler implements ITransactionHandler
{
    protected String serviceId;

    protected String name;

    protected IContext context;

    protected AbstractTransactionHandler(String serviceId, String name)
    {
        this.serviceId = serviceId;
        this.name = name;
    }

    /**
     *
     * ClassUtil.create(Class<T> clazz, String className) needs default constructor for
     * TransacationHandler implementations.
     *
     * @see net.smartcosmos.platform.util.ClassUtil#create(Class<T> clazz, String className)
     */
    public AbstractTransactionHandler()
    {

    }

    @Override
    public String getServiceId()
    {
        return serviceId;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public void setContext(IContext context)
    {
        this.context = context;
    }

    /**
     * Empty implementation.
     */
    @Override
    public void initialize()
    {

    }
}
