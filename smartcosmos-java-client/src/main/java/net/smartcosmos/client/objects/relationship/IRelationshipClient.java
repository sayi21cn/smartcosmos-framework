package net.smartcosmos.client.objects.relationship;

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

import net.smartcosmos.client.connectivity.ServiceException;
import net.smartcosmos.client.impl.IDeleteableBaseClient;
import net.smartcosmos.client.impl.IUpsertableBaseClient;
import net.smartcosmos.model.base.EntityReferenceType;
import net.smartcosmos.objects.model.context.IRelationship;
import net.smartcosmos.util.json.ViewType;

import java.util.Collection;

/**
 * <p>Defines, deletes, or queries for {@link net.smartcosmos.objects.model.context.IRelationship} instances.</p>
 * <p>A relationship is a <b>binary concept</b> that either exists or doesn't. For example, a specific Driver object cannot
 * "LIKE" a specific Car object multiple times. The driver "LIKE"s the Car, or does not "LIKE" the car. As documented
 * by {@link net.smartcosmos.client.impl.IUpsertableBaseClient}, relationship creation is idempotent; no matter how
 * many times the relationship is defined, it is guaranteed to only exist once in the database.</p>
 */
public interface IRelationshipClient extends IUpsertableBaseClient<IRelationship>, IDeleteableBaseClient<IRelationship>
{
    /**
     * <p>Retrieves <b>all</b> of the documented relationships between two specific entities.</p>
     * <p>Each {@link net.smartcosmos.objects.model.context.IRelationship} will be serialized using a
     * {@link net.smartcosmos.util.json.ViewType#Standard} view.</p>
     *
     * @param entityReferenceType        owner/parent entity reference type
     * @param referenceUrn               owner/parent reference URN
     * @param relatedEntityReferenceType child entity reference type
     * @param relatedReferenceUrn        child entity reference URN
     * @return Non-null collection of matching relationships; collection may have a size of 0 to indicate no matches
     * @throws ServiceException on error
     */
    Collection<IRelationship> findAllBetweenTwoEntities(EntityReferenceType entityReferenceType,
                                                        String referenceUrn,
                                                        EntityReferenceType relatedEntityReferenceType,
                                                        String relatedReferenceUrn) throws ServiceException;

    /**
     * <p>Retrieves <b>all</b> of the documented relationships between two specific entities.</p>
     * <p>Each {@link net.smartcosmos.objects.model.context.IRelationship} will be serialized using the specified field
     * verbosity.</p>
     *
     * @param entityReferenceType        owner/parent entity reference type
     * @param referenceUrn               owner/parent reference URN
     * @param relatedEntityReferenceType child entity reference type
     * @param relatedReferenceUrn        child entity reference URN
     * @param viewType                   Field verbosity
     * @return Non-null collection of matching relationships; collection may have a size of 0 to indicate no matches
     * @throws ServiceException on error
     */
    Collection<IRelationship> findAllBetweenTwoEntities(EntityReferenceType entityReferenceType,
                                                        String referenceUrn,
                                                        EntityReferenceType relatedEntityReferenceType,
                                                        String relatedReferenceUrn,
                                                        ViewType viewType) throws ServiceException;

    /**
     * <p>Retrieves <b>a very specific</b> relationship between two specific entities, if it exists.</p>
     * <p>Each {@link net.smartcosmos.objects.model.context.IRelationship} will be serialized using a
     * {@link net.smartcosmos.util.json.ViewType#Standard} view.</p>
     *
     * @param entityReferenceType        owner/parent entity reference type
     * @param referenceUrn               owner/parent reference URN
     * @param relatedEntityReferenceType child entity reference type
     * @param relatedReferenceUrn        child entity reference URN
     * @param relationshipType           relationship type
     * @return Non-null collection of matching relationships; collection may have a size of 0 to indicate no matches
     * @throws ServiceException on error
     */
    IRelationship findSpecificRelationship(EntityReferenceType entityReferenceType,
                                           String referenceUrn,
                                           EntityReferenceType relatedEntityReferenceType,
                                           String relatedReferenceUrn,
                                           String relationshipType) throws ServiceException;

    /**
     * <p>Retrieves <b>a very specific</b> relationship between two specific entities, if it exists.</p>
     * <p>The {@link net.smartcosmos.objects.model.context.IRelationship} will be serialized using the specified field
     * verbosity.</p>
     *
     * @param entityReferenceType        owner/parent entity reference type
     * @param referenceUrn               owner/parent reference URN
     * @param relatedEntityReferenceType child entity reference type
     * @param relatedReferenceUrn        child entity reference URN
     * @param relationshipType           case-sensitive name of the relationship
     * @param viewType                   Field verbosity
     * @return Non-null collection of matching relationships; collection may have a size of 0 to indicate no matches
     * @throws ServiceException on error
     */
    IRelationship findSpecificRelationship(EntityReferenceType entityReferenceType,
                                           String referenceUrn,
                                           EntityReferenceType relatedEntityReferenceType,
                                           String relatedReferenceUrn,
                                           String relationshipType,
                                           ViewType viewType) throws ServiceException;

    /**
     * <p>Retrieves <b>all</b> child entities that the specified entity has the specified relationship type with.</p>
     * <p>Each {@link net.smartcosmos.objects.model.context.IRelationship} will be serialized using a
     * {@link net.smartcosmos.util.json.ViewType#Standard} view.</p>
     * <p>One can use this query to answer the question "Tell me all of the relationships my Vehicle has, which might
     * be two: an {@link net.smartcosmos.objects.model.context.IObject} that is the "garage" where the vehicle is worked
     * on and another {@link net.smartcosmos.objects.model.context.IObject} that is the "owner" of the vehicle. Compare
     * this result with the collection returned from
     * {@link #findReverseRelationships(net.smartcosmos.model.base.EntityReferenceType, String, String)}.</p>
     *
     * @param entityReferenceType owner/parent entity reference type
     * @param referenceUrn        owner/parent reference URN
     * @param relationshipType    case-sensitive name of the relationship
     * @return Non-null collection of matching relationships; collection may have a size of 0 to indicate no matches
     * @throws ServiceException on error
     */
    Collection<IRelationship> findRelationships(EntityReferenceType entityReferenceType,
                                                String referenceUrn,
                                                String relationshipType) throws ServiceException;

    /**
     * <p>Retrieves <b>all</b> child entities that the specified entity has the specified relationship type with.</p>
     * <p>Each {@link net.smartcosmos.objects.model.context.IRelationship} will be serialized using the specified field
     * verbosity.</p>
     * <p>One can use this query to answer the question "Tell me all of the relationships my Vehicle has, which might
     * be two: an {@link net.smartcosmos.objects.model.context.IObject} that is the "garage" where the vehicle is worked
     * on and another {@link net.smartcosmos.objects.model.context.IObject} that is the "owner" of the vehicle. Compare
     * this result with the collection returned from
     * {@link #findReverseRelationships(net.smartcosmos.model.base.EntityReferenceType, String, String)}.</p>
     *
     * @param entityReferenceType owner/parent entity reference type
     * @param referenceUrn        owner/parent reference URN
     * @param relationshipType    case-sensitive name of the relationship
     * @param viewType            Field verbosity
     * @return Non-null collection of matching relationships; collection may have a size of 0 to indicate no matches
     * @throws ServiceException on error
     */
    Collection<IRelationship> findRelationships(EntityReferenceType entityReferenceType,
                                                String referenceUrn,
                                                String relationshipType,
                                                ViewType viewType) throws ServiceException;

    /**
     * <p>Retrieves <b>all</b> child entities that the specified entity has a relationship with.</p>
     * <p>Each {@link net.smartcosmos.objects.model.context.IRelationship} will be serialized using a
     * {@link net.smartcosmos.util.json.ViewType#Standard} view.</p>
     *
     * @param entityReferenceType owner/parent entity reference type
     * @param referenceUrn        owner/parent reference URN
     * @return Non-null collection of matching relationships; collection may have a size of 0 to indicate no matches
     * @throws ServiceException on error
     */
    Collection<IRelationship> findAllRelationships(EntityReferenceType entityReferenceType,
                                                String referenceUrn) throws ServiceException;

    /**
     * <p>Retrieves <b>all</b> child entities that the specified entity has a relationship with.</p>
     * <p>Each {@link net.smartcosmos.objects.model.context.IRelationship} will be serialized using the specified field
     * verbosity.</p>
     *
     * @param entityReferenceType owner/parent entity reference type
     * @param referenceUrn        owner/parent reference URN
     * @param viewType            Field verbosity
     * @return Non-null collection of matching relationships; collection may have a size of 0 to indicate no matches
     * @throws ServiceException on error
     */
    Collection<IRelationship> findAllRelationships(EntityReferenceType entityReferenceType,
                                                String referenceUrn,
                                                ViewType viewType) throws ServiceException;

    /**
     * <p>Retrieves <b>all</b> owner/parent entities that the specified entity has the specified relationship type with.</p>
     * <p>Each {@link net.smartcosmos.objects.model.context.IRelationship} will be serialized using a
     * {@link net.smartcosmos.util.json.ViewType#Standard} view.</p>
     * <p>One can use this query to answer the question "Tell me all of the parent relationships to my "garage",
     * which might be four: an {@link net.smartcosmos.objects.model.context.IObject} for each "Vehicle" that has been to
     * the garage. Compare this result with the collection returned from
     * {@link #findRelationships(net.smartcosmos.model.base.EntityReferenceType, String, String)}.</p>
     *
     * @param entityReferenceType child entity reference type
     * @param referenceUrn        child reference URN
     * @param relationshipType    case-sensitive name of the relationship
     * @return Non-null collection of matching relationships; collection may have a size of 0 to indicate no matches
     * @throws ServiceException on error
     */
    Collection<IRelationship> findReverseRelationships(EntityReferenceType entityReferenceType,
                                                       String referenceUrn,
                                                       String relationshipType) throws ServiceException;

    /**
     * <p>Retrieves <b>all</b> owner/parent entities that the specified entity has the specified relationship type with.
     * </p>
     * <p>Each {@link net.smartcosmos.objects.model.context.IRelationship} will be serialized using the specified field
     * verbosity.
     * </p>
     * <p>One can use this query to answer the question "Tell me all of the parent relationships to my "garage", which
     * might be four: an {@link net.smartcosmos.objects.model.context.IObject} for each "Vehicle" that has been to the
     * garage. Compare this result with the collection returned from
     * {@link #findRelationships(net.smartcosmos.model.base.EntityReferenceType, String, String)}.</p>
     *
     * @param entityReferenceType child entity reference type
     * @param referenceUrn        child reference URN
     * @param relationshipType    case-sensitive name of the relationship
     * @param viewType            Field verbosity
     * @return Non-null collection of matching relationships; collection may have a size of 0 to indicate no matches
     * @throws ServiceException on error
     */
    Collection<IRelationship> findReverseRelationships(EntityReferenceType entityReferenceType,
                                                       String referenceUrn,
                                                       String relationshipType,
                                                       ViewType viewType) throws ServiceException;
}
