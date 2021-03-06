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

import net.smartcosmos.model.base.EntityReferenceType;
import net.smartcosmos.model.context.IAccount;
import net.smartcosmos.model.context.IUser;
import net.smartcosmos.objects.model.context.IRelationship;
import net.smartcosmos.platform.api.service.IEventService;

import java.util.Collection;

public interface IRelationshipDAO extends IAdvancedDAO<IRelationship>
{
    void purgeAllRelationships(EntityReferenceType entityReferenceType,
                               String referenceUrn,
                               IUser user,
                               IEventService eventSink);

    Collection<IRelationship> reverseFindAllRelationships(EntityReferenceType entityReferenceType,
                                                          String referenceUrn,
                                                          IAccount account);

    Collection<IRelationship> findAllRelationships(EntityReferenceType entityReferenceType,
                                                   String referenceUrn,
                                                   IAccount account);

    // Give me everyone who 'Likes' me
    Collection<IRelationship> reverseFindByRelationshipType(EntityReferenceType entityReferenceType,
                                                            String referenceUrn,
                                                            String relationshipType,
                                                            IAccount account);

    // Give me everyone that I 'Likes'
    Collection<IRelationship> findByRelationshipType(EntityReferenceType entityReferenceType,
                                                     String referenceUrn,
                                                     String relationshipType,
                                                     IAccount account);

    // Give me all of my relationships that are of entity type 'ObjectInteraction'
    Collection<IRelationship> findByRelatedEntityReferenceType(EntityReferenceType entityReferenceType,
                                                               String referenceUrn,
                                                               EntityReferenceType relatedEntityReferenceType,
                                                               IAccount account);

    // Give me all of my relationships between these two objects
    Collection<IRelationship> findAllBetween(EntityReferenceType entityReferenceType,
                                             String referenceUrn,
                                             EntityReferenceType relatedEntityReferenceType,
                                             String relatedReferenceUrn,
                                             IAccount account);

    // Give me all of my 'Peer' that are of type 'ObjectInteraction'
    @SuppressWarnings("checkstyle:linelength")
    Collection<IRelationship> findByRelatedEntityReferenceTypeAndRelationshipType(EntityReferenceType entityReferenceType,
                                                                                  String referenceUrn,
                                                                                  EntityReferenceType relatedEntityReferenceType,
                                                                                  String relationshipType,
                                                                                  IAccount account);

    // Find a specific relationship
    IRelationship findExplicitRelationship(EntityReferenceType entityReferenceType,
                                           String referenceUrn,
                                           String relationshipType,
                                           EntityReferenceType relatedEntityReferenceType,
                                           String relatedReferenceUrn,
                                           IAccount account);

    // Does *any* type of relationship exist between me and this other specific object?
    boolean relationshipExistsAny(EntityReferenceType entityReferenceType,
                                  String referenceUrn,
                                  EntityReferenceType relatedEntityReferenceType,
                                  String relatedReferenceUrn,
                                  IAccount account);

    // Does this *specific* relationship type exist between me and this other specific object?
    boolean relationshipExistsSpecific(EntityReferenceType entityReferenceType,
                                       String referenceUrn,
                                       String relationshipType,
                                       EntityReferenceType relatedEntityReferenceType,
                                       String relatedReferenceUrn,
                                       IAccount account);

    // Do I have any 'Peer' related objects?
    boolean relationshipExistsOfRelationshipType(EntityReferenceType entityReferenceType,
                                                 String referenceUrn,
                                                 String relationshipType,
                                                 IAccount account);

    // Do I have any 'ObjectInteraction' related objects?
    boolean relationshipExistsOfEntityReferenceType(EntityReferenceType entityReferenceType,
                                                    String referenceUrn,
                                                    EntityReferenceType relatedEntityReferenceType,
                                                    IAccount account);
}

