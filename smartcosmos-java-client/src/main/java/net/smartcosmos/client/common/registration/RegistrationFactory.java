package net.smartcosmos.client.common.registration;

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

import net.smartcosmos.client.connectivity.ServerContext;

/**
 * Registration Client Factory.
 */
public final class RegistrationFactory
{
    private RegistrationFactory()
    {
    }

    /**
     * Creates a new instance of a registration client.
     *
     * @param server
     *            Server location, e.g. https://objects.example.com
     * @return New registration client instance
     */
    public static IRegistrationClient createClient(String server)
    {
        return new RegistrationClient(server);
    }

    /**
     * Creates a new instance of a registration client pointing at a specific server instance.
     *
     * @param context
     *            Context to use for client.
     * @return New registration client instance
     */
    public static IRegistrationClient createClient(ServerContext context)
    {
        return new RegistrationClient(context);
    }
}
