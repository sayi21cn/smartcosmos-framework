package net.smartcosmos.client.impl.command;

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

import com.google.common.base.Preconditions;
import net.smartcosmos.Field;
import net.smartcosmos.client.connectivity.ServerContext;
import net.smartcosmos.client.connectivity.ServiceException;
import net.smartcosmos.client.impl.base.AbstractBaseClient;
import net.smartcosmos.pojo.base.ResponseEntity;
import net.smartcosmos.pojo.base.Result;
import net.smartcosmos.util.json.JsonUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.Client;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static net.smartcosmos.Field.CODE_FIELD;
import static net.smartcosmos.Field.MESSAGE_FIELD;

public class PutCommand<T> extends AbstractBaseClient implements ICommand<T, T>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(PutCommand.class);

    public PutCommand(final ServerContext context, final Client client)
    {
        super(context, client);
    }

    @Override
    public T call(final Class<? extends T> clazz, final String path) throws ServiceException
    {
        throw new UnsupportedOperationException("PUT command must have inputJson");
    }

    @Override
    public T call(final Class<? extends T> clazz, final String path, final JSONObject inputJson) throws ServiceException
    {
        T response;

        Preconditions.checkNotNull(inputJson);

        ClientResource service = createClient(path);

        try
        {
            JSONObject jsonResult;
            try
            {
                Representation result = service.put(new JsonRepresentation(inputJson));
                JsonRepresentation jsonRepresentation = new JsonRepresentation(result);
                jsonResult = jsonRepresentation.getJsonObject();

                response = JsonUtil.fromJson(jsonResult, clazz);

            } catch (ResourceException e)
            {
                if (e.getStatus().equals(Status.CLIENT_ERROR_CONFLICT))
                {
                    ResponseEntity entity = new ResponseEntity.Builder(
                            Result.ERR_ALREADY_EXISTS.getCode(),
                            String.format(Result.ERR_ALREADY_EXISTS.getFormattedMessage(),
                                    "user",
                                    inputJson.getString(Field.EMAIL_ADDRESS_FIELD)))
                                            .build();

                    throw new ServiceException(entity);
                } else if (e.getStatus().equals(Status.CLIENT_ERROR_BAD_REQUEST))
                {
                    ResponseEntity entity = new ResponseEntity.Builder(
                            Result.ERR_FAILURE.getCode(),
                            String.format(Result.ERR_FAILURE.getFormattedMessage(),
                                    "Bad Request - if this was an interaction, was your session already closed?"))
                                            .build();

                    throw new ServiceException(entity);
                } else
                {
                    LOGGER.error("Unexpected Resource Exception", e);
                    throw new ServiceException(e);
                }
            }

            // This occurs when a put command is invoked and the object already exists.
            if (service.getStatus().equals(Status.SUCCESS_OK))
            {
                LOGGER.info("Object might have already existed, success but not a CREATED success.");

            }

            Status status = service.getStatus();
            if (status.equals(Status.SUCCESS_CREATED))
            {
                if (clazz.isAssignableFrom(ResponseEntity.class))
                {
                    LOGGER.debug(((ResponseEntity) response).getMessage());
                }
            } else
            {
                LOGGER.error("Unexpected HTTP status code returned: {}", service.getStatus().getCode());

                throwServiceException(jsonResult);
            }
        } catch (JSONException | IOException e)
        {
            LOGGER.error("Unexpected Exception", e);
            throw new ServiceException(e);
        }

        return response;
    }

    @Override
    public Collection<ResponseEntity> call(final String path, final JSONArray inputJson) throws ServiceException
    {
        Collection<ResponseEntity> response = new ArrayList<>();

        Preconditions.checkNotNull(inputJson);

        ClientResource service = createClient(path);

        try
        {
            Representation result = service.put(new JsonRepresentation(inputJson));
            JsonRepresentation jsonRepresentation = new JsonRepresentation(result);

            if (!service.getStatus().equals(Status.SUCCESS_OK))
            {
                LOGGER.error("Unexpected HTTP status code returned: {}", service.getStatus().getCode());

                JSONObject jsonResult = jsonRepresentation.getJsonObject();
                throwServiceException(jsonResult);
            } else
            {
                JSONArray jsonResult = jsonRepresentation.getJsonArray();

                for (int i = 0; i < jsonResult.length(); i++)
                {
                    JSONObject jsonObject = jsonResult.getJSONObject(i);

                    ResponseEntity responseEntity = new ResponseEntity();
                    responseEntity.setCode(jsonObject.getInt(CODE_FIELD));
                    responseEntity.setMessage(jsonObject.getString(MESSAGE_FIELD));

                    response.add(responseEntity);

                }
            }

        } catch (JSONException | IOException e)
        {
            LOGGER.error("Unexpected Exception", e);
            throw new ServiceException(e);
        }

        return response;
    }
}
