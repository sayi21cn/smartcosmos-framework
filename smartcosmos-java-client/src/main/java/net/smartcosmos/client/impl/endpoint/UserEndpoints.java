package net.smartcosmos.client.impl.endpoint;

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

import net.smartcosmos.util.json.ViewType;

public final class UserEndpoints
{
    private UserEndpoints()
    {
    }

    private static final String BASE = "/users";

    private static final String CREATE_PUT = BASE;

    private static final String FIND_BY_URN_GET = BASE.concat("/%s?view=%s");

    private static final String FIND_BY_EMAIL_GET = BASE.concat("/user/%s?view=%s");

    private static final String MANAGE_PASSWORD_POST = BASE.concat("/user");

    private static final String UPDATE_POST = BASE;

    public static String create()
    {
        return CREATE_PUT;
    }

    public static String update()
    {
        return UPDATE_POST;
    }

    public static String managePassword()
    {
        return MANAGE_PASSWORD_POST;
    }

    public static String findByUrn(String urn)
    {
        return findByUrn(urn, ViewType.Standard);
    }

    public static String findByUrn(String urn, ViewType viewType)
    {
        return String.format(FIND_BY_URN_GET, urn, viewType);
    }

    public static String findByEmailAddress(String emailAddress)
    {
        return findByEmailAddress(emailAddress, ViewType.Standard);
    }

    public static String findByEmailAddress(String emailAddress, ViewType viewType)
    {
        return String.format(FIND_BY_EMAIL_GET, emailAddress, viewType);
    }
}
