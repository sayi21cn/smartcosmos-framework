package net.smartcosmos.platform.api.dao;

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

import net.smartcosmos.model.extension.IExternalExtension;
import net.smartcosmos.platform.api.oauth.IOAuthTokenTransaction;

import java.util.Collection;

public interface IOAuthTokenTransactionDAO extends IBaseDAO<IOAuthTokenTransaction>
{
    IOAuthTokenTransaction findByAuthorizationCode(String authorizationCode);

    IOAuthTokenTransaction findByBearerToken(String token);

    IOAuthTokenTransaction findByRefreshToken(String refreshToken);

    IOAuthTokenTransaction findByCsrf(String csrf);

    /**
     * Invoked when an IExtension is deleted.
     *
     * @param extension
     * @return
     */
    Collection<IOAuthTokenTransaction> revokeActiveTokens(IExternalExtension extension);
}


