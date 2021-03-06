package net.smartcosmos.client.objects.file;

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
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Disposition;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.html.FormData;
import org.restlet.ext.html.FormDataSet;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.FileRepresentation;
import org.restlet.representation.InputRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import net.smartcosmos.client.connectivity.ServerContext;
import net.smartcosmos.client.connectivity.ServiceException;
import net.smartcosmos.client.impl.base.AbstractCreateableBaseClient;
import net.smartcosmos.client.impl.command.DeleteCommand;
import net.smartcosmos.client.impl.command.GetCollectionCommand;
import net.smartcosmos.client.impl.endpoint.FileEndpoints;
import net.smartcosmos.model.base.EntityReferenceType;
import net.smartcosmos.objects.model.context.IFile;
import net.smartcosmos.objects.pojo.context.File;
import net.smartcosmos.pojo.base.ResponseEntity;
import net.smartcosmos.pojo.base.Result;
import net.smartcosmos.util.json.JsonUtil;
import net.smartcosmos.util.json.ViewType;

import static net.smartcosmos.Field.URN_FIELD;

class FileClient extends AbstractCreateableBaseClient<IFile> implements IFileClient
{
    private static final Logger LOGGER = LoggerFactory.getLogger(FileClient.class);

    FileClient(ServerContext context)
    {
        super(context);
    }

    @Override
    public IFile findByUrn(String urn, ViewType viewType) throws ServiceException
    {
        return findByUrn(urn, FileEndpoints.findByUrn(urn, viewType), File.class);
    }

    @Override
    public ResponseEntity create(JSONObject instance) throws ServiceException
    {
        return create(instance, FileEndpoints.create());
    }

    @Override
    public void delete(JSONObject instance) throws ServiceException
    {
        Preconditions.checkState(instance.has(URN_FIELD));

        try
        {
            DeleteCommand command = new DeleteCommand(context, getClient());
            command.call(Object.class, FileEndpoints.delete(instance.getString(URN_FIELD)));
        } catch (JSONException e)
        {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(IFile instance) throws ServiceException
    {
        try
        {
            JSONObject json = new JSONObject(JsonUtil.toJson(instance, ViewType.Full));
            delete(json);
        } catch (JSONException e)
        {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<IFile> listOwnedBy(EntityReferenceType entityReferenceType,
                                         String referenceUrn,
                                         ViewType viewType) throws ServiceException
    {
        GetCollectionCommand<IFile> command = new GetCollectionCommand<>(context, getClient());
        return command.call(File.class,
                FileEndpoints.listFilesOwnedByEntity(entityReferenceType, referenceUrn, viewType));
    }

    @Override
    public ResponseEntity uploadOctetStream(String urn, java.io.File file, MediaType mediaType) throws ServiceException
    {
        ResponseEntity response;

        FileRepresentation fileRepresentation = new FileRepresentation(file, mediaType);
        fileRepresentation.setMediaType(MediaType.APPLICATION_OCTET_STREAM);

        ClientResource service = createClient(FileEndpoints.uploadContentsAsOctetStream(urn));
        service.accept(MediaType.APPLICATION_JSON);

        try
        {
            Representation result = service.post(fileRepresentation);
            JsonRepresentation jsonRepresentation = new JsonRepresentation(result);
            JSONObject jsonResult = jsonRepresentation.getJsonObject();
            response = JsonUtil.fromJson(jsonResult, ResponseEntity.class);

            if (service.getStatus().equals(Status.SUCCESS_OK))
            {
                LOGGER.info("Successfully uploaded file to path {}", FileEndpoints.uploadContentsAsMultipart(urn));
            } else
            {
                LOGGER.error("Unexpected HTTP status code returned: {}", service.getStatus().getCode());
                throw new ServiceException(response);
            }

        } catch (JSONException | IOException e)
        {
            LOGGER.error("Unexpected Exception", e);
            throw new ServiceException(e);
        }

        return response;
    }

    @Override
    public ResponseEntity uploadOctetStream(String urn, InputStream inputStream, MediaType mediaType)
            throws ServiceException
    {
        ResponseEntity response;

        InputRepresentation inputRepresentation = new InputRepresentation(inputStream, mediaType);
        inputRepresentation.setMediaType(MediaType.APPLICATION_OCTET_STREAM);

        ClientResource service = createClient(FileEndpoints.uploadContentsAsOctetStream(urn));
        service.accept(MediaType.APPLICATION_JSON);

        try
        {
            Representation result = service.post(inputRepresentation);
            JsonRepresentation jsonRepresentation = new JsonRepresentation(result);
            JSONObject jsonResult = jsonRepresentation.getJsonObject();
            response = JsonUtil.fromJson(jsonResult, ResponseEntity.class);

            if (service.getStatus().equals(Status.SUCCESS_OK))
            {
                LOGGER.info("Successfully uploaded input stream to path {}",
                        FileEndpoints.uploadContentsAsMultipart(urn));
            } else
            {
                LOGGER.error("Unexpected HTTP status code returned: {}", service.getStatus().getCode());
                throw new ServiceException(response);
            }

        } catch (JSONException | IOException e)
        {
            LOGGER.error("Unexpected Exception", e);
            throw new ServiceException(e);
        }

        return response;
    }

    @Override
    public ResponseEntity uploadAsMultiPartFormData(String urn, java.io.File file, MediaType mediaType)
            throws ServiceException
    {
        ResponseEntity response;

        FileRepresentation fileRepresentation = new FileRepresentation(file, mediaType);

        ClientResource service = createClient(FileEndpoints.uploadContentsAsMultipart(urn));
        service.accept(MediaType.APPLICATION_JSON);

        FormDataSet form = new FormDataSet();
        form.setMultipart(true);
        form.getEntries().add(new FormData("file", fileRepresentation));
        form.add(Disposition.NAME_FILENAME, file.getName());

        Disposition disposition = new Disposition(Disposition.TYPE_INLINE);
        fileRepresentation.setDisposition(disposition);

        try
        {
            Representation result = service.post(form);
            JsonRepresentation jsonRepresentation = new JsonRepresentation(result);
            JSONObject jsonResult = jsonRepresentation.getJsonObject();
            response = JsonUtil.fromJson(jsonResult, ResponseEntity.class);

            if (service.getStatus().equals(Status.SUCCESS_OK))
            {
                LOGGER.info("Successfully uploaded multipart-form data to path {}",
                        FileEndpoints.uploadContentsAsMultipart(urn));
            } else
            {
                LOGGER.error("Unexpected HTTP status code returned: {}", service.getStatus().getCode());
                throw new ServiceException(response);
            }

        } catch (JSONException | IOException e)
        {
            LOGGER.error("Unexpected Exception", e);
            throw new ServiceException(e);
        }

        return response;
    }

    @Override
    public InputStream getFileContents(String urn) throws ServiceException, IOException
    {
        ClientResource service = createClient(FileEndpoints.retrieveContents(urn));
        Representation representation = service.get();

        if (service.getStatus().equals(Status.CLIENT_ERROR_BAD_REQUEST))
        {
            // No such URN
            ResponseEntity responseEntity = null;
            try
            {
                JsonRepresentation jsonRepresentation = new JsonRepresentation(representation);
                JSONObject jsonResult = jsonRepresentation.getJsonObject();
                responseEntity = JsonUtil.fromJson(jsonResult, ResponseEntity.class);
            } catch (JSONException e)
            {
                e.printStackTrace();
            }

            throw new ServiceException(responseEntity);

        } else if (service.getStatus().equals(Status.SUCCESS_NO_CONTENT))
        {
            // URN found but no file has been uploaded as of yet
            ResponseEntity responseEntity = new ResponseEntity.Builder(Result.ERR_NO_FILE_CONTENT.getCode(),
                    String.format(Result.ERR_NO_FILE_CONTENT.getFormattedMessage(), urn))
                    .build();
            throw new ServiceException(responseEntity);
        } else
        {
            return representation.getStream();
        }
    }

    @Override
    public Collection<IFile> listOwnedBy(EntityReferenceType entityReferenceType, String referenceUrn)
            throws ServiceException
    {
        return listOwnedBy(entityReferenceType, referenceUrn, ViewType.Standard);
    }
}
