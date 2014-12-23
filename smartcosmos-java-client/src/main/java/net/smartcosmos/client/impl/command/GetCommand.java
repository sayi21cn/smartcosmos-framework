package net.smartcosmos.client.impl.command;

import net.smartcosmos.client.connectivity.ServerContext;
import net.smartcosmos.client.connectivity.ServiceException;
import net.smartcosmos.client.impl.base.AbstractBaseClient;
import net.smartcosmos.pojo.base.ResponseEntity;
import net.smartcosmos.util.json.JsonUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;

public class GetCommand<T> extends AbstractBaseClient implements ICommand<T, T>
{
    private final static Logger LOGGER = LoggerFactory.getLogger(GetCommand.class);

    public GetCommand(ServerContext context)
    {
        super(context);
    }

    @Override
    public T call(Class<? extends T> clazz, String path, JSONObject inputJson) throws ServiceException
    {
        throw new UnsupportedOperationException("GET command doesn't accept input JSON");
    }

    @Override
    public Collection<ResponseEntity> call(String path, JSONArray inputJson) throws ServiceException
    {
        throw new UnsupportedOperationException("GET command doesn't accept input as a JSONArray");
    }

    @Override
    public T call(Class<? extends T> clazz, String path) throws ServiceException
    {
        T instance = null;

        ClientResource service = createClient(path);

        try
        {
            Representation result = service.get();
            JsonRepresentation jsonRepresentation = new JsonRepresentation(result);

            if (service.getStatus().equals(Status.SUCCESS_NO_CONTENT))
            {
                LOGGER.info("No matching found {}", path);
            } else if (service.getStatus().equals(Status.SUCCESS_OK))
            {
                JSONObject jsonResult = jsonRepresentation.getJsonObject();
                instance = JsonUtil.fromJson(jsonResult, clazz);
            } else if (!service.getStatus().equals(Status.SUCCESS_OK))
            {
                JSONObject jsonResult = jsonRepresentation.getJsonObject();
                ResponseEntity responseEntity = JsonUtil.fromJson(jsonResult, ResponseEntity.class);

                LOGGER.error("Unexpected HTTP status code returned: {}", service.getStatus().getCode());
                throw new ServiceException(responseEntity);
            }

        } catch (JSONException | IOException e)
        {
            LOGGER.error("Unexpected Exception", e);
            throw new ServiceException(e);
        }

        return instance;
    }
}
