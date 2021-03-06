package net.smartcosmos.platform.util;

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
import net.smartcosmos.platform.api.oauth.IOAuthTokenTransaction;

public final class OAuthTokenUtil
{
    private OAuthTokenUtil()
    {
    }

    public static long calculateBearerTokenExpirationTimestamp(IContext context,
                                                               IOAuthTokenTransaction transaction)
    {
        return (transaction.getTokenExchangeTimestamp() +
                context.getConfiguration().getOAuth2Factory().getMaxBearerTokenLifeMillis());
    }

    public static long calculateRefreshTokenExpirationTimestamp(IContext context,
                                                                IOAuthTokenTransaction transaction)
    {
        return (transaction.getTokenRefreshTimestamp() +
                context.getConfiguration().getOAuth2Factory().getMaxRefreshTokenLifeMillis());
    }

    public static boolean isBearerTokenExpired(IContext context, IOAuthTokenTransaction transaction)
    {
        return (System.currentTimeMillis() > calculateBearerTokenExpirationTimestamp(context, transaction));
    }

    public static boolean isRefreshTokenExpired(IContext context, IOAuthTokenTransaction transaction)
    {
        return (System.currentTimeMillis() > calculateRefreshTokenExpirationTimestamp(context, transaction));
    }
}
