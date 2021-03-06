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
import net.smartcosmos.platform.api.oauth.IExternalOAuthTokenManager;
import net.smartcosmos.platform.api.oauth.IOAuthTokenRegistry;

public abstract class AbstractOAuthTokenManager implements IExternalOAuthTokenManager
{
    private final String name;

    protected IOAuthTokenRegistry registry;

    protected IContext context;

    protected AbstractOAuthTokenManager(String name)
    {
        this.name = name;
    }

    @Override
    public void setContext(IContext context)
    {
        this.context = context;
    }

    @Override
    public void setOAuthTokenRegistry(IOAuthTokenRegistry registry)
    {
        this.registry = registry;
    }

    @Override
    public String getName()
    {
        return name;
    }
}
