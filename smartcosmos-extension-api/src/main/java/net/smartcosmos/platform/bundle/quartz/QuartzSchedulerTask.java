package net.smartcosmos.platform.bundle.quartz;

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

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMultimap;
import io.dropwizard.servlets.tasks.Task;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;

/**
 * Administrative mechanisms for putting the Quartz scheduler into standby, pausing all jobs, resuming all jobs, or
 * (re-) starting the scheduler if it had been put into standby mode.
 * <p>
 * Query string must include a valid action parameter, e.g. ?action=start. Valid action values include:
 * <ul>
 * <li>standby</li>
 * <li>start</li>
 * <li>pauseAll</li>
 * <li>resumeAll</li>
 * </ul>
 * <p>
 * <b>NOTE:</b> For the differences between standby and pauseAll, please refer to the Quartz documentation with respect
 * to how each of these handles misfires.
 */
public class QuartzSchedulerTask extends Task
{
    private static final String RESULT_TEMPLATE = "{ result : %d }";

    private static final Logger LOG = LoggerFactory.getLogger(QuartzSchedulerTask.class);

    public static final String ACTION_STANDBY = "standby";

    public static final String ACTION_START = "start";

    public static final String ACTION_PAUSE_ALL = "pauseAll";

    public static final String ACTION_RESUME_ALL = "resumeAll";

    private final SchedulerFactory schedulerFactory;

    public QuartzSchedulerTask(SchedulerFactory schedulerFactory)
    {
        super("quartz-scheduler");
        this.schedulerFactory = schedulerFactory;
    }

    @Override
    public void execute(ImmutableMultimap<String, String> parameters, PrintWriter output) throws Exception
    {
        ImmutableCollection<String> groupNames = parameters.get("action");
        String action = groupNames.iterator().next();

        Scheduler scheduler = schedulerFactory.getScheduler();

        switch (action)
        {
            case ACTION_STANDBY:
                if (scheduler.isStarted())
                {
                    LOG.info("Placing Quartz Scheduler into standby...");
                    scheduler.standby();
                    output.println(String.format(RESULT_TEMPLATE, 1));
                } else
                {
                    output.println(String.format(RESULT_TEMPLATE, 0));
                }
                break;
            case ACTION_START:
                if (scheduler.isInStandbyMode())
                {
                    LOG.info("Resuming Quartz Scheduler from standby...");
                    scheduler.start();
                    output.println(String.format(RESULT_TEMPLATE, 1));
                } else
                {
                    output.println(String.format(RESULT_TEMPLATE, 0));
                }

                break;
            case ACTION_PAUSE_ALL:
                LOG.info("Pausing all Quartz Scheduler triggers...");
                scheduler.pauseAll();
                output.println(String.format(RESULT_TEMPLATE, 1));
                break;
            case ACTION_RESUME_ALL:
                LOG.info("Resuming all Quartz Scheduler triggers...");
                scheduler.resumeAll();
                output.println(String.format(RESULT_TEMPLATE, 1));
                break;
            default:
                output.println(String.format(RESULT_TEMPLATE, 0));
                break;
        }

        output.flush();

    }
}
