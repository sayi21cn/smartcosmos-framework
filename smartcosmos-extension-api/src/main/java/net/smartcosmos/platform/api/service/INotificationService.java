package net.smartcosmos.platform.api.service;

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

import net.smartcosmos.model.integration.INotificationEndpoint;
import net.smartcosmos.platform.api.IService;

public interface INotificationService extends IService, IHealthCheckable
{
    String createTopic(INotificationEndpoint notificationEndpoint);

    void deleteTopic(INotificationEndpoint notificationEndpoint);

    void publish(INotificationEndpoint notificationEndpoint, String json);

    String subscribe(INotificationEndpoint notificationEndpoint);

    void unsubscribe(INotificationEndpoint notificationEndpoint);

    void confirmSubscription(INotificationEndpoint notificationEndpoint, String token);
}
