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

public class DeleteCommand extends AbstractBaseClient implements ICommand<Object, Object>
{
    private final static Logger LOGGER = LoggerFactory.getLogger(DeleteCommand.class);

    public DeleteCommand(ServerContext context)
    {
        super(context);
    }

    @Override
    public Object call(Class<?> clazz, String path) throws ServiceException
    {
        ClientResource service = createClient(path);

        try
        {
            Representation result = service.delete();

            if (service.getStatus().equals(Status.SUCCESS_NO_CONTENT))
            {
                LOGGER.info("Successfully deleted {}", path);
            } else
            {
                JsonRepresentation jsonRepresentation = new JsonRepresentation(result);
                JSONObject jsonResult = jsonRepresentation.getJsonObject();

                LOGGER.error("Unexpected HTTP status code returned: {}", service.getStatus().getCode());
                ResponseEntity response = JsonUtil.fromJson(jsonResult, ResponseEntity.class);
                throw new ServiceException(response);
            }

        } catch (JSONException | IOException e)
        {
            LOGGER.error("Unexpected Exception", e);
            throw new ServiceException(e);
        }

        return null;
    }

    @Override
    public Object call(Class<?> clazz, String path, JSONObject inputJson) throws ServiceException
    {
        throw new UnsupportedOperationException("DELETE command must not have inputJson");
    }

    @Override
    public Collection<ResponseEntity> call(String path, JSONArray inputJson) throws ServiceException
    {
        throw new UnsupportedOperationException("DELETE command doesn't accept input as a JSONArray");
    }
}

