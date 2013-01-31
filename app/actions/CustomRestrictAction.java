/*
 * Copyright 2012 Steve Chaloner
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package actions;

import be.objectify.deadbolt.java.actions.RestrictAction;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import security.MyRoles;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
public class CustomRestrictAction extends Action<CustomRestrict>
{
    @Override
    public Result call(Http.Context context) throws Throwable
    {
        final CustomRestrict outerConfig = configuration;
        RestrictAction restrictionsAction = new RestrictAction(configuration.config(),
                                                               this.delegate)
        {
            @Override
            public List<String[]> getRoleGroups()
            {
                List<String[]> roleGroups = new ArrayList<String[]>();
                for (RoleGroup roleGroup : outerConfig.value())
                {
                    MyRoles[] value = roleGroup.value();
                    String[] group = new String[value.length];
                    for (int i = 0; i < value.length; i++)
                    {
                        group[i] = value[i].getName();
                    }
                    roleGroups.add(group);
                }
                return roleGroups;
            }
        };
        return restrictionsAction.call(context);
    }
}
