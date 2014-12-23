package net.smartcosmos.client.objects.timeline;

import net.smartcosmos.client.connectivity.ServerContext;
import net.smartcosmos.client.connectivity.ServiceException;
import net.smartcosmos.client.impl.base.AbstractUpdateableBaseClient;
import net.smartcosmos.client.impl.command.GetCollectionCommand;
import net.smartcosmos.client.impl.endpoint.TimelineEndpoints;
import net.smartcosmos.Field;
import net.smartcosmos.model.base.EntityReferenceType;
import net.smartcosmos.objects.model.context.ITimelineEntry;
import net.smartcosmos.objects.pojo.context.TimelineEntry;
import net.smartcosmos.pojo.base.ResponseEntity;
import net.smartcosmos.util.json.ViewType;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;

import static net.smartcosmos.Field.REFERENCE_URN_FIELD;


class TimelineClient extends AbstractUpdateableBaseClient<ITimelineEntry> implements ITimelineClient
{
    TimelineClient(ServerContext context)
    {
        super(context);
    }

    @Override
    public ITimelineEntry findByUrn(String urn, ViewType viewType) throws ServiceException
    {
        return findByUrn(urn, TimelineEndpoints.findByUrn(urn, viewType), TimelineEntry.class);
    }

    @Override
    public ResponseEntity create(JSONObject instance) throws ServiceException
    {
        try
        {
            EntityReferenceType ert = EntityReferenceType.valueOf(instance.getString(Field.ENTITY_REFERENCE_TYPE));
            String referenceUrn = instance.getString(REFERENCE_URN_FIELD);
            return create(instance, TimelineEndpoints.create(ert, referenceUrn));
        } catch (JSONException e)
        {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(JSONObject instance) throws ServiceException
    {
        update(instance, TimelineEndpoints.update());
    }

    @Override
    public Collection<ITimelineEntry> findByNameLike(String nameLike) throws ServiceException
    {
        return findByNameLike(nameLike, ViewType.Standard);
    }

    @Override
    public Collection<ITimelineEntry> findByNameLike(String nameLike, ViewType viewType) throws ServiceException
    {
        GetCollectionCommand<ITimelineEntry> command = new GetCollectionCommand<>(context);
        return command.call(TimelineEntry.class, TimelineEndpoints.findByNameLike(nameLike, viewType));
    }
}
