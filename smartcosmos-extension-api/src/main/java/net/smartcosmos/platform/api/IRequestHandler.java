package net.smartcosmos.platform.api;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import io.dropwizard.views.View;
import net.smartcosmos.platform.api.authentication.IAuthenticatedUser;
import net.smartcosmos.util.json.ViewType;
import org.json.JSONException;

import javax.ws.rs.core.Response;

/**
 * Stateless implementation capable of processing a specific HTTP method type within a resource.
 *
 * @param <T>
 */
public interface IRequestHandler<T>
{
    /**
     * Statistical counter that indicates how many times this handler has been invoked.
     *
     * @return Execution count
     */
    long count();

    boolean forceAuthentication();

    long increment();

    boolean isAuthorized(IAuthenticatedUser authenticatedUser);

    View render(T inputValue, IAuthenticatedUser authenticatedUser) throws JsonProcessingException, JSONException;

    Response handle(T inputValue, IAuthenticatedUser authenticatedUser) throws JsonProcessingException, JSONException;

    Response handle(T inputValue, ViewType view, IAuthenticatedUser authenticatedUser)
            throws JsonProcessingException, JSONException;
}
